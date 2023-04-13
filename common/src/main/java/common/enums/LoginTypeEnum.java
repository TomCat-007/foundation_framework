package common.enums;

import cn.hutool.core.collection.CollUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Getter
public enum LoginTypeEnum {

    // 密码登录
    PASSWORD("password", "密码登录"),

    // 短信登录
    SMS("sms", "短信登录"),

    // 代理
    AGENT("agent", "代理"),

    // 社交
    SOCIAL("social", "社交"),

    ;

    /**
     * 数据库存储值
     */
    public String dbValue;

    /**
     * 显示值
     */
    public String displayValue;

    LoginTypeEnum(String dbValue, String displayValue) {
        this.dbValue = dbValue;
        this.displayValue = displayValue;
    }


    public List<String> readStringValues() {
        return Arrays.stream(values()).map(genderEnum -> genderEnum.dbValue).collect(Collectors.toList());
    }

    /**
     * 根据 dbValue 查找对应的枚举对象
     *
     * @param dbValue
     */
    public LoginTypeEnum acquire(String dbValue) {
        List<LoginTypeEnum> collect = Arrays.stream(values()).filter(i -> i.getDbValue().equals(dbValue)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }

    @Override
    public String toString() {
        return dbValue + ":" + displayValue;
    }


}
