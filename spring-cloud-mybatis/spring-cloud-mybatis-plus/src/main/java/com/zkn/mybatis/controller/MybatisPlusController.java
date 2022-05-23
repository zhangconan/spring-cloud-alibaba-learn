package com.zkn.mybatis.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zkn.mybatis.dao.UserMapper;
import com.zkn.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-03-13 21:37
 * @classname ZipkinClientController
 */
@RestController
@RequestMapping(path = "mp", produces = MediaType.APPLICATION_JSON_VALUE)
public class MybatisPlusController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("getUserInfo")
    public User getUserInfo(@RequestParam String userId) {

        Wrapper<User> wrapper = Wrappers.lambdaQuery(User.class).eq(User::getId, userId);
        return userMapper.selectOne(wrapper);
    }
}
