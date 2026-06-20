package com.catoj.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ProblemPageVO {

    @Schema(description = "题目id")
    private Long id;
    @Schema(description = "题目序号")
    private Integer problemNo;
    @Schema(description = "题目名称")
    private String title;
    @Schema(description = "题目难度", example = "1=简单 2=中等 3=困难")
    private Integer difficulty;
    @Schema(description = "通过率")
    private BigDecimal passRate;
    @Schema(description = "题目的标签列表")
    private List<String> tagList;
    @Schema(description = "是否是我的收藏")
    private Boolean isMyFav;
    @Schema(description = "是否通过")
    private Boolean isAc;


}
