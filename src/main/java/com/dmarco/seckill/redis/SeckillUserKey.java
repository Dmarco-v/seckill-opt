package com.dmarco.seckill.redis;

/**
 * @author Dmarco
 */
public class SeckillUserKey extends  BasePrefix{

    private static final int TOKEN_EXPIRE=3600 * 24 *2;


    public SeckillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SeckillUserKey token =new SeckillUserKey(TOKEN_EXPIRE,"tk");

}
