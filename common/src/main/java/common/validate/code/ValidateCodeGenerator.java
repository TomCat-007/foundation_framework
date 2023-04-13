package common.validate.code;

import common.validate.code.model.ValidateCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangguiyuan
 * @description 校验码生成器
 * @date 2023/4/13 09:29
 */
public interface ValidateCodeGenerator {

    /**
     * 生成校验码
     *
     * @param request the request
     * @return validate code
     */
    ValidateCode generate(HttpServletRequest request);

    /**
     * 生成并保存校验码
     *
     * @param request the request
     * @return validate code
     */
    ValidateCode create(HttpServletRequest request);

    /**
     * 发送校验码
     *
     * @param request      the request
     * @param response     the response
     * @param validateCode the validateCode
     */
    void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode);

    /**
     * 是否需要认证
     *
     * @return boolean
     */
    default boolean needAuthenticated() {
        return false;
    }
}
