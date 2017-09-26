# PREP
https://stackoverflow.com/questions/30606447/kafka-consumer-fetching-metadata-for-topics-failed

# Test Topics
java -cp target/mqttKafkaBridge-0.2.0-jar-with-dependencies.jar com.cloudera.iot.mqtt.Kafka2MqttConnector 127.0.0.1:2181 1 sunfounder_demo 1
 
# mqttKafkaBridge

Bridge which consumes MQTT messages and republishes them on Kafka on the same topic.

## Usage

    $ java -jar mqttKafkaBridge.jar [options...]

Where `options` are:

    --help (-h)  : Show help
    --id VAL     : MQTT Client ID
    --topics VAL : MQTT topic filters (comma-separated)
    --uri VAL    : MQTT Server URI
    --zk VAL     : Zookeeper connect string

If you don't specify any command-line options, it uses the following defaults:

    id:      mqttKafkaBridge
    topics:  '#' (all topics)
    uri:     tcp://localhost:1883
    zk:      localhost:2181

***Note***: you can't run more than one bridge using the default settings, since two clients cannot connect to the same MQTT server with the same client ID. Additionally, you will get multiple messages published to Kafka for each message published to MQTT. If you wish to run multiple instances, you'll need to divide up the topics among the instances, and make sure to give them different IDs.

## Logging
`mqttKafkaBridge` uses [log4j](http://logging.apache.org/log4j/2.x/) for logging, as do the [Paho](http://www.eclipse.org/paho/) and [Kafka](http://kafka.apache.org/) libraries it uses. There is a default `log4j.properties` file packaged with the jar which simply prints all messages of level `INFO` or greater to the console. If you want to customize logging, simply create your own `log4j.properties` file, and start up `mqttKafkaBridge` as follows:

    $ java -Dlog4j.configuration=file:///path/to/log4j.properties -jar mqttKafkaBridge.jar [options...]

