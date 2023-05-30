package com.zkn.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
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
    private static String USER_NAME;
    private static String PASSWORD;

    private static ElasticsearchClientSpringFactory elasticsearchClientSpringFactory = new ElasticsearchClientSpringFactory();

    private ElasticsearchClientSpringFactory() {
    }

    public static ElasticsearchClientSpringFactory build(HttpHost[] httpHost,
                                                         Integer maxConnectNum, Integer maxConnectPerRoute, String userName, String password) {
        HTTP_HOST = httpHost;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        USER_NAME = userName;
        PASSWORD = password;
        return elasticsearchClientSpringFactory;
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
        setCredentialsProvider();
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

    /**
     * 设置密码以及SSL
     */
    private void setCredentialsProvider() {

        if (StringUtils.hasText(USER_NAME) && StringUtils.hasText(PASSWORD)) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(USER_NAME, PASSWORD));
            builder.setHttpClientConfigCallback(httpClientBuilder -> {
                SSLContext sslContext;
                try {
                    //信任所有证书
                    sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
                    //信任所有host的ssl
                    httpClientBuilder.setSSLHostnameVerifier((s, sslSession) -> true);
                    httpClientBuilder.setSSLContext(sslContext);
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                return httpClientBuilder;
            });
        }
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
