package com.alex.common2.rabbitmq.event;

import java.util.Collections;
import java.util.Map;

interface ContextProvider {
  Map<String, Object> getContext();

  void resetContext();

  void initContext(Map<String, Object> ctx);
}

public class EmptyContextProviderImpl implements ContextProvider {
  @Override
  public Map<String, Object> getContext() {
    return Collections.emptyMap();
  }

  @Override
  public void resetContext() {
  }

  @Override
  public void initContext(Map<String, Object> ctx) {
  }
}