package com.example.web.enums;

import lombok.Getter;

/**
 * @author zhangguiyuan
 * @description 账号所属平台菜单
 * @date 2023/4/4 14:42
 */
@Getter
public enum PlatformEnum {

    /**
     * 超级管理员
     */
    SUPER_ADMIN((byte) 0, "超级管理员"),

    /**
     * 用户
     */
     USER((byte) 1, "用户"),

    /**
     * 机构管理员
     */
    ADMIN((byte) 5, "机构管理员"),

    ;

    /**
     * 数据库存储值
     */
    public Byte code;

    /**
     * 显示值
     */
    public String name;

    PlatformEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

}
