package com.zkn.nacos.config.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author conanzhang@木森
 * @description
 * @date 3/15/21 1:16 PM
 * @classname UserDomain
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "zkn.user")
public class UserDomain {

    /**
     * 用户名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
}
