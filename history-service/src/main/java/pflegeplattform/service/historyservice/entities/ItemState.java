package pflegeplattform.service.historyservice.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemState {

  private String value;
  private String timestamp;

  public ItemState() {

  }

  /**
   * Constructor for ItemState object.
   *
   * @param value of itemState.
   */
  public ItemState(String value) {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.GERMANY);
    timestamp = format.format(new Date());
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getItemState() {
    return "{ \\\"value\\\" : \\\"" + value + "\\\", \\\"timestamp\\\" : \\\"" + timestamp
        + "\\\" }";
  }
}
