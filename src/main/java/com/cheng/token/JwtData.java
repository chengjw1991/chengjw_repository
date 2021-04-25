package com.cheng.token;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author ChengJW
 * 2020/11/21/021
 */
@Component
@PropertySource("classpath:/TokenData.properties")
public class JwtData {

    /**
     *  存放token的一些基本信息
     */
    @ApiModelProperty(value = "秘钥")
    private static String securityKey;
    @ApiModelProperty(value = "token有效期")
    private static long acceptTokenValidDate;
    @ApiModelProperty(value = "刷新token有消息")
    private static long refreshTokenValidDate;
    @ApiModelProperty(value = "签发人")
    private static String issuer;

    /**
     *  前后端统一的token信息
     */
    public static final String JWT_NAME = "Token";
    public static final String JWT_ROLES = "ROLES";
    public static final String JWT_PERMISSION ="PERMISSION";
    public static final String JWT_USERID = "USERID";

    public static String getSecurityKey() {
        return JwtData.securityKey;
    }

    @Value("${jwt.securityKey}")
    public  void setSecurityKey(String securityKey) {
        JwtData.securityKey = securityKey;
    }

    public static long getAcceptTokenValidDate() {
        return acceptTokenValidDate;
    }

    @Value("${jwt.acceptTokenValidate}")
    public  void setAcceptTokenValidDate(long acceptTokenValidDate) {
        JwtData.acceptTokenValidDate = acceptTokenValidDate;
    }

    public static long getRefreshTokenValidDate() {
        return refreshTokenValidDate;
    }
    @Value("${jwt.refreshTokenValidate}")
    public  void setRefreshTokenValidDate(long refreshTokenValidDate) {
        JwtData.refreshTokenValidDate = refreshTokenValidDate;
    }

    public static String getIssuer() {
        return issuer;
    }
    @Value("${jwt.issuer}")
    public  void setIssuer(String issuer) {
        JwtData.issuer = issuer;
    }
}
