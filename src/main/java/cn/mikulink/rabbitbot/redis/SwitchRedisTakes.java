package cn.mikulink.rabbitbot.redis;



import cn.mikulink.rabbitbot.entity.SwitchEntity;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@Component("SwitchRedisTakes")
public class SwitchRedisTakes implements RedisBasicTakes<String,String, SwitchEntity>{
    @Resource(name="redisTemplate")
    private RedisTemplate redisTemplate;

    private Logger logger = Logger.getLogger(String.valueOf(SwitchRedisTakes.class));

    @Override
    public void add(String key, String value) {
        if(redisTemplate==null){
            logger.warning("redisTemplate 实例化失败");
            return;
        }
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void addObj(String objectKey, String key, SwitchEntity object) {
        if(redisTemplate==null){
            logger.warning("redisTemplate 实例化失败");
            return;
        }
        redisTemplate.opsForHash().put(objectKey,key,object);
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void delete(List<String> listKeys) {

    }

    @Override
    public void deletObj(String objecyKey, String key) {

    }

    @Override
    public void update(String key, String value) {

    }

    @Override
    public void updateObj(String objectKey, String key, SwitchEntity object) {

    }

    @Override
    public String get(String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }

    @Override
    public SwitchEntity getObj(String objectKey, String key) {
        return (SwitchEntity) redisTemplate.opsForHash().get(objectKey,key);
    }
}
