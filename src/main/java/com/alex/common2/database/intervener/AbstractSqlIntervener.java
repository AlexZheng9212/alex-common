package com.alex.common2.database.intervener;

import java.util.List;

public abstract class AbstractSqlIntervener implements SqlReplacer {
  public abstract void genAndReplaceSql();

  boolean matchCondition(List<String> conditions, String condition) {
    return conditions.contains(condition);
  }
}

interface SqlReplacer {
  void doReplace(Object inputParam);
}