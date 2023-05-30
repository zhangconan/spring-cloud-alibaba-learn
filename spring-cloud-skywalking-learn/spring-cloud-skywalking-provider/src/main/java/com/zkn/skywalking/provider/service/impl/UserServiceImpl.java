package com.zkn.skywalking.provider.service.impl;

import com.conan.shared.api.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author conanzhang@木森
 * @description
 * @date 4/25/21 2:43 PM
 * @classname UserServiceImpl
 */
@RestController
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserServiceImpl implements UserService {
    /**
     * 用户名
     *
     * @param userName
     * @return
     */
    @Override
    @GetMapping("name")
    public String getUserName(String userName) {
        return null;
    }
}
