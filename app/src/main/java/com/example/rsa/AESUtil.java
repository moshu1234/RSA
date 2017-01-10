package com.example.rsa;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Administrator
 *
 */
public class AESUtil {
    public static final String AES = "AES";
    public static final String SHA1 = "SHA1PRNG";
    public static final String CRYPTO = "Crypto";
    public static final String CIPHER = "AES/ECB/PKCS5Padding";
    private static int keyLength=16;
    /**
     * ¼ÓÃÜ
     *
     * @param content
     *            £ºÎÄ±¾
     * @param password
     *            £ºÃÜÂë
     * @return
     */
    public static byte[] encrypt(byte[] content, String password) {
        try {
//            KeyGenerator kgen = KeyGenerator.getInstance(AES);
//            SecureRandom sr = new SecureRandom();// java pc°æ¼ÓÃÜÉèÖÃ
//             SecureRandom sr = SecureRandom.getInstance(SHA1, CRYPTO);//
//            // android°æ¼ÓÃÜÉèÖÃ
//            sr.setSeed(password.getBytes());
//            kgen.init(256, sr); // 192 and 256 bits may not be available
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
//            Cipher cipher = Cipher.getInstance(AES);// java pc°æ¼ÓÃÜÉèÖÃ
             Cipher cipher = Cipher.getInstance(CIPHER);// Android°æ¼ÓÃÜÉèÖÃ
//            SecretKeySpec key = new SecretKeySpec(password.getBytes(), AES);
//            byte[] byteContent = content.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, createKey(password));// ³õÊ¼»¯
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // /** 创建密钥 **/
    private static SecretKeySpec createKey(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(keyLength);
        sb.append(password);
        while (sb.length() < keyLength) {
            sb.append("0");
        }
        if (sb.length() > keyLength) {
            sb.setLength(keyLength);
        }

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }
    /**
     * ½âÃÜ
     *
     * @param content
     *            ´ý½âÃÜÄÚÈÝ
     * @param password
     *            ½âÃÜÃÜÔ¿
     * @return
     */
    public static String decrypt(byte[] content, String password) {
        try {

//            KeyGenerator kgen = KeyGenerator.getInstance(AES);
//            SecureRandom sr = new SecureRandom();// java pc°æ½âÃÜÉèÖÃ
//             SecureRandom sr = SecureRandom.getInstance(SHA1, CRYPTO);//
//            // Android
//            sr.setSeed(password.getBytes());
//            kgen.init(256, sr);
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
//            Cipher cipher = Cipher.getInstance(AES);// java pc°æÉèÖÃ
             Cipher cipher = Cipher.getInstance(CIPHER);// Android°æÉèÖÃ
//            SecretKeySpec key = new SecretKeySpec(password.getBytes(), AES);
            cipher.init(Cipher.DECRYPT_MODE, createKey(password));// ³õÊ¼»¯
            return new String(cipher.doFinal(content));
        } catch (Exception e) {
            Log.e("error",e.toString());
        }
        return null;
    }
    public static String decryptbyte(byte[] content, String password) {
        try {

            Cipher cipher = Cipher.getInstance(CIPHER);// Android°æÉèÖÃ
            cipher.init(Cipher.DECRYPT_MODE, createKey(password));// ³õÊ¼»¯
            return new String(cipher.doFinal(content));
        } catch (Exception e) {
            Log.e("error",e.toString());
        }
        return null;
    }

    /**
     * ½«¶þ½øÖÆ×ª»»³É16½øÖÆ
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * ½«16½øÖÆ×ª»»Îª¶þ½øÖÆ
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}