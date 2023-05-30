package com.zkn.springcloud.gateway.controller;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

/**
 * @author conanzhang@木森
 * @description
 * @date 2019-09-05 00:00
 * @classname NormalController
 */
@RestController
@RequestMapping("normal")
public class NormalController {

    @SneakyThrows
    @PostMapping("name")
    public String name(HttpServletRequest request) {
        System.out.println("#########普通post##############");
        System.out.println(request.getHeader("zhangsan"));
        System.out.println(request.getParameter("userInfo"));
        return "张三";
    }

    @SneakyThrows
    @PostMapping("nameForm")
    public String nameForm(HttpServletRequest request) {
        System.out.println("#########form表单##############");
        System.out.println(URLDecoder.decode(request.getHeader("address") == null ?
                "" : request.getHeader("address"), "UTF-8"));
        System.out.println(request.getHeader("zhangsan"));
        System.out.println(request.getParameter("status"));
        System.out.println(request.getParameter("address"));
        System.out.println(request.getParameter("userName"));
        System.out.println(request.getParameter("userInfo"));
        return "张三";
    }

    @SneakyThrows
    @PostMapping("nameJson")
    public String name(HttpServletRequest request, @RequestBody RequestParamInfo paramInfo) {
        System.out.println("#########json数据##############");
        System.out.println(URLDecoder.decode(request.getHeader("address"), "UTF-8"));
        return JSON.toJSONString(paramInfo);
    }

    @DeleteMapping("delete")
    public String delete(HttpServletRequest request) {

        return "delete";
    }

    @PatchMapping("patch")
    public String patch(HttpServletRequest request) {
        System.out.println(request.getParameter("userName"));
        return "patch";
    }

    @SneakyThrows
    @PatchMapping("nameJson")
    public String patchNameJson(HttpServletRequest request, @RequestBody RequestParamInfo paramInfo) {
        System.out.println("#########json数据##############");
        System.out.println(URLDecoder.decode(request.getHeader("address"), "UTF-8"));
        return JSON.toJSONString(paramInfo);
    }


    @Data
    public static class RequestParamInfo {

        private List<Integer> status;

        private UserInfo userInfo;
    }

    @Data
    public static class UserInfo {
        /**
         * 用户名
         */
        private String userName;
        /**
         * 地址
         */
        private String address;
    }
}
