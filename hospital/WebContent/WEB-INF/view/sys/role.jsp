<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title></title>
    
    <script type="text/javascript" src="${path }/js/sys/role.js"></script>
<title>角色管理</title>
</head>
<body style="margin: 1px;">

<!-- 展示列表 -->
<table id="dg" class="easyui-datagrid" fitColumns="true" 
    pagination="true" rownumbers="true" url="roleList.htm" fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th data-options="fidel:'roleId',hidden:'true'">编号</th>
    		<th field="roleName" width="100" align="center">角色名称</th>
    		<th field="roleDescription" width="200" align="center">备注</th>
    	</tr>
    </thead>
</table>


<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb">
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="570c269b28dc483da09f32fa75fb1c" clazz="easyui-linkbutton" onClick="openRoleAddDialog()" name="添加"  iconCls="icon-add" ></privilege:operation>
		<privilege:operation operationId="795f2fefd6ea4e4cae96ef6633ca9a" clazz="easyui-linkbutton" onClick="openRoleUpdateDialog()" name="修改"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="7581831ec7504ea592241df49441b5" clazz="easyui-linkbutton" onClick="deleteRole()" name="删除"  iconCls="icon-remove" ></privilege:operation>
		<privilege:operation operationId="05da9b23441449b88cf56a3c762b4c" clazz="easyui-linkbutton" onClick="openMenuDialog()" name="授权"  iconCls="icon-edit" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;角色名称：&nbsp;<input type="text" name="s_roleName" id="s_roleName" size="20" onkeydown="if(event.keyCode==13) searchRole()"/>
		<a href="javascript:searchRole()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>



<!-- 角色新增修改表单 -->
<div id="dlg" class="easyui-dialog" style="width: 570px;height: 350px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="5px;">
  		<tr>
  			<td>角色名称：</td>
  			<td width="65%"><input type="text" id="roleName" name="roleName" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td valign="top">备注：</td>
  			<td colspan="2">
  				<textarea rows="7" cols="40" name="roleDescription" id="roleDescription"></textarea>
  			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons"  style="text-align:center">
	<a href="javascript:reserveRole()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeRoleDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>




<!-- 权限菜单表格树 -->
<div id="dlg2" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
  closed="true" buttons="#dlg2-buttons">
	<ul id="tree" class="easyui-tree"></ul>
</div>

<div id="dlg2-buttons">
	<a href="javascript:saveMenu()" class="easyui-linkbutton" iconCls="icon-ok" >授权</a>
	<a href="javascript:closeMenuDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>
</body>
</html>