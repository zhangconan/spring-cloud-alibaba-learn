package com.zkn.reactor.fir.controller;

import com.conan.shared.api.result.BaseResult;
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
@RequestMapping(path = "eureka", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReactorFirController {

    @GetMapping("eurekaClient.json")
    public BaseResult eurekaClient() {
        BaseResult baseResult = new BaseResult();
        baseResult.setMessage("成功了");
        return baseResult;
    }
}
