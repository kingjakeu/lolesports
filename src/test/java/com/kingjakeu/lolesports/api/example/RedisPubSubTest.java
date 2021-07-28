package com.kingjakeu.lolesports.api.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RedisPubSubTest {

    @Autowired
    private RedisPubSub redisPubSub;
    @Test
    void pub() {
        redisPubSub.pub();;
    }
}