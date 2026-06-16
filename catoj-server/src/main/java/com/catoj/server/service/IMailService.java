package com.catoj.server.service;

public interface IMailService {
    void sendVerifyCode(String toEmail);

    void verifyCode(String email, String code);

    void sendTextMail(String toEmail, String subject, String content);
}
