package dingzhen.common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * 页面wirter
 */
public class WriterUtil {
	
	public static void write(HttpServletResponse response,String obj){
		try {
			 response.getWriter().write(obj);
		} catch (IOException e) {
			
		}
	}
}
