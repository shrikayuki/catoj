package com.catoj.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.catoj.common.constants.StatusConstant;
import com.catoj.common.enums.CodeLanguageEnum;
import com.catoj.common.exception.BusinessException;
import com.catoj.common.utils.CollUtils;
import com.catoj.pojo.dto.ProblemTemplateDTO;
import com.catoj.pojo.entity.ProblemTemplate;
import com.catoj.pojo.vo.ProblemCodeTemplateVO;
import com.catoj.server.mapper.ProblemTemplateMapper;
import com.catoj.server.service.IProblemTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 题目代码模板表 服务实现类
 * </p>
 *
 * @author rance
 * @since 2026-06-20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemTemplateServiceImpl extends ServiceImpl<ProblemTemplateMapper, ProblemTemplate> implements IProblemTemplateService {

    private final ProblemTemplateMapper templateMapper;

    @Override
    public List<ProblemCodeTemplateVO> getProblemTemplate(ProblemTemplateDTO dto) {
        // 1. 查询该题目所有语言的模板
        List<ProblemTemplate> templates = templateMapper.selectList(
                new LambdaQueryWrapper<ProblemTemplate>()
                        .eq(ProblemTemplate::getProblemId, dto.getProblemId())
                        .eq(ProblemTemplate::getDeleted, StatusConstant.Deleted.NOT)
        );

        if (CollUtils.isEmpty(templates)) {
            log.error("该题目暂无代码模板");
            return CollUtils.emptyList();
        }

        // 2. 转 VO
        List<ProblemCodeTemplateVO> voList = new ArrayList<>();
        for (ProblemTemplate template : templates) {
            CodeLanguageEnum lang = CodeLanguageEnum.of(template.getLanguage());
            if (lang == null) continue;

            ProblemCodeTemplateVO vo = new ProblemCodeTemplateVO();
            vo.setId(template.getId());
            vo.setLanguage(lang.getCode());
            vo.setTemplate(template.getTemplate());
            voList.add(vo);
        }

        return voList;
    }
}
