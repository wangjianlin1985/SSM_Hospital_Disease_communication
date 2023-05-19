package dingzhen.entity.sys;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import dingzhen.common.base.BaseEntity;

/**
 * 角色信息
 */
@Alias("role")
public class Role extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String roleId;         // ID
	private String roleName;       // 名称
	private String menuIds;     // 拥有的菜单权限的集合
	private String operationIds;     // 拥有的按钮权限的集合
	private String roleDescription;  // 描述
	
	
	
	public String getOperationIds() {
		return operationIds;
	}
	public void setOperationIds(String operationIds) {
		this.operationIds = operationIds;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
}
