package com.catoj.server.controller;


import com.catoj.pojo.restful.R;
import com.catoj.pojo.vo.UserHomeVO;
import com.catoj.server.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "用户管理")
public class UserController {

    private final IUserService userService;




}
