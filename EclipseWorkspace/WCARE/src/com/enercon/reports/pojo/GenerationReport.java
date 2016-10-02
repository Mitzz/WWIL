package com.enercon.reports.pojo;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.enercon.dao.master.WecMasterDao;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utils.CallSchedulerForSendingMail;
import com.enercon.model.DataVo;
import com.enercon.model.DataVoService;
import com.enercon.model.comparator.DataVoComparator;
import com.enercon.model.comparator.StateMasterVoComparator;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.WecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.summaryreport.WecParameterEvaluator;

public class GenerationReport {
	private final static Logger logger = Logger.getLogger(GenerationReport.class);
	private String customerFullName;
	private String customerShortName;
	private Set<String> customerIds;
	private String reportDate;
	private List<IWecMasterVo> wecs;
	private double totalWecCapacity;

	private Map<IStateMasterVo, IWecParameterVo> stateWiseReportDateWecParameterVo;
	private Map<IStateMasterVo, IWecParameterVo> stateWiseCurrentFiscalYearWecParameterVo;
	private Map<IStateMasterVo, IWecParameterVo> stateWisePreviousFiscalYearWecParameterVo;

	private List<DataVo> dataForMALessThan95;
	private List<DataVo> dataForMALessThan98;
	
	private IWecParameterVo reportDateTotal = new WecParameterVo();
	private IWecParameterVo currentFYTotal = new WecParameterVo();
	private IWecParameterVo previousFYTotal = new WecParameterVo();
	
	public GenerationReport() {
		
	}
	
	public GenerationReport initialise(String date) throws ParseException, SQLException {
		reportDate = date; 
		WecMasterDao wecMasterDao = WecMasterDao.getInstance();
		wecs = wecMasterDao.getActiveWecs(customerIds);
		setTotalWecCapacity(wecMasterDao.getTotalCapacity(wecs));

		stateWiseReportDateWecParameterVo = new TreeMap<IStateMasterVo, IWecParameterVo>(StateMasterVoComparator.BY_NAME);
		stateWiseReportDateWecParameterVo.putAll(getStateWiseWecParamterVo());
		stateWiseCurrentFiscalYearWecParameterVo = getCurrentFiscalYearData();
		stateWisePreviousFiscalYearWecParameterVo = getPreviousFiscalYearData();

		dataForMALessThan95 = getDailyMALessThan95();
		dataForMALessThan98 = getPeriodMALessThan98();
		
		populateTotal();

		return this;
	}
	
	public GenerationReport initialiseForIpp(String date) throws ParseException, SQLException {
		reportDate = date; 
		WecMasterDao wecMasterDao = WecMasterDao.getInstance();
		wecs = wecMasterDao.getActiveWecs(customerIds);
		setTotalWecCapacity(wecMasterDao.getTotalCapacity(wecs));

		stateWiseReportDateWecParameterVo = getStateWiseWecParamterVo();
		stateWiseCurrentFiscalYearWecParameterVo = getCurrentFiscalYearData();
		stateWisePreviousFiscalYearWecParameterVo = getPreviousFiscalYearData();

		populateTotal();

		return this;
	}

	private void populateTotal() {
		
		IWecParameterVo vo = null;
		
		for(IStateMasterVo state: stateWiseReportDateWecParameterVo.keySet()){
			
			vo = stateWiseReportDateWecParameterVo.get(state);
			reportDateTotal.add(vo);
			
			vo = stateWiseCurrentFiscalYearWecParameterVo.get(state);
			currentFYTotal.add(vo);
			
			vo = stateWisePreviousFiscalYearWecParameterVo.get(state);
			previousFYTotal.add(vo);
		}
	}

	private List<DataVo> getPeriodMALessThan98() throws SQLException, ParseException {
		Set<Parameter> maLessThan98Parameters = new LinkedHashSet<Parameter>();
		maLessThan98Parameters.add(Parameter.GENERATION);
		maLessThan98Parameters.add(Parameter.OPERATING_HOUR);
		maLessThan98Parameters.add(Parameter.MA);
		
		List<DataVo> dataVos = new DataVoService().periodMALessThan98(wecs, DateUtility.getFirstDateOfMonth(reportDate), reportDate, maLessThan98Parameters);
		Collections.sort(dataVos, DataVoComparator.BY_STATE_SITE_WEC);
		return dataVos;
	}

	private List<DataVo> getDailyMALessThan95() throws SQLException, ParseException {
		Set<Parameter> maLessThan95Parameters = new LinkedHashSet<Parameter>();
		maLessThan95Parameters.add(Parameter.GENERATION);
		maLessThan95Parameters.add(Parameter.OPERATING_HOUR);
		maLessThan95Parameters.add(Parameter.MA);
		maLessThan95Parameters.add(Parameter.REMARK);
		
		List<DataVo> dataVos = new DataVoService().dailyMALessThan95(wecs, reportDate, maLessThan95Parameters);
		Collections.sort(dataVos, DataVoComparator.BY_AREA_WEC);
		return dataVos;
	}

	private Map<IStateMasterVo, IWecParameterVo> getPreviousFiscalYearData() throws SQLException,
			ParseException {
		Set<Parameter> previousFiscalYearParameters = new LinkedHashSet<Parameter>();
		previousFiscalYearParameters.add(Parameter.GENERATION);
		return WecParameterEvaluator.getInstance().getStateWise(DateUtility.getFirstDateOfFiscalYear(DateUtility.getFiscalYear(reportDate) - 1),
				DateUtility.getBackwardDateInStringWRTMonths(reportDate, -12), wecs, previousFiscalYearParameters);
	}

	private Map<IStateMasterVo, IWecParameterVo> getCurrentFiscalYearData() throws SQLException,
			ParseException {
		
		Set<Parameter> currentFiscalYearParameters = new LinkedHashSet<Parameter>();
		currentFiscalYearParameters.add(Parameter.GENERATION);
		currentFiscalYearParameters.add(Parameter.REVENUE);
		currentFiscalYearParameters.add(Parameter.FM_LOSS);
		currentFiscalYearParameters.add(Parameter.LR_LOSS);

		return WecParameterEvaluator.getInstance().getStateWiseWithoutThreading(DateUtility.getFirstDateOfFiscalYear(DateUtility.getFiscalYear(reportDate)),reportDate, wecs, currentFiscalYearParameters);
	}

	private Map<IStateMasterVo, IWecParameterVo> getStateWiseWecParamterVo() throws SQLException, ParseException {
		Set<Parameter> dailyParameters = new LinkedHashSet<Parameter>();

		dailyParameters.add(Parameter.GENERATION);
		dailyParameters.add(Parameter.REVENUE);
		dailyParameters.add(Parameter.FM_LOSS);
		dailyParameters.add(Parameter.LR_LOSS);
		dailyParameters.add(Parameter.CF);
		dailyParameters.add(Parameter.MA);
		dailyParameters.add(Parameter.GA);

		return WecParameterEvaluator.getInstance().getStateWiseWecParameterVo(reportDate, wecs, dailyParameters);
	}

	public GenerationReport(String customerFullName, String customerShortName,
			Set<String> customerIds, String reportDate) throws SQLException,
			ParseException {
		setCustomerFullName(customerFullName);
		setCustomerShortName(customerShortName);
		setCustomerIds(customerIds);
		this.reportDate = reportDate;
		// setReadingDate("21-SEP-2015");
		initialise("21-SEP-2015");
		// System.out.println(this);
	}

	public double getTotalWecCapacity() {
		return totalWecCapacity;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getCustomerShortName() {
		return customerShortName;
	}

	public void setCustomerShortName(String customerShortName) {
		this.customerShortName = customerShortName;
	}

	public Set<String> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(Set<String> customerIds) {
		this.customerIds = customerIds;
	}

	public String getReportDate() {
		return reportDate;
	}

	public GenerationReport setReportDate(String readingDate) {
		this.reportDate = readingDate;
		return this;
	}
	
	public List<IWecMasterVo> getWecs() {
		return wecs;
	}

	public void setWecs(List<IWecMasterVo> wecs) {
		this.wecs = wecs;
	}

	public Map<IStateMasterVo, IWecParameterVo> getStateWiseReportDateWecParameterVo() {
		return stateWiseReportDateWecParameterVo;
	}

	public void setStateWiseReportDateWecParameterVo(
			Map<IStateMasterVo, IWecParameterVo> stateWiseReportDateWecParameterVo) {
		this.stateWiseReportDateWecParameterVo = stateWiseReportDateWecParameterVo;
	}

	public Map<IStateMasterVo, IWecParameterVo> getStateWiseCurrentFiscalYearWecParameterVo() {
		return stateWiseCurrentFiscalYearWecParameterVo;
	}

	public void setStateWiseCurrentFiscalYearWecParameterVo(
			Map<IStateMasterVo, IWecParameterVo> stateWiseCurrentFiscalYearWecParameterVo) {
		this.stateWiseCurrentFiscalYearWecParameterVo = stateWiseCurrentFiscalYearWecParameterVo;
	}

	public Map<IStateMasterVo, IWecParameterVo> getStateWisePreviousFiscalYearWecParameterVo() {
		return stateWisePreviousFiscalYearWecParameterVo;
	}

	public void setStateWisePreviousFiscalYearWecParameterVo(
			Map<IStateMasterVo, IWecParameterVo> stateWisePreviousFiscalYearWecParameterVo) {
		this.stateWisePreviousFiscalYearWecParameterVo = stateWisePreviousFiscalYearWecParameterVo;
	}

	

	public static void main(String[] args) {
		
		CallSchedulerForSendingMail callSchedulerForSendingMail = new CallSchedulerForSendingMail();
		
		callSchedulerForSendingMail.sendIPPGroupMail("28-SEP-2015");
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/enercon/spring/bean/bean-config.xml");
		
		String date = "28-SEP-2015";
		for(int i = 1; i <= 7; i++){
			GenerationReport gr = (GenerationReport) ac.getBean("generationReport" + i);
			try {
				gr.initialise(date);
			} catch (ParseException e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			} catch (SQLException e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
			
			try {
				callSchedulerForSendingMail.sendMail(new ArrayList<GenerationReport>(Arrays.asList(gr)));
			} catch (SQLException e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	}
	
	

	

	public List<DataVo> getDataForMALessThan95() {
		return dataForMALessThan95;
	}

	public void setDataForMALessThan95(List<DataVo> dataForMALessThan95) {
		this.dataForMALessThan95 = dataForMALessThan95;
	}

	public List<DataVo> getDataForMALessThan98() {
		return dataForMALessThan98;
	}

	public void setDataForMALessThan98(List<DataVo> dataForMALessThan98) {
		this.dataForMALessThan98 = dataForMALessThan98;
	}

	public IWecParameterVo getReportDateTotal() {
		return reportDateTotal;
	}

	public void setReportDateTotal(IWecParameterVo reportDateTotal) {
		this.reportDateTotal = reportDateTotal;
	}

	public IWecParameterVo getCurrentFYTotal() {
		return currentFYTotal;
	}

	public void setCurrentFYTotal(IWecParameterVo currentFYTotal) {
		this.currentFYTotal = currentFYTotal;
	}

	public void setTotalWecCapacity(double totalWecCapacity) {
		this.totalWecCapacity = totalWecCapacity;
	}

	public IWecParameterVo getPreviousFYTotal() {
		return previousFYTotal;
	}

	public void setPreviousFYTotal(IWecParameterVo previousFYTotal) {
		this.previousFYTotal = previousFYTotal;
	}
	
}

//package com.enercon.reports.pojo;
//
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//
//import org.apache.log4j.Logger;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.enercon.dao.master.WecMasterDao;
//import com.enercon.global.utility.DateUtility;
//import com.enercon.global.utils.CallSchedulerForSendingMail;
//import com.enercon.model.DataVo;
//import com.enercon.model.DataVoService;
//import com.enercon.model.report.IWecParameterVo;
//import com.enercon.model.report.WecParameterVo;
//import com.enercon.model.summaryreport.Parameter;
//import com.enercon.model.summaryreport.ParameterEvaluator;
//
//public class GenerationReport {
//	private final static Logger logger = Logger.getLogger(GenerationReport.class);
//	private String customerFullName;
//	private String customerShortName;
//	private Set<String> customerIds;
//	private String reportDate;
//	private Set<String> wecIds;
//	private double totalWecCapacity;
//
//	private Map<String, IWecParameterVo> stateWiseReportDateWecParameterVo;
//	private Map<String, IWecParameterVo> stateWiseCurrentFiscalYearWecParameterVo;
//	private Map<String, IWecParameterVo> stateWisePreviousFiscalYearWecParameterVo;
//
//	private List<DataVo> dataForMALessThan95;
//	private List<DataVo> dataForMALessThan98;
//	
//	private IWecParameterVo reportDateTotal = new WecParameterVo();
//	private IWecParameterVo currentFYTotal = new WecParameterVo();
//	private IWecParameterVo previousFYTotal = new WecParameterVo();
//	
//	public GenerationReport() {
//		
//	}
//	
//	public GenerationReport initialise(String date) throws ParseException, SQLException {
//		reportDate = date; 
//		WecMasterDao wecMasterDao = WecMasterDao.getInstance();
//		wecIds = wecMasterDao.getWecIdsBasedOnCustomerIds(customerIds);
//		setTotalWecCapacity(wecMasterDao.getTotalWecCapacityBasedOnWecIds(wecIds));
//
//		stateWiseReportDateWecParameterVo = new TreeMap<String, IWecParameterVo>(getStateWiseWecParamterVo());
//		stateWiseCurrentFiscalYearWecParameterVo = getCurrentFiscalYearData();
//		stateWisePreviousFiscalYearWecParameterVo = getPreviousFiscalYearData();
//
//		dataForMALessThan95 = getDailyMALessThan95();
//		dataForMALessThan98 = getPeriodMALessThan98();
//		
//		populateTotal();
//
//		return this;
//	}
//	
//	public GenerationReport initialiseForIpp(String date) throws ParseException, SQLException {
//		reportDate = date; 
//		WecMasterDao wecMasterDao = WecMasterDao.getInstance();
//		wecIds = wecMasterDao.getWecIdsBasedOnCustomerIds(customerIds);
//		setTotalWecCapacity(wecMasterDao.getTotalWecCapacityBasedOnWecIds(wecIds));
//
//		stateWiseReportDateWecParameterVo = new TreeMap<String, IWecParameterVo>(getStateWiseWecParamterVo());
//		stateWiseCurrentFiscalYearWecParameterVo = getCurrentFiscalYearData();
//		stateWisePreviousFiscalYearWecParameterVo = getPreviousFiscalYearData();
//
//		populateTotal();
//
//		return this;
//	}
//
//	private void populateTotal() {
//		
//		IWecParameterVo vo = null;
//		
//		for(String stateId: stateWiseReportDateWecParameterVo.keySet()){
//			
//			vo = stateWiseReportDateWecParameterVo.get(stateId);
//			reportDateTotal.add(vo);
//			
//			vo = stateWiseCurrentFiscalYearWecParameterVo.get(stateId);
//			currentFYTotal.add(vo);
//			
//			vo = stateWisePreviousFiscalYearWecParameterVo.get(stateId);
//			previousFYTotal.add(vo);
//		}
//	}
//
//	private List<DataVo> getPeriodMALessThan98() throws SQLException, ParseException {
//		Set<Parameter> maLessThan98Parameters = new LinkedHashSet<Parameter>();
//		maLessThan98Parameters.add(Parameter.GENERATION);
//		maLessThan98Parameters.add(Parameter.OPERATING_HOUR);
//		maLessThan98Parameters.add(Parameter.MA);
//		
//		List<DataVo> dataVos = new DataVoService().periodMALessThan98(wecIds, DateUtility.getFirstDateOfMonth(reportDate), reportDate, maLessThan98Parameters);
//		Collections.sort(dataVos, DataVo.STATE_SITE_WEC);
//		return dataVos;
//	}
//
//	private List<DataVo> getDailyMALessThan95() throws SQLException, ParseException {
//		Set<Parameter> maLessThan95Parameters = new LinkedHashSet<Parameter>();
//		maLessThan95Parameters.add(Parameter.GENERATION);
//		maLessThan95Parameters.add(Parameter.OPERATING_HOUR);
//		maLessThan95Parameters.add(Parameter.MA);
//		maLessThan95Parameters.add(Parameter.REMARK);
//		
//		List<DataVo> dataVos = new DataVoService().dailyMALessThan95(wecIds, reportDate, maLessThan95Parameters);
//		Collections.sort(dataVos, DataVo.AREA_WEC);
//		return dataVos;
//	}
//
//	private Map<String, IWecParameterVo> getPreviousFiscalYearData() throws SQLException,
//			ParseException {
//		Set<Parameter> previousFiscalYearParameters = new LinkedHashSet<Parameter>();
//		previousFiscalYearParameters.add(Parameter.GENERATION);
//		return new ParameterEvaluator().getStateWiseWecParameterVo(DateUtility.getFirstDateOfFiscalYear(DateUtility.getFiscalYear(reportDate) - 1),
//				DateUtility.getBackwardDateInStringWRTMonths(reportDate, -12),
//				wecIds, previousFiscalYearParameters);
//	}
//
//	private Map<String, IWecParameterVo> getCurrentFiscalYearData() throws SQLException,
//			ParseException {
//		
//		Set<Parameter> currentFiscalYearParameters = new LinkedHashSet<Parameter>();
//		currentFiscalYearParameters.add(Parameter.GENERATION);
//		currentFiscalYearParameters.add(Parameter.REVENUE);
//		currentFiscalYearParameters.add(Parameter.FM_LOSS);
//		currentFiscalYearParameters.add(Parameter.LR_LOSS);
//
//		return new ParameterEvaluator().getStateWiseWecParameterVo(DateUtility.getFirstDateOfFiscalYear(DateUtility.getFiscalYear(reportDate)),reportDate, wecIds, currentFiscalYearParameters);
//	}
//
//	private Map<String, IWecParameterVo> getStateWiseWecParamterVo() throws SQLException {
//		Set<Parameter> dailyParameters = new LinkedHashSet<Parameter>();
//
//		dailyParameters.add(Parameter.GENERATION);
//		dailyParameters.add(Parameter.REVENUE);
//		dailyParameters.add(Parameter.FM_LOSS);
//		dailyParameters.add(Parameter.LR_LOSS);
//		dailyParameters.add(Parameter.CF);
//		dailyParameters.add(Parameter.MA);
//		dailyParameters.add(Parameter.GA);
//
//		return new ParameterEvaluator().getStateWiseWecParameterVo(reportDate, wecIds, dailyParameters);
//	}
//
//	public GenerationReport(String customerFullName, String customerShortName,
//			Set<String> customerIds, String reportDate) throws SQLException,
//			ParseException {
//		setCustomerFullName(customerFullName);
//		setCustomerShortName(customerShortName);
//		setCustomerIds(customerIds);
//		this.reportDate = reportDate;
//		// setReadingDate("21-SEP-2015");
//		initialise("21-SEP-2015");
//		// System.out.println(this);
//	}
//
//	public double getTotalWecCapacity() {
//		return totalWecCapacity;
//	}
//
//	public String getCustomerFullName() {
//		return customerFullName;
//	}
//
//	public void setCustomerFullName(String customerFullName) {
//		this.customerFullName = customerFullName;
//	}
//
//	public String getCustomerShortName() {
//		return customerShortName;
//	}
//
//	public void setCustomerShortName(String customerShortName) {
//		this.customerShortName = customerShortName;
//	}
//
//	public Set<String> getCustomerIds() {
//		return customerIds;
//	}
//
//	public void setCustomerIds(Set<String> customerIds) {
//		this.customerIds = customerIds;
//	}
//
//	public String getReportDate() {
//		return reportDate;
//	}
//
//	public GenerationReport setReportDate(String readingDate) {
//		this.reportDate = readingDate;
//		return this;
//	}
//
//	public static void main(String[] args) {
//		
//		CallSchedulerForSendingMail callSchedulerForSendingMail = new CallSchedulerForSendingMail();
//		
//		callSchedulerForSendingMail.sendIPPGroupMail("28-SEP-2015");
//		
//		ApplicationContext ac = new ClassPathXmlApplicationContext("com/enercon/spring/bean/bean-config.xml");
//		
//		String date = "28-SEP-2015";
//		for(int i = 1; i <= 7; i++){
//			GenerationReport gr = (GenerationReport) ac.getBean("generationReport" + i);
//			try {
//				gr.initialise(date);
//			} catch (ParseException e) {
//				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//			} catch (SQLException e) {
//				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//			}
//			
//			try {
//				callSchedulerForSendingMail.sendMail(new ArrayList<GenerationReport>(Arrays.asList(gr)));
//			} catch (SQLException e) {
//				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//			} catch (Exception e) {
//				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
//			}
//		}
//	}
//	
//	
//
//	public Set<String> getWecIds() {
//		return wecIds;
//	}
//
//	public void setWecIds(Set<String> wecIds) {
//		this.wecIds = wecIds;
//	}
//
//	public Map<String, IWecParameterVo> getStateWiseReportDateWecParameterVo() {
//		return stateWiseReportDateWecParameterVo;
//	}
//
//	public void setStateWiseReportDateWecParameterVo(
//			Map<String, IWecParameterVo> stateWiseReportDateWecParameterVo) {
//		this.stateWiseReportDateWecParameterVo = stateWiseReportDateWecParameterVo;
//	}
//
//	public Map<String, IWecParameterVo> getStateWiseCurrentFiscalYearWecParameterVo() {
//		return stateWiseCurrentFiscalYearWecParameterVo;
//	}
//
//	public void setStateWiseCurrentFiscalYearWecParameterVo(
//			Map<String, IWecParameterVo> stateWiseCurrentFiscalYearWecParameterVo) {
//		this.stateWiseCurrentFiscalYearWecParameterVo = stateWiseCurrentFiscalYearWecParameterVo;
//	}
//
//	public Map<String, IWecParameterVo> getStateWisePreviousFiscalYearWecParameterVo() {
//		return stateWisePreviousFiscalYearWecParameterVo;
//	}
//
//	public void setStateWisePreviousFiscalYearWecParameterVo(
//			Map<String, IWecParameterVo> stateWisePreviousFiscalYearWecParameterVo) {
//		this.stateWisePreviousFiscalYearWecParameterVo = stateWisePreviousFiscalYearWecParameterVo;
//	}
//
//	public List<DataVo> getDataForMALessThan95() {
//		return dataForMALessThan95;
//	}
//
//	public void setDataForMALessThan95(List<DataVo> dataForMALessThan95) {
//		this.dataForMALessThan95 = dataForMALessThan95;
//	}
//
//	public List<DataVo> getDataForMALessThan98() {
//		return dataForMALessThan98;
//	}
//
//	public void setDataForMALessThan98(List<DataVo> dataForMALessThan98) {
//		this.dataForMALessThan98 = dataForMALessThan98;
//	}
//
//	public IWecParameterVo getReportDateTotal() {
//		return reportDateTotal;
//	}
//
//	public void setReportDateTotal(IWecParameterVo reportDateTotal) {
//		this.reportDateTotal = reportDateTotal;
//	}
//
//	public IWecParameterVo getCurrentFYTotal() {
//		return currentFYTotal;
//	}
//
//	public void setCurrentFYTotal(IWecParameterVo currentFYTotal) {
//		this.currentFYTotal = currentFYTotal;
//	}
//
//	public void setTotalWecCapacity(double totalWecCapacity) {
//		this.totalWecCapacity = totalWecCapacity;
//	}
//
//	public IWecParameterVo getPreviousFYTotal() {
//		return previousFYTotal;
//	}
//
//	public void setPreviousFYTotal(IWecParameterVo previousFYTotal) {
//		this.previousFYTotal = previousFYTotal;
//	}
//	
//}
