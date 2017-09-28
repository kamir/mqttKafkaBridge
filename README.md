# MqttKafkaBridge
A bridge consumes MQTT messages and republishes them on Kafka topic.
Multiple instances can be used to esablish m:n relations.



!!! WARNING !!! 
Project was refactored, needs an update to all docs.



# Solution 1:
Building a bidirectional bridge using a device-cluster connector and a cluster-device connector in two parallel threads.

## Test 
java -cp target/mqttKafkaBridge-0.3.0-jar-with-dependencies.jar com.cloudera.iot.bridge.simple.CDDCBridge 

## Configuration
All parameters are hardcoded in classes:

-MqttSubscriber
-MqttPublisher
-SubscribeToKafka
-PublishViaKafka
-ConsumerLoop

# Solution 2: 
Define two "Camel-routes".

# Connectivity Issue:
https://stackoverflow.com/questions/30606447/kafka-consumer-fetching-metadata-for-topics-failed

 




***Note***: you can't run more than one bridge using the default settings, since two clients cannot connect to the same MQTT server with the same client ID. 
Additionally, you will get multiple messages published to Kafka for each message published to MQTT. If you wish to run multiple instances, you'll need to divide 
up the topics among the instances, and make sure to give them different IDs.

## Logging
`mqttKafkaBridge` uses [log4j](http://logging.apache.org/log4j/2.x/) for logging, as do the [Paho](http://www.eclipse.org/paho/) 
and [Kafka](http://kafka.apache.org/) libraries it uses. There is a default `log4j.properties` file packaged with the jar which 
simply prints all messages of level `INFO` or greater to the console. If you want to customize logging, simply create your 
own `log4j.properties` file, and start up `mqttKafkaBridge` as follows:

    $ java -Dlog4j.configuration=file:///path/to/log4j.properties -jar mqttKafkaBridge.jar [options...]

