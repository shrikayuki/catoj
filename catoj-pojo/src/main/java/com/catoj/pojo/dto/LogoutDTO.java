package com.catoj.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登出请求")
public class LogoutDTO {

    @Schema(description = "Refresh Token（可选，不传则只清除服务端记录）")
    private String refreshToken;
}