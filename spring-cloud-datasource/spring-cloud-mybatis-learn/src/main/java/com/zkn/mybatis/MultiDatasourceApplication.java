package com.zkn.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-06-02 23:11
 * @classname NacosConfigApplication
 */
@MapperScan("com.zkn.mybatis.dao.first")
@SpringBootApplication
public class MultiDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDatasourceApplication.class);
    }
}
