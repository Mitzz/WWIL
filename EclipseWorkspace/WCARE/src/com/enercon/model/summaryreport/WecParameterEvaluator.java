package com.enercon.model.summaryreport;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.YearMonth;

import com.enercon.dao.WecParameterDataDao;
import com.enercon.dao.master.WecMasterDao;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.WecParameterUtility;
import com.enercon.global.utility.master.WecMasterUtility;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.master.AreaMasterVo;
import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.EbMasterVo;
import com.enercon.model.master.SiteMasterVo;
import com.enercon.model.master.StateMasterVo;
import com.enercon.model.master.WecsPresent;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.table.WecReadingVo;
import com.enercon.model.thread.map.DayWecParameterVoEvaluator;
import com.enercon.model.thread.map.FiscalYearWecParameterVoEvaluator;
import com.enercon.model.thread.map.ManyWecsMonthWiseYearlyGeneration;
import com.enercon.model.thread.map.ManyWecsMonthWiseYearlyMA;
import com.enercon.model.thread.map.ManyWecsMonthWiseYearlyOpHr;
import com.enercon.model.thread.map.MapKeyValueMapper;
import com.enercon.model.thread.map.MapValueEvaluatorWorker;
import com.enercon.model.thread.map.PeriodWecParameterVoEvaluator;
import com.enercon.model.thread.map.YearMonthWiseWecParameterVoEvaluator;
import com.enercon.model.utility.WecDate;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.WecMasterService;

public class WecParameterEvaluator {
	
	private final static Logger logger = Logger.getLogger(WecParameterEvaluator.class);
	
	private WecParameterEvaluator(){}
	
	private static class SingletonHelper{
		public final static WecParameterEvaluator INSTANCE = new WecParameterEvaluator();
	}
	
	public static WecParameterEvaluator getInstance(){
		return SingletonHelper.INSTANCE;
	}

	private Map<Year, Map<Month, Integer>> getYearWiseMonthlyParameter(String fromDate, String toDate, Set<String> wecIds, Parameter parameter) throws ParseException {
		Map<String, String> years = null;
		years = DateUtility.getYearWisePeriod(fromDate, toDate);
		
		MapKeyValueMapper<Year, Map<Month, Integer>> m = null;
		List<MapValueEvaluatorWorker<Year,Map<Month , Integer>>> workerList = null;
		String endingDate = null;
		
		m = new MapKeyValueMapper<Year, Map<Month, Integer>>();
		workerList = new ArrayList<MapValueEvaluatorWorker<Year,Map<Month , Integer>>>();
		
		for(String startingDate : years.keySet()){
			endingDate = years.get(startingDate);
			
			switch (parameter) {
			case GENERATION : workerList.add(new ManyWecsMonthWiseYearlyGeneration<Year, Map<Month , Integer>>(Year.valueOf(DateUtility.gettingYearFromStringDate(startingDate)) , startingDate, endingDate, wecIds));break;
			case OPERATING_HOUR : workerList.add(new ManyWecsMonthWiseYearlyOpHr<Year, Map<Month , Integer>>(Year.valueOf(DateUtility.gettingYearFromStringDate(startingDate)) , startingDate, endingDate, wecIds));break;
			case MA : workerList.add(new ManyWecsMonthWiseYearlyMA<Year, Map<Month , Double>>(Year.valueOf(DateUtility.gettingYearFromStringDate(startingDate)) , startingDate, endingDate, wecIds));break;
			default: break;
			}
		}
		
		return m.submit(workerList);
	}
	
	public Map<Year, Map<Month, Integer>> getYearWiseMonthlyGeneration(String fromDate, String toDate, Set<String> wecIds) throws ParseException {
		return getYearWiseMonthlyParameter(fromDate, toDate, wecIds, Parameter.GENERATION);
	}

	public Map<Year, Map<Month, Integer>> getYearWiseMonthlyOperatingHour(String fromDate, String toDate, Set<String> wecIds) throws ParseException {
		return getYearWiseMonthlyParameter(fromDate, toDate, wecIds, Parameter.OPERATING_HOUR);
	}
	
	public Map<Year, Map<Month, Integer>> getYearWiseMonthlyMA(String fromDate, String toDate, Set<String> wecIds) throws ParseException {
		return getYearWiseMonthlyParameter(fromDate, toDate, wecIds, Parameter.MA);
	}

	public Map<Year, Map<Month, IWecParameterVo>> getYearWiseMonthlyWecParameterVo(String fromDate, String toDate, Set<String> wecIds, Set<Parameter> parameters) throws ParseException {
//		logger.debug("From Date: " + fromDate + ", To Date: " + toDate);
		Map<String, String> yearWisePeriod = null;
		yearWisePeriod = DateUtility.getYearWisePeriod(fromDate, toDate);
//		logger.debug("Year Wise Period: " + yearWisePeriod);
		MapKeyValueMapper<Year, Map<Month, IWecParameterVo>> mapper = null;
		List<MapValueEvaluatorWorker<Year,Map<Month , IWecParameterVo>>> yearWiseMonthlyWecParameterVoWorkers = null;
		String endingDate = null;
		
		mapper = new MapKeyValueMapper<Year, Map<Month, IWecParameterVo>>();
		yearWiseMonthlyWecParameterVoWorkers = new ArrayList<MapValueEvaluatorWorker<Year,Map<Month , IWecParameterVo>>>();
		
		for(String startingDate : yearWisePeriod.keySet()){
			endingDate = yearWisePeriod.get(startingDate);
			logger.debug("Starting Date: " + startingDate + ", Ending Date: " + endingDate);
			yearWiseMonthlyWecParameterVoWorkers.add(new YearMonthWiseWecParameterVoEvaluator<Year, Map<Month, IWecParameterVo>>(Year.valueOf(DateUtility.gettingYearFromStringDate(startingDate)) , startingDate, endingDate, wecIds, parameters));
		}
		
		return mapper.submit(yearWiseMonthlyWecParameterVoWorkers);
	}
	
	public Map<FiscalYear, Map<Month, IWecParameterVo>> getFiscalYearWiseMonthlyWecParameterVo(String fromDate, String toDate, Set<String> wecIds, Set<Parameter> parameters) throws ParseException {
		return convert(getYearWiseMonthlyWecParameterVo(fromDate, toDate, wecIds, parameters));
	}
	
	private Map<FiscalYear, Map<Month, IWecParameterVo>> convert(Map<Year, Map<Month, IWecParameterVo>> yearWiseMonthlyWecParameterVo){
		
		
		
		Map<FiscalYear, Map<Month, IWecParameterVo>> fiscalYearWiseMonthlyWecParameterVo = new LinkedHashMap<FiscalYear, Map<Month,IWecParameterVo>>();
		for(Year year : yearWiseMonthlyWecParameterVo.keySet()){
			//logger.debug("Year: " + year.getValue());
			Map<Month, IWecParameterVo> months = yearWiseMonthlyWecParameterVo.get(year);
			
			for (Month month : months.keySet()) {
				//logger.debug("Month: " + month.getValue());
				FiscalYear fy = FiscalYear.fiscalYear(year, month);
				IWecParameterVo data = null;
				
				if(!fiscalYearWiseMonthlyWecParameterVo.containsKey(fy)){
					data = yearWiseMonthlyWecParameterVo.get(year).get(month);
					Map<Month, IWecParameterVo> fiscalMonthMap = new LinkedHashMap<Month, IWecParameterVo>();
					fiscalMonthMap.put(month, data);
					fiscalYearWiseMonthlyWecParameterVo.put(fy, fiscalMonthMap);
					
				} else {
					data = yearWiseMonthlyWecParameterVo.get(year).get(month);
					Map<Month, IWecParameterVo> fiscalMonthMap = fiscalYearWiseMonthlyWecParameterVo.get(fy);
					fiscalMonthMap.put(month, data);
					fiscalYearWiseMonthlyWecParameterVo.put(fy, fiscalMonthMap);
				}
			}
		}
		
		return fiscalYearWiseMonthlyWecParameterVo;
	}
	
	public Map<Year, IWecParameterVo> getYearWiseWecParameterVo(String fromDate, String toDate, Set<String> wecIds, Set<Parameter> parameters) throws ParseException {
		System.out.println("TODO: Implement 'Year-wise' data processing");
		return null;
	}
	
	public Map<FiscalYear, IWecParameterVo> getFiscalYearWiseWecParameterVo(String fromDate, String toDate, Set<String> wecIds, Set<Parameter> parameters) throws ParseException {
		
		Map<String, String> fiscalYears = null;
		fiscalYears = DateUtility.getPeriodFiscalYearWise(fromDate, toDate);
		
		MapKeyValueMapper<FiscalYear, IWecParameterVo> m = null;
		List<MapValueEvaluatorWorker<FiscalYear, IWecParameterVo>> workerList = null;
		String endingDate = null;
		
		m = new MapKeyValueMapper<FiscalYear, IWecParameterVo>();
		workerList = new ArrayList<MapValueEvaluatorWorker<FiscalYear, IWecParameterVo>>();
		
		for(String startingDate : fiscalYears.keySet()){
			endingDate = fiscalYears.get(startingDate);
			
			if(DateUtility.isFirstDateOfTheFiscalYear(startingDate) && DateUtility.isLastDateOfTheFiscalYear(endingDate)){
				workerList.add(new FiscalYearWecParameterVoEvaluator<FiscalYear, IWecParameterVo>(FiscalYear.valueOf(DateUtility.getFiscalYear(startingDate)), wecIds, parameters));
			} else {
				workerList.add(new FiscalYearWecParameterVoEvaluator<FiscalYear, IWecParameterVo>(FiscalYear.valueOf(DateUtility.getFiscalYear(startingDate)), wecIds, startingDate, endingDate, parameters));
			}
		}
		
		return m.submit(workerList);
	}
	
	public Map<IStateMasterVo, IWecParameterVo> getStateWiseWecParameterVo(String date, Collection<IWecMasterVo> wecs, Set<Parameter> parameters) throws SQLException {
		Map<IStateMasterVo, Set<IWecMasterVo>> stateWiseWecs = WecMasterDao.getInstance().getWecsStateWise(wecs);
		
		MapKeyValueMapper<IStateMasterVo, IWecParameterVo> m = null;
		List<MapValueEvaluatorWorker<IStateMasterVo, IWecParameterVo>> workerList = null;
		
		m = new MapKeyValueMapper<IStateMasterVo, IWecParameterVo>();
		workerList = new ArrayList<MapValueEvaluatorWorker<IStateMasterVo, IWecParameterVo>>();
		
		for (IStateMasterVo state : stateWiseWecs.keySet()) {
			Set<String> wecIds = WecMasterUtility.getWecIds(stateWiseWecs.get(state));
			workerList.add(new DayWecParameterVoEvaluator<IStateMasterVo, IWecParameterVo>(state , wecIds, date, parameters));
		}
		
		return m.submit(workerList);
	}
	
	public Map<IStateMasterVo, IWecParameterVo> getStateWiseWecParameterVoWithoutThreading(String date, Collection<IWecMasterVo> wecs, Set<Parameter> parameters) throws SQLException, ParseException {
		
		Map<IStateMasterVo, IWecParameterVo> result = new HashMap<IStateMasterVo, IWecParameterVo>();
		Map<IStateMasterVo, Set<IWecMasterVo>> stateWiseWecs = WecMasterDao.getInstance().getWecsStateWise(wecs);
		
		for (IStateMasterVo state : stateWiseWecs.keySet()) {
			logger.debug("State: " + state.getName());
			Set<String> wecIds = WecMasterUtility.getWecIds(stateWiseWecs.get(state));
			result.put(state, new DayWecParameterVoEvaluator<IStateMasterVo, IWecParameterVo>(state , wecIds, date, parameters).evaluate());
		}
		
		return result;
	}
	
	public Map<StateMasterVo, Map<FiscalYear, Map<Month, IWecParameterVo>>> getStateWiseFiscalYearWiseMonthlyWecParameterVo(List<StateMasterVo> states, String fromDate, String toDate, List<Parameter> parameters) throws ParseException{
		Map<StateMasterVo, Map<FiscalYear, Map<Month, IWecParameterVo>>> w = getMasterWiseFiscalYearWiseMonthlyWecParameterVo(states, fromDate, toDate, parameters);
		
		for(StateMasterVo state: w.keySet()){
			Map<FiscalYear, Map<Month, IWecParameterVo>> fys = w.get(state);
			
			for(FiscalYear fy: fys.keySet()){
				Map<Month, IWecParameterVo> months = fys.get(fy);
				
				for(Month month: months.keySet()){
					IWecParameterVo vo = months.get(month);
					
					logger.debug(vo);
				}
			}
		}
		return getMasterWiseFiscalYearWiseMonthlyWecParameterVo(states, fromDate, toDate, parameters);
	}
	
	public Map<SiteMasterVo, Map<FiscalYear, Map<Month, IWecParameterVo>>> getSiteWiseFiscalYearWiseMonthlyWecParameterVo(List<SiteMasterVo> sites, String fromDate, String toDate, List<Parameter> parameters) throws ParseException{
		return getMasterWiseFiscalYearWiseMonthlyWecParameterVo(sites, fromDate, toDate, parameters);
	}
	
	public <Master extends WecsPresent> Map<Master, Map<FiscalYear, Map<Month, IWecParameterVo>>> getMasterWiseFiscalYearWiseMonthlyWecParameterVo(List<Master> masters, String fromDate, String toDate, List<Parameter> parameters) throws ParseException{
		Map<Master, Map<FiscalYear, Map<Month, IWecParameterVo>>> result = new LinkedHashMap<Master, Map<FiscalYear,Map<Month,IWecParameterVo>>>();
		for(Master master: masters){
			Set<String> wecIds = new HashSet<String>(WecMasterUtility.getWecIds(master.getWecs()));
			result.put(master, getFiscalYearWiseMonthlyWecParameterVo(fromDate, toDate, wecIds, new LinkedHashSet<Parameter>(parameters)));
		}
		return result;
	}
	
	public Map<StateMasterVo, IWecParameterVo> getStateWiseWecParameterVo(List<StateMasterVo> states, String date, List<Parameter> parameters){
		return get(states, date, parameters);
	}
	
	public Map<AreaMasterVo, IWecParameterVo> getAreaWiseWecParameterVo(List<AreaMasterVo> areas, String date, List<Parameter> parameters){
		return get(areas, date, parameters);
	}
	
	public Map<SiteMasterVo, IWecParameterVo> getSiteWiseWecParameterVo(List<SiteMasterVo> sites, String date, List<Parameter> parameters){
		return get(sites, date, parameters);
	}
	
	public Map<EbMasterVo, IWecParameterVo> getEbWiseWecParameterVo(List<EbMasterVo> ebs, String date, List<Parameter> parameters){
		return get(ebs, date, parameters);
	}
	
	public Map<CustomerMasterVo, IWecParameterVo> getCustomerWiseWecParameterVo(List<CustomerMasterVo> customers, String date, List<Parameter> parameters){
		return get(customers, date, parameters);
	}
	
	public <Master extends WecsPresent> Map<Master, IWecParameterVo> get(List<Master> keys, String date, List<Parameter> parameters){
		MapKeyValueMapper<Master, IWecParameterVo> mapper = new MapKeyValueMapper<Master, IWecParameterVo>();
		List<MapValueEvaluatorWorker<Master, IWecParameterVo>> workers = new ArrayList<MapValueEvaluatorWorker<Master, IWecParameterVo>>();
		
		for (Master key : keys) {
			workers.add(new DayWecParameterVoEvaluator<Master, IWecParameterVo>(key , key.getWecs(), date, parameters));
		} 
		
		return mapper.submit(workers);
	}
	
	public Map<IStateMasterVo, IWecParameterVo> getStateWise(String fromDate, String toDate, Collection<IWecMasterVo> wecs, Set<Parameter> parameters) throws SQLException {
		
		Map<IStateMasterVo, Set<IWecMasterVo>> stateWiseWecs = WecMasterDao.getInstance().getWecsStateWise(wecs);
		
		MapKeyValueMapper<IStateMasterVo, IWecParameterVo> m = null;
		List<MapValueEvaluatorWorker<IStateMasterVo, IWecParameterVo>> workerList = null;
		m = new MapKeyValueMapper<IStateMasterVo, IWecParameterVo>();
		workerList = new ArrayList<MapValueEvaluatorWorker<IStateMasterVo, IWecParameterVo>>();
		
		for (IStateMasterVo state: stateWiseWecs.keySet()) {
			Set<String> wecIds = WecMasterUtility.getWecIds(stateWiseWecs.get(state));
			workerList.add(new PeriodWecParameterVoEvaluator<IStateMasterVo, IWecParameterVo>(state, wecIds, fromDate, toDate, parameters));
		}
		Map<IStateMasterVo, IWecParameterVo> stateWiseWecParameterVo = m.submit(workerList);
		
		return stateWiseWecParameterVo;
	}
	
	public Map<IStateMasterVo, IWecParameterVo> getStateWiseWithoutThreading(String fromDate, String toDate, Collection<IWecMasterVo> wecs, Set<Parameter> parameters) throws SQLException, ParseException {
		Map<IStateMasterVo, IWecParameterVo> result = new HashMap<IStateMasterVo, IWecParameterVo>();
		Map<IStateMasterVo, Set<IWecMasterVo>> stateWiseWecs = WecMasterDao.getInstance().getWecsStateWise(wecs);
		
		for (IStateMasterVo state: stateWiseWecs.keySet()) {
			logger.debug("State: " + state.getName());
			Set<String> wecIds = WecMasterUtility.getWecIds(stateWiseWecs.get(state));
			result.put(state, new PeriodWecParameterVoEvaluator<IStateMasterVo, IWecParameterVo>(state, wecIds, fromDate, toDate, parameters).evaluate());
		}
		return result;
	}
	
	public Map<IStateMasterVo, IWecParameterVo> getStateWiseWecParameterVoWithoutThreadinglskfj(String fromDate, String toDate, Collection<IWecMasterVo> wecs, Set<Parameter> parameters) throws SQLException, ParseException {
		Map<IStateMasterVo, IWecParameterVo> result = new HashMap<IStateMasterVo, IWecParameterVo>();
		Map<IWecMasterVo, IWecParameterVo> wecData = new HashMap<IWecMasterVo, IWecParameterVo>();
		
		WecParameterData wecParameterData = new WecParameterData();
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setParameters(parameters);
		wecParameterData.setWecs(new HashSet<IWecMasterVo>(wecs));
		
		wecData = WecParameterEvaluator.getInstance().getWecWise(wecParameterData);
		
		Map<IStateMasterVo, Set<IWecMasterVo>> stateWiseWecs = WecMasterDao.getInstance().getWecsStateWise(wecs);
		
		for (IStateMasterVo state: stateWiseWecs.keySet()) {
			Collection<IWecMasterVo> wecsByState = stateWiseWecs.get(state);
			List<IWecParameterVo> vos = new ArrayList<IWecParameterVo>();
			
			for(IWecMasterVo wec: wecsByState){
//				if(wecData.get(wec) != null);
				vos.add(wecData.get(wec));
			}
			
			result.put(state, WecParameterUtility.total(vos));
		}
		return result;
	}
	
//	public Map<String, IWecParameterVo> getWecWiseWecParameterVo(String date, Set<String> allWecIds, Set<Parameter> parameters) throws SQLException, ParseException {
//		DayWecParameterVoWecWiseEvaluator<String, Map<String,IWecParameterVo>> sd = new DayWecParameterVoWecWiseEvaluator<String, Map<String,IWecParameterVo>>(allWecIds, date, parameters);
//		return sd.evaluate();
//	}
	
//	public Map<String, IWecParameterVo> getWecWiseWecParameterVo(String fromDate, String toDate, Set<String> allWecIds, Set<Parameter> parameters) throws SQLException, ParseException {
//		PeriodWecParameterVoWecWiseEvaluator<String, Map<String,IWecParameterVo>> sd = new PeriodWecParameterVoWecWiseEvaluator<String, Map<String,IWecParameterVo>>(allWecIds, fromDate, toDate, parameters);
//		return sd.evaluate();
//	}
	
	public IWecParameterVo getTotal(WecParameterData parameterData) throws SQLException, ParseException {
		IWecParameterVo p = new DayWecParameterVoEvaluator<Void, IWecParameterVo>(parameterData).evaluateByWecParameterData();
		return p;
	}
	
	public Map<IWecMasterVo, IWecParameterVo> getWecWise(WecParameterData parameterData) throws SQLException {
		return WecParameterDataDao.getInstance().getWecWise(parameterData);
	}
	
	
	
	public Map<WecDate, IWecParameterVo> getWecDateWise(WecParameterData parameterData) throws SQLException {
		return WecParameterDataDao.getInstance().getWecDateWise(parameterData);
	}

	

	public static void main(String[] args) {
		Set<Parameter> parameters = new LinkedHashSet<Parameter>();

		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.REVENUE);
		parameters.add(Parameter.FM_LOSS);
		parameters.add(Parameter.LR_LOSS);
		parameters.add(Parameter.CF);
		parameters.add(Parameter.MA);
		parameters.add(Parameter.GA);
		
		Set<String> customerIds = new HashSet<String>(Arrays.asList("1404000004", "1009000004", "1107000004", "1107000002", "1107000003", "1205000001", "1204000008", "1204000009", "1207000002", "1304000001", "1304000029"));
		
		List<IWecMasterVo> wecs = WecMasterService.getInstance().getAll(CustomerMasterService.getInstance().get(customerIds));
		String fromDate = "01-APR-2015";
		String toDate = "01-MAR-2016";
		
		WecParameterEvaluator evaluator = WecParameterEvaluator.getInstance();
		
		try {
			Map<IStateMasterVo, IWecParameterVo> stateWiseWecParameterVoWithoutThreadinglskfj = evaluator.getStateWiseWecParameterVoWithoutThreadinglskfj(fromDate, toDate, wecs, parameters);
			
			for(IStateMasterVo state: stateWiseWecParameterVoWithoutThreadinglskfj.keySet()){
				IWecParameterVo iWecParameterVo = stateWiseWecParameterVoWithoutThreadinglskfj.get(state);
//				if(iWecParameterVo != null)
					logger.debug(String.format("State: %s, %s", state.getName(), iWecParameterVo));
			}
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		
	}

	public Map<String, IWecParameterVo> getDateWise(WecParameterData wecParameterData) throws SQLException {
		return WecParameterDataDao.getInstance().getDateWise(wecParameterData);
	}
	
	public Map<YearMonth, IWecParameterVo> getYearMonthWise(WecParameterData wecParameterData) throws SQLException, ParseException {
		return WecParameterDataDao.getInstance().getYearMonthWise(wecParameterData);
	}

	public Map<IWecMasterVo, Map<DateTime, IWecParameterVo>> getWecWiseDateWise(WecParameterData wecParameterData) throws SQLException, ParseException {
		return WecParameterDataDao.getInstance().getWecWiseDateWise(wecParameterData);
	}
	
	public Map<IWecMasterVo, Map<YearMonth, IWecParameterVo>> getWecWiseYearMonthWise(WecParameterData wecParameterData) throws SQLException, ParseException {
		return WecParameterDataDao.getInstance().getWecWiseYearMonthWise(wecParameterData);
	}
	
	public boolean delete(WecReadingVo vo) throws SQLException {
		return WecParameterDataDao.getInstance().delete(vo);
	}
}

