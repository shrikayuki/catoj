package com.catoj.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "登录响应")
public class LoginVO {

    @Schema(description = "Access Token")
    private String accessToken;

    @Schema(description = "Refresh Token")
    private String refreshToken;

    @Schema(description = "Access Token 有效期（秒）")
    private Long accessExpiresIn;

    @Schema(description = "Refresh Token 有效期（秒）")
    private Long refreshExpiresIn;
}