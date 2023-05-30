package com.zkn.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author conanzhang
 * @date 2022/6/10-5:45 PM
 * @classname LiquibaseApplicationController
 * @description
 */
@SpringBootApplication
public class LiquibaseApplicationController {

    public static void main(String[] args) {
        SpringApplication.run(LiquibaseApplicationController.class);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        //设置数据源
        liquibase.setDataSource(dataSource);
        //sql文件位置
        liquibase.setChangeLog("classpath:liquibase/master.xml");
        return liquibase;
    }
}
