package com.cloudera.iot.bridge.simple;

import com.cloudera.iot.kafka.PublishViaKafka;
import com.cloudera.iot.mqtt.MqttSubscriber;

public class Device2ClusterLink {
    
    MqttSubscriber streamReceiver = null;
    PublishViaKafka streamSender = null;
    
    public Device2ClusterLink() {
        streamReceiver = new MqttSubscriber();

        streamSender = new PublishViaKafka();
        
        System.out.println(">>> Device2ClusterLink.start() ");

        streamReceiver.setStreamSender( streamSender );
        streamReceiver.run();
    }
    
    public static void main(String[] ARGS) {
        Device2ClusterLink cd = new Device2ClusterLink();
    }

}
