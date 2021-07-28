package com.kingjakeu.lolesports.api.example;

import com.kingjakeu.lolesports.config.RedisMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPubSub {
    private final RedisMessagePublisher publisher;
    public void pub(){
        publisher.publish("hello world");
    }
}
