package com.alex.common2.rabbitmq.event;

public interface MessageHandler<T> {
  void handleMessage(String routing, T message);
}