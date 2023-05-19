package dingzhen.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * 编码工具
 */
public class CodeUtil {
	static Logger logger = Logger.getLogger(CodeUtil.class);
	/**
	 * 将unicode转为汉字
	 * @param utfString ：原unicode类型字符串
	 * @return
	 */
	public static String unicode2character(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':value = (value << 4) + aChar - '0';break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':value = (value << 4) + 10 + aChar - 'a';break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':value = (value << 4) + 10 + aChar - 'A';break;
							default:throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	/**
	 * 将汉字转化成unicode
	 * @param gbString
	 * @return
	 */
	public static String character2unicode(String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}

	/**
	 * md5加密
	 * @param plainText：原字符串
	 * @param type：16位/32位加密
	 * @return
	 */
	public static String getMd5(String plainText, int type) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			if (type == 32) {
				return buf.toString();
			} else {
				return buf.toString().substring(8, 24);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
