package com.zkn.nacos.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-08-16 14:44
 * @classname EchoFeignService
 */
@FeignClient("${feign.application.name}")
public interface EchoFeignService {
    /**
     * 输出姓名
     *
     * @param name
     * @return
     */
    @GetMapping("GetName")
    String getName(@RequestParam("name") String name);
}
