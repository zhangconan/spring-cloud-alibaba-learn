package com.zkn.mybatis.entity;

import lombok.Data;

/**
 * @author conanzhang@木森
 * @description
 * @date 8/12/21 3:27 PM
 * @classname UserInfo
 */
@Data
public class UserInfo extends BaseEntity {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 地址
     */
    private String address;
}
