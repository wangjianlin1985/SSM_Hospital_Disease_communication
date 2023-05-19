package dingzhen.common.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import dingzhen.common.util.StochasticUtil;
import dingzhen.entity.sys.User;

/**
 * 
 * 基础信息控制器
 */
public class BaseController<T> {

	protected int page = 1;   //页数
	protected int rows = 10;  //每页条数
	protected String start;   //开始时间
	protected String end;     //结束时间
	public static final String SAVE_SUCCESS = "保存成功";
	public static final String SAVE_FAIL = "对不起！保存失败";
	public static final String UPDATE_SUCCESS = "修改成功";
	public static final String UPDATE_FAIL = "对不起！修改失败";
	public static final String DELETE_SUCCESS = "删除成功";
	public static final String DELETE_FAIL = "对不起！删除失败";
	public static final String DATE_SUCCESS = "数据获取成功";
	public static final String DATE_FAIL = "数据获取失败";
	public static final String OPERATION_SUCCESS = "操作成功";
	public static final String OPERATION_FAIL = "对不起！操作失败";
	public static final int SUCCEED = 1;  // 返回成功 1
	public static final int FAIL = 0;     // 返回失败0
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();	
	}

	/**
	 * 判断是否是超管角色
	 * @return 
	 */
	public boolean isSystemAdmin(){
		User currentUser = (User) getRequest().getSession().getAttribute("currentUser");
		if(currentUser == null || !"1".equals(currentUser.getRoleId())){      
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){	
		return StochasticUtil.get32UUID();
	}
}
