package com.zkn.nacos.elasticsearch.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-02-24 00:13
 * @classname BaseEntity
 */
@Getter
@Setter
public class BaseEntity {

    /**
     * 主键
     */
    private String id;

    /**
     * 获取JSON数据
     *
     * @return
     */
    @JSONField(serialize = false)
    public String getData() {

        return JSON.toJSONString(this);
    }
}
