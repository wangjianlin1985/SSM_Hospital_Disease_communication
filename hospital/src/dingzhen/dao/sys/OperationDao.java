package dingzhen.dao.sys;


import org.springframework.stereotype.Repository;

import dingzhen.common.base.BaseDao;
import dingzhen.entity.sys.Operation;

/**
 * 
 */
@Repository
public interface OperationDao extends BaseDao<Operation>{
	public void deleteByMenuId(String menuId) throws Exception;
}
