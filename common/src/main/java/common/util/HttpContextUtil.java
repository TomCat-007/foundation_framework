//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package common.util;

import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */

public class HttpContextUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpContextUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.defaultObjectMapper();

    public HttpContextUtil() {
    }

    public static String getClientIP() {
        return ServletUtil.getClientIP(getHttpServletRequest(), new String[0]);
    }

    public static String getUserAgent() {
        return ServletUtil.getHeaderIgnoreCase(getHttpServletRequest(), "User-Agent");
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getHttpServletRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static String getHeader(String name) {
        return getHttpServletRequest().getHeader(name);
    }

    public static Enumeration<String> getHeaders(String name) {
        return getHttpServletRequest().getHeaders(name);
    }

    public static String getUrl() {
        return getHttpServletRequest().getRequestURL().toString();
    }

    public static String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin() {
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }

    public static HttpServletResponse getHttpServletResponse() {
        return getServletRequestAttributes().getResponse();
    }

    public static void write(HttpServletResponse response, Object obj) {
        PrintWriter writer = null;

        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            writer = response.getWriter();
            writer.write(OBJECT_MAPPER.writeValueAsString(obj));
            writer.flush();
        } catch (IOException var7) {
            var7.printStackTrace();
            log.error(var7.getMessage(), var7);
        } finally {
            writer.close();
        }

    }

//    public static void write(HttpServletRequest request, HttpServletResponse response, Object obj) {
//        PrintWriter writer = null;
//        try {
//            ResponseUtil.allCors(request, response);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//            writer = response.getWriter();
//            writer.write(OBJECT_MAPPER.writeValueAsString(obj));
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            writer.close();
//        }
//    }
}
