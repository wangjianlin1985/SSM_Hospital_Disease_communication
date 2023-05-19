package dingzhen.controller.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.StringUtil;
import dingzhen.entity.sys.Operation;
import dingzhen.service.sys.OperationService;

/**：
 * 页面上面的按钮权限
 */
@Controller
@RequestMapping("operation")
@SuppressWarnings({"rawtypes","unchecked"})
public class OperationController extends BaseController<Operation>{
	
	private Operation operation;
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("operationList")
	@ResponseBody
	public ResponseDate list(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			page = Integer.parseInt(request.getParameter("page"));//==null?1:Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));//==null?10:Integer.parseInt(request.getParameter("rows"));
			operation = new Operation();
			operation.setPage((page-1)*rows);
			operation.setRows(rows);
			operation.setMenuId(request.getParameter("menuId"));
			List<Operation> list = operationService.findList(operation);
			int total = operationService.count(operation);
			rd.setRows(list);
			rd.setTotal(total);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("按钮展示错误",e);
		}
		return rd;
	}
	
	
	
	
	@RequestMapping("reserveOperation")
	@ResponseBody
	public ResponseDate reserveMenu(HttpServletRequest request,HttpServletResponse response,Operation operation){
		String operationId = request.getParameter("operationId");
		ResponseDate rd = new ResponseDate();
		try {
			if (StringUtil.isNotEmpty(operationId)) {  //更新操作
				operation.setMenuId(operationId);
				operationService.update(operation);
			} else {
				/**
				 *   为了区分此ID与menuId .
				 *   获取的32位UUID只取前30位
				 */
				operation.setOperationId(get32UUID().substring(0, 30));
				operationService.add(operation);
			}
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("按钮保存错误",e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
	
	
	

	@RequestMapping("deleteOperation")
	@ResponseBody
	public ResponseDate delUser(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			List<Operation> list = new ArrayList<Operation>();
			for (String id : ids) {
				Operation op = new Operation();
				op.setOperationId(id);
				list.add(op);
			}
			operationService.deleteBatch(list);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除按钮错误",e);
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	
}
