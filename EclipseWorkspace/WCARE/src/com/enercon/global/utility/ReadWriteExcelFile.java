package com.enercon.global.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.enercon.struts.exception.ExcelException;

public class ReadWriteExcelFile {
	 private final static Logger logger = Logger.getLogger(ReadWriteExcelFile.class);
	public static ArrayList<ArrayList<String>> readXLSFile() throws IOException{
		ArrayList<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
		ArrayList<String> rowData = new ArrayList<String>();
		
		try {
		     
		    FileInputStream file = new FileInputStream(new File("C:\\uploads\\DGR.xls"));
		     
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    
		    Boolean cellBooleanValue = null;
		    Double cellDoubleValue = null;
		     
		    //Iterate through each rows from first sheet
		    Iterator<Row> rowIterator = sheet.iterator();
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while(cellIterator.hasNext()) {
		             
		            Cell cell = cellIterator.next();
		             
		            switch(cell.getCellType()) {
		                case Cell.CELL_TYPE_BOOLEAN:
		                    //System.out.print(cell.getBooleanCellValue() + "\t\t");
		                	cellBooleanValue = cell.getBooleanCellValue();
		                    rowData.add(cellBooleanValue.toString());
		                    break;
		                case Cell.CELL_TYPE_NUMERIC:
		                    //System.out.print(cell.getNumericCellValue() + "\t\t");
		                	cellDoubleValue = cell.getNumericCellValue();
		                    rowData.add(cellDoubleValue.toString());
		                    break;
		                case Cell.CELL_TYPE_STRING:
		                    //System.out.print(cell.getStringCellValue() + "\t\t");
		                    rowData.add(cell.getStringCellValue());
		                    break;
		            }
		            
		        }
		        if(rowData.size() < 1){
		        	continue;
		        }
		        //System.out.println(rowData);
		        excelData.add(rowData);
		        rowData = new ArrayList<String>();
		        //System.out.println("");
		    }
		    //System.out.println(excelData.toString());
		    file.close();
		} catch (FileNotFoundException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return excelData;
	}
	
	public static ArrayList<ArrayList<String>> readXLSFile(String absoluteFilePath) throws IOException{
		
		ArrayList<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
		ArrayList<String> rowData = new ArrayList<String>();
		
		try {
		     
		    FileInputStream file = new FileInputStream(new File(absoluteFilePath));
		     
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    
		    Boolean cellBooleanValue = null;
		    Double cellDoubleValue = null;
		     
		    //Iterate through each rows from first sheet
		    Iterator<Row> rowIterator = sheet.iterator();
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while(cellIterator.hasNext()) {
		             
		            Cell cell = cellIterator.next();
		             
		            switch(cell.getCellType()) {
		                case Cell.CELL_TYPE_BOOLEAN:
		                    //System.out.print(cell.getBooleanCellValue() + "\t\t");
		                	cellBooleanValue = cell.getBooleanCellValue();
		                    rowData.add(cellBooleanValue.toString());
		                    break;
		                case Cell.CELL_TYPE_NUMERIC:
		                    //System.out.print(cell.getNumericCellValue() + "\t\t");
		                	cellDoubleValue = cell.getNumericCellValue();
		                    rowData.add(cellDoubleValue.toString());
		                    break;
		                case Cell.CELL_TYPE_STRING:
		                    //System.out.print(cell.getStringCellValue() + "\t\t");
		                    rowData.add(cell.getStringCellValue());
		                    break;
		            }
		            
		        }
		        if(rowData.size() < 1){
		        	continue;
		        }
		        //System.out.println(rowData);
		        excelData.add(rowData);
		        rowData = new ArrayList<String>();
		        //System.out.println("");
		    }
		    //System.out.println(excelData.toString());
		    file.close();
		} catch (FileNotFoundException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return excelData;
	}
	
	public static ArrayList<ArrayList<String>> readXLSFile(String absoluteFilePath, String sheetName) throws IOException, ExcelException{
		ArrayList<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
		ArrayList<String> rowData = new ArrayList<String>();

		FileInputStream file = new FileInputStream(new File(absoluteFilePath));

		//Get the workbook instance for XLS file 
		HSSFWorkbook workbook = new HSSFWorkbook(file);

		int index = workbook.getSheetIndex(sheetName);
		if(index == -1){
			throw new ExcelException("error.file.excel.sheet.present");
		}
		
		//Get first sheet from the workbook
		HSSFSheet sheet = workbook.getSheet(sheetName);

		Boolean cellBooleanValue = null;
		Double cellDoubleValue = null;

		//Iterate through each rows from first sheet
		Iterator<Row> rowIterator = sheet.iterator();
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();

			//For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext()) {

				Cell cell = cellIterator.next();

				switch(cell.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					//System.out.print(cell.getBooleanCellValue() + "\t\t");
					cellBooleanValue = cell.getBooleanCellValue();
					rowData.add(cellBooleanValue.toString());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					//System.out.print(cell.getNumericCellValue() + "\t\t");
					cellDoubleValue = cell.getNumericCellValue();
					rowData.add(cellDoubleValue.toString());
					break;
				case Cell.CELL_TYPE_STRING:
					//System.out.print(cell.getStringCellValue() + "\t\t");
					rowData.add(cell.getStringCellValue());
					break;
				}

			}
			if(rowData.size() < 1){
				continue;
			}
			//System.out.println(rowData);
			excelData.add(rowData);
			rowData = new ArrayList<String>();
			//System.out.println("");
		}
		//System.out.println(excelData.toString());
		file.close();

		return excelData;
	}
	
	public static void writeXLSFile() throws IOException {}
	
	public static ArrayList<ArrayList<String>> readXLSXFile() throws IOException{
		ArrayList<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
		ArrayList<String> rowData = null;
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
				rowData = new ArrayList<String>(); 
				//For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					//System.out.println(cell.toString());
					/*cellValue = cell.getStringCellValue();
	            System.out.print("_" + cellValue + "\t\t");*/

					switch(cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						cellBooleanValue = cell.getBooleanCellValue();
						rowData.add(cellBooleanValue.toString());
						//System.out.print(cell.getBooleanCellValue() + "\t\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellDoubleValue = cell.getNumericCellValue();
						rowData.add(cellDoubleValue.toString());
						//System.out.print(cell.getNumericCellValue() + "\t\t");

						break;
					case Cell.CELL_TYPE_STRING:
						rowData.add(cell.getStringCellValue());
						//System.out.print(cell.getStringCellValue() + "\t\t");
						break;
					}
				}
				
				if(rowData.size() < 1){
					continue;
				}
				//System.out.println(rowData);
				excelData.add(rowData);
				rowData = new ArrayList<String>();
			}
			//System.out.println(rowData);
			file.close();
			FileOutputStream out = 
					new FileOutputStream(new File("C:\\test.xls"));
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return excelData;
	}
	
	public static ArrayList<ArrayList<String>> readXLSXFile(String absoluteFilePath) throws IOException{
		ArrayList<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
		ArrayList<String> rowData = null;
		try {
			//Hashtable<String, ArrayList<String>> m = new Hashtable<String, ArrayList<String>>();
			
			FileInputStream file = new FileInputStream(new File(absoluteFilePath));

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
				rowData = new ArrayList<String>(); 
				//For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					//System.out.println(cell.toString());
					/*cellValue = cell.getStringCellValue();
	            System.out.print("_" + cellValue + "\t\t");*/

					switch(cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						cellBooleanValue = cell.getBooleanCellValue();
						rowData.add(cellBooleanValue.toString());
						//System.out.print(cell.getBooleanCellValue() + "\t\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellDoubleValue = cell.getNumericCellValue();
						rowData.add(cellDoubleValue.toString());
						//System.out.print(cell.getNumericCellValue() + "\t\t");

						break;
					case Cell.CELL_TYPE_STRING:
						rowData.add(cell.getStringCellValue());
						//System.out.print(cell.getStringCellValue() + "\t\t");
						break;
					}
				}
				
				if(rowData.size() < 1){
					continue;
				}
				//System.out.println(rowData);
				excelData.add(rowData);
				rowData = new ArrayList<String>();
			}
			//System.out.println(rowData);
			file.close();
			FileOutputStream out = 
					new FileOutputStream(new File("C:\\test.xls"));
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return excelData;
	}
	
	public static ArrayList<ArrayList<String>> readXLSXFile(String absoluteFilePath, String sheetName) throws IOException, ExcelException{
		ArrayList<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();
		ArrayList<String> rowData = null;
		try {
			//Hashtable<String, ArrayList<String>> m = new Hashtable<String, ArrayList<String>>();
			
			FileInputStream file = new FileInputStream(new File(absoluteFilePath));

			XSSFWorkbook  workbook = new XSSFWorkbook(file);

			int index = workbook.getSheetIndex(sheetName);
			if(index == -1){
				throw new ExcelException("error.file.excel.sheet.present");
			}
			//Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);

			
			//Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			//String cellValue = "";
			Boolean cellBooleanValue = null;
			Double cellDoubleValue = null;
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				rowData = new ArrayList<String>(); 
				//For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					//System.out.println(cell.toString());
					/*cellValue = cell.getStringCellValue();
	            System.out.print("_" + cellValue + "\t\t");*/

					switch(cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						cellBooleanValue = cell.getBooleanCellValue();
						rowData.add(cellBooleanValue.toString());
						//System.out.print(cell.getBooleanCellValue() + "\t\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellDoubleValue = cell.getNumericCellValue();
						rowData.add(cellDoubleValue.toString());
						//System.out.print(cell.getNumericCellValue() + "\t\t");

						break;
					case Cell.CELL_TYPE_STRING:
						rowData.add(cell.getStringCellValue());
						//System.out.print(cell.getStringCellValue() + "\t\t");
						break;
					}
				}
				
				if(rowData.size() < 1){
					continue;
				}
				//System.out.println(rowData);
				excelData.add(rowData);
				rowData = new ArrayList<String>();
			}
			//System.out.println(rowData);
			file.close();
			FileOutputStream out = 
					new FileOutputStream(new File("C:\\test.xls"));
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return excelData;
	}
	
	public static void writeXLSXFile() throws IOException {}

	public static void main(String[] args) throws IOException {
		//writeXLSFile();
		ArrayList<ArrayList<String>> excelData = readXLSFile();
		System.out.println(excelData);
		excelData = readXLSXFile();
		System.out.println(excelData);
		//writeXLSXFile();
		//readXLSXFile();

	}
	
	

}
