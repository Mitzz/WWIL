package com.enercon.connection;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;

public class MyDataSource {

    private static MyDataSource     datasource;
    private BasicDataSource ds;

    private MyDataSource() throws IOException, SQLException, PropertyVetoException {
        ds = new BasicDataSource();
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUsername("ecare");
        ds.setPassword("customer2011");
        ds.setUrl("jdbc:oracle:thin:@172.18.16.108:1521:wcare");
       
     // the settings below are optional -- dbcp can work with defaults
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        

    }

    public static MyDataSource getInstance() throws IOException, SQLException, PropertyVetoException {
        if (datasource == null) {
            datasource = new MyDataSource();
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }
    
    
    public static void main(String[] args) throws IOException, SQLException, PropertyVetoException {
    	MyDataSource connectionPool = MyDataSource.getInstance();;
    	Connection connection = null;
		try{
			connection = connectionPool.getConnection(); // fetch a connection
			
			if (connection != null){
				System.out.println("Connection successful!");
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT S_state_name FROM tbl_state_master"); // do something with the connection.
				while(rs.next()){
					System.out.println(rs.getString(1)); // should print out "1"'
				}
			}
//			connectionPool.shutdown(); // shutdown connection pool.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    }
}

