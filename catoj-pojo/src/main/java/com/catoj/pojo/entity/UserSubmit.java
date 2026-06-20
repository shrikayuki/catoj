package com.catoj.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.catoj.common.enums.CodeLanguageEnum;
import com.catoj.common.enums.SubmitStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户提交表
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_submit")
public class UserSubmit implements Serializable {

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
     * 提交代码
     */
    private String code;

    /**
     * 语言：1-c,2-java,3-cpp,4-python
     */
    private CodeLanguageEnum language;

    /**
     * 状态：0-等待，1-运行中，2-通过，3-失败，4-编译错误，5-超时
     */
    private SubmitStatusEnum submitStatus;

    /**
     * 运行时间(ms)
     */
    private Integer runtime;

    /**
     * 内存占用(MB)
     */
    private BigDecimal memory;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 提交时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
