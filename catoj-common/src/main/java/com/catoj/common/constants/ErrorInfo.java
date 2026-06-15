package com.catoj.common.constants;

public final class ErrorInfo {
    private ErrorInfo() {}
    
    public static final class Msg {
        private Msg() {}
        public static final String OK = "OK";
    }
    
    public static final class Code {
        private Code() {}
        public static final int SUCCESS = 200;
        public static final int FAILED = 0;
    }
}