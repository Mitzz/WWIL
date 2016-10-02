package com.enercon.struts.dao.query;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.internal.FileHelper;

public class SelectVoQueryManager implements QuerySelector{
	
	 private final static Logger logger = Logger.getLogger(SelectVoQueryManager.class);
	
	private static Map<String, String> selectVoQueryResolver = new HashMap<String, String>();
	
	static{
		initialize();
	}
	
	private static void initialize(){
		Properties properties = new Properties();
		try {
			InputStream inputStream = 
					FileHelper.class.getClassLoader().getResourceAsStream("/com/enercon/files/SelectVoQuery.properties");
			properties.load(inputStream);
			
			for (String key : properties.stringPropertyNames()) {
			    String value = properties.getProperty(key);
			    selectVoQueryResolver.put(key, value);
			}
			inputStream.close();
			
		} catch (Exception e) {
			 logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	public Map<String, String> getQueryMapper() {
		return selectVoQueryResolver;
	}

}
