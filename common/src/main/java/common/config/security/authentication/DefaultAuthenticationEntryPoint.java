package common.config.security.authentication;

import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.enums.error.AuthenticationErrorCodeEnum;
import common.util.HttpContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Slf4j
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Rest<BaseResponse> rest = Rest.error(AuthenticationErrorCodeEnum.INSUFFICIENT, "");

        log.error("未登陆", authException);
        HttpContextUtil.write(response, rest);
    }
}
