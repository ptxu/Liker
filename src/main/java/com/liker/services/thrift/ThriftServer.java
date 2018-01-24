/**
 * Copyright: Copyright (c) 2018 
 * Company:深圳市深网视界科技有限公司
 * 
 * @author dell
 * @date 2018年1月23日 下午4:58:42
 * @version V1.0
 */
package com.liker.services.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liker.config.SystemConfig;
import com.liker.services.thrift.impl.FullTextIndexServiceImpl;

import liker.FullTextIndexService;
import liker.FullTextIndexService.Iface;

/**
 * @ClassName: ThriftServer
 * @Description: ThriftServer
 * @author xupengtao
 * @date 2018年1月23日 下午4:58:42
 *
 */
public class ThriftServer {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ThriftServer.class);

    public static void start() {
        Thread thread = new Thread(() -> {
            try {
                TServerSocket serverSocket = new TServerSocket(SystemConfig.getInstance().getThriftPort());
                TThreadPoolServer.Args serverParams = new TThreadPoolServer.Args(serverSocket);
                serverParams.protocolFactory(new TBinaryProtocol.Factory());
                serverParams.processor(new FullTextIndexService.Processor<Iface>(new FullTextIndexServiceImpl()));
                TServer server = new TThreadPoolServer(serverParams);
                LOGGER.info("Thrift server has started on port : " + SystemConfig.getInstance().getThriftPort());
                server.serve();
            }
            catch (Exception e) {
                LOGGER.error("Thrift server start fail.", e);
            }
        });
        thread.setName(ThriftServer.class.getSimpleName());
        thread.start();
    }
}
