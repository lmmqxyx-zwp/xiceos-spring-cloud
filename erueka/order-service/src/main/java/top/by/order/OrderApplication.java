package top.by.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Configuration
public class OrderApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrderApplication.class, args);

    }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
