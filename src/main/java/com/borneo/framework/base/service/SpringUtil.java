/**
 *
 */
package com.borneo.framework.base.service;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author peter.yuan
 */
@Service
public class SpringUtil implements ApplicationContextAware {

    public static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    public static Object getBean(String serviceName) {
        Object obj = new Object();
        try {
            obj = SpringUtil.appContext.getBean(serviceName);
        } catch (BeansException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    public static String getMessage(String key, Object... params) {
        return SpringUtil.appContext.getMessage(key, params, Locale.ENGLISH);
    }

    public static String getMessage(String key, Object[] params, Locale locale) {
        return SpringUtil.appContext.getMessage(key, params, locale);
    }
    
    public static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
