package com.catoj.server.controller;


import com.catoj.pojo.dto.ProblemTemplateDTO;
import com.catoj.pojo.restful.R;
import com.catoj.pojo.vo.ProblemCodeTemplateVO;
import com.catoj.server.service.IProblemTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 题目代码模板表 前端控制器
 * </p>
 *
 * @author rance
 * @since 2026-06-20
 */
@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class ProblemTemplateController {

    private final IProblemTemplateService problemTemplateService;

    @GetMapping
    @Operation(summary = "获取题目代码模板")
    public R<List<ProblemCodeTemplateVO>> getProblemTemplate(@Valid ProblemTemplateDTO dto) {
        List<ProblemCodeTemplateVO> vo = problemTemplateService.getProblemTemplate(dto);
        return R.ok(vo);
    }

}
