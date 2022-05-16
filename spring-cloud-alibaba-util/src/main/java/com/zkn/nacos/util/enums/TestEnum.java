package com.zkn.nacos.util.enums;

import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: zhangconan
 * @Date: 2022/5/18 00:21
 * @Description:
 */
public class TestEnum {

    private static CachingMetadataReaderFactory CACHING_METADATA_READER_FACTORY = new CachingMetadataReaderFactory();

    @Test
    public void test() {

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
                    for (Object obj : objArray) {
                        System.out.println(((Enum) obj).name() + getCode.invoke(obj));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
