package com.catoj.server.controller;


import com.catoj.common.enums.ProblemDifficultyEnum;
import com.catoj.pojo.dto.PageDTO;
import com.catoj.pojo.query.ProblemQuery;
import com.catoj.pojo.restful.R;
import com.catoj.pojo.vo.ProblemDetailVO;
import com.catoj.pojo.vo.ProblemPageVO;
import com.catoj.pojo.vo.ProblemTagsVO;
import com.catoj.server.service.ICodeProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 题目表 前端控制器
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "题目模块")
@RequestMapping("/api/problem")
public class CodeProblemController {

    private final ICodeProblemService codeProblemService;

    @GetMapping("/page")
    @Operation(summary = "分页查询题目")
    public R<PageDTO<ProblemPageVO>> pageQueryProblem(ProblemQuery query) {
        PageDTO<ProblemPageVO> page = codeProblemService.pageQueryProblem(query);
        return R.ok(page);
    }

    @GetMapping("/{id}/description")
    @Operation(summary = "查询题目详情")
    public R<ProblemDetailVO> getProblemDetail(@PathVariable("id") Long id) {
        ProblemDetailVO problemDetail = codeProblemService.getProblemDetail(id);
        return R.ok(problemDetail);
    }

    @GetMapping("/{id}/tags")
    @Operation(summary = "获取题目标签")
    public R<List<ProblemTagsVO>> getProblemTags(@PathVariable Long id) {
        List<ProblemTagsVO> tags = codeProblemService.getProblemTags(id);
        return R.ok(tags);
    }
}
