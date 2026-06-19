package com.catoj.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户通过表
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_ac")
public class UserAc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目ID
     */
    private Long problemId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 通过时使用的语言
     */
    private Integer language;

    /**
     * 通过的提交记录ID
     */
    private Long submissionId;

    /**
     * 首次AC时间
     */
    private LocalDateTime createTime;

    /**
     * 最近AC时间
     */
    private LocalDateTime lastAcTime;


}
