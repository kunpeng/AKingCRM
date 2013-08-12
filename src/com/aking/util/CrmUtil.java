package com.aking.util;

import java.security.MessageDigest;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CrmUtil {
	private static final String UNICODE_FORMAT = "UTF8";
	private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private KeySpec ks;
	private SecretKeyFactory skf;
	private Cipher cipher;
	private byte[] arrayBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	private SecretKey key;

	private static CrmUtil crmUtil;

	private CrmUtil() throws Exception {
		myEncryptionKey = "this-is-encrypt-key-you-can-write-anything";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		ks = new DESedeKeySpec(arrayBytes);
		skf = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = skf.generateSecret(ks);
	}

	public static CrmUtil getInstance() throws Exception {
		if (crmUtil == null) {
			synchronized (CrmUtil.class) {
				crmUtil = new CrmUtil();
			}
		}
		return crmUtil;
	}

	public static String str2SHA(String str) throws Exception {
		CrmUtil crmUtil = new CrmUtil();
		MessageDigest sha = MessageDigest.getInstance("SHA");
		sha.update(str.getBytes());
		String strdigest = new String(crmUtil.byte2hex(sha.digest()));
		return strdigest;
	}

	private String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString.getBytes());
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

	public static void main(String[] args) throws Exception {
		// String testStr = "测试";
		// MessageDigest sha = MessageDigest.getInstance("SHA");
		// sha.update(testStr.getBytes());
		// byte[] b = sha.digest();
		// System.out.println(b);
		// for (int n = 0; n < b.length; n++) {
		// System.out.println(java.lang.Integer.toHexString(b[n] & 0XFF));
		// if (stmp.length() == 1)
		// hs = hs + "0" + stmp;
		// else
		// hs = hs + stmp;
		// if (n < b.length - 1)
		// hs = hs + "";
		// }
		// System.out.println(sha.digest());
		CrmUtil td = CrmUtil.getInstance();

		String target = "cs";
		String encrypted = td.encrypt(target);
		String decrypted = td.decrypt(encrypted);

		System.out.println("String To Encrypt: " + target);
		System.out.println("Encrypted String: " + encrypted);
		System.out.println("Decrypted String: " + decrypted);

	}
}
