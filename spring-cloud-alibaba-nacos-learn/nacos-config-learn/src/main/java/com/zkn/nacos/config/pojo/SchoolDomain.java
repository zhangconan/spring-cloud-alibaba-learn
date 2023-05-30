package com.zkn.nacos.config.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author conanzhang@木森
 * @description
 * @date 3/15/21 1:27 PM
 * @classname SchoolDomain
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "zkn.school")
public class SchoolDomain {

    /**
     * 学校姓名
     */
    private String name;
    /**
     * 学校地址
     */
    private String address;
}
