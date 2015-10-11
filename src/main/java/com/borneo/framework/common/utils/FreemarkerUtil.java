/**
 *
 */
package com.borneo.framework.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.borneo.framework.base.service.SpringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author peter.yuan
 */
public class FreemarkerUtil {

    public static FreemarkerUtil instance;
    private static Configuration freemarkerConfiguration;

    private FreemarkerUtil() {
    }

    public static FreemarkerUtil getInstance() {
        if (instance == null) {
            synchronized (FreemarkerUtil.class) {
                if (instance == null) {
                    freemarkerConfiguration = (Configuration) SpringUtil.getBean("freemarkerConfiguration");
                    instance = new FreemarkerUtil();
                }
            }
        }
        return instance;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

    public String getBody(String templateFile, String[] keys, Object[] values) throws Exception {
        Template template = freemarkerConfiguration.getTemplate(templateFile, "UTF-8");
        Map<String, Object> context = new HashMap<String, Object>();
        if ((keys != null) && ((values == null) || (keys.length != values.length))) {
            throw new IllegalArgumentException("keys's length must same with values's");
        }
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                context.put(keys[i], values[i]);
            }
        }
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
        return content;
    }

}
