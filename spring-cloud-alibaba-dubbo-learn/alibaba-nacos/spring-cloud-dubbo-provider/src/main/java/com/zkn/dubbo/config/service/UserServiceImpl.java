package com.zkn.dubbo.config.service;

import com.conan.shared.api.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author conanzhang@木森
 * @description 用户服务类
 * @date 2019-12-19 20:49
 * @classname UserServiceImpl
 */
@DubboService
public class UserServiceImpl implements UserService {

    /**
     * 用户名
     *
     * @param userName
     * @return
     */
    @Override
    public String getUserName(String userName) {

        return "provider_userName:" + userName;
    }
}
