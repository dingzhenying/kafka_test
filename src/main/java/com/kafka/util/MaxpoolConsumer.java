package com.kafka.util;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import org.apache.kafka.clients.consumer.Consumer;

import java.util.Collections;

public class MaxpoolConsumer{
    private  Consumer<String,String> consumer;

    public void runConsumer(){
        consumer.subscribe(Collections.singletonList("topicName"));
    }
    private void pollRecordsAndProcess(){
        consumer.poll(100);
    }

    public static void main (String[] args){
        new Thread(new Thread1("s1111")).start();
        new Thread(new Thread1("2222")).start();

    }
}


class Thread1  implements Runnable{
    private String name;
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行:" + i);
            try {
                Thread.sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public Thread1(String name){
        this.name=name;
    }
}