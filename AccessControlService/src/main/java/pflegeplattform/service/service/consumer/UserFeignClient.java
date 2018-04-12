package pflegeplattform.service.service.consumer;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pflegeplattform.service.data.entities.User;

@FeignClient("USERMANAGEMENT")
public interface UserFeignClient {
  @RequestMapping(value = "/resources/user/{username}", method = GET)
  User getUserByUsername(@PathVariable("username") String  username);
}

