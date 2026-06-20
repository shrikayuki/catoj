package com.catoj.server.service.Impl;

import com.catoj.pojo.entity.TestCase;
import com.catoj.server.mapper.TestCaseMapper;
import com.catoj.server.service.ITestCaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试用例表 服务实现类
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCase> implements ITestCaseService {

}
