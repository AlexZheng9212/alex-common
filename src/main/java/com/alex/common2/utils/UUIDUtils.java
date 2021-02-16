package com.alex.common2.utils;

import java.util.UUID;

public class UUIDUtils {
  public static UUID convertToUUID(String str) {
    if (str == "" || str == null)
      return null;
    try {
      return UUID.fromString(str);
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }
}