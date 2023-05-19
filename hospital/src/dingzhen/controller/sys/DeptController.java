package dingzhen.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.StringUtil;
import dingzhen.entity.sys.Dept;
import dingzhen.service.sys.DeptService;

@Controller
@RequestMapping("dept")
public class DeptController extends BaseController<Dept>{

	@Autowired
	private DeptService deptService;
	
	@RequestMapping("deptIndex")
	public String index(){
		return "sys/dept";
	}
	
	
	// 列表展示科室信息
	@RequestMapping("deptList")
	@ResponseBody
	public ResponseDate<Dept> deptList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Dept> rd = new ResponseDate<Dept>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Dept dept = new Dept();
			dept.setPage((page-1)*rows);
			dept.setRows(rows);
			List<Dept> list= deptService.findList(dept);
			int total = deptService.count(dept);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	// 新增或修改
	@SuppressWarnings("rawtypes")
	@RequestMapping("reserveDept")
	@ResponseBody
	public ResponseDate reserveDept(HttpServletRequest request,Dept dept,HttpServletResponse response){
		String id = request.getParameter("id");
		ResponseDate rd = new ResponseDate();
		try {
			if (StringUtil.isNotEmpty(id)) {   // id不为空 说明是修改
				dept.setId(id);
				deptService.update(dept);
				rd.setSuccess(true);
			}else {   // 添加
				dept.setId(get32UUID());
				deptService.add(dept);
				rd.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
	
	
	@RequestMapping("deleteDept")
	@ResponseBody
	@Transactional
	public ResponseDate deleteDept(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				Dept dept = new Dept();
				dept.setId(id);
				deptService.delete(dept);
			}
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	
	@RequestMapping("deptData")
	@ResponseBody
	public List<Map<String, Object>> deptData() throws Exception{
		List<Dept> deptList = deptService.findList(new Dept());
		List<Map<String, Object>> list = new ArrayList<>();
		for(Dept c : deptList){
			Map<String, Object> map = new HashMap<>();
			map.put("id", c.getId());
			map.put("name", c.getName());
			list.add(map);
		}
		return list;
	}
	
	
}
