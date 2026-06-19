package com.catoj.server.controller;


import com.catoj.pojo.dto.*;
import com.catoj.pojo.restful.R;
import com.catoj.pojo.vo.LoginVO;
import com.catoj.server.service.IAuthService;
import com.catoj.server.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "用户注册登录")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public R<Void> register(@RequestBody @Valid RegisterDTO dto) {
        authService.register(dto);
        return R.ok();
    }

    @PostMapping("/send-code")
    @Operation(summary = "验证码")
    public R<Void> sendCodeAndCheck(@RequestBody @Valid SendCodeDTO dto) {
        authService.sendCodeAndCheck(dto);
        return R.ok();
    }

    // ==================== 用户端 ====================

    @PostMapping("/user/login")
    @Operation(summary = "用户端登录")
    public R<LoginVO> userLogin(@RequestBody @Valid LoginDTO dto) {
        LoginVO vo = authService.userLogin(dto);
        return R.ok( vo);
    }

    @PostMapping("/user/logout")
    @Operation(summary = "用户端登出")
    public R<Void> userLogout(@RequestBody(required = false) LogoutDTO dto) {
        authService.logout(dto);
        return R.ok();
    }

    // ==================== 管理员端 ====================

    @PostMapping("/admin/login")
    @Operation(summary = "管理员端登录")
    public R<LoginVO> adminLogin(@RequestBody @Valid LoginDTO dto) {
        LoginVO vo = authService.adminLogin(dto);
        return R.ok( vo);
    }

    @PostMapping("/admin/logout")
    @Operation(summary = "管理员端登出")
    public R<Void> adminLogout(@RequestBody(required = false) LogoutDTO dto) {
        authService.logout(dto);
        return R.ok();
    }

    // ==================== 公共 ====================

    @PostMapping("/refresh")
    @Operation(summary = "刷新 Access Token")
    public R<LoginVO> refresh(@RequestBody @Valid RefreshDTO dto) {
        LoginVO vo = authService.refresh(dto);
        return R.ok(vo);
    }
}
