package dingzhen.service.impl.sys;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.common.base.BaseServiceImpl;
import dingzhen.dao.sys.RoleDao;
import dingzhen.entity.sys.Role;
import dingzhen.service.sys.RoleService;

/**
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	@Autowired
	private RoleDao dao;

	public Role existRoleWithRoleName(String roleName) throws Exception {
		return dao.existRoleWithRoleName(roleName);
	}

	@SuppressWarnings("rawtypes")
	public void deleteRoleByRoleIds(Map map) throws Exception {
		dao.deleteRoleByRoleIds(map);
	}

	

}
