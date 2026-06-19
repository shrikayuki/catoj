package com.catoj.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录请求")
public class LoginDTO {

    @NotBlank(message = "账号不能为空")
    @Schema(description = "邮箱或用户名")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;
}