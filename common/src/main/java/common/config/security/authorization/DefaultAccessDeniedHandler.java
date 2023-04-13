package common.config.security.authorization;

import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.util.HttpContextUtil;
import common.enums.error.AuthorizationErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Slf4j
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        log.warn("无访问权限", accessDeniedException);
        Rest<BaseResponse> rest = Rest.error(AuthorizationErrorCodeEnum.ACCESS_DENIED, "");
        HttpContextUtil.write(response, rest);
    }
}
