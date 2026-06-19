package com.catoj.common.constants;

public final class StatusConstant {

    private StatusConstant() {
        throw new UnsupportedOperationException("不能实例化");
    }

    // ==================== 通用状态 ====================
    public static final class Common {
        private Common() {}

        /** 正常/上架/未删除/启用 */
        public static final boolean TRUE = true;

        /** 禁用/下架/已删除/封禁 */
        public static final boolean FALSE = false;
    }

    // ==================== 用户状态 ====================
    public static final class User {
        private User() {}

        /** 正常 */
        public static final boolean NORMAL = true;

        /** 封禁 */
        public static final boolean BANNED = false;
    }

    // ==================== 题目状态 ====================
    public static final class CodeProblem {
        private CodeProblem() {}

        /** 上架 */
        public static final boolean PUBLISHED = true;

        /** 下架 */
        public static final boolean UNPUBLISHED = false;
    }

    // ==================== 逻辑删除 ====================
    public static final class Deleted {
        private Deleted() {}

        /** 未删除 */
        public static final boolean NOT = false;

        /** 已删除 */
        public static final boolean YES = true;
    }

    // ==================== 角色 ====================
    public static final class Role {
        private Role() {}

        /** 普通用户 */
        public static final int USER = 0;

        /** 管理员 */
        public static final int ADMIN = 1;
    }
}