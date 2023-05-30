package com.zkn.learn.util.reflect;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * @author conanzhang
 * @date 2022/12/5-9:23 PM
 * @classname SpringReflectionUtils
 * @description
 */
public class SpringReflectionUtils {

    @Test
    public void testFindField() {
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        userInfoDomain.setInfo("userName", "zhangsan");
        userInfoDomain.setInfo("age", 12);
        userInfoDomain.setInfo("studentFlag", false);
        System.out.println(JSON.toJSONString(userInfoDomain));
    }
}
