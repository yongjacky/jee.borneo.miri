package com.borneo.framework.common.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.im4java.core.ConvertCmd;

public class ForWinConvertCmd extends ConvertCmd {

    public ForWinConvertCmd() throws Exception {
        super();
        this.initForWin();
    }

    public ForWinConvertCmd(boolean useGM) throws Exception {
        super(useGM);
        this.initForWin();
    }

    @SuppressWarnings("unchecked")
    protected void initForWin() throws Exception {
        if (System.getProperty("os.name").startsWith("Windows")) {
            Field field = this.getClass().getSuperclass().getSuperclass().getDeclaredField("iCommands");
            field.setAccessible(true);
            List<String> value = (List<String>) field.get(this);
            value.addAll(0, Arrays.asList(new String[] { "cmd", "/C" }));
        }
    }
}
