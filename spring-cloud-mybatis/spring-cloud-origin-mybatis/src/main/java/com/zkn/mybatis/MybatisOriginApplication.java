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
@SpringBootApplication
@MapperScan("com.zkn.mybatis.dao")
public class MybatisOriginApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisOriginApplication.class);
    }
}
