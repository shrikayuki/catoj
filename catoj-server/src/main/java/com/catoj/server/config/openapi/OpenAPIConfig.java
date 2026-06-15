package com.catoj.server.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // 定义 JWT 安全方案
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("""
                        JWT 认证 Token
                        
                        使用步骤：
                        1. 调用登录接口获取 token
                        2. 在下方输入框中输入 token
                        3. 格式：直接粘贴 token，不需要加 "Bearer " 前缀
                        
                        💡 提示：点击右上角 Authorize 按钮，把 token 粘贴进去即可
                        """);

        // 全局安全要求（所有接口都需要认证）
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("🐱 Catoj 在线判题系统 API 文档")
                        .version("1.0.0")
                        .description("""
                                ## 📌 项目介绍
                                Catoj 是一个在线判题系统，支持题目管理、代码提交、自动判题等功能。
                                
                                ## 🔧 技术栈
                                - Spring Boot 3.5.9
                                - MyBatis Plus 3.5.12
                                - Redis + RabbitMQ
                                - Druid 连接池
                                - JWT 认证
                                
                                ## 📖 使用说明
                                ### 🔐 认证流程
                                1. 先调用 `/api/user/login` 接口获取 JWT Token
                                2. 点击右上角 **Authorize** 按钮
                                3. 在弹出的输入框中粘贴 Token（不需要加 Bearer 前缀）
                                4. 点击 **Authorize** 确认
                                5. 所有带 🔒 锁的接口会自动带上认证信息
                                """)
                        .contact(new Contact()
                                .name("Catoj Team")
                                .email("catoj@example.com")
                                .url("https://github.com/shrikayuki/catoj"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("🌍 开发环境"))
                // 注册安全方案
                .schemaRequirement("JWT", securityScheme)
                // 全局安全要求（如果想让某些接口公开，用 @Operation(security = @SecurityRequirement(name = "")) 覆盖）
                .addSecurityItem(securityRequirement);
    }
}