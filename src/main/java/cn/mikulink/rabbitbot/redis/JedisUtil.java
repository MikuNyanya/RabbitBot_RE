package cn.mikulink.rabbitbot.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisUtil {
    private static JedisPool jedisPool;
    //使用静态代码块加载配置文件
    static {
        //读取配置文件
        InputStream resourceAsStream
                = JedisUtil.class.getClassLoader().getResourceAsStream("jedis.properties");
        //创建properties对象
        Properties properties = new Properties();
        //关联文件
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建配置文件对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        jedisPoolConfig.setMaxIdle(Integer.parseInt("maxIdle"));
        //初始化jedispool
        jedisPool=new JedisPool(jedisPoolConfig,properties.getProperty("host"),Integer.parseInt(properties.getProperty("port")));

    }
    //获取连接
    public static  JedisPool getJedisPool(){
        return  jedisPool;
    }
}
