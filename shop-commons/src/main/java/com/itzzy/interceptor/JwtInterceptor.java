package com.itzzy.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.itzzy.util.JWTUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
public class JwtInterceptor extends HandlerInterceptorAdapter {

    public void cros(HttpServletResponse httpServletResponse) {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        cros(response);
        //String authHeaderr = request.getHeader("Authorization");
        if (xianliu(response, request) == false) return false;
        String authHeader = "Bearer:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZXhwIjoxNTcyMjI2NDY1LCJpYXQiOjE1NzIyMjQ2NjV9.zMkO8y0btLDz9oGk0pimP8VQAV27RQSvVeEEwmiOUoE";
        if (authHeader == null || !authHeader.startsWith("Bearer:")) {
            PrintWriter writer = response.getWriter();
            writer.write("用户未登录或token被篡改");
            writer.close();
            return false;
        }
        //取得token
        String token = authHeader.substring(7);
        //验证token
        Map<String, Claim> stringClaimMap = JWTUtil.verifyToken(token);
        request.setAttribute("id", stringClaimMap.get("id"));
        return true;
    }

    private static boolean xianliu(HttpServletResponse response, HttpServletRequest request) throws IOException {
        boolean flag = true;
        Map<String, Object> map = new HashMap<>();
        ServletContext application = request.getServletContext();
        //ip和请求地址组成key
        String ip = request.getRemoteAddr();
        String path = request.getRequestURI();
        String key = ip + "," + path;
        Map applicationn = (Map) application.getAttribute(key);


        if (applicationn != null) {
            //有值说明访问过，判断访问次数是否过多；
            int count1 = (int) applicationn.get("count");
            Date looktime = (Date) applicationn.get("looktime");
            Date da = new Date();
            if (looktime.getTime() + 60000 < da.getTime()) {
                map.put("count", 1);
                map.put("looktime", new Date());
                application.setAttribute(key, map);
            } else {
                if (count1 == 3) {
                    PrintWriter writer = response.getWriter();
                    writer.write("一分钟内访问超过三次已被限流");
                    return flag = false;
                }
                //不超过3次就加一
                int count = (int) applicationn.get("count") + 1;
                map.put("count", count);
                map.put("looktime", new Date());
                application.setAttribute(key, map);
            }

        }
        //第一次访问为空，给次数赋值为1
        if (applicationn == null) {
            map.put("count", 1);
            map.put("looktime", new Date());
            application.setAttribute(key, map);
        }
        return flag;
    }
}