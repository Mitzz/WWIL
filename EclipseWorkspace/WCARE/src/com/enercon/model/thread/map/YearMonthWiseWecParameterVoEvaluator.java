package com.enercon.model.thread.map;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Parameter;
import com.enercon.model.summaryreport.Year;

public class YearMonthWiseWecParameterVoEvaluator<Key extends Year, Value> implements MapValueEvaluatorWorker<Key, Map<Month, IWecParameterVo>>{

	private Key year;
	private String fromDate;
	private String toDate;
	private Set<String> wecIds;
	private Set<Parameter> parameters;
	
	public YearMonthWiseWecParameterVoEvaluator(Key year, String fromDate, String toDate, Set<String> wecIds, Set<Parameter> parameters) {
		this.year = year;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.wecIds = wecIds;
		this.parameters = parameters;
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

	public MapValueEvaluatorWorker<Month, IWecParameterVo> getWorker1(){
		return new MonthWecParameterVoEvaluator<Month, IWecParameterVo>(Month.valueOf(DateUtility.gettingMonthNumberFromStringDate(getFromDate())), year, wecIds, getFromDate(), getToDate(), parameters);
	}
	
	public MapValueEvaluatorWorker<Month, IWecParameterVo> getWorker2() throws ParseException{
		return new MonthWecParameterVoEvaluator<Month, IWecParameterVo>(Month.valueOf(DateUtility.gettingMonthNumberFromStringDate(getFromDate())), year, wecIds, getFromDate(), DateUtility.getLastDateOfMonth(getFromDate()), parameters);
	}
	public MapValueEvaluatorWorker<Month, IWecParameterVo> getWorker3(Month month){
		return new MonthWecParameterVoEvaluator<Month, IWecParameterVo>(month, year, wecIds, parameters);
	}
	public MapValueEvaluatorWorker<Month, IWecParameterVo> getWorker4() throws ParseException {
		return new MonthWecParameterVoEvaluator<Month, IWecParameterVo>(Month.valueOf(DateUtility.gettingMonthNumberFromStringDate(getToDate())), year, wecIds, DateUtility.getFirstDateOfMonth(getToDate()), getToDate(), parameters);
	}
	
	public Map<Month, IWecParameterVo> evaluate() throws ParseException{
		Map<Month, IWecParameterVo> yearMonthWiseWecParameterVo = null;
		
		MapKeyValueMapper<Month, IWecParameterVo> monthWecParameterVoMapper = new MapKeyValueMapper<Month, IWecParameterVo>();
		List<MapValueEvaluatorWorker<Month, IWecParameterVo>> monthWecParameterVoEvaluators = new ArrayList<MapValueEvaluatorWorker<Month,IWecParameterVo>>();
		String offsetFromDate = "";
		String offsetToDate = "";
		int monthDifference = 0;
		
		monthDifference = DateUtility.getMonthDifferenceBetweenTwoDates(getFromDate(), getToDate());
		
		if(monthDifference == 0){
			monthWecParameterVoEvaluators.add(getWorker1());
		}
		else {
			if(DateUtility.isFirstDateOfTheMonth(getFromDate())){
				offsetFromDate = getFromDate();
			} else {
				monthWecParameterVoEvaluators.add(getWorker2());
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
					monthWecParameterVoEvaluators.add(getWorker3(month));
				}
			}
			if(!DateUtility.isLastDateOfTheMonth(getToDate())){
				monthWecParameterVoEvaluators.add(getWorker4());
			}
		}
		yearMonthWiseWecParameterVo = monthWecParameterVoMapper.submit(monthWecParameterVoEvaluators);
		return yearMonthWiseWecParameterVo;
	}

	public Map<Month, IWecParameterVo> call() throws Exception {
		return evaluate();
	}

	public Key getKey() {
		return year;
	}

/*	public MapValueEvaluatorWorker<Month, ParameterVo> call() throws Exception {
		return null;
	}
*/	
}
