package com.ituser.controller.User;

import com.ituser.biz.User.IUserService;
import com.itzzy.commons.ResopnseEnum;
import com.itzzy.commons.ServerResponse;
import com.itzzy.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService iuserservice;

    @Autowired
    private HttpServletRequest servletRequest;


    @RequestMapping("login")
    @ResponseBody
    public ServerResponse login(User user) {
        //看账号密码是否为空
        if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
            return ServerResponse.userresponse(ResopnseEnum.USER_NAMEADNPASSWORD_ERROR);
        }

        //看用户是否存在
        User user1 = iuserservice.finduserbyname(user.getName());
        if (user1 == null) {
            return ServerResponse.userresponse(ResopnseEnum.USER_USER_ERROR);
        }

        //验证密码
        if (!user.getPassword().equals(user1.getPassword())) {
            return ServerResponse.success();
        }
        HttpSession session = servletRequest.getSession();
        session.setAttribute("user", user1);
        return ServerResponse.success();
    }


}