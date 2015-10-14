package com.enercon.model.thread.map;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

import com.enercon.dao.WecDataDao;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Year;

public class MonthMAEvaluator<Key extends Month, Value extends Number> extends MonthEvaluator<Key, Value> {
	
	public MonthMAEvaluator(Key month, Year year, Set<String> wecIds){
		super(month, year, wecIds);
	}
	
	public MonthMAEvaluator(Key month, Year year, Set<String> wecIds, String fromDate, String toDate){
		super(month, year, wecIds, fromDate, toDate);
	}
	
	public Value call() throws Exception {
		return cal();
	}

	@Override
	public Value getWorker1() throws SQLException, ParseException {
		return new WecDataDao().getMA(getWecIds(), getMonth(), getYear());
	}

	@Override
	public Value getWorker2() throws SQLException, ParseException{
		return new WecDataDao().getMA(getWecIds(), getFromDate(), getToDate());
	}
	
}
