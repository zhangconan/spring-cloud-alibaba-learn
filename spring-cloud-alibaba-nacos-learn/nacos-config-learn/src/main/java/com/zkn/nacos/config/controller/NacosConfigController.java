package com.zkn.nacos.config.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-06-03 13:04
 * @classname NacosConfigController
 */
@Getter
@Setter
@RefreshScope
@RestController
public class NacosConfigController {
    /**
     * 姓名
     */
    @Value("${zkn.nacos.name}")
    private String name;

    @GetMapping("name")
    public String name() {
        return "name:" + name;
    }
}
