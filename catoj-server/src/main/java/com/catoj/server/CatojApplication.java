package com.catoj.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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


