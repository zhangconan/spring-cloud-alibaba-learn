package com.zkn.nacos.util.enums;

import lombok.Getter;

/**
 * @author conanzhang
 * @date 2022/5/17-8:29 PM
 * @classname TestEnum
 * @description
 */
@Getter
public enum TestEnum {

    GREEN("绿灯"),
    RED("红灯"),
    YELLOW("黄灯"),
    ;

    private String code;

    TestEnum(String code) {
        this.code = code;
    }
    
}
