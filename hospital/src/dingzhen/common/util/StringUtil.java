package dingzhen.common.util;



/**
 * 
 */
public class StringUtil {
	
	/**
	 * 判断字符串是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if("".equals(str)|| str==null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断字符串不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(!"".equals(str)&&str!=null){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 判断某一个字符串数组中是否含有某一字符串
	 * @param str
	 * @param strArr
	 * @return
	 */
	public static boolean existStrArr(String str,String []strArr){
		for(int i=0;i<strArr.length;i++){
			if(strArr[i].equals(str)){
				return true;
			}
		}
		return false;
	}
	
	
	
}
