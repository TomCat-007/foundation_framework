package common.validate.code.model;

import common.validate.code.enums.ValidateCodeType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhangguiyuan
 * @description 校验码封装类
 * @date 2023/4/13 09:32
 */
@Data
public class ValidateCode implements Serializable {

    /**
     * 验证码类型
     */
    private ValidateCodeType type;

    /**
     * 校验码唯一 ID
     */
    private final String uid;

    /**
     * 校验码
     */
    private String code;

    /**
     * 有效期，单位秒
     */
    private int expireIn;

    /**
     * 失效时间
     */
    private LocalDateTime expireTime;

    /**
     * @param type     类型
     * @param uid      校验码唯一 ID
     * @param code     校验码
     * @param expireIn 有效期, 单位秒
     */
    public ValidateCode(ValidateCodeType type, String uid, String code, int expireIn) {
        this.type = type;
        this.uid = uid;
        this.code = code;
        this.expireIn = expireIn;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    /**
     * 设置校验码有效期
     *
     * @param expireIn 有效期，单位秒
     */
    public void setExpireTime(int expireIn) {
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    /**
     * 是否过期
     *
     * @return true/false
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

}
