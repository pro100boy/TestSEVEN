package com.seven.test;

import com.seven.test.controller.LoginController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test7ApplicationTests {

    @Autowired
    private LoginController loginController;

    @Test
    public void contextLoads() {
        assertThat(loginController).isNotNull();
    }

}
