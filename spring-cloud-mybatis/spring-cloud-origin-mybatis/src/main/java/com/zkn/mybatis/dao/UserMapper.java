package com.zkn.mybatis.dao;

import com.zkn.mybatis.model.CombineUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author conanzhang
 * @date 2022/5/20-4:42 PM
 * @classname UserMapper
 * @description
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @return
     */
    List<CombineUserInfo> selectByUserId(String userId);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @return
     */
    List<CombineUserInfo> associationResult(String userId);
}
