package com.zkn.learn.util.pattern.proxy;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Proxy;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-09-03 21:02
 * @classname ProxyServiceTest
 */
public class ProxyServiceTest {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        RequestMapping proxyService = (RequestMapping) Proxy.newProxyInstance(ProxyServiceTest.class.getClassLoader(),
                new Class[]{ProxyService.class, RequestMapping.class},
                new ProxyInvocationHandler(new ProxyServiceImpl()));
    }
}
