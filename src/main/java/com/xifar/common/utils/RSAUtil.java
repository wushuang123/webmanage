package com.xifar.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class RSAUtil {

	/** 非对称加密密钥算法 **/
	public static final String KEY_ALGORITHM = "RSA";
	/** 公钥 **/
	public static final String PUBLIC_KEY = "RSAPublicKey";
	/** 私钥 **/
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	/** RSA密钥长度(默认1024,必须是64的倍数，范围在512-65536之间) **/
	private static final int KEY_SIZE = 512;

	private static final Logger log = LoggerFactory.getLogger(AESUtil.class);

	/**
	 * 公钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            公钥
	 * @param return
	 *            byte[] 解密数据
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException {
		// 取得公钥
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成公钥
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * @param data
	 *            待解密内容(需BASE64编码)
	 * @param key
	 *            密钥(需BASE64编码)
	 * @param charsetName
	 *            返回内容的字符集编码
	 * @return result 解密后的内容
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchPaddingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * 
	 */
	public static String decryptByPublicKey(String data, String key, String charsetName)
			throws UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		if (!StringUtils.hasLength(data)) {
			log.warn("待RSA解密的数据不能为空");
			return null;
		}
		if (!StringUtils.hasLength(key)) {
			log.warn("RSA的解密密钥不能为空");
			return null;
		}
		byte[] content = Base64.decodeBase64(data);
		byte[] binaryKey = Base64.decodeBase64(key);
		byte[] resultArray = decryptByPublicKey(content, binaryKey);
		String result = null;
		if (null != resultArray) {
			result = new String(resultArray, charsetName);
		}
		return result;
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            私钥
	 * @param return
	 *            byte[] 加密数据
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * @param data
	 *            待加密内容
	 * @param key
	 *            密钥(需BASE64编码)
	 * @param charsetName
	 *            内容的字符集编码
	 * 
	 * @return 返回值经过BASE64编码
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchPaddingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * 
	 */
	public static String encryptByPrivateKey(String data, String key, String charsetName)
			throws UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		if (!StringUtils.hasLength(data)) {
			log.warn("待RSA加密的数据不能为空");
			return null;
		}
		if (!StringUtils.hasLength(key)) {
			log.warn("RSA的加密密钥不能为空");
			return null;
		}
		byte[] content = data.getBytes(charsetName);
		byte[] binaryKey = Base64.decodeBase64(key);
		byte[] resultArray = encryptByPrivateKey(content, binaryKey);
		String result = null;
		if (null != resultArray) {
			result = Base64.encodeBase64String(resultArray);
		}
		return result;
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 *            密钥Map
	 * @return byte[] 私钥
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 *            密钥Map
	 * @return byte[] 私钥
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	/**
	 * 初始化密钥
	 * 
	 * @return Map 密钥Map
	 * @throws NoSuchAlgorithmException
	 * 
	 */
	public static Map<String, Object> initKey() throws NoSuchAlgorithmException {
		// 实例化密钥对生成器
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥生成器
		keyPairGenerator.initialize(KEY_SIZE);
		// 生成密钥对
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 封装入Map中
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

}
