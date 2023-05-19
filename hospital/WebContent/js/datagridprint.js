tableString="<html><head></head><body><table cellspacing='0'  id='PrintBody' border='1' style='font-size:12px;margin:1.3%' >";

function doPrint(){
	 
   tableString += "<script language='javascript'>window.print();</s"+"cript>";

   var iWidth=window.screen.availWidth; //弹出窗口的宽度;  
   var iHeight=window.screen.availHeight; //弹出窗口的高度;  
   var iTop =  0; //获得窗口的垂直位置;  
   var iLeft = 0; //获得窗口的水平位置;  
   
   win = document.open('','','height='+iHeight+',width='+iWidth+',top='+iTop+',left='+iLeft
		   +',scrollbars=yes,status = no,location= yes,menubar =no, toolbar = no');

   win.document.write(tableString);
   tableString="<html><head></head><body><table cellspacing='0'  id='PrintBody' border='1' style='font-size:12px;margin:1.3%' >";

   win.close();
  
}


function CreateFormPage(printDatagrid) {

 var frozenColumns = printDatagrid.datagrid("options").frozenColumns;  // 得到frozenColumns对象

 var columns = printDatagrid.datagrid("options").columns;    // 得到columns对象



 // 载入title

 tableString = tableString + "\n<tr>";

 if(frozenColumns != undefined && frozenColumns != '') {

    for(var i = 0;i<frozenColumns[0].length; i++) {

        if(frozenColumns[0][i].hidden != true) {

           tableString = tableString + "\n<th width= '" +frozenColumns[0][i].width  +"'>" + frozenColumns[0][i].title + "</th>";

        }

    }

 }

 if(columns != undefined && columns != '') {

    for(var i = 0;i<columns[0].length; i++) {

        if(columns[0][i].hidden != true) {

        	tableString = tableString + "\n<th width= '" +columns[0][i].width  +"'>" + columns[0][i].title + "</th>";
        	//tableString = tableString + "\n<th width= '40px'>" + columns[0][i].title + "</th>";
        }

    }

 }

 tableString = tableString + "\n</tr>";



 // 载入内容

 var rows = printDatagrid.datagrid("getRows"); // 这段代码是获取当前页的所有行。

 for(var j = 0; j <rows.length;j++) {

    tableString = tableString + "\n<tr>";

    if(frozenColumns != undefined && frozenColumns != '') {

        for(var i = 0;i<frozenColumns[0].length; i++) {

           if(frozenColumns[0][i].hidden != true) {
        	   if(rows[j][frozenColumns[0][i].field]==undefined){
        		   tableString = tableString + "\n<td></td>";
        	   }else{
        		   if(frozenColumns[0][i].formatter!=null && frozenColumns[0][i].formatter!=undefined){
        			   tableString = tableString + "\n<td  style='text-align:center;'>" +  frozenColumns[0][i].formatter(rows[j][frozenColumns[0][i].field]) + "</td>";
        		   }else{
        			   tableString = tableString + "\n<td >" + rows[j][frozenColumns[0][i].field] + "</td>";
        		   }
        	   }
           }

        }

    }

    if(columns != undefined && columns != '') {

        for(var i = 0;i<columns[0].length; i++) {

           if(columns[0][i].hidden != true) {
               if(rows[j][columns[0][i].field]==undefined)
               {
            	   tableString = tableString + "\n<td></td>";
               }else{
            	   if(columns[0][i].formatter!=null && columns[0][i].formatter!=undefined){
            		   tableString = tableString + "\n<td  style='text-align:center;'>" +  columns[0][i].formatter(rows[j][columns[0][i].field]) + "</td>";
            	   }else{
            		   tableString = tableString + "\n<td  style='text-align:center;'>" + rows[j][columns[0][i].field] + "</td>";
            	   }
               }
           }
          

        }

    }

    tableString = tableString + "\n</tr>";

 }

 tableString = tableString + "\n</table></body></html>";


 doPrint();
}


function startPrint(datagrid){
  CreateFormPage(datagrid);
}