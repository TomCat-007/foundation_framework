package common.validate.code.authentication;

import cn.hutool.core.util.StrUtil;
import common.validate.code.ValidateCodeGenerator;
import common.validate.code.ValidateCodeProcessor;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangguiyuan
 * @description 校验码配置器
 * @date 2023/4/13 09:22
 */
public class ValidateCodeConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<ValidateCodeConfigurer<H>, H> {

    private AuthenticationFailureHandler failureHandler;
    private final Map<String, ValidateCodeGenerator> validateCodeGenerators = new HashMap<>();
    private final Map<String, ValidateCodeProcessor> validateCodeProcessors = new HashMap<>();

    @Override
    public void configure(H builder) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(this.validateCodeGenerators, this.validateCodeProcessors);
        validateCodeFilter.setFailureHandler(this.failureHandler);
        builder.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public ValidateCodeConfigurer<H> validateCodeGenerator(String generateUrl, ValidateCodeGenerator validateCodeGenerator) {
        Assert.isNull(this.validateCodeGenerators.get(generateUrl), StrUtil.format("{} has already exist", generateUrl));
        this.validateCodeGenerators.put(generateUrl, validateCodeGenerator);
        return ValidateCodeConfigurer.this;
    }

    public ValidateCodeConfigurer<H> validateCodeProcessor(String processingUrl, ValidateCodeProcessor validateCodeProcessor) {
        Assert.isNull(this.validateCodeProcessors.get(processingUrl), StrUtil.format("{} has already exist", processingUrl));
        this.validateCodeProcessors.put(processingUrl, validateCodeProcessor);
        return ValidateCodeConfigurer.this;
    }

    public ValidateCodeConfigurer<H> failureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
        return ValidateCodeConfigurer.this;
    }
}
