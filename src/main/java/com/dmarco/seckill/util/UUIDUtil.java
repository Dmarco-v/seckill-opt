package com.dmarco.seckill.util;

import java.util.UUID;

/**
 * @author Dmarco
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
