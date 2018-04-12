package de.fhdortmund.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by phil on 12.02.17.
 *
 * This class provides a simple rest endpoint.
 */
@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
@EnableFeignClients
public class RemoteAccessServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(RemoteAccessServiceApplication.class, args);
  }
}
