package com.nrsc.redis.learning.untils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/***
 * 从IOC容器里获取想要的bean
 */
@Component
public class ApplicationContextUtils implements InitializingBean {


    private static ApplicationContext CONTEXT;

    @Autowired
    private ApplicationContext applicationContext;

    private ApplicationContextUtils() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CONTEXT = applicationContext;
    }


    /***
     * 从IOC容器中根据类型拿bean实例
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getBeanByType(Class<T> clazz) {
        return CONTEXT.getBean(clazz);
    }

    /***
     * 从IOC容器中根据beanName拿bean实例
     * @param beanName
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getBeanByName(String beanName) {
        return (T) CONTEXT.getBean(beanName);
    }

}
