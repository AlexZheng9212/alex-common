package com.alex.common2.cache.base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.JsonSerializable.Base;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public abstract class BaseCache<K, V> {
  private LoadingCache<K, V> cache;
  ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));
  ListeningExecutorService executorService = MoreExecutors.listeningDecorator(executor);

  public BaseCache() {
    cache = CacheBuilder.newBuilder().refreshAfterWrite(1, TimeUnit.HOURS).concurrencyLevel(5).initialCapacity(10)
        .maximumSize(200).build(new CacheLoader<K, V>() {
          @Override
          public V load(K k) throws Exception {
            return loadData(k);
          }

          @Override
          public ListenableFuture<V> reload(K k, V oldValue) throws Exception {
            return executorService.submit(new Callable() {
              @Override
              public Object call() throws Exception {
                return loadData(k);
              }
            });
          }
        });
  }

  protected abstract V loadData(K k);

  protected abstract void refresh();

  protected abstract V loadAllData();

  public V getCache(K k) {
    return cache.getUnchecked(k);
  }

  public void refresh(K k) {
    cache.refresh(k);
  }

  public void invalidAll() {
    cache.invalidateAll();
  }

  public Map<K, V> getAll() {
    return cache.asMap();
  }
}
