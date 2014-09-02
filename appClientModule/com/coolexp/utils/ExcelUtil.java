package com.coolexp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.coolexp.vo.ObjKeyVO;



/**
 * ����: Excel��ȡ����.<br>
 * 
 * ������Excel��ȡ���ߣ���Ҫ���ڶ�ȡexcel�е�����.<br>
 * 
 * ��Ȩ��Copyright (C) 1997-2008 Oak Pacific Interactive. <br>
 * 
 * @author �ƽ� 2010-06-011 10:50 �½�
 * 
 * @since jdk1.6
 * 
 * @version 0.1
 */
public class ExcelUtil {
	public static Map<String,ObjKeyVO> getKeysList(String xlsPath, String sheetName) throws Exception{
		InputStream inp = new FileInputStream(xlsPath);
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(inp));
		Map<String,ObjKeyVO> map = new HashMap<String,ObjKeyVO>();
		HSSFSheet childSheet = workbook.getSheet(sheetName);
		if(childSheet==null){
			System.out.println("���"+xlsPath+" Sheet: "+sheetName+"������");
			System.exit(0);
		}
		int rowNum = childSheet.getPhysicalNumberOfRows();
		HSSFRow firstRow = childSheet.getRow(0);
		int cellNum = 0;
		if(firstRow!=null){
			cellNum = firstRow.getPhysicalNumberOfCells();
		}
		for(int j=5;j<rowNum;j++) {
			HSSFRow row = childSheet.getRow(j);
			ObjKeyVO ovo = new ObjKeyVO();
			for(int k=1;k<cellNum;k++){
				HSSFCell cell = row.getCell(k);
				switch(k){
				case 1:
					ovo.sheetName = getCellData(cell);
					break;
				case 2:
					ovo.keyStr = getCellData(cell);
					break;
				case 4:
					ovo.searchAttr = getCellData(cell);
				case 5:
					ovo.methodStr = getCellData(cell);
					break;
				}
			}
			map.put(ovo.sheetName, ovo);
		}
		return map;
	}
	
	/**
	 * ��ȡָ��Excelָ��sheet������
	 * 
	 * @param xlsName
	 * @param sheetName
	 * @return
	 */
	public static String[][] getSheetData(String xlsPath, String sheetName)
			throws Exception {
//		OtherUtils.showInfo("[#GS.GameData.loadFromXls -- >object] [excel:"
//				+ xlsPath + " sheet:" + sheetName + "]");
		InputStream inp = new FileInputStream(xlsPath);
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(inp));
		HSSFSheet sheet = workbook.getSheet(sheetName);
		int rowNumber = sheet.getPhysicalNumberOfRows();
		HSSFRow row = sheet.getRow(0);
		if (rowNumber == 0 || row == null) {
			return new String[0][0];
		}
		
		int columnNumber = row.getLastCellNum();
		
		if (row.getCell(0) == null || row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_BLANK) {// �����¹������(0,0)Ϊ�գ�����ҳsheet����
			return new String[0][0];
		}

		// �ж���ֵ
		Object tmp;
		for (int i = 0; i < columnNumber; i++) {
			tmp = row.getCell(i);
			if (!"".equals(tmp) && tmp != null) {
				continue;
			} else {
				columnNumber = i;
				break;
			}
		}

		String[][] data = new String[rowNumber][columnNumber];
		boolean end = false;
		int rowNumber_legal = rowNumber;
		for (int rowIndex = 0; rowIndex < rowNumber; rowIndex++) {
			if (end) {// �������ض�
				break;
			}

			row = sheet.getRow(rowIndex);
			data[rowIndex] = new String[columnNumber];

			if (row == null) {// �������Ϊ��ʱ��ֱ���˳�
				rowNumber_legal = rowIndex;
				end = true;
				break;
			}

			tmp = row.getCell(0);
			if ("".equals(tmp) || tmp == null) {// ���ĳ�еĵ�һ��Ϊ��ʱ���˳�
				rowNumber_legal = rowIndex;
				break;
			}

			for (int columnIndex = 0; columnIndex < columnNumber; columnIndex++) {
				data[rowIndex][columnIndex] = PoiUtils.getStringValue(row
						.getCell(columnIndex));
			}

		}
		if (rowNumber_legal != rowNumber) {
			// ������������
			String[][] back = new String[rowNumber_legal][columnNumber];
			System.arraycopy(data, 0, back, 0, rowNumber_legal);

			return back;
		} else {
			return data;
		}
	}
	
	/**
	 * ͨ��excel·�����HSSFWorkbook
	 * @param xlsPath
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook getWorkBook(String xlsPath) throws Exception{
//		OtherUtils.showInfo("[#GS.GameData.loadFromXls -- >object] [excel:"
//				+ xlsPath + "]");
		InputStream inp = new FileInputStream(xlsPath);
		
		return new HSSFWorkbook(new POIFSFileSystem(inp));
	}
	
	/**
	 * ���sheetName��HSSFWorkbook�е�index
	 * @param workbook
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public static int getSheetIndex(HSSFWorkbook workbook, String sheetName) throws Exception {
		return workbook.getSheetIndex(sheetName);
	}
	

	/**
	 * ���columnName��sheetName�е�index
	 * @param workbook
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public static int getColumnIndex(HSSFWorkbook workbook,String sheetName,String columnName) throws Exception {
		
		HSSFSheet sheet = workbook.getSheet(sheetName);
		
		int rowNumber = sheet.getPhysicalNumberOfRows();
		if (rowNumber == 0) {
			return -1;
		}
		HSSFRow row = sheet.getRow(0);
		int columnNumber = row.getLastCellNum();
		if (row.getCell(0) == null) {// �����¹������(0,0)Ϊ�գ�����ҳsheet����
			return -1;
		}

		// �ж���ֵ
		for (int i = 0; i < columnNumber; i++) {
			if (row.getCell(i) != null&&PoiUtils.getStringValue(row.getCell(i)).equals(columnName)) {
				return i;
			}
		}
		
		return -1;		
	}
	
	/**
	 * ��ȡ����ָ��ҳ������
	 * 
	 * @param path
	 *            excel��·��
	 * @param sheetIndex
	 *            ҳ�����
	 * @return ���ض�ά����
	 * @throws IOException
	 *             �����׳�����
	 */
	public static List<Map<String, String>> getSheetData(String path,
			int sheetIndex) throws IOException {
		// ��ȡ����
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		
		// �ҵ��ļ�
		InputStream inp = new FileInputStream(path);
		// ��excel
		HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(inp));
		// ��ҳ
		HSSFSheet sheet = workBook.getSheetAt(sheetIndex);
		// ��ȡ������
		int rowCount = sheet.getPhysicalNumberOfRows();
		// û������
		if (rowCount < 2) {
			throw new IOException("��Ϊ�����������飡");
		}

		// ��ȡ������
		HSSFRow row = sheet.getRow(0);
		int columnCount = row.getLastCellNum();
		if (row.getCell(0) == null) {// �����¹������(0,0)Ϊ�գ�����ҳsheet����
			return data;
		}

		// û������
		if (columnCount == 0) {
			throw new IOException("��Ϊ�գ����飡");
		}

		// ��ȡ��ֵ������
		row = sheet.getRow(1);
		String[] key = new String[columnCount];
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			key[columnIndex] = getCellData(row.getCell(columnIndex));
		}

		

		// ������
		Map<String, String> rowData = new HashMap<String, String>();
		for (int rowIndex = 2; rowIndex < rowCount; rowIndex++) {
			// ��ȡ��
			row = sheet.getRow(rowIndex);
			// ��ȡ����
			rowData = new HashMap<String, String>();
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				rowData.put(key[columnIndex],
						getCellData(row.getCell(columnIndex)));
			}
			data.add(rowData);
		}

		return data;
	}

	/**
	 * ��ȡ��Ԫ���е�����
	 * 
	 * @param cell
	 *            Ҫ��ȡ���ݵĵ�Ԫ��
	 * @return ��������
	 */
	public static String getCellData(HSSFCell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING: {
			return cell.toString();
		}

		case HSSFCell.CELL_TYPE_NUMERIC: {
			String tmp = cell.toString();
			if (tmp.endsWith(".0")) {
				tmp = tmp.substring(0, tmp.length() - 2);
			}
			return tmp;
		}

		default: {
			return cell.toString();
		}
		}
	}

	/**
	 * ��ȡ����ҳ�������
	 * 
	 * @param path
	 * 
	 * @return ����sheet������
	 */
	public static String[] getSheetName(String path) throws IOException {
		// �ҵ��ļ�
		InputStream inp = new FileInputStream(path);
		// ��excel
		HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(inp));
		int count = workBook.getNumberOfSheets();
		String[] sheetName = new String[count];
		for (int i = 0; i < count; i++) {
			sheetName[i] = workBook.getSheetName(i);
		}
		return sheetName;
	}
}
