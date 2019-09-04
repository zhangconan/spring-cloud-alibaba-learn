package com.zkn.nacos.util.method;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-09-04 00:10
 * @classname SunMethodClass
 */
public class SunMethodClass extends FatherMethodClass implements InterfaceClass {

    /**
     * 子方法
     */
    public void sunMethod() {

        System.out.println("子方法");
    }

    /**
     * 子类默认方法
     */
    void defaultSunMethod() {

        System.out.println("子类默认方法!");
    }

    /**
     * 子类默认方法
     */
    void protectSunMethod() {

        System.out.println("子类保护方法!");
    }

    /**
     * 子类默认方法
     */
    void privateSunMethod() {

        System.out.println("子类私有方法!");
    }

    /**
     * 接口方法
     */
    @Override
    public void interfaceMethod() {
        System.out.println("这是接口方法");
    }
}
