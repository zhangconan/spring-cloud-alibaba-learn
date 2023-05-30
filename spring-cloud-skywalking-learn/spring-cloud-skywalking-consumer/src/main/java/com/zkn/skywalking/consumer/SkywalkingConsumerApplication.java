package com.zkn.skywalking.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-06-02 23:11
 * @classname NacosConfigApplication
 */
@EnableFeignClients
@SpringBootApplication
public class SkywalkingConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkywalkingConsumerApplication.class);
    }
}
