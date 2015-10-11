package com.borneo.framework.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author peter.yuan
 */
public class BlowFish {

    public static final String ENCODING = "UTF-8";

    // 32bit - 128bit (448bit)   
    public static final String TEST_KEY = "peter@borneo888";

    public static Logger log = Logger.getLogger(BlowFish.class);

    public static String byteToString(byte[] bytes) {

        StringBuffer buf = new StringBuffer();

        for (byte b : bytes) {
            int d = b;
            if (d < 0) {
                d += 256;
            }
            if (d < 16) {
                buf.append("0");
            }
            buf.append(Integer.toString(d, 16));
        }

        return buf.toString();
    }

    public static byte[] stringToByte(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        byte[] bytes = new byte[string.length() / 2];
        String b;

        for (int i = 0; i < (string.length() / 2); i++) {
            b = string.substring(i * 2, (i * 2) + 2);
            bytes[i] = (byte) Integer.parseInt(b, 16);
        }

        return bytes;
    }

    public static byte[] encrypt(String text) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        SecretKeySpec sksSpec = new SecretKeySpec(TEST_KEY.getBytes(), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sksSpec);
        byte[] encrypted = cipher.doFinal(text.getBytes(ENCODING));

        return encrypted;
    }

    public static String encryptString(String text) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        if (StringUtils.isEmpty(text)) {
            return "";
        }
        return byteToString(encrypt(text));
    }

    public static String decrypt(byte[] encrypted) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        if (encrypted == null) {
            return "";
        }
        SecretKeySpec sksSpec = new SecretKeySpec(TEST_KEY.getBytes(), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sksSpec);
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted, ENCODING);
    }

    public static String decryptString(String encrypted) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        if (StringUtils.isEmpty(encrypted)) {
            return "";
        }
        return decrypt(stringToByte(encrypted));
    }

    /**
     * <pre>
     * 
     * Test
     * 
     *  java BlowFish encode 'dmm!devel!emcom!' adv.emcom
     * 
     * java BlowFish decode 'dmm!devel!emcom!' adv.emcom
     * 
     * <pre>
     * 
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        //System.out.println(encryptString("abc"));
        log.info(encryptString("B.Store $0.30 Test"));
        //System.out.println(decryptString("a589ed90ec95d84e"));
        log.info(decryptString("1b6362e86bfb651e4a2796f06b617b3e07109d6d417ef27bdb330689cfed98a3"));
        log.info("1b6362e86bfb651e4a2796f06b617b3e07109d6d417ef27bdb330689cfed98a3".length());
    }
}
