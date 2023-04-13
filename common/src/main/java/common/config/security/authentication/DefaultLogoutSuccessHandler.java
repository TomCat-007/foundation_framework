package common.config.security.authentication;

import common.util.HttpContextUtil;
import common.util.JsonUtil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Slf4j
@Component
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ObjectNode data = JsonUtil.emptyObjectNode();

        HttpContextUtil.write(response, JsonUtil.defaultObjectMapper()
                .createObjectNode()
                .put("code", "00000")
                .put("msg", "退出成功")
                .putPOJO("data", data));
    }
}
