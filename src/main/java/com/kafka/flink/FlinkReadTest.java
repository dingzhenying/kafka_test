package com.kafka.flink;

import com.kafka.flink.entity.Hiacloud;
import com.sun.xml.internal.ws.resources.TubelineassemblyMessages;
import kafka.utils.json.JsonObject;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import sun.nio.cs.HistoricallyNamedCharset;

import java.util.Properties;

public class FlinkReadTest {
    public static void main(String[] args) throws Exception {
        //定义流处理环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 非常关键，一定要设置启动检查点！！
        env.enableCheckpointing(5000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //配置kafka信息
        Properties props = new Properties();
        props.setProperty("bootstrap.servers",
                "192.168.66.194:9092");
        props.setProperty("zookeeper.connect",
                "192.168.66.194:2181,192.168.66.195:2181,192.168.66.193:2181");
        props.setProperty("group.id", "kafka_to_hdfs");
//读取数据
        FlinkKafkaConsumer010<String> consumer = new FlinkKafkaConsumer010<>(
                "hiacloud-ts-dev", new SimpleStringSchema(), props);
//设置只读取最新数据
        consumer.setStartFromLatest();
//添加kafka为数据源
        DataStream<String> stream = env.addSource(consumer);
        //stream.print();
        Hiacloud hiacloud = new Hiacloud();
        DataStreamSink<Hiacloud> ss=stream.flatMap(new MessageSplitter()).print();


        //stream.writeAsText("C:\\Users\\Administrator\\Desktop\\aa\\flink");
        env.execute("Kafka-to-HDFS");
    }
}
