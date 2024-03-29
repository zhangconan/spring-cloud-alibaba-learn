package com.zkn.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-06-02 23:11
 * @classname MybatisPlusApplication
 */
@MapperScan("com.zkn.mybatis.dao")
@SpringBootApplication
public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class);
    }
}
