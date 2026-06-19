package com.catoj.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;



/**
 * 用户注册请求 DTO
 *
 * @author Catoj
 * @since 1.0.0
 */
@Data
@Schema(description = "用户注册请求")
public class RegisterDTO {

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "user@example.com")
    private String email;
    /**
     * 邮箱验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Schema(description = "邮箱验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名长度为2-20个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$", message = "用户名只能包含字母、数字、下划线和中文字符")
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "zhangsan")
    private String name;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度为6-20个字符")
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

}