package dingzhen.controller.sys;


import java.util.List;

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
import dingzhen.common.util.StringUtil;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.sys.Menu;
import dingzhen.entity.sys.Operation;
import dingzhen.service.sys.MenuService;
import dingzhen.service.sys.OperationService;

/**
 * 菜单
 */
@RequestMapping("menu")
@Controller
@SuppressWarnings("rawtypes")
public class MenuController extends BaseController<Menu>{

	private Menu menu;
	private Operation operation;
	@Autowired
	private MenuService menuService;
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("menuIndex")
	public String index(){
		return "sys/menu";
	}
	
	@RequestMapping("treeGridMenu")
	public void treeGridMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String parentId=request.getParameter("parentId");
			JSONArray jsonArray = getListByParentId(parentId);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单展示错误",e);
		}
	}
	
	
	
	
	public JSONArray getListByParentId(String parentId)throws Exception{
		JSONArray jsonArray=this.getTreeGridMenuByParentId(parentId);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", getListByParentId(jsonObject.getString("id")));
			}
		}
		return jsonArray;
	}
	
	
	
	public JSONArray getTreeGridMenuByParentId(String parentId)throws Exception{
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
			jsonObject.put("seq", menu.getSeq());
			jsonObject.put("menuUrl", menu.getMenuUrl());
			jsonObject.put("menuDescription", menu.getMenuDescription());
			
			// 加上该页面菜单下面的按钮
			operation = new Operation();
			operation.setMenuId(menuId);
			List<Operation> operaList = operationService.findList(operation);
			if (operaList!=null && operaList.size()>0) {
				String string = "";
				for (Operation o : operaList) {
					string += o.getOperationName() + ",";
				}
				jsonObject.put("operationNames", string.substring(0,string.length()-1));
			} else {
				jsonObject.put("operationNames", "");
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
	
	@RequestMapping("reserveMenu")
	@ResponseBody
	public ResponseDate reserveMenu(HttpServletRequest request,HttpServletResponse response,Menu menu){
		String menuId = request.getParameter("menuId");
		ResponseDate rd = new ResponseDate();
		try {
			if (StringUtil.isNotEmpty(menuId)) {  //更新操作
				menu.setMenuId(menuId);
				menuService.update(menu);
			} else {
				String parentId = request.getParameter("parentId");
				menu.setParentId(parentId);
				if (isLeaf(parentId)) {
					// 添加操作
					menu.setMenuId(get32UUID());
					menuService.add(menu);  
					
					// 更新他上级状态。变成CLOSED
					menu = new Menu();
					menu.setMenuId(parentId);
					menu.setState("closed");
					menuService.update(menu);
				} else {
					menu.setMenuId(get32UUID());
					menuService.add(menu);
				}
			}
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单保存错误",e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
	
	
	
	// 判断是不是叶子节点
	public boolean isLeaf(String menuId){
		boolean flag = false;
		try {
			menu = new Menu();
			menu.setParentId(menuId);
			List<Menu> list = menuService.findList(menu);
			if (list==null || list.size()==0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("判断菜单是否叶子节点错误",e);
		}
		return flag;
	}
	
	
	
	
	@RequestMapping("deleteMenu")
	@ResponseBody
	public ResponseDate deleteMenu(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String menuId = request.getParameter("menuId");
			String parentId = request.getParameter("parentId");
			if (!isLeaf(menuId)) {  //不是叶子节点，说明有子菜单，不能删除
				rd.setErrorMsg("该菜单下面有子菜单，不能直接删除");
			} else {
				menu = new Menu();
				menu.setParentId(parentId);
				int sonNum = menuService.count(menu);
				if (sonNum == 1) {  
					// 只有一个孩子，删除该孩子，且把父亲状态置为open
					menu = new Menu();
					menu.setMenuId(parentId);
					menu.setState("open");
					menuService.update(menu);
					
					Menu delMenu = new Menu();
					delMenu.setMenuId(menuId);
					menuService.delete(delMenu);
				} else {
					//不只一个孩子，直接删除
					Menu delMenu = new Menu();
					delMenu.setMenuId(menuId);
					menuService.delete(delMenu);
				}
				
				// 删除该menuID下面的按钮
				operationService.deleteByMenuId(menuId);
				
				rd.setSuccess(true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单删除错误",e);
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	 

}
