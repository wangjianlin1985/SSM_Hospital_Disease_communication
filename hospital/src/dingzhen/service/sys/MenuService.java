package dingzhen.service.sys;

import java.util.List;
import java.util.Map;

import dingzhen.common.base.BaseService;
import dingzhen.entity.sys.Menu;


/**
 */
public interface MenuService extends BaseService<Menu>{

	@SuppressWarnings("rawtypes")
	public List<Menu> menuTree(Map map) throws Exception;

}
