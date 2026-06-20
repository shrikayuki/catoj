package com.catoj.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.catoj.common.enums.ProblemDifficultyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 题目表
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("code_problem")
public class CodeProblem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目编号
     */
    private Integer problemNo;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目描述
     */
    private String description;

    /**
     * 提示
     */
    private String hint;

    /**
     * 进阶
     */
    private String advanced;

    /**
     * 难度：1-简单，2-中等，3-困难
     */
    private ProblemDifficultyEnum difficulty;

    /**
     * 时间限制（ms）
     */
    private Integer timeLimit;

    /**
     * 内存限制（MB）
     */
    private BigDecimal memoryLimit;

    /**
     * 通过次数
     */
    private Integer passCount;

    /**
     * 提交次数
     */
    private Integer submitCount;

    /**
     * 通过率
     */
    private BigDecimal passRate;

    /**
     * 状态：0-下架，1-上架
     */
    private Boolean codeStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
