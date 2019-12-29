package com.dmarco.seckill.redis;

/**
 * @author Dmarco
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
