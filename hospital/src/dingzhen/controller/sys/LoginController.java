package dingzhen.controller.sys;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.StringUtil;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.sys.Menu;
import dingzhen.entity.sys.Role;
import dingzhen.entity.sys.User;
import dingzhen.service.sys.MenuService;
import dingzhen.service.sys.RoleService;
import dingzhen.service.sys.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 注册登陆模块controller
 */
@Controller
@SuppressWarnings({ "all" })
public class LoginController extends BaseController{

	private User user;
	private User currentUser;
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
	private Role role;
	@Autowired
	private RoleService roleService;
	private Map map;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String STUDENTROLEID = "815f6fd1f3904e25b779120f7e3b63be"; // 数据库中学生的角色ID
	
	
	// 登陆
	@RequestMapping("login")
	public void login(HttpServletRequest request,HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			String name=request.getParameter("name");
			String password=request.getParameter("password");
			String imageCode=request.getParameter("imageCode");
			request.setAttribute("name", name);
			request.setAttribute("password", password);
			request.setAttribute("imageCode", imageCode);
			if(StringUtil.isEmpty(name)||StringUtil.isEmpty(password)){
				request.setAttribute("error", "账户或密码为空");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
			
			if(StringUtil.isEmpty(imageCode)){
				request.setAttribute("error", "验证码为空");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
			if(!imageCode.equals(session.getAttribute("sRand"))){
				request.setAttribute("error", "验证码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
			map = new HashMap<String, String>();
			map.put("name", name);
			map.put("password", password);
			currentUser = userService.loginUser(map);
			if(currentUser==null){
				request.setAttribute("error", "用户名或密码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else{
				
				// 登录信息存入session
				Role a = new Role();
				a.setRoleId(currentUser.getRoleId());
				role = roleService.findOne(a);
				String roleName=role.getRoleName();
				currentUser.setRoleName(roleName);
				session.setAttribute("currentUser", currentUser);  // 当前用户信息
				session.setAttribute("currentOperationIds", role.getOperationIds());  // 当前用户所拥有的按钮权限
				response.sendRedirect("main.htm");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户登录错误",e);
		}
	}
	
	
	// 进入系统主界面
	@RequestMapping("main")
	public String toMain(){
		return "sys/main";
	}
	
	
	// 加载最上级左菜单树
	@RequestMapping("menuTree")
	public void getMenuTree(HttpServletRequest request,HttpServletResponse response){
		try {
			String parentId = request.getParameter("parentId");
			currentUser = (User) request.getSession().getAttribute("currentUser");
			Role a = new Role();
			a.setRoleId(currentUser.getRoleId());
			role = roleService.findOne(a);
			String[] menuIds = role.getMenuIds().split(",");
			map = new HashMap();
			map.put("parentId",parentId);
			map.put("menuIds", menuIds);
			JSONArray jsonArray = getMenusByParentId(parentId, menuIds);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加载左菜单错误",e);
		}
	}
	
	
	// 递归加载所所有树菜单
	public JSONArray getMenusByParentId(String parentId,String[] menuIds)throws Exception{
		JSONArray jsonArray=this.getMenuByParentId(parentId,menuIds);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", getMenusByParentId(jsonObject.getString("id"),menuIds));
			}
		}
		return jsonArray;
	}
	
	
	// 将所有的树菜单放入easyui要求格式的json
	public JSONArray getMenuByParentId(String parentId,String[] menuIds)throws Exception{
		JSONArray jsonArray=new JSONArray();
		map= new HashMap();
		map.put("parentId",parentId);
		map.put("menuIds", menuIds);
		List<Menu> list = menuService.menuTree(map);
		for(Menu menu : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", menu.getMenuId());
			jsonObject.put("text", menu.getMenuName());
			jsonObject.put("iconCls", menu.getIconCls());
			JSONObject attributeObject = new JSONObject();
			attributeObject.put("menuUrl", menu.getMenuUrl());
			if(!hasChildren(menu.getMenuId(), menuIds)){
				jsonObject.put("state", "open");
			}else{
				jsonObject.put("state", menu.getState());				
			}
			jsonObject.put("attributes", attributeObject);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
	// 判断是不是有子孩子，人工结束递归树
	public boolean hasChildren(String parentId,String[] menuIds){
		boolean flag = false;
		try {
			map= new HashMap();
			map.put("parentId",parentId);
			map.put("menuIds", menuIds);
			List<Menu> list = menuService.menuTree(map);
			if (list == null || list.size()==0) {
				flag = false;
			}else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加载左菜单时判断是不是有子孩子错误",e);
		}
		return flag;
	}

	
	
	
	// 更新密码
	@RequestMapping("updatePassword")
	@ResponseBody
	public ResponseDate updatePassword(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String id=request.getParameter("id");
			String newPassword=request.getParameter("newPassword");
			
			currentUser = (User) request.getSession().getAttribute("currentUser");
			String roleid = currentUser.getRoleId();
			user=new User();
			user.setId(id);
			user.setPassword(newPassword);
			userService.update(user);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("密码更新失败",e);
			rd.setSuccess(true);
			rd.setErrorMsg("对不起。密码修改失败，稍后再试");
		}
		return rd;
	}
	
	
	//安全退出
	@RequestMapping("logout")
	private void logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// 清空session
		request.getSession().invalidate();
		response.sendRedirect("login.jsp");
	}
	
	
}
