package com.kafka.main;

import java.util.logging.Logger;

/**
 * log4j日志生成器
 */
public class KafkaLog4jApp {
    private static Logger logger = Logger.getLogger(KafkaLog4jApp.class.getName());

    public static void main(String[] args) throws Exception {
        int index = 0;

        while(true) {
            Thread.sleep(1000);
            logger.info("value is: " + index++);
        }
    }
}
