package com.catoj.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProblemCodeTemplateVO {
    @Schema(description = "代码模板id")
    private Long id;
    @Schema(description = "代码语言")
    private Integer language;
    @Schema(description = "模板代码")
    private String template;

}
