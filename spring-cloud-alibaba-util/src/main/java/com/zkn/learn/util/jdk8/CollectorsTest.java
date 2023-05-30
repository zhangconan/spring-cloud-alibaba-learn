package com.zkn.learn.util.jdk8;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author conanzhang
 * @date 2022/11/9-12:33 PM
 * @classname CollectorsTest
 * @description
 */
public class CollectorsTest {

    @Test
    public void testGroupingBy() {
        List<DataEnhance> dataEnhanceList = new ArrayList<DataEnhance>() {
            {
                DataEnhance dataEnhance = new DataEnhance();
                dataEnhance.setBinderBean("test");
                dataEnhance.setPairData(new ArrayList<PairData>() {{
                    PairData pairData = new PairData();
                    pairData.setName("name1");
                    pairData.setValue("value1");
                    add(pairData);
                    pairData = new PairData();
                    pairData.setName("name2");
                    pairData.setValue("value2");
                    add(pairData);
                }});
                add(dataEnhance);
                dataEnhance = new DataEnhance();
                dataEnhance.setBinderBean("test");
                dataEnhance.setPairData(new ArrayList<PairData>() {{
                    PairData pairData = new PairData();
                    pairData.setName("name3");
                    pairData.setValue("value3");
                    add(pairData);
                }});
                add(dataEnhance);
                DataEnhance dataEnhance1 = new DataEnhance();
                dataEnhance1.setBinderBean("test1");
                dataEnhance1.setPairData(new ArrayList<PairData>() {{
                    PairData pairData = new PairData();
                    pairData.setName("name11");
                    pairData.setValue("value11");
                    add(pairData);
                }});
                add(dataEnhance1);
            }
        };
        List<DataEnhance> enhanceList = dataEnhanceList.stream().collect(Collectors
                .groupingBy(DataEnhance::getBinderBean,
                        Collectors.toList())).entrySet().stream().map(ele -> {

            DataEnhance dataEnhance = ele.getValue().stream().reduce(new DataEnhance(), (key, value) -> {
                if (CollectionUtils.isEmpty(key.getPairData())) {
                    key.setPairData(new ArrayList<>());
                }
                key.setBinderBean(value.getBinderBean());
                key.getPairData().addAll(value.getPairData());
                return key;
            });
            return dataEnhance;
        }).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(enhanceList));
    }


}

/**
 * 返回数据增强配置项
 */
@Data
class DataEnhance {
    private String binderBean;
    private List<PairData> pairData;
}

/**
 * 自定义参数键值对
 */
@Data
class PairData {

    private String name;

    private String value;
}