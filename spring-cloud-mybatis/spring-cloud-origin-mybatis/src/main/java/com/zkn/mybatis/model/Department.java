package com.zkn.mybatis.model;

import lombok.Data;

/**
 * @author conanzhang
 * @date 2022/5/20-4:42 PM
 * @classname Department
 * @description
 */
@Data
public class Department {

    /**
     * 主键ID
     */
    private String id;
    /**
     * 部门名称
     */
    private String depName;
}
