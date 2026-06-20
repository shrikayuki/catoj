package com.catoj.server.service;

import com.catoj.pojo.dto.ProblemTemplateDTO;
import com.catoj.pojo.entity.ProblemTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.catoj.pojo.vo.ProblemCodeTemplateVO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * <p>
 * 题目代码模板表 服务类
 * </p>
 *
 * @author rance
 * @since 2026-06-20
 */
public interface IProblemTemplateService extends IService<ProblemTemplate> {

    List<ProblemCodeTemplateVO> getProblemTemplate(@Valid ProblemTemplateDTO dto);
}
