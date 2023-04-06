package common.exception;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.enums.error.ParameterErrorCodeEnum;
import common.enums.error.SystemErrorCodeEnum;
import common.util.JsonUtil;
import common.util.ServiceExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    private static final ObjectMapper OBJECT_MAPPER = JsonUtil.defaultObjectMapper();

    /**
     * 捕获基类Throwable
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Rest<BaseResponse> handler(Throwable e) {
        //处理异常
        log.error(e.getMessage(), e);
        ServiceException serviceException = ServiceExceptionUtil.transform(e);
        return Rest.error(SystemErrorCodeEnum.BUSY, serviceException);
    }

    /**
     * 捕获系统中的自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Rest<BaseResponse> handler(ServiceException e) {
        //处理异常
        log.warn(e.getMessage(), e);
        return Rest.error(e);
    }

    /**
     * 参数格式错误 @RequestParam 上 validate 失败抛出的异常
     * 对应的就是校验RequestParam参数和校验PathVariable参数，这两种校验不通过，系统会抛出此异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Rest<BaseResponse> handler(ConstraintViolationException e) {
        //处理异常
        ArrayNode arrayNode = OBJECT_MAPPER.createArrayNode();
        e.getConstraintViolations().forEach(constraintViolation -> {
            for (Path.Node next : constraintViolation.getPropertyPath()) {
                if (ElementKind.PARAMETER.equals(next.getKind())) {

                    String receivedValue = ObjectUtil.defaultIfNull(constraintViolation.getInvalidValue(), "null").toString();
                    ObjectNode objectNode = OBJECT_MAPPER.createObjectNode()
                            .put("参数", next.getName())
                            .put("接收到的值", receivedValue)
                            .put("错误信息", constraintViolation.getMessage());

                    arrayNode.add(objectNode);
                }
            }
        });
        String msg;
        if (arrayNode.size() > 0) {
            msg = arrayNode.toString();
        } else {
            msg = e.getMessage();
        }
        log.warn(ParameterErrorCodeEnum.PARAMETER_NOT_VALID.getMsgTemplate(), msg);
        return Rest.error(ParameterErrorCodeEnum.PARAMETER_NOT_VALID, msg);
    }

    /**
     * 参数格式错误 @RequestBody 上 validate 失败抛出的异常
     * 对应的是校验RequestBody入参，校验不通过，系统会抛出此异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Rest<BaseResponse> handler(MethodArgumentNotValidException e) {
        //处理异常
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        ArrayNode arrayNode = OBJECT_MAPPER.createArrayNode();
        String msg = null;

        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                String field = ((FieldError) error).getField();
                String defaultMessage = error.getDefaultMessage();
                Object rejectedValue = ((FieldError) error).getRejectedValue();
                String receivedValue = ObjectUtil.defaultIfNull(rejectedValue, "null").toString();
                ObjectNode objectNode = OBJECT_MAPPER.createObjectNode()
                        .put("参数", field)
                        .put("接收到的值", receivedValue)
                        .put("错误信息", defaultMessage);
                arrayNode.add(objectNode);
                msg = arrayNode.toString();
            } else {
                msg = error.getDefaultMessage();
            }
        }
        log.warn(ParameterErrorCodeEnum.PARAMETER_NOT_VALID.getMsgTemplate(), msg);
        return Rest.error(ParameterErrorCodeEnum.PARAMETER_NOT_VALID, msg);
    }

    /**
     * RequestParam 注解中 required = true 的情况拦截
     * springServlet层次的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Rest<BaseResponse> handler(MissingServletRequestParameterException e) {
        //处理异常
        String parameterName = e.getParameterName();
        String parameterType = e.getParameterType();

        log.warn(ParameterErrorCodeEnum.MISSING_REQUEST_PARAMETER.getMsgTemplate(), parameterName, parameterType);
        return Rest.error(ParameterErrorCodeEnum.MISSING_REQUEST_PARAMETER, parameterName, parameterType);
    }

    /**
     * 请求方法不支持 例如：GET/POST不对应
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Rest<BaseResponse> handler(HttpRequestMethodNotSupportedException e) {
        //处理异常
        List<String> supportedMethods = Arrays.asList(e.getSupportedMethods());
        log.warn(ParameterErrorCodeEnum.METHOD_NOT_SUPPORT.getMsgTemplate(), e.getMethod(), supportedMethods);
        return Rest.error(ParameterErrorCodeEnum.METHOD_NOT_SUPPORT, e.getMethod(), supportedMethods);
    }

    /**
     * 接口不存在
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Rest<BaseResponse> handler(NoHandlerFoundException e) {
        //处理异常
        String requestURL = e.getRequestURL();
        log.warn(ParameterErrorCodeEnum.NO_HANDLER_FOUND.getMsgTemplate(), requestURL);
        return Rest.error(ParameterErrorCodeEnum.NO_HANDLER_FOUND, requestURL);
    }
}
