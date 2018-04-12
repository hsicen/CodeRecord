package com.hsc.vince.androidmvp.net;

import android.util.Base64;

import com.facebook.stetho.common.LogUtil;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * <p>作者：黄思程  2018/4/11 16:10
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：加密解密类
 */
public class AESCrypt {
    private static final String UTF8 = "UTF-8";
    /**
     * 加密的单例对象
     */
    private static AESCrypt aesCrypt;
    private final Cipher cipher;
    private final SecretKeySpec key;
    private AlgorithmParameterSpec spec;

    /**
     * 初始化加密
     *
     * @throws NoSuchAlgorithmException     NoSuchAlgorithmException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     * @throws NoSuchPaddingException       NoSuchPaddingException
     */
    private AESCrypt() throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String formatTime = ApiStore.APPKEY;
        //加密种子
        String sSEED16CHARACTER = formatTime + "gou";
        sSEED16CHARACTER = md5Encryption(sSEED16CHARACTER).replaceAll("\n", "").substring(0, 8);
        digest.update(sSEED16CHARACTER.getBytes(UTF8));
        byte[] keyBytes = new byte[32];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        key = new SecretKeySpec(keyBytes, "AES");
        spec = getIV();
    }

    /**
     * @return 获取AES加密单例
     * @throws NoSuchPaddingException       NoSuchPaddingException
     * @throws NoSuchAlgorithmException     NoSuchPaddingException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static AESCrypt getInstance() throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        synchronized (AESCrypt.class) {
            if (aesCrypt == null) {
                aesCrypt = new AESCrypt();
            }
        }
        return aesCrypt;
    }

    /**
     * 对字符串进行MD5加密(MD5加密，32位)
     *
     * @param str 要加密的字符串
     * @return 加密后的文本
     */
    private static String md5Encryption(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 指定初始化向量
     *
     * @return 初始化向量
     */
    private AlgorithmParameterSpec getIV() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    /**
     * 加密
     *
     * @param plainText 原始文本
     * @return 加密后的文本
     * @throws InvalidAlgorithmParameterException InvalidAlgorithmParameterException
     * @throws InvalidKeyException                InvalidKeyException
     * @throws UnsupportedEncodingException       UnsupportedEncodingException
     * @throws BadPaddingException                BadPaddingException
     * @throws IllegalBlockSizeException          IllegalBlockSizeException
     */
    public String encrypt(String plainText) throws InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(UTF8));
        String encryptedText = new String(Base64.encode(encrypted, Base64.DEFAULT), UTF8);
        return encryptedText.replaceAll("\n", "");
    }

    /**
     * 解密
     *
     * @param cryptedText 加密文本
     * @return 解密后的文本
     * @throws InvalidKeyException                InvalidKeyException
     * @throws InvalidAlgorithmParameterException InvalidAlgorithmParameterException
     * @throws UnsupportedEncodingException       UnsupportedEncodingException
     * @throws BadPaddingException                BadPaddingException
     * @throws IllegalBlockSizeException          IllegalBlockSizeException
     */
    public String decrypt(String cryptedText) throws InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = Base64.decode(cryptedText.getBytes(UTF8), Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(bytes);
        return new String(decrypted, UTF8);
    }
}
