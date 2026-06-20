package com.catoj.common.enums;

/**
 * 基础枚举接口
 */
public interface BaseEnum<T> {

    T getCode();

    String getName();
}