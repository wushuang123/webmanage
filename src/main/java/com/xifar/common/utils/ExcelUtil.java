package com.xifar.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {

	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

	/** EXCEL2003 **/
	public static final int XLS = 0;
	/** EXCEL2007 **/
	public static final int XLSX = 1;

	/** Cell value type **/
	public static final int UNKNOW = -1;
	public static final int STRING = 0;
	public static final int INT = 1;
	public static final int DOUBLE = 2;
	public static final int BOOLEAN = 3;
	public static final int DATE = 4;
	public static final int FLOAT = 5;
	public static final int SHORT = 6;
	public static final int LONG = 7;

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static ThreadLocal<DateFormat> threadLocalDateFormat = new ThreadLocal<DateFormat>();

	public static DateFormat getDateFormat(String dateFormat) {
		DateFormat df = threadLocalDateFormat.get();
		if (df == null) {
			df = new SimpleDateFormat(dateFormat);
			threadLocalDateFormat.set(df);
		}
		return df;
	}

	public static String formatDate(Date date, String dateFormat) throws ParseException {
		return getDateFormat(dateFormat).format(date);
	}

	public static Date parse(String strDate, String dateFormat) throws ParseException {
		return getDateFormat(dateFormat).parse(strDate);
	}

	/** export **/
	public static Workbook export(String sheetName, List<String> columnList, List<?> list,
			Map<String, Map<Object, String>> map, List<String> fieldList, int excelType)
			throws IllegalArgumentException, IllegalAccessException {
		if (null == columnList || columnList.size() == 0 || null == list || list.size() == 0 || null == fieldList
				|| fieldList.size() == 0) {
			return null;
		}

		if (null == sheetName || sheetName.trim() == "") {
			sheetName = getDateFormat(DATE_FORMAT).format(new Date());
		}
		Workbook wb = null;
		if (excelType == XLS) {
			wb = new HSSFWorkbook();
		} else if (excelType == XLSX) {
			wb = new XSSFWorkbook();
		} else {
			wb = new HSSFWorkbook();
		}
		Sheet sheet = wb.createSheet(sheetName);
		Row row = sheet.createRow(0);
		for (int i = 0; i < columnList.size(); i++) {
			row.createCell(i).setCellValue(columnList.get(i));
		}

		Map<String, Object> fieldMap = new HashMap<String, Object>();
		for (String field : fieldList) {
			fieldMap.put(field, field);
		}

		for (int k = 0; k < list.size(); k++) {
			row = sheet.createRow(k + 1);
			Object object = list.get(k);
			// pojo字段都是private,所以用getDeclaredFields
			Field[] fieldArray = object.getClass().getDeclaredFields();
			// 遍历属性字段
			int colNum = 0;
			for (int j = 0; j < fieldArray.length; j++) {
				Field field = fieldArray[j];
				boolean match = false;
				if (fieldMap.containsKey(field.getName())) {
					match = true;
				}
				if (match) {
					setFieldValue(field, object, map, row, colNum);
					colNum++;
				} else {
					continue;
				}
			}
		}
		return wb;
	}

	/** set field value to cell **/
	private static void setFieldValue(Field field, Object object, Map<String, Map<Object, String>> map, Row row,
			int columnIndex) throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		String name = field.getName();
		Object value = field.get(object);
		String type = field.getType().getName();
		int valueType = getCellTypeByType(type);
		if (null == map || map.size() == 0) {
			setCellValue(valueType, value, row, columnIndex);
		} else {
			replaceCellValue(valueType, name, value, map, row, columnIndex);
		}
	}

	/** 设置表格的值 **/
	private static void setCellValue(int valueType, Object value, Row row, int columnIndex) {
		if (Objects.isNull(row)) {
			throw new RuntimeException("row can not be null");
		}
		Cell cell = row.createCell(columnIndex);

		if (value == null || value == "") {
			cell.setCellValue("");
		} else {
			switch (valueType) {
			case STRING:
				cell.setCellValue(String.valueOf(value));
				break;
			case INT:
				cell.setCellValue(Integer.parseInt(String.valueOf(value)));
				break;
			case DOUBLE:
				cell.setCellValue(Double.parseDouble(String.valueOf(value)));
				break;
			case BOOLEAN:
				cell.setCellValue(((Boolean) value == true ? "是" : "否"));
				break;
			case DATE:
				cell.setCellValue(getDateFormat(DATE_FORMAT).format(value));
				break;
			case FLOAT:
				cell.setCellValue(Float.parseFloat(String.valueOf(value)));
				break;
			case SHORT:
				cell.setCellValue(Short.parseShort(String.valueOf(value)));
				break;
			case UNKNOW:
				cell.setCellValue("");
				break;
			default:
				break;
			}
		}
	}

	/** 枚举值替换 **/
	private static void replaceCellValue(int valueType, String name, Object value, Map<String, Map<Object, String>> map,
			Row row, int columnIndex) {
		if (!map.containsKey(name)) {
			setCellValue(valueType, value, row, columnIndex);
		} else {
			Map<?, String> fieldMap = map.get(name);
			// 获取要替换的值
			String tempValue = fieldMap.get(value);
			row.createCell(columnIndex).setCellValue(tempValue);
		}
	}

	/** 读取Excel **/
	public static Workbook readExcel(InputStream in, int type) {
		Workbook wb = null;
		try {
			if (XLS == type) {
				wb = new HSSFWorkbook(in);
			} else if (XLSX == type) {
				wb = new XSSFWorkbook(in);
			} else {
			}
		} catch (Exception ex) {
			log.error("读取Excel出现异常" + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				log.error("读取Excel关闭流时出现IO异常" + e.getMessage());
				e.printStackTrace();
			}
		}
		return wb;
	}

	/**
	 * 获取Excel表头信息
	 * 
	 * @param workbook
	 *            表格对象
	 * 
	 * @param sheetIndex
	 *            sheet页码
	 * 
	 * @param rowIndex
	 *            表头所在行位置(从零开始)
	 **/
	public static Map<String, String> getHead(Workbook workbook, int sheetIndex, int rowIndex) {
		if (null == workbook) {
			return null;
		}
		Map<String, String> map = null;
		try {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			if (null == sheet) {
				return null;
			}
			Row row = sheet.getRow(rowIndex);
			if (null == row) {
				return null;
			}
			int count = row.getLastCellNum();
			map = new HashMap<String, String>();
			for (int i = 0; i < count; i++) {
				Cell cell = row.getCell(i);
				String value = cell.getStringCellValue();
				map.put(getExcelColumnLabel(i), value);
			}
		} catch (Exception ex) {
			log.error("获取表头失败" + ex.getMessage());
		}
		return map;
	}

	/** 根据列序号获取列字母 **/
	public static String getExcelColumnLabel(int num) {
		String temp = "";
		double i = Math.floor(Math.log(25.0 * (num) / 26.0 + 1) / Math.log(26)) + 1;
		if (i > 1) {
			double sub = num - 26 * (Math.pow(26, i - 1) - 1) / 25;
			for (double j = i; j > 0; j--) {
				temp = temp + (char) (sub / Math.pow(26, j - 1) + 65);
				sub = sub % Math.pow(26, j - 1);
			}
		} else {
			temp = temp + (char) (num + 65);
		}
		return temp;
	}

	/** 根据列字母获取列序号 **/
	public static int excelColStrToNum(String column) {
		int num = 0;
		int result = 0;
		int length = column.length();
		for (int i = 0; i < length; i++) {
			char ch = column.charAt(length - i - 1);
			num = (int) (ch - 'A');
			num *= Math.pow(26, i);
			result += num;
		}
		result = result + 26 * (length - 1);
		return result;
	}

	/**
	 * 读取Excel某个sheet内容信息
	 * 
	 * @param workbook
	 *            表格对象
	 * 
	 * @param excelType
	 *            excel类型
	 * 
	 * @param sheetIndex
	 *            sheet页码
	 * 
	 * @param startIndex
	 *            数据开始行索引
	 * 
	 * @param endIndex
	 *            数据结束行索引
	 * 
	 * @param clazz
	 *            读取后转换的类
	 * 
	 * @param list
	 *            Excel模板明细
	 * 
	 * @param map
	 *            替换的字段枚举
	 **/
	public static <T> List<T> getContent(Workbook workbook, int excelType, int sheetIndex, int startIndex, int endIndex,
			Class<T> clazz, Map<Integer, String> columnMap, Map<String, Map<String, Integer>> map) {
		if (null == workbook || null == columnMap || columnMap.size() == 0) {
			return null;
		}
		try {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			if (null == sheet) {
				return null;
			}
			int lastRowNum = sheet.getLastRowNum();
			if (endIndex != 0) {
				lastRowNum = endIndex;
			}
			List<T> resultList = new ArrayList<T>();
			for (int i = startIndex; i <= lastRowNum; i++) {
				Row row = sheet.getRow(i);
				if (null == row) {
					continue;
				}
				T instance = clazz.newInstance();
				for (Map.Entry<Integer, String> entry : columnMap.entrySet()) {
					if (null == entry) {
						continue;
					}
					int columnIndex = entry.getKey();
					String fieldName = entry.getValue();
					Cell cell = row.getCell(columnIndex);
					if (null == cell) {
						continue;
					}
					try {
						instance = setObjectFieldValue(instance, fieldName, cell, excelType, map);

					} catch (Exception ex) {
						ex.printStackTrace();
						log.error("读取单元格出错" + ex.getMessage());
					}
				}
				if (null != instance) {
					resultList.add(instance);
				}
			}
			return resultList;
		} catch (Exception ex) {
			log.error("获取内容失败" + ex.getMessage());
			return null;
		}
	}

	/**
	 * 设置实例的值
	 * 
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 **/
	private static <T> T setObjectFieldValue(T instance, String fieldName, Cell cell, int excelType,
			Map<String, Map<String, Integer>> map) throws IllegalArgumentException, IllegalAccessException,
			ParseException, NoSuchFieldException, SecurityException {
		if (Objects.isNull(instance) || Objects.isNull(cell) || fieldName == null || fieldName.trim() == "") {
			return null;
		}
		int cellType = getCellTypeByType(instance.getClass().getDeclaredField(fieldName).getType().getName());
		switch (cellType) {
		case STRING:
			if (null == map || map.size() == 0) {
				instance = setInstanceValue(instance, fieldName, cell.getStringCellValue());
			}
			break;
		case INT:
			if (null == map || map.size() == 0) {
				instance = setInstanceValue(instance, fieldName, cell.getNumericCellValue());
			} else {
				instance = setInstanceValue(instance, fieldName,
						getValue(fieldName, cell.getStringCellValue(), map) == null ? cell.getStringCellValue()
								: getValue(fieldName, cell.getStringCellValue(), map));
			}
			break;
		case LONG:
			if (null == map || map.size() == 0) {
				instance = setInstanceValue(instance, fieldName, cell.getNumericCellValue());
			} else {
				instance = setInstanceValue(instance, fieldName,
						getValue(fieldName, cell.getStringCellValue(), map) == null ? cell.getStringCellValue()
								: getValue(fieldName, cell.getStringCellValue(), map));
			}
			break;
		case DOUBLE:
			instance = setInstanceValue(instance, fieldName, cell.getNumericCellValue());
			break;
		case BOOLEAN:
			if (null == map || map.size() == 0) {
				instance = setInstanceValue(instance, fieldName, cell.getBooleanCellValue());
			} else {
				instance = setInstanceValue(instance, fieldName,
						getValue(fieldName, cell.getStringCellValue(), map) == null ? cell.getStringCellValue()
								: getValue(fieldName, cell.getStringCellValue(), map));
			}
			break;
		case DATE:
			boolean isDate = false;
			// isDate = DateUtil.isCellDateFormatted(cell);
			if (excelType == XLS) {
				isDate = HSSFDateUtil.isCellDateFormatted(cell);
			} else {
				isDate = DateUtil.isCellDateFormatted(cell);
			}
			if (isDate) {
				instance = setInstanceValue(instance, fieldName, cell.getDateCellValue());
			}
			break;
		case FLOAT:
			instance = setInstanceValue(instance, fieldName, cell.getNumericCellValue());
			break;
		case SHORT:
			instance = setInstanceValue(instance, fieldName, cell.getNumericCellValue());
			break;
		case UNKNOW:
			break;
		default:
			break;
		}
		return instance;
	}

	/**
	 * 用反射给字段赋值
	 * 
	 * @throws ParseException
	 **/
	private static <T> T setInstanceValue(T t, String fieldName, Object value)
			throws IllegalArgumentException, IllegalAccessException, ParseException {
		Field[] fields = ((Object) t).getClass().getDeclaredFields();
		// 遍历属性字段
		for (int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			field.setAccessible(true);
			String name = field.getName();
			if (fieldName.equals(name)) {
				String fieldType = field.getType().getName();
				Object certainValue = getFieldValue(getCellTypeByType(fieldType), value);
				fields[j].set(t, certainValue);
			}
		}
		return t;
	}

	/**
	 * 字段替换为枚举值
	 * 
	 * @param name
	 *            字段英文名
	 * 
	 * @param value
	 *            字段对应的字符串值
	 * 
	 * @param map
	 *            过滤的Map集合
	 * 
	 * @return 字符串对应的枚举值
	 * 
	 **/
	private static Object getValue(String fieldName, String value, Map<String, Map<String, Integer>> map) {
		if (!map.containsKey(fieldName)) {
			return null;
		} else {
			Map<String, Integer> tempMap = map.get(fieldName);
			if (tempMap.containsKey(value)) {
				return tempMap.get(value);
			} else {
				return null;
			}
		}
	}

	/**
	 * 确定值的类型并以Object返回(导入时用到)
	 * 
	 * @throws ParseException
	 **/
	private static Object getFieldValue(int valueType, Object value) throws ParseException {
		Object obj = null;
		switch (valueType) {
		case STRING:
			obj = String.valueOf(value) == null ? null : String.valueOf(value).trim();
			break;
		case INT:
			if (value == null || String.valueOf(value) == "") {
				obj = null;
			} else {
				Double tempObj = new Double(String.valueOf(value));
				obj = tempObj.intValue();
			}
			break;
		case DOUBLE:
			if (value == null || value == "") {
				obj = null;
			} else {
				obj = Double.parseDouble(String.valueOf(value));
			}
			break;
		case BOOLEAN:
			obj = Boolean.getBoolean(String.valueOf(value));
			break;
		case DATE:
			obj = value;
			break;
		case FLOAT:
			obj = Float.parseFloat(String.valueOf(value));
			break;
		case SHORT:
			obj = (Short) value;
			break;
		case LONG:
			Double tempObj = new Double(String.valueOf(value));
			obj = tempObj.longValue();
			break;
		case UNKNOW:
			break;
		default:
			break;
		}
		return obj;
	}

	private static int getCellTypeByType(String type) {
		if (String.class.getTypeName().equals(type)) {
			return STRING;
		} else if (Integer.class.getTypeName().equals(type)) {
			return INT;
		} else if (Long.class.getTypeName().equals(type)) {
			return LONG;
		} else if (Double.class.getTypeName().equals(type)) {
			return DOUBLE;
		} else if (Boolean.class.getTypeName().equals(type)) {
			return BOOLEAN;
		} else if (Date.class.getTypeName().equals(type)) {
			return DATE;
		} else if (Float.class.getTypeName().equals(type)) {
			return FLOAT;
		} else if (Short.class.getTypeName().equals(type)) {
			return SHORT;
		} else {
			return UNKNOW;
		}
	}

	public static void main(String[] args) {

	}

}
