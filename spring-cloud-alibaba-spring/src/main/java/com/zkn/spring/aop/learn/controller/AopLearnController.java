package com.zkn.spring.aop.learn.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author conanzhang@木森
 * @description aop 控制类
 * @date 3/14/21 11:30 PM
 * @classname AopLearnController
 */
@RestController
@RequestMapping(path = "aop", produces = MediaType.APPLICATION_JSON_VALUE)
public class AopLearnController {

    /**
     * AOP环绕通知
     *
     * @return
     */
    @GetMapping("around")
    public String around() {

        return "环绕通知!";
    }
}
