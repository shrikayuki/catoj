package com.catoj.server.service.Impl;

import com.catoj.pojo.entity.ProblemTagRelation;
import com.catoj.server.mapper.ProblemTagRelationMapper;
import com.catoj.server.service.IProblemTagRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目标签关联表 服务实现类
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Service
public class ProblemTagRelationServiceImpl extends ServiceImpl<ProblemTagRelationMapper, ProblemTagRelation> implements IProblemTagRelationService {

}
