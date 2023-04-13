package common.validate.code.enums;

import cn.hutool.core.collection.CollUtil;
import common.enums.error.SystemErrorCodeEnumerable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/4/13 11:26
 */
public enum ValidateCodeErrorEnum implements SystemErrorCodeEnumerable {
    FAILURE("VALIDATE_FAILURE", "{}", "校验失败"),

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

    ValidateCodeErrorEnum(String code, String msgTemplate, String tip) {
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
     * @param code code
     */
    public ValidateCodeErrorEnum acquire(String code) {
        List<ValidateCodeErrorEnum> collect = Arrays.stream(values()).filter(i -> i.getCode().equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }
}
