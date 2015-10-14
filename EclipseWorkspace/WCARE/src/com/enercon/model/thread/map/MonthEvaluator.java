package com.enercon.model.thread.map;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

import com.enercon.model.summaryreport.Year;

public abstract class MonthEvaluator<Key, Value> implements MapValueEvaluatorWorker<Key, Value>{

	private Key month;
	private Year year;
	private Set<String> wecIds;
	private String fromDate;
	private String toDate;
	
	protected MonthEvaluator(Key month, Year year,
			Set<String> wecIds, String fromDate, String toDate) {
		this(month, year, wecIds);
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	protected MonthEvaluator(Key month, Year year,
			Set<String> wecIds) {
		super();
		this.month = month;
		this.year = year;
		this.wecIds = wecIds;
	}
	
	public Value cal() throws SQLException, ParseException{
		Value parameter = null; 
		if(fromDate == null || toDate == null){
			parameter = getWorker1();
		} else {
			parameter = getWorker2();
		}
		return parameter;
	}
	
	public Key getKey() {
		return month;
	}
	
	abstract public Value getWorker1() throws SQLException, ParseException;
	abstract public Value getWorker2() throws SQLException, ParseException;

	public Key getMonth() {
		return month;
	}

	public Year getYear() {
		return year;
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
