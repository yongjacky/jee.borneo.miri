package com.borneo.framework.common.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author peter.yuan
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                org.apache.commons.io.FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
