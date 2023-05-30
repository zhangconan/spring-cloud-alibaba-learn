package com.zkn.spring.aop.learn.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

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
    public String around(String zhangsan01) {
        System.out.println(zhangsan01);
        return "环绕通知!";
    }

    public static void main(String[] args) {

        Class clazz = AopLearnController.class;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
}
