package com.liker.core;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.liker.model.Document;
import com.liker.model.Field;
import com.liker.model.FullTextIndexRequestParam;
import com.liker.model.FullTextIndexTargetResult;
import com.liker.model.Keyword;

public class FullTextIndexSearcher {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FullTextIndexSearcher.class);

    /**
     * @Title: crerateWorker
     * @Description: crerateWorker
     * @param param
     *            FullTextIndexRequestParam
     * @param query
     *            query
     * @param i
     *            searcher index
     */
    public static List<FullTextIndexTargetResult> search(FullTextIndexRequestParam param) throws Exception {
        // 定义索引目录
        Path path = FileSystems.getDefault().getPath("index");
        Directory directory = FSDirectory.open(path);
        // 定义索引查看器
        IndexReader indexReader = DirectoryReader.open(directory);
        // 定义搜索器
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        long startTime = System.currentTimeMillis();
        // 分页信息
        int page = param.getCurrentPage();
        int pageSize = param.getPageSize();
        int start = (page - 1) * pageSize;
        int end = start + pageSize;
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        for (Keyword keyword : param.getKeywords()) {
            Query query = null;
            if (keyword.isTokenized()) {
                query = new QueryParser(keyword.getFieldName(), new IKAnalyzer()).parse(keyword.getFieldValue());// 模糊搜索
            }
            else {
                Term term = new Term(keyword.getFieldName(), keyword.getFieldValue());
                query = new TermQuery(term);
            }
            builder.add(query, BooleanClause.Occur.MUST);
        }
        BooleanQuery booleanQuery = builder.build();
        TopDocs topDocs = indexSearcher.search(booleanQuery, end);// 根据end查询

        long totalPage = ((topDocs.totalHits / pageSize) == 0) && topDocs.totalHits > pageSize ? topDocs.totalHits / pageSize : ((topDocs.totalHits / pageSize) + 1);

        // 取出文档
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        int length = scoreDocs.length > end ? end : scoreDocs.length;
        // 遍历取出数据
        List<FullTextIndexTargetResult> targetList = new ArrayList<>();
        for (int j = start; j < length; j++) {
            ScoreDoc doc = scoreDocs[j];
            FullTextIndexTargetResult target = new FullTextIndexTargetResult();
            target.setScore(doc.score);
            Document targetDoc = new Document();
            for (String fieldName : param.getResponseFieldNames()) {
                org.apache.lucene.document.Document document = indexSearcher.doc(doc.doc);
                String value = document.get(fieldName);
                if (StringUtils.isNoneEmpty(value)) {
                    Field field = new Field();
                    field.setFieldName(fieldName);
                    field.setFieldValue(document.get(fieldName));
                    targetDoc.addField(field);
                }
            }
            target.setDoc(targetDoc);
            targetList.add(target);

        }
        indexReader.close();
        LOGGER.info(String.format("search %s use time %d ms, response %d records, page size is: %d / %d", Arrays.toString(param.getKeywords()), System.currentTimeMillis() - startTime,
                topDocs.totalHits, page, totalPage));
        return targetList;
    }
}