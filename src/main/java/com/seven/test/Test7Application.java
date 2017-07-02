package com.seven.test;

import com.seven.test.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
public class Test7Application {
    public static void main(String[] args) {
        SpringApplication.run(Test7Application.class, args);
    }
}
