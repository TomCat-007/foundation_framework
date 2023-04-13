package common.config.security.authentication;

import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.enums.error.AuthenticationErrorCodeEnum;
import common.util.HttpContextUtil;
import common.validate.code.enums.ValidateCodeErrorEnum;
import common.validate.code.exception.ValidateCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Rest<BaseResponse> rest = Rest.error(AuthenticationErrorCodeEnum.FAILURE, ", 请稍后重试");
        if (exception instanceof UsernameNotFoundException) {
            rest = Rest.error(AuthenticationErrorCodeEnum.USERNAME_NOT_FOUND, "");
        } else if (exception instanceof BadCredentialsException) {
            rest = Rest.error(AuthenticationErrorCodeEnum.BAD_CREDENTIALS, "");
        } else if (exception instanceof SessionAuthenticationException) {
            rest = Rest.error(AuthenticationErrorCodeEnum.LOGIN_ON_ANOTHER_DEVICE, "本次登录失败");
        } else if (exception instanceof DisabledException) {
            rest = Rest.error(AuthenticationErrorCodeEnum.ACCOUNT_DISABLED, "");
        } else if (exception instanceof LockedException) {
            rest = Rest.error(AuthenticationErrorCodeEnum.ACCOUNT_LOCKED, "");
        } else if (exception instanceof AccountExpiredException) {
            rest = Rest.error(AuthenticationErrorCodeEnum.ACCOUNT_EXPIRED, "");
        } else if (exception instanceof ValidateCodeException) {
            rest = Rest.error(ValidateCodeErrorEnum.FAILURE, exception.getMessage()).setTip(exception.getMessage());
        }
        HttpContextUtil.write(response, rest);
    }

}
