package common.validate.code.model.generator;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import common.validate.code.ValidateCodeGenerator;
import common.validate.code.ValidateCodeRepository;
import common.validate.code.enums.ValidateCodeType;
import common.validate.code.model.ValidateCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/4/13 09:54
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public abstract class AbstractValidateCodeGenerator implements ValidateCodeGenerator {

    /**
     * 基础字符，排除易混淆字符: o, O, i, I, l, L, p, P, q, Q
     */
    public static final String BASE_CHAR_WITHOUT = "abcdefghjkmnrstuvwxyz";

    public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";

    public static final String BASE_NUMBER = "1234567890";

    private final ValidateCodeRepository validateCodeRepository;

    /**
     * 验证码类型
     */
    private ValidateCodeType type;

    /**
     * 有效期, 单位秒
     */
    private int expireIn = 60;

    /**
     * 验证码位数，默认 4 位
     */
    private int count = 4;

    /**
     * 是否仅数字，默认否
     */
    private boolean numberOnly = false;

    /**
     * 是否需要认证
     */
    private boolean needAuthenticated = false;

    @Override
    public ValidateCode generate(HttpServletRequest request) {
        return new ValidateCode(type, IdUtil.fastUUID()
                , numberOnly ? RandomUtil.randomNumbers(count) : RandomUtil.randomString(BASE_CHAR + BASE_NUMBER, count), expireIn);
    }

    @Override
    public ValidateCode create(HttpServletRequest request) {
        ValidateCode validateCode = generate(request);
        validateCodeRepository.save(validateCode, validateCode.getExpireIn());
        return validateCode;
    }

    @Override
    public boolean needAuthenticated() {
        return this.needAuthenticated;
    }

}
