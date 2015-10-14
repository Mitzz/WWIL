package com.enercon.model.thread.map;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

import com.enercon.dao.WecDataDao;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.model.summaryreport.Parameter;

public class FiscalYearWecParameterVoEvaluator<Key extends FiscalYear, Value extends IWecParameterVo> implements MapValueEvaluatorWorker<Key, Value>{

	private Key fiscalYear;
	private Set<String> wecIds;
	private String fromDate;
	private String toDate;
	private Set<Parameter> parameters;
	
	public FiscalYearWecParameterVoEvaluator(Key fiscalYear,
			Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters) {
		this(fiscalYear, wecIds, parameters);
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public FiscalYearWecParameterVoEvaluator(Key fiscalYear,
			Set<String> wecIds, Set<Parameter> parameters) {
		this.fiscalYear = fiscalYear;
		this.wecIds = wecIds;
		this.parameters = parameters;
	}
	
	public Value call() throws SQLException, ParseException{
		Value parameter = null; 
		if(fromDate == null || toDate == null){
			parameter = getWorker1();
		} else {
			parameter = getWorker2();
		}
		return parameter;
	}
	
	public Key getKey() {
		return fiscalYear;
	}
	
	public Value getWorker1() throws SQLException, ParseException{
		return new WecDataDao().getWecParameterVo(wecIds, fiscalYear, parameters);
	}
	public Value getWorker2() throws SQLException, ParseException{
		return new WecDataDao().getWecParameterVo(getWecIds(), getFromDate(), getToDate(), parameters);
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
