package com.catoj.server.service.impl;

import com.catoj.pojo.entity.UserSubmit;
import com.catoj.server.mapper.UserSubmitMapper;
import com.catoj.server.service.IUserSubmitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户提交表 服务实现类
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Service
public class UserSubmitServiceImpl extends ServiceImpl<UserSubmitMapper, UserSubmit> implements IUserSubmitService {

}
