package com.kafka.flink;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kafka.flink.entity.Hiacloud;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.Map;

/**
 * 拆分数据
 */
public class   MessageSplitter implements FlatMapFunction<String, Hiacloud> {

    @Override
    public void flatMap(String value, Collector<Hiacloud> out) throws Exception {
        if (value != null) {
            Hiacloud hiacloud= JSONObject.parseObject(JSONArray.parseArray(value).get(0).toString(),Hiacloud.class);
            out.collect(hiacloud);
        }
    }
    public static void main (String[] args){
        String aa= "[{\"namespace\":\"000000\",\"internalSeriesId\":\"hiacloud0003000098L[]\",\"regions\":10,\"t\":1545632786000,\"s\":0,\"v\":\"L#1545632786000\",\"gatewayId\":\"hiacloud\",\"pointId\":\"0003000098L\"}]";
       Hiacloud hiacloud= JSONObject.parseObject(JSONArray.parseArray(aa).get(0).toString(),Hiacloud.class);

        System.out.println(hiacloud.getGatewayId());
    }
}