package nh.playground.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ConsumerLoop implements Runnable {
  private final Logger LOGGER = LoggerFactory.getLogger(getClass());
  private final KafkaConsumer<String, String> consumer;

  public ConsumerLoop() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9992");
    props.put("group.id", "group-one");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put("enable.auto.commit", "false");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

    //
    LOGGER.info("Create Consumer");
    consumer = new KafkaConsumer<>(props);
  }

  @Override
  public void run() {
    try {
      LOGGER.info("Subscribe to 'events'");
      consumer.subscribe(Arrays.asList("events"));
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(100);
        for (ConsumerRecord<String, String> record : records)
          LOGGER.info("partition = {}, offset = {}, key = {}, value = {}", record.partition(), record.offset(), record.key(), record.value());
      }
    } catch (WakeupException e) {
      // ignore for shutdown
    } finally {
      consumer.close();
    }
  }

  public void shutdown() {
    consumer.wakeup();
  }

}
