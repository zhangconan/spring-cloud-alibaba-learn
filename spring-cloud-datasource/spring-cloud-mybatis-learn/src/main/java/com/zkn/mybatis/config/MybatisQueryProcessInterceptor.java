package com.zkn.mybatis.config;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * @author conanzhang@木森
 * @description 对查询进行拦截
 * @date 8/11/21 4:34 PM
 * @classname MybatisQueryProcessInterceptor
 */
@Component
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = Statement.class))
public class MybatisQueryProcessInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        List<Object> resultList = (List<Object>) invocation.proceed();
        if (CollectionUtils.isEmpty(resultList)) {
            return resultList;
        }
        ResultSetHandler resultSetHandler = (ResultSetHandler) realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(resultSetHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
        //这里可以根据参数类型做各种的处理
        //根据MappedStatement中的resultMaps判断类型或者resultList判断类型，修改resultList中的值
        return resultList;
    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private Object realTarget(Object target) {

        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return target;
    }
}
