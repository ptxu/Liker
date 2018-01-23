package com.liker;

import com.liker.core.FullTextIndexWriter;
import com.liker.services.http.HttpServer;
import com.liker.services.thrift.ThriftServer;

/**
 * @ClassName: Main
 * @Description: Main
 * @author dell
 * @date 2018年1月23日 下午5:59:52
 *
 */
public class Main {

    /**
     * @Title: main
     * @Description: main
     * @param String[]
     */
    public static void run(String[] args) {
        FullTextIndexWriter.start();
        ThriftServer.start();
        HttpServer.start();
    }

}
