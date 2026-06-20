package com.catoj.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProblemDetailVO {

    @Schema(description = "题目编号")
    private Integer problemNo;

    @Schema(description = "题目标题")
    private String title;

    @Schema(description = "题目详情")
    private String description;

    @Schema(description = "题目提示")
    private String hint;

    @Schema(description = "题目进阶")
    private String advanced;

    @Schema(description = "题目难度")
    private Integer difficulty;

    @Schema(description = "通过次数")
    private Integer passCount;

    @Schema(description = "提交次数")
    private Integer submitCount;

    @Schema(description = "通过率")
    private BigDecimal passRate;

    @Schema(description = "评论数")
    private Integer commentCount;

    @Schema(description = "点赞数")
    private Integer likeCount;

    @Schema(description = "当前用户是否AC")
    private Boolean isAc;

    @Schema(description = "是否是我的收藏")
    private Boolean isMyFav;

    @Schema(description = "是否是我的点赞")
    private Boolean isMyLike;

    @Schema(description = "题目示例集合")
    private List<ProblemIoVO> problemIoVOList;



}
