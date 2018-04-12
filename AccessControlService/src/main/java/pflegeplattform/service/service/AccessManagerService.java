package pflegeplattform.service.service;

import pflegeplattform.service.data.entities.SmartHomeAccessInstance;

public interface AccessManagerService {

  boolean isUserAdmin(String userName);

  SmartHomeAccessInstance addSmartHomeAccessInstance(String instanceName);

  boolean smartHomeAccessInstanceExists(String instanceName);

  SmartHomeAccessInstance addWriteAccessForUser(String instanceName, String itemName,
      String userName);

  SmartHomeAccessInstance addReadAccessForUser(String instanceName, String itemName,
      String userName);

  boolean hasReadAccessToItem(String instanceName, String itemName, String userName);

  boolean hasWriteAccessToItem(String instanceName, String itemName, String userName);

}
