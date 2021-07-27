package com.kingjakeu.lolesports.api.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class HashMappingTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void writeHash() {
        redisTemplate.opsForHash().put("gameId:1", "1", "HLE chovy" );

    }

    @Test
    void loadHash() {
        System.out.println(redisTemplate.opsForHash().get("gameId:1", "1"));
    }
}