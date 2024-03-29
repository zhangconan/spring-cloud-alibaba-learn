package com.zkn.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author conanzhang
 * @date 2022/5/20-4:43 PM
 * @classname Role
 * @description
 */
@Data
@TableName("yl_role")
public class Role {

    /**
     * 主键ID
     */
    private String id;
    /**
     * 角色名称
     */
    private String roleName;
}
