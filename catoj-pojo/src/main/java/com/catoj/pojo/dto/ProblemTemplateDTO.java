package com.catoj.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "获取该问题下的问题模板请求")
public class ProblemTemplateDTO {

    @NotNull
    private Long problemId;


}
