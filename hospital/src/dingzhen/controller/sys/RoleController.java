package dingzhen.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.PropertiesUtil;
import dingzhen.common.util.StringUtil;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.sys.Menu;
import dingzhen.entity.sys.Operation;
import dingzhen.entity.sys.Role;
import dingzhen.service.sys.MenuService;
import dingzhen.service.sys.OperationService;
import dingzhen.service.sys.RoleService;
import dingzhen.service.sys.UserService;

/**
 * 角色
 */
@Controller
@RequestMapping("role")
@SuppressWarnings({"unchecked","rawtypes"})
public class RoleController extends BaseController<Role> {

	private Role role;
	private Operation operation;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	private Map map;
	private Menu menu;
	@Autowired
	private MenuService menuService;
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("roleIndex")
	public String index(){
		return "sys/role";
	}
	
	@RequestMapping("roleList")
	@ResponseBody
	public ResponseDate<Role> roleList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Role> rd = new ResponseDate<Role>();
		try {
			page = Integer.parseInt(request.getParameter("page"));//==null?1:Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));//==null?10:Integer.parseInt(request.getParameter("rows"));
			role = new Role();
			role.setPage((page-1)*rows);
			role.setRows(rows);
			role.setRoleName(request.getParameter("roleName"));
			List<Role> list = findAllRole(role);
			int total = roleService.count(role);
			rd.setRows(list);
			rd.setTotal(total);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("角色展示列表错误",e);
		}
		return rd;
	}
	
	
	private List<Role> findAllRole(Role role){
		try {
			if(!isSystemAdmin()){  //不是超管。过滤超级管理员角色
				role.setRoleId("1");
			}
			return roleService.findList(role);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("roleCombobox")
	public void roleCombobox(HttpServletRequest request,HttpServletResponse response){
		try {
			JSONArray jsonArray=new JSONArray();
			List<Role> list = findAllRole(new Role());
			jsonArray.addAll(list);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("reserveRole")
	@ResponseBody
	public ResponseDate addRole(HttpServletRequest request,Role role,HttpServletResponse response){
		ResponseDate<Role> rd = new ResponseDate<Role>();
		String roleId = request.getParameter("roleId");
		try {
			if (StringUtil.isNotEmpty(roleId)) {
				role.setRoleId(roleId);
				roleService.update(role);
				rd.setSuccess(true);
			}else {
				if(roleService.existRoleWithRoleName(role.getRoleName())==null){  // 没有重复可以添加
					role.setRoleId(get32UUID());
					role.setMenuIds(PropertiesUtil.getProperty("MAINMENEID"));
					roleService.add(role);
					rd.setSuccess(true);
				} else {
					rd.setSuccess(true);
					rd.setErrorMsg("该角色名被使用!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("角色保存错误",e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
	
	
	
	@RequestMapping("deleteRole")
	@ResponseBody
	public ResponseDate delRole(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Role> rd = new ResponseDate<Role>();
		try {
			String[] roleIds=request.getParameter("ids").split(",");
			for (int i=0;i<roleIds.length;i++) {
				boolean b = userService.existUserWithRoleId(roleIds[i])==null; //该角色下面没有用户
				if(!b){
					rd.setErrorMsg("有角色下面有用户，不能删除");
					rd.setOther(i + "");
					return rd;
				}
			}
			if (roleIds.length==1) {
				Role r = new Role();
				r.setRoleId(roleIds[0]);
				roleService.delete(r);
			} else {
				map = new HashMap();
				map.put("roleIds", roleIds);
				roleService.deleteRoleByRoleIds(map);
			}
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("角色删除错误",e);
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	
	
	@RequestMapping("chooseMenu")
	public void chooseMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String parentId=request.getParameter("parentId");
			String roleId=request.getParameter("roleId");
			Role r = new Role();
			r.setRoleId(roleId);
			role = roleService.findOne(r);
			String menuIds = role.getMenuIds();
			String operationIds = role.getOperationIds();
			JSONArray jsonArray=getCheckedMenusByParentId(parentId, menuIds,operationIds);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加载权限菜单树错误",e);
		}
	}
	
	
	
	
	// 选中已有的角色
	public JSONArray getCheckedMenusByParentId(String parentId,String menuIds,String operationIds)throws Exception{
		JSONArray jsonArray=this.getCheckedMenuByParentId(parentId,menuIds,operationIds);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", getCheckedMenusByParentId(jsonObject.getString("id"),menuIds,operationIds));
			}
		}
		return jsonArray;
	}
	
	public JSONArray getCheckedMenuByParentId(String parentId,String menuIds,String operationIds)throws Exception{
		JSONArray jsonArray=new JSONArray();
		menu = new Menu();
		menu.setParentId(parentId);
		List<Menu> list = menuService.findList(menu);
		for(Menu menu : list){
			JSONObject jsonObject = new JSONObject();
			String menuId = menu.getMenuId();
			jsonObject.put("id", menuId);
			jsonObject.put("text", menu.getMenuName());
			jsonObject.put("iconCls", menu.getIconCls());
			jsonObject.put("state", menu.getState());
			if (StringUtil.isNotEmpty(menuIds)) {
				if (dingzhen.common.util.StringUtil.existStrArr(menuId+"", menuIds.split(","))) {
					jsonObject.put("checked", true);
				} 	
			}
			jsonObject.put("children", getOperationJsonArray(menuId,operationIds));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
	public JSONArray getOperationJsonArray(String menuId,String operationIds){
		JSONArray jsonArray=new JSONArray();
		try {
			operation = new Operation();
			operation.setMenuId(menuId);
			List<Operation> list = operationService.findList(operation);
			for(Operation operation : list){
				JSONObject jsonObject = new JSONObject();
				String operationId = operation.getOperationId();
				jsonObject.put("id", operationId);
				jsonObject.put("text", operation.getOperationName());
				jsonObject.put("iconCls", "");
				jsonObject.put("state", "open");
				if (StringUtil.isNotEmpty(operationIds)) {
					if (dingzhen.common.util.StringUtil.existStrArr(operationId+"", operationIds.split(","))) {
						jsonObject.put("checked", true);
					} 	
				}
				jsonArray.add(jsonObject);
			}
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	@RequestMapping("updateRoleMenu")
	@ResponseBody
	public ResponseDate updateRoleMenu(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String roleId = request.getParameter("roleId");
			String[] ids = request.getParameter("menuIds").split(",");
			String menuIds = "";
			String operationIds = "";
			/**
			 * ======================================================
			 * 采用的方案是在菜单递归之后，再加上各菜单下的按钮
			 * 采用easyui的解析方式所以字段都用的是id和text。
			 * 为了区别两者，我们规定operationId自增从10000开始
			 * menuId从1开始，在上传过来的ids中是这样的形式
			 * 2,10000,3,4,7,10004,10006,45 这样的菜单ID和按钮ID的混合形式
			 * 所以通过与10000的比较来确定哪些是菜单的哪些是按钮的
			 * for (int i = 0; i < ids.length; i++) {
					int id = Integer.parseInt(ids[i]);
					if (id>=10000) {
						operationIds += (","+id);
					} else {
						menuIds += (","+id);
					}
				}
			 * =======================================================
			 * 上面是初始化的方法。因需求将所有的主键ID修改为uuid。上述方法不适合。调整如下
			 * =====================================================
			 * 在添加的时候将menuID设置为32位的UUID
			 * 将opertionId设置为30位的UUID。
			 * 以此来判断传过来的ID属于谁
			 */
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				if (id.length() < 31 ) {
					operationIds += (","+id);
				} else {
					menuIds += (","+id);
				}
			}
			role = new Role();
			role.setRoleId(roleId);
			role.setMenuIds(menuIds.substring(1));
			if(StringUtil.isNotEmpty(operationIds)){
				role.setOperationIds(operationIds.substring(1));
			}
			roleService.update(role);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("授权失败",e);
			rd.setErrorMsg("对不起，授权失败！");
		}
		return rd;
	}
	
	
	
}
