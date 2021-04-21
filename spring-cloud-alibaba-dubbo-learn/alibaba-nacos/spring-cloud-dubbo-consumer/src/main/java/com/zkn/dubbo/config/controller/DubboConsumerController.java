package com.zkn.dubbo.config.controller;

import com.conan.shared.api.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-06-03 13:04
 * @classname NacosConfigController
 */
@RestController
public class DubboConsumerController {

    @Reference
    private UserService userService;

    @GetMapping("name")
    public String name(String userName) {
        return userService.getUserName(userName);
    }
}
