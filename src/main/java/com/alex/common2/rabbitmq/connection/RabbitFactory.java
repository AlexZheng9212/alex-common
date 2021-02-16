package com.alex.common2.rabbitmq.connection;

import java.util.concurrent.ExecutorService;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitFactory {
  public static Connection createRabbitConnection(RabbitConnectionProperties rabbitConnectionProperties,
      ExecutorService executorService) {
    return createRabbitConnection(rabbitConnectionProperties, executorService, 10);
  }

  public static Connection createRabbitConnection(RabbitConnectionProperties rabbitConnectionProperties,
      ExecutorService executorService, int maxRetryCount) {
    final String FAILED_TO_CONNECT = "Failed to connect";
    int count = 0;
    while (count < maxRetryCount) {
      try {
        count++;
        ConnectionFactory factory = buildConnectionFactory(rabbitConnectionProperties, executorService);
        return factory.newConnection();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    throw new RuntimeException(FAILED_TO_CONNECT);
  }

  public static ConnectionFactory buildConnectionFactory(RabbitConnectionProperties rabbitConnectionProperties,
      ExecutorService executorService) {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(rabbitConnectionProperties.getAddresses().get(0));
    factory.setPort(rabbitConnectionProperties.getPort());
    factory.setUsername(rabbitConnectionProperties.getUsername());
    factory.setPassword(rabbitConnectionProperties.getPassword());
    factory.setVirtualHost(rabbitConnectionProperties.getVirtualhost());
    factory.setAutomaticRecoveryEnabled(true);
    factory.setTopologyRecoveryEnabled(true);
    factory.setRequestedHeartbeat(rabbitConnectionProperties.getHeartbeat());
    factory.setSharedExecutor(executorService);
    return factory;
  }
}