package com.alex.common2.threadpoolexcutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class CommonThreadPool {
  private String ERR_THREAD_IS_EXISTED = "ThreadName is existed";
  private String ERR_THREAD_IS_NOT_EXISTED = "ThreadName is not existed";
  Map<String, ThreadPoolExecutor> threadPoolMap = new HashMap<>();

  public ThreadPoolExecutor genCommonExecutor(String treadName, int corePoolSize, int maxPoolSize, int capacity) {
    ResizableBlockingQueue workQueue = new ResizableBlockingQueue<>(capacity);
    ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 1, TimeUnit.MILLISECONDS, workQueue,
        new ThreadFactory() {
          public Thread newThread(Runnable r) {
            return new Thread(r + "-" + treadName + "-" + r.hashCode());
          }
        });
    if (threadPoolMap.containsKey(treadName)) {
      throw new IllegalArgumentException(ERR_THREAD_IS_EXISTED);
    }
    threadPoolMap.put(treadName, executor);
    return executor;
  }

  public ThreadPoolExecutor findThreadExecutorByName(String name) throws Exception {
    if (!threadPoolMap.containsKey(name)) {
      throw new Exception(ERR_THREAD_IS_NOT_EXISTED);
    }
    return threadPoolMap.get(name);
  }

  public void resizeThreadExecutor(String name) throws Exception {
    if (!threadPoolMap.containsKey(name)) {
      throw new Exception(ERR_THREAD_IS_NOT_EXISTED);
    }
    ThreadPoolExecutor executor = threadPoolMap.get(name);
    int maxPoolSize = executor.getMaximumPoolSize();
    if (maxPoolSize >= 10) {
      return;
    }
    executor.setMaximumPoolSize(10);
    ResizableBlockingQueue queue = (ResizableBlockingQueue) executor.getQueue();
    if (queue.size() >= 100) {
      return;
    }
    queue.setCapacity(100);
  }
}
