package com.catoj.common.constants;

public final class Constant {
    
    private Constant() {
        throw new UnsupportedOperationException("不能实例化");
    }
    
    // ========== 请求头相关 ==========
    public static final String REQUEST_ID_HEADER = "requestId";
    public static final String REQUEST_FROM_HEADER = "x-request-from";
    
    public static final String GATEWAY_ORIGIN_NAME = "gateway";
    public static final String FEIGN_ORIGIN_NAME = "feign";
    
    public static final String BODY_PROCESSED_MARK_HEADER = "IS_BODY_PROCESSED";
    
    // ========== 数据字段名（下划线风格） ==========
    public static final class DbField {
        private DbField() {}
        
        public static final String ID = "id";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_TIME = "update_time";
        public static final String LIKED_TIMES = "liked_times";
        public static final String CREATER = "creater";
        public static final String UPDATER = "updater";
    }
    
    // ========== 数据字段名（驼峰风格） ==========
    public static final class CamelField {
        private CamelField() {}
        
        public static final String CREATE_TIME = "createTime";
        public static final String UPDATE_TIME = "updateTime";
        public static final String LIKED_TIMES = "likedTimes";
    }
    
    // ========== 逻辑删除标识 ==========
    public static final class DeleteFlag {
        private DeleteFlag() {}
        
        /** 已删除 */
        public static final boolean DELETED = true;
        /** 未删除 */
        public static final boolean NOT_DELETED = false;
    }
}