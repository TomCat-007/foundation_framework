package common.validate.code.model.processor;

import cn.hutool.core.util.StrUtil;
import common.validate.code.ValidateCodeProcessor;
import common.validate.code.ValidateCodeRepository;
import common.validate.code.exception.ValidateCodeException;
import common.validate.code.model.ValidateCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangguiyuan
 * @description 默认校验码校验器
 * @date 2023/4/13 10:55
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultValidateCodeProcessor implements ValidateCodeProcessor {

    private String keyParameter = "uid";

    private String valueParameter = "captcha";

    private final ValidateCodeRepository validateCodeRepository;

    @Override
    public void validate(HttpServletRequest request) {
        String key = obtainKey(request);
        String value = obtainValue(request);
        ValidateCode validateCode = validateCodeRepository.getAndRemove(key);
        if (validateCode == null || validateCode.isExpired()) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StrUtil.equalsIgnoreCase(value, validateCode.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }
        log.info("验证码[{}]校验通过...", key);
    }

    @Override
    public void validateNotDelete(HttpServletRequest request) {
        String key = obtainKey(request);
        String value = obtainValue(request);
        ValidateCode validateCode = validateCodeRepository.get(key);
        if (validateCode == null || validateCode.isExpired()) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StrUtil.equalsIgnoreCase(value, validateCode.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }
    }

    protected String obtainKey(HttpServletRequest request) {
        String key = request.getParameter(keyParameter);
        if (StrUtil.isBlank(key)) {
            throw new ValidateCodeException("验证码的id不能为空");
        }
        return key;
    }

    protected String obtainValue(HttpServletRequest request) {
        String value = request.getParameter(valueParameter);
        if (StrUtil.isBlank(value)) {
            throw new ValidateCodeException("验证码的id不能为空");
        }
        return value;
    }

}
