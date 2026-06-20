package com.catoj.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProblemTagsVO {
    @Schema(description = "标签id")
    private Long id;
    @Schema(description = "标签名称")
    private String tagName;
}
