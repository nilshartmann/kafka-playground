#! /bin/bash
base_dir=$PWD/$(dirname $0)

java -cp $base_dir/app/build/libs/playground-1.0-SNAPSHOT.jar nh.playground.kafka.producer.MyProducer $base_dir/working/app
