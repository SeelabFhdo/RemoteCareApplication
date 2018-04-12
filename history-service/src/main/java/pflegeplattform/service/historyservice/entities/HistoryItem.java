package pflegeplattform.service.historyservice.entities;

import com.google.gson.Gson;
import java.util.LinkedList;
import java.util.List;

public class HistoryItem extends Item {

  private List<ItemState> itemStateList;

  public HistoryItem() {
    super();
    this.itemStateList = new LinkedList<ItemState>();
  }

  /**
   * Constructor for HistoryItem Object.
   *
   * @param transferItem transfer item Object.
   */
  public HistoryItem(TransferItem transferItem) {
    super(transferItem.getItemName(), transferItem.getItemType());
    this.itemStateList = new LinkedList<ItemState>();
    this.itemStateList.add(transferItem.getItemState());
  }

  public void addItemState(ItemState itemState) {
    itemStateList.add(itemState);
  }

  public List<ItemState> getItemStateList() {
    return itemStateList;
  }

  public void setItemStateList(List<ItemState> itemStateList) {
    this.itemStateList = itemStateList;
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
