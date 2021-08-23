package com.zkn.skywalking.consumer.controller;

import com.conan.shared.api.result.PlaintDataResult;
import com.zkn.skywalking.consumer.feign.service.FeignUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-03-13 22:08
 * @classname EurekaClientController
 */
@RestController
@RequestMapping(path = "consumer", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsumerFirController {

    @Autowired
    private FeignUserService feignUserService;

    @GetMapping("user/name")
    public PlaintDataResult<String> userName(String userName) {
        PlaintDataResult<String> baseResult = new PlaintDataResult<>();
        baseResult.setData(feignUserService.getUserName(userName));
        return baseResult;
    }
}
