package com.catoj.server.service.Impl;

import com.catoj.pojo.entity.ProblemTag;
import com.catoj.server.mapper.ProblemTagMapper;
import com.catoj.server.service.IProblemTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目标签表 服务实现类
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Service
public class ProblemTagServiceImpl extends ServiceImpl<ProblemTagMapper, ProblemTag> implements IProblemTagService {

}
