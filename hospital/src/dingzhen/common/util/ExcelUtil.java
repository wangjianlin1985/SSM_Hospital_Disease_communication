package dingzhen.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	/**
	 * @param fileName  excel文件路径
	 * @param startRow  excel数据开始的行数
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> readExcel(String fileName,int startRow) throws IOException {
        File file = new File(fileName);
        Workbook wb = null;
        if (fileName.endsWith(".xlsx")) {// 2007
            wb = new XSSFWorkbook(new FileInputStream(file));// 创建 一个excel文档对象
        } else if (fileName.endsWith(".xls")) {// 2003
            wb = new HSSFWorkbook(new FileInputStream(file));// 创建 一个excel文档对象
        }

        Sheet sheet = wb.getSheetAt(0);// 读取第一个sheet页表格内容
        Object value = null;
        Row row = null;
        Cell cell = null;
        // 行
        List<List<Object>> rowlist = new LinkedList<List<Object>>();
        for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            // 列
            List<Object> cellList = new LinkedList<Object>();
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }

                DecimalFormat df = new DecimalFormat("0");// 格式化 number String
                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
                switch (cell.getCellType()) {
                    case STRING:// 字符串——String type
                        value = cell.getStringCellValue();
                        break;
                    case NUMERIC:// 数字——Number type
                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                            value = df.format(cell.getNumericCellValue());
                        } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                            value = nf.format(cell.getNumericCellValue());
                        } else {
                            value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                        }
                        break;
                    case BOOLEAN:// boolean——Boolean type
                        value = cell.getBooleanCellValue();
                        break;
                    case BLANK:// 空白——Blank type
                        value = "";
                        break;
                    default:// default type
                        value = cell.toString();
                }
                if (value == null || "".equals(value)) {
                    continue;
                }
                cellList.add(value);
            }
            rowlist.add(cellList);
        }
        return rowlist;
    }
	
	
	
	
	
	
	
	
	
	
	
    /*
     * 导出数据
     * */
    public static String export(String title,String[] rowName,List<Object[]>  dataList,HttpServletRequest request) throws Exception{
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表
            
            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);
            
            
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));  
            cellTiltle.setCellValue(title);
            
            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2);                // 在索引2的位置创建行(最顶端的行开始的第二行)
            
            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNum;n++){
                HSSFCell  cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(CellType.STRING);                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);                                    //设置列头单元格的值
            }
            
            //将查询出的数据设置到sheet对应的单元格中
            for(int i=0;i<dataList.size();i++){
                
                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i+3);//创建所需的行数
                
                for(int j=0; j<obj.length; j++){
                    HSSFCell  cell = null;   //设置单元格的数据类型
                    cell = row.createCell(j,CellType.STRING);
                    if(!"".equals(obj[j]) && obj[j] != null){
                        cell.setCellValue(obj[j].toString());                        //设置单元格的值
                    }
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == CellType.STRING) {
                        	try {
                        		int length = currentCell.getStringCellValue().getBytes().length;
                                if (columnWidth < length) {
                                    columnWidth = length;
                                }
							} catch (Exception e) {
								
							}
                            
                        }
                    }
                }
                if(colNum == 0){
                    sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                }else{
                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                }
            }
            
            if(workbook !=null){
            	OutputStream os = null;
                try
                {
                	String path = request.getSession().getServletContext().getRealPath("upload");
                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                    os = new FileOutputStream(new File(path + "/" + fileName));
        			workbook.write(os);
        			return "upload/" + fileName;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
        
    }
    

	
	
	
	
}
