package dingzhen.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.common.base.BaseServiceImpl;
import dingzhen.dao.sys.OperationDao;
import dingzhen.entity.sys.Operation;
import dingzhen.service.sys.OperationService;

/**
 */
@Service("operationService")
public class OperationServiceImpl extends BaseServiceImpl<Operation> implements OperationService{
	
	@Autowired
	private OperationDao dao;

	public void deleteByMenuId(String menuId) throws Exception {
		dao.deleteByMenuId(menuId);
	}
	
}
