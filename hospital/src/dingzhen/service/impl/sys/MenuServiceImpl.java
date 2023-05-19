package dingzhen.service.impl.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.common.base.BaseServiceImpl;
import dingzhen.dao.sys.MenuDao;
import dingzhen.entity.sys.Menu;
import dingzhen.service.sys.MenuService;

/**
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService{

	@Autowired
	private MenuDao dao;

	@SuppressWarnings("rawtypes")
	public List<Menu> menuTree(Map map) throws Exception {
		return dao.menuTree(map);
	}
	
	
}
