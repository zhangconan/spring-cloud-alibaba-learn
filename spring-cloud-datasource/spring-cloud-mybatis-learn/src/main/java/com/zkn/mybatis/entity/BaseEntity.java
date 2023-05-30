package com.zkn.mybatis.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author conanzhang@木森
 * @description
 * @date 8/12/21 3:27 PM
 * @classname BaseEntity
 */
@Data
public class BaseEntity {

    /**
     * 主键
     */
    private Long id;
    /**
     * 删除标记
     */
    private Integer isDeleted;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 最后修改人
     */
    private String modifier;
    /**
     * 最后修改时间
     */
    private LocalDateTime gmtModified;
}
