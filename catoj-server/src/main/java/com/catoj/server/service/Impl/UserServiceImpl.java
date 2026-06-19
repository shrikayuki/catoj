package com.catoj.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.catoj.common.exception.BusinessException;
import com.catoj.common.utils.PasswordUtils;
import com.catoj.pojo.dto.RegisterDTO;
import com.catoj.pojo.dto.SendCodeDTO;
import com.catoj.pojo.entity.User;
import com.catoj.server.mapper.UserMapper;
import com.catoj.server.service.IMailService;
import com.catoj.server.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {


}
