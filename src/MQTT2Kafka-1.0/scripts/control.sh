#!/bin/bash

export MQTT2KAFKA_BRIDGE_HOME=/opt/cloudera/iot/
export MQTT2KAFKA_BRIDGE_REPO=https://github.com/kamir/mqttKafkaBridge


#
# Some default-settings (maybe they work not in any case)
#

export CONTEXT_FILE=$1






#
# Here we use a CLI parameter to define a location of the modelfile and the port.
#

######################################
#
#  Apache Camel based Bridge-Service
#

case $CMD in
  (start)
    clear
    echo "$(pwd)/control.sh"
    echo " "
    echo "      MQTT2KAFKA_BRIDGE_HOME: $MQTT2KAFKA_BRIDGE_HOME"
    echo "             MQTT2KAFKA_BRIDGE_REPO: $MQTT2KAFKA_BRIDGE_REPO"
    echo "       CONTEXT_FILE: $CONTEXT_FILE"
    echo " "
    echo ">>> Starting the Bridge-Server."
    exec java -cp MQTT2KAFKA_BRIDGE_HOME/mqttKafkaBridge/target/mqtt2kafkaBridge-0.3.0-jar-with-dependencies.jar com.cloudera.iot.bridge.camel.CamelConsoleMain $CONTEXT_FILE &
    echo ">>> Done."

    ;;
  (bootstrap)
    clear
    echo "$(pwd)/control.sh"
    echo " "
    echo "      MQTT2KAFKA_BRIDGE_HOME: $MQTT2KAFKA_BRIDGE_HOME"
    echo "             MQTT2KAFKA_BRIDGE_REPO: $MQTT2KAFKA_BRIDGE_REPO"
    echo "       CONTEXT_FILE: $CONTEXT_FILE"
    echo " "
    echo ">>> Starting bootstrapper ..."
	mkdir $MQTT2KAFKA_BRIDGE_HOME
	cd $MQTT2KAFKA_BRIDGE_HOME
    git clone $MQTT2KAFKA_BRIDGE_REPO
    cd mqttKafkaBridge
    mvn clean compile assembly:single
    ;;
    echo ">>> Done."

  (*)
    echo "Don't understand [$CMD]"
    ;;
esac
