package com.cheng.shiro;

import com.cheng.beans.Permission;
import com.cheng.beans.Roles;
import com.cheng.beans.UserInfo;
import com.cheng.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author ChengJW
 * 2020/11/21/021
 *  用户登录时，进行账号密码验证
 */
public class LoginRealm extends AuthorizingRealm {

    @Resource(name = "UserServiceImpl")
    private UserService service;

    /**
     *  判断token 类型，若是UsernamePasswordToken 则通过此验证
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        boolean isToken = (token instanceof UsernamePasswordToken)&&(!(token instanceof JwtToken));
        return isToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-----LoginRealmAuthentication-----");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (token == null){
            return  null;
        }
        String username = (String) token.getUsername(); //获取 用户名称
        //System.out.println(username);
        UserInfo user  =  service.SelectByName(username);
        String password = user.getPassword();
        //System.out.println(password+"--");
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,password,getName());
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取账号
        System.out.println("-----LoginRealmAuthorization-----");
        /**
         * 判断关键信息是 token 还是 userinfo
         */
        boolean isToken = principalCollection.getPrimaryPrincipal() instanceof String ? true : false;
        if (!isToken){
            UserInfo user = (UserInfo) principalCollection.getPrimaryPrincipal();
            List<Roles> roles = user.getRolesList();
            List<Permission> permissions = user.getPermList();
            List<String> roles_type = new ArrayList<>();
            for (Roles role : roles){
                String role_type = role.getType();
                roles_type.add(role_type);
            }
            List<String> permissions_code = new ArrayList<>();
            for (Permission perm : permissions){
                String perm_code = perm.getCode();
                permissions_code.add(perm_code);
            }
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addStringPermissions(permissions_code);
            info.addRoles(roles_type);
            return info;
        }else {
            return null;
        }
    }
}
