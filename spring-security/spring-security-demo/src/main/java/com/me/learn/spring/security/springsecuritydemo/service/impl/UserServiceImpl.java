/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.spring.security.springsecuritydemo.service.impl;

import com.me.learn.spring.security.springsecuritydemo.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2020/10/12 13:32
 * Project Name: spring-security-demo
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!"jed".equals(username)) {
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority("ROLE_ROOT");
        authorities.add(auth);
        String password = "$2a$10$UOCYXsH7cLT1jat9iGz7PuyiBzOwYNxoYycW4c5iVL0DAgJA2w0tK";
//        UserDetails user = new User(username, "{noop}123456", authorities);

        UserDetails  user = new User(username, password, true,
        true, true,
        true, authorities);
        return user;
    }
}
