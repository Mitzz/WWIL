package com.enercon.model.thread.map;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.summaryreport.Month;

public abstract class YearEvaluator<Key, Value> implements MapValueEvaluatorWorker<Key, Value>{

	private Key year;
	private String fromDate;
	private String toDate;
	private Set<String> wecIds;
	
	protected YearEvaluator(Key year, String fromDate, String toDate,
			Set<String> wecIds) {
		this.year = year;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.wecIds = wecIds;
	}

	public Key getYear() {
		return year;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public Set<String> getWecIds() {
		return wecIds;
	}
	
	public Map<Month, Integer> cal() throws Exception {
		Map<Month, Integer> monthsValue = null;
		
		MapKeyValueMapper<Month, Integer> m = new MapKeyValueMapper<Month, Integer>();
		List<MapValueEvaluatorWorker<Month, Integer>> workerList = new ArrayList<MapValueEvaluatorWorker<Month,Integer>>();
		String offsetFromDate = "";
		String offsetToDate = "";
		int monthDifference = 0;
		
		monthDifference = DateUtility.getMonthDifferenceBetweenTwoDates(getFromDate(), getToDate());
		if(monthDifference == 0){
			workerList.add(getWorker1());
		}
		else {
			if(DateUtility.isFirstDateOfTheMonth(getFromDate())){
				offsetFromDate = getFromDate();
			} else {
				workerList.add(getWorker2());
				offsetFromDate = DateUtility.getMonthFirstDateByOffsetFromGivenDate(getFromDate(), 1);
			}
			
			if(DateUtility.isLastDateOfTheMonth(getToDate())){
				offsetToDate = getToDate();
			} else {
				offsetToDate = DateUtility.getMonthLastDateByOffsetFromGivenDate(getToDate(), -1);
			}
			monthDifference = DateUtility.getMonthDifferenceBetweenTwoDates(offsetFromDate, offsetToDate);
			if(monthDifference > 0){
				for(Month month	: Month.values(DateUtility.gettingMonthNumberFromStringDate(offsetFromDate), DateUtility.gettingMonthNumberFromStringDate(offsetToDate))){
					workerList.add(getWorker3(month));
				}
			}
			if(!DateUtility.isLastDateOfTheMonth(getToDate())){
				System.out.println("toDate :: " + getToDate());
				workerList.add(getWorker4());
			}
		}
		monthsValue = m.submit(workerList);
		return monthsValue;
	}

	abstract public MapValueEvaluatorWorker<Month, Integer> getWorker1();
	abstract public MapValueEvaluatorWorker<Month, Integer> getWorker2() throws ParseException;
	abstract public MapValueEvaluatorWorker<Month, Integer> getWorker3(Month month) throws ParseException;
	abstract public MapValueEvaluatorWorker<Month, Integer> getWorker4() throws ParseException;
	
}
