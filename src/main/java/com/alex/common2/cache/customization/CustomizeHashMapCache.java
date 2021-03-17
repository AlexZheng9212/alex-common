package com.alex.common2.cache.customization;

import java.util.HashMap;

import com.alex.common2.cache.RefreshPolicy;
import com.alex.common2.utils.ObjectManagementUtils;

public class CustomizeHashMapCache<K, V> extends HashMap<K, V> implements CustomizeMap<K, V> {
  private RefreshPolicy policy;
  private Long cacheSize;

  public <K, V> CustomizeHashMapCache(Long cacheSize, RefreshPolicy policy) {
    this.cacheSize = cacheSize;
    this.policy = policy;
  }

  @Override
  public V putIfAvailable(K key, V value) {
    if (!ObjectManagementUtils.isOutOfLimitSize(cacheSize, this)) {
      return super.put(key, value);
    }
    policy.getFunc().accept(this);
    if (RefreshPolicy.NONE.getName().equals(policy.getName())) {
      return null;
    }
    return super.put(key, value);
  }
}
