package com.example.web.enums.error;

import cn.hutool.core.collection.CollUtil;
import common.enums.error.BusinessErrorCodeEnumerable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DictErrorCodeEnum implements BusinessErrorCodeEnumerable {

    ELEMENT_HAS_CHILDREN("ELEMENT_HAS_CHILDREN", "元素ID[{}]存在子级", "元素存在子级，不能删除"),

    ELEMENT_NOT_FOUND("ELEMENT_NOT_FOUND", "元素ID[{}]不存在或不可用", "该元素不存在或不可用"),

    ELEMENT_EXISTS("ELEMENT_EXISTS", "元素[{}]已存在", "该项已存在"),

    REGION_TYPE_ERROR("REGION_TYPE_ERROR", "区划数据异常 code= {}", "请选择正确的区划"),

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

    DictErrorCodeEnum(String code, String msgTemplate, String tip) {
        this.code = new StringBuilder(errorType())
                .append(".").append("{appIdentityCode}")
                .append(".").append("SYS_MANAGE")
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
    public DictErrorCodeEnum acquire(String code) {
        List<DictErrorCodeEnum> collect = Arrays.stream(values()).filter(i -> i.getCode().equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }
}
