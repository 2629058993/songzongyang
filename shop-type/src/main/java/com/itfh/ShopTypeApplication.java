package com.itfh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(scanBasePackages = {"com.itfh.*","com.itzzy.*"})
@MapperScan("com.itfh.mapper")
@EnableRedisRepositories
public class ShopTypeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopTypeApplication.class, args);
    }

}
