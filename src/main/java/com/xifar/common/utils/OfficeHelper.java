package com.xifar.common.utils;

import java.io.FileInputStream;
import java.util.List;

public class OfficeHelper {
	
	public static List<?> readExcel(FileInputStream inputStream){
		return null;

//		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
//        // 创建对工作表的引用。
//        // 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
//        HSSFSheet sheet = workbook.getSheet("Sheet1");
//        // 也可用getSheetAt(int index)按索引引用，
//        // 在Excel文档中，第一张工作表的缺省索引是0，
//        // 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
//        // 读取左上端单元
//        HSSFRow row = sheet.getRow(0);
//        HSSFCell cell = row.getCell((short)0);
//        // 输出单元内容，cell.getStringCellValue()就是取所在单元的值
//        System.out.println("左上端单元是： " + cell.getStringCellValue());
//		return null;
		
	}
	

}
