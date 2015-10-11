// Copyright:Copyright (c) 2009 DCIS,Inc
//
// $Id:  $
// $Log: $
package com.borneo.framework.common.utils;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import magick.CompositeOperator;
import magick.CompressionType;
import magick.DrawInfo;
import magick.ImageInfo;
import magick.MagickApiException;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;
import magick.PreviewType;

import org.apache.log4j.Logger;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

/**
 * Image Processing Tools Class Base on ImageMagick,please properly install and configuration the class first
 * @author peter.yuan
 */
public class PhotoUtils {
    public static int BORDER = 5;
    public static Logger log = Logger.getLogger(PhotoUtils.class);
    public static int WIDTH_SIZE = 100;
    public static int HEIGHT_SIZE = 100;

    static {
        System.setProperty("jmagick.systemclassloader", "no");
    }

    /**
     * Adjust the image size
     * @param imgFrom Adjust the image source
     * @param imgTo Adjust the image save path
     * @param scale Adjust the image scale
     */
    public static void resizeImage(String imgFrom, String imgTo, double scale) {
        try {
            MagickImage image = new MagickImage(new ImageInfo(imgFrom));
            Dimension dimension = image.getDimension();
            resizeImage(imgFrom, imgTo, dimension.height * scale, dimension.width * scale);
        } catch (MagickException e) {
            // e.printStackTrace();
            log.error(e.getMessage());
        }

    }

    /**
     * jmagic install in linux catch some problem.so use this method replace with "resizeImage_jmagic"
     * @param imgFrom
     * @param imgTo
     * @param height
     * @param width
     */
    public static boolean resizeImage_java(String imgFrom, String imgTo, double height, double width) {
        try {
            File inPutFile = new File(imgFrom);
            File outPutFile = new File(imgTo);
            BufferedImage source = ImageIO.read(inPutFile);
            if (source == null) {
                return false;
            }
            double hx = height / source.getHeight();
            double wy = width / source.getWidth();
            if (hx < wy) {
                wy = hx;
                width = (int) (source.getWidth() * wy);
            } else {
                hx = wy;
                height = (int) (source.getHeight() * hx);
            }

            int type = source.getType();
            BufferedImage target = null;
            if (type == BufferedImage.TYPE_CUSTOM) { // handmade
                ColorModel cm = source.getColorModel();
                WritableRaster raster = cm.createCompatibleWritableRaster((int) width, (int) height);
                boolean alphaPremultiplied = cm.isAlphaPremultiplied();
                target = new BufferedImage(cm, raster, alphaPremultiplied, null);
            } else {
                target = new BufferedImage((int) width, (int) height, type);
            }
            Graphics2D g = target.createGraphics();
            // smoother than exlax:
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            g.drawRenderedImage(source, AffineTransform.getScaleInstance(wy, hx));
            g.dispose();

            ImageIO.write(target, "JPEG", outPutFile);
            return true;
        } catch (Exception ex) {
            //ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return false;
    }

    /**
     * Adjust the image size
     * @param imgFrom imgFrom Adjust the image source
     * @param imgTo Adjust the image save path
     * @param height
     * @param width
     */
    public synchronized static boolean resizeImageJMagick(String imgFrom, String imgTo, double height, double width) {
        try {
            boolean needResize = true;
            ImageInfo info = new ImageInfo(imgFrom);
            MagickImage image = new MagickImage(new ImageInfo(imgFrom));
            Dimension dimension = image.getDimension();
            if ((dimension.height < height) && (dimension.getWidth() < width)) {
                needResize = false;
                width = dimension.width;
                height = dimension.height;
            }
            if (needResize) {
                double height_scale = height / dimension.height;
                double width_scale = width / dimension.width;
                if (height_scale < width_scale) {
                    width = (int) (dimension.width * height_scale);
                } else {
                    height = (int) (dimension.height * width_scale);
                }
            }
            MagickImage scaled = image.scaleImage((int) width, (int) height);
            scaled.setFileName(imgTo);
            scaled.writeImage(info);
            scaled.destroyImages();
        } catch (MagickApiException ex) {
            // ex.printStackTrace();
            log.error("Image From: " + imgFrom);
            log.error(ex.getMessage());
            return false;
        } catch (MagickException ex) {
            //ex.printStackTrace();
            log.error("Image From: " + imgFrom);
            log.error(ex.getMessage());
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("Image From: " + imgFrom);
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public static void addWaterMark(String imgFrom, String imgTo, double scale) {
        try {
            MagickImage image = new MagickImage(new ImageInfo(imgFrom));
            Dimension dimension = image.getDimension();
            addWaterMark(imgFrom, imgTo, dimension.height * scale, dimension.width * scale);
        } catch (MagickException e) {
            // e.printStackTrace();
            log.error(e.getMessage());
        }

    }

    /**
     * @author http://hi.baidu.com/szmneo/blog/item/13090b19201653bd4aedbcec.html
     * @param imgFrom
     * @param imgTo
     * @param width
     * @return
     */
    public synchronized static boolean resizeImage_fixedWidth(String imgFrom, String imgTo, double width) {
        try {

            //            File inPutFile = new File(imgFrom);
            //            BufferedImage source = ImageIO.read(inPutFile);
            //            if (source == null) {
            //                return false;
            //            }
            //
            //            double height = source.getHeight();

            //            int[] wh = getWidthHeight(imgFrom);
            //            
            //            int width_scale = (int) width / wh[0];
            //            int height = (int) (wh[1] * width_scale);

            IMOperation op = new IMOperation();
            op.addImage();
            //op.resize((int)width, (int)height);
            op.resize((int) width);
            op.addImage();

            ConvertCmd convert = new ForWinConvertCmd();
            convert.setErrorConsumer(StandardStream.STDERR);

            convert.run(op, imgFrom, imgTo);

        } catch (IOException e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            return false;
        } catch (InterruptedException e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            return false;
        } catch (IM4JavaException e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("Image From: " + imgFrom);
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @author http://hi.baidu.com/szmneo/blog/item/13090b19201653bd4aedbcec.html
     * @param imgFrom
     * @param imgTo
     * @param height
     * @param width
     * @return
     */
    public synchronized static boolean resizeImage(String imgFrom, String imgTo, double height, double width) {
        try {
            //            boolean needResize = true;
            //            
            //            int[] wh = getWidthHeight(imgFrom);
            //
            //            if(wh[1]<height && wh[0]<width) {
            //                needResize = false;
            //                width = wh[0];
            //                height = wh[1];
            //            }
            //            if(needResize) {
            //                double height_scale = (double) height / wh[1];
            //                double width_scale = (double) width / wh[0];
            //                if (height_scale < width_scale) {
            //                    width = (int) (wh[0] * height_scale);
            //                } else {
            //                    height = (int) (wh[1] * width_scale);
            //                }
            //            }

            IMOperation op = new IMOperation();
            op.addImage();
            //op.resize((int)width, (int)height,">");
            op.resize((int) width, (int) height, "^");//使用宽高中较小的那个值作为尺寸
            op.addImage();

            ConvertCmd convert = new ForWinConvertCmd();
            convert.setErrorConsumer(StandardStream.STDERR);

            convert.run(op, imgFrom, imgTo);

        } catch (IOException e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            return false;
        } catch (InterruptedException e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            return false;
        } catch (IM4JavaException e) {
            //e.printStackTrace();
            log.error(e.getMessage());
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("Image From: " + imgFrom);
            log.error(e.getMessage());
            return false;
        }
        return true;

    }

    public static void addWaterMark(String imgFrom, String imgTo, double height, double width) {
        try {
            boolean needResize = true;
            ImageInfo info = new ImageInfo(imgFrom);
            MagickImage image = new MagickImage(new ImageInfo(imgFrom));
            Dimension dimension = image.getDimension();
            if ((dimension.height < height) && (dimension.getWidth() < width)) {
                needResize = false;
            }
            double height_scale = height / dimension.height;
            double width_scale = width / dimension.width;
            if (needResize) {
                if (height_scale < width_scale) {
                    width = (int) (dimension.width * height_scale);
                } else {
                    height = (int) (dimension.height * width_scale);
                }
            }
            MagickImage scaled = image.scaleImage((int) width, (int) height);
            addText2Img(scaled, info, imgTo, 5, "yukang");
        } catch (MagickApiException ex) {
            //ex.printStackTrace();
            log.error(ex.getMessage());
        } catch (MagickException ex) {
            //ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }

    /**
     * Add Watermarking to image
     * @param magickImage
     * @param imageInfo
     * @param imgTo
     * @param location
     * @param text
     * @throws MagickException
     */
    public static void addText2Img(MagickImage magickImage, ImageInfo imageInfo, String imgTo, int location, String text) throws MagickException {
        if (magickImage.getFileName().toUpperCase().endsWith("JPG") || magickImage.getFileName().toUpperCase().endsWith("JPEG")) {
            imageInfo.setCompression(CompressionType.JPEGCompression); //the Category of zipped is JPEG type  
            imageInfo.setPreviewType(PreviewType.JPEGPreview); //The Browse Format is JPEG type   
            imageInfo.setQuality(95);
        }
        int width = magickImage.getDimension().width;
        int height = magickImage.getDimension().height;
        int a = 0;
        int b = 0;
        String[] as = text.split("");
        for (String string : as) {
            if (string.matches("[\u4E00-\u9FA5]")) {
                a++;
            }
            if (string.matches("[a-zA-Z0-9]")) {
                b++;
            }
        }
        int tl = (a * 12) + (b * 6); //the  picture element length of word and letter
        int x = 0, y = 0;
        int w, h;
        w = tl;
        h = 12; //the default height is 12px  
        boolean lc = true;
        if ((width < w) || (height < h)) {
            return;
        }
        switch (location) {
        case 0:
            lc = false;
            break;
        case 1:
            x = BORDER;
            y = h + BORDER;
            break;
        case 2:
            x = (width - w) / 2;
            y = h + BORDER;
            break;
        case 3:
            x = width - w - BORDER;
            y = h + BORDER;
            break;
        case 4:
            x = BORDER;
            y = (height + h) / 2;
            break;
        case 5:
            x = (width - w) / 2;
            y = (height + h) / 2;
            break;
        case 6:
            x = width - w - BORDER;
            y = (height + h) / 2;
            break;
        case 7:
            x = BORDER;
            y = height - BORDER;
            break;
        case 8:
            x = (width - w) / 2;
            y = height - BORDER;
            break;
        case 9:
            x = width - w - BORDER;
            y = height - BORDER;
            break;
        }
        DrawInfo aInfo = new DrawInfo(imageInfo);
        aInfo.setFill(PixelPacket.queryColorDatabase("black"));
        aInfo.setUnderColor(PixelPacket.queryColorDatabase("white"));
        aInfo.setPointsize(12);
        String fontPath = "C:/WINDOWS/Fonts/SIMSUN.TTC";//FIXME iris. If os platform is linux,need change the fontPath value in correct   
        aInfo.setFont(fontPath);
        aInfo.setTextAntialias(true);
        aInfo.setOpacity(80);
        aInfo.setText(text);
        aInfo.setGeometry("+" + x + "+" + y);
        if (lc) {
            magickImage.annotateImage(aInfo);
        }
        magickImage.setFileName(imgTo);
        magickImage.writeImage(imageInfo);
        magickImage.destroyImages();
    }

    /**
     * @param args
     * @throws MagickException
     */
    public static void main(String[] args) throws Exception {
        resizeImage_fixedWidth("d:\\1298898019403_182.jpg", "c:\\2.jpg", 111);
        //        File inPutFile = new File("c:\\a.jpg");
        //        BufferedImage source = ImageIO.read(inPutFile);
        //        System.err.println(source.getWidth());
    }

    public static void testIM4Java() throws Exception {

    }

    public static void testEffect() throws MagickException {

        ImageInfo info = new ImageInfo("D:\\work\\polygontest\\200.png");

        MagickImage image = new MagickImage(info);

        Rectangle rect = new Rectangle(20, 20, 150, 120);

        //image = image.contrastImage(true);

        image = image.chopImage(rect);

        Dimension dimension = image.getDimension();
        MagickImage scaled = image.scaleImage((int) dimension.getWidth(), (int) dimension.getHeight());
        scaled.setFileName("D:\\work\\polygontest\\128-2.png");
        scaled.writeImage(info);
        scaled.destroyImages();
    }

    public static MagickImage picToMagicImage(String imagePath) {
        try {
            MagickImage magickImage = new MagickImage(new ImageInfo(imagePath));
            return magickImage;
        } catch (MagickException e) {
            // e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * Add Watermarking to image
     * @param magickImage
     * @param imageInfo
     * @param logoPath
     * @param imgTo
     * @param location
     * @throws MagickException
     */
    public static void addPic2Img(MagickImage magickImage, ImageInfo imageInfo, String logoPath, String imgTo, int location) throws MagickException {
        ImageInfo info = new ImageInfo();
        MagickImage sImage = null;
        MagickImage fLogo = null;
        Dimension imageDim = null;
        Dimension logoDim = null;
        try {
            fLogo = new MagickImage(new ImageInfo(logoPath));
            imageDim = magickImage.getDimension();
            logoDim = fLogo.getDimension();
            int x = 0, y = 0;
            int w = logoDim.width;
            int h = logoDim.height;
            int width = imageDim.width;
            int height = imageDim.height;
            if ((width < w) || (height < h)) {
                return;
            }
            boolean lc = true;
            switch (location) {
            case 0:
                lc = false;
                break;
            case 1:
                x = BORDER;
                y = BORDER;
                break;
            case 2:
                x = (width - w) / 2;
                y = BORDER;
                break;
            case 3:
                x = width - w - BORDER;
                y = BORDER;
                break;
            case 4:
                x = BORDER;
                y = (height - h) / 2;
                break;
            case 5:
                x = (width - w) / 2;
                y = (height - h) / 2;
                break;
            case 6:
                x = width - w - BORDER;
                y = (height - h) / 2;
                break;
            case 7:
                x = BORDER;
                y = height - h - BORDER;
                break;
            case 8:
                x = (width - w) / 2;
                y = height - h - BORDER;
                break;
            case 9:
                x = width - w - BORDER;
                y = height - h - BORDER;
                break;
            }
            if (x < BORDER) {
                x = BORDER;
            }
            if (y < BORDER) {
                y = BORDER;
            }
            sImage = magickImage.scaleImage(width, height);
            if (lc) {
                sImage.compositeImage(CompositeOperator.AtopCompositeOp, fLogo, x, y);
            }
            sImage.setFileName(imgTo);
            sImage.writeImage(info);
        } finally {
            if (sImage != null) {
                sImage.destroyImages();
            }
        }
    }

    public static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {

        BufferedImage resizedImage = new BufferedImage(WIDTH_SIZE, HEIGHT_SIZE, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, WIDTH_SIZE, HEIGHT_SIZE, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }
}
