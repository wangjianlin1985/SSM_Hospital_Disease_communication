package dingzhen.service.sys;


import dingzhen.common.base.BaseService;
import dingzhen.entity.sys.Operation;


public interface OperationService extends BaseService<Operation> {
	public void deleteByMenuId(String menuId) throws Exception;
}