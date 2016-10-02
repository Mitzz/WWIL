package com.enercon.model.thread.map;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.dao.WecParameterDataDao;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;

public class PeriodWecParameterVoEvaluator<Key, Value extends IWecParameterVo> implements MapValueEvaluatorWorker<Key, Value>{
	private final static Logger logger = Logger.getLogger(PeriodWecParameterVoEvaluator.class);
	private Key key;
	private Set<String> wecIds;
	private String fromDate;
	private String toDate;
	private Set<Parameter> parameters;
	
	public PeriodWecParameterVoEvaluator(Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters) {
		this.wecIds = wecIds;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.parameters = parameters;
	}
	
	public PeriodWecParameterVoEvaluator(Key key, Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters) {
		this(wecIds, fromDate, toDate, parameters);
		this.key = key;
		
	}
	
	public static void main(String[] args) {
		try {
			IWecParameterVo parameterVo = new PeriodWecParameterVoEvaluator<String, IWecParameterVo>(null, null, null, null).evaluate();
			
		} catch (SQLException e) {
			 logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (ParseException e) {
			 logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
	
	public <T extends IWecParameterVo> T evaluate() throws SQLException, ParseException{
		T parameter = null; 
		parameter = WecParameterDataDao.getInstance().getTotal(wecIds, fromDate, toDate, parameters);
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

	public String getFromDate() {
		return fromDate;
	}
	
	public String getToDate() {
		return toDate;
	}
	
}
