package com.catoj.test;

import com.catoj.server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;  // 如果注入成功说明没问题

    @Test
    public void test() {
        System.out.println(userMapper);
    }
}