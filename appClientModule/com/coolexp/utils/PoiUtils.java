package com.coolexp.utils;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.util.NumberToTextConverter;


public class PoiUtils {

	/**
	 * ��������int��ȡcell�е�ֵ
	 * 
	 * @param cell
	 * @return 0,��cellΪ��ʱ;���򷵻�����������ʾ��ֵ
	 * @exception NumberFormatException
	 */
	public static int getIntValue(HSSFCell cell) {
		if (cell == null || cell.toString().length() == 0) {
			return 0;
		}
		return (int) Double.parseDouble(cell.toString());
	}

	/**
	 * ��������short��ȡcell�е�ֵ
	 * 
	 * @param cell
	 * @return 0,��cellΪ��ʱ;���򷵻�����������ʾ��ֵ
	 * @exception NumberFormatException
	 */
	public static short getShortValue(HSSFCell cell) {
		if (cell == null || cell.toString().length() == 0) {
			return 0;
		}
		return (short) Double.parseDouble(cell.toString());
	}

	/**
	 * ���ո�����double��ȡcell�е�ֵ
	 * 
	 * @param cell
	 * @return 0.0,��cellΪ��ʱ;���򷵻�����������ʾ��ֵ
	 * @exception NumberFormatException
	 */
	public static double getDoubleValue(HSSFCell cell) {
		if (cell == null || cell.toString().length() == 0) {
			return 0.0;
		}
		return Double.parseDouble(cell.toString());
	}

	/**
	 * ���������Ͷ�ȡcell�е�ֵ
	 * 
	 * @param cell
	 * @param pattern
	 * @return null,��cellΪ��ʱ;���򷵻�����������ʾ��ֵ
	 */
	public static Date getDateValue(HSSFCell cell, String pattern) {
		if (cell != null && cell.toString().length() > 0) {
			return cell.getDateCellValue();
		}
		return null;

	}

	/***
	 * ����String
	 * 
	 * @param cell
	 * @return
	 * @throws IllegalAccessException
	 */
	public static String getStringValue(HSSFCell cell) throws IllegalAccessException {

		if (cell == null) {

			return "";
		}
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING: {
				return cell.toString();
			}
			case HSSFCell.CELL_TYPE_NUMERIC: {
				String str = NumberToTextConverter.toText(cell.getNumericCellValue());
				return str;
			}
			case HSSFCell.CELL_TYPE_FORMULA: {
				throw new IllegalAccessException(String.format(
						"cell[sheet:%s][column:%d][row:%d] type formula", cell
								.getSheet().getSheetName(), cell.getColumnIndex(),
						cell.getRowIndex()));
			}
			default: {
				return cell.toString();
	
			}
		}

	}

	public static float getFloatValue(HSSFCell cell) {
		if (cell == null || cell.toString().length() == 0) {
			return 0;
		}
		try {
			return Float.parseFloat(cell.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static String getIntString(HSSFCell cell) {
		return "" + getIntValue(cell);
	}
}
