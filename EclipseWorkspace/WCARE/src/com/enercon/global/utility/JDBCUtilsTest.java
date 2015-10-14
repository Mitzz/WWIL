package com.enercon.global.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtilsTest {
	private Connection testingConnection;
	private Connection scadaConnection;
	private Connection wcareConnection;
	
	public JDBCUtilsTest() {
		
		String serverName = "";
        String portNumber = "";
        String sid = "";
        String url = "";
        String username = "";
        String password = "";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			/*serverName = "localhost";
	        portNumber = "1521";
	        sid = "mit";
	        url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
	        username = "mithul";
	        password = "mit";
	        
	        testingConnection = DriverManager.getConnection(url, username, password);*/
			
			serverName = "localhost";
	        portNumber = "1521";
	        sid = "XE";
	        url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
	        username = "system";
	        password = "root";
	        
	        testingConnection = DriverManager.getConnection(url, username, password);
			
			serverName = "172.18.16.160";
	        portNumber = "1521";
	        sid = "ecare";
	        url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
	        username = "scadadw";
	        password = "scadadw123";
	        
	        scadaConnection = DriverManager.getConnection(url, username, password);
			
			serverName = "172.18.16.28";
	        portNumber = "1521";
	        sid = "oecare";
	        url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
	        username = "ecare";
	        password = "customer2011";
	        
	        wcareConnection = DriverManager.getConnection(url, username, password);
	        
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getTestingConnection() {
		return testingConnection;
	}
	
	public Connection getScadaConnection() {
		return scadaConnection;
	}
	
	public Connection getWcareConnection() {
		return wcareConnection;
	}
	
}
