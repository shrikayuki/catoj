package com.catoj.common.utils;

/**
 * 邮件工具类
 * 纯静态方法，无外部依赖，只做字符串拼接、格式化等
 * 
 * @author Catoj
 * @since 1.0.0
 */
public final class MailUtils {

    private MailUtils() {
        throw new UnsupportedOperationException("工具类不能实例化");
    }

    /**
     * 构建验证码邮件内容
     */
    public static String buildVerifyCodeEmail(String code, int expireMinutes) {
        return """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="font-family: Arial, sans-serif; padding: 20px; background: #f5f7fa;">
                <div style="max-width: 500px; margin: 0 auto; background: #ffffff; border-radius: 12px; padding: 30px; box-shadow: 0 4px 12px rgba(0,0,0,0.1);">
                    <h2 style="text-align: center; color: #667eea; margin-bottom: 20px;">Catoj 验证码</h2>
                    <p style="color: #333; font-size: 14px;">您好，您正在注册 Catoj 账号，验证码如下：</p>
                    <div style="text-align: center; font-size: 36px; font-weight: bold; color: #667eea; letter-spacing: 10px; background: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0;">
                        %s
                    </div>
                    <p style="text-align: center; color: #999; font-size: 12px;">验证码 %d 分钟内有效，请勿泄露</p>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
                    <p style="text-align: center; color: #bbb; font-size: 11px;">此邮件由系统自动发送，请勿回复</p>
                </div>
            </body>
            </html>
            """.formatted(code, expireMinutes);
    }

    /**
     * 构建普通文本邮件内容
     */
    public static String buildTextMail(String content) {
        return content;
    }

    /**
     * 验证码格式校验
     */
    public static boolean isValidCode(String code) {
        return code != null && code.matches("^[A-Za-z0-9]{6}$");
    }
}