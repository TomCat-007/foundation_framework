package common.constant;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
public class SecurityConstants {

    public static final String AUTH_CAPTCHA = "/auth/captcha/{uuid}";

    public static final String AUTH_MOBILE_CAPTCHA = "/auth/mobile/captcha";

    public static final String AUTH_MOBILE = "/auth/mobile";

    public static final String AUTH_WECHAT = "/auth/wechat";

    public static final String LOGIN_PROCESSING_URL = "/auth/form";

    public static final String AUTH_LOGOUT = "/auth/logout";

    public static final String MENU_TREE_PATH = "/system/menu/tree";

    public static final String[] NO_NEED_AUTH_URL = new String[]{
            AUTH_CAPTCHA,
            AUTH_MOBILE_CAPTCHA,
    };

}
