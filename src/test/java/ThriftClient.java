import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.liker.config.SystemConfig;

import liker.AddTaskResponseResult;
import liker.Document;
import liker.Field;
import liker.FullTextIndexService;
import liker.IndexRequestParam;
import liker.IndexTask;
import liker.IndexTaskType;
import liker.Keyword;
import liker.SearchResponseResult;

/**
 * @ClassName: ThriftClient
 * @Description: Thrift客户端
 * @author xupengtao
 * @date 2018年1月24日 上午10:10:06
 *
 */
public class ThriftClient {

    /**
     * @Title: main
     * @Description: main
     * @param args
     *            String[]
     */
    public static void main(String[] args) {
        // testAddIndexTask();
        testFullTextSearch();
    }

    /**
     * @Title: testAddIndexTask
     * @Description: 测试添加索引任务
     */
    private static void testAddIndexTask() {
        TTransport transport = null;
        try {
            // 设置调用的服务地址为本地，端口为 7911
            transport = new TSocket("127.0.0.1", SystemConfig.getInstance().getThriftPort());
            transport.open();
            // 设置传输协议为 TBinaryProtocol
            TProtocol protocol = new TBinaryProtocol(transport);
            FullTextIndexService.Client client = new FullTextIndexService.Client(protocol);
            // 调用服务的 addIndexTask 方法
            List<IndexTask> tasks = new ArrayList<>();
            IndexTask task = new IndexTask();
            task.setType(IndexTaskType.Add);
            Document doc = new Document();
            List<Field> fields = new ArrayList<>();
            Field field = new Field();
            field.setFieldName("id");
            field.setFieldValue("123456");
            field.setIndexed(false);
            fields.add(field);

            field = new Field();
            field.setFieldName("name");
            field.setFieldValue("你好，世界！");
            field.setIndexed(true);
            fields.add(field);
            doc.setFields(fields);
            task.setDoc(doc);

            tasks.add(task);
            AddTaskResponseResult response = client.addIndexTask(tasks);
            System.out.println(response.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    /**
     * @Title: testFullTextSearch
     * @Description: 测试全文检索
     */
    private static void testFullTextSearch() {
        TTransport transport = null;
        try {
            // 设置调用的服务地址为本地，端口为 7911
            transport = new TSocket("127.0.0.1", SystemConfig.getInstance().getThriftPort());
            transport.open();
            // 设置传输协议为 TBinaryProtocol
            TProtocol protocol = new TBinaryProtocol(transport);
            FullTextIndexService.Client client = new FullTextIndexService.Client(protocol);
            // 调用服务的 fullTextSearch 方法
            IndexRequestParam param = new IndexRequestParam();
            param.setCurrentPage(1);
            param.setPageSize(100);
            param.setKeywords(Arrays.asList(new Keyword("name", "你好", true)));
            param.setResponseFieldNames(Arrays.asList("id"));
            SearchResponseResult resoponse = client.fullTextSearch(param);
            System.out.println(resoponse.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}
