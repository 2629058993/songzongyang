package com.itzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(scanBasePackages = {"com.itzy.*","com.itzzy.*"})
@MapperScan("com.itzy.mapper")
@EnableRedisRepositories
public class ShopBrandApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopBrandApplication.class, args);
    }

}
