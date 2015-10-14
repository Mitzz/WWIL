package com.enercon.model.thread.map;

import java.text.ParseException;
import java.util.Map;
import java.util.Set;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Year;

public class ManyWecsMonthWiseYearlyGeneration<Key extends Year, Value> extends YearEvaluator<Key, Map<Month, Integer>> {
	
	public ManyWecsMonthWiseYearlyGeneration(Key year, String fromDate, String toDate, Set<String> wecIds) {
		super(year, fromDate, toDate, wecIds);
	}

	public Map<Month, Integer> call() throws Exception {
		return cal();
	}

	public Key getKey() {
		return getYear();
	}

	@Override
	public MapValueEvaluatorWorker<Month, Integer> getWorker1() {
		Year year = getYear();
		Set<String> wecIds = getWecIds();
		return new MonthGenerationEvaluator<Month, Integer>(Month.valueOf(DateUtility.gettingMonthNumberFromStringDate(getFromDate())), year, wecIds, getFromDate(), getToDate());
	}

	@Override
	public MapValueEvaluatorWorker<Month, Integer> getWorker2() throws ParseException {
		Year year = getYear();
		Set<String> wecIds = getWecIds();
		return new MonthGenerationEvaluator<Month, Integer>(Month.valueOf(DateUtility.gettingMonthNumberFromStringDate(getFromDate())), year, wecIds, getFromDate(), DateUtility.getLastDateOfMonth(getFromDate()));
	}

	@Override
	public MapValueEvaluatorWorker<Month, Integer> getWorker3(Month month) throws ParseException {
		Year year = getYear();
		Set<String> wecIds = getWecIds();
		return new MonthGenerationEvaluator<Month, Integer>(month, year, wecIds);
	}

	@Override
	public MapValueEvaluatorWorker<Month, Integer> getWorker4() throws ParseException {
		Year year = getYear();
		Set<String> wecIds = getWecIds();
		return new MonthGenerationEvaluator<Month, Integer>(Month.valueOf(DateUtility.gettingMonthNumberFromStringDate(getToDate())), year, wecIds, DateUtility.getFirstDateOfMonth(getToDate()), getToDate());
	}
	
}
