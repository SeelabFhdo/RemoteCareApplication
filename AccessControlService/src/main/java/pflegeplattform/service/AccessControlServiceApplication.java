package pflegeplattform.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
@EnableFeignClients
public class AccessControlServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccessControlServiceApplication.class, args);
    }
}
