<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>用户主页</title>
   	<script type="text/javascript" src="${path }/js/sys/user.js"></script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="userList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
            		<th data-options="fidel:'roleId',hidden:'true'">
                	<th data-options="fidel:'id',hidden:'true'">用户编号</th>
                	<th field="name" width="60" align="center">用户名</th>
                	<th field="password" width="60" align="center">密码</th>
                	<th field="realname" width="60" align="center">姓名</th>
                	<th field="phone" width="60" align="center">电话</th>
                	<th field="email" width="60" align="center">email</th>
                	<th field="deptnAME" width="60" align="center" formatter="formatDept">科室</th>
                	<th field="roleName" width="60" align="center">用户角色</th>
                	<th field="description" width="60" align="center">备注</th>
            	</tr>
        </thead>
</table>
<script type="text/javascript">
	function formatDept(value,row,index){
		if(row.dept != null){
			return row.dept.name;
		}
	}
</script>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="7e609bcf3b5b4313bd6bd01e1bcb36" clazz="easyui-linkbutton" onClick="openUserAddDialog()" name="添加"  iconCls="icon-add" ></privilege:operation>
		<privilege:operation operationId="8a5cc94bc02c4ec9a082524bc9b396" clazz="easyui-linkbutton" onClick="openUserUpdateDialog()" name="修改"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="b9d2596fb6914b8392809ec16be0cb" clazz="easyui-linkbutton" onClick="deleteUser()()" name="删除"  iconCls="icon-remove" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;登录名/姓名/手机：&nbsp;<input type="text" name="s_name" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
		&nbsp;用户角色：&nbsp;<input class="easyui-combobox" id="s_roleId" name="s_roleId"  />
		<a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 新增和修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 620px;height: 320px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
 	<table cellspacing="5px;">
  		<tr>
  			<td>用户名：</td>
  			<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/></td>
  			<td>&nbsp;&nbsp;</td>
  			<td>密码：</td>
  			<td><input type="text" id="password" name="password" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>姓名：</td>
  			<td><input type="text" id="realname" name="realname" class="easyui-validatebox" required="true"/></td>
  			<td>&nbsp;&nbsp;</td>
  			<td>部门：</td>
  			<td><input id="deptid" class="easyui-combobox" name="dept.id"
   					data-options="valueField:'id',textField:'name',url:'${path}/dept/deptData.htm'"></td>
  		</tr>
  		<tr>
  			<td>电话：</td>
  			<td><input type="text" id="phone" name="phone" class="easyui-validatebox" /></td>
  			<td>&nbsp;&nbsp;</td>
  			<td>邮箱：</td>
  			<td><input type="text" id="email" name="email" class="easyui-validatebox" /></td>
  		</tr>
  		<tr>
  			<td>角色：</td>
  			<td><input type="hidden" id="roleId" name="roleId" /><input type="text" id="roleName" name="roleName" readonly="readonly" class="easyui-validatebox" required="true"/></td>
  			<td>&nbsp;&nbsp;</td>
  			<td colspan="2"><a href="javascript:openRoleChooseDialog()" class="easyui-linkbutton" >选择角色</a></td>
  		</tr>
  		<tr>
  			<td valign="top">备注：</td>
  			<td colspan="4">
  				<textarea rows="7" cols="50" name="description" id="description"></textarea>
  			</td>
  		</tr>
  	</table>
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveUser()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


<!-- 用户角色对话框 -->
<div id="dlg2" class="easyui-dialog" iconCls="icon-search" style="width: 500px;height: 480px;padding: 10px 20px"
  closed="true" buttons="#dlg2-buttons">
  <div style="height: 40px;" align="center">
  	角色名称：<input type="text" id="s_roleName" name="s_roleName" onkeydown="if(event.keyCode==13) searchRole()"/>
  	<a href="javascript:searchRole()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
  </div>
  <div style="height: 350px;">
  	<table id="dg2" title="查询结果" class="easyui-datagrid" fitColumns="true"  pagination="true" rownumbers="true" url="${path }/role/roleList.htm" singleSelect="true" fit="true" >
    <thead>
    	<tr>
    		<th field="roleId" width="50" align="center" data-options="hidden:true">编号</th>
    		<th field="roleName" width="100" align="center">角色名称</th>
    		<th field="roleDescription" width="200" align="center">备注</th>
    	</tr>
    </thead>
</table>
  </div>
</div>

<div id="dlg2-buttons">
	<a href="javascript:chooseRole()" class="easyui-linkbutton" iconCls="icon-ok" >确定</a>
	<a href="javascript:closeRoleDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

</body>
</html>
