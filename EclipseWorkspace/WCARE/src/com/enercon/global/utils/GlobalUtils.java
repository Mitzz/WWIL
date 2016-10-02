package com.enercon.global.utils;

import static com.enercon.connection.WcareConnector.wcareConnector;

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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.enercon.admin.bean.CreateRoleBean;
import com.enercon.admin.bean.RoleMappingBean;
import com.enercon.dao.DaoUtility;

public class GlobalUtils {
	
	private static final Logger logger = Logger.getLogger(GlobalUtils.class);

	/* For getting & setting the request parameter */

	public static DynaBean getDynaBean(HttpServletRequest request) {
		/* Map requestParams = request.getParameterMap(); */
		// logger.trace("inside dynabean");
		Enumeration enumparameter = request.getParameterNames();
		if (enumparameter.hasMoreElements()) {
			// Enumeration enumparameter = request.getAttributeNames();
			DynaBean dynaBean = new DynaBean();
			while (enumparameter.hasMoreElements()) {
				String sKey = enumparameter.nextElement().toString();
				StringBuffer text = new StringBuffer(request.getParameter(sKey));
				int loc = (new String(text)).indexOf('\n');
				while (loc > 0) {
					text.replace(loc, loc + 1, "<BR>");
					loc = (new String(text)).indexOf('\n');
				}
				String sValue = text.toString(); // request.getParameter(sKey);
				dynaBean.setProperty(sKey, sValue);
			}
			return dynaBean;
		}
		return null;
	}

	public static DynaBean getAttDynaBean(HttpServletRequest request) {
		/* Map requestParams = request.getParameterMap(); */
		// logger.trace("inside dynabean");
		Enumeration enumparameter = request.getAttributeNames();
		DynaBean dynaBean = new DynaBean();
		while (enumparameter.hasMoreElements()) {
			String sKey = enumparameter.nextElement().toString();
			String sValue = request.getAttribute(sKey).toString();
			dynaBean.setProperty(sKey, sValue);
		}
		return dynaBean;
	}

	public Hashtable getAllRoles() throws Exception {
		// logger.trace("Enter in to the getAllRoles GlobalUtil");
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = GlobalSQLC.GET_ALL_ROLES;
		Hashtable retHash = new Hashtable();
		try {
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				CreateRoleBean createRoleBean = new CreateRoleBean();
				createRoleBean.setsRoleId(String.valueOf(rs.getString(1)));
				createRoleBean.setSRoleName(rs.getString(2));
				retHash.put(createRoleBean.getsRoleId(),
						createRoleBean.getSRoleName());
			}
			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return retHash;
	}

	/*--------Abhishek --------------*/

	public static String trimAllSpace(String str) {
		String str1 = "";
		int i = 0;
		while (i != str.length()) {
			if (str.charAt(i) != ' ')
				str1 = str1 + str.charAt(i);
			i++;
		}
		return str1;
	}

	public static String trimString(String str) {
		Diff df = new Diff();
		String str1 = "";
		int i = 0;
		while (i != str.length()) {
			if (str.charAt(i) != ' ')
				str1 = str1 + str.charAt(i);
			i++;
		}
		boolean retval = df.isNumber(str1);
		if (retval == false)
			return "-100";
		else
			return str1;
	}

	/*--------Abhishek --------------*/
	/**
	 * 
	 * @throws java.lang.Exception
	 * @return
	 * 
	 */
	public Hashtable getAllUser() throws Exception {
		// logger.trace("Enter In to the getAllUser In Global");
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = GlobalSQLC.GET_ALL_USER;
		Hashtable retHash = new Hashtable();
		try {
			// logger.trace("Enter In to the getAllUser");
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				RoleMappingBean roleMappingBean = new RoleMappingBean();
				roleMappingBean.setsEmployeeId(String.valueOf(rs.getString(1)));
				roleMappingBean.setsUserName(rs.getString(2));
				retHash.put(roleMappingBean.getsEmployeeId(),
						roleMappingBean.getsUserName());
			}
			prepStmt.close();
			rs.close();
			// logger.trace("After Executing Query");
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		// logger.trace("Hastable is :Debugged : Global Util "+retHash);
		return retHash;
	}

	/********* Mithul ************/
	public static int[] minuteToHour(int minutes) {
		if(minutes == 0){
			return new int[]{0,0};
		}
		int[] hourMinute = new int[2];
		int hourPresentInMinute = minutes / 60;
		int minutesLeft = minutes % 60;
		hourMinute[0] = hourPresentInMinute;
		hourMinute[1] = minutesLeft;
		return hourMinute;
	}

	/********* Mithul ************/
	
	
	public static void displayVectorMember(List list) {
		int memberCount = 0;
		for (Object object : list) {
			logger.debug((memberCount++) + ":" + object);
		}
	}
	
	public static String todaysDateInGivenFormat(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}
	public static void displayRequestParameter(HttpServletRequest request){
		 Enumeration en = request.getParameterNames();
	        
	        while (en.hasMoreElements()) {
	            
	            String paramName = (String) en.nextElement();
	            logger.trace(paramName + " = " + request.getParameter(paramName));
	            
	        }
	}
	
	/*TIme in HH:MM or HH.MM format converted to totalMinutes*/
	public static int hourMinuteStringFormatToTotalMinutes(String str,String delimiter){
		int totalTimeInMinutes = 0;
		if(str.equals("")){
			return 0;
		}
		if (str.contains(delimiter)) {
			String[] timeStringSplitted = new String[2];
			if(delimiter.equalsIgnoreCase(".")){
				timeStringSplitted = str.split("\\.");
			}
			else{
				timeStringSplitted = str.split(delimiter);
			}
			//logger.trace("0:" + timeStringSplitted[0] + ",[1]:" + timeStringSplitted[1]);
			int timeStringMinuteValue = Integer.parseInt(timeStringSplitted[1]);
			int timeStringHourValue   = Integer.parseInt(timeStringSplitted[0]);
			totalTimeInMinutes = timeStringMinuteValue + (timeStringHourValue * 60);
		} else {
			totalTimeInMinutes = Integer.parseInt(str) * 60;
		}
		return totalTimeInMinutes;
	}
	
	/**
	 *  0th Element has Hour Value
	 *  1st Element has Minute Value
	 */
	public static String hourMinuteToStringHH_MMFormat(int[] s, String delimiter){
		StringBuffer hourMinute = new StringBuffer();
		if(s[0] <= 9){
			hourMinute.append("0" + s[0] + delimiter);
		}
		else{
			hourMinute.append(s[0] + "" + delimiter);
		}
		
		if(s[1] <= 9 ){
			hourMinute.append("0" + s[1]);
		}
		else{
			hourMinute.append(s[1]);
		}
		//logger.trace(hourMinute);
		
		return new String(hourMinute);
	}
	
	public static int getMinutes(String minute){
		int mm = 0;
		int hh = 0;
		if(minute.contains(":")){
			String[] split = minute.split(":");
			if(split[1].length() == 2){
				mm = Integer.parseInt(split[1]);
			}
			else{
				mm = Integer.parseInt(split[1]) * 10;
			}
			hh = Integer.parseInt(split[0]) * 60;
			//logger.trace("sjdlsdkfjd: " + hh + mm);
			return (hh + mm);
		}
		else{
			return Integer.parseInt(minute) * 60;
		}
		
	}
	
	public static int getMinutesModified(String minute){
		try{
		int mm = 0;
		int hh = 0;
		//logger.trace("GetMinutesModified:" + minute);
		if(!minute.equalsIgnoreCase("")){
			if(minute.contains(".")){
				String[] split = minute.split("\\.");
				if(split[1].length() == 2){
					mm = (int) Double.parseDouble(split[1]);
				}
				else if(split[1].length() == 1){
					mm = (int) Double.parseDouble(split[1]) * 10;
				}
				else{
					//logger.trace("sdddddddddddddddddddddddddddddddddddddd");
					mm = 0 ;
				}
				if(split[0].length() == 0){
					//logger.trace("sdddddddddddddddddddddddddddddddddddddd");
					hh = 0;
				}
				else{
					hh = (int) Double.parseDouble(split[0]) * 60;
				}
				//logger.trace("sjdlsdkfjd: " + hh + mm);
				return hh + mm;
			}
			else{
				return Integer.parseInt(minute) * 60;
			}
		}
		else{
			return 0;
		}
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			logger.trace("getMinutesModified Exception");
		}
		return 0;
	}
	
	public static void displaySessionAttribute(HttpSession session){
		logger.trace("------Session Attributes and their value-----");
		Enumeration<String> attributeNames = session.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
		    String name = attributeNames.nextElement();
		    Object value = session.getAttribute(name);
		    logger.trace(name + "=" + value);
		}
		logger.trace("------End-----");
	}

	public static void getSQLQueryResultInHTMLFile() {

	//	JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		BufferedReader fileReader = null;
		PrintWriter fos = null;
		String lineString = "";
		String fileWriterPath = "C:\\Users\\HP\\Desktop\\TBL_ROLE.htm";
		StringBuffer content = new StringBuffer(
				"<html>\n "
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
						+ "        <br>\n ");
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
			//logger.trace(e.getMessage());
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
				//logger.trace(e.getMessage());
			}
		}
	}

	public static String[] getColumnNames(ResultSet rs) {
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
	
	

	public static void getJavaStringBasedOnQuery() {
		BufferedReader fileReader = null;
		PrintWriter fos = null;
		String lineString = "";
		String fileReaderPath = "D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\Java\\JavaGenericEclipseWorkspace\\Utility\\src\\files\\StoringSQLQuery.txt";
		String fileWriterPath = "D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\Java\\JavaGenericEclipseWorkspace\\Utility\\src\\files\\StoredJavaStringFromSQLQuery.txt";
		try {
			fos = new PrintWriter(new BufferedWriter(new FileWriter(
					fileWriterPath)), true);
			fileReader = new BufferedReader(new FileReader(fileReaderPath));
			while ((lineString = fileReader.readLine()) != null) {
				lineString = getStringWithDoubleQuotesOnBothSideWithPlusAtTheEnd(lineString);
				fos.println(lineString);
			}
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			//logger.trace(e.getMessage());
		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
				//logger.trace(e.getMessage());
			}
		}
	}

	public static String getStringWithDoubleQuotesOnBothSideWithPlusAtTheEnd(
			String lineString) {
		StringBuffer newLineString = new StringBuffer();
		newLineString.append("\"");
		newLineString.append(lineString);
		newLineString.append(" \" + ");
		return new String(newLineString);
	}

	private static void calculateFrequencyOfCharactersInAString(String str) {
		int length = str.length();
		String characters[] = new String[length];// to store all unique
													// characters in string
		int frequency[] = new int[length];// to store the frequency of the
											// characters
		for (int i = 0; i < length; i++) {
			characters[i] = null;
			frequency[i] = 0;
		}

		// To get unique characters
		char temp;
		String temporary;
		int uniqueCount = 0;
		for (int i = 0; i < length; i++) {
			int flag = 0;
			temp = str.charAt(i);
			temporary = "" + temp;
			for (int j = 0; j < length; j++) {
				if (characters[j] != null && characters[j].equals(temporary)) {
					flag = 1;
					break;
				}
			}
			if (flag == 0) {
				characters[uniqueCount] = temporary;
				uniqueCount++;
			}
		}

		// To get the frequency of the characters
		for (int i = 0; i < length; i++) {
			temp = str.charAt(i);
			temporary = "" + temp;
			for (int j = 0; i < characters.length; j++) {
				if (characters[j].equals(temporary)) {
					frequency[j]++;
					break;
				}
			}
		}

		// To display the output
		for (int i = 0; i < length; i++) {
			if (characters[i] != null) {
				logger.trace(characters[i] + " " + frequency[i]);
			}
		}
	}

	public static String yesterdayDateStringInGivenFormat(String format)
			throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}

	public static String previousDateString(String dateString, String format)
			throws ParseException {
		// Create a date formatter using your format string
		DateFormat dateFormat = new SimpleDateFormat(format);

		// Parse the given date string into a Date object.
		// Note: This can throw a ParseException.
		Date myDate = dateFormat.parse(dateString);

		// Use the Calendar class to subtract one day
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(myDate);
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		// Use the date formatter to produce a formatted date string
		Date previousDate = calendar.getTime();
		String result = dateFormat.format(previousDate);

		return result;
	}

	public static void getUserViewSourceCodeInFile() {
		//JDBCUtils conmanager = new JDBCUtils();
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
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Select View_Name,Text From User_Views";
			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				logger.trace("VIEW_NAME:" + rs.getObject(1));
				logger.trace("---");
				logger.trace("Text:" + rs.getObject(2));
				logger.trace("----------------------------------------------------");

				fileWriterPath = "D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\WWIL\\ECARE\\Views\\"
						+ rs.getObject(1) + ".sql";

				fos = new PrintWriter(new BufferedWriter(new FileWriter(
						fileWriterPath)), true);
				// fileReader = new BufferedReader(new
				// FileReader(fileReaderPath));
				while ((lineString = fileReader.readLine()) != null) {
					if (lineString.length() == 0) {
						continue;
					}
					fos.println(lineString);
				}
				fos.println(rs.getObject(2));

			}
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			//logger.trace(e.getMessage());
		} finally {
			try {
				DaoUtility.releaseResources(prepStmt, rs, conn);
				if (fileReader != null) {
					fileReader.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
				//logger.trace(e.getMessage());
			}
		}
	}

	public static void storingTableStartingWithTBLAlongwithItsColumnNames() {
		//JDBCUtils conmanager = new JDBCUtils();
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

			conn = wcareConnector.getConnectionFromPool();
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
			//logger.trace(e.getMessage());
		} finally {
			try {
				DaoUtility.releaseResources(prepStmt, rs, conn);
				if (fileReader != null) {
					fileReader.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
				//logger.trace(e.getMessage());
			}
		}
		// return null;
	}

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
							new FileWriter(
									"D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\WWIL\\ECARE\\UtilityFiles\\CommonColumnNamesBetweenTables.txt")),
					true);
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

	public static void characterCountInString() {
		String input = "aaaabbaaDD";
		int count = 0;
		char lastChar = 0;
		int inputSize = input.length();
		String output = "";
		for (int i = 0; i < inputSize; i++) {
			if (i == 0) {
				lastChar = input.charAt(i);
				count++;
			} else {
				if (lastChar == input.charAt(i)) {
					count++;
				} else {
					output = output + count + "" + lastChar;
					count = 1;
					lastChar = input.charAt(i);
				}
			}
		}
		output = output + count + "" + lastChar;
		logger.trace(output);
	}

	public static void seqNoGenerated() {
		String s = Integer.toString(2);
		String sCode = "1305";
		if (s.length() < 6) { // pad on left with zeros
			s = "000000".substring(0, 6 - s.length()) + s;
		}
		String seqNo = sCode + s;
		logger.trace(seqNo);
	}
	
	public static void getTableDataWhoseRecordCountIsInGivenRange(int recordCount){
		//JDBCUtils conmanager = new JDBCUtils();
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
			conn = wcareConnector.getConnectionFromPool();
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
						logger.trace(countQuery + " : " + tableQuery);
						//logger.trace(count);
						getSQLQueryResultInHTMLFile(tableQuery, tableName);
					}
				}
			}
			
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			//logger.trace(e.getMessage());
		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(prepStmt,prepStmt1) , Arrays.asList(rs,rs1) , conn);
		}
		
	}
	
	public static void getSQLQueryResultInHTMLFile(String sqlQuery, String fileName) {

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		PrintWriter fos = null;
		String fileWriterPath = "D:\\Mithul\\Dropbox\\Photos\\Mitz\\Languages\\" + fileName + ".html";
		
		StringBuffer content = new StringBuffer(getHeader());
		try {
			fos = new PrintWriter(new BufferedWriter(new FileWriter(
					fileWriterPath)), true);
			conn = wcareConnector.getConnectionFromPool();
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
			//logger.trace(e.getMessage());
		} finally {
			try {
				DaoUtility.releaseResources(prepStmt, rs, conn);
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
				//logger.trace(e.getMessage());
			}
		}
	
		
	}
	
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
	/*
	 * public static HashMap<String, ArrayList<Object>>
	 * getColumnNameWithRowValueMapping(ResultSet resultSet, String[]
	 * columnNames){ HashMap<String, ArrayList<Object>>
	 * columnNameWithRowValuesMapping = new HashMap<String,
	 * ArrayList<Object>>(); try{ for (String columnName : columnNames) {
	 * if(columnNameWithRowValuesMapping.containsKey(columnName)){
	 * ArrayList<Object> existingColValues =
	 * columnNameWithRowValuesMapping.get(columnName);
	 * existingColValues.add(resultSet.getObject(columnName)); } else{
	 * ArrayList<Object> colValue = new ArrayList<Object>();
	 * colValue.add(resultSet.getObject(columnName));
	 * columnNameWithRowValuesMapping.put(columnName, colValue); } } return
	 * columnNameWithRowValuesMapping; } catch(Exception e){
	 * logger.trace(e.getMessage()); } return
	 * columnNameWithRowValuesMapping; }
	 */

	public static String getStringFromArrayForQuery(Collection<String> listItems) {
		StringBuffer listItemStringFormat = new StringBuffer();
		boolean firstRecord = true;
		listItemStringFormat.append("(");
		for(String ebId : listItems) {
			if(firstRecord){
				listItemStringFormat.append("'" + ebId + "'");
				firstRecord = false;
			}else{
				listItemStringFormat.append(",'" + ebId + "'");
			}
		}
		
		listItemStringFormat.append(") ");
		return new String(listItemStringFormat);
	}
	
	public static List<List<String>> splitArrayList(List<String> originalList, int partitionSize){
		List<List<String>> partitions = new LinkedList<List<String>>();
		for (int i = 0; i < originalList.size(); i += partitionSize) {
		    partitions.add(originalList.subList(i,i + Math.min(partitionSize, originalList.size() - i)));
		}
		return partitions;
		
	}
	
	public static List<List<String>> splitArrayList(Set<String> original, int partitionSize){
		List<String> originalList = new ArrayList<String>(original);
		List<List<String>> partitionsList = new LinkedList<List<String>>();
		for (int i = 0; i < original.size(); i += partitionSize) {
		    partitionsList.add(originalList.subList(i,i + Math.min(partitionSize, original.size() - i)));
		}
		return partitionsList;
		
		
	}
	
	public static void main(String[] args) {
		
		List<Integer> list1 = Arrays.asList(1,2,3,4,5);
		List<Integer> list2 = Arrays.asList(1,2,3,5);
		
		logger.trace(intersection(list1, list2));
		
	}

	public static <T> Set<T> intersection(Collection<T> col1, Collection<T> col2) {
		Set<T> s = new HashSet<T>();
		for (T elem : col1) {
			if(col2.contains(elem)) s.add(elem);
		}
		return s;
	}
	
	public static <K, V> Map<K, V> sortByValue(Map<K, V> map, Comparator<Map.Entry<K, V>> c) {
		Map<K, V> result =   new LinkedHashMap<K, V>();
		Set<Entry<K, V>> set = map.entrySet();
        List<Entry<K, V>> list = new ArrayList<Entry<K, V>>(set);
        Collections.sort( list, c);
        
        for(Map.Entry<K, V> entry:list){
        	result.put(entry.getKey(), entry.getValue());
        }
        
        return result;
	}
	
}
