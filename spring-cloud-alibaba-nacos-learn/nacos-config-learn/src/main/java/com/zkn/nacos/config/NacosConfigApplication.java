package com.zkn.nacos.config;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-06-02 23:11
 * @classname NacosConfigApplication
 */
@SpringBootApplication
public class NacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class);
    }
}
