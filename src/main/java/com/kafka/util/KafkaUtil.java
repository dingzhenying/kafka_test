package com.kafka.util;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import scala.collection.Seq;
import scala.collection.immutable.List;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class KafkaUtil {
    // 创建主题
    private static void createTopic(String topicName) {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 创建一个单分区单副本名为t1的topic
        AdminUtils.createTopic(zkUtils, topicName, 10, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
        zkUtils.close();
        System.out.println("创建topic:"+topicName+"成功!");
    }

    // 删除主题(未彻底删除)
    private static void deleteTopic(String topicName) {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 删除topic 't1'
        AdminUtils.deleteTopic(zkUtils, topicName);
        zkUtils.close();
        System.out.println("删除topic:"+topicName+"成功!");
    }

    // 修改主题
    private static void editTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), NAME);
        // 增加topic级别属性
        props.put("min.cleanable.dirty.ratio", "0.3");
        // 删除topic级别属性
        props.remove("max.message.bytes");
        // 修改topic 'test'的属性
        AdminUtils.changeTopicConfig(zkUtils, NAME, props);
        zkUtils.close();
    }

    // 主题读取
    private static void queryTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 获取topic 'test'的topic属性属性
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), NAME);
        // 查询topic-level属性
        Iterator it = props.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        zkUtils.close();
    }
    /**
     * 获取topic列表
     *
     *
     */
    public static void topicList(){
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        List<String> topicList = zkUtils.getAllTopics().toList();
        System.out.println(topicList);
    }


    /**
     * 生产者
     *
     * @param
     */
    public static void Producer(String topicName) throws Exception{
        Properties props = new Properties();

        // 服务器ip:端口号，集群用逗号分隔
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,KAFKA_URL);
        // key序列化指定类
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value序列化指定类
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        // 生产者对象
        Producer<String, String> producer = new KafkaProducer<>(props);
        int i=0;
        // 向test_topic发送hello, kafka
        Random random = new Random();

        while (true&&i<=200000){
            Thread.sleep(400);
//            String data="[{\"namespace\":\"000000\",\"internalSeriesId\":\"hiacloud0003000094L[]\",\"regions\":10,\"t\":"+System.currentTimeMillis()+",\"s\":0,\"v\":\"L#1547999583000\",\"gatewayId\":\"hiacloud\",\"pointId\":\"000002F\"}]";
            String data1 ="[{\"namespace\": \"000001\", \"internalSeriesId\": \"hiacloud0003000094L[]\", \"regions\": 10, \"v\": \"F#"+(i*2.54+random.nextDouble())+"\", \"s\": 0, \"t\":"+System.currentTimeMillis()+", \"gatewayId\": \"hiacloud\", \"pointId\": \"/SymLink-bewg-AnNingShiDEWSC/PLC01.PLC01.JSLLLJ\"}]";
            producer.send(new ProducerRecord<String, String>(topicName,String.valueOf(System.currentTimeMillis()), data1));
            System.out.printf(" value = %s, key = %s", data1, System.currentTimeMillis());
            System.out.println();
            String data ="[{\"namespace\": \"000002\", \"internalSeriesId\": \"hiacloud0003000094L[]\", \"regions\": 10, \"v\": \"F#"+random.nextDouble()*1000+"\", \"s\": 0, \"t\":"+System.currentTimeMillis()+", \"gatewayId\": \"hiacloud\", \"pointId\": \"/SymLink-bewg-AnNingShiDEWSC/ChnOPC.ANSDEWS.ANSDEWS.ANSDEWS-FLOAT.TAG53\"}]";
            producer.send(new ProducerRecord<String, String>(topicName,String.valueOf(System.currentTimeMillis()), data));
            System.out.printf(" value = %s, key = %s", data, System.currentTimeMillis());
            System.out.println();
            Thread.sleep(400);
            String data3 ="[{\"namespace\": \"000003\", \"internalSeriesId\": \"hiacloud0003000094L[]\", \"regions\": 10, \"v\": \"F#"+random.nextDouble()*1000+"\", \"s\": 0, \"t\":"+System.currentTimeMillis()+", \"gatewayId\": \"hiacloud\", \"pointId\": \"/SymLink-bewg-AnNingShiDEWSC/PLC01.PLC01.JSLLLJ\"}]";
            producer.send(new ProducerRecord<String, String>(topicName,String.valueOf(System.currentTimeMillis()), data3));
            System.out.printf(" value = %s, key = %s", data3, System.currentTimeMillis());
            System.out.println();
            i++;
        }
    }

    /**
     * 消费者
     *
     */
    public static void Consumer(String topicName){
        //直接获取数据
        Properties props = new Properties();

        // 服务器ip:端口号，集群用逗号分隔
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_URL);
        // 消费者指定组，名称可以随意，注意相同消费组中的消费者只能对同一个分区消费一次
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "javaConsumer");
        // 是否启用自动提交，默认true
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 自动提交间隔时间1s
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        props.put("auto.offset.reset", "latest");//latest, earliest, none
        // key反序列化 指定类
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // value反序列化指定类，注意生产者与消费者要保持一致，否则解析出问题
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 消费者对象
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);

        kafkaConsumer.subscribe(Arrays.asList(topicName));
        while (true) {
            //非多线程并发安全的（多线程会抛异常）
            ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("timestamp = %d, value = %s, key = %s, offset = %d,partition = %s\t", record.timestamp(), record.value(),record.key(),record.offset(),record.partition());
                System.out.println();
            }
        }
    }
//    private static final String URL = "192.168.106.199:2181";
//    private static final String KAFKA_URL = "192.168.106.199:9092";
//    private static final String KAFKA_URL = "192.168.66.194:9092,192.168.66.193:9092,192.168.66.192:9092";
    private static final String URL = "cdh-worker-dc-1:2181,cdh-worker-dc-2:2181,cdh-worker-dc-3:2181";
    private static final String KAFKA_URL = "microservice-dc-1:9092";
    private static final String NAME = "hiacloud-ts-v3";

    public static void main(String[] args) {
        try {
//            createTopic(NAME);
//            topicList();
//            deleteTopic("dc_data");
//            queryTopic();
            //生产者生产数据
//            Producer(NAME);
//            消费者消费数据
            Consumer(NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
