package com.alex.common2.rabbitmq.utils;

import java.io.IOException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class RabbitUtils {
  public static void deployChannel(Channel channel, String queueName, String exchange) throws IOException {
    channel.queueDeclare(queueName, false, false, false, null);
    channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT, true, false, false, null);
    channel.queueBind(queueName, exchange, "");
  }
}