package com.example.web.config.security.access;

import common.api.response.DefaultLoginResponse;
import com.example.web.config.security.userdetails.CustomUserDetails;
import com.example.web.pojo.Account;
import common.util.HttpContextUtil;
import common.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultLoginResponse loginResponse = new DefaultLoginResponse();

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;

            loginResponse.setLoginName(userDetails.getUsername())
                    .setIdentityId(userDetails.currentIdentityId())
                    .setCurrentAccountId(userDetails.currentAccountId())
                    .setAccountIds(userDetails.getAccounts().stream().mapToLong(Account::getId).boxed().collect(Collectors.toList()));
        }
//        else if (principal instanceof CustomOpenIdUserDetails) {
//
//            CustomOpenIdUserDetails userDetails = (CustomOpenIdUserDetails) principal;
//            loginResponse.setLoginName(userDetails.getUsername())
//                    .setIdentityId(userDetails.currentIdentityId())
//                    .setCurrentAccountId(userDetails.currentAccountId())
//                    .setAccountIds(userDetails.getAccounts().stream().mapToLong(Account::getId).boxed().collect(Collectors.toList()))
//                    .setSubscribe(userDetails.getSubscribe());
//        }

        HttpContextUtil.write(response, JsonUtil.defaultObjectMapper()
                .createObjectNode()
                .put("code", "00000")
                .put("msg", "认证成功")
                .putPOJO("data", loginResponse));
    }
}
