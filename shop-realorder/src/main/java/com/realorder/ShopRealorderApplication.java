package com.realorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.realorder.*","com.itzzy.*","com.github.wxpay.sdk.*"})
@EnableScheduling
@MapperScan("com.realorder.mapper")
public class ShopRealorderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopRealorderApplication.class, args);
    }

}
