package com.shenl.utils.MyUtils;


import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

    private static String DES_Algorithm = "DES";
    ///////////////////////////////////////////////////////////////////////////
    // 哈希加密相关
    ///////////////////////////////////////////////////////////////////////////
    private static String TripleDES_Algorithm = "DESede";
    private static String AES_Algorithm = "AES";
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    /**
     * DES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static String DES_Transformation = "DES/ECB/NoPadding";
    /**
     * 3DES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static String TripleDES_Transformation = "DESede/ECB/NoPadding";
    /**
     * AES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static String AES_Transformation = "AES/ECB/NoPadding";


    /**
     * TODO 功能：md2加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String MD2(String data) {
        return MD2(data.getBytes());
    }

    /**
     * TODO 功能：md2加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String MD2(byte[] data) {
        return bytes2HexString(encryptMD2(data));
    }

    //md2加密
    public static byte[] encryptMD2(byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /**
     * TODO 功能：MD5加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String MD5(String data) {
        return MD5(data.getBytes());
    }

    /**
     * TODO 功能：MD5加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String MD5(byte[] data) {
        return bytes2HexString(encryptMD5(data));
    }

    /**
     * TODO 功能：MD5加密 可加盐
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String MD5(String data, String salt) {
        return bytes2HexString(encryptMD5((data + salt).getBytes()));
    }


    /**
     * TODO 功能：MD5加密 可加盐
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String MD5(byte[] data, byte[] salt) {
        if (data == null || salt == null) return null;
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return bytes2HexString(encryptMD5(dataSalt));
    }

    //MD5加密
    public static byte[] encryptMD5(byte[] data) {
        return hashTemplate(data, "MD5");
    }


    /**
     * TODO 功能：SHA1加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA1(String data) {
        return SHA1(data.getBytes());
    }

    /**
     * TODO 功能：SHA1加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA1(byte[] data) {
        return bytes2HexString(encryptSHA1(data));
    }

    //SHA1加密
    public static byte[] encryptSHA1(byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    /**
     * TODO 功能：SHA224加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA224(String data) {
        return SHA224(data.getBytes());
    }

    /**
     * TODO 功能：SHA224加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA224(byte[] data) {
        return bytes2HexString(encryptSHA224(data));
    }

    //SHA224加密
    public static byte[] encryptSHA224(byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * TODO 功能：SHA256加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA256(String data) {
        return SHA256(data.getBytes());
    }

    /**
     * TODO 功能：SHA256加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA256(byte[] data) {
        return bytes2HexString(encryptSHA256(data));
    }

    //SHA256加密
    public static byte[] encryptSHA256(byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    /**
     * TODO 功能：SHA384加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA384(String data) {
        return SHA384(data.getBytes());
    }

    /**
     * TODO 功能：SHA384加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA384(byte[] data) {
        return bytes2HexString(encryptSHA384(data));
    }

    //SHA384加密
    public static byte[] encryptSHA384(byte[] data) {
        return hashTemplate(data, "SHA384");
    }

    /**
     * TODO 功能：SHA512加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA512(String data) {
        return SHA512(data.getBytes());
    }

    /**
     * TODO 功能：SHA512加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String SHA512(byte[] data) {
        return bytes2HexString(encryptSHA512(data));
    }

    //SHA512加密
    public static byte[] encryptSHA512(byte[] data) {
        return hashTemplate(data, "SHA512");
    }


    //hash加密模板
    private static byte[] hashTemplate(byte[] data, String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * TODO 功能：HmacMD5加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacMD5(String data, String key) {
        return HmacMD5(data.getBytes(), key.getBytes());
    }

    /**
     * TODO 功能：HmacMD5加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacMD5(byte[] data, byte[] key) {
        return bytes2HexString(encryptHmacMD5(data, key));
    }

    //HmacMD5加密
    public static byte[] encryptHmacMD5(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacMD5");
    }

    /**
     * TODO 功能：HmacSHA1加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA1(String data, String key) {
        return HmacSHA1(data.getBytes(), key.getBytes());
    }

    /**
     * TODO 功能：HmacSHA1加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA1(byte[] data, byte[] key) {
        return bytes2HexString(encryptHmacSHA1(data, key));
    }

    //HmacSHA1加密
    public static byte[] encryptHmacSHA1(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    /**
     * TODO 功能：HmacSHA224加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA224(String data, String key) {
        return HmacSHA224(data.getBytes(), key.getBytes());
    }

    /**
     * TODO 功能：HmacSHA224加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA224(byte[] data, byte[] key) {
        return bytes2HexString(encryptHmacSHA224(data, key));
    }

    //HmacSHA224加密
    public static byte[] encryptHmacSHA224(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    /**
     * TODO 功能：HmacSHA256加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA256(String data, String key) {
        return HmacSHA256(data.getBytes(), key.getBytes());
    }

    /**
     * TODO 功能：HmacSHA256加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA256(byte[] data, byte[] key) {
        return bytes2HexString(encryptHmacSHA256(data, key));
    }

    //HmacSHA256加密
    public static byte[] encryptHmacSHA256(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA256");
    }


    /**
     * TODO 功能：HmacSHA384加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA384(String data, String key) {
        return HmacSHA384(data.getBytes(), key.getBytes());
    }

    /**
     * TODO 功能：HmacSHA384加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA384(byte[] data, byte[] key) {
        return bytes2HexString(encryptHmacSHA384(data, key));
    }

    //HmacSHA384加密
    public static byte[] encryptHmacSHA384(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    /**
     * TODO 功能：HmacSHA512加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA512(String data, String key) {
        return HmacSHA512(data.getBytes(), key.getBytes());
    }

    /**
     * TODO 功能：HmacSHA512加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String HmacSHA512(byte[] data, byte[] key) {
        return bytes2HexString(encryptHmacSHA512(data, key));
    }

    //HmacSHA512加密
    public static byte[] encryptHmacSHA512(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    //Hmac加密模板
    private static byte[] hmacTemplate(final byte[] data, final byte[] key, final String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * TODO 功能：DES加密后转为Base64编码
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static byte[] DES2Base64(byte[] data, byte[] key) {
        return base64Encode(DES(data, key));
    }


    /**
     * TODO 功能：DES加密后转为16进制
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String DES2HexString(byte[] data, byte[] key) {
        return bytes2HexString(DES(data, key));
    }

    /**
     * TODO 功能：DES加密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static byte[] DES(byte[] data, byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, true);
    }

    /**
     * TODO 功能：DES解密Base64编码密文
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static byte[] decryptBase64DES(byte[] data, byte[] key) {
        return decryptDES(base64Decode(data), key);
    }

    /**
     * TODO 功能：DES解密16进制密文
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static byte[] decryptHexStringDES(String data, byte[] key) {
        return decryptDES(hexString2Bytes(data), key);
    }

    /**
     * TODO 功能：DES解密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static byte[] decryptDES(byte[] data, byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, false);
    }

    /**
     * TODO 功能：3DES加密后转为Base64编码
     *
     * @param data 明文
     * @param key  24字节秘钥
     * @return Base64密文
     */
    public static byte[] DES32Base64(byte[] data, byte[] key) {
        return base64Encode(DES3(data, key));
    }

    /**
     * TODO 功能：3DES加密后转为16进制
     *
     * @param data 明文
     * @param key  24字节秘钥
     * @return 16进制密文
     */
    public static String DES32HexString(byte[] data, byte[] key) {
        return bytes2HexString(DES3(data, key));
    }

    /**
     * TODO 功能：3DES加密
     *
     * @param data 明文
     * @param key  24字节密钥
     * @return 密文
     */
    public static byte[] DES3(byte[] data, byte[] key) {
        return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, true);
    }

    /**
     * 3DES解密Base64编码密文
     *
     * @param data Base64编码密文
     * @param key  24字节秘钥
     * @return 明文
     */
    public static byte[] decryptBase64_3DES(byte[] data, byte[] key) {
        return decrypt3DES(base64Decode(data), key);
    }

    /**
     * 3DES解密16进制密文
     *
     * @param data 16进制密文
     * @param key  24字节秘钥
     * @return 明文
     */
    public static byte[] decryptHexString3DES(String data, byte[] key) {
        return decrypt3DES(hexString2Bytes(data), key);
    }

    /**
     * TODO 功能：3DES解密方式
     * <p>
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static byte[] decrypt3DES(byte[] data, byte[] key) {
        return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, false);
    }

    /**
     * TODO 功能：AES加密后转为Base64编码
     *
     * @param data 明文
     * @param key  16、24、32字节秘钥
     * @return Base64密文
     */
    public static byte[] AES2Base64(byte[] data, byte[] key) {
        return base64Encode(AES(data, key));
    }

    /**
     * TODO 功能：AES加密后转为16进制
     *
     * @param data 明文
     * @param key  16、24、32字节秘钥
     * @return 16进制密文
     */
    public static String AES2HexString(byte[] data, byte[] key) {
        return bytes2HexString(AES(data, key));
    }

    /**
     * TODO 功能：AES加密方式
     * <p>
     * 参数说明:   key  16、24、32字节秘钥
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static byte[] AES(byte[] data, byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, true);
    }

    /**
     * TODO 功能：AES解密Base64编码密文
     *
     * @param data Base64编码密文
     * @param key  16、24、32字节秘钥
     * @return 明文
     */
    public static byte[] decryptBase64AES(byte[] data, byte[] key) {
        return decryptAES(base64Decode(data), key);
    }

    /**
     * TODO 功能：AES解密16进制密文
     *
     * @param data 16进制密文
     * @param key  16、24、32字节秘钥
     * @return 明文
     */
    public static byte[] decryptHexStringAES(String data, byte[] key) {
        return decryptAES(hexString2Bytes(data), key);
    }

    /**
     * TODO 功能：AES解密
     * <p>
     * 参数说明:   key  16、24、32字节秘钥
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static byte[] decryptAES(byte[] data, byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, false);
    }

    /*
     * DES加密模板
     *
     * @param data           数据
     * @param key            秘钥
     * @param algorithm      加密算法
     * @param transformation 转变
     * @param isEncrypt      {@code true}: 加密 {@code false}: 解密
     * @return 密文或者明文，适用于DES，3DES，AES
     */
    public static byte[] desTemplate(final byte[] data, final byte[] key, final String algorithm, final String transformation, final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            SecureRandom random = new SecureRandom();
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytes2HexString(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static byte[] base64Encode(byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * TODO 功能：String转Base64字符串
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static String Str2Base64(String message) {
        return Base64.encodeToString(message.getBytes(), Base64.DEFAULT);
    }

    /**
     * TODO 功能：Base64字符串转bytes
     *
     * 参数说明:
     * 作    者:   沈 亮
     * 创建时间:   2019/7/2
     */
    public static byte[] Base642Byte(String base64Message) {
        return Base64.decode(base64Message, Base64.DEFAULT);
    }

    //判断是否有空格
    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
