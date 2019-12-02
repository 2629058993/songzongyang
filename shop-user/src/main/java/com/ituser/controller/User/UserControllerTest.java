package com.ituser.controller.User;


import com.alibaba.fastjson.JSONObject;
import com.ituser.biz.User.IUserService;
import com.itzzy.commons.Datableresult;
import com.itzzy.commons.ResopnseEnum;
import com.itzzy.commons.ServerResponse;
import com.itzzy.po.User;
import com.itzzy.pram.UserPram;
import com.itzzy.util.JWTUtil;
import com.itzzy.util.RedisUtil;
import com.itzzy.util.SendCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@RestController
@CrossOrigin(maxAge = 3600, origins = "http://localhost:8083")
@RequestMapping("usertest")
public class UserControllerTest {
    @Autowired
    private IUserService userService;

    private Timer timer;  //定时器对象

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private HttpSession session;

/*    public static void main(String[] args) {
        redisUtil = new RedisUtil();
        String key = "sssda";
        redisUtil.set(key, 10);
    }*/


    @PostMapping("user")
    public ServerResponse usertest(User user) {
        userService.adduser(user);
        return ServerResponse.success();
    }

    @GetMapping("user")
    public ServerResponse userlist(UserPram userPram, @RequestHeader String token) {
        Datableresult datableresult = userService.userlist(userPram);
        return ServerResponse.success(datableresult);
    }


    //发送验证码  用户不存在先注册
    @PostMapping("login")
    public ServerResponse sendverification(String telnum) throws IOException {
        User user = userService.finduserbyphone(telnum);
        /*if (user == null) {    //未注册时发送验证码并注册
            JSONObject sendmsg = SendCode.sendmsg(telnum);//发送验证码;
            if (sendmsg.getString("code") != "200") {
                return ServerResponse.userresponse(ResopnseEnum.USER_LOCK_ERROR);
            }
            user=new User();
            user.setPhone(telnum);
            userService.registeruser(user);    //注册用户
            user.setVerify(sendmsg.getString("obj"));
            userService.updateverify(user);//将验证码存放数据库;
            return ServerResponse.userresponse(ResopnseEnum.USER_USER_ERROR);
        }如果是想发送完短信再注册的话就用这注释里面的。 */
        if (user == null) {  //这里是如果没有用户然后直接创建.
            user = new User();
            user.setPhone(telnum);
            user.setCartid(UUID.randomUUID().toString());
            userService.registeruser(user);    //注册用户
        }
        JSONObject sendmsg = SendCode.sendmsg(telnum);//已注册时发送验证码;
        user.setVerify(sendmsg.getString("obj"));
        userService.updateverify(user);//将验证码存放数据库;
        timer = new Timer(false);  //获取定时器对象，90秒后自动清空验证码;
        User finalUser = user;
        timer.schedule(new TimerTask() {
            public void run() {
                userService.setverifyisnull(finalUser);
            }
        }, 90000);
        return ServerResponse.success();//把验证码放在数据库而不放在session的原因就是：
        // 防止在发送验证码的时候输入的手机号和点击登录传过来的手机号不对应！
        //使用session就会有漏洞，点击发送验证码和点击登录时输入的手机号可以不一致并且还能通过验证！
    }

    @PostMapping("yanzheng")
    public ServerResponse yanzheng(@RequestParam String phone, @RequestParam String verification) throws UnsupportedEncodingException {
        User user = userService.finduserbyphone(phone);
        if (user == null) { //判断用户
            return ServerResponse.userresponse(ResopnseEnum.USER_USER_SUCCESS);
        }
        if (user.getVerify() == null || !user.getVerify().equals(verification)) {//判断验证码
            return ServerResponse.userresponse(ResopnseEnum.USER_USER_SUCCESSS);
        }
        userService.setverifyisnull(user);    //删除验证码
        //完成验证返回token，并将用户信息保存在redis中
        String token = JWTUtil.createToken(user.getId());
        user.setVerify(null);
        redisUtil.set(user.getId()+"cartid",user.getCartid());
        redisUtil.set("user", user);
        return ServerResponse.success(token);
    }
}
