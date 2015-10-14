package com.enercon.global.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public ArrayList<ArrayList<String>> method1(){
		ArrayList<ArrayList<String>> m = new ArrayList<ArrayList<String>>();
		ArrayList<String> cellValues = null;
		try {
			//Hashtable<String, ArrayList<String>> m = new Hashtable<String, ArrayList<String>>();
			
			FileInputStream file = new FileInputStream(new File("C:\\uploads\\Book1.xlsx"));

			XSSFWorkbook  workbook = new XSSFWorkbook(file);

			//Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			
			//Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			//String cellValue = "";
			Boolean cellBooleanValue = null;
			Double cellDoubleValue = null;
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				cellValues = new ArrayList<String>(); 
				//For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					/*cellValue = cell.getStringCellValue();
	            System.out.print("_" + cellValue + "\t\t");*/

					switch(cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						cellBooleanValue = cell.getBooleanCellValue();
						cellValues.add(cellBooleanValue.toString());
						System.out.print(cell.getBooleanCellValue() + "\t\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellDoubleValue = cell.getNumericCellValue();
						cellValues.add(cellDoubleValue.toString());
						System.out.print(cell.getNumericCellValue() + "\t\t");

						break;
					case Cell.CELL_TYPE_STRING:
						cellValues.add(cell.getStringCellValue());
						System.out.print(cell.getStringCellValue() + "\t\t");
						break;
					}
				}

				System.out.println("");
				m.add(cellValues);
				cellValues = new ArrayList<String>();
			}
			//GlobalUtils.displayVectorMember(m);
			file.close();
			FileOutputStream out = 
					new FileOutputStream(new File("C:\\test.xls"));
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public ArrayList<ArrayList<String>> method2(){
		ArrayList<ArrayList<String>> m = new ArrayList<ArrayList<String>>();
		ArrayList<String> cellValues = null;
		try {
			//Hashtable<String, ArrayList<String>> m = new Hashtable<String, ArrayList<String>>();
			
			FileInputStream file = new FileInputStream(new File("C:\\uploads\\DGR.xls"));

			//XSSFWorkbook  workbook = new XSSFWorkbook(file);

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			//Get first sheet from the workbook
			//XSSFSheet sheet = workbook.getSheetAt(0);
			
			HSSFSheet sheet = workbook.getSheetAt(0);

			
			//Iterate through each rows from first sheet
			//Iterator<Row> rowIterator = sheet.iterator();
			//org.apache.poi.ss.usermodel.Row;
			Iterator<Row> rowIterator = sheet.rowIterator();
			//String cellValue = "";
			Boolean cellBooleanValue = null;
			Double cellDoubleValue = null;
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				cellValues = new ArrayList<String>(); 
				//For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					/*cellValue = cell.getStringCellValue();
	            System.out.print("_" + cellValue + "\t\t");*/

					switch(cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						cellBooleanValue = cell.getBooleanCellValue();
						cellValues.add(cellBooleanValue.toString());
						System.out.print(cell.getBooleanCellValue() + "\t\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellDoubleValue = cell.getNumericCellValue();
						cellValues.add(cellDoubleValue.toString());
						System.out.print(cell.getNumericCellValue() + "\t\t");

						break;
					case Cell.CELL_TYPE_STRING:
						cellValues.add(cell.getStringCellValue());
						System.out.print(cell.getStringCellValue() + "\t\t");
						break;
					}
				}

				System.out.println("");
				m.add(cellValues);
				cellValues = new ArrayList<String>();
			}
			//GlobalUtils.displayVectorMember(m);
			file.close();
			FileOutputStream out = 
					new FileOutputStream(new File("C:\\test.xls"));
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public static void main(String[] args) {
		new ExcelReader().method2();
	}
}
