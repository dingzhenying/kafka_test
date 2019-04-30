package com.kafka.flink;

import com.kafka.flink.entity.Hiacloud;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;

import java.util.Properties;

public class FlinkReadKafka {
    public static void main (String[] args) throws Exception{
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 非常关键，一定要设置启动检查点！！
        env.enableCheckpointing(5000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "192.168.66.194:9092");
        props.setProperty("group.id", "flink-group");

        //    args[0] = "test-0921";  //传入的是kafka中的topic
        FlinkKafkaConsumer010<String> consumer =
                new FlinkKafkaConsumer010<>("hiacloud-ts-dev", new SimpleStringSchema(), props);
//        consumer.assignTimestampsAndWatermarks(new MessageWaterEmitter());//分区器

//        DataStream<Tuple2<Hiacloud, String>> keyedStream = env
//                .addSource(consumer)
//                .flatMap(new MessageSplitter())
//                .keyBy(0)
//                .timeWindow(Time.seconds(10))
//                .apply(new WindowFunction<Tuple2<Hiacloud, String>, Tuple2<Hiacloud, String>, Tuple, TimeWindow>() {
//                    public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple2<Hiacloud, String>> input, Collector<Tuple2<Hiacloud, String>> out) throws Exception {
//                        long sum = 0L;
//                        int count = 0;
//                        String hiacloudInfo=null;
//                        for (Tuple2<Hiacloud, String> record: input) {
//                            sum +=Integer.valueOf(record.f1) ;
//                            count++;
//                            hiacloudInfo=record.f0.toString();
//                        }
//                        Tuple2<Hiacloud, String> result = input.iterator().next();
//                        result.f1 = String.valueOf(sum / count);
//                        out.collect(result);
//                    }
//                });

        //将结果打印出来
       // keyedStream.print();
        //    将结果保存到文件中
        //    args[1] = "E:\\FlinkTest\\KafkaFlinkTest";//传入的是结果保存的路径
       // keyedStream.writeAsText("C:\\Users\\Administrator\\Desktop\\aa\\flink");
        env.execute("Kafka-FlinkTest");
    }
}
