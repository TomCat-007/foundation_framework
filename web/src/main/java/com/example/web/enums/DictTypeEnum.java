package com.example.web.enums;

import lombok.Getter;

@Getter
public enum DictTypeEnum {

    /**
     * 文本
     */
    TEXT((byte) 1, "文本"),

    /**
     * 数值
     */
    NUMBER((byte) 2, "数值"),

    /**
     * 区划
     */
    REGION((byte) 5, "区划"),

    ;

    /**
     * 数据库存储值
     */
    public Byte code;

    /**
     * 显示值
     */
    public String name;

    DictTypeEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }


}
