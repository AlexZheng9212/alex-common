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
    CustomizeHashMapCache<String, String> chmap = new CustomizeHashMapCache<String, String>(1024l, RefreshPolicy.NONE);
    chmap.putIfAvailable(key, value);
    assertEquals(value, chmap.get(key));
  }
}
