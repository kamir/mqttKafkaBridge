package com.cloudera.iot.bridge.simple;

import com.cloudera.iot.kafka.SubscribeToKafka;
import com.cloudera.iot.mqtt.MqttPublisher;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 *
 * @author kamir
 */
public class Cluster2DeviceLink {

    MqttPublisher cmdPublisher = null;
    SubscribeToKafka cmdReceiver = null;
    
    public static void main(String[] args) {
        Cluster2DeviceLink cd = new Cluster2DeviceLink();
        cd.start();
    }

    void start() {
        cmdReceiver = new SubscribeToKafka();
        
        cmdPublisher = new MqttPublisher();
         
        cmdReceiver.connect( cmdPublisher  );
        
        cmdReceiver.init();
         
    }

}
