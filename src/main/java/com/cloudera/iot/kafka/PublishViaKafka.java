/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudera.iot.kafka;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 *
 * @author kamir
 */
public class PublishViaKafka {

    static String topic = "sunfounder_demo_stream";

    Producer<String, String> producer = null;

    public PublishViaKafka() {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        producer = new KafkaProducer<String, String>(props);

    }

    public static void main(String[] args) {
        PublishViaKafka p = new PublishViaKafka();
        p.send(topic, "TEST Note");
        p.stop();
    }

    public void stop() {
        producer.close();
    }

    public void send(String topic, String data) {
        producer.send(new ProducerRecord(topic, data));
    }

}
