package com.alex.common2.cache;

import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum RefreshPolicy {

  NONE("NONE", (x) -> executeNone(x)), FIFO("FIFO", (x) -> executeFIFO(x));

  private String name;
  private Consumer<Map> func;

  RefreshPolicy(String name, Consumer<Map> func) {
    this.name = name;
    this.func = func;
  }

  private final static Logger LOGGER = LoggerFactory.getLogger(RefreshPolicy.class);

  private static void executeNone(Map map) {
    LOGGER.info("Map is full, size: {}, abort put new value", map.size());
  };

  private static void executeFIFO(Map map) {
    map.remove(map.keySet().toArray()[0]);
  }

  public String getName() {
    return this.name;
  }

  public Consumer<Map> getFunc() {
    return this.func;
  }
}
