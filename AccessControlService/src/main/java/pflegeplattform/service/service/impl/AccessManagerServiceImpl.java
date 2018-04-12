package pflegeplattform.service.service.impl;

import java.security.InvalidParameterException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pflegeplattform.service.data.entities.AccessItem;
import pflegeplattform.service.data.entities.SmartHomeAccessInstance;
import pflegeplattform.service.data.entities.User;
import pflegeplattform.service.data.repositories.SmartHomeAccessInstanceRepository;
import pflegeplattform.service.service.AccessManagerService;
import pflegeplattform.service.service.consumer.UserFeignClient;

@Service
public class AccessManagerServiceImpl implements AccessManagerService {

  private UserFeignClient userFeignClient;
  private SmartHomeAccessInstanceRepository smartHomeAccessInstanceRepository;

  @Autowired
  public AccessManagerServiceImpl(
      UserFeignClient userFeignClient,
      SmartHomeAccessInstanceRepository smartHomeAccessInstanceRepository) {
    this.userFeignClient = userFeignClient;
    this.smartHomeAccessInstanceRepository = smartHomeAccessInstanceRepository;
  }

  public boolean isUserAdmin(String userName) {
    User user = userFeignClient.getUserByUsername(userName);
    if (user != null) {

      return true;
    }
    return false;
  }

  public SmartHomeAccessInstance addSmartHomeAccessInstance(String instanceName) {
    return smartHomeAccessInstanceRepository.save(new SmartHomeAccessInstance(instanceName));
  }

  public boolean smartHomeAccessInstanceExists(String instanceName) {
    if (smartHomeAccessInstanceRepository.findByInstanceName(instanceName) != null) {
      return true;
    }

    return false;
  }

  public SmartHomeAccessInstance addWriteAccessForUser(String instanceName, String itemName,
      String userName) {
    SmartHomeAccessInstance smartHomeAccessInstance =
        smartHomeAccessInstanceRepository.findByInstanceName(instanceName);

    if (smartHomeAccessInstance == null) {
      return smartHomeAccessInstance;
    }

    smartHomeAccessInstance.addWriteAccess(itemName, userName);
    System.out.println(smartHomeAccessInstance.toString());
    smartHomeAccessInstanceRepository.save(smartHomeAccessInstance);
    return smartHomeAccessInstance;
  }

  public SmartHomeAccessInstance addReadAccessForUser(String instanceName, String itemName,
      String userName) {
    SmartHomeAccessInstance smartHomeAccessInstance =
        smartHomeAccessInstanceRepository.findByInstanceName(instanceName);

    if (smartHomeAccessInstance == null) {
      return smartHomeAccessInstance;
    }

    smartHomeAccessInstance.addReadAccess(itemName, userName);
    smartHomeAccessInstanceRepository.save(smartHomeAccessInstance);
    return smartHomeAccessInstance;
  }


  @Override
  public boolean hasReadAccessToItem(String instanceName, String itemName, String userName) {
    AccessItem accessItem = getAccessItem(instanceName, itemName, userName);

    if (accessItem != null && accessItem.getReadAccessList().contains(userName)) {
      return true;
    } else {
      return false;
    }

  }

  @Override
  public boolean hasWriteAccessToItem(String instanceName, String itemName, String userName) {
    AccessItem accessItem = getAccessItem(instanceName, itemName, userName);
    if (accessItem != null && accessItem.getWriteAccessList().contains(userName)) {
      return true;
    } else {
      return false;
    }
  }

  private AccessItem getAccessItem(String instanceName, String itemName, String userName) {
    SmartHomeAccessInstance smartHomeAccessInstance =
        smartHomeAccessInstanceRepository.findByInstanceName(instanceName);
    if (smartHomeAccessInstance == null) {
      throw new InvalidParameterException("Wrong AccessInstancename.");
    }

    List<AccessItem> accessItemsList = smartHomeAccessInstance.getItemList();
    AccessItem accessItem = null;

    for (AccessItem accessItemCandidate : accessItemsList) {
      if (accessItemCandidate.getItemName().equals(itemName)) {
        accessItem = accessItemCandidate;
        break;
      }
    }
    return accessItem;
  }
}
