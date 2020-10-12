/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.spring.security.springsecuritydemo.config;

import com.me.learn.spring.security.springsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Administrator
 * @date 2020/10/10 23:15
 * Project Name: spring-security-demo
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用默认的过滤器连
//        super.configure(http);

        http.
                //开启对登陆表单的配置
                formLogin()
                //为登陆时的请求的跳转接口
                .loginPage("/login.html")
                .loginProcessingUrl("/login.do")
                .defaultSuccessUrl("/loginsuccess")//登录成功时的跳转接口
                .failureUrl("/loginfail")  //登陆失败时的跳转接口
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                    .authorizeRequests()
                    .antMatchers("/login.html","/error.html").permitAll()
                    .anyRequest().authenticated()  //除了上方提到的接口，其余接口的访问都要登录验证
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login.html")
                .and()
                    .csrf().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存验证
       /* auth.inMemoryAuthentication()
                .withUser("jed")
                .password("{noop}111")//表示不加密
                .roles("USER_ROLE");*/
        //基于数据库验证
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123456";
        String str1 = encoder.encode(password);
        String str2 = encoder.encode(password);
        String str3 = encoder.encode(password);
        String str4 = encoder.encode(password);
        System.out.println("str1 = " + str1);
        System.out.println("str2 = " + str2);
        System.out.println("str3 = " + str3);
        System.out.println("str4 = " + str4);
        System.out.println(encoder.matches(password, str1));
        System.out.println(encoder.matches(password, str2));
        System.out.println(encoder.matches(password, str3));
        System.out.println(encoder.matches(password, str4));
    }
}
