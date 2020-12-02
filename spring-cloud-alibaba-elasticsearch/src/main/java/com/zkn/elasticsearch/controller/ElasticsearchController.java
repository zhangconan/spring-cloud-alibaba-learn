package com.zkn.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.zkn.elasticsearch.domain.ClassScoreDomain;
import com.zkn.elasticsearch.domain.SchoolDomain;
import com.zkn.elasticsearch.service.BaseElasticService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-02-21 00:11
 * @classname ElasticsearchController
 */
@Slf4j
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

    /**
     * 单个字段聚合
     *
     * @param requestId
     * @return
     */
    @RequestMapping("singleAggregation.json")
    public Map<String, Long> singleAggregation(String requestId) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //聚合查询
        AbstractAggregationBuilder aggregationBuilder = AggregationBuilders.terms("aggs-class").field("className");
        searchSourceBuilder.aggregation(aggregationBuilder);
        try {
            return baseElasticService.querySingleAggregation("school", searchSourceBuilder, "aggs-class");
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("ElasticsearchController#singleAggregation Exception!", e);
        }
        return Collections.emptyMap();
    }

    /**
     * test
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("test.json")
    public String test() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder all = AggregationBuilders.terms("aggs-first").field("className");
        AggregationBuilder subAgg = AggregationBuilders.terms("aggs-second").field("stuName.keyword");
        AggregationBuilder sumBuilder = AggregationBuilders.count("stuSum").field("stuName.keyword");
        all.subAggregation(subAgg.subAggregation(sumBuilder));
        searchSourceBuilder.aggregation(all);
        Aggregations aggregations = baseElasticService.queryAggregation("school", searchSourceBuilder);
        for (Aggregation a : aggregations) {
            ParsedStringTerms stringTerms = (ParsedStringTerms) a;
            for (Terms.Bucket bucket : stringTerms.getBuckets()) {
                Aggregation aggs = bucket.getAggregations().getAsMap().get("aggs-second");
                ParsedStringTerms terms1 = (ParsedStringTerms) aggs;
                for (Terms.Bucket bu : terms1.getBuckets()) {
                    log.info(bucket.getKeyAsString() + "  " + bu.getKeyAsString() + " " + bu.getDocCount() + "    " + ((ParsedValueCount) bu.getAggregations().asMap().get("stuSum")).getValue());
                }
            }
        }
        return "success";
    }

    /**
     * 创建班级分数索引
     *
     * @return
     * @throws Exception
     */
    @GetMapping("createClassScoreIndex.json")
    public String createIndex() throws Exception {

        String indexMapping = "{\"properties\": {\"id\": {\"type\": \"keyword\",\"store\":true,\"index\":true}," +
                "\"className\": {\"type\": \"keyword\"},\"score\": {\"type\": \"long\"},\"name\": {\"type\": \"keyword\"}}}";
        if (baseElasticService.isExistsIndex("class_score")) {
            return "索引已经存在";
        }
        baseElasticService.createIndex("class_score", indexMapping);
        return "索引创建成功";
    }


    /**
     * 创建班级分数索引
     *
     * @return
     * @throws Exception
     */
    @GetMapping("getAvg.json")
    public String getClassAvg() throws Exception {
        SearchSourceBuilder endSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder endBoolQuery = QueryBuilders.boolQuery();
        endSourceBuilder.query(endBoolQuery);
        //多字段聚合查询
        TermsAggregationBuilder endAggregationBuilder = AggregationBuilders.terms("aggs-first").field("className.keyword");
        endSourceBuilder.aggregation(endAggregationBuilder.subAggregation(AggregationBuilders.avg("agg").field("score")));
        //查询平均值
        Aggregations avgStatistics = baseElasticService.queryAggregation("class_score", endSourceBuilder);
        for (Aggregation a : avgStatistics) {
            ParsedTerms stringTerms = (ParsedTerms) a;
            for (Terms.Bucket bucket : stringTerms.getBuckets()) {
                System.out.println("className:" + bucket.getKeyAsString() + ",value:" + ((ParsedAvg) bucket.getAggregations().getAsMap().get("agg")).value());
            }
        }
        return "索引创建成功";
    }

    /**
     * 创建一个学校文档
     *
     * @param scoreDomain
     * @return
     */
    @RequestMapping("saveClassScore.json")
    public String saveSchool(ClassScoreDomain scoreDomain) {
        scoreDomain.setId(UUID.randomUUID().toString());
        baseElasticService.insertOrUpdateOne("class_score", scoreDomain);
        return "success";
    }

}
