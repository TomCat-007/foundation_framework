package com.example.web.enums.error;

import cn.hutool.core.collection.CollUtil;
import common.enums.error.BusinessErrorCodeEnumerable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public enum BusinessErrorCodeEnum implements BusinessErrorCodeEnumerable {

    /**
     * 记录不存在
     */
    RECORD_NOT_FOUND("RECORD_NOT_FOUND", "记录不存在:[{}]", "记录不存在"),

    /**
     * 记录重复
     */
    RECORD_REPEAT("RECORD_REPEAT", "记录重复:[{}]", "记录重复"),

    ;

    /**
     * 错误码标识
     */
    private String code;

    /**
     * 错误消息模板
     */
    private String msgTemplate;

    /**
     * 用户提示
     */
    private String tip;

    BusinessErrorCodeEnum(String code, String msgTemplate, String tip) {
        this.code = new StringBuilder(errorType())
                .append(".").append("{appIdentityCode}")
                .append(".").append(code)
                .toString();
        this.msgTemplate = msgTemplate;
        this.tip = tip;

    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsgTemplate() {
        return msgTemplate;
    }

    @Override
    public String getTip() {
        return tip;
    }

    /**
     * 根据 code 查找对应的枚举对象
     *
     * @param code
     */
    public BusinessErrorCodeEnum acquire(String code) {
        List<BusinessErrorCodeEnum> collect = Arrays.stream(values()).filter(i -> i.getCode().equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }
}
