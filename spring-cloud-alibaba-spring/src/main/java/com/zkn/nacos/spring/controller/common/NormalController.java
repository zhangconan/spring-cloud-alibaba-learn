package com.zkn.nacos.spring.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-09-05 00:00
 * @classname NormalController
 */
@RestController("normal")
public class NormalController {

    @RequestMapping("name")
    public String name() {

        return "张三";
    }
}
