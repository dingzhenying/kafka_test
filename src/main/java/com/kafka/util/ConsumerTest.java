package com.kafka.util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * Created by Dingzhenying on 2018/8/2220:04
 * 消费者实例
 */
public class ConsumerTest {
    public static void main(String[] args) throws Exception{
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.66.194:9092");
        props.put("group.id", "ConsumerTest");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");// 每次都是从头开始消费（from-beginning），可配置其他消费方式
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("dzy-test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            if (records.count() > 0) {
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.toString());
                   // System.out.println("_topic_"+record.topic()+"_key_"+record.key()+"_"+record.value()+"_position_"+record.partition());
                }

            }
        }

    }

}
