#! /bin/bash

base_dir=$PWD/$(dirname $0)

echo base_dir: $base_dir

export KAFKA_LOG4J_OPTS="-Dlog4j.configuration=file:$base_dir/config/zookeeper-log4j.properties"
export EXTRA_ARGS="-Dzookeeper.log.dir=${base_dir}/working/zookeeper-log4j"

echo LOG4j: $KAFKA_LOG4J_OPTS
echo EXTRA_ARGS: $EXTRA_ARGS

${base_dir}/../kafka/bin/zookeeper-server-start.sh ${base_dir}/config/zookeeper.properties

