package com.alex.common2.utils;

import java.util.List;

import lombok.Builder;

@Builder
public class BasicPageResult<T> {
  private List<T> data;
  private Integer total;
  private Integer current;
  private Integer pageSize;
}