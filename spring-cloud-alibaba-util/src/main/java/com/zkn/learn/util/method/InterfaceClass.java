package com.zkn.learn.util.method;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-09-04 00:55
 * @classname InterfaceClass
 */
public interface InterfaceClass {

    /**
     * 接口方法
     */
    void interfaceMethod();

    /**
     * 默认方法
     */
    default void defaultMethod() {

        System.out.println("这是一个默认方法!");
    }
}
