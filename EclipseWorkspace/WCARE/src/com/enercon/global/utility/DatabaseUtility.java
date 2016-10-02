package com.enercon.global.utility;

import java.io.BufferedReader;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utils.JDBCUtils;

import static com.enercon.connection.WcareConnector.wcareConnector;

public class DatabaseUtility {
	private final static Logger logger = Logger.getLogger(DatabaseUtility.class);
	/**
	 *Gets the result of an SQL query into HTML file 
	 */
	public static void getSQLQueryResultInHTMLFile() {

		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		BufferedReader fileReader = null;
		PrintWriter fos = null;
		String lineString = "";
		String fileWriterPath = "C:\\Users\\HP\\Desktop\\TBL_ROLE.htm";
		StringBuffer content = new StringBuffer(getHeader());
		try {
			fos = new PrintWriter(new BufferedWriter(new FileWriter(
					fileWriterPath)), true);
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "SELECT * FROM TBL_ROLE";
			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();

			String[] columnNames = getColumnNames(rs);
			content.append("\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n "
					+ "\t\t\t<tr>\n");
			for (String columnName : columnNames) {
				content.append("\t\t\t\t<th>" + columnName + "</th>\n");
			}
			content.append("\t\t\t</tr>\n");
			content.append("\t\t\t<tbody id=\"data\">\n");
			while (rs.next()) {
				content.append("\t\t\t\t<tr>\n");
				for (String columnName : columnNames) {
					content.append("\t\t\t\t\t<td>" + rs.getObject(columnName)
							+ "</td>\n");
				}
				content.append("\t\t\t\t</tr>\n");
			}
			content.append("\t\t\t</tbody>\n");
			content.append("\t\t</table>\n");
			content.append("\t</body>\n" + "</html>\n");
			fos.println(new String(content));
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			try {
				DaoUtility.releaseResources(prepStmt, rs, conn);
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	}
	
	
	
	/**
	 *Stores Each Function Source Code of a Particular Database in a File
	 *Name of the File is the Fumction Name  
	 */
	public static void storingAllUserFunctionSourceInFile(){
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery1 = "";
		PreparedStatement prepStmt1 = null;
		ResultSet rs1 = null;
		BufferedReader fileReader = null;
		PrintWriter fos = null;
		String fileWriterPath = "";
			
		try{
			/*File Reader Part*/
			/*fileReader = new BufferedReader(new FileReader(fileReaderPath));
			while((fileLineReader = fileReader.readLine()) != null){
				System.out.println(fileLineReader);
			}*/
			
			/*File Writer Part*/
			/*fos = new PrintWriter(new BufferedWriter(new FileWriter(fileWriterPath)), true);*/
			
			/*SQL Query Part*/
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 	"Select OBJECT_NAME " + 
						"From User_Objects " + 
						"Where Upper(Object_Type) = 'FUNCTION' " ;
			prepStmt = conn.prepareStatement(sqlQuery);
				
			rs = prepStmt.executeQuery();
			int count = 0;
			while (rs.next()) {
				String functionName = rs.getString("OBJECT_NAME");
				System.out.println(++count +":"+ functionName);
				sqlQuery1 = "Select Text From User_Source Where Upper(Name) In (?)" ;
				prepStmt1 = conn.prepareStatement(sqlQuery1);
				prepStmt1.setObject(1, functionName);
				rs1 = prepStmt1.executeQuery();
				fileWriterPath = "D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\WWIL\\ECARE\\DataBase\\Functions\\"+ functionName +".sql";
				fos = new PrintWriter(new BufferedWriter(new FileWriter(fileWriterPath)), true);
				while(rs1.next()){
					fos.print(rs1.getString("TEXT"));
				}
				fos.close();
				fos = null;
				System.out.println();
			}
			//return null;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			try{
				DaoUtility.releaseResources(Arrays.asList(prepStmt, prepStmt1), Arrays.asList(rs, rs1), conn);
				if (fileReader != null) {
					fileReader.close();
				}
				if (fos != null) {
					fos.close();
				}
			}
			catch(Exception e){
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	}
	
	/**
	 *Stores All Table Names With Its Column Name of a particular Database in a file 
	 */
	public static void storingTableStartingWithTBLAlongwithItsColumnNames() {
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery1 = "";
		PreparedStatement prepStmt1 = null;
		ResultSet rs1 = null;
		
		BufferedReader fileReader = null;
		PrintWriter fos = null;
		String fileWriterPath = "D:\\StoredColumnNamesBetweenTables.txt";

		try {
			fos = new PrintWriter(new BufferedWriter(new FileWriter(fileWriterPath)), true);

			conn = conmanager.getConnection();
			sqlQuery = 	"Select Table_Name From User_Tables  " + 
						"                  Where Table_Name Like 'TBL%'  " + 
						"                  order by table_name " ;
			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				fos.println(tableName);
				sqlQuery1 = "Select Column_Name From User_Tab_Cols  " + 
						"WHERE table_name In (?) " ;
				prepStmt1 = conn.prepareStatement(sqlQuery1);
				prepStmt1.setString(1, tableName);
				
				rs1 = prepStmt1.executeQuery();
				while (rs1.next()) {
					String columnName = rs1.getString("Column_Name");
					fos.println(columnName);
				}
				fos.println();
			}
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (fileReader != null) {
					fileReader.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
		// return null;
	}

	/**
	 * 
	 */
	public static void mappingCommonColumnsBetweenTable() {
		Iterator<String> keyIter;
		Set<String> keysSet;

		BufferedReader schema = null;
		PrintWriter fos = null;

		HashMap<String, ArrayList<String>> tableColumnMap = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> columnTableMap = new HashMap<String, ArrayList<String>>();

		ArrayList<String> commonArrayList = new ArrayList<String>();
		String lineString = "";

		try {
			schema = new BufferedReader(
					new FileReader(
							"D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\WWIL\\ECARE\\UtilityFiles\\allTablesWithColumnNames.txt"));
			String currentTableName = null;

			/* Scanning the whole file and determining TableColumnMap */
			while ((lineString = schema.readLine()) != null) {

				/*
				 * Checking for table name and whitespace for storing Column
				 * names in ArrayList
				 */
				if (!lineString.matches(".*TBL.*") && !lineString.equals("")) {
					commonArrayList.add(lineString);
					continue;
				}

				/* Checking Table Name for storing table name in HashMap as Key */
				if (lineString.matches(".*TBL.*")) {
					if (currentTableName != null) {
						tableColumnMap.put(currentTableName, commonArrayList);
					}
					currentTableName = lineString;
					commonArrayList = null;
					commonArrayList = new ArrayList<String>();

					continue;
				}
			}
			if (currentTableName != null) {
				tableColumnMap.put(currentTableName, commonArrayList);
			}
			/* Determining ColumnTableMapping from TableColumnMapping */
			keysSet = tableColumnMap.keySet();
			keyIter = keysSet.iterator();
			while (keyIter.hasNext()) {
				String tableNameKey = keyIter.next();

				/* tableColumnMap.get(tableNameKey) is an ArrayList */
				for (String columnName : tableColumnMap.get(tableNameKey)) {
					if (columnTableMap.containsKey(columnName)) {
						commonArrayList = columnTableMap.get(columnName);
						commonArrayList.add(tableNameKey);
						columnTableMap.put(columnName, commonArrayList);
						/*
						 * commonArrayList = null; commonArrayList = new
						 * ArrayList<>();
						 */
					} else {
						/*
						 * commonArrayList.add(tableNameKey);
						 * columnTableMap.put(columnName, commonArrayList);
						 */
						ArrayList<String> c = new ArrayList<String>();
						c.add(tableNameKey);
						columnTableMap.put(columnName, c);
						c = null;
					}
				}
			}

			fos = new PrintWriter(
					new BufferedWriter(
							new FileWriter("D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\WWIL\\ECARE\\UtilityFiles\\CommonColumnNamesBetweenTables.txt")),true);
			/* For displaying on console */
			keysSet = columnTableMap.keySet();
			keyIter = keysSet.iterator();
			while (keyIter.hasNext()) {
				String columnNameKey = keyIter.next();

				/* Column Which are not needed */
				if (columnNameKey.equals("S_CREATED_BY")
						|| columnNameKey.equals("S_LAST_MODIFIED_BY")
						|| columnNameKey.equals("D_CREATED_DATE")
						|| columnNameKey.equals("D_LAST_MODIFIED_DATE")
						|| columnNameKey.equals("D_READING_DATE")
						|| columnNameKey.equals("S_REMARKS")) {
					continue;
				}

				/* Column Present in more than one table */
				if (columnTableMap.get(columnNameKey).size() > 1) {
					fos.println("Column: " + columnNameKey);

					/*
					 * 'columnTableMap.get(columnNameKey)' is an ArrayList
					 * having Table Name
					 */
					for (String tableNameValue : columnTableMap
							.get(columnNameKey)) {
						fos.println(tableNameValue);
					}
					fos.println();
				}
			}
		}

		catch (FileNotFoundException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			try {
				if (schema != null) {
					schema.close();
				}

				if (fos != null) {
					fos.close();
				}

			} catch (IOException e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}

	}
	
	/**
	 * Stores Column Names present in different Tables
	 */
	public static void mappingCommonColumnsBetweenTable(String inputFilePath) {
		Iterator<String> keyIter;
		Set<String> keysSet;

		BufferedReader schema = null;
		PrintWriter fos = null;

		HashMap<String, ArrayList<String>> tableColumnMap = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> columnTableMap = new HashMap<String, ArrayList<String>>();

		ArrayList<String> commonArrayList = new ArrayList<String>();
		String lineString = "";

		try {
			schema = new BufferedReader(
					new FileReader(inputFilePath));
			String currentTableName = null;

			/* Scanning the whole file and determining TableColumnMap */
			while ((lineString = schema.readLine()) != null) {

				/*
				 * Checking for table name and whitespace for storing Column
				 * names in ArrayList
				 */
				if (!lineString.matches(".*TBL.*") && !lineString.equals("")) {
					commonArrayList.add(lineString);
					continue;
				}

				/* Checking Table Name for storing table name in HashMap as Key */
				if (lineString.matches(".*TBL.*")) {
					if (currentTableName != null) {
						tableColumnMap.put(currentTableName, commonArrayList);
					}
					currentTableName = lineString;
					commonArrayList = null;
					commonArrayList = new ArrayList<String>();

					continue;
				}
			}
			if (currentTableName != null) {
				tableColumnMap.put(currentTableName, commonArrayList);
			}
			/* Determining ColumnTableMapping from TableColumnMapping */
			keysSet = tableColumnMap.keySet();
			keyIter = keysSet.iterator();
			while (keyIter.hasNext()) {
				String tableNameKey = keyIter.next();

				/* tableColumnMap.get(tableNameKey) is an ArrayList */
				for (String columnName : tableColumnMap.get(tableNameKey)) {
					if (columnTableMap.containsKey(columnName)) {
						commonArrayList = columnTableMap.get(columnName);
						commonArrayList.add(tableNameKey);
						columnTableMap.put(columnName, commonArrayList);
						/*
						 * commonArrayList = null; commonArrayList = new
						 * ArrayList<>();
						 */
					} else {
						/*
						 * commonArrayList.add(tableNameKey);
						 * columnTableMap.put(columnName, commonArrayList);
						 */
						ArrayList<String> c = new ArrayList<String>();
						c.add(tableNameKey);
						columnTableMap.put(columnName, c);
						c = null;
					}
				}
			}

			fos = new PrintWriter(
					new BufferedWriter(
							new FileWriter("D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\WWIL\\ECARE\\UtilityFiles\\CommonColumnNamesBetweenTables.txt")),true);
			/* For displaying on console */
			keysSet = columnTableMap.keySet();
			keyIter = keysSet.iterator();
			while (keyIter.hasNext()) {
				String columnNameKey = keyIter.next();

				/* Column Which are not needed */
				if (columnNameKey.equals("S_CREATED_BY")
						|| columnNameKey.equals("S_LAST_MODIFIED_BY")
						|| columnNameKey.equals("D_CREATED_DATE")
						|| columnNameKey.equals("D_LAST_MODIFIED_DATE")
						|| columnNameKey.equals("D_READING_DATE")
						|| columnNameKey.equals("S_REMARKS")) {
					continue;
				}

				/* Column Present in more than one table */
				if (columnTableMap.get(columnNameKey).size() > 1) {
					fos.println("Column: " + columnNameKey);

					/*
					 * 'columnTableMap.get(columnNameKey)' is an ArrayList
					 * having Table Name
					 */
					for (String tableNameValue : columnTableMap.get(columnNameKey)) {
						fos.println(tableNameValue);
					}
					fos.println();
				}
			}
		}

		catch (FileNotFoundException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			try {
				if (schema != null) {
					schema.close();
				}

				if (fos != null) {
					fos.close();
				}

			} catch (IOException e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}

	}
	
	/**
	 * Stores the All Table Data starting with 'TBL_' whose record count is less than the Specified range into HTML File Format present in a particular Database
	 * Name of the File is the Table Name Itself
	 * @param recordCount:No of Records Present in a table
	 */
	public static void getTableDataWhoseRecordCountIsInGivenRange(int recordCount){
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery1 = "";
		PreparedStatement prepStmt1 = null;
		ResultSet rs1 = null;
		
		try{
			/*File Writer Part*/
			//fos = new PrintWriter(new BufferedWriter(new FileWriter(fileWriterPath)), true);
			
			/*SQL Query Part*/
			conn = conmanager.getConnection();
			sqlQuery = "Select 'SELECT COUNT(*) FROM '||Table_Name,'SELECT * FROM '||Table_Name,TABLE_NAME  From User_Tables  " + 
					"Where Table_Name Like 'TBL_%' " + 
					"ORDER BY TABLE_NAME " ;
			prepStmt = conn.prepareStatement(sqlQuery);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				String countQuery = rs.getString(1);
				String tableQuery = rs.getString(2);
				String tableName = rs.getString(3);
				sqlQuery1 = countQuery;
				prepStmt1 = conn.prepareStatement(sqlQuery1);
				rs1 = prepStmt1.executeQuery();
				while(rs1.next()){
					long count = rs1.getLong(1);
					if(count < recordCount && count > 3){
						System.out.println(countQuery + " : " + tableQuery);
						//System.out.println(count);
						getSQLQueryResultInHTMLFile(tableQuery, tableName);
					}
				}
			}
			
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				
			}
			catch(Exception e){
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
		
	}
	public static StringBuffer que = new StringBuffer();
	
	public static void getSQLQueryResultInExcelFile(String sqlQuery, String filePath) {

		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		PrintWriter fos = null;
		String fileWriterPath = filePath;
		
		StringBuffer content = new StringBuffer(getHeader());
		try {
			fos = new PrintWriter(new BufferedWriter(new FileWriter(fileWriterPath)), true);
			
			conn = WcareConnector.wcareConnector.getConnectionFromPool();
			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();

			String[] columnNames = getColumnNames(rs);
			content.append("\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n "
					+ "\t\t\t<tr>\n");
			for (String columnName : columnNames) {
				content.append("\t\t\t\t<th>" + columnName + "</th>\n");
			}
			content.append("\t\t\t</tr>\n");
			content.append("\t\t\t<tbody id=\"data\">\n");
			while (rs.next()) {
				content.append("\t\t\t\t<tr>\n");
				for (String columnName : columnNames) {
					content.append("\t\t\t\t\t<td>" + rs.getObject(columnName)
							+ "</td>\n");
				}
				content.append("\t\t\t\t</tr>\n");
			}
			content.append("\t\t\t</tbody>\n");
			content.append("\t\t</table>\n");
			content.append("\t</body>\n" + "</html>\n");
			content.append("<!--" + sqlQuery + "-->");
			que.append(filePath + ":"+ sqlQuery + "\n");
			
			fos.println(new String(content));
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			try {
				DaoUtility.releaseResources(prepStmt, rs, conn);
				if (fos != null) {
					fos.close();
				}
				
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	
		
	}
	
	/**
	 * 
	 * @param sqlQuery
	 * @param filePath
	 */
	public static void getSQLQueryResultInHTMLFile(String sqlQuery, String filePath) {

		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		PrintWriter fos = null;
		String fileWriterPath = filePath;
		
		StringBuffer content = new StringBuffer(getHeader());
		try {
			fos = new PrintWriter(new BufferedWriter(new FileWriter(fileWriterPath)), true);
			
			conn = WcareConnector.wcareConnector.getConnectionFromPool();
			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();

			String[] columnNames = getColumnNames(rs);
			content.append("\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n "
					+ "\t\t\t<tr>\n");
			for (String columnName : columnNames) {
				content.append("\t\t\t\t<th>" + columnName + "</th>\n");
			}
			content.append("\t\t\t</tr>\n");
			content.append("\t\t\t<tbody id=\"data\">\n");
			while (rs.next()) {
				content.append("\t\t\t\t<tr>\n");
				for (String columnName : columnNames) {
					content.append("\t\t\t\t\t<td>" + rs.getObject(columnName)
							+ "</td>\n");
				}
				content.append("\t\t\t\t</tr>\n");
			}
			content.append("\t\t\t</tbody>\n");
			content.append("\t\t</table>\n");
			content.append("\t</body>\n" + "</html>\n");
			content.append("<!--" + sqlQuery + "-->");
			que.append(filePath + ":"+ sqlQuery + "\n");
			
			fos.println(new String(content));
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (fos != null) {
					fos.close();
				}
				
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	
		
	}
	
	public static void getSQLQueryResultInHTMLFile(String sqlQuery, String fileName, Object ... obj) {

		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		PrintWriter fos = null;
		String fileWriterPath = "D:\\WCARE\\" + fileName + ".html";
		
		StringBuffer content = new StringBuffer(getHeader());
		try {
			fos = new PrintWriter(new BufferedWriter(new FileWriter(fileWriterPath)), true);
			conn = conmanager.getConnection();
			prepStmt = conn.prepareStatement(sqlQuery);
			
			int i = 0;
			for (Object object : obj) {
				prepStmt.setObject(++i, object);
			}
			
			rs = prepStmt.executeQuery();

			String[] columnNames = getColumnNames(rs);
			content.append("\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n "
					+ "\t\t\t<tr>\n");
			for (String columnName : columnNames) {
				content.append("\t\t\t\t<th>" + columnName + "</th>\n");
			}
			content.append("\t\t\t</tr>\n");
			content.append("\t\t\t<tbody id=\"data\">\n");
			while (rs.next()) {
				content.append("\t\t\t\t<tr>\n");
				for (String columnName : columnNames) {
					content.append("\t\t\t\t\t<td>" + rs.getObject(columnName)
							+ "</td>\n");
				}
				content.append("\t\t\t\t</tr>\n");
			}
			content.append("\t\t\t</tbody>\n");
			content.append("\t\t</table>\n");
			content.append("\t</body>\n" + "</html>\n");
			content.append("<!--" + sqlQuery + ".\nParameter:\n");
			i = 0;
			que.append(fileName + ":"+ sqlQuery + "\n");
			for (Object object : obj) {
				content.append((++i) + "-" +object.toString() + "\n");
				que.append((i) + "-" +object.toString() + "\n");
			}
			content.append("-->");
			fos.println(new String(content));
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getHeader(){
		return "<html>\n "
				+ "	<head>\n "
				+ "        <meta http-equiv=\"content-type\" content=\"text/html; charset=Cp1252\">\n "
				+ "        <!-- base href=\"http://apexdev.us.oracle.com:7778/pls/apx11w/\" -->\n "
				+ "        <style type=\"text/css\">\n "
				+ "            table {\n "
				+ "                background-color:#F2F2F5;\n "
				+ "                border-width:1px 1px 0px 1px;\n "
				+ "                border-color:#C9CBD3;\n "
				+ "                border-style:solid;\n "
				+ "            }\n "
				+ "            td {\n "
				+ "                color:#000000;\n "
				+ "                font-family:Tahoma,Arial,Helvetica,Geneva,sans-serif;\n "
				+ "                font-size:9pt;\n "
				+ "                background-color:#EAEFF5;\n "
				+ "                padding:8px;\n "
				+ "                background-color:#F2F2F5;\n "
				+ "                border-color:#ffffff #ffffff #cccccc #ffffff;\n "
				+ "                border-style:solid solid solid solid;\n "
				+ "                border-width:1px 0px 1px 0px;\n "
				+ "            }\n "
				+ "            th {\n "
				+ "                font-family:Tahoma,Arial,Helvetica,Geneva,sans-serif;\n "
				+ "                font-size:9pt;\n "
				+ "                padding:8px;\n "
				+ "                background-color:#CFE0F1;\n "
				+ "                border-color:#ffffff #ffffff #cccccc #ffffff;\n "
				+ "                border-style:solid solid solid none;\n "
				+ "                border-width:1px 0px 1px 0px;\n "
				+ "                white-space:nowrap;\n "
				+ "            }\n "
				+ "        </style>\n "
				+ "        <script type=\"text/javascript\">\n "
				+ "            window.apex_search = {};\n "
				+ "            apex_search.init = function() {\n "
				+ "                this.rows = document.getElementById('data').getElementsByTagName('TR');\n "
				+ "                this.rows_length = apex_search.rows.length;\n "
				+ "                this.rows_text = [];\n "
				+ "                for (var i = 0; i < apex_search.rows_length; i++) {\n "
				+ "                    this.rows_text[i] = (apex_search.rows[i].innerText) ? apex_search.rows[i].innerText.toUpperCase() : apex_search.rows[i].textContent.toUpperCase();\n "
				+ "                }\n "
				+ "                this.time = false;\n "
				+ "            }\n "
				+ "\n "
				+ "            apex_search.lsearch = function() {\n "
				+ "                this.term = document.getElementById('S').value.toUpperCase();\n "
				+ "                for (var i = 0, row; row = this.rows[i], row_text = this.rows_text[i]; i++) {\n "
				+ "                    row.style.display = ((row_text.indexOf(this.term) != -1) || this.term === '') ? '' : 'none';\n "
				+ "                }\n "
				+ "                this.time = false;\n "
				+ "            }\n "
				+ "\n "
				+ "            apex_search.search = function(e) {\n "
				+ "                var keycode;\n "
				+ "                if (window.event) {\n "
				+ "                    keycode = window.event.keyCode;\n "
				+ "                }\n "
				+ "                else if (e) {\n "
				+ "                    keycode = e.which;\n "
				+ "                }\n "
				+ "                else {\n "
				+ "                    return false;\n "
				+ "                }\n "
				+ "                if (keycode == 13) {\n "
				+ "                    apex_search.lsearch();\n "
				+ "                }\n "
				+ "                else {\n "
				+ "                    return false;\n "
				+ "                }\n "
				+ "            }</script>\n "
				+ "    </head>\n "
				+ "	<body onload=\"apex_search.init();\">\n "
				+ "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n "
				+ "            <tbody>\n "
				+ "				<tr>\n "
				+ "					<td>\n "
				+ "						<input type=\"text\" size=\"30\" maxlength=\"1000\" value=\"\" id=\"S\" onkeyup=\"apex_search.search(event);\" />\n "
				+ "						<input type=\"button\" value=\"Search\" onclick=\"apex_search.lsearch();\"/> \n "
				+ "                    </td>\n " + "				</tr>\n "
				+ "            </tbody>\n " + "		</table>\n "
				+ "        <br>\n ";
	}
	
	/**
	 * Stores the source code of all the views present in a particular Database
	 * in a file named same as that of the view name
	 */
	public static void getUserViewSourceCodeInFile() {
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		BufferedReader fileReader = null;
		PrintWriter fos = null;
		String lineString = "";
		String fileReaderPath = "";
		String fileWriterPath = "";
		try {
			conn = conmanager.getConnection();
			sqlQuery = "Select View_Name,Text From User_Views";
			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				String viewName = rs.getString(1);
				String soureCode = rs.getString(2);

				/*Creating File With View Name*/
				fileWriterPath = "D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\WWIL\\ECARE\\DataBase\\Views\\"
						+ viewName + ".sql";

				fos = new PrintWriter(new BufferedWriter(new FileWriter(
						fileWriterPath)), true);
				
				/*Storing Source Code in the file*/
				fos.println(soureCode);
				/*fileReader = new BufferedReader(new FileReader(fileReaderPath));
				while ((lineString = fileReader.readLine()) != null) {
					if (lineString.length() == 0) {
						continue;
					}
					fos.println(lineString);
				}*/
			}
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (fileReader != null) {
					fileReader.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	}
	
	/**
	 *Transfer Table Data Present in one Database into Other Database
	 *Pass the Connection of the two database Involved 
	 */
	public static void transferTableData(){

		JDBCUtils n  = new JDBCUtils();
		JDBCUtilsTest conmanager = new JDBCUtilsTest();
		Connection myConnection = null;
		Connection wcareConnection = null;
		
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String sqlQuery1 = "";
		PreparedStatement prepStmt1 = null;
			
		try{
			myConnection = conmanager.getTestingConnection();
			wcareConnection = n.getConnection();
			sqlQuery = "Select * From Tbl_Wec_Reading Where S_Customer_Id = '1000000736' And Extract(Month From D_Reading_Date) = 12 And Extract(year From D_Reading_Date) = 2012";
			prepStmt = wcareConnection.prepareStatement(sqlQuery);
				
			rs = prepStmt.executeQuery();
			String[] columnNames = getColumnNames(rs);
			StringBuffer query = new StringBuffer();
			StringBuffer questionMarks = new StringBuffer();
			
			query.append("(");
			questionMarks.append("(");
			boolean firstColumn = true;
			for (String columnName : columnNames) {
				if(firstColumn){
					query.append(columnName);
					questionMarks.append("?");
					firstColumn = false;
				}
				else{
					query.append("," + columnName);
					questionMarks.append(",?");
				}
			}
			query.append(")");
			questionMarks.append(")");
			int i = 0;
			while (rs.next()) {
				sqlQuery1 = "insert into Tbl_Wec_Reading"+query+" values "+questionMarks;
				prepStmt1 = myConnection.prepareStatement(sqlQuery1);
				for (String columnName : columnNames) {
					prepStmt1.setObject(++i, rs.getObject(columnName));
				}
				
				prepStmt1.executeUpdate();
				
				prepStmt1.close();
				i = 0;	
			}
			if(rs != null){
				rs.close();
			}
			//return null;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			try{
				if(myConnection != null){
					myConnection.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}

	}
	
	public static void insertTableData(String[] dates, int[] values,
			String wecId) {
		JDBCUtilsTest conmanager = new JDBCUtilsTest();
		Connection myConnection = null;
		Connection wcareConnection = null;
		
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String sqlQuery1 = "";
		PreparedStatement prepStmt1 = null;
			
		try{
			wcareConnection = conmanager.getWcareConnection();
			for(int i = 0 ; i < 5; i++){
				System.out.println("Executed");
				sqlQuery = "Update Tbl_Wec_Reading Set N_Actual_Value = ? Where S_Wec_Id = ? And D_Reading_Date = ? and S_mp_id = '0808000023'";
				prepStmt = wcareConnection.prepareStatement(sqlQuery);
				prepStmt.setObject(1,values[i]);
				prepStmt.setObject(2,wecId);
				prepStmt.setObject(3,dates[i]);
				rs = prepStmt.executeQuery();
			}
			
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			try{
				if(myConnection != null){
					myConnection.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	}
	
	/**
	 * 
	 * @param rs
	 * @return The names of column Names present in a ResultSet
	 */
	private static String[] getColumnNames(ResultSet rs) {
		ResultSetMetaData metaData;

		int noOfColumn = 0;
		String[] columnNames = null;
		try {
			metaData = rs.getMetaData();
			noOfColumn = metaData.getColumnCount();
			columnNames = new String[noOfColumn];
			for (int i = 1; i <= noOfColumn; i++) {
				columnNames[i - 1] = metaData.getColumnName(i);
			}
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return columnNames;
	}



	
}

