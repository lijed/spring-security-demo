package com.me.javamail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JavamailApplication implements CommandLineRunner {
    private static ConfigurableApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext =  SpringApplication.run(JavamailApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
