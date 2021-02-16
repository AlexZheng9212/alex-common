package com.alex.common2.rabbitmq.connection;

import java.util.List;

import com.rabbitmq.client.Address;

public class RabbitConnectionProperties {

  private List<String> addresses;

  private int port;

  private String username;

  private String password;

  private String virtualhost;

  private int heartbeat = 60;

  private int reTrySecords = 5;

  private int prefetchCount = 100;

  public void setAddresses(List<String> addresses) {
    this.addresses = addresses;
  }

  public List<String> getAddresses() {
    return addresses;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getPort() {
    return port;
  }

  public void setHeartbeat(int heartbeat) {
    this.heartbeat = heartbeat;
  }

  public int getHeartbeat() {
    return heartbeat;
  }

  public void setVirtualhost(String virtualhost) {
    this.virtualhost = virtualhost;
  }

  public String getVirtualhost() {
    return virtualhost;
  }

  public void setReTrySecords(int reTrySecords) {
    this.reTrySecords = reTrySecords;
  }

  public int getReTrySecords() {
    return reTrySecords;
  }

  public void setPrefetchCount(int prefetchCount) {
    this.prefetchCount = prefetchCount;
  }

  public int getPrefetchCount() {
    return prefetchCount;
  }

  public Address[] getHostAddresses() {
    if (addresses != null) {
      Address[] res = new Address[this.addresses.size()];
      for (int i = 0; i < addresses.size(); i++) {
        res[i] = new Address(addresses.get(i), this.port);
      }
      return res;
    }
    return null;
  }

}