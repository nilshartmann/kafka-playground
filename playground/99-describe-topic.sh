#! /bin/bash
base_dir=$PWD/$(dirname $0)

export KAFKA_LOG4J_OPTS="-Dlog4j.configuration=file:$base_dir/config/kafka-topics-log4j.properties"
export EXTRA_ARGS="-Dkafka.logs.dir=$base_dir/working/kafka-topics-log4j"

$base_dir/../kafka/bin/kafka-topics.sh --zookeeper localhost:2181/KAFKA-ZK --describe --topic events
