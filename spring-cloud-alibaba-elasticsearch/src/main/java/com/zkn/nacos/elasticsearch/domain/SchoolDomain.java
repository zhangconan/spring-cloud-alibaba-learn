package com.zkn.nacos.elasticsearch.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-02-23 21:59
 * @classname SchoolDomain
 */
@Getter
@Setter
@ToString
public class SchoolDomain extends BaseEntity implements Serializable {

    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 班级
     */
    private String className;
}
