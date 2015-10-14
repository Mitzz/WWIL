package com.enercon.global.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class GetColumnNames {
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
			System.out.println(e.getMessage());
		}
		return columnNames;
	}
}
