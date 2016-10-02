package com.enercon.model.dgr;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.joda.time.YearMonth;

import com.enercon.global.utility.WecParameterUtility;
import com.enercon.model.comparator.WecMasterVoComparator;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.WecParameterEvaluator;
import com.enercon.service.WecMasterService;

public class WecMonthWise {
	
	
	private static Logger logger = Logger.getLogger(WecMonthWise.class);
	
	public final static Comparator<WecMonthWise> BY_WEC_NAME = new Comparator<WecMonthWise>() {

		public int compare(WecMonthWise o1, WecMonthWise o2) {
			return WecMasterVoComparator.sortByWec(o1.getWec(), o2.getWec());
		}
	};

	//Input
	private String fromDate;
	private String toDate;
	private IWecMasterVo wec;
	private Set<Parameter> wecParameters;

	// Output
	private Map<YearMonth, IWecParameterVo> monthData = new TreeMap<YearMonth, IWecParameterVo>();
	private IWecParameterVo total;

	// Local
	private WecParameterData wecParameterData;

	public WecMonthWise(String fromDate, String toDate, IWecMasterVo wec, Set<Parameter> wecParameters) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.wec = wec;
		this.wecParameters = wecParameters;

		setUp();
	}

	private void setUp() {
		setWecParameterData();
	}

	private void setWecParameterData() {
		wecParameterData = new WecParameterData();
		wecParameterData.setDataCheck(true);
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setParameters(wecParameters);
		
		Set<IWecMasterVo> wecs = new HashSet<IWecMasterVo>();
		wecs.add(wec);
		wecParameterData.setWecs(wecs);
		wecParameterData.setPublish(1);

	}

	public WecMonthWise populateData() throws SQLException, ParseException {
		WecParameterEvaluator evaluator = WecParameterEvaluator.getInstance();
		monthData.putAll(evaluator.getYearMonthWise(wecParameterData));
		return this;
	}
	
	public WecMonthWise populateTotal(){
		total = WecParameterUtility.total(monthData.values());
		return this;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public IWecMasterVo getWec() {
		return wec;
	}

	public void setWec(IWecMasterVo wec) {
		this.wec = wec;
	}

	public Map<YearMonth, IWecParameterVo> getMonthData() {
		return monthData;
	}

	public void setDatesData(Map<YearMonth, IWecParameterVo> monthData) {
		this.monthData = monthData;
	}
	
	public IWecParameterVo getTotal() {
		return total;
	}

	public void setTotal(IWecParameterVo datesDataTotal) {
		this.total = datesDataTotal;
	}
	
	
	
	public final WecParameterData getWecParameterData() {
		return wecParameterData;
	}

	public static void main(String[] args) throws ParseException {
		Set<Parameter> parameters = new HashSet<Parameter>();
		
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.OPERATING_HOUR);
		parameters.add(Parameter.LULL_HOUR);
		parameters.add(Parameter.CF);
		parameters.add(Parameter.GA);
		parameters.add(Parameter.MA);
		
		WecMonthWise monthWise = new WecMonthWise("01-APR-2015", "02-MAR-2016", WecMasterService.getInstance().get("0809002488"), parameters);
		
		try {
			Map<YearMonth, IWecParameterVo> yearMonthWise = WecParameterEvaluator.getInstance().getYearMonthWise(monthWise.getWecParameterData());
			for(YearMonth yearMonth: yearMonthWise.keySet()){
				IWecParameterVo iWecParameterVo = yearMonthWise.get(yearMonth);
				logger.debug(String.format("(%s, %s)", yearMonth, iWecParameterVo));
			}
			monthWise.setDatesData(yearMonthWise);
			logger.debug(monthWise.populateTotal().getTotal());
			logger.debug(monthWise.populateTotal().getTotal().ga());
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
	}
}
