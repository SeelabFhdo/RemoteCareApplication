package pflegeplattform.service.historyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class HistoryServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HistoryServiceApplication.class, args);
  }
}
