package common.validate.code;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangguiyuan
 * @description 校验码处理器
 * @date 2023/4/13 09:29
 */
public interface ValidateCodeProcessor {

    /**
     * 校验验证码(验证后删除)
     *
     * @param request the servlet web request
     */
    void validate(HttpServletRequest request);

    /**
     * 校验验证码(验证后不删除)
     *
     * @param request the servlet web request
     */
    void validateNotDelete(HttpServletRequest request);
}
