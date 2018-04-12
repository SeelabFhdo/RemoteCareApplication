package pflegeplattform.service.data.entities;

import java.util.LinkedList;
import java.util.List;

public class AccessItem {
    private String itemName;
    private List<String> readAccessList;
    private List<String> writeAccessList;

    public AccessItem(String itemName) {
        this.itemName = itemName;
        readAccessList = new LinkedList<String>();
        writeAccessList = new LinkedList<String>();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<String> getReadAccessList() {
        return readAccessList;
    }

    public void setReadAccessList(List<String> readAccessList) {
        this.readAccessList = readAccessList;
    }

    public List<String> getWriteAccessList() {
        return writeAccessList;
    }

    public void setWriteAccessList(List<String> writeAccessList) {
        this.writeAccessList = writeAccessList;
    }

    public void addWriteAccess (String userName) {
        for (String name: writeAccessList) {
            if (name.equals(userName)) {
                return;
            }
        }
        writeAccessList.add(userName);
    }

    public void addReadAccess (String userName) {
        for (String name : readAccessList) {
            if (name.equals(userName)) {
                return;
            }
        }
        readAccessList.add(userName);
    }


}
