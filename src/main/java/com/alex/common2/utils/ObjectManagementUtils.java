package com.alex.common2.utils;

import org.apache.lucene.util.RamUsageEstimator;

public class ObjectManagementUtils {
  /**
   * 
   * @param obj
   * @return size of obj (k)
   */
  public static double figureObjectSizeInHeap(Object... obj) {
    Long size = RamUsageEstimator.shallowSizeOf(obj);
    return size.doubleValue() / Double.valueOf(1024d);
  }

  /**
   * 
   * @param limitSize pre k
   * @param obj
   * @return boolean
   */
  public static Boolean isOutOfLimitSize(Long limitSize, Object... obj) {
    double size = figureObjectSizeInHeap(obj);
    if (limitSize.doubleValue() < size) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }
}
