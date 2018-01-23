package com.liker.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: SystemConfig
 * @Description: SystemConfig
 * @author xupengtao
 * @date 2018年1月12日 下午6:50:31
 *
 */
public class SystemConfig {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfig.class);

    /**
     * 系统配置文件名
     */
    private static final String SYSTEM_CONFIG_FILE_NAME = "SystemConfig.properties";

    /**
     * http 端口
     */
    private int httpPort;

    /**
     * thrift 端口
     */
    private int thriftPort;

    private static class SystemConfigHolder {

        private static SystemConfig INSTANCE = new SystemConfig();
    }

    public static SystemConfig getInstance() {
        return SystemConfigHolder.INSTANCE;
    }

    private SystemConfig() {

        InputStream inputStream = null;
        Properties properties = null;
        try {
            inputStream = SystemConfig.class.getClassLoader().getResourceAsStream(SYSTEM_CONFIG_FILE_NAME);
            properties = new Properties();
            properties.load(inputStream);

            String temp = null;
            if (properties.containsKey("httpPort")) {
                temp = properties.getProperty("httpPort");
            }
            if (StringUtils.isNoneEmpty(temp)) {
                httpPort = Integer.valueOf(temp);
            }

            temp = null;
            if (properties.containsKey("thriftPort")) {
                temp = properties.getProperty("thriftPort");
            }
            if (StringUtils.isNoneEmpty(temp)) {
                thriftPort = Integer.valueOf(temp);
            }
        }
        catch (IOException e) {
            LOGGER.error("init system config properties exception", e);
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (properties != null) {
                    properties.clear();
                }
            }
            catch (IOException e) {
                LOGGER.error("load SystemConfig properties file error.", e);
            }
            inputStream = null;
            properties = null;
        }
    }

    /**
     * getter method
     * 
     * @return the httpPort
     */
    public int getHttpPort() {
        return httpPort;
    }

    /**
     * setter method
     * 
     * @param httpPort
     *            the httpPort to set
     */
    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    /**
     * getter method
     * 
     * @return the thriftPort
     */
    public int getThriftPort() {
        return thriftPort;
    }

    /**
     * setter method
     * 
     * @param thriftPort
     *            the thriftPort to set
     */
    public void setThriftPort(int thriftPort) {
        this.thriftPort = thriftPort;
    }

}
