package com.zkn.learn.util.enums;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: zhangconan
 * @Date: 2022/5/18 00:21
 * @Description:
 */
public class TestEnum {

    private static CachingMetadataReaderFactory CACHING_METADATA_READER_FACTORY = new CachingMetadataReaderFactory();
    /**
     * 缓存枚举信息
     */
    private static Map<String, Map<String, String>> CACHE_ENUM = new ConcurrentHashMap<>();

    private AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    private Map<String, Map<String, String>> test() {
        if (Boolean.TRUE.equals(atomicBoolean.get())) {
            return CACHE_ENUM;
        }
        try {
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(new
                    DefaultResourceLoader()).getResources(
                    ResourceUtils.CLASSPATH_URL_PREFIX + "*/**/enums/**/*.class");
            for (Resource resource : resources) {
                //这里使用Spring提供带相关累，带缓存
                MetadataReader metadataReader = CACHING_METADATA_READER_FACTORY.getMetadataReader(resource);
                //这里用Spring的工具栏，带缓存
                Class clazz = ClassUtils.forName(metadataReader.getClassMetadata().getClassName(), null);
                //如果是枚举类
                if (clazz.isEnum()) {
                    Method getCode = clazz.getMethod("getCode");
                    Object[] objArray = clazz.getEnumConstants();
                    Map<String, String> tempMap = new HashMap<>();
                    for (Object obj : objArray) {
                        tempMap.put(((Enum) obj).name(), (String) getCode.invoke(obj));
                    }
                    CACHE_ENUM.put(clazz.getSimpleName(), tempMap);
                }
            }
            atomicBoolean.set(true);
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return CACHE_ENUM;
    }
}
