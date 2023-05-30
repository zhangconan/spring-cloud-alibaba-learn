package com.zkn.learn.util.reflect;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author conanzhang
 * @date 2022/12/5-9:20 PM
 * @classname UserInfoDomain
 * @description
 */
@Data
public class UserInfoDomain {

    private String userName;

    private Integer age;

    private Boolean studentFlag;

    @SneakyThrows
    public void setInfo(String fieldName, Object value) {
        Field field = ReflectionUtils.findField(this.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            field.set(this, value);
        }
    }
}
