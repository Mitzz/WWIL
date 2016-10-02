package com.enercon.model.dgr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.model.comparator.CustomerSiteDGRComparator;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.WecParameterEvaluator;
import com.enercon.service.WecMasterService;

public class MPR {
	
	private static Logger logger = Logger.getLogger(MPR.class);

	//Input
	private String fromDate;
	private String toDate;
	private WecTypeMasterVo wecType;
	private IStateMasterVo state;
	private ISiteMasterVo site;
	private IEbMasterVo eb;
	private IWecMasterVo wec;
	
	//Output
	private List<CustomerSiteDGR> dgrs;
	
	//Local
	private WecParameterData parameterData;
	private Map<IWecMasterVo, IWecParameterVo> wecData;
	private Set<IWecMasterVo> wecs;
	
	public MPR(){
		
	}
	
	public MPR(String fromDate, String toDate, WecTypeMasterVo wecType, IStateMasterVo state, ISiteMasterVo site, IEbMasterVo eb, IWecMasterVo wec) throws SQLException {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.wecType = wecType;
		this.state = state;
		this.site = site;
		this.eb = eb;
		this.wec = wec;
		
		setUp();
	}

	private void setUpWecParameterData(){
		parameterData = new WecParameterData();
		Set<Parameter> wecParameters = new HashSet<Parameter>();
		
		wecParameters.add(Parameter.GENERATION);
		wecParameters.add(Parameter.OPERATING_HOUR);
		wecParameters.add(Parameter.LULL_HOUR);
		wecParameters.add(Parameter.MF);
		wecParameters.add(Parameter.MS);
		wecParameters.add(Parameter.GIF);
		wecParameters.add(Parameter.GEF);
		wecParameters.add(Parameter.GIS);
		wecParameters.add(Parameter.GES);
		wecParameters.add(Parameter.EB_LOAD);
		wecParameters.add(Parameter.FM);
		wecParameters.add(Parameter.MA);
		wecParameters.add(Parameter.CF);
		wecParameters.add(Parameter.GA);
		
		parameterData.setFromDate(fromDate).setToDate(toDate).setWecs(wecs).setParameters(wecParameters).setPublish(1);
	}
	
	private void setUp() throws SQLException {
		setUpWecs();
		setUpWecParameterData();
		setUpWecData();
		setUpSiteCustomer();
	}

	private void setUpWecData() throws SQLException {
		WecParameterEvaluator instance = WecParameterEvaluator.getInstance();
		wecData = instance.getWecWise(parameterData);
	}

	private void setUpSiteCustomer() {
		dgrs = new ArrayList<CustomerSiteDGR>();
		
		//For Determining Distinct (Site, Customer)
		Map<ISiteMasterVo, Set<ICustomerMasterVo>> siteCustomersM = new HashMap<ISiteMasterVo, Set<ICustomerMasterVo>>();
		
		//For Adding wecs to a particular (Site, Customer)
		Map<CustomerSiteDGR, List<IWecMasterVo>> dgrWecsM = new HashMap<CustomerSiteDGR, List<IWecMasterVo>>();
		
		for(IWecMasterVo wec: wecs){
			ICustomerMasterVo customer = wec.getCustomer();
			ISiteMasterVo site = wec.getSite();
			
			if(siteCustomersM.containsKey(site)){
				
				//DGR already present
				if(siteCustomersM.get(site).contains(customer)){
					CustomerSiteDGR presentDgr = new CustomerSiteDGR(customer, site);
					dgrWecsM.get(presentDgr).add(wec);
				} else {
					siteCustomersM.get(site).add(customer);
					
					CustomerSiteDGR newDgr = new CustomerSiteDGR(customer, site);
					ArrayList<IWecMasterVo> dgrWecs = new ArrayList<IWecMasterVo>();
					dgrWecs.add(wec);
					dgrWecsM.put(newDgr, dgrWecs);
					dgrs.add(newDgr);
				}
			} else {
				Set<ICustomerMasterVo> customers = new HashSet<ICustomerMasterVo>();
				customers.add(customer);
				siteCustomersM.put(site, customers);
				
				CustomerSiteDGR newDgr = new CustomerSiteDGR(customer, site);
				ArrayList<IWecMasterVo> dgrWecs = new ArrayList<IWecMasterVo>();
				dgrWecs.add(wec);
				dgrWecsM.put(newDgr, dgrWecs);
				dgrs.add(newDgr);
			}
		}
		
		Collections.sort(dgrs, CustomerSiteDGRComparator.BY_STATE_SITE_CUSTOMER);
		logger.debug(dgrs.size());
		
		logger.debug("S1");
		for(CustomerSiteDGR customerSiteDGR: dgrWecsM.keySet()){
			customerSiteDGR.setWecs(dgrWecsM.get(customerSiteDGR)).capacity();
			Map<IWecMasterVo, IWecParameterVo> wecParameterData = new HashMap<IWecMasterVo, IWecParameterVo>();
			
			for(IWecMasterVo wec: customerSiteDGR.getWecs()){
				wecParameterData.put(wec, wecData.get(wec));
			}
			customerSiteDGR.setWecData(wecParameterData).doTotal();
		}
		logger.debug("W1");
		
		logger.debug("S2");
		
		/*for(ISiteMasterVo site: siteCustomer.keySet()){
			Set<ICustomerMasterVo> customers = siteCustomer.get(site);
			for(ICustomerMasterVo customer: customers){
				Map<IWecMasterVo, IWecParameterVo> data = new HashMap<IWecMasterVo, IWecParameterVo>();
				for(IWecMasterVo s: wecs){
					if(s.getCustomer().equals(customer) && s.getSite().equals(site)) {
						data.put(s, wecData.get(s));
					}
				}
//				dgrs.add(new CustomerSiteDGR(customer, site, data).capacity());
			}
		}*/
		
		logger.debug("W2");
	}

	private void setUpWecs() {
		WecMasterService wecService = WecMasterService.getInstance();
		
		//Only Wec Type
		if(wecType != null && state == null && site == null && eb == null && wec == null){
			wecs = new HashSet<IWecMasterVo>(wecService.getActiveTransferred(wecType.getDescription()));
		} 
		//WecType and State
		else if(wecType != null && state != null && site == null && eb == null && wec == null){
			wecs = new HashSet<IWecMasterVo>(wecService.getActiveTransferred(state, wecType.getDescription()));
		}
		//Only State
		else if(wecType == null && state != null && site == null && eb == null && wec == null){
			wecs = new HashSet<IWecMasterVo>(wecService.getActiveTransferred(state));
		}
		//WecType and Site
		else if(wecType != null && state != null && site != null && eb == null && wec == null){
			wecs = new HashSet<IWecMasterVo>(wecService.getActiveTransferred(site.getState(), site, wecType.getDescription()));
		}
		//Eb
		else if(wecType == null && state == null && site == null && eb != null && wec == null){
			wecs = new HashSet<IWecMasterVo>(wecService.getActiveTransferred(eb));
		}
		//Wec
		else if(wecType == null && state == null && site == null && eb == null && wec != null){
			wecs = new HashSet<IWecMasterVo>();
			wecs.add(wec);
		}
		
		logger.debug("Wec Size: " + wecs.size());
		
	}

	public MPR populateWecTypeData(){
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

	public WecTypeMasterVo getWecType() {
		return wecType;
	}

	public void setWecType(WecTypeMasterVo wecType) {
		this.wecType = wecType;
	}
	
	public static void main(String[] args) {
		try {
			MPR mpr = new MPR();
			/*for(CustomerSiteDGR dgr: mpr.dgrs){
				for(IWecMasterVo wec: dgr.getWecs()){
					logger.debug(String.format("%s: %s: %s: %s", dgr.getCustomer().getName(), dgr.getSite().getName(), wec.getName(), dgr.wecData.get(wec)));
				}
			}*/
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
	}

	public List<CustomerSiteDGR> getDgrs() {
		return dgrs;
	}
	
	
	
}
