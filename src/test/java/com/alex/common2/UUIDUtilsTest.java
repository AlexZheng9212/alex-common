package com.alex.common2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.alex.common2.utils.UUIDUtils;

import org.junit.Test;

public class UUIDUtilsTest {
  @Test
  public void testUUID() {
    String testStr = "fa95c426-adf6-4823-b0be-4e5e12c87439";
    String errorStr = "abcde1234";
    assertEquals(testStr, UUIDUtils.convertToUUID(testStr).toString());
    try {
      assertEquals(errorStr, UUIDUtils.convertToUUID(errorStr).toString());
    } catch (Exception e) {
      assertTrue("Invalid UUID string: abcde1234".equals(e.getMessage()));
    }
  }
}
