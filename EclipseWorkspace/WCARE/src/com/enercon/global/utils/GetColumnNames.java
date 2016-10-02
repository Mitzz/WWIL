package com.enercon.global.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class GetColumnNames {
	private final static Logger logger = Logger.getLogger(GetColumnNames.class);
	public static String[] getColumnNames(ResultSet resultSet){
		ResultSetMetaData metaData;
		String[] columnNames = null;
		try {
			metaData = resultSet.getMetaData();
			int noOfColumn = metaData.getColumnCount();
			columnNames = new String[noOfColumn];
	        for (int j = 1; j <= noOfColumn; j++){
	        	columnNames[j-1] = metaData.getColumnName(j);
	        }
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return columnNames;
	}
}
