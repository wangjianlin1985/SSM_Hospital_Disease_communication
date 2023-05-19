package dingzhen.controller.info;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import dingzhen.entity.info.Condition;
import dingzhen.entity.sys.Dept;
import dingzhen.entity.sys.User;
import dingzhen.service.info.ConditionService;
import dingzhen.service.info.ReplyService;

@Controller
@RequestMapping("condition")
@SuppressWarnings("rawtypes")
public class ConditionController extends BaseController<Condition> {
	
	@Autowired
	private ConditionService conditionService;
	@Autowired
	private ReplyService replyService;
	
	
	/***********************病人功能开始****************************/
	
	// 我的病情
	@RequestMapping("patientIndex")
	public String patientIndex(){
		return "info/condition/patient";
	}
	
	@RequestMapping("patientList")
	@ResponseBody
	public ResponseDate<Condition> conditionList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Condition> rd = new ResponseDate<Condition>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Condition condition = new Condition();
			condition.setPage((page-1)*rows);
			condition.setRows(rows);
			User currentUser = (User) request.getSession().getAttribute("currentUser");
			condition.setUser(currentUser);
			List<Condition> list= conditionService.findList(condition);
			int total = conditionService.count(condition);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	
	// 新增或修改
	@RequestMapping("reserveCondition")
	@ResponseBody
	public ResponseDate reserveDept(HttpServletRequest request,Condition condition,HttpServletResponse response){
		String id = request.getParameter("id");
		ResponseDate rd = new ResponseDate();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		condition.setTime(time);
		try {
			if (StringUtil.isNotEmpty(id)) {   // id不为空 说明是修改
				condition.setId(id);
				conditionService.update(condition);
				rd.setSuccess(true);
			}else {   // 添加
				condition.setId(get32UUID());
				User currentUser = (User) request.getSession().getAttribute("currentUser");
				condition.setUser(currentUser);
				conditionService.add(condition);
				rd.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
		
	
	@RequestMapping("deleteCondition")
	@ResponseBody
	@Transactional
	public ResponseDate deleteDept(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				Condition condition = new Condition();
				condition.setId(id);
				conditionService.delete(condition);
				
				replyService.deleletByConditionid(id);
				
			}
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	/***********************病人功能结束****************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***********************医生功能结束****************************/
	// 我的病情
	@RequestMapping("doctorIndex")
	public String doctorIndex(){
		return "info/condition/doctor";
	}
	
	@RequestMapping("doctorList")
	@ResponseBody
	public ResponseDate<Condition> doctorList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Condition> rd = new ResponseDate<Condition>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Condition condition = new Condition();
			condition.setPage((page-1)*rows);
			condition.setRows(rows);
			condition.setTitle(request.getParameter("title"));
			// 只查看本科室的
			User currentUser = (User) request.getSession().getAttribute("currentUser");
			condition.setDept(currentUser.getDept());
			List<Condition> list= conditionService.findList(condition);
			int total = conditionService.count(condition);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	/***********************医生功能结束****************************/
	
	

	
	/***********************管理员功能开始****************************/
	@RequestMapping("adminIndex")
	public String adminIndex(){
		return "info/condition/admin";
	}
	
	@RequestMapping("adminList")
	@ResponseBody
	public ResponseDate<Condition> adminList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Condition> rd = new ResponseDate<Condition>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Condition condition = new Condition();
			condition.setPage((page-1)*rows);
			condition.setRows(rows);
			condition.setTitle(request.getParameter("title"));
			
			String realname = request.getParameter("realname");
			if(StringUtil.isNotEmpty(realname)){
				User user = new User();
				user.setRealname(realname);
				condition.setUser(user);
			}
			
			String deptid = request.getParameter("deptid");
			if(StringUtil.isNotEmpty(deptid)){
				Dept dept = new Dept();
				dept.setId(deptid);
				condition.setDept(dept);
			}
			List<Condition> list= conditionService.findList(condition);
			int total = conditionService.count(condition);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	@RequestMapping("clearReply")
	@ResponseBody
	@Transactional
	public ResponseDate clearReply(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				Condition condition = new Condition();
				condition.setId(id);
				
				replyService.deleletByConditionid(id);
			}
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	/***********************管理员功能结束****************************/
}
