package cn.mikulink.rabbitbot.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("redisBasic")
public class RedisBasic {
    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


    public <V> void putHash(String redisKey, String key, V value) {
        redisTemplate.opsForHash().put(redisKey, key, value);
    }

    public <T> T getHash(String redisKey, String key) {
       return (T) redisTemplate.opsForHash().get(redisKey, key);
    }

    public <T> T getRedisHash(String redisKey) {
        return (T) redisTemplate.opsForValue().get(redisKey);
    }

    public boolean hasKey(String redisKey) {
        return  redisTemplate.hasKey(redisKey);
    }


}
