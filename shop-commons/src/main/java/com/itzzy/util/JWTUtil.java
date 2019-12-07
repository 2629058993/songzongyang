package com.itzzy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itzzy.Exeception.DaoException;
import com.itzzy.commons.Constant;
import com.itzzy.commons.ResopnseEnum;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    //jwt有三部分，第一部分是头部包含类型以及加密算法，第二部分是主要信息，
    //第三部分是前两部分进行Base64并通过.分割之后加上header中声明的加密方式进行加盐secret组合加密
    //构成了三部分

    //生成JWT
    public static String createToken(Long id) throws UnsupportedEncodingException {
        //过期时间  三十分钟过期
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 240);
        Date expiresDate = nowTime.getTime();
        //下面进行加密出一个token
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)
                .withClaim("id", Math.toIntExact(id))
                .withExpiresAt(expiresDate) //设置过期时间
                .withIssuedAt(Constant.IADDATE)      //设置签发时间
                .sign(Algorithm.HMAC256(Constant.SECRET));  //加密
        return "Bearer:" + token;
    }

    //解密token
    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
        JWTVerifier verifyer = JWT.require(Algorithm.HMAC256(Constant.SECRET))
                .build();
        DecodedJWT jwt = null;
        try {
            jwt = verifyer.verify(token);
        } catch (JWTVerificationException e) {
            throw new DaoException(ResopnseEnum.USER_ISNO_LOGIN);
        }
        return jwt.getClaims();
    }
}
