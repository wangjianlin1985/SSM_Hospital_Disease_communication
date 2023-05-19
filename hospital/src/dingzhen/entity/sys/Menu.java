package dingzhen.entity.sys;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 菜单信息
 */
@Alias("menu")
public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String menuId; // 主键
	private String menuName;     // 名称
	private String menuUrl;          // 方法界面
	private String parentId;         // 上级菜单
	private String menuDescription;    // 描述说明
	private String state;           // 状态（easyui使用）
	private String iconCls;     // 图标样式
	private Integer seq;         // 顺序
	private String[] menuIds;       
	
	// 操作按钮名称合集
	private String operations;
	
	
	
	
	public String getOperations() {
		return operations;
	}
	public void setOperations(String operations) {
		this.operations = operations;
	}
	public String[] getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String[] menuIds) {
		this.menuIds = menuIds;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getMenuDescription() {
		return menuDescription;
	}
	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
