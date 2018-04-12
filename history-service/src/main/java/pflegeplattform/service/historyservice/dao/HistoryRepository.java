package pflegeplattform.service.historyservice.dao;

import pflegeplattform.service.historyservice.entities.EshInstance;
import pflegeplattform.service.historyservice.entities.ItemNameInstance;
import pflegeplattform.service.historyservice.entities.PlatformInstance;

public interface HistoryRepository {

  PlatformInstance getInstances();

  EshInstance getItemsForInstance(String instanceName);

  EshInstance getEshInstance(String instanceName);

  String getItemStates(String instanceName, String itemName);

  String getLatestItemState(String instanceName, String itemName);

}
