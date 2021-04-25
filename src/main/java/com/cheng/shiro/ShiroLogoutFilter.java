package com.cheng.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author ChengJW
 * 2020/11/30/030
 * 退出过滤器
 */
public class ShiroLogoutFilter extends LogoutFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("-----ShiroLogoutFilter-----");
        // 获取主体信息
        Subject subject = getSubject(request,response);
        String redirectUrl = getRedirectUrl(request,response,subject);
        subject.logout();
        issueRedirect(request,response,redirectUrl);
        return false;
    }
}
