package com.dmarco.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dmarco
 */
public class ValidatorUtil {

    private static final Pattern MOBILE_PATTERN=Pattern.compile("1\\d{10}");

    public static boolean isMobile(String str){
        if(StringUtils.isEmpty(str)){
            return false;
        }
        Matcher m= MOBILE_PATTERN.matcher(str);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("12345678987"));
        System.out.println(isMobile("22345678987"));

    }
}
