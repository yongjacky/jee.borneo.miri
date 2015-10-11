package com.borneo.framework.json;

/**
 * User: seven_shi@qq.com Date: 13-8-22 Time: 上午11:01
 */

import java.util.Map;

public class MappingJacksonJsonView extends org.springframework.web.servlet.view.json.MappingJacksonJsonView {

    @Override
    protected Object filterModel(Map<String, Object> model) {
        Map<?, ?> result = (Map<?, ?>) super.filterModel(model);
        if (result.size() == 1) {
            return result.values().iterator().next();
        } else {
            return result;
        }
    }
}
