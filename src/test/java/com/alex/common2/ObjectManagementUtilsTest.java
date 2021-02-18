package com.alex.common2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.alex.common2.utils.ObjectManagementUtils;

import org.junit.Test;

public class ObjectManagementUtilsTest {

  @Test
  public void figureObjectSizeInHeap() {
    Integer a = Integer.valueOf(123);
    Integer b = Integer.valueOf(123321);
    Integer c = Integer.valueOf(123123321);
    double sizeA = ObjectManagementUtils.figureObjectSizeInHeap(a);
    double sizeB = ObjectManagementUtils.figureObjectSizeInHeap(b);
    double sizeC = ObjectManagementUtils.figureObjectSizeInHeap(c);
    double sizeD = ObjectManagementUtils.figureObjectSizeInHeap(a, c, b);

    assertEquals(0.023d, sizeA, 0.001d);
    assertEquals(0.023d, sizeB, 0.001d);
    assertEquals(0.023d, sizeC, 0.001d);
    assertEquals(0.031d, sizeD, 0.001d);

  }

  @Test
  public void isOutOfLimitSize() {
    Integer c = Integer.valueOf(123123321);
    assertEquals(Boolean.FALSE, ObjectManagementUtils.isOutOfLimitSize(1l, c));
    assertNotEquals(Boolean.TRUE, ObjectManagementUtils.isOutOfLimitSize(1l, c));
  }
}
