package de.fhdortmund.service.service;

public interface MqttService {
  void connectToBroker();
  void sendAccessControlCommand(String instanceName, String itemName, String commandValue, String commandType);
}
