package com.catoj.server.service.impl;

import com.catoj.pojo.entity.UserSkill;
import com.catoj.server.mapper.UserSkillMapper;
import com.catoj.server.service.IUserSkillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户技能表 服务实现类
 * </p>
 *
 * @author rance
 * @since 2026-06-17
 */
@Service
public class UserSkillServiceImpl extends ServiceImpl<UserSkillMapper, UserSkill> implements IUserSkillService {

}
