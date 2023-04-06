package common.config.security.config;

import cn.hutool.core.util.ObjectUtil;
import common.config.typeHandler.AccountAware;
import common.config.typeHandler.IdentityAware;
import common.config.typeHandler.OrganAware;
import common.config.typeHandler.TenantAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;


public class ContextHolder {

    private static final InheritableThreadLocal<UserDetails> userDetailsThreadLocal = new InheritableThreadLocal<>();

    public static void set(UserDetails userDetails) {
        userDetailsThreadLocal.set(userDetails);
    }

    public static void remove() {
        userDetailsThreadLocal.remove();
    }

    public static UserDetails currentUserDetails() {
        return acquireUserDetails();
    }

    public static Long currentTenantId() {
        UserDetails userDetails = acquireUserDetails();
        if (userDetails instanceof TenantAware) {
            return ((TenantAware) userDetails).currentTenantId();
        }
        return 0L;
    }

    public static Long currentIdentityId() {
        UserDetails userDetails = acquireUserDetails();
        if (userDetails instanceof IdentityAware) {
            return ((IdentityAware) userDetails).currentIdentityId();
        }
        return 0L;
    }

    public static Long currentAccountId() {
        UserDetails userDetails = acquireUserDetails();
        if (userDetails instanceof AccountAware) {
            return ((AccountAware) userDetails).currentAccountId();
        }
        return 0L;
    }

    public static Long currentOrganId() {
        UserDetails userDetails = acquireUserDetails();
        if (userDetails instanceof OrganAware) {
            return ((OrganAware) userDetails).currentOrganId();
        }
        return 0L;
    }

    private static UserDetails acquireUserDetails() {
        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtil.isNotNull(authentication)) {
            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            userDetails = userDetailsThreadLocal.get();
        }
        Assert.notNull(userDetails, "上下文中缺少认证信息，请进行登录或者手动调用 " + ContextHolder.class + "的 set(userDetails userDetails) 方法指定认证信息上下文");
        return userDetails;
    }

}
