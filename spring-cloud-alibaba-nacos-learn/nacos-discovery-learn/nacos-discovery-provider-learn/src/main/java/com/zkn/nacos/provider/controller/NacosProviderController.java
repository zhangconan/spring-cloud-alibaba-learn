package com.zkn.nacos.provider.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
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
@RestController
public class NacosProviderController {

    /**
     * 得到姓名
     *
     * @param name
     * @return
     */
    @GetMapping("GetName")
    public String getName(String name) {
        return "Provider-Name:" + name;
    }
}
