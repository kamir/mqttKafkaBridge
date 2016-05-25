xterm -e bash -c "mosquitto -d -p 1883 -v;exec bash" &

xterm -e bash -c "mosquitto_sub -t T1;exec bash" &

xterm -e bash -c "./run.sh;exec bash" &

xterm -e bash -c "echo 'Ready to send messages ... (mosquitto_pub -t T1 -m ABCDEFG)';exec bash" & 
