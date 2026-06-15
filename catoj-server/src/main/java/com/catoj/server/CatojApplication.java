package com.catoj.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

// 临时测试用的 Controller，可以放在单独的文件里
@RestController
class TestController {
    @GetMapping("/")
    public String home() {
        return """
                <html>
                <head><title>Catoj</title></head>
                <body style="background:#0a0e27; color:#00ffcc; font-family: monospace; text-align:center; padding-top:50px;">
                    <h1>🐱 Catoj 在线判题系统</h1>
                    <p>✅ 服务运行正常</p>
                    <p>Spring Boot 3.5.9 | Java 17</p>
                    <hr style="width:50%%">
                    <p><a href="/actuator/health" style="color:#00ffcc">健康检查</a></p>
                </body>
                </html>
                """;
    }
}