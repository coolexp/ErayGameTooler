package com.coolexp.utils;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.util.NumberToTextConverter;


public class PoiUtils {

	/**
	 * 按照整形int读取cell中的值
	 * 
	 * @param cell
	 * @return 0,当cell为空时;否则返回其内容所表示的值
	 * @exception NumberFormatException
	 */
	public static int getIntValue(HSSFCell cell) {
		if (cell == null || cell.toString().length() == 0) {
			return 0;
		}
		return (int) Double.parseDouble(cell.toString());
	}

	/**
	 * 按照整形short读取cell中的值
	 * 
	 * @param cell
	 * @return 0,当cell为空时;否则返回其内容所表示的值
	 * @exception NumberFormatException
	 */
	public static short getShortValue(HSSFCell cell) {
		if (cell == null || cell.toString().length() == 0) {
			return 0;
		}
		return (short) Double.parseDouble(cell.toString());
	}

	/**
	 * 按照浮点型double读取cell中的值
	 * 
	 * @param cell
	 * @return 0.0,当cell为空时;否则返回其内容所表示的值
	 * @exception NumberFormatException
	 */
	public static double getDoubleValue(HSSFCell cell) {
		if (cell == null || cell.toString().length() == 0) {
			return 0.0;
		}
		return Double.parseDouble(cell.toString());
	}

	/**
	 * 按照日期型读取cell中的值
	 * 
	 * @param cell
	 * @param pattern
	 * @return null,当cell为空时;否则返回其内容所表示的值
	 */
	public static Date getDateValue(HSSFCell cell, String pattern) {
		if (cell != null && cell.toString().length() > 0) {
			return cell.getDateCellValue();
		}
		return null;

	}

	/***
	 * 返回String
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
