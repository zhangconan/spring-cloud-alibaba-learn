package com.zkn.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.conan.shared.api.result.PageResult;
import com.zkn.elasticsearch.domain.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-02-23 22:20
 * @classname SchoolService
 */
@Slf4j
@Component
public class BaseElasticService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * @param idxName 索引名称
     * @param idxSQL  索引描述
     */
    public void createIndex(String idxName, String idxSQL) {
        try {
            if (!this.indexExist(idxName)) {
                log.error(" idxName={} 已经存在,idxSql={}", idxName, idxSQL);
                return;
            }
            CreateIndexRequest request = new CreateIndexRequest(idxName);
            buildSetting(request);
            request.mapping(idxSQL, XContentType.JSON);
            CreateIndexResponse res = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new RuntimeException("初始化失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BaseElasticService#createIndex，创建索引异常!", e);
        }
    }

    /**
     * 创建索引别名
     *
     * @param indexName 索引名称
     * @param aliasName 索引别名
     */
    public boolean createIndexAlias(String indexName, String aliasName) {

        boolean exists = indexAliasExists(indexName, aliasName);
        if (!exists) {
            IndicesAliasesRequest aliasesRequest = new IndicesAliasesRequest();
            IndicesAliasesRequest.AliasActions aliasActions =
                    new IndicesAliasesRequest.AliasActions(
                            IndicesAliasesRequest.AliasActions.Type.ADD)
                            .index(indexName).alias(aliasName);
            aliasesRequest.addAliasAction(aliasActions);
            try {
                return restHighLevelClient.indices().updateAliases(aliasesRequest, RequestOptions.DEFAULT).isAcknowledged();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("创建别名失败!aliasName:" + aliasName, e);
                return false;
            }
        }
        return true;
    }

    /**
     * 索引别名是否存在
     *
     * @param aliasName 索引别名
     * @param indexName 索引名称
     */
    public boolean indexAliasExists(String indexName, String aliasName) {

        GetAliasesRequest aliasesRequest = new GetAliasesRequest();
        aliasesRequest.indices(indexName);
        aliasesRequest.aliases(aliasName);
        try {
            GetAliasesResponse aliasesResponse = restHighLevelClient.indices().getAlias(aliasesRequest, RequestOptions.DEFAULT);
            return RestStatus.OK.equals(aliasesResponse.status());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("检查别名失败!aliasName:" + aliasName, e);
        }
        return false;
    }

    /**
     * 删除索引别名
     *
     * @param aliasName 索引别名
     * @param indexName 索引名称
     */
    public boolean deleteIndexAlias(String indexName, String aliasName) {
        boolean exists = indexAliasExists(indexName, aliasName);
        if (exists) {
            IndicesAliasesRequest aliasesRequest = new IndicesAliasesRequest();
            IndicesAliasesRequest.AliasActions aliasActions =
                    new IndicesAliasesRequest.AliasActions(
                            IndicesAliasesRequest.AliasActions.Type.REMOVE)
                            .index(indexName).alias(aliasName);
            aliasesRequest.addAliasAction(aliasActions);
            try {
                return restHighLevelClient.indices().updateAliases(aliasesRequest, RequestOptions.DEFAULT).isAcknowledged();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("删除别名失败!aliasName:" + aliasName, e);
                return false;
            }
        }
        return true;
    }

    /**
     * 根据索引别名获取索引
     *
     * @param aliasName
     * @return
     */
    public List<String> getIndexNameByAlias(String aliasName) {

        GetAliasesRequest aliasesRequest = new GetAliasesRequest();
        aliasesRequest.aliases(aliasName);
        try {
            GetAliasesResponse aliasesResponse = restHighLevelClient.indices().getAlias(aliasesRequest, RequestOptions.DEFAULT);
            if (RestStatus.OK.equals(aliasesResponse.status())) {
                Map<String, Set<AliasMetaData>> aliasMetaData = aliasesResponse.getAliases();
                if (!CollectionUtils.isEmpty(aliasMetaData)) {
                    List<String> indexNames = new ArrayList<>(aliasMetaData.size());
                    aliasMetaData.keySet().forEach(indexNames::add);
                    return indexNames;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("根据别名获取索引名称失败!", e);
            throw new RuntimeException("根据别名获取索引名称失败!");
        }
        return null;
    }

    /**
     * 断某个index是否存在
     *
     * @param idxName index名
     * @return boolean
     */
    public boolean indexExist(String idxName) throws Exception {
        GetIndexRequest request = new GetIndexRequest(idxName);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 断某个index是否存在
     *
     * @param idxName index名
     * @return boolean
     */
    public boolean isExistsIndex(String idxName) {

        try {
            return restHighLevelClient.indices().exists(new GetIndexRequest(idxName), RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("检查索引存在不存在异常!", e);
            throw new RuntimeException("检查索引存在不存在异常!");
        }
    }

    /**
     * 同步索引数据
     *
     * @param sourceIndex
     * @param destIndex
     * @return
     */
    public boolean reindex(String sourceIndex, String destIndex) {

        ReindexRequest reindexRequest = new ReindexRequest();
        reindexRequest.setSourceIndices(sourceIndex);
        reindexRequest.setDestIndex(destIndex);
        reindexRequest.setDestVersionType(VersionType.EXTERNAL);
        reindexRequest.setDestOpType("create");
        reindexRequest.setConflicts("proceed");
        try {
            BulkByScrollResponse response = restHighLevelClient.reindex(reindexRequest, RequestOptions.DEFAULT);
            return response.getStatus().getTotal() >= 0;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("同步索引数据出现异常!", e);
            return false;
        }
    }

    /**
     * putmapping
     *
     * @param indexName
     * @param mappings
     * @return
     */
    public boolean putMapping(String indexName, String mappings) {
        PutMappingRequest request = new PutMappingRequest(indexName);
        request.source(mappings, XContentType.JSON);
        try {
            return restHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT).isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("putMapping索引数据出现异常!", e);
            throw new RuntimeException("putMapping Exception!");
        }
    }

    /**
     * 刷新索引
     *
     * @param indexName
     * @return
     */
    public boolean refreshIndex(String indexName) {

        if (isExistsIndex(indexName)) {
            try {
                RefreshRequest request = new RefreshRequest(indexName);
                RefreshResponse response = restHighLevelClient.indices().refresh(request, RequestOptions.DEFAULT);
                return RestStatus.OK.equals(response.getStatus());
            } catch (IOException e) {
                e.printStackTrace();
                log.error("刷新索引出现异常!", e);
                throw new RuntimeException("刷新索引出现异常!");
            }
        }
        return true;
    }

    /**
     * 设置分片
     *
     * @param request
     */
    private void buildSetting(CreateIndexRequest request) {

        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 1));
    }

    /**
     * @param idxName index
     * @param entity  对象
     */
    public void insertOrUpdateOne(String idxName, BaseEntity entity) {
        IndexRequest request = new IndexRequest(idxName);
        log.error("Data : id={},entity={}", entity.getId(), JSON.toJSONString(entity.getData()));
        request.id(entity.getId());
        request.source(entity.getData(), XContentType.JSON);
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量插入数据
     *
     * @param idxName index
     * @param list    带插入列表
     */
    public void insertBatch(String idxName, List<BaseEntity> list) {

        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(idxName).id(item.getId())
                .source(item.getData(), XContentType.JSON)));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除
     *
     * @param idxName index
     * @param idList  待删除列表
     */
    public <T> void deleteBatch(String idxName, Collection<T> idList) {
        BulkRequest request = new BulkRequest();
        idList.forEach(item -> request.add(new DeleteRequest(idxName, item.toString())));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param idxName index
     * @param builder 查询参数
     * @param c       结果类对象
     */
    public <T> List<T> search(String idxName, SearchSourceBuilder builder, Class<T> c) {
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), c));
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 滚动分页查询
     *
     * @param indexName
     * @param builder
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> PageResult<T> scrollSearch(String indexName, SearchSourceBuilder builder, Class<T> clazz) {

        PageResult<T> pageResult = new PageResult<>();
        TimeValue timeValue = new TimeValue(2, TimeUnit.MINUTES);
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(builder);
        searchRequest.scroll(new Scroll(timeValue));
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                List<T> resultList = new ArrayList<>(searchHits.length);
                for (SearchHit searchHit : searchHits) {
                    resultList.add(JSON.parseObject(searchHit.getSourceAsString(), clazz));
                }
                pageResult.setData(resultList);
                pageResult.setSpecialFlag(searchResponse.getScrollId());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("scrollSearch出现异常!", e);
        }
        return pageResult;
    }

    public void searchAfter(String indexName, SearchSourceBuilder builder) {
        
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(builder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            while (searchHits.length > 0) {
                SearchHit last = searchHits[searchHits.length - 1];
                builder = builder.searchAfter(last.getSortValues());
                searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
                searchHits = searchResponse.getHits().getHits();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 滚动分页查询
     *
     * @param scrollId
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> PageResult<T> scrollSearch(String scrollId, Class<T> clazz) {

        PageResult<T> pageResult = new PageResult<>();
        TimeValue timeValue = new TimeValue(2, TimeUnit.MINUTES);
        SearchScrollRequest scrollRequest = new SearchScrollRequest()
                .scrollId(scrollId).scroll(timeValue);
        try {
            SearchResponse searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                List<T> resultList = new ArrayList<>(searchHits.length);
                for (SearchHit searchHit : searchHits) {
                    resultList.add(JSON.parseObject(searchHit.getSourceAsString(), clazz));
                }
                pageResult.setData(resultList);
                pageResult.setSpecialFlag(searchResponse.getScrollId());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("scrollSearch出现异常!", e);
        }
        return pageResult;
    }

    /**
     * 删除index
     *
     * @param idxName
     * @return void
     */
    public void deleteIndex(String idxName) {
        try {
            if (!this.indexExist(idxName)) {
                log.error(" idxName={}索引不存在！", idxName);
                return;
            }
            restHighLevelClient.indices().delete(new DeleteIndexRequest(idxName), RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param idxName
     * @param builder
     */
    public void deleteByQuery(String idxName, QueryBuilder builder) {

        DeleteByQueryRequest request = new DeleteByQueryRequest(idxName);
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 单字段聚合
     *
     * @param indexName
     * @param searchSourceBuilder
     * @param aggregationName
     * @return
     */
    public Map<String, Long> querySingleAggregation(String indexName, SearchSourceBuilder searchSourceBuilder, String aggregationName) throws Exception {

        //执行查询
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //解析返回数据，获取分组名称为aggs-class的数据
        Terms terms = searchResponse.getAggregations().get(aggregationName);
        if (terms != null) {
            List<Terms.Bucket> bucketList = (List<Terms.Bucket>) terms.getBuckets();
            Map<String, Long> dataMap = new HashMap<>(bucketList.size() << 1);
            for (Terms.Bucket bucket : bucketList) {
                dataMap.put(bucket.getKeyAsString(), bucket.getDocCount());
            }
            return dataMap;
        }
        return Collections.emptyMap();
    }

    /**
     * 聚合查询
     *
     * @param indexName
     * @param searchSourceBuilder
     */
    public Aggregations queryAggregation(String indexName, SearchSourceBuilder searchSourceBuilder) throws Exception {

        //执行查询
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.getAggregations();
    }
}
