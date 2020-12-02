package com.zkn.datasource.controller;

import com.conan.shared.api.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-03-13 21:37
 * @classname ZipkinClientController
 */
@RestController
@RequestMapping(path = "datasource", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MultiDatasourceController {


}
