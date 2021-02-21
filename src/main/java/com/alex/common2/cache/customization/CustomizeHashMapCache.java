package com.alex.common2.cache.customization;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

import com.alex.common2.cache.RefreshPolicy;
import com.alex.common2.utils.ObjectManagementUtils;

import lombok.Builder;

@Builder
public class CustomizeHashMapCache<K, V> extends HashMap<K, V> implements CustomizeMap<K, V> {
  private RefreshPolicy policy;
  private Long cacheSize;
  private Function<K, V> func;

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

  @Override
  public V getValSafely(K key) throws Exception {
    if (func == null) {
      throw new Exception("func is null, denied");
    }
    V val = super.get(key);
    if (Objects.nonNull(val)) {
      return val;
    }
    super.put(key, func.apply(key));
    return super.get(key);
  }
}
