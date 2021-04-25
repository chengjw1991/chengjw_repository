package com.cheng.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @Author ChengJW
 * 2020/11/24/024
 * 自定义一个角色权限验证器，当有多个角色都具有某个权限时，只要用户拥有其中一个角色，便可访问
 */
public class RolesAuthFilter extends RolesAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        //获取主体
        Subject subject = getSubject(request,response);
        //获取配置的过虑链的映射表中role的列表
        String [] roles = (String[]) mappedValue;
        // 当roles列表为空，表示没有role限制
        if (roles == null || roles.length == 0){
            return true;
        }
        for(String rolesName : roles){
            if (subject.hasRole(rolesName)){
                return true;
            }else {
                return false;
            }
        }
       return false;
    }
}
