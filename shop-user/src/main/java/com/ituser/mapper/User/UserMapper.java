package com.ituser.mapper.User;


import com.itzzy.po.User;
import com.itzzy.pram.UserPram;

import java.util.List;

public interface UserMapper {

    User finduserbyname(String name);

    void adduser(User user);

    List<User> userlist(UserPram userPram);

    long querycount(UserPram userPram);

    User finduserbyphone(String phone);

    void updateverify(User user);

    void setverifyisnull(User user);

    void registeruser(User user);
}
