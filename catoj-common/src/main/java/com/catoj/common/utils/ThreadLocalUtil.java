package com.catoj.common.utils;

public class ThreadLocalUtil {

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> EMAIL = new ThreadLocal<>();
    private static final ThreadLocal<Integer> ROLE = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }

    public static Long getUserId() {
        return USER_ID.get();
    }

    public static void setEmail(String email) {
        EMAIL.set(email);
    }

    public static String getEmail() {
        return EMAIL.get();
    }

    public static void setRole(Integer role) {
        ROLE.set(role);
    }

    public static Integer getRole() {
        return ROLE.get();
    }

    public static boolean isAdmin() {
        Integer role = ROLE.get();
        return role != null && role == 1;
    }

    public static void clear() {
        USER_ID.remove();
        EMAIL.remove();
        ROLE.remove();
    }
}