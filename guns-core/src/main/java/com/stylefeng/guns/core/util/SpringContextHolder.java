package com.stylefeng.guns.core.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Spring的ApplicationContext的持有者,可以用静态方法的方式获取spring容器中的bean
 *
 * @author fengshuonan
 * @date 2016年11月27日 下午3:32:11
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(SpringContextHolder.class);
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    
    public static String getRootRealPath(){
		String rootRealPath ="";
		try {
			rootRealPath=getApplicationContext().getResource("").getFile().getAbsolutePath();
		} catch (IOException e) {
			logger.warn("获取系统根目录失败");
		}
		return rootRealPath;
	}
	
	public static String getResourceRootRealPath(){
		String rootRealPath ="";
		try {
			rootRealPath=new DefaultResourceLoader().getResource("").getFile().getAbsolutePath();
		} catch (IOException e) {
			logger.warn("获取资源根目录失败");
		}
		return rootRealPath;
	}
    
    private static void assertApplicationContext() {
        if (SpringContextHolder.applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }
    
}
