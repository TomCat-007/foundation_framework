package com.example.web.config.security.access.expression;

import cn.hutool.core.util.StrUtil;
import com.example.web.config.security.userdetails.CustomUserDetails;
import com.example.web.enums.PlatformEnum;
import common.config.security.authority.DefaultGrantedAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Slf4j
@Component
public class DefaultSecurityExpressionRoot implements AuthorizationManager<RequestAuthorizationContext>, EnvironmentAware {

    public static Boolean permissionCheck = false;

    private List<String> profiles;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static final List<String> whiteListPatterns = new ArrayList<>();

    static {
        whiteListPatterns.add("/permission/check/*");
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // 真实请求路径
        String path = request.getRequestURI();

        // 认证主体
        Object principal = authentication.getPrincipal();

        String principalName = authentication.getName();

        // 当前认证身份拥有的授权集合
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> authorityPatterns = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        log.info(StrUtil.format("当前环境{}请求路径为[{}]；认证主体[{}]；授权列表[{}]", profiles, path, principalName, authorityPatterns));

        for (String pattern : whiteListPatterns) {
            if (antPathMatcher.match(pattern, path)) {
                return true;
            }
        }

        if (antPathMatcher.matchStart("/system/menu/tree", path)) {
            return true;
        }
        if (profiles.contains("dev")) {
            return true;
        }
        if ((profiles.contains("test") || profiles.contains("prod")) && !permissionCheck) {
            return true;
        }

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            if (userDetails.getPlatform().equals(PlatformEnum.SUPER_ADMIN.getCode())) {
                return true;
            }
        }

        boolean hasPermission = false;
        for (GrantedAuthority authority : authorities) {
            if (authority instanceof DefaultGrantedAuthority) {
                // 授权的路径模式
                DefaultGrantedAuthority defaultGrantedAuthority = (DefaultGrantedAuthority) authority;
                hasPermission = antPathMatcher.match(defaultGrantedAuthority.getPath(), path);
                if (hasPermission) {
                    break;
                }
            }
        }
//        if (principal instanceof CustomUserDetails) {
//            for (GrantedAuthority authority : authorities) {
//                // 授权的路径模式
//                String pattern = authority.getAuthority();
//                hasPermission = antPathMatcher.match(pattern, path);
//                if (hasPermission) {
//                    break;
//                }
//            }
//        }
        return hasPermission;
    }

    @Override
    public void setEnvironment(Environment environment) {
        profiles = Arrays.asList(environment.getActiveProfiles());
    }

    public static List<String> getWhiteListPatterns() {
        return whiteListPatterns;
    }

    private boolean isAnonymousUser(Authentication authentication) {
        return !"anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString());
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        boolean hasPermission = isAnonymousUser(authentication.get()) ? hasPermission(request, authentication.get()) : isAnonymousUser(authentication.get());
        return new AuthorizationDecision(hasPermission);
    }
}
