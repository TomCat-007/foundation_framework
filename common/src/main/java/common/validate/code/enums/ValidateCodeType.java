package common.validate.code.enums;

import lombok.Getter;

/**
 * @author zhangguiyuan
 * @description 校验码类型菜单
 * @date 2023/4/13 09:34
 */
@Getter
public enum ValidateCodeType {

    /**
     * 图形验证码
     */
    IMAGE("图形"),

    /**
     * 短信验证码
     */
    SMS("短信"),

    /**
     * 邮箱验证码
     */
    EMAIL("邮箱"),

    ;

    private final String typeName;

    ValidateCodeType(String typeName) {
        this.typeName = typeName;
    }

}
