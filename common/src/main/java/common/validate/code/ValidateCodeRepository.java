package common.validate.code;

import common.validate.code.model.ValidateCode;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/4/13 09:51
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     *
     * @param validateCode the code
     * @param expireIn     失效时间,单位秒
     */
    void save(ValidateCode validateCode, int expireIn);

    /**
     * 获取验证码
     *
     * @param uid 验证码唯一 ID
     * @return validate code
     */
    ValidateCode get(String uid);

    /**
     * 获取验证码
     *
     * @param uid 验证码唯一 ID
     * @return validate code
     */
    default ValidateCode getAndRemove(String uid) {
        ValidateCode validateCode = get(uid);
        remove(uid);
        return validateCode;
    }

    /**
     * 移除验证码
     *
     * @param uid 验证码唯一 ID
     */
    void remove(String uid);
}
