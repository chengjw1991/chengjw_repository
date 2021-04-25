package com.cheng.shiro;

import com.cheng.utils.EncryptPassword;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @Author ChengJW
 * 2020/11/21/021
 *
 * 自定义一个 密码验证的方法
 */
public class PsswordMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo info) {
        System.out.println("密码匹配检测-----");
        // 获取token中的password，并将pasword 进行 md5 +盐 加密
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String password = String.valueOf(token.getPassword());
        String encryptpassword = EncryptPassword.Encrypt(password);
        System.out.println("token--"+encryptpassword);
        // 获取info 中的的password
        String basepassword = (String) info.getCredentials();
        System.out.println("info--"+basepassword);
        return this.equals(basepassword,encryptpassword);
    }
}
