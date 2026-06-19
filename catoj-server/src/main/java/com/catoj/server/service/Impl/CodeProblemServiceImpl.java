package com.catoj.server.service.impl;

import com.catoj.pojo.entity.CodeProblem;
import com.catoj.server.mapper.CodeProblemMapper;
import com.catoj.server.service.ICodeProblemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目表 服务实现类
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Service
public class CodeProblemServiceImpl extends ServiceImpl<CodeProblemMapper, CodeProblem> implements ICodeProblemService {

}
