package com.enercon.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.enercon.global.utility.FilePathUtility;

//Uncomment for Production
//public class WcareConnectorManager implements DatabaseConnector{
//	private final static Logger logger = Logger.getLogger(WcareConnectorManager.class);
//	private static WcareConnectorManager scManager;
//	
//	private WcareDataSourceConnector connector;
//	
//	private WcareConnectorManager(){
//		connector = WcareDataSourceConnector.getInstance();
//	}
//	
//	public synchronized Connection getConnectionFromPool(){
//		Connection connection = null;
//		try {
//			 connection = connector.getConnection();
//		} catch (SQLException e) {
//			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//		}
//		return connection;
//	}
//
//	public synchronized void returnConnectionToPool(Connection connection){
//		try {
//			connection.close();
//		} catch (SQLException e) {
//			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//		}
//	}
//
//	public void shutDown() {
//		connector.shutdown();
//	}
//	
//	public synchronized static WcareConnectorManager getInstance(){
//		
//		if (scManager == null){
//			scManager = new WcareConnectorManager();
//		}
//		return scManager;
//	}
//	
//}

//

//Uncomment for Local
public class WcareConnectorManager implements DatabaseConnector{
	private final static Logger logger = Logger.getLogger(WcareConnectorManager.class);
	private static WcareConnectorManager scManager;
	
	final private static int MAX_POOL_SIZE = 2;
	
	private static int count = MAX_POOL_SIZE; 

	private static String databaseUrl;
	private static String userName;
	private static String password;

	private static Vector<Connection> connectionPool = new Vector<Connection>();
	
	private WcareConnectorManager(){
		initializeForDev();
	}
	
	private static void initializeForDev() {
		databaseUrl = "jdbc:oracle:thin:@172.18.16.108:1521:wcare";
		userName = "ecare";
		password = "customer2011";
		try {
			
			initializeConnectionPool();
			
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	public static synchronized WcareConnectorManager getInstance(){
		
		if (scManager == null){
			scManager = new WcareConnectorManager();
		}
		return scManager;
	}
	private static void initialize(){
		Properties p1 = new Properties();
		try {
			
			InputStream inputStream = 
				    new FileInputStream(new File(FilePathUtility.getInstance().getPath("configuration\\database\\WcareDB.properties")));
			p1.load(inputStream);
			
			databaseUrl = p1.getProperty("url");
			userName = p1.getProperty("username");
			password = p1.getProperty("password");
			
			inputStream.close();
			initializeConnectionPool();
			
			//System.out.println("Connection Pool : " + connectionPool);
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	private static void initializeConnectionPool(){
		while(!checkIfConnectionPoolIsFull()){
			logger.warn("Connection Pool is NOT full. Proceeding with adding new connections");
			//Adding new connection instance until the pool is full
			connectionPool.addElement(createNewConnectionForPool());
//			System.out.println("After Adding Size: " + connectionPool.size());
		}
		System.out.println("Connection Pool is full.");
	}

	private synchronized static boolean checkIfConnectionPoolIsFull(){
		
		if(connectionPool.size() <= MAX_POOL_SIZE){
			return false;
		}

		return true;
	}

	//Creating a connection
	private static Connection createNewConnectionForPool(){
		Connection connection = null;

		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(databaseUrl, userName, password);
//			System.out.println("Connection: "+connection);
		}
		catch(SQLException sqle){
			logger.error("\nClass: " + sqle.getClass() + "\nMessage: " + sqle.getMessage() + "\n", sqle);
			return null;
		}
		catch(ClassNotFoundException cnfe){
			logger.error("\nClass: " + cnfe.getClass() + "\nMessage: " + cnfe.getMessage() + "\n", cnfe);
			return null;
		}

		return connection;
	}

	public synchronized Connection getConnectionFromPool(){
		Connection connection = null;

		//Check if there is a connection available. There are times when all the connections in the pool may be used up
		if(connectionPool.size() > 0){
			connection = (Connection) connectionPool.firstElement();
			connectionPool.removeElementAt(0);
		}else{
			logger.warn("Connection Exhausted. Creating a new Connection");
			connection = createNewConnectionForPool();
			connectionPool.addElement(connection);
			
		}
		
		//Giving away the connection from the connection pool
		return connection;
	}

	public synchronized void returnConnectionToPool(Connection connection){
		try {
			if(connection == null || connection.isClosed()){
				logger.warn("Connection is 'null' or 'closed'");
				connectionPool.addElement(createNewConnectionForPool());
			}
			else{
				connectionPool.addElement(connection);
			}
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	public void shutDown() {
		logger.warn("Connection Pool Shutdown in process...");
		for (Connection connection : connectionPool) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
		logger.warn("Connection Pool Shutdown Successfully");
	}
}