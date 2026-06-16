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
public class UserRegisterDTO {

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱", required = true, example = "user@example.com")
    private String email;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名长度为2-20个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$", message = "用户名只能包含字母、数字、下划线和中文字符")
    @Schema(description = "用户名", required = true, example = "zhangsan")
    private String name;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度为6-20个字符")
    @Schema(description = "密码", required = true, example = "123456", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码", required = true, example = "123456", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String confirmPassword;

    /**
     * 性别（可选）
     */
    @Schema(description = "性别：0-女，1-男", allowableValues = {"0", "1"}, example = "1")
    private Integer gender;
}