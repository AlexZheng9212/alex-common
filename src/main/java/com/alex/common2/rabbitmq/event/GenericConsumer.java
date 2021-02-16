package com.alex.common2.rabbitmq.event;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.alex.common2.lang.Codecs;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.alex.common2.lang.SafeRunnable.safeRun;

public class GenericConsumer<T> implements Runnable, AutoCloseable {
  private final static Logger LOGGER = LoggerFactory.getLogger(GenericConsumer.class);
  private volatile boolean running = false;
  private Channel channel = null;
  private String queueName = null;
  private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
  private MessageHandler<? super T> messageHandler;
  private ContextProvider contextProvider;
  private Function<byte[], ? extends T> eventDeserializer;

  public static <T> Function<byte[], T> jsonDes(Class<T> clazz) {
    return jsonBytes -> Codecs.jsonToObj(jsonBytes, clazz);
  }

  public GenericConsumer(Channel channel, String queueName, Function<byte[], ? extends T> eventDeserializer,
      MessageHandler<? super T> messageHandler, ContextProvider contextProvider) {
    this.channel = channel;
    this.queueName = queueName;
    this.eventDeserializer = eventDeserializer;
    this.messageHandler = messageHandler;
    this.contextProvider = contextProvider;
  }

  public synchronized void run() {
    try {
      if (running)
        return;
      this.channel.basicConsume(queueName, new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
            throws IOException {
          try {
            contextProvider.initContext(properties.getHeaders());
            T eventMsg = eventDeserializer.apply(body);
            LOGGER.info("Event received: {} with consumerTag={}, envelope={}, properties={}, body={}", queueName,
                consumerTag, envelope, properties, eventMsg.toString());
            messageHandler.handleMessage(envelope.getRoutingKey(), eventMsg);
            ack(envelope.getDeliveryTag());
          } catch (Exception e) {
            LOGGER.error(e.toString());
            reject(consumerTag, envelope, properties, body);
          }
        }
      });
      running = true;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void reject(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) {
    LOGGER.warn("msg is rejected, consumerTag:{} properties:{} body:{}", consumerTag, properties, new String(body));
    scheduledExecutorService.schedule(safeRun(() -> rejectAck(envelope.getDeliveryTag())), 4, TimeUnit.SECONDS);
  }

  private void rejectAck(long tag) {
    try {
      this.channel.basicReject(tag, false);
    } catch (Exception e) {
      LOGGER.error(e.toString());
    }
  }

  private void ack(long tag) {
    try {
      this.channel.basicAck(tag, false);
    } catch (Exception e) {
      LOGGER.error(e.toString());
    }
  }

  @Override
  public void close() throws Exception {
    this.channel.close();
    this.scheduledExecutorService.shutdown();
  }
}