package pflegeplattform.service.controller;

import com.google.gson.Gson;
import java.security.InvalidParameterException;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pflegeplattform.service.data.entities.SmartHomeAccessInstance;
import pflegeplattform.service.service.AccessManagerService;

@RestController
@RequestMapping(value = "/resources/accesscontrol", produces = "application/json")
public class AccessController {

  private AccessManagerService accessManagerService;

  @Autowired
  public AccessController(AccessManagerService accessManagerService) {
    this.accessManagerService = accessManagerService;
  }

  @GetMapping(value = "/smarthomeaccessinstance/{instanceName}/{itemName}/read")
  @ResponseBody
  public String checkReadAccess(Principal principal,
      @PathVariable String instanceName,
      @PathVariable String itemName) {

    Gson gson = new Gson();
    return gson.toJson(
        accessManagerService.hasReadAccessToItem(instanceName, itemName, principal.getName()));
  }

  @GetMapping(value = "/smarthomeaccessinstance/{instanceName}/{itemName}/write")
  @ResponseBody
  public String checkWriteAccess(Principal principal,
      @PathVariable String instanceName,
      @PathVariable String itemName) {

    Gson gson = new Gson();
    return gson.toJson(
        accessManagerService.hasWriteAccessToItem(instanceName, itemName, principal.getName()));
  }

  @PutMapping(value = "/smarthomeaccessinstance")
  @ResponseBody
  public String addSmartHomeAccessInstance(Principal principal,
      @RequestParam String smartHomeAccessInstanceName) {
    if (!accessManagerService.isUserAdmin(principal.getName())) {
      throw new InvalidParameterException("Only Admins are allowed to add new instances.");
    }

    if (accessManagerService.smartHomeAccessInstanceExists(smartHomeAccessInstanceName)) {
      throw new InvalidParameterException("An instance with this name already exists.");
    }

    SmartHomeAccessInstance smartHomeAccessInstance =
        accessManagerService.addSmartHomeAccessInstance(smartHomeAccessInstanceName);
    Gson gson = new Gson();
    return gson.toJson(smartHomeAccessInstance);
  }

  @PostMapping(value = "/smarthomeaccessinstance/{instanceName}/write")
  @ResponseBody
  public String addWriteAccess(Principal principal,
      @PathVariable String instanceName,
      @RequestParam String itemName,
      @RequestParam String accessName) {
    if (!accessManagerService.isUserAdmin(principal.getName())) {
      throw new InvalidParameterException("Only Admins are allowed to add new permissions.");
    }

    if (!accessManagerService.smartHomeAccessInstanceExists(instanceName)) {
      throw new InvalidParameterException("This smarthomeinstance des not exist.");
    }

    SmartHomeAccessInstance smartHomeAccessInstance =
        accessManagerService.addWriteAccessForUser(instanceName, itemName, accessName);

    Gson gson = new Gson();
    return gson.toJson(smartHomeAccessInstance);
  }

  @PostMapping(value = "/smarthomeaccessinstance/{instanceName}/read")
  @ResponseBody
  public String addReadAccess(Principal principal,
      @PathVariable String instanceName,
      @RequestParam String itemName,
      @RequestParam String accessName) {
    if (!accessManagerService.isUserAdmin(principal.getName())) {
      throw new InvalidParameterException("Only Admins are allowed to add new permissions.");
    }

    if (!accessManagerService.smartHomeAccessInstanceExists(instanceName)) {
      throw new InvalidParameterException("This smarthomeinstance des not exist.");
    }

    SmartHomeAccessInstance smartHomeAccessInstance =
        accessManagerService.addReadAccessForUser(instanceName, itemName, accessName);

    Gson gson = new Gson();
    return gson.toJson(smartHomeAccessInstance);
  }

}
