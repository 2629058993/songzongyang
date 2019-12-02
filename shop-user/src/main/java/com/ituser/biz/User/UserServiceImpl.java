package com.ituser.biz.User;

import com.ituser.mapper.User.UserMapper;
import com.itzzy.Vo.UserVo;
import com.itzzy.commons.Datableresult;
import com.itzzy.po.User;
import com.itzzy.pram.UserPram;
import com.itzzy.util.dateutil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper usermapper;

    @Override
    public User finduserbyname(String name) {
        return usermapper.finduserbyname(name);
    }

    @Override
    public void adduser(User user) {
        usermapper.adduser(user);
    }

    @Override
    public Datableresult userlist(UserPram userPram) {
        List<User> userlist = usermapper.userlist(userPram);
        List<UserVo> userVos = userVoList(userlist);
        long count = usermapper.querycount(userPram);
        return new Datableresult(userPram.getDraw(), count, count, userVos);
    }

    @Override
    public User finduserbyphone(String phone) {
        return usermapper.finduserbyphone(phone);
    }

    @Override
    public void updateverify(User user) {
        usermapper.updateverify(user);
    }

    @Override
    public void setverifyisnull(User user) {
        usermapper.setverifyisnull(user);
    }

    @Override
    public void registeruser(User user) {
        usermapper.registeruser(user);
    }

    private List<UserVo> userVoList(List<User> userlist) {
        List<UserVo> userVoklist = new ArrayList<>();
        for (User user : userlist) {
            UserVo vo = new UserVo();
            vo.setId(user.getId());
            vo.setRealname(user.getRealname());
            vo.setName(user.getName());
            vo.setPassword(user.getPassword());
            vo.setSex(user.getSex());
            vo.setAge(user.getAge());
            vo.setPhone(user.getPhone());
            vo.setEmil(user.getEmil());
            vo.setPay(user.getPay());
            vo.setRuzhitime(dateutil.date2string(user.getRuzhitime(), dateutil.YYYY_MM_DD));
            userVoklist.add(vo);
        }
        return userVoklist;
    }
}
