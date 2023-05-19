package dingzhen.dao.sys;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dingzhen.common.base.BaseDao;
import dingzhen.entity.sys.User;


/**
 *@author: wangq
 *@date: 2015-5-18上午10:30:05
 *@version:
 *@description：
 */
@Repository
public interface UserDao extends BaseDao<User>{

	// 登录
	public abstract User loginUser(Map<String, String> map) throws Exception;
	
	//通过用户名判断是否存在，（新增时不能重名）
	public abstract User existUserWithUserName(String userName) throws Exception;
	
	// 通过角色判断是否存在
	public abstract User existUserWithRoleId(String roleId) throws Exception;
	
}
