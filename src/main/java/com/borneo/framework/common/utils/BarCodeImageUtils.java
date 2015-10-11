package com.borneo.framework.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class BarCodeImageUtils {

    private static Logger log = Logger.getLogger(BarCodeImageUtils.class);

    //    public static void main(String[] args) {
    //
    //        String path = "d:/test";
    //        String generatedImagePath = generateQRMatrixImage("good stuff!", path, 200, 200);
    //        File file = new File(generatedImagePath);
    //        System.out.println(decodeMatrixImage(file));
    //    }

    /**
     * @param content
     * @param destPath
     * @param width
     * @param height
     * @return will return a path (destPath.png)
     */
    public static String generateQRMatrixImage(String content, String destPath, int width, int height) {
        BitMatrix bitMatrix;
        String path = destPath + ".png";
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
            File file = new File(path);
            File filePath = new File(file.getParent());
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            MatrixToImageWriter.writeToFile(bitMatrix, "png", file);
            log.info("####### Generated Matrix Image ----->  " + path);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        } catch (WriterException e1) {
            log.error(e1.getMessage());
            return null;
        }
        return path;
    }

    public static String decodeMatrixImage(File file) {
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException ioe) {
            log.error(ioe.getMessage());
            return null;
        }
        if (image == null) {
            log.error("####### Could not decode image " + file.toString());
            return null;
        }
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        try {
            result = new MultiFormatReader().decode(bitmap);
        } catch (ReaderException re) {
            log.error(re.getMessage());
            return null;
        }
        log.info("####### Decoded Matrix Image -----> " + file.toString());
        return result.getText();
    }
}
