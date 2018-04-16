package pflegeplattform.service.historyservice.controller.rest;

import com.google.gson.Gson;
import java.security.InvalidParameterException;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pflegeplattform.service.historyservice.entities.EshInstance;
import pflegeplattform.service.historyservice.entities.RepresentativeStateItem;
import pflegeplattform.service.historyservice.service.HistoryService;
import pflegeplattform.service.historyservice.service.consumer.AccessControlFeignClient;


@RestController
@RequestMapping(value = "/resources/history", produces = "application/json")
public class HistoryserviceController {

  private HistoryService historyService;
  private AccessControlFeignClient accessControlFeignClient;

  @Autowired
  public HistoryserviceController(
      HistoryService historyService,
      AccessControlFeignClient accessControlFeignClient) {
    this.historyService = historyService;
    this.accessControlFeignClient = accessControlFeignClient;
  }

  /**
   * This Methode return all EshInstances, which are stored in the Database.
   *
   * @return a json object, which contains all EshInstance names.
   */
  @GetMapping(value = "/instances")
  public String getInstanceNames() {
    Gson gson = new Gson();
    return gson.toJson(historyService.getInstances());
  }

  /**
   * This methode return all Item names from an EshInstance.
   *
   * @param instanceName Name of the EshInstance
   * @return the names of all Items.
   */
  @GetMapping(value = "/instances/{instanceName}/items")
  public String getItemsOfInstance(
      @PathVariable("instanceName") String instanceName) {
    List<RepresentativeStateItem> itemList = historyService.getItemsForInstance(instanceName);

    if (itemList == null) {
      throw new InvalidParameterException("Smarthome instance not found!");
    }

    Gson gson = new Gson();
    return gson.toJson(itemList);

  }

  /**
   * This methode return all Item names from an EshInstance.
   *
   * @param instanceName Name of the EshInstance
   * @return the names of all Items.
   */
  @GetMapping(value = "/instances/{instanceName}")
  public String getItemsAndStatesOfInstance(
      @PathVariable("instanceName") String instanceName) {
    EshInstance eshInstance = historyService.getEshInstance(instanceName);

    if (eshInstance == null) {
      throw new InvalidParameterException("Smarthome instance not found!");
    }

    Gson gson = new Gson();
    return gson.toJson(eshInstance);
  }

  /**
   * This methode return all itemstates from an specific Item
   *
   * @param instanceName Name of the EshInstance
   * @param itemName Name of the Item
   * @return all item states
   */
  @GetMapping(value = "/instances/{instanceName}/item/{itemName}")
  public String getItemStates(@PathVariable("instanceName") String instanceName,
      @PathVariable("itemName") String itemName) {
    if (!accessControlFeignClient.hasReadAccess(instanceName, itemName)) {
      throw new InvalidParameterException("No access to this information.");
    }

    String eshItems = historyService.getLatestItemState(instanceName, itemName);
    if (eshItems == null) {
      throw new InvalidParameterException("Smarthome instance or Smarthome item not found!");
    }
    return eshItems;
  }

  /**
   * This methode return the latest state form a specific item
   *
   * @param instanceName name of the EshInstance
   * @param itemName name of the Item
   * @return The latest Item state
   */
  @GetMapping(value = "/instances/{instanceName}/item/{itemName}/latest")
  public String getItemStateLatest(
      @PathVariable("instanceName") String instanceName,
      @PathVariable("itemName") String itemName, Principal principal) {
    if (!accessControlFeignClient.hasReadAccess(instanceName, itemName)) {
      throw new InvalidParameterException("No access to this information.");
    }
    String itemState = historyService.getLatestItemState(instanceName, itemName);

    if (itemState == null) {
      throw new InvalidParameterException("Smarthome instance or Smarthome item not found!");
    }
    return itemState;
  }

}
