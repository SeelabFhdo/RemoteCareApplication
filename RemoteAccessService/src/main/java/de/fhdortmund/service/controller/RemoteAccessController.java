package de.fhdortmund.service.controller;

import com.google.gson.Gson;
import de.fhdortmund.service.service.MqttService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/resources/remoteaccess", produces = "application/json")
public class RemoteAccessController {

  private MqttService mqttService;

  @Autowired
  public RemoteAccessController(MqttService mqttService) {
    this.mqttService = mqttService;
  }

  @PostMapping
  @ResponseBody
  public String executeCommand(Principal principal,
      @RequestParam String instanceName,
      @RequestParam String itemName,
      @RequestParam String command,
      @RequestParam String commandType) {
      String response = "InstanceName: " + instanceName + " | Itemname: " + itemName + " | Command: " + command;
      mqttService.sendAccessControlCommand(instanceName, itemName, command, commandType);

      Gson gson = new Gson();
    return gson.toJson(response);
  }

}
