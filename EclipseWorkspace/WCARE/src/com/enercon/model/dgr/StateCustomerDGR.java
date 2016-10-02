package com.enercon.model.dgr;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.model.comparator.SiteStateCustomerDGRComparator;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.WecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.service.SiteMasterService;

public class StateCustomerDGR {
	private static Logger logger = Logger.getLogger(StateCustomerDGR.class);
	
	//Input
	private IStateMasterVo state;
	private ICustomerMasterVo customer;
	private String fromDate;
	private String toDate;
	private List<Parameter> parameters;
	
	//Output
	private List<SiteStateCustomerDGR> sitesData = new ArrayList<SiteStateCustomerDGR>();
	private IWecParameterVo total;
	private boolean trial;
	
	public StateCustomerDGR(IStateMasterVo state, ICustomerMasterVo customer, String fromDate, String toDate, List<Parameter> parameters) {
		this.state = state;
		this.customer = customer;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.parameters = parameters;
	}
	
	public StateCustomerDGR doTotal(){
		for (SiteStateCustomerDGR site : sitesData) {
			IWecParameterVo siteTotal = site.getTotal();
			if(siteTotal != null){
				if(total == null) total = new WecParameterVo();
				total.add(siteTotal);
			}
			
		}
		return this;
	}
	
	public StateCustomerDGR doSiteDGR() throws SQLException, ParseException{
		SiteMasterService siteService = SiteMasterService.getInstance();
		for(ISiteMasterVo site: siteService.getActive(customer, state)){
			sitesData.add(new SiteStateCustomerDGR(site, state, customer, fromDate, toDate, parameters).doTotal());
		}
		return trial();
	}

	public List<SiteStateCustomerDGR> getSitesTotal(){
		return sitesData;
	}
	
	public IWecParameterVo getTotal(){
		return total;
	}
	
	public IStateMasterVo getState(){
		return state;
	}
	
	public ICustomerMasterVo getCustomer(){
		return customer;
	}
	
	public boolean getTrial(){
		return trial;
	}
	
	public StateCustomerDGR trial(){
		for(SiteStateCustomerDGR siteDGR: sitesData){
			if(siteDGR.getTrial()) {
				trial = true;
				return this;
			}
		}
		trial = false;
		return this;
	}
	
	public StateCustomerDGR sortBySite(){
		Collections.sort(sitesData, SiteStateCustomerDGRComparator.BY_SITE_NAME);
		return this;
	}
}
