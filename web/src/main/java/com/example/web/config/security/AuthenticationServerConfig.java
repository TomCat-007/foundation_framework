package com.example.web.config.security;

import common.config.security.authentication.DefaultAuthenticationEntryPoint;
import common.config.security.authentication.DefaultAuthenticationFailureHandler;
import com.example.web.config.security.access.DefaultAuthenticationSuccessHandler;
import common.config.security.authentication.DefaultLogoutSuccessHandler;
import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.config.security.SecurityBeanConfig;
import com.example.web.config.security.access.expression.DefaultSecurityExpressionRoot;
import common.config.security.authorization.DefaultAccessDeniedHandler;
import common.constant.SecurityConstants;
import common.enums.error.AuthenticationErrorCodeEnum;
import common.util.HttpContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhanghuiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class AuthenticationServerConfig {

    /**
     * {@link SecurityBeanConfig#corsConfigurationSource()}
     */
    private final CorsConfigurationSource corsConfigurationSource;

    private final DefaultSecurityExpressionRoot defaultSecurityExpressionRoot;

    @Bean
    WebSecurityCustomizer ignoringCustomizer() {
        String[] swaggerExportPaths = {
                "/doc.html", "/index.html", "/swagger-ui.html",
                "/api-docs-ext", "/swagger-resources", "/api-docs", "/v2/api-docs-ext", "/v2/api-docs",
                "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security",
                "/manifest.json", "/robots.txt", "/favicon.ico",
                "/webjars/css/chunk-*.css", "/webjars/css/app.*.css",
                "/webjars/js/app.*.js", "/webjars/js/chunk-*.js", "/precache-manifest.*.js", "/service-worker.js",
        };
        String[] actuatorPaths = {
                "/actuator/health",
                "/actuator/prometheus"
        };
        return (web) -> web.ignoring().antMatchers(swaggerExportPaths).antMatchers(actuatorPaths);
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // @formatter:off
        http
                .csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource);
        http
                .exceptionHandling()
                .accessDeniedHandler(new DefaultAccessDeniedHandler())
                .authenticationEntryPoint(new DefaultAuthenticationEntryPoint());
        http
                .logout()
                .clearAuthentication(true)
                .logoutUrl(SecurityConstants.AUTH_LOGOUT)
                .logoutSuccessHandler(new DefaultLogoutSuccessHandler());

        http
                .formLogin()
                // .loginPage("http://127.0.0.1:3001/#/login")
                .loginProcessingUrl(SecurityConstants.LOGIN_PROCESSING_URL)
                .successHandler(new DefaultAuthenticationSuccessHandler())
                .failureHandler(new DefaultAuthenticationFailureHandler());
//        http.authenticationProvider(new DaoAuthenticationProvider());

        // 验证码配置
//        http.apply(new ValidateCodeConfigurer<>())
//                .failureHandler(new DefaultAuthenticationFailureHandler())
//                .validateCodeGenerator(SecurityConstants.URI.URL_IMAGE_CAPTCHA, new ImageCodeGenerator(validateCodeRepository).setExpireIn(60 * 3))
//                // .validateCodeGenerator("/auth/captcha/mobile", new SmsCodeGenerator(validateCodeRepository).setNeedAuthenticated(true))
//                .validateCodeProcessor(SecurityConstants.URI.URL_LOGIN_PROCESSING, new DefaultValidateCodeProcessor(validateCodeRepository))
//                .and()
//        ;

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .antMatchers(SecurityConstants.AUTH_CAPTCHA)
                    .permitAll()
                    .anyRequest()
                    .access(defaultSecurityExpressionRoot);
        });

        // http.oauth2Login(oauth2Login ->
        //     oauth2Login.successHandler(new CustomAuthenticationSuccessHandler())
        //             .failureHandler(new DefaultAuthenticationFailureHandler())
        //             // .loginPage("http://127.0.0.1:3001/#/login")
        // );
        // http.oauth2Client(Customizer.withDefaults());

        // sessionManagement
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionConcurrency(concurrencyControlConfigurer -> {
                // 触发创建 ConcurrentSessionFilter
                concurrencyControlConfigurer.maximumSessions(3);
                concurrencyControlConfigurer.expiredSessionStrategy(event -> {
                    HttpServletResponse response = event.getResponse();
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    Rest<BaseResponse> rest =
                            Rest.error(AuthenticationErrorCodeEnum.LOGIN_ON_ANOTHER_DEVICE, "本次登录失效");
                    HttpContextUtil.write(response, rest);
                });
            });
            httpSecuritySessionManagementConfigurer.invalidSessionStrategy((request, response) -> {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                HttpContextUtil.write(response, Rest.error(AuthenticationErrorCodeEnum.CREDENTIALS_EXPIRED, ""));
            });
        });

        // @formatter:on
        return http.build();
    }


}
