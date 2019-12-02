package com.cart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(scanBasePackages = {"com.itzzy.*","com.cart.*"})
@MapperScan("com.cart.mapper")
@EnableRedisRepositories
public class ShopCartApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShopCartApplication.class, args);
    }

}
