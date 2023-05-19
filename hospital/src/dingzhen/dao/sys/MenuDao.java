package dingzhen.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dingzhen.common.base.BaseDao;
import dingzhen.entity.sys.Menu;

/**
 */
@Repository
public interface MenuDao extends BaseDao<Menu>{
	
	@SuppressWarnings({"rawtypes" })
	public abstract List<Menu> menuTree(Map map) throws Exception;
}
