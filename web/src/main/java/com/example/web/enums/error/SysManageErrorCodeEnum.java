package com.example.web.enums.error;

import cn.hutool.core.collection.CollUtil;
import common.enums.error.BusinessErrorCodeEnumerable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SysManageErrorCodeEnum implements BusinessErrorCodeEnumerable {

    ELEMENT_HAS_CHILDREN("ELEMENT_HAS_CHILDREN", "元素ID[{}]存在子级", "元素存在子级，不能删除"),

    ROLE_IS_USING("ROLE_IS_USING", "角色在使用中", "角色在使用中"),

    ROLE_NOT_EXISTS("ROLE_NOT_EXISTS", "角色不存在", "请选择可用的角色"),

    PARENT_NOT_EXISTS("PARENT_NOT_EXISTS", "上级 ID 不存在", "上级 ID 不存在"),

    NAME_IS_USING("NAME_IS_USING", "该名称已被使用", "该名称已被使用"),

    MODULE_HASH_CHILDREN("MODULE_HASH_CHILDREN", "模块 ID [{}]存在子级", "模块存在子级，不能删除"),

    MODULE_IS_USING("MODULE_IS_USING", "模块在使用中", "模块在使用中，不能删除"),

    APP_USED_IN_PRODUCT("APP_USED_IN_PRODUCT", "应用被产品使用中", "应用被产品使用中，不能删除"),

    RELEASE_PRODUCT_NOT_ALLOW_DELETE("RELEASE_PRODUCT_NOT_ALLOW_DELETE", "已发布产品不允许删除", "已发布产品不允许删除"),

    LOGIN_IDENTITY_EXIST("LOGIN_IDENTITY_EXIST", "登录信息已存在 [{}]", "登录信息已存在 [{}]"),

    LOGIN_IDENTITY_USED_BY_OTHER("LOGIN_IDENTITY_USED_BY_OTHER", "登录信息已被其他人使用 [{}]", "登录信息已被其他人使用 [{}]"),

    ORIGINAL_PASSWORD_ERROR("ORIGINAL_PASSWORD_ERROR", "原密码错误", "原密码错误"),

    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND", "用户名或密码错误", "用户名不存在"),

    PASSWORD_NOT_MATCH("PASSWORD_NOT_MATCH", "用户名或密码错误", "密码错误"),

    MOBILE_NO_NOT_MATCH("MOBILE_NO_NOT_MATCH", "当前账户不存在，请联系管理员[{}]", "当前账户不存在，请联系管理员"),

    ACCOUNT_BINDING_SYS_ERROR("ACCOUNT_BINDING_SYS_ERROR", "手机号码未被授权", "账号绑定出错"),

    ACCOUNT_BINDING_ERROR("ACCOUNT_BINDING_ERROR", "账号绑定失败,未查询到{}", "账号绑定出错"),

    CAPTCHA_CODE_NOT_MATCH("CAPTCHA_CODE_NOT_MATCH", "验证码不匹配", "验证码错误"),

    ACCOUNT_NO_FOUND("ACCOUNT_NO_FOUND", "当前身份[{}]所关联[{}]平台账户信息不存在，请确认当前锁在平台！", "挡墙账号不存在，请确认当前登录信息！"),

    ACCOUNT_NO_ROLE("ACCOUNT_NO_ROLE", "账户[{}]没有分配角色", "账户[{}]没有分配角色，请联系管理员为其分配角色"),

    STAFF_NO_FOUND("STAFF_NO_FOUND", "手机号[{}]staff信息不存在", "当前账户信息不存在！请联系管理员"),

    ACCOUNT_BIND_STAFF_NO_FOUND("ACCOUNT_BIND_STAFF_NO_FOUND", "未查询到account[{}]关联 staff 信息", "账户不存在，请联系管理员"),

    ORGAN_IS_DISABLE("ORGAN_IS_DISABLE", "机构[]已禁用", "当前机构已被禁用"),

    MOBILE_IS_USING("MOBILE_IS_USING", "手机号[]已存在", "该手机号已存在"),

    REENTER_PASSWORD_NOT_MATCH("", "确认密码不一致", "确认密码不一致"),

    ORGAN_HAS_CHILDREN("ORGAN_HAS_CHILDREN", "当前机构存在下级", "当前机构存在下级，不能删除"),

    ORGAN_HAS_ACCOUNT("ORGAN_HAS_ACCOUNT", "当前机构下存在账号", "当前机构下存在账号，不能删除"),

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

    SysManageErrorCodeEnum(String code, String msgTemplate, String tip) {
        this.code = new StringBuilder(errorType())
                .append(".").append("{appIdentityCode}")
                .append(".").append(getModuleName())
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

    public String getModuleName() {
        return "SYSTEM";
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
    public SysManageErrorCodeEnum acquire(String code) {
        List<SysManageErrorCodeEnum> collect = Arrays.stream(values()).filter(i -> i.getCode().equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }
}
