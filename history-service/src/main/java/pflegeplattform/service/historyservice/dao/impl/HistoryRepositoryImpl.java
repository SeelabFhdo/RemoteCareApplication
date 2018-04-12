package pflegeplattform.service.historyservice.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pflegeplattform.service.historyservice.dao.HistoryRepository;
import pflegeplattform.service.historyservice.entities.EshInstance;
import pflegeplattform.service.historyservice.entities.HistoryItem;
import pflegeplattform.service.historyservice.entities.ItemNameInstance;
import pflegeplattform.service.historyservice.entities.ItemState;
import pflegeplattform.service.historyservice.entities.PlatformInstance;

@Repository

public class HistoryRepositoryImpl implements HistoryRepository {

  private MongoTemplate mongoTemplate;

  @Autowired
  public HistoryRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public PlatformInstance getInstances() {
    HashSet collectionNames = (HashSet<String>) mongoTemplate.getCollectionNames();
    JsonObject jsonObject = new JsonObject();
    collectionNames.remove("system.indexes");
    PlatformInstance platformInstance = new PlatformInstance(collectionNames);
    return platformInstance;
  }

  @Override
  public EshInstance getItemsForInstance(String instanceName) {
    Query instanceQuery = new Query();
    instanceQuery.addCriteria(Criteria.where("instanceName").is(instanceName));
    System.out.println(instanceName);
    if (mongoTemplate.exists(instanceQuery, instanceName)) {
      EshInstance eshInstance = (EshInstance) mongoTemplate
          .findOne(instanceQuery, EshInstance.class, instanceName);
/*      ItemNameInstance itemNameInstance = new ItemNameInstance(eshInstance.getInstanceName());
      for (HistoryItem historyItem : eshInstance.getHistoryItemList()) {
        itemNameInstance.addItemName(historyItem.getItemName());
      }
      System.out.println(instanceName);
      Gson gson = new GsonBuilder().setPrettyPrinting().create();*/
      return eshInstance;
    }
    return null;
  }

  @Override
  public EshInstance getEshInstance(String instanceName) {
    Query instanceQuery = new Query();
    instanceQuery.addCriteria(Criteria.where("instanceName").is(instanceName));
    System.out.println(instanceName);
    if (mongoTemplate.exists(instanceQuery, instanceName)) {
      EshInstance eshInstance = (EshInstance) mongoTemplate
          .findOne(instanceQuery, EshInstance.class, instanceName);

      List<HistoryItem> lastItemStateList = eshInstance.getHistoryItemList();

      for (HistoryItem item : lastItemStateList) {
        List<ItemState> list = new LinkedList<ItemState>();
        list.add(item.getItemStateList().get(item.getItemStateList().size() - 1));
        item.setItemStateList(list);
      }
      return eshInstance;
    }
    return null;
  }

  @Override
  public String getItemStates(String instanceName, String itemName) {
    Query instanceQuery = new Query();
    instanceQuery.addCriteria(Criteria.where("instanceName").is(instanceName));

    if (mongoTemplate.exists(instanceQuery, instanceName)) {
      EshInstance eshInstance = (EshInstance) mongoTemplate
          .findOne(instanceQuery, EshInstance.class, instanceName);
      return eshInstance.getItemStates(itemName);
    }
    return null;
  }

  @Override
  public String getLatestItemState(String instanceName, String itemName) {
    Query instanceQuery = new Query();
    instanceQuery.addCriteria(Criteria.where("instanceName").is(instanceName));

    if (mongoTemplate.exists(instanceQuery, instanceName)) {
      EshInstance eshInstance = (EshInstance) mongoTemplate
          .findOne(instanceQuery, EshInstance.class, instanceName);
      return eshInstance.getItemStateLatest(itemName);
    }
    return null;
  }
}

