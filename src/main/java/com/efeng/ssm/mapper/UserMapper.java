package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    User getUser(Map<String, String> map);

    List<User> getUserList();

}
