package com.zkn.springmvc.learn.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * @author conanzhang
 * @date 2022/11/21-7:21 PM
 * @classname MvcTestDTO
 * @description
 */
@Data
public class MvcTestDTO {
    /**
     * 状态
     */
    private List<String> status;
    /**
     * 用户信息
     */
    private UserInfo user;

    /**
     * 用户相关信息
     */
    @Data
    public static class UserInfo {
        /**
         * 用户姓名
         */
        private String userName;
        /**
         * 用户地址
         */
        private String address;
    }
}

