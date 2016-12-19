#! /bin/bash
base_dir=$PWD/$(dirname $0)
export KAFKA_LOG4J_OPTS="-Dlog4j.configuration=file:$base_dir/config/kafka-server-log4j.properties"
export EXTRA_ARGS="-Dkafka.logs.dir=$base_dir/working/kafka-server-1-log4j"
# $base_dir/config/kafka-server-1.properties


${base_dir}/../kafka/bin/kafka-server-start.sh $base_dir/config/kafka-server-1.properties --override log.dirs=$base_dir/working/kafka-server-1-logs
