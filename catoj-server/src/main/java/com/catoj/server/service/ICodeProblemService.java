package com.catoj.server.service;

import com.catoj.pojo.dto.PageDTO;
import com.catoj.pojo.entity.CodeProblem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.catoj.pojo.query.ProblemQuery;
import com.catoj.pojo.vo.ProblemDetailVO;
import com.catoj.pojo.vo.ProblemPageVO;
import com.catoj.pojo.vo.ProblemTagsVO;

import java.util.List;

/**
 * <p>
 * 题目表 服务类
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
public interface ICodeProblemService extends IService<CodeProblem> {

    PageDTO<ProblemPageVO> pageQueryProblem(ProblemQuery query);

    ProblemDetailVO getProblemDetail(Long id);

    List<ProblemTagsVO> getProblemTags(Long id);
}
