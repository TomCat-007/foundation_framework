package common.config.api.base;

import common.enums.error.ErrorCodeEnumerable;
import common.enums.error.SystemErrorCodeEnum;
import common.exception.ServiceException;
import common.util.ServiceExceptionUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import javassist.*;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 响应类
 */
@Data
@Slf4j
@Accessors(chain = true)
@Schema
public class Rest<T extends BaseResponse> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     *
     * @apiNote true/false
     * @mock true
     */
    @Schema(description = "是否成功", type = "boolean", required = true, allowableValues = {"true", "false"})
    private Boolean success = Boolean.TRUE;

    /**
     * 返回code
     *
     * @mock 00000
     */
    @Schema(description = "返回code", type = "string", required = true)
    private String code = "00000";

    /**
     * 返回信息
     *
     * @apiNote 返回信息，一般为错误信息描述
     * @mock 操作成功
     */
    @Schema(description = "返回信息", type = "string")
    private String msg = SystemErrorCodeEnum.SUCCESS.getMsgTemplate();

    /**
     * 返回提示
     *
     * @apiNote 返回提示，可用作前端弹窗提示
     * @mock 操作成功
     */
    @Schema(description = "返回提示", type = "string")
    private String tip = SystemErrorCodeEnum.SUCCESS.getTip();

    /**
     * 返回数据对象
     */
    @Schema(description = "返回数据对象")
    private T data;

    private static final NoPropertyResponse noPropertyResponse = new NoPropertyResponse();

    /**
     * 成功
     *
     * @return
     */
    public static Rest<BaseResponse> success() {
        return new Rest<>()
                .setData(noPropertyResponse);
    }

    /**
     * 成功
     *
     * @param tip 提示信息
     * @return
     */
    public static Rest<BaseResponse> success(String tip) {
        return new Rest<>()
                .setData(noPropertyResponse)
                .setTip(tip);
    }

    /**
     * 成功
     *
     * @param data 响应数据
     * @param <T>
     * @return
     */
    public static <T extends BaseResponse> Rest<T> success(T data) {
        return new Rest<T>()
                .setData(data);
    }

    /**
     * 成功
     *
     * @param data 响应数据
     * @param tip  提示信息
     * @param <T>
     * @return
     */
    public static <T extends BaseResponse> Rest<T> success(T data, String tip) {
        return new Rest<T>()
                .setData(data)
                .setTip(tip);
    }

    /**
     * 错误
     *
     * @param errorCode 错误code
     * @param params    信息模板参数列表
     * @return
     */
    public static Rest<BaseResponse> error(ErrorCodeEnumerable errorCode, Object... params) {
        return new Rest<>()
                .setData(noPropertyResponse)
                .setCode(errorCode.getCode())
                .setMsg(ServiceExceptionUtil.error(errorCode, null, params).getMessage())
                .setTip(errorCode.getTip())
                .setSuccess(false);
    }

    /**
     * 错误
     *
     * @param e 异常
     * @return
     */
    public static Rest<BaseResponse> error(ServiceException e) {
        return new Rest<>()
                .setData(noPropertyResponse)
                .setCode(e.getCode())
                .setMsg(e.getMessage())
                .setTip(e.getTip())
                .setSuccess(false);
    }

    private static final ConcurrentHashMap<String, Class<?>> loadedClass = new ConcurrentHashMap<>();

    /**
     * 向量函数，即 property 和 value 都是可以动态指定
     *
     * @param property   属性名称
     * @param value      属性值
     * @param valueClazz 类型
     * @param <T>        value 的 Class 对象
     * @return
     */
    public static <T> Rest<BaseResponse> vector(String property, Object value, Class<T> valueClazz) {
        String upperProperty = Character.toUpperCase(property.charAt(0)) + property.substring(1);
        String className = Rest.class.getPackage().getName() + ".Vector$" + upperProperty + "$" + valueClazz.getName().replace(".", "$");

        Object o;
        Class clazz;

        synchronized (loadedClass) {
            if (loadedClass.containsKey(className)) {
                clazz = loadedClass.get(className);
            } else {
                ClassPool pool = new ClassPool();
                ClassLoader classLoader = Rest.class.getClassLoader();
                pool.insertClassPath(new LoaderClassPath(classLoader));
                CtClass cc = pool.makeClass(className);
                try {
                    cc.setSuperclass(pool.get(BaseResponse.class.getName()));
                    CtClass valueCtClass = pool.get(valueClazz.getName());

                    CtField f = new CtField(valueCtClass, property, cc);
                    f.setModifiers(Modifier.PUBLIC);
                    cc.addField(f);

                    CtClass[] parameters = {valueCtClass};
                    CtMethod setter = new CtMethod(pool.get(void.class.getName()), "set" + upperProperty, parameters, cc);
                    setter.setBody("{this." + property + " = $1;}");
                    cc.addMethod(setter);

                    CtMethod getter = new CtMethod(valueCtClass, "get" + upperProperty, null, cc);
                    getter.setBody("{return this." + property + ";}");
                    cc.addMethod(getter);

                    clazz = cc.toClass();
                    loadedClass.put(className, clazz);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }

        try {
            o = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("set" + upperProperty, valueClazz);
            method.invoke(o, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return Rest.success((BaseResponse) o);
    }
}
