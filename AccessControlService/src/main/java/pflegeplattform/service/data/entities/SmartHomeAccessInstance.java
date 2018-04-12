package pflegeplattform.service.data.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Document(collection = "SmartHomeAccessInstance")
public class SmartHomeAccessInstance {
    @Id
    private String id;

    private String instanceName;
    private List<AccessItem> itemList;

    public SmartHomeAccessInstance () {

    }

    public SmartHomeAccessInstance (String instanceName){
        this.instanceName = instanceName;
        itemList = new LinkedList<AccessItem>();
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public List<AccessItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<AccessItem> itemList) {
        this.itemList = itemList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addWriteAccess (String itemName, String userName) {
        for (AccessItem accessItem: itemList) {
            if (accessItem.getItemName().equals(itemName)) {
                accessItem.addWriteAccess(userName);
                return;
            }
        }
        AccessItem accessItem = new AccessItem(itemName);
        accessItem.addWriteAccess(userName);
        itemList.add(accessItem);
    }

    public void addReadAccess (String itemName, String userName) {
        for (AccessItem accessItem: itemList) {
            if (accessItem.getItemName().equals(itemName)) {
                accessItem.addReadAccess(userName);
                return;
            }
        }
        AccessItem accessItem = new AccessItem(itemName);
        accessItem.addReadAccess(userName);
        itemList.add(accessItem);
    }

    @Override
    public String toString() {
        return  "SmartHomeAccessInstance{" +
                "id='" + id + '\'' +
                ", instanceName='" + instanceName + '\'' +
                '}';
    }
}
