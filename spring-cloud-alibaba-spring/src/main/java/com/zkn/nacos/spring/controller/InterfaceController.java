package com.zkn.nacos.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-09-04 00:29
 * @classname InterfaceController
 */
public interface InterfaceController {

    /**
     * 测试方法
     *
     * @return
     */
    @RequestMapping("test")
    default String test() {
        return "test";
    }
}
