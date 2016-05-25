xterm -e mosquitto -d -p 1883 -v

xterm -e mosquitto_sub -t T1

xterm -e ./run.sh

xterm -e echo "Ready to send messages ... (mosquitto_sub -t T1)"
