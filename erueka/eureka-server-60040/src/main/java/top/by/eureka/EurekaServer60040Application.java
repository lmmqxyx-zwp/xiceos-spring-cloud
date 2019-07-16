package top.by.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer60040Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer60040Application.class, args);
    }

}
