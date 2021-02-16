package com.alex.common2.rabbitmq.event;

import com.alex.common2.lang.Codecs;
import com.alex.common2.rabbitmq.connection.RabbitChannelPool;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericPublisher<T> {
  private RabbitChannelPool channelPool;
  private String exchange;
  private boolean verboseLogging;

  private final static Logger LOGGER = LoggerFactory.getLogger(GenericPublisher.class);

  public GenericPublisher(Connection connection, String exchange) {
    this(connection, exchange, true);
    System.out.println("generic: " + connection.toString());
  }

  public GenericPublisher(Connection connection, String exchange, boolean verboseLogging) {
    this.channelPool = new RabbitChannelPool(connection);
    this.exchange = exchange;
    this.verboseLogging = verboseLogging;

  }

  public void publishEvent(String routingKey, T eventObj) {
    Channel channel = null;
    try {
      String caller = Thread.currentThread().getStackTrace()[3].toString();
      channel = channelPool.borrowObject();
      byte[] body = Codecs.objToJsonBytes(eventObj);
      channel.basicPublish(exchange, routingKey, null, body);
      if (verboseLogging) {
        LOGGER.info("Event published by {}, exchange: {}, routingKey: {}, \n body: {}", caller, exchange, routingKey,
            eventObj.toString());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (channel != null)
        channelPool.returnObject(channel);
    }

  }
}