package com.cloudera.iot.kafka;

import com.cloudera.iot.mqtt.MqttPublisher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit; 

public class SubscribeToKafka {
    
    public static Hashtable<String,String> mapping = new Hashtable();

    public static void init() {
        
        
        String groupId = "sunfounder_demo_group";
        mapping.put("sunfounder_demo_cmd", "sunfounder_demo/cmd");
        
        List<String> topics = Arrays.asList("sunfounder_demo_cmd");

        final ExecutorService executor = Executors.newFixedThreadPool(1);

        final List<ConsumerLoop> consumers = new ArrayList<>();

        ConsumerLoop consumer = new ConsumerLoop(1, groupId, topics);
        consumers.add(consumer);
        executor.submit(consumer);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (ConsumerLoop consumer : consumers) {
                    consumer.shutdown();
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static MqttPublisher pub = null;
            
    public void connect(MqttPublisher cmdPublisher) {
        pub = cmdPublisher;
    }

}
