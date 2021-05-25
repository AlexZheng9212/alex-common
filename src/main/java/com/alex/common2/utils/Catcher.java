package com.alex.common2.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Catcher {
  private final static Logger LOGGER = LoggerFactory.getLogger(Catcher.class);

  private Catcher() {
  }

  public interface ThrowableRunner {
    void run() throws Throwable;
  }

  public interface ThrowableSupplier<T> {
    T get() throws Throwable;
  }

  public static void safeCall(ThrowableRunner caller, boolean logError) {
    try {
      caller.run();
    } catch (Throwable e) {
      if (logError) {
        LOGGER.error(e.getMessage(), e);
      } else {
        LOGGER.warn(e.getMessage(), e);
      }
    }
  }

  public static void safeCall(ThrowableRunner caller) {
    safeCall(caller, false);
  }
}
