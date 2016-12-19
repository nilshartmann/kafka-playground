#! /bin/bash
base_dir=$PWD/$(dirname $0)

# How many consumers should work in parallel
CONSUMERS=2

java -cp $base_dir/app/build/libs/playground-1.0-SNAPSHOT.jar nh.playground.kafka.consumer.MyConsumer $CONSUMERS
