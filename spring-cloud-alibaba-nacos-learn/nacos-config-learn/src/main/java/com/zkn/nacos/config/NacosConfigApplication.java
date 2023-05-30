package com.zkn.nacos.config;

import com.zkn.nacos.config.pojo.SchoolDomain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-06-02 23:11
 * @classname NacosConfigApplication
 */
@EnableConfigurationProperties(SchoolDomain.class)
@SpringBootApplication
public class NacosConfigApplication {

    public static void main(String[] args) {

        SpringApplication.run(NacosConfigApplication.class);
    }
}
