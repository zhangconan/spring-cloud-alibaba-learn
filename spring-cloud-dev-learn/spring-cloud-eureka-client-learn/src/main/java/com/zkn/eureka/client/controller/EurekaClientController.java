package com.zkn.eureka.client.controller;

import com.conan.shared.api.result.BaseResult;
import com.zkn.eureka.client.service.FeignTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-03-13 22:08
 * @classname EurekaClientController
 */
@RestController
@RequestMapping(path = "eureka", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EurekaClientController {

    @Autowired
    private FeignTestService feignTestService;

    @GetMapping("eurekaClient")
    public BaseResult eurekaClient() {

        return feignTestService.eurekaClient();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        IntStream.range(0, 100000).forEach(ele -> System.out.println(UUID.randomUUID().toString()));
        System.out.println(System.currentTimeMillis() - start);
    }
}
