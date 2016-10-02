package com.enercon.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.StringUtility;

//Uncomment for Production
public class DaoUtility implements WcareConnector{
	private final static Logger logger = Logger.getLogger(DaoUtility.class);
	
	public static void releaseResources(PreparedStatement prepStmt, ResultSet rs, Connection con){
		try{
//			releaseResources(Arrays.asList(prepStmt), Arrays.asList(rs), con);
			if(prepStmt != null) prepStmt.close();
			if(rs != null) rs.close();
			wcareConnector.returnConnectionToPool(con);
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
	
	public static void releaseResources(PreparedStatement prepStmt, ResultSet rs){
		try{
			if(prepStmt != null) prepStmt.close();
			if(rs != null) rs.close();
			
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
	
	public static void releaseResources(List<PreparedStatement> preparedStatments, List<ResultSet> resultSets, Connection con){
		try{
			for(PreparedStatement prepStmt: preparedStatments)
				if(prepStmt != null) prepStmt.close();
			
			for(ResultSet rs: resultSets)
				if(rs != null) rs.close();
				
			wcareConnector.returnConnectionToPool(con);
			
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
	
	public static void releaseResources(PreparedStatement prepStmt, Connection con){
		releaseResources(prepStmt, null, con);
	}
	
	public static void releaseResources(List<PreparedStatement> preparedStatments, Connection con){
		releaseResources(preparedStatments, null, con);
	}
	
	public static void displayQueryWithParameter(String query, Object ... parameters){
		
	}
	
	public static void displayQueryWithParameter(Integer queryId, String query, Object ... parameters){
		
	}
	
	public static int getRowCount(Integer queryId, ResultSet rs) {
			
			return 0;
			
	}

	public static void releaseResources(List<PreparedStatement> preparedStatments, List<ResultSet> resultSets) {
		try{
			for(PreparedStatement prepStmt: preparedStatments)
				if(prepStmt != null) prepStmt.close();
			
			for(ResultSet rs: resultSets)
				if(rs != null) rs.close();
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	public static void closeWriter() {
		
	}
}

//Uncomment for Local
//public class DaoUtility implements WcareConnector{
//	private final static Logger logger = Logger.getLogger(DaoUtility.class);
//	public static int queryId = 0;
//	public static PrintWriter writer = null;
//	static {
//		try {
//			writer = new PrintWriter(new BufferedWriter(new FileWriter("E:\\WWIL\\Query\\query.txt")), true);
//		} catch (IOException e) {
//			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//		}
//		writer.append("Something");
//	}
//
//	public static void releaseResources(PreparedStatement prepStmt, ResultSet rs, Connection con){
//		try{
////			releaseResources(Arrays.asList(prepStmt), Arrays.asList(rs), con);
//			if(prepStmt != null) prepStmt.close();
//			if(rs != null) rs.close();
//			wcareConnector.returnConnectionToPool(con);
//		}
//		catch(Exception e){
//			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//		}
//	}
//	
//	public static void releaseResources(PreparedStatement prepStmt, ResultSet rs){
//		try{
//			if(prepStmt != null) prepStmt.close();
//			if(rs != null) rs.close();
//			
//		}
//		catch(Exception e){
//			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//		}
//	}
//	
//	public static void releaseResources(List<PreparedStatement> preparedStatments, List<ResultSet> resultSets, Connection con){
//		try{
//			for(PreparedStatement prepStmt: preparedStatments)
//				if(prepStmt != null) prepStmt.close();
//			
//			for(ResultSet rs: resultSets)
//				if(rs != null) rs.close();
//				
//			wcareConnector.returnConnectionToPool(con);
//			
//		}
//		catch(Exception e){
//			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//		}
//	}
//	
//	public static void releaseResources(PreparedStatement prepStmt, Connection con){
//		releaseResources(prepStmt, null, con);
//	}
//	
//	public static void releaseResources(List<PreparedStatement> preparedStatments, Connection con){
//		releaseResources(preparedStatments, null, con);
//	}
//	
//	public static void displayQueryWithParameter(String query, Object ... parameters){
//		query = getFormatted(query, 3);
//		query = query.toUpperCase().replace("FROM ", "\nFROM ")
//				.replace("AND ", "\nAND ")
//				.replace("WHERE ", "\nWHERE ")
//				.replace("GROUP ", "\nGROUP ")
//				.replace("ORDER BY ", "\nORDER BY ");
//		if(parameters == null) {
//			query = "\n----------\n" + query + ";\n------------\n";
////			logger.info(query);
//			writer.append(query);
//			writer.flush();
//			return;
//		}
//		query = query.replace("?", "''");
//		for(Object parameter: parameters){
//			query = query.replaceFirst("''", ((parameter == null) ? "null" : "'" + parameter.toString() + "'"));
////			logger.info((++count) + ":" + parameter.toString());
//		}
//		query = "\n----------\n" + query + ";\n------------\n";
////		logger.info(query);
//		writer.append(query);
//		writer.flush();
//	}
//	
//	public static void displayQueryWithParameter(Integer queryId, String query, Object ... parameters){
//		displayQueryWithParameter("--" + queryId + "\n" + query, parameters);
//	}
//	
//	public static int getRowCount(Integer queryId, ResultSet rs) {
//			try {
//				Integer rowCount = rs.getRow();
//				writer.append(String.format("--Row Count of Id %s: %s\n" ,queryId, rowCount.toString()));
//				writer.flush();
//				return rowCount;
////				return rs.isAfterLast() ? rs.getRow() : 0;
//			} catch (SQLException e) {
//				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//			}
//			return 0;
//			
//	}
//
//	private static String getFormatted(String query, int i) {
//		String line = query.toUpperCase();
//        String b = line.substring(0, line.indexOf("FROM"));
//        
//        final int OFFSET = i; 
//        int occurence = OFFSET;
//        int count = StringUtility.countCharacter(b, ",");
//        int n = count / OFFSET;
//        
//        while(n > 0){
//        	line = StringUtility.includeAt(line, StringUtility.ordinalIndexOf(line, ',', occurence), ",\n");
//        	occurence = occurence + OFFSET;
//        	n--;
//        }
//        
//		return line;
//	}
//
//	public static void releaseResources(List<PreparedStatement> preparedStatments, List<ResultSet> resultSets) {
//		try{
//			for(PreparedStatement prepStmt: preparedStatments)
//				if(prepStmt != null) prepStmt.close();
//			
//			for(ResultSet rs: resultSets)
//				if(rs != null) rs.close();
//		}
//		catch(Exception e){
//			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//		}
//	}
//
//	public static void closeWriter() {
//		writer.close();
//	}
//}

/*
DaoUtility.releaseResources(prepStmt, rs, conn);
logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
logger.error\(\"\\nClass: \" \+ e.getClass\(\) \+ \"\\nMessage: \" \+ e.getMessage\(\) \+ \"\\n\", e\);
logger.error("\nClass: " + exp.getClass() + "\nMessage: " + exp.getMessage() + "\n", exp);
*/