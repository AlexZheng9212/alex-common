package com.alex.common2;

import static org.junit.Assert.assertEquals;

import com.alex.common2.cache.RefreshPolicy;
import com.alex.common2.cache.customization.CustomizeHashMapCache;

import org.junit.Test;

public class CustomizeHashMapCacheTest {
  @Test
  public void happyPass() throws Exception {
    String key = "key";
    String value = "val";
    String newKey = "new Key";
    CustomizeHashMapCache<String, String> chmap = CustomizeHashMapCache.<String, String>builder().cacheSize(1024l)
        .func(s -> mockFunc(s)).policy(RefreshPolicy.NONE).build();
    chmap.putIfAvailable(key, value);
    assertEquals(value, chmap.get(key));
    assertEquals(newKey, chmap.getValSafely(newKey));
  }

  private String mockFunc(String s) {
    return s;
  }
}
