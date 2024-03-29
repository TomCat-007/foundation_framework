package common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * @author zhangguiyuan
 * @description 获取springboot中的bean工具
 * @date 2023/3/3 13:33
 */
public class BeanFactoryUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext application) throws BeansException {
        applicationContext = application;
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);

    }

    /**
     * 通过class获取Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过class获取同类型的所有 bean
     *
     * @param <T>
     * @param clazz
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 获取应用程序当前运行环境
     *
     * @return
     */
    public static Environment currentEnvironment() {
        return applicationContext.getEnvironment();
    }

    /**
     * 获取应用程序当前激活的配置
     *
     * @return
     */
    public static String[] currentProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }
}
