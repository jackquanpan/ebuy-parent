package com.java.utils;

import java.security.MessageDigest;
import java.util.Random;

/**
 * MD5加密 单项加密  没有一项加密算法是安全的
 * 明文--->密文
 *MessageDigest 明文变成16个长度的字节数组   目标32位
 */
public class MD5Too {
    private static final String[]digital={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    public static String initMd5(String tx) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        if(tx==null){
            throw new Exception("亲，您眼瞎嘛，明文为null");
        }
        byte[] bytes = md5.digest(tx.getBytes("UTF-8"));
        StringBuilder builder=new StringBuilder("");

        for (byte aByte : bytes) {
            int num=aByte;
            if(num<0){
                num+=256;
            }
            int index1=num/16;
            int index2=num%16;
            builder.append(digital[index1]).append(digital[index2]);
        }
        return builder.toString();
    }
    public static String Md5(String txt) throws Exception {
        String miWen =initMd5(initMd5(initMd5 (initMd5(txt) + "JACK")+"JACK")+"JACK");
        return miWen;
    }

    public static void main(String[] args) throws Exception {
       /* String s = Md5("123456");
        System.out.println(s);*/
        int i = new Random().nextInt(1000000);
        System.out.println(i);
    }


}
