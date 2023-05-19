package dingzhen.entity.sys;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import dingzhen.common.base.BaseEntity;

/**
 操作按钮所拥有的属性
 */
@Alias("operation")
public class Operation extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String operationId;  //按钮ID
	private String menuId;       //所属哪一个页面菜单的ID
	private String operationName;  //按钮名称
	private String menuName;
	
	
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
}
