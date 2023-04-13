package common.validate.code.model.generator.image;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import common.api.response.ImageCodeResponse;
import common.config.api.base.Rest;
import common.util.HttpContextUtil;
import common.validate.code.exception.ValidateCodeException;
import common.validate.code.ValidateCodeRepository;
import common.validate.code.enums.ValidateCodeType;
import common.validate.code.model.generator.AbstractValidateCodeGenerator;
import common.validate.code.model.ValidateCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangguiyuan
 * @description 图片校验码生成器
 * @date 2023/4/13 10:23
 */
@Getter
@Setter
@Slf4j
@Accessors(chain = true)
public class ImageCodeGenerator extends AbstractValidateCodeGenerator {

    public ImageCodeGenerator(ValidateCodeRepository validateCodeRepository) {
        super(validateCodeRepository);
        super.setType(ValidateCodeType.IMAGE);
    }

    @Override
    public ImageCode generate(HttpServletRequest request) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(75, 30, getCount(), 4);
        lineCaptcha.setGenerator(new RandomGenerator(isNumberOnly() ? BASE_NUMBER : BASE_NUMBER + BASE_CHAR_WITHOUT, getCount()));
        return new ImageCode(IdUtil.fastUUID(), lineCaptcha.getCode(), getExpireIn(), lineCaptcha.getImageBase64Data());
    }

    @Override
    public ValidateCode create(HttpServletRequest request) {
        ImageCode imageCode = generate(request);
        super.getValidateCodeRepository().save(imageCode, imageCode.getExpireIn());
        return imageCode;
    }


    @Override
    public void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode) {
        if (!(validateCode instanceof ImageCode)) {
            throw new ValidateCodeException("发送验证码错误, 未生成图形");
        }
        ImageCode imageCode = (ImageCode) validateCode;
        if (StrUtil.isBlank(imageCode.getBase64Str())) {
            throw new ValidateCodeException("发送验证码错误, 未生成图形");
        }
        ImageCodeResponse responseData = new ImageCodeResponse().setUid(imageCode.getUid())
                .setCaptcha(imageCode.getBase64Str());
        log.info("发送图形验证码[{}]成功...", validateCode.getUid());
        HttpContextUtil.write(response, Rest.success(responseData));
    }
}
