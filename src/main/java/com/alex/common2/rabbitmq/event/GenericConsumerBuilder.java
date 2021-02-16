package com.alex.common2.rabbitmq.event;

import java.util.function.Function;

import com.rabbitmq.client.Channel;

import lombok.Data;

import static com.alex.common2.lang.Codecs.jsonToObj;

@Data
public class GenericConsumerBuilder<T> {
  private Channel channel;
  private String queueName;
  private Function<byte[], ? extends T> eventDeserializer;
  private MessageHandler<? super T> messageHandler = (a, b) -> {
  };
  private ContextProvider contextProvider = new EmptyContextProviderImpl();

  public GenericConsumerBuilder(Class<T> eventType, Channel channel, String queueName) {
    this.channel = channel;
    this.queueName = queueName;
    this.eventDeserializer = jsonBytes -> jsonToObj(jsonBytes, eventType);
  }

  public GenericConsumer<T> createGenericConsumer() {
    return new GenericConsumer<>(channel, queueName, eventDeserializer, messageHandler, contextProvider);
  }
}