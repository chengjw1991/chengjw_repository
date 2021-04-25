package com.cheng.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * @Author ChengJW
 * 2020/11/22/022
 */
public class JwtMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("-----jwtTokenMatcher-----");
        JwtToken jwtToken = (JwtToken) token;
        String web_token = (String) jwtToken.getPrincipal();
        String info_token = (String) info.getCredentials();
       // System.out.println(this.equals(web_token,info_token));
        return this.equals(web_token,info_token);
    }
}
