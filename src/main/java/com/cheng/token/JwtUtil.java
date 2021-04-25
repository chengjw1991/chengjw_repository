package com.cheng.token;

import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @Author ChengJW
 * 2020/11/21/021
 *
 * token 工具类
 */
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    private static String securityKey = JwtData.getSecurityKey();   //秘钥
    private static long acceptTokenValidate = JwtData.getAcceptTokenValidDate();  //token时长
    private static long refreshTokenValidate = JwtData.getRefreshTokenValidDate(); //刷新token时长
    private static String issuer = JwtData.getIssuer(); //签发人

    public static String CreateBase64SecurityKey(String secretkey){
        Base64 base64 = new Base64();
        String secretkey_64 = null;
        try {
             secretkey_64 = base64.encodeAsString(secretkey.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException",e);
        }
        return secretkey_64;
    }
    /**
     *  创建一个基础的token
     * @param secretKey 秘钥
     * @param signer    签发人
     * @param claims    身份信息集合
     * @param subject    主体，我们这里存放的是userid，使用的是UUID自动生成
     * @param millisecond  有效时长
     * @return
     */
    public static String CreateBasicToken(String secretKey, String signer, Map<String,Object> claims, String subject,long millisecond){

        //对秘钥进行base64url编码
        String secretkey_base64 = CreateBase64SecurityKey(secretKey);
        //获取当前时间/签发日期
        long seconds = System.currentTimeMillis();
        Date issdate = new Date();
        //有效期截止时间
        long valiseconds = seconds+millisecond;
        Date validdate = new Date(valiseconds);
        //设置token头部信息
        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ","JWT");
        if(!StringUtils.isEmpty(signer)){
            builder.setIssuer(signer);
        }
        if (!StringUtils.isEmpty(subject)){
            builder.setSubject(subject);
        }
        if (claims != null){
            builder.setClaims(claims);
        }
        if (millisecond>0){
            builder.setExpiration(validdate);
        }
        builder.setIssuedAt(issdate);
        builder.signWith(SignatureAlgorithm.HS256,secretkey_base64);
        return builder.compact();
    }

    /**
     * 创建有效的 token
     * @param subject
     * @param claims
     * @return
     */
    public static String CreateAccepteToken(String subject,Map<String,Object> claims){
        return CreateBasicToken(securityKey,issuer,claims,subject,acceptTokenValidate);
    }

    /**
     * 创建 刷新 token
     * @param subject
     * @param claims
     * @return
     */
    public static String CreaterefreshToken(String subject,Map<String,Object> claims){
        return CreateBasicToken(securityKey,issuer,claims,subject,refreshTokenValidate);
    }

    /**
     *  解析 token 获取 claims
     * @param token
     * @return
     */
    public static Claims ParseClaimsFromToken(String token){
        JwtParser parser = Jwts.parser();
        Claims claims = parser.setSigningKey(CreateBase64SecurityKey(securityKey)).parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 从token中解析userid
     * @param token
     * @return
     */
    public static String ParseUserId(String token){
        Claims claims = ParseClaimsFromToken(token);
        String userid = (String) claims.get(JwtData.JWT_USERID);
        return userid;
    }
    /**
     * 判断 token 是否失效
     * @param token
     * @return
     */
    public static boolean IsTokenExpiration(String token){
        Date validDate = ParseClaimsFromToken(token).getExpiration();
        long validMilliSecond = validDate.getTime();
        long currentMilliSecond = System.currentTimeMillis();
        if (validMilliSecond>currentMilliSecond){
            return false;
        }else {
            return true;
        }
    }
    /**
     *  判断token是否有效
     * @param token
     * @return
     */
    public static boolean IsTokenValid(String token){
        if (!IsTokenExpiration(token)&&token != null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取token剩余时间
     * @param token
     * @return
     */
    public static long ResidualTime(String token){
        if (IsTokenValid(token)){
            Date validDate = ParseClaimsFromToken(token).getExpiration();
            long validMilliSecond = validDate.getTime();
            long currentMilliSecond = System.currentTimeMillis();
            return validMilliSecond-currentMilliSecond;
        }else {
            return 0;
        }
    }
}
