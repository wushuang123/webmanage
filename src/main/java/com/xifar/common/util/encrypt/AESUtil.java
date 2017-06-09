package com.xifar.common.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class AESUtil {

	/**
	 * 密钥算法 AES
	 */
	public static final String KEY_ALGORITHM = "AES";

	/**
	 * 加密/解密算法 / 工作模式 / 填充方式
	 * 
	 */
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	private static final Logger log = LoggerFactory.getLogger(AESUtil.class);

	/**
	 * 转换密钥
	 * 
	 * @param 二进制密钥
	 * @return Key 密钥
	 * 
	 */
	private static Key toKey(byte[] key) {
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
		return secretKey;
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * 
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * 
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 还原密钥
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * @param data
	 *            加密内容
	 * @param key
	 *            密钥
	 * @param charsetName
	 *            字符集名称
	 * 
	 * @return 返回值经BASE64编码
	 * 
	 */
	public static String encrypt(String data, String key, String charsetName) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		if (!StringUtils.hasLength(data)) {
			log.warn("待AES加密的数据不能为空");
			return null;
		}
		if (!StringUtils.hasLength(key)) {
			log.warn("AES的加密密钥不能为空");
			return null;
		}
		if (!StringUtils.hasLength(charsetName)) {
			log.warn("AES的加密字符编码不能为空");
			return null;
		}
		byte[] content = data.getBytes(charsetName);
		byte[] binaryKey = Base64.decodeBase64(key);
		byte[] resultArray = encrypt(content, binaryKey);
		String result = null;
		if (null != resultArray) {
			result = Base64.encodeBase64String(resultArray);
		}
		return result;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * 
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);

	}

	/**
	 * @param data
	 *            解密内容(需BASE64解码)
	 * @param key
	 *            密钥(需BASE64解码)
	 * @param charsetName
	 *            字符集名称
	 * @return 返回字符集编码为charsetName的字符串内容
	 * 
	 */
	public static String decrypt(String data, String key, String charsetName) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		if (!StringUtils.hasLength(data)) {
			log.warn("待AES解密的数据不能为空");
			return null;
		}
		if (!StringUtils.hasLength(key)) {
			log.warn("AES的解密密钥不能为空");
			return null;
		}
		if (!StringUtils.hasLength(charsetName)) {
			log.warn("AES的解密字符编码不能为空");
			return null;
		}
		byte[] content = Base64.decodeBase64(data);
		byte[] binaryKey = Base64.decodeBase64(key);
		byte[] resultArray = decrypt(content, binaryKey);
		String result = null;
		if (null != resultArray) {
			result = new String(resultArray, charsetName);
		}
		return result;
	}

	/**
	 * 生成密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 * 
	 */
	public static byte[] initKey() throws NoSuchAlgorithmException {
		// 实例化
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 密钥长度为128位(建议生成256位的，不过需要替换jre和jdk下面的授权jar)
		kg.init(128);
		// 生成密钥
		SecretKey secretKey = kg.generateKey();
		// 获得密钥的二进制编码形式
		return secretKey.getEncoded();
	}
}
