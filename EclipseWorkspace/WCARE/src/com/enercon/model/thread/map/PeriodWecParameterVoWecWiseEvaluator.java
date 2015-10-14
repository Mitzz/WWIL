package com.enercon.model.thread.map;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;

import com.enercon.dao.WecDataDao;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.model.summaryreport.Parameter;

public class PeriodWecParameterVoWecWiseEvaluator<Key, Value extends Map<String, IWecParameterVo>> implements MapValueEvaluatorWorker<Key, Value>{

	private Key key;
	private Set<String> wecIds;
	private String fromDate;
	private String toDate;
	private Set<Parameter> parameters;
	
	public PeriodWecParameterVoWecWiseEvaluator(Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters) {
		this.wecIds = wecIds;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.parameters = parameters;
	}
	
	public PeriodWecParameterVoWecWiseEvaluator(Key key, Set<String> wecIds, String toDate, String fromDate, Set<Parameter> parameters) {
		this(wecIds, fromDate, toDate, parameters);
		this.key = key;
		
	}
	
	public static void main(String[] args) {
		try {
			IWecParameterVo parameterVo = new DayWecParameterVoEvaluator<String, IWecParameterVo>(null, null, null).evaluate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public <T extends Map<String, IWecParameterVo>> T evaluate() throws SQLException, ParseException{
		T parameter = null; 
		parameter = new WecDataDao().getWecWiseWecParameterVo(fromDate, toDate, wecIds, parameters);
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

