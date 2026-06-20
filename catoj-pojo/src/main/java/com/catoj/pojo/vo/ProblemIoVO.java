package com.catoj.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
@Data
public class ProblemIoVO {
    @Schema(description = "Io主键")
    private Long id;

    @Schema(description = "输入")
    private String inputDesc;

    @Schema(description = "输出")
    private String outputDesc;

    @Schema(description = "解释")
    private String descExplain;
    @Schema(description = "样例图片列表")
    private List<String> url;
}
