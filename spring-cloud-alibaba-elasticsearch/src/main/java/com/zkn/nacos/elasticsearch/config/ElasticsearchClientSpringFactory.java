package com.zkn.nacos.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-02-23 23:51
 * @classname ElasticsearchClientSpringFactory
 */
public class ElasticsearchClientSpringFactory {

    private final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClientSpringFactory.class);

    public static int CONNECT_TIMEOUT_MILLIS = 1000;
    public static int SOCKET_TIMEOUT_MILLIS = 30000;
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;
    public static int MAX_CONN_PER_ROUTE = 10;
    public static int MAX_CONN_TOTAL = 30;

    private static HttpHost[] HTTP_HOST;
    private RestClientBuilder builder;
    private RestClient restClient;
    private RestHighLevelClient restHighLevelClient;

    private static ElasticsearchClientSpringFactory elasticsearchClientSpringFactory = new ElasticsearchClientSpringFactory();

    private ElasticsearchClientSpringFactory() {
    }

    public static ElasticsearchClientSpringFactory build(HttpHost[] httpHostArray,
                                                         Integer maxConnectNum, Integer maxConnectPerRoute) {
        HTTP_HOST = httpHostArray;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        return elasticsearchClientSpringFactory;
    }

    public static ElasticsearchClientSpringFactory build(HttpHost[] httpHostArray, Integer connectTimeOut, Integer socketTimeOut,
                                                         Integer connectionRequestTime, Integer maxConnectNum, Integer maxConnectPerRoute) {
        HTTP_HOST = httpHostArray;
        CONNECT_TIMEOUT_MILLIS = connectTimeOut;
        SOCKET_TIMEOUT_MILLIS = socketTimeOut;
        CONNECTION_REQUEST_TIMEOUT_MILLIS = connectionRequestTime;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        return elasticsearchClientSpringFactory;
    }

    /**
     * 初始化方法
     */
    public void init() {
        builder = RestClient.builder(HTTP_HOST);
        setConnectTimeOutConfig();
        setMutiConnectConfig();
        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(builder);
        LOGGER.info("init factory" + Arrays.toString(HTTP_HOST));
    }

    /**
     * 配置连接时间延时
     */
    public void setConnectTimeOutConfig() {
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestConfigBuilder;
        });
    }

    /**
     * 使用异步httpclient时设置并发连接数
     */
    public void setMutiConnectConfig() {

        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            return httpClientBuilder;
        });
    }

    public RestClient getClient() {
        return restClient;
    }

    public RestHighLevelClient getRhlClient() {
        return restHighLevelClient;
    }

    public void close() {
        if (restClient != null) {
            try {
                restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("close client");
    }
}
