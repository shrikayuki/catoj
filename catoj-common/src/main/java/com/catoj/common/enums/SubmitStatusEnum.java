package com.catoj.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.catoj.common.utils.EnumUtils;
import lombok.Getter;

@Getter
public enum SubmitStatusEnum implements BaseEnum<Integer>{

    WAITING(0, "等待"),
    RUNNING(1, "运行"),
    AC(2, "通过"),
    FAILED(3, "失败"),
    ERROR(4, "编译出错"),
    TIMEOUT(5, "超时"),
    MEMORYOUT(6, "超出内存");

    @EnumValue
    private final Integer code;

    private final String name;

    SubmitStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SubmitStatusEnum of(Integer code) {
        return EnumUtils.of(SubmitStatusEnum.class, code);
    }

    public static String getName(Integer code) {
        return EnumUtils.getName(SubmitStatusEnum.class, code);
    }

    public static boolean isValid(Integer code) {
        return EnumUtils.isValid(SubmitStatusEnum.class, code);
    }
}
