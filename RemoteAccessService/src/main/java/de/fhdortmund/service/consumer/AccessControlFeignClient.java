package de.fhdortmund.service.consumer;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("ACCESSCONTROLSERVICE")
public interface AccessControlFeignClient {

  @RequestMapping(value = "/resources/accesscontrol/smarthomeaccessinstance/{instanceName}/"
      + "{itemName}/write", method = GET)
  boolean checkWriteAccess(@PathVariable("instanceName") String username,
      @PathVariable("itemName") String intemName);
}
