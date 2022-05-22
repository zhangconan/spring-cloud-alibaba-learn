package com.zkn.mybatis.model;

import lombok.Data;

/**
 * @author conanzhang
 * @date 2022/5/20-4:42 PM
 * @classname User
 * @description
 */
@Data
public class User {

    /**
     * 主键ID
     */
    private String id;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 地址信息
     */
    private String address;
    /**
     * 电话
     */
    private String telPhone;
}
