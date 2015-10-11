package com.borneo.framework.spring.mvc.tag;

/**
 * @author seven.shi
 */
class Utils {

    public static String BLOCK = "__jsp_override__";

    public static String getOverrideVariableName(String name) {
        return BLOCK + name;
    }

}
