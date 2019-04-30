package com.kafka.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
/**
 * Created by Dingzhenying on 2018/8/2220:00
 * 生产者
 */
public class ProducerTest {

    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        InputStream inStream = ProducerTest.class.getClassLoader().getResourceAsStream("producer.properties");

        properties.load(inStream);

        Producer<String, String> producer = new KafkaProducer<>(properties);
        String TOPIC = "test2";
        for (int messageNo = 1; messageNo < 10000; messageNo++) {
            Thread.sleep(1000);
            producer.send(new ProducerRecord<String, String>(TOPIC,messageNo + "", messageNo+"_"+UUID.randomUUID() + "itcast"));
            System.out.println(messageNo+"_"+UUID.randomUUID() + "itcast");
        }
    }
}
