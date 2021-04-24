package com.efeng.ssm.service;

import com.efeng.exception.LoginException;
import com.efeng.ssm.domain.User;

import java.util.List;

public interface UserService {

    User findUser(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> findUserList();
}
