package com.zkn.learn.util.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-09-03 20:58
 * @classname ProxyInvocationHandler
 */
public class ProxyInvocationHandler implements InvocationHandler {

    private ProxyService proxyService;

    public ProxyInvocationHandler(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return method.invoke(proxyService, args);
    }
}
