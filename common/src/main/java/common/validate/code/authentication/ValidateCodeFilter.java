package common.validate.code.authentication;

import cn.hutool.core.collection.CollUtil;
import common.validate.code.exception.ValidateCodeException;
import common.validate.code.ValidateCodeGenerator;
import common.validate.code.ValidateCodeProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhangguiyuan
 * @description 校验码过滤器
 * @date 2023/4/13 09:25
 */
@RequiredArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private final Map<String, ValidateCodeGenerator> validateCodeGenerators;
    private final Map<String, ValidateCodeProcessor> validateCodeProcessors;

    private AuthenticationFailureHandler failureHandler;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // init
        Assert.isNull(this.failureHandler, "failureHandler cannot be null");
        // validateCodeGenerators 和 validateCodeProcessors 不能都为空
        Assert.isTrue(CollUtil.isEmpty(validateCodeGenerators) && CollUtil.isEmpty(validateCodeProcessors), "validateCodeGenerators and validateCodeProcessors must be init at least one");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (!requiresValidateMethod(request, response)) {
            chain.doFilter(request, response);
            return;
        }
        ValidateCodeGenerator validateCodeGenerator;
        ValidateCodeProcessor validateCodeProcessor;
        // generator
        if (null != (validateCodeGenerator = getValidateGenerator(request, response))) {
            try {
                // 判断认证状态
                if (validateCodeGenerator.needAuthenticated()
                        && null == SecurityContextHolder.getContext().getAuthentication()) {
                    throw new BadCredentialsException("Failed to generate since no authentication found");
                }
                validateCodeGenerator.send(request, response, validateCodeGenerator.create(request));
            } catch (AuthenticationException e) {
                if (e instanceof ValidateCodeException) {
                    this.failureHandler.onAuthenticationFailure(request, response, e);
                } else if (e instanceof BadCredentialsException) {// 透出未登录异常
                    this.failureHandler.onAuthenticationFailure(request, response, e);
                } else {
                    logger.error("validate code send error", e);
                    this.failureHandler.onAuthenticationFailure(request, response, new ValidateCodeException("验证码发送出错"));
                }
            }
            return;
        }
        // validate
        if (null != (validateCodeProcessor = getValidateProcessor(request, response))) {
            try {
                validateCodeProcessor.validate(request);
            } catch (AuthenticationException e) {
                if (e instanceof ValidateCodeException) {
                    this.failureHandler.onAuthenticationFailure(request, response, e);
                } else {
                    logger.error("validate code validate error", e);
                    this.failureHandler.onAuthenticationFailure(request, response, new ValidateCodeException("验证码校验出错"));
                }
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private ValidateCodeGenerator getValidateGenerator(HttpServletRequest request, HttpServletResponse response) {
        return validateCodeGenerators.get(request.getRequestURI());// 简单直接获取，避免循环 match
    }

    private ValidateCodeProcessor getValidateProcessor(HttpServletRequest request, HttpServletResponse response) {
        return validateCodeProcessors.get(request.getRequestURI());// 简单直接获取，避免循环 match
    }

    private boolean requiresValidateMethod(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod();
        return HttpMethod.GET.matches(method) || HttpMethod.POST.matches(method);
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

}
