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

    public <T> T get(String key,Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis=jedisPool.getResource();
            String str= jedis.get(key);
            T t=stringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> Boolean set(String key,T value){
        Jedis jedis = null;
        try{
            jedis=jedisPool.getResource();
            String str=beanToString(value);
            if(str==null || str.length()<=0){
                return false;
            }
            jedis.set(key,str);
            return true;
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
