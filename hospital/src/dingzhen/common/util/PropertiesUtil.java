package dingzhen.common.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件工具
 */
public final class PropertiesUtil {
	private static Properties prop;

	private PropertiesUtil() {
	}

	static {
		reload();
	}

	/**
	 * 加载资源配置文件
	 * @return
	 */
	public static boolean reload() {
		boolean flag = false;
		prop = new Properties();
		try {
			prop.load(PropertiesUtil.class.getResourceAsStream("/config.properties"));
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static Properties getSysProperties() {
		return prop;
	}

	/**
	 * 获取指定的系统属性值
	 * 
	 * @param key
	 *            指定的属性名称
	 * @return 指定的系统属性值
	 */
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	/**
	 * 获取指定的系统属性值
	 * 
	 * @param key
	 *            指定的属性名称
	 * @param defaultVal
	 *            默认值
	 * @return 指定的系统属性值
	 */
	public static String getProperty(String key, String defaultVal) {
		return prop.getProperty(key, defaultVal);
	}
	
}

