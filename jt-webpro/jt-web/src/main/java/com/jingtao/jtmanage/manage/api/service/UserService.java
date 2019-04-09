package com.jingtao.jtmanage.manage.api.service;


import com.jingtao.jtmanage.manage.api.module.User;

public interface UserService {

    void saveUser(User user);

    String findUserByUP(String username, String password);

}
