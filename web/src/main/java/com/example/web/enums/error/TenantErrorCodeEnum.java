package com.example.web.enums.error;

import cn.hutool.core.collection.CollUtil;
import common.constant.ErrorCodeType;
import common.enums.error.ErrorCodeEnumerable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author zhanghuiyuan
 * @description 租户异常管理
 * @date 2023/3/3 13:33
 */
public enum TenantErrorCodeEnum implements ErrorCodeEnumerable {


    // 租户编号已存在
    TENANT_EXISTS("TENANT_EXISTS", "租户编号[{}]已存在", "租户编号已存在,请检查后重试"),

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
     * 员工提示
     */
    private String tip;

    TenantErrorCodeEnum(String code, String msgTemplate, String tip) {
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
    public String errorType() {
        return ErrorCodeType.SYSTEM;
    }

    /**
     * 根据 code 查找对应的枚举对象
     *
     * @param code
     */
    public TenantErrorCodeEnum acquire(String code) {
        List<TenantErrorCodeEnum> collect = Arrays.stream(values()).filter(i -> i.getCode().equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }
}
