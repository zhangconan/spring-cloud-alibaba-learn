package com.zkn.mybatis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkn.mybatis.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author conanzhang
 * @date 2022/5/22-7:34 PM
 * @classname UserMapper
 * @description
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
