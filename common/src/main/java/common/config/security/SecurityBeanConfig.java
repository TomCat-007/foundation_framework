package common.config.security;

import cn.hutool.core.util.StrUtil;
import common.config.security.session.CompositeHttpSessionIdResolver;
import common.config.security.session.URLHttpSessionIdResolver;
import common.validate.code.ValidateCodeRepository;
import common.validate.code.model.repository.RedissonValidateCodeRepository;
import org.redisson.api.RedissonClient;
import org.redisson.spring.session.RedissonSessionRepository;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Configuration
public class SecurityBeanConfig {

    /**
     * @see EnableRedissonHttpSession
     */
    @Bean
    @ConditionalOnBean(RedissonSessionRepository.class)
    public SessionRegistry sessionRegistry(RedissonSessionRepository sessionRepository,
                                           @Value("${spring.application.name}") String applicationName) {
        if (StrUtil.isNotBlank(applicationName)) {
            sessionRepository.setKeyPrefix(buildKeyPrefix(applicationName) + ":session:");
        }
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        HeaderHttpSessionIdResolver primaryHttpSessionIdResolver = HeaderHttpSessionIdResolver.xAuthToken();
        URLHttpSessionIdResolver urlHttpSessionIdResolver = URLHttpSessionIdResolver.xAuthToken();
        return new CompositeHttpSessionIdResolver(primaryHttpSessionIdResolver, Arrays.asList(primaryHttpSessionIdResolver, urlHttpSessionIdResolver));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        //        corsConfiguration.addAllowedOrigin("*"); // springboot 2.4.X 不允许适用 * 号设置 Origin
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        // header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedHeader("*");
        // 允许的请求方法，POST、GET等
        corsConfiguration.addAllowedMethod("*");
        //        corsConfiguration.setExposedHeaders(Collections.singletonList("X-Auth-Token"));
        corsConfiguration.setExposedHeaders(Arrays.asList("X-Auth-Token", "Authorization"));
        corsConfiguration.setAllowCredentials(true);
        // 配置允许跨域访问的url
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    private String buildKeyPrefix(String applicationName) {
        String name = StrUtil.trim(applicationName).replaceAll("-", ":").replaceAll("\\.", ":");
        String[] split = name.split(":");
        return split.length > 2 ? StrUtil.format("{}:{}", split[0], split[1]) : name;
    }

    @Bean
    ValidateCodeRepository validateCodeRepository(RedissonClient redissonClient,
                                                  @Value("${spring.application.name}") String applicationName) {
        RedissonValidateCodeRepository redissonValidateCodeRepository = new RedissonValidateCodeRepository(redissonClient);
        if (StrUtil.isNotBlank(applicationName)) {
            redissonValidateCodeRepository.setKeyPrefix(buildKeyPrefix(applicationName) + ":captcha");
        }
        return redissonValidateCodeRepository;
    }
}
