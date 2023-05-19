package dingzhen.controller.info;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.StringUtil;
import dingzhen.entity.info.Condition;
import dingzhen.entity.info.Reply;
import dingzhen.entity.sys.User;
import dingzhen.service.info.ConditionService;
import dingzhen.service.info.ReplyService;

@Controller
@RequestMapping("reply")
@SuppressWarnings("all")
public class ReplyController extends BaseController<Reply>{

	@Autowired
	private ReplyService replyService;
	@Autowired
	private ConditionService conditionService;
	
	// 公共留言
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		try {
			String conditionid = request.getParameter("conditionid");
			Condition c = new Condition();
			c.setId(conditionid);
			Reply reply = new Reply();
			reply.setCondition(c);
			reply.setType("1");
			List<Reply> list = replyService.findList(reply);
			
			Condition condition = conditionService.findOne(c);
			request.setAttribute("list", list);
			request.setAttribute("condition", condition);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "info/reply/edit";
	}
	
	
	// 私聊
	@RequestMapping("indexOneVSOne")
	public String indexOneVSOne(HttpServletRequest request){
		try {
			String conditionid = request.getParameter("conditionid");
			String patientid = request.getParameter("patientid");
			String doctorid = request.getParameter("doctorid");
			User currentUser = (User) request.getSession().getAttribute("currentUser");
			Condition c = new Condition();
			c.setId(conditionid);
			Reply reply = new Reply();
			reply.setCondition(c);
			
			User doctor = new User();doctor.setId(doctorid);
			reply.setDoctor(doctor);
			
			User patient = new User();patient.setId(patientid);
			reply.setPatient(patient);
			
			if("815f6fd1f3904e25b779120f7e3b63be".equals(currentUser.getRoleId())){ // 当前登录用户是医生。
				reply.setDoctor(currentUser);
			} else {
				reply.setPatient(currentUser);
			}
			reply.setType("2");
			List<Reply> list = replyService.findListOneVSOne(reply);
			
			Condition condition = conditionService.findOne(c);
			request.setAttribute("patientid", reply.getPatient().getId());
			request.setAttribute("doctorid", reply.getDoctor().getId());
			request.setAttribute("list", list);
			request.setAttribute("condition", condition);
			request.setAttribute("currentUser", currentUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "info/reply/editOneVSOne";
	}
	
	
	
	// 私聊列表
	@RequestMapping("sllist")
	@ResponseBody
	public ResponseDate sllist(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String conditionid = request.getParameter("conditionid");
			Condition condition = new Condition();
			condition.setId(conditionid);
			Reply reply = new Reply();
			reply.setCondition(condition);
			List<Reply> list = replyService.findSllist(reply);
			rd.setRows(list);
			rd.setTotal(10);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
		
	
	
	
	@RequestMapping("addReply")
	@ResponseBody
	@Transactional
	public ResponseDate addReply(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		String conditionid = request.getParameter("conditionid");
		Condition condition = new Condition();condition.setId(conditionid);
		String remarks = request.getParameter("remarks");
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		String other = "【病人】";
		if("815f6fd1f3904e25b779120f7e3b63be".equals(currentUser.getRoleId())){
			other = "【医生】";
		}
		try {
			Reply reply = new Reply();
			reply.setId(get32UUID());
			reply.setTime(time);
			reply.setRemarks(remarks);
			reply.setCondition(condition);
			reply.setUser(currentUser);
			reply.setType("1");
			replyService.add(reply);
			rd.setSuccess(true);
			Map map = new HashMap<>();
			map.put("time", time);
			map.put("rolename", other);
			map.put("realname", currentUser.getRealname());
			rd.setMap(map);
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg("提交失败");
		}
		return rd;
	}
	
	
	
	@RequestMapping("addReplyOneVSOne")
	@ResponseBody
	@Transactional
	public ResponseDate addReplyOneVSOne(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		String conditionid = request.getParameter("conditionid");
		Condition condition = new Condition();condition.setId(conditionid);
		String remarks = request.getParameter("remarks");
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String patientid = request.getParameter("patientid");
		String doctorid = request.getParameter("doctorid");
		String touserid = "";
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if("815f6fd1f3904e25b779120f7e3b63be".equals(currentUser.getRoleId())){ // 说明是医生，则推送到病人
			touserid =  patientid;
		} else {
			touserid = doctorid;
		}
		try {
			Reply reply = new Reply();
			reply.setId(get32UUID());
			reply.setTime(time);
			reply.setRemarks(remarks);
			reply.setCondition(condition);
			User doctor = new User();
			doctor.setId(doctorid);
			reply.setDoctor(doctor);
			
			User patient = new User();
			patient.setId(patientid);
			reply.setPatient(patient);
			reply.setUser(currentUser);
			
			reply.setType("2");
			replyService.addOneVSOne(reply);
			rd.setSuccess(true);
			Map map = new HashMap<>();
			map.put("time", time);
			map.put("rolename", currentUser.getRoleName());
			map.put("realname", currentUser.getRealname());
			map.put("remarks", remarks);
			rd.setMap(map);
			
			JSONObject object = new JSONObject();
			object.put("map", map);
			
			WebSocketService.sendMessage(object.toJSONString(), touserid); // 推送
			
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg("提交失败");
		}
		return rd;
	}
	
	
	
	@RequestMapping("adminIndex")
	public String adminIndex(HttpServletRequest request){
		try {
			String conditionid = request.getParameter("conditionid");
			Condition c = new Condition();
			c.setId(conditionid);
			Reply reply = new Reply();
			reply.setCondition(c);
			List<Reply> list = replyService.findList(reply);
			Condition condition = conditionService.findOne(c);
			request.setAttribute("list", list);
			request.setAttribute("condition", condition);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "info/reply/editadmin";
	}
	
	
	
	
	@RequestMapping("deleteReply")
	@ResponseBody
	@Transactional
	public ResponseDate deleteReply(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				Reply reply = new Reply();
				reply.setId(id);
				replyService.delete(reply);
			}
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
}
