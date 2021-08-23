package com.zkn.mybatis.controller;

import com.zkn.mybatis.dao.first.FirstDAO;
import com.zkn.mybatis.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-03-13 21:37
 * @classname ZipkinClientController
 */
@RestController
@RequestMapping(path = "datasource", produces = MediaType.APPLICATION_JSON_VALUE)
public class MultiDatasourceController {

    @Autowired
    private FirstDAO firstDAO;

    @GetMapping("test")
    public String test(Long id) {

        UserInfo userInfo = firstDAO.selectById(id);
        if (userInfo != null) {
            return userInfo.getUserName();
        }
        return "default";
    }
}
