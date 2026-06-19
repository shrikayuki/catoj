package com.catoj.server.service;

import com.catoj.pojo.dto.*;
import com.catoj.pojo.vo.LoginVO;
import org.springframework.transaction.annotation.Transactional;

public interface IAuthService {
    @Transactional(rollbackFor = Exception.class)
    void register(RegisterDTO dto);

    void sendCodeAndCheck(SendCodeDTO dto);

    /** 用户端登录 */
    LoginVO userLogin(LoginDTO dto);

    /** 管理员端登录 */
    LoginVO adminLogin(LoginDTO dto);

    /** 刷新 Token */
    LoginVO refresh(RefreshDTO dto);

    /** 登出 */
    void logout(LogoutDTO dto);
}
