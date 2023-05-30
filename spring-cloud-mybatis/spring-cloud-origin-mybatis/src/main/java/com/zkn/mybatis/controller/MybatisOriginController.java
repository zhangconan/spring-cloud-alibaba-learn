package com.zkn.mybatis.controller;

import com.zkn.mybatis.dao.UserMapper;
import com.zkn.mybatis.model.CombineUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-03-13 21:37
 * @classname ZipkinClientController
 */
@RestController
@RequestMapping(path = "mybatis")
public class MybatisOriginController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户ID查询用户信息
     *
     * @return
     */
    @GetMapping("selectByUserId")
    public List<CombineUserInfo> selectByUserId(@RequestParam String userId) {

        return userMapper.selectByUserId(userId);
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @return
     */
    @GetMapping("associationResult")
    public List<CombineUserInfo> associationResult(@RequestParam String userId) {

        return userMapper.associationResult(userId);
    }

}
