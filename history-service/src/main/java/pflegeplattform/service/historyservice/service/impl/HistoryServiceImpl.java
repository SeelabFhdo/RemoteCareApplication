package pflegeplattform.service.historyservice.service.impl;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pflegeplattform.service.historyservice.dao.HistoryRepository;
import pflegeplattform.service.historyservice.entities.EshInstance;
import pflegeplattform.service.historyservice.entities.HistoryItem;
import pflegeplattform.service.historyservice.entities.PlatformInstance;
import pflegeplattform.service.historyservice.entities.RepresentativeStateItem;
import pflegeplattform.service.historyservice.service.HistoryService;
import pflegeplattform.service.historyservice.service.consumer.AccessControlFeignClient;

@Service
public class HistoryServiceImpl implements HistoryService {

  private HistoryRepository historyRepository;
  private AccessControlFeignClient accessControlFeignClient;

  @Autowired
  public HistoryServiceImpl(
      HistoryRepository historyRepository,
      AccessControlFeignClient accessControlFeignClient) {
    this.historyRepository = historyRepository;
    this.accessControlFeignClient = accessControlFeignClient;
  }

  @Override
  public PlatformInstance getInstances() {
    return historyRepository.getInstances();
  }

  @Override
  public List<RepresentativeStateItem> getItemsForInstance(String instanceName) {
    EshInstance eshInstance = historyRepository.getItemsForInstance(instanceName);
    List<RepresentativeStateItem> itemList = new LinkedList<RepresentativeStateItem>();

    for (HistoryItem historyItem : eshInstance.getHistoryItemList()) {
      RepresentativeStateItem item = new RepresentativeStateItem();
      item.setItemName(historyItem.getItemName());
      item.setItemType(historyItem.getItemType());
      item.setItemState(
          historyItem.getItemStateList().get(historyItem.getItemStateList().size() - 1));
      itemList.add(item);

    }

    List<RepresentativeStateItem> itemListForUser = new LinkedList<RepresentativeStateItem>();
    for (RepresentativeStateItem ritem : itemList) {

      if (accessControlFeignClient.hasReadAccess(instanceName, ritem.getItemName())) {
        itemListForUser.add(ritem);
      }
    }
    return itemListForUser;
  }

  @Override
  public EshInstance getEshInstance(String instanceName) {
    return historyRepository.getEshInstance(instanceName);
  }

  @Override
  public String getItemStates(String instanceName, String itemName) {
    return historyRepository.getItemStates(instanceName, itemName);
  }

  @Override
  public String getLatestItemState(String instanceName, String itemName) {
    return historyRepository.getLatestItemState(instanceName, itemName);
  }
}
