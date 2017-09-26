package com.cloudera.iot.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author kamir
 */
public class ConsumerLoop implements Runnable {
  private final KafkaConsumer<String, String> consumer;
  private final List<String> topics;
  private final int id;

  public ConsumerLoop(int id,
                      String groupId, 
                      List<String> topics) {
    this.id = id;
    this.topics = topics;
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", groupId);
    props.put("key.deserializer", StringDeserializer.class.getName());
    props.put("value.deserializer", StringDeserializer.class.getName());
    this.consumer = new KafkaConsumer<>(props);
  }
 
  @Override
  public void run() {
    try {
      consumer.subscribe(topics);

      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
        for (ConsumerRecord<String, String> record : records) {
          Map<String, Object> data = new HashMap<>();
          data.put("partition", record.partition());
          data.put("offset", record.offset());
          data.put("value", record.value());
          System.out.println(this.id + ": " + data);
          
          if ( SubscribeToKafka.pub != null ) {
              SubscribeToKafka.pub.sendMessage( SubscribeToKafka.mapping.get(record.topic()), record.value() );
                      
          }
          
        }
      }
    } catch (WakeupException e) {
      // ignore for shutdown 
    } catch (Exception ex) {
          Logger.getLogger(ConsumerLoop.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
      consumer.close();
    }
  }

  public void shutdown() {
    consumer.wakeup();
  }
}
    
