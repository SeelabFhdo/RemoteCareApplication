package pflegeplattform.service.historyservice.entities;

import java.util.Collection;
import java.util.LinkedList;


public class PlatformInstance {

  private Collection<String> instances;

  public PlatformInstance(Collection<String> instances) {
    this.instances = instances;
  }

  public PlatformInstance() {
    instances = new LinkedList<String>();
  }

  public Collection<String> getInstances() {
    return instances;
  }

  public void setInstances(Collection<String> instances) {
    this.instances = instances;
  }
}
