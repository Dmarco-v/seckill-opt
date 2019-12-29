package com.dmarco.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Dmarco
 */
public class MD5Util {

    //固定salt
    private static final String salt="1a2b3c4d";


    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    public static String inputPassToFormPass(String inputpass){
        String str= ""+ salt.charAt(0)+salt.charAt(2)+ inputpass +salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String salt){
        String str= ""+ salt.charAt(0)+salt.charAt(2)+ formPass +salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String input, String saltDB){
        String formPass=inputPassToFormPass(input);
        String dbPass=formPassToDBPass(formPass,saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass(inputPassToFormPass("123456"),"1a2b3c4d"));

    }
}
