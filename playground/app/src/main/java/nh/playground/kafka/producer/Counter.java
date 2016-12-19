package nh.playground.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Very simple and trivial persistent counter. Used to generate unique, but "readable" ids
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class Counter {

  private final static Logger LOGGER = LoggerFactory.getLogger(Counter.class);

  private File counterFile;
  private int currentValue;

  public Counter(File counterFile, int counter) {
    this.counterFile = counterFile;
    this.currentValue = counter;
  }

  public int value() {
    return this.currentValue;
  }

  public static Counter open(String workingDirName) {
    if (workingDirName == null) {
      workingDirName = System.getProperty("user.dir");
    }

    final File workingDir = new File(workingDirName);

    if (!workingDir.exists()) {
      workingDir.mkdirs();
    }
    final File counterFile = new File(workingDir, "counter.txt");
    LOGGER.info("Using counter file {} ", counterFile);
    int counter = 0;

    if (counterFile.exists()) {
      LOGGER.info("Reading counter file {} ", counterFile);
      try (BufferedReader reader = new BufferedReader(new FileReader(counterFile))) {
        String line = reader.readLine();
        counter = Integer.parseInt(line.trim());
      } catch (Exception e) {
        throw new RuntimeException("Could not read counter file " + counterFile + ": " + e, e);
      }
    }

    return new Counter(counterFile, counter);
  }

  public int increment() {
    currentValue++;
    save();
    return this.currentValue;
  }

  private void save() {
    try {
      try (PrintWriter writer = new PrintWriter(new FileWriter(counterFile))) {
        writer.write(Integer.toString(currentValue));
      }
    } catch (IOException e) {
      throw new RuntimeException("Could not write counter file: " + e, e);
    }
  }
}
