package common.validate.code.model.generator.sms;

import common.validate.code.ValidateCodeRepository;
import common.validate.code.model.generator.AbstractValidateCodeGenerator;
import common.validate.code.model.ValidateCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangguiyuan
 * @description 短信校验码
 * @date 2023/4/13 10:49
 */
public class SmsCodeGenerator extends AbstractValidateCodeGenerator {
    public SmsCodeGenerator(ValidateCodeRepository validateCodeRepository) {
        super(validateCodeRepository);
    }

    @Override
    public void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode) {

    }
}
