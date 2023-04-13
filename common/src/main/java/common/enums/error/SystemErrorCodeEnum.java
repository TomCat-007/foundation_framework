package common.enums.error;

import cn.hutool.core.collection.CollUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
public enum SystemErrorCodeEnum implements SystemErrorCodeEnumerable {

    // 操作成功
    SUCCESS("SUCCESS", "操作成功"),

    // 系统繁忙
    BUSY("BUSY", "系统繁忙"),

    ;

    /**
     *
     */
    public String code;

    /**
     * 显示值
     */
    public String msg;

    SystemErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public List<String> readStringValues() {
        return Arrays.stream(values()).map(genderEnum -> genderEnum.code).collect(Collectors.toList());
    }

    /**
     * 根据 code 查找对应的枚举对象
     *
     * @param code
     */
    public SystemErrorCodeEnum acquire(String code) {
        List<SystemErrorCodeEnum> collect = Arrays.stream(values()).filter(i -> i.code.equals(code)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }

    @Override
    public String toString() {
        return code + ":" + msg;
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
        return msg;
    }

}
