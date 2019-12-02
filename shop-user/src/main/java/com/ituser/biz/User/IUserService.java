package com.ituser.biz.User;


import com.itzzy.po.User;
import com.itzzy.commons.Datableresult;
import com.itzzy.pram.UserPram;

public interface IUserService {
    User finduserbyname(String name);

    void adduser(User user);

    Datableresult userlist(UserPram userPram);

    User finduserbyphone(String phone);

    void updateverify(User user);

    void setverifyisnull(User user);

    void registeruser(User user);
}
