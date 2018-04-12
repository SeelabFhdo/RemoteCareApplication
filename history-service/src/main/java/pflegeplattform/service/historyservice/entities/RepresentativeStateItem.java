package pflegeplattform.service.historyservice.entities;

import java.util.Objects;

public class RepresentativeStateItem extends Item {
  private ItemState itemState;

  public RepresentativeStateItem() {
  }

  public RepresentativeStateItem(String itemName, String itemType,
      ItemState itemState) {
    super(itemName, itemType);
    this.itemState = itemState;
  }

  public ItemState getItemState() {
    return itemState;
  }

  public void setItemState(ItemState itemState) {
    this.itemState = itemState;
  }

  @Override
  public String toString() {
    return "RepresentativeStateItem{" +
        "itemState=" + itemState +
        '}';
  }
}
