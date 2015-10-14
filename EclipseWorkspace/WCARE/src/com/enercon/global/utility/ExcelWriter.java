package com.enercon.global.utility;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private final String sheetName;
	
	private final short darkGrey = IndexedColors.GREY_80_PERCENT.getIndex();
	private final short white = IndexedColors.WHITE.getIndex();
	private final short lightGrey = IndexedColors.GREY_25_PERCENT.getIndex();
	private final short black = IndexedColors.BLACK.getIndex();
	private final short mediumGrey = IndexedColors.GREY_50_PERCENT.getIndex();
	
	public XSSFCellStyle CENTERALIGN_WHITEFONT_DARKGREYBACKGROUND = null;
	public XSSFCellStyle LEFTALIGN_WHITEFONT_DARKGREYBACKGROUND = null;
	public XSSFCellStyle RIGHTALIGN_WHITEFONT_DARKGREYBACKGROUND = null;
	public XSSFCellStyle CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND = null;
	public XSSFCellStyle CENTERALIGN_BLACKFONT_WHITEBACKGROUND = null;
	public XSSFCellStyle RIGHTALIGN_BLACKFONT_LIGHTGREYBACKGROUND = null;
	public XSSFCellStyle RIGHTALIGN_BLACKFONT_WHITEBACKGROUND = null;
	public XSSFCellStyle RIGHTALIGN_BLACKFONT_MEDIUMGREYBACKGROUND = null;
	public XSSFCellStyle LEFTALIGN_BLACKFONT_MEDIUMGREYBACKGROUND = null;
	
	public ExcelWriter(String sheetName){

		workbook = new XSSFWorkbook();
		this.sheetName = sheetName;
		this.sheet = workbook.createSheet(this.sheetName);
		
		intializeStyle();
		
	}
	
	private void intializeStyle() {
		CENTERALIGN_WHITEFONT_DARKGREYBACKGROUND = getCellStyle(CellStyle.ALIGN_CENTER, white, darkGrey);
		LEFTALIGN_WHITEFONT_DARKGREYBACKGROUND = getCellStyle(CellStyle.ALIGN_LEFT, white, darkGrey);
		RIGHTALIGN_WHITEFONT_DARKGREYBACKGROUND = getCellStyle(CellStyle.ALIGN_RIGHT, white, darkGrey);
		CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND = getCellStyle(CellStyle.ALIGN_CENTER, black, lightGrey);
		CENTERALIGN_BLACKFONT_WHITEBACKGROUND = getCellStyle(CellStyle.ALIGN_CENTER, black, white);
		RIGHTALIGN_BLACKFONT_LIGHTGREYBACKGROUND = getCellStyle(CellStyle.ALIGN_RIGHT, black, lightGrey);
		RIGHTALIGN_BLACKFONT_WHITEBACKGROUND = getCellStyle(CellStyle.ALIGN_RIGHT, black, white);
		RIGHTALIGN_BLACKFONT_MEDIUMGREYBACKGROUND = getCellStyle(CellStyle.ALIGN_RIGHT, black, mediumGrey);
		LEFTALIGN_BLACKFONT_MEDIUMGREYBACKGROUND = getCellStyle(CellStyle.ALIGN_LEFT, black, mediumGrey);
	}

	private void initialize(String sheetName) {
		
		
	}

	public void data(int row, int column, double data) {
		sheet.getRow(row).getCell(column).setCellValue(data);
	}

	public void mergeColumn(int startRow,int endRow,int startColumnNo,int endColumnNo) {
		sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startColumnNo, endColumnNo));
	}

	public XSSFWorkbook getWorkBook() {
		return workbook;
	}

	public void data(int row, int column, String data) {
		sheet.getRow(row).getCell(column).setCellValue(data);
	}
	
	public void data(int row, int column, int data) {
		sheet.getRow(row).getCell(column).setCellValue(data);
	}

	public void createColumn(int currentRow, int currentColumn) {
		sheet.getRow(currentRow).createCell(currentColumn);
	}

	public void createRow(int currentRow) {
		sheet.createRow(currentRow);
	}

	public void mergeColumnWithRow(int row, int startColumnNo, int endColumnNo) {
		mergeColumn(row, row, startColumnNo, endColumnNo);
	}
		
	public void doRightBorder(int startRow, int endRow, int startColumn, int endColumn, short borderStyle) {
		org.apache.poi.ss.util.CellRangeAddress region = new org.apache.poi.ss.util.CellRangeAddress(startRow, endRow, startColumn, endColumn);
		RegionUtil.setBorderRight(borderStyle, region, sheet, workbook);
	}
	
	public void doLeftBorder(int startRow, int endRow, int startColumn, int endColumn, short borderStyle) {
		org.apache.poi.ss.util.CellRangeAddress region = new org.apache.poi.ss.util.CellRangeAddress(startRow, endRow, startColumn, endColumn);
		RegionUtil.setBorderLeft(borderStyle, region, sheet, workbook);
		
	}
	
	public void doTopBorder(int startRow, int endRow, int startColumn, int endColumn, short borderStyle) {
		org.apache.poi.ss.util.CellRangeAddress region = new org.apache.poi.ss.util.CellRangeAddress(startRow, endRow, startColumn, endColumn);
		RegionUtil.setBorderTop(borderStyle, region, sheet, workbook);
		
	}
	
	public void doBottomBorder(int startRow, int endRow, int startColumn, int endColumn, short borderStyle) {
		org.apache.poi.ss.util.CellRangeAddress region = new org.apache.poi.ss.util.CellRangeAddress(startRow, endRow, startColumn, endColumn);
		RegionUtil.setBorderBottom(borderStyle, region, sheet, workbook);
		
	}
	
	public void doBorder(int startRow, int endRow, int startColumn, int endColumn, short borderStyle) {
		
		org.apache.poi.ss.util.CellRangeAddress region = new org.apache.poi.ss.util.CellRangeAddress(startRow, endRow, startColumn, endColumn);
		RegionUtil.setBorderBottom(borderStyle, region, sheet, workbook);
		RegionUtil.setBorderTop(borderStyle, region, sheet, workbook);
		RegionUtil.setBorderLeft(borderStyle, region, sheet, workbook);
		RegionUtil.setBorderRight(borderStyle, region, sheet, workbook);
	}
	
	public XSSFCellStyle getCellStyle(short textAlignment, short fontColor, short foregroundColor){
		
		XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		XSSFFont font = sheet.getWorkbook().createFont();
		
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(textAlignment);
		cellStyle.setFillForegroundColor(foregroundColor);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBold(true);
		font.setColor(fontColor);
		
		cellStyle.setFont(font);
		return cellStyle;
		
	}
	
	
	public void styleCell(int rowNo, int columnNo, short textAlignment, short fontColor, short foregroundColor){
		
		XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		XSSFFont font = sheet.getWorkbook().createFont();
		
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(textAlignment);
		cellStyle.setFillForegroundColor(foregroundColor);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBold(true);
		font.setColor(fontColor);
		cellStyle.setFont(font);
		sheet.getRow(rowNo).getCell(columnNo).setCellStyle(cellStyle);
		
	}
	
	public void styleCell(int rowNo, int columnNo, XSSFCellStyle cellStyle){
		sheet.getRow(rowNo).getCell(columnNo).setCellStyle(cellStyle);
	}

	public void data(int row, int column, Number data) {
		sheet.getRow(row).getCell(column).setCellValue(data.doubleValue());
	}

	public void data(int row, int column, Object value) {
		sheet.getRow(row).getCell(column).setCellValue(value.toString());
	}
}
