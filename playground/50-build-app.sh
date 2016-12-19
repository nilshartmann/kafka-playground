#! /bin/bash
base_dir=$PWD/$(dirname $0)

cd $base_dir/app && ./gradlew clean build
