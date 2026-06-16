package com.catoj.test;


import com.catoj.server.CatojApplication;
import com.catoj.server.service.IMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CatojApplication.class)

public class mailTest {
    @Autowired
    private IMailService mailService;

    @Test
    void sendMailTest() {
        mailService.sendVerifyCode("2892661562@qq.com");
    }
}

