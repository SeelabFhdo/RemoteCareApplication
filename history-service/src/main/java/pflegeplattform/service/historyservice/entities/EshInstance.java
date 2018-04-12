package pflegeplattform.service.historyservice.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.annotation.Id;

public class EshInstance {

  @Id
  private String id;

  private String instanceName;
  private List<HistoryItem> historyItemList;

  public EshInstance() {
    this.instanceName = "unbekannt";
    historyItemList = new LinkedList<HistoryItem>();
  }

  public EshInstance(String instanceName) {
    this.instanceName = instanceName;
    historyItemList = new LinkedList<HistoryItem>();
  }

  public void addHistoryItem(HistoryItem historyItem) {
    historyItemList.add(historyItem);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getInstanceName() {
    return instanceName;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }

  public List<HistoryItem> getHistoryItemList() {
    return historyItemList;
  }

  public void setHistoryItemList(List<HistoryItem> historyItemList) {
    this.historyItemList = historyItemList;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    EshInstance that = (EshInstance) object;

    if (!id.equals(that.id)) {
      return false;
    }
    return instanceName.equals(that.instanceName);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + instanceName.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "EshInstance{" +
        "id='" + id + '\'' +
        ", instanceName='" + instanceName + '\'' +
        '}';
  }

  public String getItemNames() {
    JsonObject jsonObject = new JsonObject();
    HashSet<String> itemNameSet = new HashSet<String>();

    for (HistoryItem historyItem : historyItemList) {
      itemNameSet.add(historyItem.getItemName());
    }
    jsonObject.addProperty("itemNames", itemNameSet.toString());
    return jsonObject.toString();
  }

  public String getItemStates(String itemName) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    for (HistoryItem historyItem : historyItemList) {
      if (historyItem.getItemName().equals(itemName)) {

        return gson.toJson(historyItem);
      }
    }
    return null;
  }

  public String getItemStateLatest(String itemName) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    for (HistoryItem historyItem : historyItemList) {
      if (historyItem.getItemName().equals(itemName)) {
        return gson
            .toJson(historyItem.getItemStateList().get(historyItem.getItemStateList().size() - 1));
      }
    }
    return null;
  }

}
