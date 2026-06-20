package com.catoj.pojo.query;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "游戏分页查询参数")
public class ProblemQuery extends PageQuery{
    @Schema(description = "关键词（标题模糊搜索）", example = "两数之和")
    private String Title;

    @Schema(description = "难度：1-简单，2-中等，3-困难", example = "1")
    private Integer difficulty;

    @Schema(description = "是否展示标签")
    private Boolean isShowTag;
}
