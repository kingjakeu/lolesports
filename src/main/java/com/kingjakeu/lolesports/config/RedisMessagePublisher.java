package com.kingjakeu.lolesports.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessagePublisher implements MessagePublisher{

    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;

    @Override
    public void publish(String message) {
        this.redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
