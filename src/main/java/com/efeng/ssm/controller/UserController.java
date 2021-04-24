package com.efeng.ssm.controller;

import com.efeng.exception.LoginException;
import com.efeng.ssm.domain.User;
import com.efeng.ssm.service.UserService;
import com.efeng.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/ssm/user/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String loginAct,
                        @RequestParam("password") String loginPwd,
                        HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        String loginPwdMD5 = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        HttpSession session = request.getSession();
        try {
            User user = userService.findUser(loginAct, loginPwdMD5, ip);
            session.setAttribute("user", user);
        } catch (LoginException e) {
            String msg = e.getMessage();
            session.setAttribute("msg", msg);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        return "main";
    }

}
