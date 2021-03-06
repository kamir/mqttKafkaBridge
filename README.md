# MqttKafkaBridge
An IoT bridge is a system, which consumes MQTT messages from an MQTT-Broker and republishes them on a Kafka topic. 
The bridge we implemented here connects the IoT plattform Eclipse Kapua with the Enterprise Data Hub, (CDH on premise or in the cloud).

Currently, we use only on instance of the bridge, but multiple instances can be used to esablish m:n relations.
Before we use many bridge instaces in a particular cluster, we have to add some more features:

1) - configurable route definitions.
2) - monitoring of the "long running" service
3) - packaging of CSD for CM based deployment.

!!! WARNING !!! 
The project was refactored recently, it still needs docu updates.

![sketch](https://github.com/kamir/mqttKafkaBridge/blob/master/docs/MQTT-Kafka-Bridge/Canvas%201.jpg?raw=true "Overview: Bridge between IoT platforms and data management platforms.")

The IoT platforms are focused on devices and connectivity. Data management platforms are focused on dealing with data from all devices in near real time and at scale. Both technologies need each other in a variety of contexts. Typically, a big data applocation can't offer device management, while an IoT system has doesn't offer the data analysis and ML capabilities.

# Quickstart:
1) - clone the repo into your working directory - this is needed only temporarily
2) - modify the code / routes 
3) - build the jar file and copy the project folder to /opt/clouder/iot/mqttKafkaBridge
```
mvn clean compile assemby:single
```
4) - execute the "control.sh" script in the CSD folder mqttKafkaBridge/src/MQTT2Kafka-1.0/scripts/
```
./src/MQTT2Kafka-1.0/scripts/ control.sh start
```
Assuming, the routes are fine and access is given to the system - the bridge should forward messages in two direction.

Our approach for bridging IoT platforms and CDH clusters uses two complementary solutions.

# Solution 1:
Building a bidirectional bridge using a device-cluster connector and a cluster-device connector in two parallel threads.
This approach allows any kind of custom code to be integrated without the learning overhead of additional frameworks.

## Test 
java -cp target/mqttKafkaBridge-0.3.0-jar-with-dependencies.jar com.cloudera.iot.bridge.simple.CDDCBridge 

## Configuration
All parameters are hardcoded in the classes:

-MqttSubscriber

-MqttPublisher

-SubscribeToKafka

-PublishViaKafka

-ConsumerLoop

# Solution 2: 
Using a standard message routing system allows us, to work with many different endpoints for data transfer. Working with enterprise integration patterns becomes easy this way. We simply define two "Camel-routes" between the topics we want to mirror.

java -cp target/mqttKafkaBridge-0.3.0-jar-with-dependencies.jar com.cloudera.iot.bridge.camel.CamelConsoleMain 

## Configuration
All routes are defined in the camel-context.xml file.


# Next Steps
1) Finish parametrization
2) Finish CSD based deployment
3) Manage logging and metrics via CM


# Open Issues:

## Connectivity Issue:
https://stackoverflow.com/questions/30606447/kafka-consumer-fetching-metadata-for-topics-failed

 




***Note***: you can't run more than one bridge using the default settings, since two clients cannot connect to the same MQTT server with the same client ID. 
Additionally, you will get multiple messages published to Kafka for each message published to MQTT. If you wish to run multiple instances, you'll need to divide 
up the topics among the instances, and make sure to give them different IDs.

## Logging
`mqttKafkaBridge` uses [log4j](http://logging.apache.org/log4j/2.x/) for logging, as do the [Paho](http://www.eclipse.org/paho/) 
and [Kafka](http://kafka.apache.org/) libraries it uses. There is a default `log4j.properties` file packaged with the jar which simply prints all messages of level `INFO` or greater to the console. If you want to customize logging, simply create your own `log4j.properties` file, and start up `mqttKafkaBridge` as follows:

    $ java -Dlog4j.configuration=file:///path/to/log4j.properties ... [options...]

