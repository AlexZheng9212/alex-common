package com.alex.common2.lang;

public final class SafeRunnable implements Runnable {

  private GenericFunction impl;

  public SafeRunnable(GenericFunction impl) {
    this.impl = impl;
  }

  @Override
  public void run() {
    try {
      impl.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static Runnable safeRun(GenericFunction impl) {
    return new SafeRunnable(impl);
  }
}