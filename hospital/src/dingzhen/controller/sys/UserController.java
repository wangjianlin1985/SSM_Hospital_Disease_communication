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
import dingzhen.entity.sys.User;
import dingzhen.service.sys.UserService;

/**
 * 用户
 */

@Controller
@RequestMapping("user")
@SuppressWarnings("rawtypes")
public class UserController extends BaseController<User>{

	private User user;
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("userIndex")
	public String index(){
		return "sys/user";
	}
	
	
	@RequestMapping("userList")
	@ResponseBody
	public ResponseDate<User> userList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<User> rd = new ResponseDate<User>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			user = new User();
			user.setPage((page-1)*rows);
			user.setRows(rows);
			user.setName(request.getParameter("name"));
			user.setRoleId(request.getParameter("roleId"));
			List<User> list= userService.findList(user);
			int total = userService.count(user);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	// 新增或修改
	@RequestMapping("reserveUser")
	@ResponseBody
	public ResponseDate reserveUser(HttpServletRequest request,User user,HttpServletResponse response){
		String id = request.getParameter("id");
		ResponseDate rd = new ResponseDate();
		try {
			if (StringUtil.isNotEmpty(id)) {   // id不为空 说明是修改
				user.setId(id);
				userService.update(user);
				rd.setSuccess(true);
			}else {   // 添加
				if(userService.existUserWithUserName(user.getName())==null){  // 没有重复可以添加
					user.setId(get32UUID());
					userService.add(user);
					rd.setSuccess(true);
				} else {
					rd.setSuccess(true);
					rd.setErrorMsg("该用户名已被占用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
	
	
	@RequestMapping("deleteUser")
	@ResponseBody
	public ResponseDate delUser(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			List<User> list = new ArrayList<User>();
			for (String id : ids) {
				User user = new User();
				user.setId(id);
				list.add(user);
			}
			userService.deleteBatch(list);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
}
