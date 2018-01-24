package com.liker.services.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liker.config.SystemConfig;
import com.liker.services.http.channel.HttpChannelInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName: HttpServer
 * @Description: HttpServer
 * @author xupengtao
 * @date 2018年1月12日 下午9:59:11
 *
 */
public class HttpServer {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void start() {
        Thread thread = new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new HttpChannelInitializer());

                LOGGER.info("Http server has started on port : " + SystemConfig.getInstance().getHttpPort());

                Channel ch = b.bind(SystemConfig.getInstance().getHttpPort()).sync().channel();
                ch.closeFuture().sync();
            }
            catch (Exception e) {
                LOGGER.error("Netty server start fail.", e);
            }
            finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

        });
        thread.setName(HttpServer.class.getSimpleName());
        thread.start();
    }

}
