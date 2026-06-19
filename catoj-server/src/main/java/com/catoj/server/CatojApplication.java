package com.catoj.server;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
})
@ComponentScan(basePackages = {"com.catoj.common", "com.catoj.server"})
@MapperScan("com.catoj.server.mapper")
public class CatojApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatojApplication.class, args);

        // 控制台打印醒目启动日志
        String banner = """
                
                ╔══════════════════════════════════════════════════════════════╗
                ║                                                              ║
                ║      ██████╗ █████╗ ████████╗ ██████╗      ███╗              ║
                ║     ██╔════╝██╔══██╗╚══██╔══╝██╔═══██╗      ██╗              ║
                ║     ██║     ███████║   ██║   ██║   ██║      ██║              ║
                ║     ██║     ██╔══██║   ██║   ██║   ██║      ██║              ║
                ║     ╚██████╗██║  ██║   ██║   ╚██████╔╝ ██████╔╝              ║
                ║      ╚═════╝╚═╝  ╚═╝   ╚═╝    ╚═════╝  ╚═════╝               ║
                ║                                                              ║
                ║                   Catoj  online                              ║
                ║                   Spring Boot 3.5.9                          ║
                ║                   http://localhost:8080                      ║
                ║                                                              ║
                ╚══════════════════════════════════════════════════════════════╝
                """;

        System.out.println(banner);
        System.out.println("✅ Catoj 启动成功！等待请求...");
    }
}


