package com.afis.web.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class POIUtil {

	private final static short COLUMN_WIDTH = 2 * 10 * 256; // 单位是1/256个字符宽度

	private final static DataFormatter df = new DataFormatter();

	/**
	 * 读取excel
	 * 
	 * @param file
	 *            文件完整路径
	 * @param startRow
	 *            从第几行开始读，0是第一行
	 * @param colNum
	 *            总共有几列。 传0，自动获取实际有数据的列数
	 * @return
	 */
	public static List<List<String>> readExcel(MultipartFile multipartFile, int startRow, int colNum) {
		List<String> colList = null;
		List<List<String>> rowList = new ArrayList<List<String>>();

		try {
			InputStream input = multipartFile.getInputStream();
			
			XSSFWorkbook wb = new XSSFWorkbook(input);

			FormulaEvaluator formulaEval = wb.getCreationHelper().createFormulaEvaluator();

			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row = null;

			// 得到总行数
			int rowNum = sheet.getLastRowNum();
			for (int i = startRow; i <= rowNum; i++) {
				colList = new ArrayList<String>();
				row = sheet.getRow(i);
				// 如果文件中间某一行是空行，则跳过
				if(row == null){
					continue;
				}
				int j = 0;
				if (colNum == 0) {
					colNum = row.getLastCellNum();
				}
				while (j < colNum) {
					String str = getCellFormatValue(row.getCell((short) j), formulaEval);
					colList.add(str);
					j++;
				}
				rowList.add(colList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowList;
	}
	
	/**
	 * 读取excel
	 * 
	 * @param file
	 *            文件完整路径
	 * @param startRow
	 *            从第几行开始读，0是第一行
	 * @param colNum
	 *            总共有几列。 传0，自动获取实际有数据的列数
	 * @return
	 */
	public static List<List<String>> readExcel(String file, int startRow, int colNum) {
		List<String> colList = null;
		List<List<String>> rowList = new ArrayList<List<String>>();

		try {
			InputStream is = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(is);

			FormulaEvaluator formulaEval = wb.getCreationHelper().createFormulaEvaluator();

			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row = sheet.getRow(startRow);

			// 得到总行数
			int rowNum = sheet.getLastRowNum();

			if (colNum == 0) {
				colNum = row.getLastCellNum();
			}

			for (int i = startRow; i <= rowNum; i++) {
				colList = new ArrayList<String>();
				row = sheet.getRow(i);
				int j = 0;
				while (j < colNum) {
					String str = getCellFormatValue(row.getCell((short) j), formulaEval);
					colList.add(str);
					j++;
				}
				rowList.add(colList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowList;
	}

	private static String getCellFormatValue(XSSFCell cell, FormulaEvaluator formulaEval) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				} else {
					cellvalue = df.formatCellValue(cell);
				}
				break;
			default:
				cellvalue = df.formatCellValue(cell, formulaEval);
			}
		}
		return cellvalue;
	}

	private static Font setTitleFont(SXSSFWorkbook wb) {
		Font font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 16);// 字体大小
		return font;
	}

	private static Font setHeaderFont(SXSSFWorkbook wb) {
		Font font = wb.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 10);// 字体大小
		return font;
	}

	private static CellStyle setTitleStyle(SXSSFWorkbook wb) {
		Font font = setTitleFont(wb);

		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.setWrapText(true);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER); // 左右居中
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 上下居中

		titleStyle.setFont(font);
		return titleStyle;
	}

	private static CellStyle setHeaderStyle(SXSSFWorkbook wb) {
		Font font = setHeaderFont(wb);

		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setWrapText(true);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER); // 左右居中
		headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 上下居中
		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerStyle.setBorderTop(CellStyle.BORDER_THIN);

		headerStyle.setFont(font);
		return headerStyle;
	}

	private static CellStyle setCriteriaStyle(SXSSFWorkbook wb) {
		Font font = setHeaderFont(wb); // 查询条件字体和表头一样
		font.setFontHeightInPoints((short) 12);

		CellStyle style = wb.createCellStyle();
		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);

		style.setFont(font);

		return style;
	}

	private static CellStyle setDataStyle(SXSSFWorkbook wb) {
		CellStyle cellStyle = setHeaderStyle(wb); // 数据和表头的样式一样

		org.apache.poi.ss.usermodel.DataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@")); // 设置成文本格式

		return cellStyle;
	}

	private static CellStyle setDataIntStyle(SXSSFWorkbook wb) {
		CellStyle cellStyle = setHeaderStyle(wb); // 数据和表头的样式一样

		org.apache.poi.ss.usermodel.DataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("#,##0.000")); // 设置成文本格式

		return cellStyle;
	}

	private static void setCell(Sheet sheet, Row newRow, CellStyle cellStyle, Cell newCell, int cellNum, String value) {
		newCell = newRow.createCell(cellNum);
		newCell.setCellStyle(cellStyle);
		newCell.setCellValue(value);
	}

	private static void setIntCell(Sheet sheet, Row newRow, CellStyle cellStyle, Cell newCell, int cellNum,
			String value) {
		newCell = newRow.createCell(cellNum);
		newCell.setCellStyle(cellStyle);
		newCell.setCellValue(Double.parseDouble(value));
	}

	/**
	 * 
	 * @param fileFullName
	 *            导出路径
	 * @param headerArray
	 *            表头列表
	 * @param datas
	 *            里层是列List，外层是行List
	 * @param title
	 *            报表标题
	 * @param criteria
	 *            数据查询条件，显示在表格上方
	 * @param sumRow
	 *            合计，显示在最后一行，依次显示在最后几列。如表头有三列[1|2|3]，传list里有2和3, 合计就显示在最后一行2,3列
	 * @return
	 */
	public static boolean exportExcel(String fileFullName, String[] headerArray, List<List<String>> datas, String title,
			String criteria, List<String> sumRow) {
		SXSSFWorkbook wb = new SXSSFWorkbook();
		Sheet sheet = wb.createSheet();

		CellStyle titleStyle = setTitleStyle(wb);
		CellStyle headerStyle = setHeaderStyle(wb);
		CellStyle criteriaStyle = setCriteriaStyle(wb);
		CellStyle dataStyle = setDataStyle(wb);

		int rowNum = 0;
		Cell newCell = null;

		// 设置列宽
		if (headerArray != null) {
			for (int i = 0; i < headerArray.length; i++) {
				sheet.setColumnWidth(i, COLUMN_WIDTH);
			}
		}

		// set title
		if (!"".equals(getValidString(title))) {
			Row titleRow = sheet.createRow(rowNum++);
			for (int k = 0; k < headerArray.length; k++) {
				newCell = titleRow.createCell(k);
				newCell.setCellStyle(titleStyle);

				if (k == 0) {
					newCell.setCellValue(title); // 合并单元格之前，在第一个单元格设置标题
				}
			}

			titleRow.setHeight((short) 600);

			CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, headerArray.length - 1);
			sheet.addMergedRegion(titleRange);
		}

		// set criteria
		if (!"".equals(getValidString(criteria))) {
			int currRowNum = rowNum;
			Row criteriaRow = sheet.createRow(rowNum++);
			for (int k = 0; k < headerArray.length; k++) {
				newCell = criteriaRow.createCell(k);
				newCell.setCellStyle(criteriaStyle);

				if (k == 0) {
					newCell.setCellValue(criteria); // 合并单元格之前，在第一个单元格设置查询条件
				}
			}

			// criteriaRow.setHeight((short)1000);

			CellRangeAddress criteriaRange = new CellRangeAddress(currRowNum, currRowNum, 0, headerArray.length - 1);
			sheet.addMergedRegion(criteriaRange);

		}

		// set table header
		Row headerRow = sheet.createRow(rowNum++);
		for (int i = 0; i < headerArray.length; i++) {
			newCell = headerRow.createCell(i);
			newCell.setCellValue(headerArray[i]);
			newCell.setCellStyle(headerStyle);
		}

		// set datas
		Row newRow;
		List<String> colList;
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				newRow = sheet.createRow(rowNum++);

				colList = datas.get(i);
				if (colList != null && colList.size() > 0) {
					for (int j = 0; j < colList.size(); j++) {
						setCell(sheet, newRow, dataStyle, newCell, j, colList.get(j));
					}
				}
			}
		}

		// 合计
		if (sumRow != null && sumRow.size() > 0) {
			int currRowNum = rowNum;
			newRow = sheet.createRow(rowNum++);

			int sumInd = 0;
			for (int k = 0; k < headerArray.length; k++) {
				newCell = newRow.createCell(k);
				newCell.setCellStyle(dataStyle);

				if (k == 0) {
					newCell.setCellValue("合计"); // 合并单元格之前，在第一个单元格设置合计
				} else {
					// 依次填合计的列值
					if (k >= headerArray.length - sumRow.size()) {
						setCell(sheet, newRow, dataStyle, newCell, k, sumRow.get(sumInd++));
					}
				}
			}

			CellRangeAddress criteriaRange = new CellRangeAddress(currRowNum, currRowNum, 0,
					headerArray.length - sumRow.size() - 1);
			sheet.addMergedRegion(criteriaRange);
		}

		try {
			FileOutputStream fileoutputstream = new FileOutputStream(fileFullName);
			wb.write(fileoutputstream);
			fileoutputstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static boolean exportIntExcel(String fileFullName, String[] headerArray, List<List<String>> datas,
			String title, String criteria, List<String> sumRow, Integer intColumn) {
		SXSSFWorkbook wb = new SXSSFWorkbook();
		Sheet sheet = wb.createSheet();

		CellStyle titleStyle = setTitleStyle(wb);
		CellStyle headerStyle = setHeaderStyle(wb);
		CellStyle criteriaStyle = setCriteriaStyle(wb);
		CellStyle dataStyle = setDataStyle(wb);
		CellStyle dataIntStyle = setDataIntStyle(wb);

		int rowNum = 0;
		Cell newCell = null;

		// 设置列宽
		if (headerArray != null) {
			for (int i = 0; i < headerArray.length; i++) {
				sheet.setColumnWidth(i, COLUMN_WIDTH);
			}
		}

		// set title
		if (!"".equals(getValidString(title))) {
			Row titleRow = sheet.createRow(rowNum++);
			for (int k = 0; k < headerArray.length; k++) {
				newCell = titleRow.createCell(k);
				newCell.setCellStyle(titleStyle);

				if (k == 0) {
					newCell.setCellValue(title); // 合并单元格之前，在第一个单元格设置标题
				}
			}

			titleRow.setHeight((short) 600);

			CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, headerArray.length - 1);
			sheet.addMergedRegion(titleRange);
		}

		// set criteria
		if (!"".equals(getValidString(criteria))) {
			int currRowNum = rowNum;
			Row criteriaRow = sheet.createRow(rowNum++);
			for (int k = 0; k < headerArray.length; k++) {
				newCell = criteriaRow.createCell(k);
				newCell.setCellStyle(criteriaStyle);

				if (k == 0) {
					newCell.setCellValue(criteria); // 合并单元格之前，在第一个单元格设置查询条件
				}
			}

			// criteriaRow.setHeight((short)1000);

			CellRangeAddress criteriaRange = new CellRangeAddress(currRowNum, currRowNum, 0, headerArray.length - 1);
			sheet.addMergedRegion(criteriaRange);

		}

		// set table header
		Row headerRow = sheet.createRow(rowNum++);
		for (int i = 0; i < headerArray.length; i++) {
			newCell = headerRow.createCell(i);
			newCell.setCellValue(headerArray[i]);
			newCell.setCellStyle(headerStyle);
		}

		// set datas
		Row newRow;
		List<String> colList;
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				newRow = sheet.createRow(rowNum++);

				colList = datas.get(i);
				if (colList != null && colList.size() > 0) {
					for (int j = 0; j < colList.size(); j++) {
						if (j == intColumn) {
							setIntCell(sheet, newRow, dataIntStyle, newCell, j, colList.get(j));
						} else {
							setCell(sheet, newRow, dataStyle, newCell, j, colList.get(j));
						}

					}
				}
			}
		}

		// 合计
		if (sumRow != null && sumRow.size() > 0) {
			int currRowNum = rowNum;
			newRow = sheet.createRow(rowNum++);

			int sumInd = 0;
			for (int k = 0; k < headerArray.length; k++) {
				newCell = newRow.createCell(k);
				newCell.setCellStyle(dataStyle);

				if (k == 0) {
					newCell.setCellValue("合计"); // 合并单元格之前，在第一个单元格设置合计
				} else {
					// 依次填合计的列值
					if (k >= headerArray.length - sumRow.size()) {
						setCell(sheet, newRow, dataStyle, newCell, k, sumRow.get(sumInd++));
					}
				}
			}

			CellRangeAddress criteriaRange = new CellRangeAddress(currRowNum, currRowNum, 0,
					headerArray.length - sumRow.size() - 1);
			sheet.addMergedRegion(criteriaRange);
		}

		try {
			FileOutputStream fileoutputstream = new FileOutputStream(fileFullName);
			wb.write(fileoutputstream);
			fileoutputstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static String getValidString(String str) {
		if (str == null || "null".equalsIgnoreCase(str)) {
			return "";
		}
		return str.trim();
	}

}
