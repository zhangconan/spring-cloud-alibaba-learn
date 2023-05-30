package com.zkn.dubbo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-06-02 23:11
 * @classname NacosConfigApplication
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudDubboNacosConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDubboNacosConsumerApplication.class);
    }
}

