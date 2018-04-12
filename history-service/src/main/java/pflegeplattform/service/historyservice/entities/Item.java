package pflegeplattform.service.historyservice.entities;

public  class Item {

  private String itemName;
  private String itemType;

  public Item() {

  }

  public Item(String itemName, String itemType) {
    this.itemName = itemName;
    this.itemType = itemType;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    Item that = (Item) object;

    if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) {
      return false;
    }
    return itemType != null ? itemType.equals(that.itemType) : that.itemType == null;
  }

  @Override
  public int hashCode() {
    int result = itemName != null ? itemName.hashCode() : 0;
    result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Item{" +
        "itemName='" + itemName + '\'' +
        ", itemType='" + itemType + '\'' +
        '}';
  }
}
