package dingzhen.service.impl.sys;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.common.base.BaseServiceImpl;
import dingzhen.dao.sys.UserDao;
import dingzhen.entity.sys.User;
import dingzhen.service.sys.UserService;

/**
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Autowired
	private UserDao dao;

	public User loginUser(Map<String, String> map) throws Exception {
		return dao.loginUser(map);
	}

	public User existUserWithUserName(String userName) throws Exception {
		return dao.existUserWithUserName(userName);
	}

	public User existUserWithRoleId(String roleId) throws Exception {
		return dao.existUserWithRoleId(roleId);
	}
	
	
}
