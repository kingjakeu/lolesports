package com.kingjakeu.lolesports.api.live.service;

import com.kingjakeu.lolesports.api.live.domain.PlayerLiveStat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LiveStatRedisMapper {
    private final RedisTemplate redisTemplate;
    private HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();

    public void savePlayerHash(String gameId, Map<String, String> playerHash){
        this.redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        this.redisTemplate.opsForHash().putAll("gameId:"+gameId+":player", playerHash);
    }

    public List<String> getPlayerHashList(String gameId, Set<String> playerId){
        this.redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return this.redisTemplate.opsForHash().multiGet("gameId:"+gameId+":player", playerId);
    }

    public void savePlayerStatHash(String gameId, String playerId, PlayerLiveStat playerLiveStat){
        this.redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(PlayerLiveStat.class));
        this.redisTemplate.opsForHash().put("gameId:"+gameId+":player-stat", playerId, playerLiveStat);
    }

    public PlayerLiveStat getPlayerStatHash(String gameId, String playerId){
        this.redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(PlayerLiveStat.class));
        return (PlayerLiveStat) this.redisTemplate.opsForHash().get("gameId:"+gameId+":player-stat", playerId);
    }

    public void savePlayerDamageShare(String gameId, String playerId, Double damageShare){
        this.redisTemplate.opsForZSet().add("gameId:"+gameId+":player-damage", playerId, damageShare);
    }

    public Set<String> getPlayerDamageShare(String gameId){
        return this.redisTemplate.opsForZSet().range("gameId:"+gameId+":player-damage", 0, -1);
    }

    public Set<ZSetOperations.TypedTuple<String>> getPlayerDamageShareWithScore(String gameId){
        return this.redisTemplate.opsForZSet().rangeWithScores("gameId:"+gameId+":player-damage", 0, -1);
    }
}
