package com.zkn.nacos.util.enums;

import lombok.Getter;

/**
 * @Author: zhangconan
 * @Date: 2022/5/18 00:18
 * @Description:
 */
@Getter
public enum TrafficEnum {

    GREEN("绿色"),

    RED("红色"),

    YELLOW("黄色"),

    ;

    private String code;

    TrafficEnum(String code) {
        this.code = code;
    }

}
