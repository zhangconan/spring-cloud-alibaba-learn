package com.zkn.dubbo.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-06-02 23:11
 * @classname NacosConfigApplication
 */
@EnableDubbo
@SpringBootApplication
public class SpringCloudDubboNacosConsumerApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "nacos");
        SpringApplication.run(SpringCloudDubboNacosConsumerApplication.class);
    }
}
