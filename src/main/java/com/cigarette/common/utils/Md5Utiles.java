package com.cigarette.common.utils;


import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author EdwardLee
 * @create 2021-08-09 20:03
 */
public class Md5Utiles {

    public static String getMd5Str(String str){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //return Base64.encodeBase64String(md5.digest((str + Constant.APP_SALT).getBytes()));
        return Base64.encodeBase64String(md5.digest((str).getBytes()));
    }
}
