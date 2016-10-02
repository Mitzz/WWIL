package com.enercon.model.dgr;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.enercon.global.utility.WecDgrShow;
import com.enercon.global.utility.WecParameterUtility;
import com.enercon.model.comparator.WecMasterVoComparator;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.eb.EbParameterData;
import com.enercon.model.parameter.eb.IEbParameterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.WecParameterEvaluator;
import com.enercon.model.utility.EbDetails;
import com.enercon.service.WecMasterService;
import com.enercon.service.parameter.EbParameterEvaluator;

public class EbDgr{
	private static Logger logger = Logger.getLogger(EbDgr.class);
	
	//Input
	/*private String fromDate;
	private String toDate;*/
	private ICustomerMasterVo customer;
	private IEbMasterVo eb;
	/*private Set<Parameter> wecParameters;
	private Set<EbParameter> ebParameters;*/
	
	//Output
	private IWecParameterVo total;
	private EbDetails ebDetails;
	private List<IWecMasterVo> noConnectivityWecs;
	
	//Outsider
	private Map<IWecMasterVo, IWecParameterVo> wecsData = new TreeMap<IWecMasterVo, IWecParameterVo>(WecMasterVoComparator.BY_NAME);
	private IEbParameterVo ebData;
	
	//Local
	private Set<IWecMasterVo> wecs;
	private WecParameterEvaluator wecParameterEvaluator = WecParameterEvaluator.getInstance();
	private WecParameterData wecParameterData = new WecParameterData();
	private EbParameterData ebParameterData = new EbParameterData();
	
	public EbDgr(String date, ICustomerMasterVo customer, IEbMasterVo eb) {
		/*this.fromDate = date;
		this.toDate = date;*/
		this.customer = customer;
		this.eb = eb;

		setUp();
	}
	
	public EbDgr(String fromDate, String toDate, ICustomerMasterVo customer, IEbMasterVo eb) {
		/*this.fromDate = fromDate;
		this.toDate = toDate;*/
		this.customer = customer;
		this.eb = eb;

		setUp();
	}
	
	private void setUp() {
		setWecs();
//		setWecParameterData();
//		setEbParameterData();
	}

	/*private void setEbParameterData() {
		ebParameterData.setFromDate(fromDate);
		ebParameterData.setToDate(toDate);
		ebParameterData.setParameters(ebParameters);
		ebParameterData.setDataCheck(true);
		Set<IEbMasterVo> ebs = new HashSet<IEbMasterVo>();
		ebs.add(eb);
		ebParameterData.setEbs(ebs);
	}*/

	private void setWecs() {
		wecs = new HashSet<IWecMasterVo>(WecMasterService.getInstance().getDisplayActive(eb, customer));		
	}

	/*private void setWecParameterData() {
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setWecParameters(wecParameters);
		wecParameterData.setDataCheck(true);
		wecParameterData.setWecs(new HashSet<IWecMasterVo>(wecs));
	}*/

	/*public EbDgr populateWecParameterData() throws SQLException{
		wecsData.putAll(wecParameterEvaluator.getWecWise(wecParameterData));
		checkWecsConnectivity();
		return this;
	}*/
	
	private void checkWecsConnectivity() {
		noConnectivityWecs = new ArrayList<IWecMasterVo>();
		WecDgrShow dgr = new WecDgrShow();
		for(IWecMasterVo wec: wecsData.keySet()){
			if(!dgr.setWecParameterVo(wecsData.get(wec)).isShow()){
				noConnectivityWecs.add(wec);
			}
		}
	}
	
	public List<IWecMasterVo> getNoConnectivityWecs(){
		return noConnectivityWecs;
	}

	/*public EbDgr populateEbParameterData() throws SQLException, ParseException{
		EbParameterEvaluator ebEvaluator =  EbParameterEvaluator.getInstance();
		
		ebData = ebEvaluator.getTotal(ebParameterData);
		return this;
	}*/
	
	public EbDgr populateTotal(){
		total = WecParameterUtility.total(wecsData.values());
		return this;
	}
	
	public EbDgr populateDetails(){
		ebDetails = new EbDetails(eb);
		return this;
	}
	
	@Override
	public String toString() {
		return "EbDgr [ebDetails=" + ebDetails.getWecCountType() + "]";
	}
	
	public Map<IWecMasterVo, IWecParameterVo> getWecsData() {
		return wecsData;
	}
	
	public EbDgr setWecsData(Map<IWecMasterVo, IWecParameterVo> wecsData){
		this.wecsData.putAll(wecsData);
		checkWecsConnectivity();
		populateTotal();
		return this;
	}

	public ICustomerMasterVo getCustomer() {
		return customer;
	}

	public IEbMasterVo getEb() {
		return eb;
	}

	public IWecParameterVo getTotal() {
		return total;
	}

	public IEbParameterVo getEbData() {
		return ebData;
	}
	
	public EbDgr setEbData(IEbParameterVo ebData) {
		this.ebData = ebData;
		return this;
	}

	public double getCapacity(){
		return ebDetails.getCapacity();
	}
	
	public String getTypeCount(){
		return ebDetails.getWecCountType();
	}
	
	public Set<IWecMasterVo> getWecs(){
		return wecs;
	}
}
