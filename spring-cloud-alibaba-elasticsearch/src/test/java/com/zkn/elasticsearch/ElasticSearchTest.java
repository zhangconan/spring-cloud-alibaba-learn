package com.zkn.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.conan.shared.api.result.PageResult;
import com.zkn.elasticsearch.domain.SchoolDomain;
import com.zkn.elasticsearch.service.BaseElasticService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

/**
 * @author conanzhang@木森
 * @description 单元测试类
 * @date 2020-05-09 00:50
 * @classname ElasticSearchTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticsearchApplication.class)
public class ElasticSearchTest {

    @Autowired
    private BaseElasticService baseElasticService;

    @Test
    public void testScrollSearch() {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.termQuery("stuName.keyword", "王五"));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.size(2);

        PageResult<SchoolDomain> pageResult = baseElasticService.scrollSearch("school", sourceBuilder, SchoolDomain.class);
        System.out.println(JSON.toJSONString(pageResult));
        while (!CollectionUtils.isEmpty(pageResult.getData())) {
            pageResult = baseElasticService.scrollSearch(pageResult.getSpecialFlag(), SchoolDomain.class);
            System.out.println(JSON.toJSONString(pageResult));
        }
    }

    @Test
    public void testSearchAfter() {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.size(2).from(0).sort("_id");
        baseElasticService.searchAfter("school", sourceBuilder);
    }

}
