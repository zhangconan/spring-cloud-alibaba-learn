package com.zkn.nacos.util.method;

import org.junit.Test;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-09-04 00:12
 * @classname MethodTest
 */
public class MethodTest {

    @Test
    public void test() throws NoSuchMethodException {
        SunMethodClass sunMethodClass = new SunMethodClass();
        Method[] methods = sunMethodClass.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
        //获取default方法
        Method method = ReflectionUtils.findMethod(SunMethodClass.class, "defaultMethod");

        Method defaultMethod = ClassUtils.getMostSpecificMethod(method, sunMethodClass.getClass());

        Method method1 = sunMethodClass.getClass().getMethod("defaultMethod", null);

    }
}
