package com.itzzy.aspect;

import com.auth0.jwt.interfaces.Claim;
import com.itzzy.Exeception.DaoException;
import com.itzzy.commons.ResopnseEnum;
import com.itzzy.util.JWTUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Aspect
@Component
@CrossOrigin(maxAge = 3600, origins = "http://localhost:8083")
public class InFoaApect {
    @Resource
    private HttpServletRequest request;

    @Around(value = "execution(* com.*.controller..*.*(..))  && @annotation(com.itzzy.aspect.ShopAnnotation)")
    public Object Interceptor(ProceedingJoinPoint joinPoint) throws UnsupportedEncodingException {
        String token = request.getHeader("token");
        if (token == null || token.length() < 50) {
            throw new DaoException(ResopnseEnum.USER_ISNO_LOGIN);  //返回前台
        }
        String newtoken = token.substring(44);
        Map<String, Claim> stringClaimMap = JWTUtil.verifyToken(newtoken);
        request.setAttribute("userid", stringClaimMap.get("id").asInt());
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }
}
