package com.zkn.eureka.client.service;

import com.conan.shared.api.result.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-06-04 17:39
 * @classname FeignTestService
 */
@FeignClient("${spring.feign.name}")
public interface FeignTestService {

    /**
     * 客户端测试
     *
     * @return
     */
    @GetMapping("eureka/eurekaClient.json")
    BaseResult eurekaClient();
}
