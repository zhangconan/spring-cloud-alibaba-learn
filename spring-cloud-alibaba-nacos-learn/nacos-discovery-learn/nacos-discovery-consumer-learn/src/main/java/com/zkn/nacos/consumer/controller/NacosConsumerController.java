package com.zkn.nacos.consumer.controller;

import com.zkn.nacos.consumer.service.EchoFeignService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
@RefreshScope
@RestController
public class NacosConsumerController {

    @Autowired
    private EchoFeignService echoFeignService;

    /**
     * 输出姓名
     *
     * @param name
     * @return
     */
    @GetMapping("GetName.json")
    public String getName(String name) {
        return "name:" + echoFeignService.getName(name);
    }
}
