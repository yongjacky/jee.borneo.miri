package com.borneo.framework.common.utils;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class StringHashDecrypterUtil {
	 private Cipher dcipher;
	 private final String M_KEY="8PGPs3WrIWbBbCbU/tO7ygbinV1AIjg4lIZVtPfHAsM=";
     private byte[] salt = new String("gTupVg6hmF01BRpxlrpMnVz6b0LIQYAkrH/drOaJrdg=").getBytes();
     private int iterationCount = 1024;
     private int keyStrength = 256;
     private SecretKey key;
     private byte[] iv;
	 
     
     public byte[] getIv() {
		return iv;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

	public StringHashDecrypterUtil(String passPhrase) throws Exception {
    	if (passPhrase==null) passPhrase="";
    	if (passPhrase.equalsIgnoreCase("")) passPhrase=M_KEY;
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount, keyStrength);
        SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
        dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
     }
	 
     private Cipher getCipher(){
    	SecretKeyFactory factory;
    	Cipher dcipher=null;
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(M_KEY.toCharArray(), salt, iterationCount, keyStrength);
	        SecretKey tmp = factory.generateSecret(spec);
	        key = new SecretKeySpec(tmp.getEncoded(), "AES");
	        dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
        return dcipher;
     }
     
     public String encrypt(String data) throws Exception {
    	dcipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters params = dcipher.getParameters();
        iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] utf8EncryptedData = dcipher.doFinal(data.getBytes());
        String base64EncryptedData = new sun.misc.BASE64Encoder().encodeBuffer(utf8EncryptedData);
        return base64EncryptedData;
     }
	 
     public String decrypt(String base64EncryptedData) throws Exception {
    	 this.dcipher = getCipher();
    	 dcipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(iv));
         byte[] decryptedData = new sun.misc.BASE64Decoder().decodeBuffer(base64EncryptedData);
         byte[] utf8 = dcipher.doFinal(decryptedData);
         return new String(utf8, "UTF8");
     }
	 
     public static void main(String args[]) throws Exception {
    	StringHashDecrypterUtil decrypter = new StringHashDecrypterUtil("");
        String encrypted = "STGmWrsaHwm49DtUp2T/NQ==";//decrypter.encrypt("test13333");
        System.out.println(encrypted);
        String decrypted = decrypter.decrypt(encrypted);
        System.out.println(decrypted);
     }
}
