package com.dmarco.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Dmarco
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    /**
     * 获取单个对象
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key,Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            String str= jedis.get(realKey);
            T t=stringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis = null;
        try{
            jedis=jedisPool.getResource();
            String str=beanToString(value);
            if(str==null || str.length()<=0){
                return false;
            }
            String realKey=prefix.getPrefix()+key;
            int expireSeconds=prefix.expireSeconds();
            if(expireSeconds<=0){
                jedis.set(realKey,str);
            }else{
                jedis.setex(realKey,expireSeconds,str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Boolean exists(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            jedis.exists(realKey);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if(value==null){
            return null;
        }
        Class<?> clazz=value.getClass();
        //由于value还可能是基本类型，因此这里不能简单的用instanceOf进行关键字判断
        if(clazz==int.class|| clazz==Integer.class){
            return ""+value;
        }else if(clazz==String.class){
            return (String) value;
        }else if(clazz==long.class|| clazz==Long.class){
            return ""+value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class<T> clazz) {
        if(str==null || str.length()<=0 || clazz==null){
            return null;
        }
        if(clazz==int.class|| clazz==Integer.class){
            return (T)Integer.valueOf(str);
        }else if(clazz==String.class){
            return (T)str;
        }else if(clazz==long.class|| clazz==Long.class){
            return (T)Long.valueOf(str);
        }else{
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis!=null){
            jedis.close();
        }
    }


}
