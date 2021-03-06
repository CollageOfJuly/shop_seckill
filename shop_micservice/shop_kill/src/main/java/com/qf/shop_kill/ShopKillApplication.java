package com.qf.shop_kill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
public class ShopKillApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopKillApplication.class, args);
    }

}
