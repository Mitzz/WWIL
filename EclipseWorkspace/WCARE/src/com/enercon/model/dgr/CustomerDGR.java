package com.enercon.model.dgr;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.model.comparator.StateCustomerDGRComparator;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.WecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.service.StateMasterService;

//Total Problem
//Data Present Check in Wec Reading Summary
//Add in WecParameterVo
//Comparator for CUstomerStateDgr
//Only Active Wecs are considered
//If any one wec is in trial than whole bunch is in trial

//Mistakes
//considered all wecs instead of active wecs

//Challenges
//Data present check for Wec Reading
//Modelling for DGR
//Changes in WecDataDao which is very important
//Splitting of wec in WecDataDao becoz of 1000 limit
//Adding MA, GA and so which is the average
//Navigation for one jsp to other jsp

//Learn
//Corner Cases
//1. Data Present check at each level(Customer, State and Site) and using total for data present
//2. Convert String Date to check commission date comparison to current date
//3. Sorting by State and Site using Comparator
//JSP conversion
//1. Input parameter
//2. Output with all corner cases
public class CustomerDGR {
	private static Logger logger = Logger.getLogger(CustomerDGR.class);

	//Input
	private ICustomerMasterVo customer;
	private String fromDate;
	private String toDate;
	private List<Parameter> parameters;
	
	//Output
	private List<StateCustomerDGR> statesData = new ArrayList<StateCustomerDGR>();
	private IWecParameterVo total;
	private boolean trial;
	
	public CustomerDGR(ICustomerMasterVo customer, String fromDate, String toDate, List<Parameter> parameters) {
		this.customer = customer;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.parameters = parameters;
	}

	public CustomerDGR doStateDGR() throws SQLException, ParseException{
		StateMasterService stateService = StateMasterService.getInstance();
		
		for(IStateMasterVo state: stateService.getActive(customer)){
			statesData.add(new StateCustomerDGR(state, customer, fromDate, toDate, parameters).doSiteDGR().doTotal().sortBySite());
		}
		return this;
	}
	
	public CustomerDGR doTotal(){
		for(StateCustomerDGR stateDetail: statesData){
			IWecParameterVo stateTotal = stateDetail.getTotal();
			if(stateTotal != null){
				if(total == null) total = new WecParameterVo();
				total.add(stateTotal);
			}
		}
		return trial();
	}
	
	public List<StateCustomerDGR> getStatesTotal(){
		return statesData;
	}
	
	public IWecParameterVo getTotal(){
		return total;
	}
	
	public boolean getTrial(){
		return trial;
	}
	
	public CustomerDGR trial(){
		for(StateCustomerDGR stateDGR: statesData){
			if(stateDGR.getTrial()) {
				trial = true;
				return this;
			}
		}
		trial = false;
		return this;
	}
	
	public CustomerDGR sortStateByName(){
		Collections.sort(statesData, StateCustomerDGRComparator.BY_STATE_NAME);
		return this;
	}
}
