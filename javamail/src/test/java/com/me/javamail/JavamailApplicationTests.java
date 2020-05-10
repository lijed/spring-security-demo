package com.me.javamail;

import com.me.javamail.service.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavamailApplicationTests {

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Test
    void contextLoads() {
        emailServiceImpl.sendSimpleMessage("jed.li@ariix.com", "test", "this is mandril email test");
    }

}
