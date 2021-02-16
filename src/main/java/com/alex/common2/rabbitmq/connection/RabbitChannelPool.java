package com.alex.common2.rabbitmq.connection;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class RabbitChannelPool extends GenericObjectPool<Channel> {

  public RabbitChannelPool(Connection conn) {
    super(new RabbitChannelPoolFactory(conn));
  }
}

class RabbitChannelPoolFactory extends BasePooledObjectFactory<Channel> {
  private Connection conn;

  public RabbitChannelPoolFactory(Connection conn) {
    this.conn = conn;
  }

  @Override
  public DefaultPooledObject<Channel> wrap(Channel obj) {
    return new DefaultPooledObject<>(obj);
  }

  @Override
  public Channel create() throws Exception {
    return conn.createChannel();
  }

  @Override
  public void destroyObject(PooledObject<Channel> p) throws Exception {
    p.getObject().close();
  }
}