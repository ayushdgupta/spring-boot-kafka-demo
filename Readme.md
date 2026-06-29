### Kafka Server in CMD
1. First go to /bin/windows (for windows) folder inside the kafka where it downloaded and extracted.
2. Open cmd in that folder and execute below commands.
3. To create a kafka server first we need to generate a random UUID which will be specific to our kafka cluster
```yaml
kafka-storage.bat random-uuid
```
4. We need to format the memory where all the metadata of kafka cluster will be stored because earlier Zookeeper used to keep those details but now it's completely out from kafka and Kraft is used so we need to format the memory.
```yaml
kafka-storage.bat format --standalone -t UUID_GENERATED_FROM_ABOVE_CMD -c C:\kafka_2.13-4.3.0\config\server.properties
```
5. To start kafka server run following command
```yaml
kafka-server-start.bat C:\kafka_2.13-4.3.0\config\server.properties
```
6. if kafka cluster is already created and closed and then again we want to start the same kafka-cluster then execute last command only.
7. in server.properties there is a property ''log.dir'' which keep the default path where all the logs related to kafka cluster will be generated so if we want to change that location that also we can do before running any of the above command.
8. To create a kafka topic
```yaml
kafka-topics.bat --bootstrap-server localhost:9092 --create --topic guptaji-topic --partitions 4 --replication-factor 1
```
    1. localhost:9092 -- bootstrap server or kafka server details (9092 default port where kafka-cluster starts)
    2. guptaji-topic -- Topic name
    3. partitions -- 4 (no. of partitions in topic)
    4. replication-factor -- 1 (no. of copies of kafka cluster)
9. To start the producer
```yaml
kafka-console-producer.bat --bootstrap-server localhost:9092 --topic guptaji-topic
```
10. to start the consumer
```yaml
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic guptaji-topic --from-beginning
```