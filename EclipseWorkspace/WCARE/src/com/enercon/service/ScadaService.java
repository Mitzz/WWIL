package com.enercon.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;


import com.enercon.scada.dao.ScadaDao;


public class ScadaService {

	private final static Logger logger = Logger.getLogger(ScadaService.class);
	private ScadaDao dao = ScadaDao.getInstance();
	
	public ScadaService(){}
    
    private static class SingletonHelper{
        private static final ScadaService INSTANCE = new ScadaService();
    }
     
    public static ScadaService getInstance(){
        return SingletonHelper.INSTANCE;
    }
	
    public void mergeWindSpeed(String date) throws SQLException {
		 dao.mergeWindSpeed(date);
	}
    
    public void mergeWindSpeedDate(String date) throws SQLException {
		 dao.mergeWindSpeedDate(date);
	}
}
