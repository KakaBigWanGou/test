package com.atguigu.utils;

/**
 * @author QKC
 * @create 2022-08-04-17:47
 */
public class StringUtils {

    //判断字符串是否为空
    public static boolean isEmpty(String str){
        return str==null || "".equals(str);
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }



}
