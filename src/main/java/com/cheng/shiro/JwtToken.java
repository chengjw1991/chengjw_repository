package com.cheng.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Author ChengJW
 * 2020/11/21/021
 */
public class JwtToken extends UsernamePasswordToken {

    private String token;

    public JwtToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
