package com.kafka.util;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;
/**
 * Created by Dingzhenying on 2018/8/2220:07
 * 分区器
 */
public class PartitionTest implements Partitioner {
    /**
     * 系统会将生产者的生产属性传递过来供开发者使用,就是producer.properties中的参数值
     * prop.getProperty("metadata.broker.list");
     */
    private kafka.utils.VerifiableProperties prop;

    /**
     * 必须提供一个构造方法，参数为VerifiableProperties
     * @param prop
     */
    public PartitionTest(VerifiableProperties prop) {
        super();
        this.prop = prop;
    }


    /**
     * numberPartition:表示partition的数量
     */
    @Override
    public int partition(Object key, int numberPartition) {
        prop.getProperty("metadata.broker.list");
        //生产者的key是key1，key2
        String keyStr = (String) key;
        if(keyStr.contains("1"))
        {
            return 1;//返回1分区，
        }
        else
        {
            if(keyStr.contains("2"))
            {
                return 2;//返回2分区
            }
        }
        return 0;
    }

}
