package com.efeng.ssm.service.impl;

import com.efeng.exception.LoginException;
import com.efeng.ssm.domain.User;
import com.efeng.ssm.mapper.UserMapper;
import com.efeng.ssm.service.UserService;
import com.efeng.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findUser(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        User user = userMapper.getUser(map);

        if (user == null){
            throw new LoginException("账号密码错误");
        }

        if (user.getExpireTime().compareTo(DateTimeUtil.getSysTime()) > 0){
            throw new LoginException("账号已失效");
        }

        if ("0".equals(user.getLockState())){
            throw new LoginException("账号已锁定");
        }

        if (!user.getAllowIps().contains(ip)){
            throw new LoginException("ip地址受限");
        }

        return user;
    }

    @Override
    public List<User> findUserList() {
        return userMapper.getUserList();
    }
}
