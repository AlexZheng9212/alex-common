package com.alex.common2.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.alex.common2.utils.Catcher;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class AsyncInvoker {
  @Async
  public <T> Future<T> invoke(Callable<T> callable) {
    return Catcher.unSafeCall(() -> new AsyncResult<>(callable.call()));
  }

  @Async
  public Future<Boolean> invoke(Runnable runnable) {
    return Catcher.unSafeCall(() -> {
      runnable.run();
      return new AsyncResult<>(true);
    });
  }
}
