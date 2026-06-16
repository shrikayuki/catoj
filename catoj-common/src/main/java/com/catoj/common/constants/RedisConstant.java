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
}
