package com.catoj.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表实体类
 * 
 * @author Catoj
 * @since 1.0.0
 */
@Data
@TableName("user")
@Schema(description = "用户实体")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "用户ID", example = "1234567890123456789")
    private Long id;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", required = true, example = "user@example.com")
    private String email;

    /**
     * 用户名
     */
    @Schema(description = "用户名", required = true, example = "zhangsan")
    private String name;

    /**
     * 性别：0-女，1-男
     */
    @Schema(description = "性别：0-女，1-男", allowableValues = {"0", "1"}, example = "1")
    private Integer gender;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    /**
     * 密码（加密存储）
     */
    @Schema(description = "密码（加密存储）", required = true, example = "encrypted_password", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    /**
     * 状态：0-封禁，1-正常
     */
    @Schema(description = "状态：false-封禁，true-正常", allowableValues = {"0", "1"}, example = "1")
    private Boolean status;

    /**
     * 用户权限: 0-普通用户， 1-管理员
     */
    @Schema(description = "状态：0-普通用户，1-管理员", allowableValues = {"0", "1"}, example = "0")
    private Integer role;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间", example = "2024-01-01 12:00:00")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间", example = "2024-01-01 12:00:00")
    private LocalDateTime updateTime;
}