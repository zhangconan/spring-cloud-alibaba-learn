package com.zkn.nacos.config.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.client.config.listener.impl.AbstractConfigChangeListener;
import com.zkn.nacos.config.pojo.SchoolDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author conanzhang@木森
 * @description
 * @date 3/15/21 1:31 PM
 * @classname NacosConfigListener
 */
@Component
@EnableConfigurationProperties(SchoolDomain.class)
public class NacosConfigListener implements ApplicationRunner {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        nacosConfigManager.getConfigService().addListener("cloud-alibaba-nacos-config-learn", null,
                new AbstractConfigChangeListener() {
                    @Override
                    public void receiveConfigChange(ConfigChangeEvent event) {

                        System.out.println(JSON.toJSONString(event.getChangeItems()));
                    }
                });
    }
}
