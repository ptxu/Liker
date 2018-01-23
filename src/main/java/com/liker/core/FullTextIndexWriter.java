package com.liker.core;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.liker.model.Field;
import com.liker.model.IndexTask;
import com.liker.model.TaskType;

public class FullTextIndexWriter {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FullTextIndexWriter.class);

    /**
     * task queue
     */
    private static final Queue<IndexTask> TASK_QUEUE = new LinkedList<>();

    /**
     * 对象锁
     */
    private static final Object SYNC_ROOT = new Object();

    /**
     * WORKER
     */
    private static Thread WORKER = null;

    private static void initCreateIndex() {
        Path path = FileSystems.getDefault().getPath("", "index");
        if (path.toFile().exists()) {
            LOGGER.info(String.format("dir [%s] is exist.", path.toString()));
        }
        else {
            try {
                Directory directory = FSDirectory.open(path);
                Analyzer analyzer = new IKAnalyzer();
                IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
                // 提交
                indexWriter.commit();
                // 关闭
                indexWriter.close();
            }
            catch (IOException e) {
                LOGGER.error(String.format("create dir [%s] error.", path.toString()), e);
                e.printStackTrace();
            }
            LOGGER.info(String.format("create dir [%s].", path.toString()));
        }
    }

    /**
     * @Title: start
     * @Description: 启动特征比对器
     */
    public static void start() {
        initCreateIndex();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    IndexWriter indexWriter = null;
                    try {
                        List<IndexTask> taskList = pollList();

                        if (taskList == null || taskList.size() <= 0) {
                            Thread.sleep(3 * 1000);
                            continue;
                        }
                        Path path = FileSystems.getDefault().getPath("", "index");
                        Directory directory = FSDirectory.open(path);
                        Analyzer analyzer = new IKAnalyzer();
                        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer).setOpenMode(IndexWriterConfig.OpenMode.APPEND);
                        indexWriter = new IndexWriter(directory, indexWriterConfig);

                        Map<TaskType, List<IndexTask>> map = taskList.stream().collect(Collectors.groupingBy(IndexTask::getType));
                        for (TaskType type : map.keySet()) {
                            if (type.equals(TaskType.Add)) {
                                if (map.get(type) != null && map.get(type).size() > 0) {
                                    List<com.liker.model.Document> docList = map.get(type).stream().map(p -> p.getDoc()).collect(Collectors.toList());
                                    LOGGER.info(String.format("add [%d] document to index.", docList.size()));
                                    createIndex(docList, indexWriter);
                                }
                            }
                            else if (type.equals(TaskType.Update)) {
                                for (IndexTask task : map.get(type)) {
                                    LOGGER.info(String.format("update [%s] document.", task.getTargetField().toString()));
                                    updateIndex(task.getTargetField(), task.getDoc(), indexWriter);
                                }
                            }
                            else if (type.equals(TaskType.Delete)) {
                                for (IndexTask task : map.get(type)) {
                                    LOGGER.info(String.format("delete [%s] document.", task.getTargetField().toString()));
                                    delIndex(task.getTargetField(), indexWriter);
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        if (e instanceof InterruptedException) {
                            Thread.currentThread().interrupt();
                        }
                        else {
                            LOGGER.error("write index fail.", e);
                        }
                    }
                    finally {
                        // 提交
                        if (indexWriter != null) {
                            try {
                                indexWriter.commit();
                                indexWriter.close();
                            }
                            catch (IOException e) {
                                LOGGER.error("close index writer fail.", e);
                            }
                        }
                    }
                }
            }
        };

        WORKER = new Thread(runnable);
        WORKER.setName("FeatureComparator.Thread");
        WORKER.start();
    }

    /**
     * @Title: addTask
     * @Description: 添加任务
     * @param task
     *            FaceSearchTask
     * @return 是/否
     */
    public static boolean addTask(IndexTask... tasks) {
        synchronized (SYNC_ROOT) {
            for (IndexTask task : tasks) {
                TASK_QUEUE.offer(task);
            }
            return true;
        }
    }

    /**
     * @Title: pollList
     * @Description: 取出数据
     * @return List
     */
    private static List<IndexTask> pollList() {
        List<IndexTask> list = new ArrayList<>();
        synchronized (SYNC_ROOT) {
            while (TASK_QUEUE.size() > 0) {
                list.add(TASK_QUEUE.poll());
            }
        }
        return list;
    }

    /**
     * @Title: stop
     * @Description: 停止特征比对
     */
    public static void stop() {
        if (WORKER != null) {
            WORKER.interrupt();
        }
        WORKER = null;
    }

    private static void createIndex(List<com.liker.model.Document> docList, IndexWriter indexWriter) throws IOException {
        for (com.liker.model.Document doc : docList) {
            // 定义文档
            Document document = new Document();
            // 定义文档字段
            for (com.liker.model.Field field : doc.getFields()) {
                if (field.isIndexed()) {
                    document.add(new TextField(field.getFieldName(), field.getFieldValue(), Store.NO));
                }
                else {
                    document.add(new StoredField(field.getFieldName(), field.getFieldValue()));
                }
            }
            indexWriter.addDocument(document);
        }
    }

    private static void delIndex(Field tartgetField, IndexWriter indexWriter) throws IOException {
        indexWriter.deleteDocuments(new Term(tartgetField.getFieldName(), tartgetField.getFieldValue()));
    }

    private static void updateIndex(Field tartgetField, com.liker.model.Document doc, IndexWriter indexWriter) throws IOException {
        Document document = new Document();
        for (com.liker.model.Field field : doc.getFields()) {
            if (field.isIndexed()) {
                document.add(new TextField(field.getFieldName(), field.getFieldValue(), Store.NO));
            }
            else {
                document.add(new StoredField(field.getFieldName(), field.getFieldValue()));
            }
        }
        indexWriter.updateDocument(new Term(tartgetField.getFieldName(), tartgetField.getFieldValue()), document);

    }

}