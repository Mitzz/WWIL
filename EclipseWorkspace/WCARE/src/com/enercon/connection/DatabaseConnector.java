package com.enercon.connection;

import java.sql.Connection;

public interface DatabaseConnector {
	
	Connection getConnectionFromPool();
	void returnConnectionToPool(Connection connection);
	void shutDown();
	
}
