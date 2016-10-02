package com.enercon.model.thread.map;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.dao.WecParameterDataDao;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;

public class DayWecParameterVoWecWiseEvaluator<Key, Value extends Map<String, IWecParameterVo>> implements MapValueEvaluatorWorker<Key, Value>{
	private final static Logger logger = Logger.getLogger(DayWecParameterVoWecWiseEvaluator.class);
	private Key key;
	private Set<String> wecIds;
	private String date;
	private Set<Parameter> parameters;
	
	public DayWecParameterVoWecWiseEvaluator(Set<String> wecIds, String date, Set<Parameter> parameters) {
		this.wecIds = wecIds;
		this.date = date;
		this.parameters = parameters;
	}
	
	public DayWecParameterVoWecWiseEvaluator(Key key, Set<String> wecIds, String date, Set<Parameter> parameters) {
		this.key = key;
		this.wecIds = wecIds;
		this.date = date;
		this.parameters = parameters;
	}
	
	public static void main(String[] args) {
		try {
			IWecParameterVo parameterVo = new DayWecParameterVoEvaluator<String, IWecParameterVo>(null, null, null).evaluate();
			
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
	
	public <T extends Map<String, IWecParameterVo>> T evaluate() throws SQLException, ParseException{
		T parameter = null; 
		parameter = WecParameterDataDao.getInstance().getWecWise(date, date, wecIds, parameters);
		return parameter;
	}

	public Value call() throws SQLException, ParseException{
		return evaluate();
	}
	
	public Key getKey() {
		return key;
	}
	
	public Set<String> getWecIds() {
		return wecIds;
	}

	public String getDate() {
		return date;
	}
	
}
