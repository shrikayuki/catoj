package com.catoj.common.constants;

public final class RedisConstant {
    private RedisConstant() {
        throw new UnsupportedOperationException("不能实例化");
    }

    public static final class Mail {
        private Mail() {}

        public static final String CODE_PREFIX = "verify:code:{}";
        public static final String INTERVAL_PREFIX = "verify:interval:{}";
        public static final String ERROR_PREFIX = "verify:error:{}";

        // ========== 配置常量 ==========
        public static final long EXPIRE_MINUTES = 5;
        public static final long SEND_INTERVAL_SECONDS = 60;
        public static final int MAX_ERROR_TIMES = 5;
    }

    public static final class Auth {
        private Auth() {}

        /** Refresh Token 白名单前缀 */
        public static final String REFRESH_TOKEN_PREFIX = "refresh:token:";

        /** Refresh Token 黑名单前缀（登出/撤销） */
        public static final String REFRESH_TOKEN_BLACKLIST = "refresh:blacklist:";
    }
}
