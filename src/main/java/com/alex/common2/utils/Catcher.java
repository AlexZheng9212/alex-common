package com.alex.common2.utils;

public abstract class Catcher {

  private Catcher() {
  }

  public interface ThrowableRunner {
    void run() throws Throwable;
  }

  public interface ThrowableSupplier<T> {
    T get() throws Throwable;
  }

  public static <T> T unSafeCall(ThrowableSupplier<T> supplier) {
    try {
      return supplier.get();
    } catch (Throwable e) {
      if (e instanceof RuntimeException) {
        throw (RuntimeException) e;
      } else {
        throw new RuntimeException(e);
      }
    }
  }

  public static void safeCall(ThrowableRunner caller) {
    try {
      caller.run();
    } catch (Throwable e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
