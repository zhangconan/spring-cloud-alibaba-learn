package com.zkn.reactor.fir.simple;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

/**
 * @author conanzhang@木森
 * @description
 * @date 4/15/21 1:45 PM
 * @classname SimpleReactorTest
 */
public class SimpleReactorTest {

    @Test
    public void testCreate() {

        Flux.create(skin -> {
            skin.next("zhangsan");
            skin.complete();
        }).subscribe(System.out::println);
    }
}
