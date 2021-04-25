package com.cheng.shiro;

import com.cheng.beans.Permission;
import com.cheng.beans.Roles;
import com.cheng.beans.UserInfo;
import com.cheng.service.UserService;
import com.cheng.token.JwtData;
import com.cheng.token.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/11/21/021
 *  用户登录后的请求 通过 checkrealm 进行token身份验证
 */
public class CheckRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizingRealm.class);

    @Resource(name = "UserServiceImpl")
    private UserService service;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-----CheckRealmAuthentication-----");
        JwtToken token = (JwtToken) authenticationToken;
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(),token.getCredentials(),getName());
        return info;
    }

    /**
     *  登录之后，前端请求携带token，在此处进行授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-----CheckRealmAuthorization-----");
        boolean IsToken = principalCollection.getPrimaryPrincipal() instanceof String ? true:false;
        if (IsToken){
            String jwttoken = (String) principalCollection.getPrimaryPrincipal();
            SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
            if (jwttoken != null){
                Claims claims = JwtUtil.ParseClaimsFromToken(jwttoken); // 解析token
                List<String> roles = (List<String>) claims.get(JwtData.JWT_ROLES);
                List<String> permissions = (List<String>) claims.get(JwtData.JWT_PERMISSION);
                info.addRoles(roles);
                info.addStringPermissions(permissions);
                return info;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
