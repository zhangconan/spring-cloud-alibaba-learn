package com.zkn.springmvc.learn.controller;

import com.zkn.springmvc.learn.controller.dto.MvcTestDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author conanzhang@木森
 * @description aop 控制类
 * @date 3/14/21 11:30 PM
 * @classname AopLearnController
 */
@RestController
@RequestMapping(path = "mvc", produces = MediaType.APPLICATION_JSON_VALUE)
public class MvcLearnController {

    /**
     * 测试数组
     *
     * @param dto
     * @return
     */
    @GetMapping("arrayStatus")
    public MvcTestDTO arrayTest(MvcTestDTO dto) {

        return dto;
    }

    /**
     * 测试嵌套对象
     *
     * @param dto
     * @return
     */
    @GetMapping("nestUser")
    public MvcTestDTO nestUser(MvcTestDTO dto) {

        return dto;
    }
}
