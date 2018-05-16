package de.fhdortmund.service.service.MqttServiceImpl;

import static de.fhdortmund.service.constant.MQTTConstants.BASE_TOPIC;
import static de.fhdortmund.service.constant.MQTTConstants.CLIENT_NAME;
import static de.fhdortmund.service.constant.MQTTConstants.PASSWORD;
import static de.fhdortmund.service.constant.MQTTConstants.REGISTER_TOPIC;
import static de.fhdortmund.service.constant.MQTTConstants.URL;
import static de.fhdortmund.service.constant.MQTTConstants.USERNAME;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhdortmund.service.consumer.AccessControlFeignClient;
import de.fhdortmund.service.entity.Command;
import de.fhdortmund.service.service.MqttService;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttServiceImpl implements MqttService {

  @Autowired
  private AccessControlFeignClient accessControlFeignClient;

  private MqttClient mqttClient;
  private MqttConnectOptions mqttConnectOptions;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public void connectToBroker() {
    try {
      mqttClient.connect(mqttConnectOptions);
      mqttClient.subscribe(REGISTER_TOPIC);
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sendAccessControlCommand(String instanceName, String itemName, String commandValue,
      String commandType) {
    log.info("Access: " + accessControlFeignClient.checkWriteAccess(instanceName, instanceName));
    if (!accessControlFeignClient.checkWriteAccess(instanceName, itemName)) {
      log.info("User has no Access Right.");
      return;
    }
    if (!mqttClient.isConnected()) {
      connectToBroker();
      log.info("MQTT Broker Connected: " + mqttClient.isConnected());
    }
    ObjectMapper mapper = new ObjectMapper();
    MqttMessage message = new MqttMessage();
    Command command = new Command();
    command.setCommand(commandValue);
    command.setItemname(itemName);
    command.setCommandtype(commandType);
    try {
      message.setPayload(mapper.writeValueAsBytes(command));
      log.info(String.format("%s/%s/command", BASE_TOPIC, instanceName), message);
      mqttClient.publish(String.format("%s/%s/command", BASE_TOPIC, instanceName), message);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (MqttException e) {
      e.printStackTrace();
    }
    log.info("Command send: " + command.toString());
  }

  @PostConstruct
  public void init() {
    SSLContext sslContext = null;
    try {
      sslContext = SSLContext.getInstance("SSL");
      TrustManagerFactory trustManagerFactory = TrustManagerFactory
          .getInstance(TrustManagerFactory.getDefaultAlgorithm());
      KeyStore keyStore = KeyStore.getInstance("JKS");
      keyStore.load(new FileInputStream("client.jks"),
          "password".toCharArray());
      trustManagerFactory.init(keyStore);
      sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (KeyStoreException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (CertificateException e) {
      e.printStackTrace();
    } catch (KeyManagementException e) {
      e.printStackTrace();
    }

    if (sslContext != null) {
      mqttConnectOptions = new MqttConnectOptions();
      mqttConnectOptions.setSocketFactory(sslContext.getSocketFactory());
      try {
        mqttClient = new MqttClient(URL, CLIENT_NAME);
      } catch (MqttException e) {
        e.printStackTrace();
      }
      mqttConnectOptions.setUserName(USERNAME);
      mqttConnectOptions.setPassword(PASSWORD.toCharArray());
    }
  }
}
