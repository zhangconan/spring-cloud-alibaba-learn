package com.zkn.mybatis.entity;

import lombok.Data;

/**
 * @author conanzhang
 * @date 2022/5/20-4:44 PM
 * @classname CombineUserInfo
 * @description
 */
@Data
public class CombineUserInfo {

    /**
     * 部门信息
     */
    private Department department;
    /**
     * 角色信息
     */
    private Role role;
    /**
     * 用户信息
     */
    private User user;
}
