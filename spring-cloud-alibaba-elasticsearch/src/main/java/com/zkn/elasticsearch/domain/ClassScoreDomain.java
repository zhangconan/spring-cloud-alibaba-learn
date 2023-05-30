package com.zkn.elasticsearch.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-04-17 19:45
 * @classname ClassScoreDomain
 */
@Getter
@Setter
@ToString
public class ClassScoreDomain extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -9193594123932177743L;

    /**
     * 班级名称
     */
    private String className;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 姓名
     */
    private String name;
}
