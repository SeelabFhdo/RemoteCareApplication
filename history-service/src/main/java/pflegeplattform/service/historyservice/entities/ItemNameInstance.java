package pflegeplattform.service.historyservice.entities;

import java.util.LinkedList;
import java.util.List;

public class ItemNameInstance {

  private String instanceName;
  private List<String> itemNameList;

  public ItemNameInstance(String itemName) {
    this.instanceName = itemName;
    itemNameList = new LinkedList<String>();
  }

  public String getInstanceName() {
    return instanceName;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }

  public List<String> getItemNameList() {
    return itemNameList;
  }

  public void setItemNameList(List<String> itemNameList) {
    this.itemNameList = itemNameList;
  }

  public void addItemName(String itemName) {
    itemNameList.add(itemName);
  }
}
