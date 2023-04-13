package common.config.security.session;

import cn.hutool.core.util.StrUtil;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */

public class URLHttpSessionIdResolver implements HttpSessionIdResolver {


    private static final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";

    private static final String HEADER_AUTHENTICATION_INFO = "Authentication-Info";

    private final String headerName;

    /**
     * Convenience factory to create {@link URLHttpSessionIdResolver} that uses
     * "X-Auth-Token" header.
     *
     * @return the instance configured to use "X-Auth-Token" header
     */
    public static URLHttpSessionIdResolver xAuthToken() {
        return new URLHttpSessionIdResolver(HEADER_X_AUTH_TOKEN);
    }

    /**
     * Convenience factory to create {@link URLHttpSessionIdResolver} that uses
     * "Authentication-Info" header.
     *
     * @return the instance configured to use "Authentication-Info" header
     */
    public static URLHttpSessionIdResolver authenticationInfo() {
        return new URLHttpSessionIdResolver(HEADER_AUTHENTICATION_INFO);
    }

    /**
     * The name of the header to obtain the session id from.
     *
     * @param headerName the name of the header to obtain the session id from.
     */
    public URLHttpSessionIdResolver(String headerName) {
        if (headerName == null) {
            throw new IllegalArgumentException("headerName cannot be null");
        }
        this.headerName = headerName;
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String headerValue = request.getParameter(headerName.toUpperCase());
        if (StrUtil.isEmpty(headerValue)) {
            headerValue = request.getParameter(headerName.toLowerCase());
        }
        return (headerValue != null) ? Collections.singletonList(headerValue) : Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
//        response.setHeader(this.headerName, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
//        response.setHeader(this.headerName, "");
    }
}
