package com.zkn.nacos.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.zkn.nacos.elasticsearch.domain.SchoolDomain;
import com.zkn.nacos.elasticsearch.service.BaseElasticService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-02-21 00:11
 * @classname ElasticsearchController
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ElasticsearchController {

    @Autowired
    private BaseElasticService baseElasticService;

    /**
     * 创建一个学校文档
     *
     * @param stuName
     * @param className
     * @return
     */
    @RequestMapping("saveSchool.json")
    public String saveSchool(String stuName, String className) {
        SchoolDomain schoolDomain = new SchoolDomain();
        schoolDomain.setId(UUID.randomUUID().toString());
        schoolDomain.setStuName(stuName);
        schoolDomain.setClassName(className);
        baseElasticService.insertOrUpdateOne("school", schoolDomain);
        return "success";
    }

    /**
     * 查询
     *
     * @param className
     * @return
     */
    @RequestMapping("boolQueryDemo.json")
    public String boolQuery(String className, Integer pageSize, Integer currentPage) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (StringUtils.hasText(className)) {
            boolQueryBuilder.must(QueryBuilders.termQuery("className", className));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(boolQueryBuilder)
                .from(currentPage == null ? 0 : currentPage)
                .size(pageSize == null ? 20 : pageSize)
                .trackTotalHits(true);
        List<SchoolDomain> result = baseElasticService.search("school", searchSourceBuilder, SchoolDomain.class);
        return JSON.toJSONString(result);
    }

}
