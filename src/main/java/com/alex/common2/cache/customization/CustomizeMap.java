package com.alex.common2.cache.customization;

public interface CustomizeMap<K, V> {
  public V putIfAvailable(K key, V value);

  public V getValSafely(K key) throws Exception;
}
