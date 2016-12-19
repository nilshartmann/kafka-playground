package nh.playground.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyConsumer {

  private final static Logger LOGGER = LoggerFactory.getLogger(MyConsumer.class);

  public static void main(String[] args) {
    final int numConsumers = (args.length > 0 ? Integer.parseInt(args[0]) : 2);
    LOGGER.info("Running consumers: " + numConsumers);
    final ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

    final List<ConsumerLoop> consumers = new ArrayList<>();
    for (int i = 0; i < numConsumers; i++) {
      ConsumerLoop consumer = new ConsumerLoop();
      consumers.add(consumer);
      executor.submit(consumer);
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        for (ConsumerLoop consumer : consumers) {
          consumer.shutdown();
        }
        executor.shutdown();
        try {
          executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
