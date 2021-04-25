package com.cheng.shiro;

import com.cheng.beans.Permission;
import com.cheng.beans.Roles;
import com.cheng.beans.UserInfo;
import com.cheng.service.Impl.UserServiceImpl;
import com.cheng.service.UserService;
import com.cheng.token.JwtData;
import com.cheng.token.JwtUtil;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ChengJW
 * 2020/11/21/021
 *  自定义一个 jwttoken过滤器
 */
public class JwtAuthFilter extends AccessControlFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);

    /**
     * 判断请求是否可以通过，直接去找controller，true表示可以，false表示需要进一步验证，进入 onAccessDenied
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     *  对 isAccessAllowed 传过来的请求进行验证。true表示允许允许访问，false表示直接将错误信息响应给前端
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        System.out.println("-----JwtAuthFilter-----");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
       //获取请求携带的 token
        String req_token = req.getHeader(JwtData.JWT_NAME);
        //判断 token 是否为空
        if(!StringUtils.isEmpty(req_token)){
            //判断是否有效
            try {
                //如果有效，则刷新token，这样前端就能无感一直使用
                String refreshToken = getRefreshToken(req_token); // 创建刷新token
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                resp.setContentType("application/html;charset=UTF-8");
                resp.setHeader(JwtData.JWT_NAME,refreshToken);  //将刷新token放入响应信息的头部返回给前端
                // 请求重新登录
                getSubject(servletRequest,servletResponse).login(new JwtToken(req_token));
                return true;
            } catch (ExpiredJwtException e) {
                LOGGER.error("ExpiredJwtException...{}"+e);
                ResponseData(DataResult.getDataResult(CodeAndMsgImpl.ExpiredJwtException),servletResponse);
            }
        }else {
            ResponseData(DataResult.getDataResult(CodeAndMsgImpl.TokenIsNull),servletResponse);
        }
        return false;
    }

    /**
     *  通过传过来的有效的token，创建刷新token
     * @param token
     * @return
     */
    public static String getRefreshToken(String token){
        Claims claims = JwtUtil.ParseClaimsFromToken(token);
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put(JwtData.JWT_USERID,claims.get(JwtData.JWT_USERID));
        map.put(JwtData.JWT_ROLES,claims.get(JwtData.JWT_ROLES));
        map.put(JwtData.JWT_PERMISSION,claims.get(JwtData.JWT_PERMISSION));
        String refreshToken = JwtUtil.CreaterefreshToken((String) claims.get(JwtData.JWT_USERID),map);
        return refreshToken;
    }
    public static void ResponseData(DataResult result,ServletResponse response){
        HttpServletResponse resp = (HttpServletResponse) response;
        //设置响应格式
        response.setContentType("application/json;charset=UTF-8");
        //设置响应信息,将对象转换成json字符串
        ObjectMapper mapper = new ObjectMapper();
        try {
            String data = mapper.writeValueAsString(result); // 将对象转换成json字符串
            PrintWriter stream = resp.getWriter(); //获取输出流
            stream.write(data,0,data.length()); //将错误信息响应给前端
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonProcessingException...{}"+e);
        } catch (IOException e){
            LOGGER.error("IOException...{}"+e);
        }
    }
}
