package com.catoj.server.service.Impl;

import com.catoj.pojo.entity.UserAc;
import com.catoj.server.mapper.UserAcMapper;
import com.catoj.server.service.IUserAcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户通过表 服务实现类
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Service
public class UserAcServiceImpl extends ServiceImpl<UserAcMapper, UserAc> implements IUserAcService {

}
