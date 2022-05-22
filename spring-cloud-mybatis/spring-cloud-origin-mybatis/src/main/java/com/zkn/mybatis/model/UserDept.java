package com.zkn.mybatis.model;

import lombok.Data;

/**
 * @author conanzhang
 * @date 2022/5/21-3:18 PM
 * @classname UserDept
 * @description
 */
@Data
public class UserDept extends User {

    /**
     * 部门信息
     */
    private Department department;
}
