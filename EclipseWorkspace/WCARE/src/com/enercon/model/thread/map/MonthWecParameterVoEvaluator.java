package com.enercon.model.thread.map;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

import com.enercon.dao.WecDataDao;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Parameter;
import com.enercon.model.summaryreport.Year;

public class MonthWecParameterVoEvaluator <Key extends Month, Value extends IWecParameterVo> extends MonthEvaluator<Key, Value> {
	private Set<Parameter> parameters;
	
	public MonthWecParameterVoEvaluator(Key month, Year year, Set<String> wecIds, Set<Parameter> parameters){
		super(month, year, wecIds);
		this.parameters = parameters;
	}
	
	public MonthWecParameterVoEvaluator(Key month, Year year, Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters){
		super(month, year, wecIds, fromDate, toDate);
		this.parameters = parameters;
	}

	
	public Value call() throws Exception {
		return cal();
	}

	@Override
	public Value getWorker1() throws SQLException, ParseException {
		return new WecDataDao().getWecParameterVo(getWecIds(), getMonth(), getYear(), parameters);
	}

	@Override
	public Value getWorker2() throws SQLException, ParseException {
		return new WecDataDao().getWecParameterVo(getWecIds(), getFromDate(), getToDate(), parameters);
	}
	
}
