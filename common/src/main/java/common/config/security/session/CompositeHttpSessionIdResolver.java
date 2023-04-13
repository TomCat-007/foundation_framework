package common.config.security.session;

import cn.hutool.core.collection.CollUtil;
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

public final class CompositeHttpSessionIdResolver implements HttpSessionIdResolver {

    private final HttpSessionIdResolver primaryHttpSessionIdResolver;

    private final List<HttpSessionIdResolver> httpSessionIdResolvers;

    public CompositeHttpSessionIdResolver(HttpSessionIdResolver primaryHttpSessionIdResolver, List<HttpSessionIdResolver> httpSessionIdResolvers) {
        this.primaryHttpSessionIdResolver = primaryHttpSessionIdResolver;
        this.httpSessionIdResolvers = httpSessionIdResolvers;
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        for (HttpSessionIdResolver httpSessionIdResolver : httpSessionIdResolvers) {
            List<String> sessionValues = httpSessionIdResolver.resolveSessionIds(request);
            if (CollUtil.isNotEmpty(sessionValues)) {
                return sessionValues;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        primaryHttpSessionIdResolver.setSessionId(request, response, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        primaryHttpSessionIdResolver.expireSession(request, response);
    }
}
