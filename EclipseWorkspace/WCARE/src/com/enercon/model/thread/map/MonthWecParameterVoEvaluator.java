package com.enercon.model.thread.map;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

import com.enercon.dao.WecParameterDataDao;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.parameter.wec.Parameter;
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
		return WecParameterDataDao.getInstance().getTotal(getWecIds(), getMonth(), getYear(), parameters);
	}

	@Override
	public Value getWorker2() throws SQLException, ParseException {
		return WecParameterDataDao.getInstance().getTotal(getWecIds(), getFromDate(), getToDate(), parameters);
	}
	
}
