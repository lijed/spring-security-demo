/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.spring.security.springsecuritydemo.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 * @date 2020/10/11 0:13
 * Project Name: spring-security-demo
 */
@Controller
public class BaseController {

    @RequestMapping("/hello.html")
    public String hello() {
        return "hello";

    }
    @RequestMapping("/login.html")
    public String toLogin() {
        System.out.println("testing");
        System.out.println("testing-----------------");
        return "login.html";
    }

    @RequestMapping("/loginsuccess")
    public String loginSuccess() {
        return "login_success.html";
    }

    @RequestMapping("/loginfail")
    public String loginFail() {
        return "login_fail.html";
    }


}
