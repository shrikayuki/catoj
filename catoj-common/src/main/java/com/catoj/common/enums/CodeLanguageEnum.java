package com.catoj.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.catoj.common.utils.EnumUtils;
import lombok.Getter;

@Getter
public enum CodeLanguageEnum implements BaseEnum<Integer> {

    C(1, "C"),
    JAVA(2, "Java"),
    CPP(3, "C++"),
    PY(4, "Python");

    @EnumValue
    private final Integer code;

    private final String name;

    CodeLanguageEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CodeLanguageEnum of(Integer code) {
        return EnumUtils.of(CodeLanguageEnum.class, code);
    }

    public static String getName(Integer code) {
        return EnumUtils.getName(CodeLanguageEnum.class, code);
    }

    public static boolean isValid(Integer code) {
        return EnumUtils.isValid(CodeLanguageEnum.class, code);
    }
}