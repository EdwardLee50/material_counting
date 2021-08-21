package com.cigarette.common.utils;

import com.alibaba.fastjson.JSON;
import com.cigarette.controller.viewObject.UserReturnVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author EdwardLee
 * @create 2021-08-10 19:31
 */
public class JwtUtils {

    /**
     * token过期时间
     */
    public static final long DEFAULT_EXPIRE = 1000 * 60 * 60 * 24;

    /**
     * 秘钥
     */
    public static final String DEFAULT_APP_SECRET = Constant.APP_SECRET_KEY;

    public static String getJwtToken(Object obj, Long expireTime, String appSecret) {
        String JwtToken = Jwts.builder()
                // header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // 设置过期时间
                .setSubject("material_counting")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                // 设置token主体部分 ，存储用户信息
                .claim("MC_TOKEN_OBJECT", obj)
                // 根据算法和密钥进行编码
                .signWith(SignatureAlgorithm.HS256, appSecret)
                .compact();
        return JwtToken;
    }

    /**
     * 生成token字符串的方法
     *
     * @return
     */
    public static String getJwtToken(Object obj) {
        return getJwtToken(obj, DEFAULT_EXPIRE, DEFAULT_APP_SECRET);
    }

    /**
     * 根据设置的密钥判断token是否存在与有效
     *
     * @param jwtToken 传入的token
     * @return 判断是否有效，若无效（伪造等），返回false，否则返回true
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(DEFAULT_APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据设置的密钥判断token是否存在与有效
     *
     * @param request 保存header的request
     * @return 判断是否有效，若无效（伪造等），返回false，否则返回true
     */
    public static boolean checkToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("MC_TOKEN");
        return checkToken(jwtToken);
    }

    /**
     * 根据token字符串获取数据存在token中的数据
     *
     * @param request 存储带有token信息的请求
     * @return 若不存在，则返回null
     */
    public static Object getInfoByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("MC_TOKEN");
        return getInfoByJwtToken(jwtToken);
    }

    /**
     * 根据token字符串获取数据存在token中的数据
     *
     * @return 若不存在，则返回null
     */
    public static Object getInfoByJwtToken(String jwtToken) {
        if (!checkToken(jwtToken)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(DEFAULT_APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return claims.get("MC_TOKEN_OBJECT");
    }
}

