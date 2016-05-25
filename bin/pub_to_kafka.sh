echo "This is my first message!" | kafka-console-producer.sh \
                  --broker-list localhost:9092 \
                  --topic T2 \
                  --new-producer


Currently, the Bridge looks like a one way bridge ...



/usr/bin/kafka-console-producer --broker-list 127.0.0.1:9092 --topic t2
