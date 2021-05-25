package com.alex.common2.async;

import java.util.concurrent.Executor;

import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ComAsyncConfig implements AsyncConfigurer {
  private final int queueSize = 1000;
  private final String prefix = "ComAsync-";

  @Override
  public Executor getAsyncExecutor() {
    int cpuCount = Runtime.getRuntime().availableProcessors();
    int threadCnt = cpuCount * 2;
    ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
    threadPool.setCorePoolSize(threadCnt);
    threadPool.setMaxPoolSize(threadCnt);
    threadPool.setQueueCapacity(queueSize);
    threadPool.setAwaitTerminationSeconds(60 * 15);
    threadPool.setWaitForTasksToCompleteOnShutdown(true);
    threadPool.setThreadNamePrefix(prefix);
    threadPool.initialize();
    return threadPool;
  }
}
