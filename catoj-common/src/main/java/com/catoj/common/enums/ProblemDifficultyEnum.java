package com.catoj.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.catoj.common.utils.EnumUtils;
import lombok.Getter;

@Getter
public enum ProblemDifficultyEnum implements BaseEnum<Integer> {

    EASY(1, "简单"),
    MEDIUM(2, "中等"),
    HARD(3, "困难");

    @EnumValue
    private final Integer code;

    private final String name;

    ProblemDifficultyEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ProblemDifficultyEnum of(Integer code) {
        return EnumUtils.of(ProblemDifficultyEnum.class, code);
    }

    public static String getName(Integer code) {
        return EnumUtils.getName(ProblemDifficultyEnum.class, code);
    }

    public static boolean isValid(Integer code) {
        return EnumUtils.isValid(ProblemDifficultyEnum.class, code);
    }
}