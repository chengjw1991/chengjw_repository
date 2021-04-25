package com.cheng;

import com.cheng.utils.EncryptPassword;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

/**
 * @Author ChengJW
 * 2020/11/22/022
 */
public class test {

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        String scretkey = "123456";
        String salt = "chengjw";
        Md5Hash md5 = new Md5Hash(scretkey,salt);
       // System.out.println( md5.toString());

        String str = EncryptPassword.Encrypt("123456");
        System.out.println(str);


//        String str = "Solemn Statement: This system is independently developed by me, all rights reserved, and the offender will be prosecuted. Any problems encountered during use, please inform me";
//        String st = str.toUpperCase();
//        System.out.println(st);
    }

}
