package dingzhen.service.sys;

import java.util.Map;

import dingzhen.common.base.BaseService;
import dingzhen.entity.sys.User;

public interface UserService extends BaseService<User>{
	
	// 登录
	public abstract User loginUser(Map<String, String> map) throws Exception;
	
	//通过用户名判断是否存在，（新增时不能重名）
	public abstract User existUserWithUserName(String userName) throws Exception;
	
	// 通过角色判断是否存在
	public abstract User existUserWithRoleId(String roleId) throws Exception;
	
}
