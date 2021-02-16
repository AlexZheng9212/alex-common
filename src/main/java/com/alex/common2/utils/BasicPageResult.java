package com.alex.common2.utils;

import java.util.List;

public class BasicPageResult<T> {
  public List<T> data;
  public Integer total;

  public BasicPageResult(List<T> data) {
    this.data = data;
    this.total = data.size();
  }

}