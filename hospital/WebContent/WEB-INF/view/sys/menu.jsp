<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>菜单主页</title>
   
<script type="text/javascript" src="${path }/js/sys/menu.js"></script>
</head>
<body style="margin: 1px;">

<!-- 菜单显示主页面 -->
<table id="treeGrid"  class="easyui-treegrid" 
  toolbar="#tb" data-options="idField:'id',treeField:'text',fit:true,fitColumns:true">
    <thead>
    	<tr>
    		<th field="id" width="30" align="center" data-options="hidden:true">菜单编号</th>
    		<th field="text" width="80">菜单名称</th>
    		<!-- <th field="iconCls" width="35" align="center" formatter="formatIconCls" >图标</th> -->
    		<th field="operationNames" width="80" align="center"  >包含按钮</th>
    		<th field="menuUrl" width="100" align="center">链接地址</th>
    		<th field="seq" width="100" align="center">顺序</th>
    		<th field="menuDescription" width="100" align="center">备注</th>
    	</tr>
    </thead>
</table>

<!-- 菜单显示主页面上的操作按钮 -->
<div id="tb">
	<div>
		<privilege:operation operationId="78cfc2d05f394e619300c3c22ba51c" clazz="easyui-linkbutton" onClick="openMenuAddDialog()" name="添加"  iconCls="icon-add" ></privilege:operation>
		<privilege:operation operationId="7f1f1c3bf1de4e5fbc8e95a4851871" clazz="easyui-linkbutton" onClick="openMenuUpdateDialog()" name="修改"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="6de09cc41d834dd3a5b66f9bcbc3a9" clazz="easyui-linkbutton" onClick="deleteMenu()" name="删除"  iconCls="icon-remove" ></privilege:operation>
		<privilege:operation operationId="ebf5a2f3af5f4d67ad05fd9a5691b2" clazz="easyui-linkbutton" onClick="openOperationDialog()" name="按钮管理"  iconCls="icon-edit" ></privilege:operation>
	</div>
</div>


<!-- 菜单的新增/修改form -->
<div id="dlg" class="easyui-dialog" style="width: 630px;height: 350px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons" >
  <form id="fm" method="post">
  	<table cellspacing="5px;">
  		<tr>
  			<td>名称：</td>
  			<td><input type="text" id="menuName" name="menuName" class="easyui-validatebox" required="true"/></td>
  			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
  			<td>图标：</td>
  			<input  type="hidden" id="iconCls" name="iconCls" class="easyui-validatebox" required="true"/>
  			<td id="selIconTD"></td>
  		</tr>
  		<tr>
  			<td>路径：</td>
  			<td ><input type="text" id="menuUrl" name="menuUrl" class="easyui-validatebox" /></td>
  			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
  			<td>序号：</td>
  			<td ><input type="text" id="seq" name="seq" class="easyui-validatebox" required="true" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
  		</tr>
  		<tr>
  			<td valign="top">备注：</td>
  			<td colspan="4">
  				<textarea rows="7" cols="50" name="menuDescription" id="menuDescription"></textarea>
  			</td>
  		</tr>
  	</table>
  </form>
</div>
<div id="dlg-buttons"  style="text-align:center">
	<a href="javascript:reserveMenu()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeMenuDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>





<!-- 图标展示列表 -->
<div id="iconDiv" class="easyui-dialog" style="width: 450px;height: 300px;padding: 10px 20px" closed="true">
		<div >
			<c:forEach begin="1" end="520" step="1" varStatus="s">
				<div onclick="subIcon('icon-${s.index }');" class="easyui-linkbutton" style="float:left;margin-left:15px;" iconCls="icon-${s.index }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
				<c:if test="${s.index % 14 == 0}"><br/></c:if>
			</c:forEach>	
		</div>
</div>







<!-- 按钮展示列表 -->
<div id="operationDlg" class="easyui-dialog" style="width: 450px;height: 300px;padding: 10px 20px" closed="true" >
<table  class="easyui-datagrid" id="operationTable"   toolbar="#operationTb">
    <thead>
    	<tr>
    		<th field="cb2" checkbox="true" align="center"></th>
    		<th field="menuId" width="30" align="center" data-options="hidden:true"></th>
    		<th field="operationId" width="100" align="center" data-options="hidden:true">按钮编号</th>
    		<th field="operationName" width="100" align="center">按钮名称</th>
    		<th field="menuName" width="100" align="center">所属菜单</th>
    	</tr>
    </thead>
</table>
</div>
<div id="operationTb">
	<div>
		<a href="javascript:openOperationAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openOperationUpdateDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteOperation()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
</div>




<!-- 按钮的新增/修改form -->
<div id="operationReserveDlg" class="easyui-dialog" style="width: 350px;height: 200px;padding: 10px 20px"
  closed="true" buttons="#operationdlg-buttons" >
  <form id="fm2" method="post">
  	<table cellspacing="5px;">
  		<tr>
  			<td>所属菜单：</td>
  			<td><input type="text" id="o_menuName" name="menuName" class="easyui-validatebox" readonly="readonly"/></td>
  		</tr>
  		<tr>
  			<td>按钮名称：</td>
  			<td><input type="text" id="operationName" name="operationName" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<input type="hidden" id="o_menuId" name="menuId" />
  	</table>
  </form>
</div>

<div id="operationdlg-buttons"  style="text-align:center">
	<a href="javascript:reserveOperation()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeOperationReserveDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>





</body>
</html>