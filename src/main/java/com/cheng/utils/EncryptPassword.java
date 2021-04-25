package com.cheng.utils;

import org.apache.shiro.crypto.hash.Md5Hash;


/**
 * @Author ChengJW
 * 2020/11/21/021
 * 采用 M5对密码进行加密，同时加盐
 */
public class EncryptPassword {

    private static final String salt = "chengjw";

    //加密
    public static String Encrypt(String password){
        Md5Hash md5 = new Md5Hash(password,salt);
        return md5.toString();
    }
}
