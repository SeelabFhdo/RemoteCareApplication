package pflegeplattform.service.historyservice.service;

import java.util.List;
import pflegeplattform.service.historyservice.entities.EshInstance;
import pflegeplattform.service.historyservice.entities.PlatformInstance;
import pflegeplattform.service.historyservice.entities.RepresentativeStateItem;

public interface HistoryService {

  PlatformInstance getInstances();

  List<RepresentativeStateItem> getItemsForInstance(String instanceName);

  EshInstance getEshInstance(String instanceName);

  String getItemStates(String instanceName, String itemName);

  String getLatestItemState(String instanceName, String itemName);
}
