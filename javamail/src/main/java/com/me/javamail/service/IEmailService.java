/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.javamail.service;

import org.springframework.mail.SimpleMailMessage;

/**
 * @author Administrator
 * @date 2020/5/10 11:53
 * Project Name: javamail
 */
public interface IEmailService {
    public void sendSimpleMessage(
            String to, String subject, String text);

    void sendSimpleMessageUsingTemplate(String to,
                                        String subject,
                                        SimpleMailMessage template,
                                        String... templateArgs);

    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment);
}
