/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.javamail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Administrator
 * @date 2020/5/10 11:15
 * Project Name: javamail
 */
@Configuration
public class JavaMailConfig {

    @Autowired
    private Environment environment;
    private Properties javaMailProperties;

    @Bean
    @ConfigurationProperties(prefix = "spring.mail")
    public JavaMailSender emailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        javaMailProperties = mailSender.getJavaMailProperties();
        javaMailProperties.setProperty("mail.smtp.auth", environment.getProperty("spring.mail.properties.mail.smtp.auth"));
        javaMailProperties.setProperty("mail.transport.protocol", environment.getProperty("spring.mail.protocol"));
        javaMailProperties.setProperty("mail.smtp.starttls.enable", environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        javaMailProperties.setProperty("mail.smtp.ssl.trust", environment.getProperty("spring.mail.properties.mail.smtp.ssl.trust"));
        javaMailProperties.setProperty("mail.debug", "true");
        return mailSender;
    }
}
