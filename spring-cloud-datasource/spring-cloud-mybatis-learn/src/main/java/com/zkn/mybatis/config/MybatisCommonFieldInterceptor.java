package com.zkn.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * @author conanzhang@木森
 * @description 做一些通用的拦截，比如加一些通用字段
 * @date 8/11/21 11:17 AM
 * @classname MybatisCommonFieldInterceptor
 */
@Slf4j
@Component
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class MybatisCommonFieldInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) realTarget(invocation.getTarget());
        //MetaObject是Mybatis里面获取对象值等信息的主要类
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //获取当前的执行sql语句
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        switch (mappedStatement.getSqlCommandType()) {
            case SELECT:
                sql = processQuerySql(sql);
                break;
            case INSERT:
                sql = processInsertSql(sql);
                break;
            case UPDATE:
                sql = processUpdateSql(sql);
                break;
            case DELETE:
                sql = processDeleteSql(sql);
                break;
            default:
        }
        metaObject.setValue("delegate.boundSql.sql", sql);
        return invocation.proceed();
    }

    /**
     * @param sql
     * @return
     */
    private String processDeleteSql(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            Delete delete = (Delete) statement;
            //这里的处理也比较简单，按需处理即可
            return delete.toString();
        } catch (JSQLParserException e) {
            log.info("解析sql异常!", e);
        }
        return sql;
    }

    /**
     * 处理更新语句
     *
     * @param sql
     * @return
     */
    private String processUpdateSql(String sql) {
        StringBuilder sb = new StringBuilder();
        //批量更新的场景
        String[] strArr = sql.split(";");
        for (String str : strArr) {
            try {
                Statement statement = CCJSqlParserUtil.parse(str);
                Update update = (Update) statement;
                //按需获取处理
                return update.toString();
            } catch (JSQLParserException e) {
                log.info("解析sql异常!", e);
            }
        }
        return sb.toString();
    }

    /**
     * 处理insert语句
     *
     * @param sql
     * @return
     */
    private String processInsertSql(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            Insert insert = (Insert) statement;
            Column column = new Column();
            column.setColumnName("字段名");
            insert.getColumns().add(column);
            //这里要注意insert分为 单个插入和批量插入两种情况
            if (insert.getItemsList() instanceof ExpressionList) {
                ((ExpressionList) insert.getItemsList()).getExpressions()
                        .add(new StringValue("具体的字段值"));
                //批量插入的情况
            } else if (insert.getItemsList() instanceof MultiExpressionList) {
                ((MultiExpressionList) insert.getItemsList()).getExprList()
                        .forEach(ele -> ele.getExpressions()
                                .add(new StringValue("具体的字段值")));
            }
            return insert.toString();
        } catch (JSQLParserException e) {
            log.info("解析sql异常!", e);
        }
        return sql;
    }

    /**
     * 处理查询语句
     *
     * @param sql
     * @return
     */
    private String processQuerySql(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            Select select = (Select) statement;
            SelectBody selectBody = select.getSelectBody();
            processSelectBody(selectBody);
            return selectBody.toString();
        } catch (JSQLParserException e) {
            log.info("解析sql异常!", e);
        }
        return sql;
    }

    /**
     * 处理select语句
     *
     * @param selectBody
     */
    private void processSelectBody(SelectBody selectBody) throws JSQLParserException {
//
//        if (selectBody instanceof PlainSelect) {
//            ((PlainSelect) selectBody).setWhere(new AndExpression(((PlainSelect) selectBody).getWhere(),
//                    CCJSqlParserUtil.parseCondExpression("new sql")));
//        } else if (selectBody instanceof WithItem) {
//            WithItem withItem = (WithItem) selectBody;
//            if (withItem.getSubSelect() != null) {
//                processSelectBody(withItem.getSubSelect());
//            }
//        } else {
//            SetOperationList operationList = (SetOperationList) selectBody;
//            List<SelectBody> selects = operationList.getSelects();
//            for (SelectBody select : selects) {
//                processSelectBody(select);
//            }
//        }
    }

    @Override
    public Object plugin(Object target) {

        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
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
