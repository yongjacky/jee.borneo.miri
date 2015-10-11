package com.borneo.framework.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFileUtil {

    private static final String SLASH = "/";

    public static void uploadFile(CommonsMultipartFile uploadedFile, String fileName, String uploadFilePath, String destFileFolder) {
        try {
            File directory = createDirectory(uploadFilePath, destFileFolder);

            File dstFile = new File(directory.getAbsolutePath() + File.separator + fileName);

            FileOutputStream outputStream = null;
            outputStream = new FileOutputStream(dstFile);
            outputStream.write(uploadedFile.getFileItem().get());
            outputStream.close();

            // PhotoUtils.resizeImage_java(directory.getAbsolutePath() + File.separator + fileName, directory.getAbsolutePath() + File.separator + imgTo, height, width);

            /*
             * InputStream in = uploadedFile.getInputStream(); OutputStream out = new FileOutputStream(dstFile); // Transfer bytes from in to out byte[] buf = new byte[1024]; int len; while ((len = in.read(buf)) > 0) { out.write(buf, 0, len); } in.close(); out.close();
             */

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void uploadFile(CommonsMultipartFile uploadedFile, String fileName, String imgTo, int height, int width, String uploadFilePath, String destFileFolder) {
        try {
            File directory = createDirectory(uploadFilePath, destFileFolder);

            File dstFile = new File(directory.getAbsolutePath() + File.separator + fileName);

            FileOutputStream outputStream = null;
            outputStream = new FileOutputStream(dstFile);
            outputStream.write(uploadedFile.getFileItem().get());
            outputStream.close();

            // PhotoUtils.resizeImage_java(directory.getAbsolutePath() + File.separator + fileName, directory.getAbsolutePath() + File.separator + imgTo, height, width);

            /*
             * InputStream in = uploadedFile.getInputStream(); OutputStream out = new FileOutputStream(dstFile); // Transfer bytes from in to out byte[] buf = new byte[1024]; int len; while ((len = in.read(buf)) > 0) { out.write(buf, 0, len); } in.close(); out.close();
             */

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //  public static void uploadFile(BufferedImage image, String type, String fileName,String uploadFilePath,String destFileFolder)
    //  {
    //      try
    //      {  
    //          File directory = createDirectory(uploadFilePath, destFileFolder);
    //      
    //          File dstFile = new File(directory.getAbsolutePath()+File.separator+ fileName);
    //          
    //          ImageIO.write(image, type, dstFile);
    //           
    //      
    //      }
    //      catch(Exception ex)
    //      {
    //          ex.printStackTrace();
    //      }
    //  }

    public static File createDirectory(String path, String name) throws Exception {
        File dir = new File(path + File.separator + name);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static String getFileExtension(String fileName) throws Exception {
        int dotPos = fileName.lastIndexOf(".");
        String extension = fileName.substring(dotPos);
        return extension;
    }

    public static String getFullPath(String name, String path, String folder) throws Exception {

        File dir = new File(path + SLASH + folder + SLASH + name);

        String fullPath = "";
        if (dir.exists()) {
            fullPath = dir.getAbsolutePath(); //path+SLASH+folder+SLASH+name; dir.getAbsolutePath();
            //System.out.print("fullPath:"+dir.getAbsolutePath());
            //dir.mkdirs();
        } else {
            System.out.println("error");
        }
        return fullPath;
    }
    
    public static void copyBytes(InputStream input,OutputStream output,int bufferSize) throws IOException {
  	    byte[] buf = new byte[bufferSize];
  	    int bytesRead = input.read(buf);
  	    while (bytesRead != -1) {
  	      output.write(buf, 0, bytesRead);
  	      bytesRead = input.read(buf);
  	    }
  	    output.flush();
  	 }
}
