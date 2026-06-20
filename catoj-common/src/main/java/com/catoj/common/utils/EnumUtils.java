package com.catoj.common.utils;

import com.catoj.common.enums.BaseEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 枚举工具类
 */
public class EnumUtils {

    /**
     * 根据 code 获取枚举
     */
    public static <T, E extends BaseEnum<T>> E of(Class<E> enumClass, T code) {
        if (code == null) return null;
        for (E e : enumClass.getEnumConstants()) {
            if (code.equals(e.getCode())) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据 code 获取名称
     */
    public static <T, E extends BaseEnum<T>> String getName(Class<E> enumClass, T code) {
        E e = of(enumClass, code);
        return e != null ? e.getName() : "未知";
    }

    /**
     * 校验 code 是否有效
     */
    public static <T, E extends BaseEnum<T>> boolean isValid(Class<E> enumClass, T code) {
        return of(enumClass, code) != null;
    }

    /**
     * 获取枚举 Map（code -> enum）
     */
    public static <T, E extends BaseEnum<T>> Map<T, E> getMap(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .collect(Collectors.toMap(BaseEnum::getCode, Function.identity()));
    }
}