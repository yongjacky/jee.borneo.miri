package com.borneo.framework.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    private static final Logger LOG = LoggerFactory.getLogger(BeanUtils.class);

    public static Object getPropertyValue(Object object, String propertyName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = null;
        String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        method = object.getClass().getDeclaredMethod(methodName, null);
        Object returnValue = method.invoke(object, null);
        return returnValue;
    }

    public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException {
        Field field = getDeclaredField(object, fieldName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    public static void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException {
        Field field = getDeclaredField(object, fieldName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            LOG.error(e.getMessage());
        }
    }

    public static Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
        Assert.notNull(object);
        return getDeclaredField(object.getClass(), fieldName);
    }

    @SuppressWarnings("unchecked")
    public static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
        Assert.notNull(clazz);
        Assert.hasText(fieldName);
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                LOG.error(e.getMessage());
            }
        }
        throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
    }
}
