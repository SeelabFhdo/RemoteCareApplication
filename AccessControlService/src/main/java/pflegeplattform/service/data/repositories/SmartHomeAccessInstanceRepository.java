package pflegeplattform.service.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pflegeplattform.service.data.entities.SmartHomeAccessInstance;

public interface SmartHomeAccessInstanceRepository extends MongoRepository<SmartHomeAccessInstance, String> {
    public SmartHomeAccessInstance findByInstanceName(String instanceName);
    public SmartHomeAccessInstance findById (String id);
}
