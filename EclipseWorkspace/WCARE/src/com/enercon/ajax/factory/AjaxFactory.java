package com.enercon.ajax.factory;

import java.util.ArrayList;
import java.util.List;

import com.enercon.ajax.ajaxDataRetriver.AreaDataRetriverBasedOnCustomerId;
import com.enercon.ajax.ajaxDataRetriver.AreaDataRetriverBasedOnStateIds;
import com.enercon.ajax.ajaxDataRetriver.AreaDataRetriverBasedOnWecType;
import com.enercon.ajax.ajaxDataRetriver.IredaVoListRetrieverBasedOnDate;
import com.enercon.ajax.ajaxDataRetriver.IredaVoListRetrieverBasedOnFiscalYear;
import com.enercon.ajax.ajaxDataRetriver.IredaVoListRetrieverBasedOnMonthYear;
import com.enercon.ajax.ajaxDataRetriver.SiteDataRetriverBasedOnCustomerIdsAreaIds;
import com.enercon.ajax.ajaxDataRetriver.SiteDataRetriverBasedOnStateIdsAreaIds;
import com.enercon.ajax.ajaxDataRetriver.SiteDataRetriverBasedOnWecTypeAreaIds;
import com.enercon.ajax.ajaxDataRetriver.WecDataRetriverBasedOnCustomerIdsAreaIdsSiteIds;
import com.enercon.ajax.ajaxDataRetriver.WecDataRetriverBasedOnLocationNos;
import com.enercon.ajax.ajaxDataRetriver.WecDataRetriverBasedOnProjectIds;
import com.enercon.ajax.ajaxDataRetriver.WecDataRetriverBasedOnStateIdsAreaIdsSiteIds;
import com.enercon.ajax.ajaxDataRetriver.WecDataRetriverBasedOnWecTypeAreaIdsSiteIds;
import com.enercon.ajax.interfaces.AjaxDataRetriver;

public class AjaxFactory {

	public static AjaxDataRetriver getAjaxDataRetriver(String ajaxType){
		AjaxDataRetriver dataRetriver = null;
		List<String> requestParameterNames = new ArrayList<String>();
		if(ajaxType.equals("getAreaSelectVoBasedOnMultipleCustomerIds")){
			requestParameterNames.add("customerIds");
			dataRetriver = new AreaDataRetriverBasedOnCustomerId(requestParameterNames);
		}
		if(ajaxType.equals("getAreaSelectVoBasedOnMultipleWECType")){
			requestParameterNames.add("wecType");
			dataRetriver = new AreaDataRetriverBasedOnWecType(requestParameterNames);
		}
		if(ajaxType.equals("getAreaSelectVoBasedOnMultipleStateIds")){
			requestParameterNames.add("stateIds");
			dataRetriver = new AreaDataRetriverBasedOnStateIds(requestParameterNames);
		}
		
		if(ajaxType.equals("getSiteSelectVoBasedOnCustomerIdsAreaIds")){
			requestParameterNames.add("customerIds");
			requestParameterNames.add("areaIds");
			dataRetriver = new SiteDataRetriverBasedOnCustomerIdsAreaIds(requestParameterNames);
		}
		
		if(ajaxType.equals("getSiteSelectVoBasedOnWecTypeAreaIds")){
			requestParameterNames.add("wecType");
			requestParameterNames.add("areaIds");
			dataRetriver = new SiteDataRetriverBasedOnWecTypeAreaIds(requestParameterNames);
		}
		if(ajaxType.equals("getSiteSelectVoBasedOnStateIdsAreaIds")){
		
			requestParameterNames.add("stateIds");
			requestParameterNames.add("areaIds");
			dataRetriver = new SiteDataRetriverBasedOnStateIdsAreaIds(requestParameterNames);
		}
		if(ajaxType.equals("getWecSelectVoBasedOnCustomerIdsAreaIdsSiteIds")){
			requestParameterNames.add("customerIds");
			requestParameterNames.add("areaIds");
			requestParameterNames.add("siteIds");
			dataRetriver = new WecDataRetriverBasedOnCustomerIdsAreaIdsSiteIds(requestParameterNames);
		}
		if(ajaxType.equals("getWecSelectVoBasedOnWecTypeAreaIdsSiteIds")){
			requestParameterNames.add("wecType");
			requestParameterNames.add("areaIds");
			requestParameterNames.add("siteIds");
			dataRetriver = new WecDataRetriverBasedOnWecTypeAreaIdsSiteIds(requestParameterNames);
		}
		if(ajaxType.equals("getWecSelectVoBasedOnStateIdsAreaIdsSiteIds")){
			requestParameterNames.add("stateIds");
			requestParameterNames.add("areaIds");
			requestParameterNames.add("siteIds");
			dataRetriver = new WecDataRetriverBasedOnStateIdsAreaIdsSiteIds(requestParameterNames);
		}
		
		if(ajaxType.equals("getWecSelectVoBasedOnLocationIds")){
			requestParameterNames.add("locationIds");
			dataRetriver = new WecDataRetriverBasedOnLocationNos(requestParameterNames);
		}
		if(ajaxType.equals("getWecSelectVoBasedOnProjectIds")){
			requestParameterNames.add("projectIds");
			dataRetriver = new WecDataRetriverBasedOnProjectIds(requestParameterNames);
		}
		if(ajaxType.equals("getIREDAListVoBasedOnMultipleProjectNoAndMonthYear")){
			requestParameterNames.add("projectNos");
			requestParameterNames.add("month");
			requestParameterNames.add("year");
			
			dataRetriver = new IredaVoListRetrieverBasedOnMonthYear(requestParameterNames);
			
		}
		if(ajaxType.equals("getIREDAListVoBasedOnMultipleProjectNoAndFiscalYear")){
			requestParameterNames.add("projectNos");
			requestParameterNames.add("fiscalYear");
			
			dataRetriver = new IredaVoListRetrieverBasedOnFiscalYear(requestParameterNames);
			
		}
		
		if(ajaxType.equals("getIREDAListVoBasedOnMultipleProjectNoAndDate")){
			requestParameterNames.add("projectNos");
			requestParameterNames.add("date");
			
			dataRetriver = new IredaVoListRetrieverBasedOnDate(requestParameterNames);
			
		}
		
		return dataRetriver;
	}
}
