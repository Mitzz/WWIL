package com.enercon.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import com.enercon.global.utility.FilePathUtility;

public class WcareConnectorManager implements DatabaseConnector{
	private final static Logger logger = Logger.getLogger(WcareConnectorManager.class);
	private static WcareConnectorManager scManager;
	
	final private static int MAX_POOL_SIZE = 4;

	private static String databaseUrl;
	private static String userName;
	private static String password;

	private static Vector<Connection> connectionPool = new Vector<Connection>();
	
	private WcareConnectorManager(){
//		initialize();
		initializeForDev();
	}
	
	private static void initializeForDev() {
		databaseUrl = "jdbc:oracle:thin:@172.18.16.108:1521:wcare";
		userName = "ecare";
		password = "customer2011";
		try {
			
			initializeConnectionPool();
			
		} catch (Exception e) {
			e.printStackTrace();
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
			
			System.out.println("Connection Pool : " + connectionPool);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initializeConnectionPool(){
		while(!checkIfConnectionPoolIsFull()){
			System.out.println("Connection Pool is NOT full. Proceeding with adding new connections");
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
			System.err.println("SQLException: "+sqle);
			return null;
		}
		catch(ClassNotFoundException cnfe){
			System.err.println("ClassNotFoundException: "+cnfe);
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
			connection = createNewConnectionForPool();
			connectionPool.addElement(connection);
			System.out.println("Connection Pool Size :: " + connectionPool.size());
		}
		
		//Giving away the connection from the connection pool
		return connection;
	}

	public synchronized void returnConnectionToPool(Connection connection){
		try {
			if(connection == null || connection.isClosed()){
				logger.error("Connection is 'null' or 'closed'");
				connectionPool.addElement(createNewConnectionForPool());
			}
			else{
				connectionPool.addElement(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void shutDown() {
		for (Connection connection : connectionPool) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		
	}
}

/*package com.enercon.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import com.enercon.global.utility.FilePathUtility;

public class WcareConnectorManager implements DatabaseConnector{
	private static WcareConnectorManager scManager;
	
	final private static int MAX_POOL_SIZE = 50;

	private Semaphore semaphore = new Semaphore(MAX_POOL_SIZE);
	
	private static String databaseUrl;
	private static String userName;
	private static String password;

	private static Vector<Connection> connectionPool = new Vector<Connection>();
	
	private WcareConnectorManager(){
		initialize();
	}
	
	public static synchronized WcareConnectorManager getInstance(){
		
		if (scManager == null){
			scManager = new WcareConnectorManager();
		}
		return scManager;
	}
//	InputStream inputStream = FileHelper.class.getClassLoader().getResourceAsStream(FilePathUtility.getInstance().getPath("configuration\\database\\WcareDB.properties"));
	private static void initialize(){
		Properties p1 = new Properties();
		try {
			
			InputStream inputStream = 
				    new FileInputStream(new File(FilePathUtility.getInstance().getPath("configuration\\database\\WcareDB.properties")));
			p1.load(inputStream);
			
			databaseUrl = p1.getProperty("url");
			userName = p1.getProperty("username");
			password = p1.getProperty("password");
			
//			databaseUrl = "jdbc:oracle:thin:@172.18.16.108:1521:wcare";
//			userName = "ecare";
//			password = "customer2011";
			
			inputStream.close();
			initializeConnectionPool();
			
//			System.out.println("Connection Pool : " + connectionPool);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initializeConnectionPool(){
		while(!checkIfConnectionPoolIsFull()){
//			System.out.println("Connection Pool is NOT full. Proceeding with adding new connections");
			//Adding new connection instance until the pool is full
			connectionPool.addElement(createNewConnectionForPool());
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
			System.out.println("Connection: "+connection);
		}
		catch(SQLException sqle){
			System.err.println("SQLException: "+sqle);
			return null;
		}
		catch(ClassNotFoundException cnfe){
			System.err.println("ClassNotFoundException: "+cnfe);
			return null;
		}

		return connection;
	}

	public synchronized Connection getConnectionFromPool(){
		Connection connection = null;

		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		connection = (Connection) connectionPool.firstElement();
		connectionPool.removeElementAt(0);
		
		//Giving away the connection from the connection pool
		return connection;
	}

	public synchronized void returnConnectionToPool(Connection connection){
		try {
			if(connection == null || connection.isClosed()){
				connectionPool.addElement(createNewConnectionForPool());
			}
			else{
				connectionPool.addElement(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}
}
*/
class WcareDatabaseConnector implements DatabaseConnector{
	
	private ConnectorVo connectionVo;
	private ConnectorPoolManager connector; 
	
	public WcareDatabaseConnector(){
		connectionVo = new ConnectorVo("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@172.18.16.108:1521:wcare", "ecare", "customer2011");
		connector = new ConnectorPoolManager(connectionVo, 2); 
	}

	public Connection getConnectionFromPool() {
		return connector.getConnectionFromPool();
	}

	public void returnConnectionToPool(Connection connection) {
		connector.returnConnectionToPool(connection);
	}

	public void shutDown() {
		connector.shutDown();
	}
	
}

class ConnectorPoolManager implements DatabaseConnector{
	
	private Vector<Connection> connectionPool = new Vector<Connection>();
	private ConnectorVo connectionVo;
	private final int MAX_POOL_SIZE;
	private final static Logger logger = Logger.getLogger(ConnectorPoolManager.class);
	
	public ConnectorPoolManager(ConnectorVo connectionVo, int size){
		this.connectionVo = connectionVo;
		MAX_POOL_SIZE = size;
		initialize();
	}
	
	private void initialize() {
		initializeConnectionPool();
	}

	private void initializeConnectionPool(){
		while(!checkIfConnectionPoolIsFull()){
			System.out.println("Connection Pool is NOT full. Proceeding with adding new connections");
			//Adding new connection instance until the pool is full
			connectionPool.addElement(createNewConnectionForPool());
//			System.out.println("After Adding Size: " + connectionPool.size());
		}
		System.out.println("Connection Pool is full.");
	}

	private synchronized boolean checkIfConnectionPoolIsFull(){
		
		if(connectionPool.size() <= MAX_POOL_SIZE){
			return false;
		}

		return true;
	}

	//Creating a connection
	private Connection createNewConnectionForPool(){
		Connection connection = null;

		try{
			Class.forName(connectionVo.getDriverName());
			connection = DriverManager.getConnection(connectionVo.getDatabaseUrl(), connectionVo.getUserName(), connectionVo.getPassword());
//			System.out.println("Connection: "+connection);
		}
		catch(SQLException sqle){
			System.err.println("SQLException: "+sqle);
			return null;
		}
		catch(ClassNotFoundException cnfe){
			System.err.println("ClassNotFoundException: "+cnfe);
			return null;
		}

		return connection;
	}

	public Connection getConnectionFromPool() {
		Connection connection = null;

		//Check if there is a connection available. There are times when all the connections in the pool may be used up
		if(connectionPool.size() > 0){
			connection = (Connection) connectionPool.firstElement();
			connectionPool.removeElementAt(0);
		}else{
			connection = createNewConnectionForPool();
			connectionPool.addElement(connection);
			System.out.println("Connection Pool Size :: " + connectionPool.size());
		}
		
		return connection;
	}

	public void returnConnectionToPool(Connection connection) {
		try {
			if(connection == null || connection.isClosed()){
				logger.error("Connection is 'null' or 'closed'");
				connectionPool.addElement(createNewConnectionForPool());
			}
			else{
				connectionPool.addElement(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void shutDown() {
		for (Connection connection : connectionPool) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
	}
}

class ConnectorVo{

	private String driverName;
	private String databaseUrl;
	private String userName;
	private String password;
	
	public ConnectorVo(String driverName, String databaseUrl, String userName, String password) {
		this.driverName = driverName;
		this.databaseUrl = databaseUrl;
		this.userName = userName;
		this.password = password;
	}
	public String getDatabaseUrl() {
		return databaseUrl;
	}
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
}