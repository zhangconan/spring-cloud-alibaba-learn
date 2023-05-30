package com.zkn.skywalking.consumer.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author conanzhang@木森
 * @description
 * @date 4/25/21 2:45 PM
 * @classname FeignUserService
 */
@FeignClient("${application.provider.name}")
public interface FeignUserService {

    /**
     * 用户名
     *
     * @param userName
     * @return
     */
    @GetMapping("user/name")
    String getUserName(@RequestParam String userName);
}
