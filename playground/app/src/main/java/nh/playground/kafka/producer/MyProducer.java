package nh.playground.kafka.producer;

import java.io.*;
import java.util.Date;
import java.util.Properties;

import nh.playground.kafka.MyEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyProducer {

  private final static int MAX_PARTITIONS = 3;
  private final static Logger LOGGER = LoggerFactory.getLogger("PRODUCER");

  public static void main(String[] args) {

    final Counter counter = Counter.open(args.length>0 ? args[0] : null);

    LOGGER.info("Starting with counter at {}", counter.value());


    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9991");
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "nh.playground.kafka.JSonSerializer");


    int partition = 0;

    // produce events for all topics
    try (Producer<String, Object> producer = new KafkaProducer<>(props)) {
      while (true) {
        final int eventId = counter.increment();
        LOGGER.info("Writing Event {} to Partition {}", eventId, partition);
        producer.send(new ProducerRecord<String, Object>("events", partition, Integer.toString(eventId), new MyEvent(new Date() + " - " + eventId, "Event-" + eventId)));

        s(1000);
        partition = partition >= MAX_PARTITIONS - 1 ? 0 : partition+1;
      }
    }
  }

  private static void s(long d) {
    try {
      Thread.sleep(d);
    } catch (Exception e) {
      // egal
    }
  }


}
