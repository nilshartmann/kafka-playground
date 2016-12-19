Kafka Playground
================

Simple example of an [Apache Kafka](https://kafka.apache.org/) setup with multiple brokers, producer and client.

Getting started
---------------
Everything you need to get started is included in this repository. So just clone this
repository:

```git clone https://github.com/nilshartmann/kafka-playground```
 
Then run the commands described below.

Note: all commands must be run from the `playground` subdirectory.

# Starting Zookeeper and Kafka

## 01: Starting Zookeeper
`./01-start-zookeeper.sh` Starts a single Zookeeper instance needed for Kafka.

## 02: Starting two Kafka Brokers
```bash
02-start-kafka-server-1.sh
03-start-kafka-server-2.sh
```

With this commands two brokers are started with the following configuration:
 * They listen on port `9991` and `9992`.
 * They connect to zookeeper with `localhost:2181/KAFKA-ZK`
 * They have auto creation of topics enabled (with a default partition size of 3 and a default replication factor of 2)

# Starting Producer and consumers

The producer and consumer application is written in java in the `app` folder. 

The producer runs in an endless loop and generated messages to the `events` topic to 
all three partitions.

The consumer starts a configured number of working threads in one consumer group.

As we have started two Kafka brokers, even if you stop one broker, both consumer
and producer continue to work (due to fail over to the other, running, broker)

## Step 1: Build the app

`./50-build-app.sh` Runs gradle to compile the Java Sources and build a Jar file.

## Step 2: Start the producer

`51-start-producer.sh` Runs the producer (stop with Ctrl+C) and start producing "events" to the `events` topic.

## Step 3: Start the consumer

`52-start-consumer.sh` Run the consumer in endless loop (stop with Ctrl+C). The number of consumer threads can be set in the shell script. (Use three threads to make each thread consuming from exactly one partition)

(Note that the consumer always starts consuming from the first entry in the `events` topic)

## Inspect the `events` Topic

`99-describe-topic.sh` shows the status of the `events` Topic (replication state etc)

# Configuration and runtime

All configuration files are in `playground/config`. Zookeeper, Kafka and the example
producer and consumers are configured to store all their files (Logging, Logs etc) in
`playground/working`. 
The original Kafka distribution (`kafka` subfolder) is not modified in any way. 
