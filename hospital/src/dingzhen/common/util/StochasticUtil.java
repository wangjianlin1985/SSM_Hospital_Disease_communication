package dingzhen.common.util;

import java.util.Random;
import java.util.UUID;

/**
  * 生成随机数字/字符串/uuid等工具
 */
public class StochasticUtil {

	/**
	 * 随机整数
	 * @param num
	 * @return
	 */
	public static int getRandom(int num){
		Random random = new Random();
		return random.nextInt(num);
	}
	
	
	/**
	 * 随机字母
	 * @param num
	 * @return
	 */
	public static String getRandom(double num){
		String pool = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		String str = "";
		for (long i = 0; i < num; i++) {
			int j = new Random().nextInt(pool.length());
			str += pool.charAt(j);
		}
		return str;
	}
	
	
	/**
	 * 生成随机UUID
	 * @return
	 */
	public static String get32UUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	
	/**
	 * 根据字符串生成固定uuid
	 * @param name
	 * @return
	 */
	public static String get32UUID(String name){
		UUID uuid=UUID.nameUUIDFromBytes(name.getBytes());
	    return uuid.toString().replace("-", "");
	}
	
	
	/**
	 * 随机生成几位数字
	 * 这里的转型用的是Long。所以参数num不宜过大
	 * @param num 
	 * @return
	 */
	public static String getNum(int num){
		String string = "";
		while(num > 1){
			string += "0";
			num --;
		}
		long b = Long.parseLong(1 + string);
		long a = (long) ((Math.random()*9+1)*b);
		return a+"";
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(get32UUID());
	}
	
	
	
}
