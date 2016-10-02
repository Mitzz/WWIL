package com.enercon.customer.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.enercon.admin.dao.AdminDao;
import com.enercon.admin.metainfo.CustomerMetaInfo;
import com.enercon.admin.metainfo.EBMetaInfo;
import com.enercon.admin.metainfo.SiteMetaInfo;
import com.enercon.admin.metainfo.StateMetaInfo;
import com.enercon.admin.metainfo.WECMetaInfo;
import com.enercon.connection.WcareConnector;
import com.enercon.customer.dao.CustomerSQLC;
import com.enercon.dao.DaoUtility;
import com.enercon.dao.master.WecMasterDao;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.NumberUtility;
import com.enercon.global.utility.TimeUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.datatype.WEC;
import com.enercon.sqlQuery.sqlQueryDao.FindingQueryDao;
import com.enercon.sqlQuery.sqlQueryDao.InfoQueryDao;

public class CustomerUtility implements WcareConnector{
	
	private static Logger logger = Logger.getLogger(CustomerUtility.class);
	
	public static List<String> getCustomerMetaInfoBasedOnEbId(String ebId) {
		List<String> customerMetaInfo = new ArrayList<String>();
		List<String> s = CustomerMetaInfo.getCustomerMetaInfoBasedOnEbId(ebId);
		customerMetaInfo.add(s.get(0));
		customerMetaInfo.add(s.get(1));
		customerMetaInfo.add(s.get(2));
		customerMetaInfo.add(s.get(3));
		customerMetaInfo.add(s.get(6));
		customerMetaInfo.add(s.get(7));
		customerMetaInfo.add(s.get(11));
		customerMetaInfo.add(s.get(14));
		return customerMetaInfo;
	}
	
	public static ArrayList<ArrayList<Object>> getOneWECTotalForBetweenDaysBasedOnEbId(String ebId, String fromDate, String toDate){
		ArrayList<ArrayList<Object>> ebWECWiseTotalTranList = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> ebWECWiseTotal = EBMetaInfo.getOneEBWECWiseTotalForBetweenDays(ebId, fromDate, toDate); 
		ArrayList<Object> tranList = new ArrayList<Object>();
		for(ArrayList<Object> wecTotal: ebWECWiseTotal){
		if(wecTotal.size() == 2){
			tranList.add((String)wecTotal.get(0));
			tranList.add((String)wecTotal.get(1));
		}
		else{
		tranList.add((String)wecTotal.get(0));
		tranList.add((String)wecTotal.get(1));
		tranList.add((String)wecTotal.get(2));
		tranList.add((String)wecTotal.get(3));
		tranList.add((Integer)wecTotal.get(4));
		tranList.add((String)wecTotal.get(5));
		tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(6))));
		tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(7))));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(8), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(9), ":"));
		tranList.add((String)wecTotal.get(10));
		tranList.add((java.sql.Date)wecTotal.get(11));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(12), ":"));
		tranList.add(wecTotal.get(13));
		tranList.add(wecTotal.get(14));
		tranList.add(wecTotal.get(15));
		tranList.add(wecTotal.get(16));
		tranList.add(wecTotal.get(17));
		tranList.add(wecTotal.get(18));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(19), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(20), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(21), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(22), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(23), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(24), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(25), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(26), ":"));
		long gridFault = (Long)wecTotal.get(22) + (Long)wecTotal.get(24); 
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
		long gridShutdown = (Long)wecTotal.get(23) + (Long)wecTotal.get(25);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
		long faultHours = 	(Long)wecTotal.get(12) + (Long)wecTotal.get(19) + (Long)wecTotal.get(20) + 
							(Long)wecTotal.get(21) + (Long)wecTotal.get(22) + (Long)wecTotal.get(23) + 
							(Long)wecTotal.get(24) + (Long)wecTotal.get(25) + (Long)wecTotal.get(26)+(Long)wecTotal.get(27);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));
		}
		ebWECWiseTotalTranList.add(tranList);
		tranList = new ArrayList<Object>();
	}
	return ebWECWiseTotalTranList;
}
	
	public static List<Object> getOneSiteTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerId(String siteId, String customerId, String fromDate,
			String toDate, String wecType) {
		List<Object> tranList = new ArrayList<Object>();
		List<Object> siteTotal = new ArrayList<Object>();
		siteTotal = SiteMetaInfo.getOneSiteTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerId(wecType, siteId, customerId, DateUtility.convertDateFormats(fromDate, "dd/MM/yyyy", "dd-MMM-yyyy"), DateUtility.convertDateFormats(toDate, "dd/MM/yyyy", "dd-MMM-yyyy"));
		Vector<Object> v = new Vector<Object>();
		//v.add(siteTotal.get(1));
		v.add(NumberUtility.formatNumber(((Long)siteTotal.get(6))));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(8), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(12), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(19), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(20), ":"));
		long gridFault = (Long)siteTotal.get(22) + (Long)siteTotal.get(24); 
		v.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
		long gridShutdown = (Long)siteTotal.get(23) + (Long)siteTotal.get(25);
		v.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
		long faultHours = 	(Long)siteTotal.get(12) + (Long)siteTotal.get(19) + (Long)siteTotal.get(20) + 
							(Long)siteTotal.get(21) + (Long)siteTotal.get(22) + (Long)siteTotal.get(23) + 
							(Long)siteTotal.get(24) + (Long)siteTotal.get(25) + (Long)siteTotal.get(26)+(Long)siteTotal.get(27);
		v.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));
		v.add(siteTotal.get(13));
		v.add(siteTotal.get(15));
		v.add(siteTotal.get(16));
		v.add(siteTotal.get(14));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(21), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(26), ":"));
		tranList.add(v);

		return tranList;
		
	}
	
	public static List<Object> getOneSiteWECWiseTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerId(String siteId, String customerId, String fromDate,
			String toDate, String wecType) {
		List<Object> wecTotalDetailSiteWiseCustomerWise = new ArrayList<Object>();
		List<ArrayList<Object>> customerDetails = new ArrayList<ArrayList<Object>>();
		customerDetails = SiteMetaInfo.getOneSiteWECWiseTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerId(wecType, siteId, customerId, DateUtility.convertDateFormats(fromDate, "dd/MM/yyyy", "dd-MMM-yyyy"), DateUtility.convertDateFormats(toDate, "dd/MM/yyyy", "dd-MMM-yyyy"));
		for (ArrayList<Object> wecTotal : customerDetails) {
			Vector<Object> v = new Vector<Object>();
			if(wecTotal.size() == 2){
				v.add(wecTotal.get(0));
				v.add(wecTotal.get(1));
			}
			else{
				v.add(wecTotal.get(1));
				v.add(NumberUtility.formatNumber(((Long)wecTotal.get(6))));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(8), ":"));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(12), ":"));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(19), ":"));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(20), ":"));
				long gridFault = (Long)wecTotal.get(22) + (Long)wecTotal.get(24); 
				v.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
				long gridShutdown = (Long)wecTotal.get(23) + (Long)wecTotal.get(25);
				v.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
				
				long faultHours = 	(Long)wecTotal.get(12) + (Long)wecTotal.get(19) + (Long)wecTotal.get(20) + 
									(Long)wecTotal.get(21) + (Long)wecTotal.get(22) + (Long)wecTotal.get(23) + 
									(Long)wecTotal.get(24) + (Long)wecTotal.get(25) + (Long)wecTotal.get(26)+(Long)wecTotal.get(27);
				v.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));
				v.add(wecTotal.get(13));
				v.add(wecTotal.get(15));
				v.add(wecTotal.get(16));
				v.add(wecTotal.get(14));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(21), ":"));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(26), ":"));
			}
			wecTotalDetailSiteWiseCustomerWise.add(v);
		}
		return wecTotalDetailSiteWiseCustomerWise;
		
	}
	
	public static List<Object> customerInfoBasedonSiteIdWECType(String siteId,
			String wecType) {
		List<Object> customerDetails = new ArrayList<Object>();
		customerDetails = CustomerMetaInfo.getCustomerInfoBasedOnSiteIdWECType(siteId, wecType);
		return customerDetails;
	}
	
	public static List<Object> getWECTypeCapacityBasedOnSiteId(String siteId){
		List<Object> wecTypeCapacity = new ArrayList<Object>();
		Vector<String> v = new Vector<String>();
		for(String str : WecMasterDao.getWecTypeCapacityBasedOnSiteId(siteId)){
			v.add(str);
			wecTypeCapacity.add(v);
			v = new Vector<String>(); 
		}
		return wecTypeCapacity;
	}
	
	public static ArrayList<Object> getOneSiteTotalForBetweenDaysBasedOnSiteIdCustomerIdMeta(String siteId, String customerId, String fromDate,
			String toDate) {
		ArrayList<Object> tranList = new ArrayList<Object>();
		List<Object> siteTotal = new ArrayList<Object>();
		siteTotal = SiteMetaInfo.getOneSiteTotalForBetweenDaysBasedOnSiteIdCustomerId(siteId, customerId, fromDate, toDate);
		tranList.add((String)siteTotal.get(0));
		tranList.add((String)siteTotal.get(1));
		tranList.add((String)siteTotal.get(2));
		tranList.add((String)siteTotal.get(3));
		tranList.add((String)siteTotal.get(4));
		tranList.add((String)siteTotal.get(5));
		tranList.add(NumberUtility.formatNumber(((Long)siteTotal.get(6))));
		tranList.add((String)siteTotal.get(7));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(8), ":"));
		tranList.add((String)siteTotal.get(9));
		tranList.add((String)siteTotal.get(10));;
		tranList.add((String)siteTotal.get(11));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(12), ":"));
		tranList.add(siteTotal.get(13));
		tranList.add(siteTotal.get(14));
		tranList.add(siteTotal.get(15));
		tranList.add(siteTotal.get(16));
		tranList.add(siteTotal.get(17));
		tranList.add(siteTotal.get(18));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(19), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(20), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(21), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(22), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(23), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(24), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(25), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(26), ":"));
		long gridFault = (Long)siteTotal.get(22) + (Long)siteTotal.get(24); 
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
		long gridShutdown = (Long)siteTotal.get(23) + (Long)siteTotal.get(25);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
		long faultHours = 	(Long)siteTotal.get(12) + (Long)siteTotal.get(19) + (Long)siteTotal.get(20) + 
							(Long)siteTotal.get(21) + (Long)siteTotal.get(22) + (Long)siteTotal.get(23) + 
							(Long)siteTotal.get(24) + (Long)siteTotal.get(25) + (Long)siteTotal.get(26)+(Long)siteTotal.get(27);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));

		return tranList;
		
	}
	
	public static ArrayList<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerIdMeta(String siteId, String customerId, String fromDate,
			String toDate) {
		ArrayList<ArrayList<Object>> siteWECWiseTotalTranList = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> siteWECWiseTotal = SiteMetaInfo.getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerIdChange(siteId, customerId, fromDate, toDate); 
		ArrayList<Object> tranList = new ArrayList<Object>();
		for(ArrayList<Object> wecTotal: siteWECWiseTotal){
		if(wecTotal.size() == 2){
			tranList.add((String)wecTotal.get(0));
			tranList.add((String)wecTotal.get(1));
		}
		else{
			tranList.add((String)wecTotal.get(0));
			tranList.add((String)wecTotal.get(1));
			tranList.add((String)wecTotal.get(2));
			tranList.add((String)wecTotal.get(3));
			tranList.add((Integer)wecTotal.get(4));
			tranList.add((String)wecTotal.get(5));
			tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(6))));
			tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(7))));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(8), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(9), ":"));
			tranList.add((String)wecTotal.get(10));
			tranList.add((java.sql.Date)wecTotal.get(11));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(12), ":"));
			tranList.add(wecTotal.get(13));
			tranList.add(wecTotal.get(14));
			tranList.add(wecTotal.get(15));
			tranList.add(wecTotal.get(16));
			tranList.add(wecTotal.get(17));
			tranList.add(wecTotal.get(18));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(19), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(20), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(21), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(22), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(23), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(24), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(25), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(26), ":"));
			long gridFault = (Long)wecTotal.get(22) + (Long)wecTotal.get(24); 
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
			long gridShutdown = (Long)wecTotal.get(23) + (Long)wecTotal.get(25);
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
			long faultHours = 	(Long)wecTotal.get(12) + (Long)wecTotal.get(19) + (Long)wecTotal.get(20) + 
								(Long)wecTotal.get(21) + (Long)wecTotal.get(22) + (Long)wecTotal.get(23) + 
								(Long)wecTotal.get(24) + (Long)wecTotal.get(25) + (Long)wecTotal.get(26)+(Long)wecTotal.get(27);
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));
		}
		siteWECWiseTotalTranList.add(tranList);
		tranList = new ArrayList<Object>();
		}
		return siteWECWiseTotalTranList;
		
	}
	
	public static List<String> getCustomerMetaInfoBasedOnWECId(String wecId) {
		List<String> customerMetaInfo = new ArrayList<String>();
		List<String> s = CustomerMetaInfo.getCustomerMetaInfoBasedOnWECId(wecId);
		customerMetaInfo.add(s.get(0));
		customerMetaInfo.add(s.get(1));
		customerMetaInfo.add(s.get(2));
		customerMetaInfo.add(s.get(3));
		customerMetaInfo.add(s.get(6));
		customerMetaInfo.add(s.get(7));
		customerMetaInfo.add(s.get(11));
		customerMetaInfo.add(s.get(14));
		return customerMetaInfo;
	}
	
	
	public static List<Object> getOneWECTotalForBetweenDaysBasedOnWECId(String wecId, String fromDate, String toDate){
		List<Object> wecTotal = WECMetaInfo.getOneWECTotalForBetweenDays(wecId, fromDate, toDate);
		//WECMetaInfo.getOneWECTotalForBetweenDaysMeta(wecId, fromDate, toDate);
		List<Object> tranList = new ArrayList<Object>();
		if(wecTotal.size() == 2){
			tranList.add((String)wecTotal.get(0));
			tranList.add((String)wecTotal.get(1));
			return wecTotal;
		}
		tranList.add((String)wecTotal.get(0));
		tranList.add((String)wecTotal.get(1));
		tranList.add((String)wecTotal.get(2));
		tranList.add((String)wecTotal.get(3));
		tranList.add((Integer)wecTotal.get(4));
		tranList.add((String)wecTotal.get(5));
		tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(6))));
		tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(7))));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(8), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(9), ":"));
		tranList.add((String)wecTotal.get(10));
		tranList.add((java.sql.Date)wecTotal.get(11));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(12), ":"));
		tranList.add(wecTotal.get(13));
		tranList.add(wecTotal.get(14));
		tranList.add(wecTotal.get(15));
		tranList.add(wecTotal.get(16));
		tranList.add(wecTotal.get(17));
		tranList.add(wecTotal.get(18));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(19), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(20), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(21), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(22), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(23), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(24), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(25), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(26), ":"));
		long gridFault = (Long)wecTotal.get(22) + (Long)wecTotal.get(24); 
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
		long gridShutdown = (Long)wecTotal.get(23) + (Long)wecTotal.get(25);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
		long faultHours = 	(Long)wecTotal.get(12) + (Long)wecTotal.get(19) + (Long)wecTotal.get(20) + 
							(Long)wecTotal.get(21) + (Long)wecTotal.get(22) + (Long)wecTotal.get(23) + 
							(Long)wecTotal.get(24) + (Long)wecTotal.get(25) + (Long)wecTotal.get(26)+(Long)wecTotal.get(27);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));
		
		return tranList;
	}
	
	public static ArrayList<Object> getOneSiteTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMeta(String siteId, String customerId, String fromDate,
			String toDate, String wecType) {
		ArrayList<Object> tranList = new ArrayList<Object>();
		List<Object> siteTotal = new ArrayList<Object>();
		siteTotal = SiteMetaInfo.getOneSiteTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMPR(wecType, siteId, customerId, fromDate, toDate);
		tranList.add((String)siteTotal.get(0));
		tranList.add((String)siteTotal.get(1));
		tranList.add((String)siteTotal.get(2));
		tranList.add((String)siteTotal.get(3));
		tranList.add((String)siteTotal.get(4));
		tranList.add((String)siteTotal.get(5));
		tranList.add(NumberUtility.formatNumber(((Long)siteTotal.get(6))));
		tranList.add((String)siteTotal.get(7));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(8), ":"));
		tranList.add((String)siteTotal.get(9));
		tranList.add((String)siteTotal.get(10));;
		tranList.add((String)siteTotal.get(11));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(12), ":"));
		tranList.add(siteTotal.get(13));
		tranList.add(siteTotal.get(14));
		tranList.add(siteTotal.get(15));
		tranList.add(siteTotal.get(16));
		tranList.add(siteTotal.get(17));
		tranList.add(siteTotal.get(18));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(19), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(20), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(21), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(22), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(23), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(24), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(25), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteTotal.get(26), ":"));
		long gridFault = (Long)siteTotal.get(22) + (Long)siteTotal.get(24); 
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
		long gridShutdown = (Long)siteTotal.get(23) + (Long)siteTotal.get(25);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
		long faultHours = 	(Long)siteTotal.get(12) + (Long)siteTotal.get(19) + (Long)siteTotal.get(20) + 
							(Long)siteTotal.get(21) + (Long)siteTotal.get(22) + (Long)siteTotal.get(23) + 
							(Long)siteTotal.get(24) + (Long)siteTotal.get(25) + (Long)siteTotal.get(26)+(Long)siteTotal.get(27);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));

		return tranList;
		
	}
	
	public static ArrayList<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMeta(String siteId, String customerId, String fromDate,
			String toDate, String wecType) {
		ArrayList<ArrayList<Object>> siteWECWiseTotalTranList = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> siteWECWiseTotal = SiteMetaInfo.getOneSiteWECWiseTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMPR(wecType, siteId, customerId, fromDate, toDate); 
		ArrayList<Object> tranList = new ArrayList<Object>();
		for(ArrayList<Object> wecTotal: siteWECWiseTotal){
		if(wecTotal.size() == 2){
			tranList.add((String)wecTotal.get(0));
			tranList.add((String)wecTotal.get(1));
		}
		else{
			tranList.add((String)wecTotal.get(0));
			tranList.add((String)wecTotal.get(1));
			tranList.add((String)wecTotal.get(2));
			tranList.add((String)wecTotal.get(3));
			tranList.add((Integer)wecTotal.get(4));
			tranList.add((String)wecTotal.get(5));
			tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(6))));
			tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(7))));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(8), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(9), ":"));
			tranList.add((String)wecTotal.get(10));
			tranList.add((java.sql.Date)wecTotal.get(11));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(12), ":"));
			tranList.add(wecTotal.get(13));
			tranList.add(wecTotal.get(14));
			tranList.add(wecTotal.get(15));
			tranList.add(wecTotal.get(16));
			tranList.add(wecTotal.get(17));
			tranList.add(wecTotal.get(18));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(19), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(20), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(21), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(22), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(23), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(24), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(25), ":"));
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(26), ":"));
			long gridFault = (Long)wecTotal.get(22) + (Long)wecTotal.get(24); 
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
			long gridShutdown = (Long)wecTotal.get(23) + (Long)wecTotal.get(25);
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
			long faultHours = 	(Long)wecTotal.get(12) + (Long)wecTotal.get(19) + (Long)wecTotal.get(20) + 
								(Long)wecTotal.get(21) + (Long)wecTotal.get(22) + (Long)wecTotal.get(23) + 
								(Long)wecTotal.get(24) + (Long)wecTotal.get(25) + (Long)wecTotal.get(26)+(Long)wecTotal.get(27);
			tranList.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));
		}
		siteWECWiseTotalTranList.add(tranList);
		tranList = new ArrayList<Object>();
		}
		return siteWECWiseTotalTranList;
		
	}
	
	public static List<Object> getOneSiteTotalBasedOnSiteIdCustomerIdForBetweenDaysMeta(String siteId, String customerId, String fromReadingDate, String toReadingDate){
		List<Object> tranList = new ArrayList<Object>();
		fromReadingDate = DateUtility.convertDateFormats(fromReadingDate, "dd/MM/yyyy", "dd-MMM-yyyy");
		toReadingDate = DateUtility.convertDateFormats(toReadingDate, "dd/MM/yyyy", "dd-MMM-yyyy");
		List<Object> oneSiteTotal = SiteMetaInfo.getOneSiteTotalBasedOnSiteIdCustomerIdMeta(siteId, customerId, fromReadingDate, toReadingDate);
		//display(oneSiteTotal);
		Vector<Object> v = new Vector<Object>();
		for (int i = 0; i < oneSiteTotal.size(); i++) {
			v.add(oneSiteTotal.get(i));
		}
		tranList.add(v);
		//GlobalUtils.displayVectorMember(tranList);
		return tranList;
	}
	
	public static List<Object> getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerIdMetaDuplicate(String siteId, String customerId, String fromReading, String toReading){
		List<Object> tranList = new ArrayList<Object>();
		fromReading = DateUtility.convertDateFormats(fromReading, "dd/MM/yyyy", "dd-MMM-yy");
		toReading =  DateUtility.convertDateFormats(toReading, "dd/MM/yyyy", "dd-MMM-yy");
		ArrayList<ArrayList<Object>> oneSiteWECWiseTotal = SiteMetaInfo.getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerIdMeta(siteId, customerId, fromReading, toReading);
		//GlobalUtils.displayVectorMember(oneSiteWECWiseTotal);
		Vector<Object> v = new Vector<Object>();
		for (ArrayList<Object> wecTotal : oneSiteWECWiseTotal) {
			v = new Vector<Object>();
			for (Object object : wecTotal) {
				
				v.add(object);
			}
			tranList.add(v);
		}
		//System.out.println("-------------------");
		//GlobalUtils.displayVectorMember(tranList);
		return tranList;
	}
	
	

	public static List<Object> getWECCountSiteNameStateNameBasedOnCustomerIdsSiteIds(String custIds, String siteIds){
		List<Object> tranList = new ArrayList<Object>();
		ArrayList<ArrayList<String>> customerInfo = CustomerMetaInfo.getWECCountSiteNameStateNameBasedOnCustomerIdsSiteIds(custIds, siteIds);
		/*"Select S_customer_name,count(S_wec_id), S_Site_Id , S_Site_Name,S_Customer_Id,S_State_Name " +*/
		for (ArrayList<String> arrayList : customerInfo) {
			Vector<Object> v = new Vector<Object>();
			v.add(arrayList.get(0));
			v.add(arrayList.get(3));
			v.add(arrayList.get(1));
			v.add("NA");
			v.add(arrayList.get(2));
			v.add(arrayList.get(4));
			v.add(arrayList.get(5));
			tranList.add(v);
		}
		return tranList;
	}
	
	
	
	/**
	 * 27 : WEC Count
	 * @param ebId
	 * @param requestDate
	 * @return
	 */
	public static List<Object> getOneEBTotalForOneday(String ebId, String requestDate){
		List<Object> tranList = new ArrayList<Object>();
		List<Object> ebTotalInfo = EBMetaInfo.getOneEBTotalForOneDay(ebId, AdminDao.convertDateFormat(requestDate, "dd/MM/yyyy", "dd-MMM-yy"));
		Vector<Object> v = new Vector<Object>();
		
		v.add(ebTotalInfo.get(27));
		v.add(NumberUtility.formatNumber(((Long)ebTotalInfo.get(6))));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)ebTotalInfo.get(8), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)ebTotalInfo.get(12), ":"));
		v.add(ebTotalInfo.get(13));
		v.add(ebTotalInfo.get(14));
		v.add(ebTotalInfo.get(15));
		v.add(ebTotalInfo.get(17));
		v.add(ebTotalInfo.get(16));
		v.add(ebTotalInfo.get(18));
		
		tranList.add(v);
		
		return tranList;
	}
	
	
	
	/**
	 * No Addition
	 * @param ebId
	 * @param requestDate
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getOneEBWECWiseInfoForOneDay(String ebId, String requestDate) throws Exception{
		boolean isEbDataAvailable = false;
		
		Connection conn = null;
		List<Object> tranList = new ArrayList<Object>();
		
		//All Active Wec connected to ebId
		Set<String> remainingActiveWecIds = FindingQueryDao.getActiveWecIdsBasedOnOneEbId(ebId);
		//logger.debug(remainingActiveWecIds);
		try{
		conn = wcareConnector.getConnectionFromPool();
		requestDate = AdminDao.convertDateFormat(requestDate, "dd/MM/yyyy", "dd-MMM-yy");
		Map<String, Integer> unpublishWECWiseCount = null;
		
		//Count of Unpublish Data in wecreading of all wecs  
		unpublishWECWiseCount = getPublishStatusWECWiseBasedOnEbId(ebId, requestDate);
		//logger.debug(unpublishWECWiseCount);
		//List of Wec Data(Data itself is list)
		ArrayList<ArrayList<Object>> ebInfo = EBMetaInfo.getOneEBWECWiseInfoForOneDay(ebId, requestDate);
		
		Vector<Object> v = new Vector<Object>();
		String wecId = null;
		String scadaRemark = " <span style='color:red;'>Connectivity failure during data transfer </span>";
		String actualremarks = "";
		String remarks = "";
		
//		System.out.println("publishStatusWECWise :: " + publishStatusWECWise);
		for (ArrayList<Object> wecDataInfo : ebInfo) {
			
			//Not Needed
			isEbDataAvailable = true;
			
			//Wec Id
			wecId = (String) wecDataInfo.get(0);
			
			//true if (scadaConnected and operating hour is 0 and fault hour < 4 || Data Not Present)
			boolean isLullHourDash = checkLullHour(wecDataInfo.get(0).toString(), conn, DateUtility.stringDateFormatToSQLDate(requestDate, "dd-MMM-yy"));
//			System.out.println("WEC Description : " + WECInfo.get(1).toString() + ", lull hour : " + isLullHourDash);
			
			//Removing if lulldash is not i.e Data which will seen will be remove 
			if(!isLullHourDash) remainingActiveWecIds.remove(wecId);
			
			//Data partially published
			if(unpublishWECWiseCount.containsKey(wecId) && unpublishWECWiseCount.get(wecId) > 0){
				
				//GlobalUtils.displayVectorMember(WECInfo);
				v = new Vector<Object>();
				v.add(wecDataInfo.get(1));//Descr:0
				v.add(wecDataInfo.get(3));//WEC Type:1
				
				v.add("-");//Generation:2
				v.add("-");//Operatinghr:3
				v.add("-");//Lull Hour:4
				
				v.add("-");//Mavail:5
				v.add("-");//Gavail:6
				v.add("-");//Cfactor:7
				
				v.add(wecId);//WECId:8
				
				v.add(wecDataInfo.get(1) + " - " + scadaRemark);
				
				v.add(remarks);//Remards : 9
				v.add(wecDataInfo.get(18));//TRIALRUN : 10
				v.add("-");//MIAVIAL : 11
				v.add("-");//GIAVIAL : 12
			}
			else{
//				GlobalUtils.displayVectorMember(WECInfo);
				if(!isLullHourDash){
					v = new Vector<Object>();
					v.add(wecDataInfo.get(1));//Descr : 0
					v.add(wecDataInfo.get(3));//WEC Type : 1
					
					if (isLullHourDash) {
						v.add("-");//Gen : 2
						v.add("-");//OpHr : 3
						v.add("-");//LullHr : 4
					}
					else{
						v.add(NumberUtility.formatNumber(((Long)wecDataInfo.get(6))));//Generation : 2
						v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecDataInfo.get(8), ":"));//Operatinghr : 3
						v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecDataInfo.get(12), ":"));//LullHour : 4
					}
					
					v.add(wecDataInfo.get(13));//Mavail:5
					v.add(wecDataInfo.get(14));//Gavail:6
					if(isLullHourDash){
						v.add("-");//Cfactor : 7
					}
					else{
						v.add(wecDataInfo.get(15));//Cfactor : 7
					}
					v.add(wecDataInfo.get(0));//WECId : 8
					
					if (!isLullHourDash) {
						actualremarks = wecDataInfo.get(10).toString();
						remarks = actualremarks.equals("") ? "NIL" : actualremarks;//Remarks
					} else {
						remarks = actualremarks.equals("") ? wecDataInfo.get(1) + "-" + scadaRemark : wecDataInfo.get(1).toString() + actualremarks + "." + scadaRemark;
					}
					v.add(remarks);//Remaerks : 9
					v.add(wecDataInfo.get(18));//TRIALRUN : 10
					v.add(wecDataInfo.get(17));//MIAVIAL : 11
					v.add(wecDataInfo.get(16));//GIAVIAL : 12
				}
			}
			/*MethodClass.displayMethodClassName();
			*/
			//System.out.println("Vector v : " + v.size());
			if(!isLullHourDash) tranList.add(v);
			v = new Vector<Object>();
			
		}
		
		if(remainingActiveWecIds.size() != 0){
//			System.out.println(remainingActiveWecIds);
			for(String leftWecId : remainingActiveWecIds){
				v = new Vector<Object>();
				
				WEC wec = InfoQueryDao.getWecInfoBasedOnOneWecId(leftWecId);
				v.add(wec.getS_WECSHORT_DESCR() + " (" + (wec.getS_FOUND_LOC() == null ? "NA" : wec.getS_FOUND_LOC())  + ")");			//0  : Descr N
				v.add(wec.getS_WEC_TYPE());			//1  : WEC Type N
				v.add("-");			//2  : Gen  
				v.add("-");			//3  : Op Hr
				v.add("-");			//4  : Lull Hr
				v.add("-");			//5  : Mavail
				v.add("-");			//6  : Gavail
				v.add("-");			//7  : CF
				v.add("-");			//8  : WECId N
				
				v.add(wec.getS_WECSHORT_DESCR()  + " (" + (wec.getS_FOUND_LOC() == null ? "NA" : wec.getS_FOUND_LOC()) + ")" + " - " + scadaRemark);			//9  : Remarks
				v.add("0");			//10 : Trial Run
				v.add("-");			//11 : Miavail
				v.add("-");			//12 : Giavail
				
				tranList.add(v);
				
			}
		}
		//logger.debug(tranList);
		return tranList;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			try{
				if(conn != null){ wcareConnector.returnConnectionToPool(conn);}
			}catch(Exception e){
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
		return tranList;
	}
	
	private static Map<String, Integer> getPublishStatusWECWiseBasedOnEbId(
			String ebId, String requestDate) throws SQLException {
//		MethodClass.displayMethodClassName();
//		System.out.println("sd ::: EB ID ::: " + ebId);
		Map<String, Integer> wecWisePublishStatus = new HashMap<String, Integer>();
		String query = new String();
		Connection connection = null; 
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		query = "SELECT S_wec_id, count(N_Publish) as \"Publish_Count\" " + 
				"FROM Tbl_Wec_Reading " + 
				"WHERE D_Reading_Date = ? " + 
				"AND S_Wec_Id                                IN " + 
				"  (SELECT S_Wec_Id " + 
				"  FROM tbl_wec_master wecmaster, tbl_eb_master ebmaster " + 
				"  WHERE wecmaster.S_eb_id = ebmaster.S_eb_id " + 
				"  and ebmaster.S_EB_ID = ?) " + 
				"  and N_PUBLISH = '0' " + 
				"  group by S_wec_id " + 
				"ORDER BY S_Wec_Id ";

		try {
			connection = wcareConnector.getConnectionFromPool();
			
			stmt = connection.prepareStatement(query);
			stmt.setString(1, requestDate);
			stmt.setString(2, ebId);
			DaoUtility.displayQueryWithParameter(13, query, requestDate, ebId);
			resultSet = stmt.executeQuery();
			
			while(resultSet.next()){
				DaoUtility.getRowCount(13, resultSet);
				wecWisePublishStatus.put(resultSet.getString("S_wec_ID"), resultSet.getInt("Publish_Count"));
			}
			//System.out.println("jshf : WecWisePublishStatusMapping :::: " + wecWisePublishStatus);
		}finally{
			 DaoUtility.releaseResources(stmt, resultSet, connection);
			
		}
		
		return wecWisePublishStatus;
	}

	/**
	 * 27 : WEC Count, 28 : CustId, 29 : State Name, 30 : State Id
	 * @param customerId
	 * @param readingDate
	 * @return
	 */
	public static List<Object> getStateWiseTotalForOneDayBasedOnCustomerIdMeta(String customerId, String requestDate){
		logger.info("------------Start");
		logger.info(String.format("Customer Id: %s, Request Date: %s", customerId, requestDate));
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> stateWiseWECTotal = StateMetaInfo.getStateWiseTotalForOneDayBasedOnCustomerIdMeta(customerId, DateUtility.convertDateFormats(requestDate, "dd/MM/yyyy", "dd-MMM-yyyy"));
		logger.info(String.format("Value Returned stateWiseWECTotal: %s", stateWiseWECTotal));
		for (ArrayList<Object> stateWECTotal : stateWiseWECTotal) {
			logger.info(String.format("Size: %s, Data: %s", stateWECTotal.size(), stateWECTotal));
			for(int i = 0; i < stateWECTotal.size(); i++){
				v.add(stateWECTotal.get(i));
//				logger.info(stateWECTotal.get(i));
			}
			tranList.add(v);
			
			v = new Vector<Object>();
		}
		logger.info(String.format("transList: %s", tranList));
		logger.info("-------------End");
		return tranList;
	}
	
	
	
	/**
	 * No Addition
	 * @param customerId
	 * @param readingDate
	 * @return
	 */
	public static List<Object> getOverallTotalForOneDayBasedOnCustomerIdMeta(String customerId, String requestDate){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		List<Object> overAllTotalInfo = new ArrayList<Object>();
		
		overAllTotalInfo = CustomerMetaInfo.getOverallTotalForOneDayBasedOnCustomerIdMeta(customerId, AdminDao.convertDateFormat(requestDate, "dd/MM/yyyy", "dd-MMM-yy"));
		
		for(int i = 0 ; i < overAllTotalInfo.size() ; i++){
			v.add(overAllTotalInfo.get(i));
		}
			tranList.add(v);
			//GlobalUtils.displayVectorMember(tranList);
		return tranList;
	}
	
	/**
	 * 27 : WEC Count, 28 : Customer Id, 29 : State Id, 30 : Site Name, 31 : Site Id
	 * @param customerId
	 * @param stateId
	 * @param readingDate
	 * @return
	 */
	public static List<Object> getSiteWiseTotalForOneDayBasedOnStateIdCustomerIdMeta(String customerId, String stateId,String readingDate){
		logger.debug("--------------Start");
		logger.debug(String.format("Customer Id: %s, State Id: %s, Reading Date: %s", customerId, stateId, readingDate));
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> siteWiseWECTotal = SiteMetaInfo.getSiteWiseTotalForOneDayBasedOnStateIdCustomerIdMeta(customerId, stateId,DateUtility.convertDateFormats(readingDate, "dd/MM/yyyy", "dd-MMM-yyyy"));
		for (ArrayList<Object> siteWECTotal : siteWiseWECTotal) {
			logger.debug(String.format("Size: %s, Data: %s", siteWECTotal.size(), siteWECTotal));
			for(int i = 0; i < siteWECTotal.size(); i++){
//				logger.debug(String.format("(%s)%s: %s ", i, getMpDescription(i), siteWECTotal.get(i)));
				v.add(siteWECTotal.get(i));
			}
			
			tranList.add(v);
			v = new Vector<Object>();
		}
		//GlobalUtils.displayVectorMember(tranList);
		logger.debug("--------------End");
		return tranList;	
	}
	
	private static String getMpDescription(int i) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		if(i > 27) return "Undefined";
		map.put(0 , "Wec Id");
		map.put(1 , "Wec Description");
		map.put(2 , "Eb Id");
		map.put(3 , "Wec Type");
		map.put(4 , "Wec Capacity");
		map.put(5 , "Reading Date");
		map.put(6 , "Gen");
		map.put(7 , "TotGen");
		map.put(8 , "OpHr");
		map.put(9 , "TotOpHr");
		map.put(10, "Remarks");
		map.put(11, "Commision Date");
		map.put(12, "Lull Hour");
		map.put(13, "Mavial");
		map.put(14, "Gavial");
		map.put(15, "CF");
		map.put(16, "Giavial");
		map.put(17, "Miavial");
		map.put(18, "Trial Run");
		map.put(19, "MF");
		map.put(20, "MS");
		map.put(21, "EBLoad");
		map.put(22, "GIF");
		map.put(23, "GIS");
		map.put(24, "GEF");
		map.put(25, "GES");
		map.put(26, "WecShutdown");
		map.put(27, "Customer Scope");
		return map.get(i);
	}

	/*--------------------------------------------------------------------------------------------------------*/
	/*--------------------------------------------------------------------------------------------------------*/
	/*--------------------------------------------------------------------------------------------------------*/
	/*--------------------------------------------------------------------------------------------------------*/
	
	private static boolean checkLullHour(String wecID, Connection conn, java.sql.Date reportdate){	
		boolean makeLullHourDash = false;
		double operatingHourPresent = 0;
		double faultHourTotal = 0;
		String wecScadaConnectivityQuery = CustomerSQLC.CHECK_WEC_STATUS_SCADA_CONNECTIVITY;
		
		PreparedStatement myPrepareStmt = null;
		ResultSet myResultSet = null;
		PreparedStatement myPrepareStmt1 = null;
		ResultSet myResultSet1 = null;
		try {
			myPrepareStmt = conn.prepareStatement(wecScadaConnectivityQuery);
			myPrepareStmt.setObject(1, wecID);
			DaoUtility.displayQueryWithParameter(15, wecScadaConnectivityQuery, wecID);
			myResultSet = myPrepareStmt.executeQuery();
			while(myResultSet.next()){
				DaoUtility.getRowCount(15, myResultSet);
				String machineActiveStatus = myResultSet.getString("S_status");
//				
				if(machineActiveStatus.equals("1")){
					
					/*Getting the actual operating hours for a particular machine*/
					String myQuery1 = 
									"select sum(n_actual_value) from tbl_wec_reading " + 
									"where s_wec_id = ? " +
									"and d_reading_date = ? " +
									"and s_mp_id = '0808000023' ";
					
					myPrepareStmt1 = conn.prepareStatement(myQuery1);
					
					myPrepareStmt1.setObject(1, wecID);
					myPrepareStmt1.setObject(2, reportdate);
					
					DaoUtility.displayQueryWithParameter(16, myQuery1, wecID, reportdate);
					myResultSet1 = myPrepareStmt1.executeQuery();
					while(myResultSet1.next()){
						DaoUtility.getRowCount(16, myResultSet1);
						
						//Checking for operating hour data present or not
						//If not present assume 0
						if(myResultSet1.getObject(1) != null){
							operatingHourPresent = myResultSet1.getDouble(1);
						}
					}
					
					/*If actual operating hours is greater than 0 then Lull Hour will be as it is*/
					if(operatingHourPresent > 0){
						makeLullHourDash = false;
						return makeLullHourDash;
					}
					
					/* Getting Fault Hour of particular day and a particular WEC */
					myQuery1 = 
							"select sum(n_actual_value) from tbl_wec_reading " + 
							"where s_wec_id = ? " +
							"and d_reading_date = ? " +
							"and s_mp_id in ('1408000001', '1401000006', '1401000005', '1401000004', '1401000003', '1401000002', '1401000001', '0810000002', '0810000001', '0808000028', '0808000027', '0808000026', '0808000025') ";

					myPrepareStmt1 = conn.prepareStatement(myQuery1);
					myPrepareStmt1.setObject(1, wecID);
					myPrepareStmt1.setObject(2, reportdate);
					DaoUtility.displayQueryWithParameter(17, myQuery1, wecID, reportdate);
					myResultSet1 = myPrepareStmt1.executeQuery();
					
					while(myResultSet1.next()){
						DaoUtility.getRowCount(17, myResultSet1);
						
						//Checking for fault hour data present or not
						//If not present assume 0
						if(myResultSet1.getObject(1) != null)
							faultHourTotal = myResultSet1.getDouble(1);
					}
					
					if(faultHourTotal > 4){
						makeLullHourDash = false;
						return makeLullHourDash;
					} else {
						return true;
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			DaoUtility.releaseResources(Arrays.asList(myPrepareStmt, myPrepareStmt1), Arrays.asList(myResultSet, myResultSet1));
		}
		
		return makeLullHourDash;
	}
	
	public static ArrayList<Object> getOneWECInfoForOneMonth(String wecId, int month, int year){
		ArrayList<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> wecDateWiseInfoBetweenDays = WECMetaInfo.getOneWECInfoForOneMonth(wecId, month, year);
		for (ArrayList<Object> arrname : wecDateWiseInfoBetweenDays) {
			
			v.add(arrname.get(1));
			v.add(arrname.get(3));
			v.add(DateUtility.sqlDateToStringDate(((java.sql.Date)arrname.get(5)), "dd-MMM-yyyy"));
			v.add(NumberUtility.formatNumber(((Long)arrname.get(6))));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)arrname.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)arrname.get(12), ":"));
			
			v.add(arrname.get(13));
			v.add(arrname.get(14));
			v.add(arrname.get(15));
			v.add(arrname.get(18));
			v.add("0");
			v.add(arrname.get(17));
			v.add(arrname.get(16));
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
	}

	public static List<Object> getOneWECMonthWiseTotalForOneFiscalYear(String wecId, int fiscalYear){
		ArrayList<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> oneWECTotalForOneMonth = WECMetaInfo.getOneWECTotalMonthWiseForOneFiscalYear(wecId, fiscalYear);
		for (ArrayList<Object> arrname : oneWECTotalForOneMonth) {
			
			v.add(arrname.get(27) + "-" + arrname.get(28));
			v.add(NumberUtility.formatNumber(((Long)arrname.get(6))));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)arrname.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)arrname.get(12), ":"));
			v.add(arrname.get(13));
			v.add(arrname.get(14));
			v.add(arrname.get(15));
			v.add(arrname.get(17));
			v.add(arrname.get(16));
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
	}

	public static List<Object> getOneWECTotalForOneMonth(String wecId, int month, int year){
		List<Object> tranList = new ArrayList<Object>();
		List<Object> arrname = WECMetaInfo.getOneWECTotalForOneMonth(wecId, month, year);
		Vector<Object> v = new Vector<Object>();
		v.add(NumberUtility.formatNumber(((Long)arrname.get(6))));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)arrname.get(8), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)arrname.get(12), ":"));
		v.add(arrname.get(13));
		v.add(arrname.get(14));
		v.add(arrname.get(15));
		v.add(arrname.get(17));
		v.add(arrname.get(16));
		v.add(arrname.get(18));
		tranList.add(v);
		
		return tranList;
	}

	public static List<Object> getOneWECTotalForOneFiscalYear(String wecId, int fiscalYear){
		List<Object> tranList = new ArrayList<Object>();
		List<Object> arrname = WECMetaInfo.getOneWECTotalForOneFiscalYear(wecId, fiscalYear);
		Vector<Object> v = new Vector<Object>();
		v.add(NumberUtility.formatNumber(((Long)arrname.get(6))));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)arrname.get(8), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)arrname.get(12), ":"));
		v.add(arrname.get(13));
		v.add(arrname.get(14));
		v.add(arrname.get(15));
		v.add(arrname.get(17));
		v.add(arrname.get(16));
		v.add(arrname.get(18));
		tranList.add(v);
		
		return tranList;
	}

	public static void display(List<Object> ss){
		List<Object> a = new ArrayList<Object>();
		if(ss.size() <20){
			GlobalUtils.displayVectorMember(ss);
			return;
		}
		for (Object object : ss) {
			if(a.size() == 8 || a.size() == 12 ||a.size() == 19 || a.size() == 20 ||a.size() == 21 || a.size() == 22 ||a.size() == 23 || a.size() == 24 ||a.size() == 25 || a.size() == 26){
				a.add(TimeUtility.convertMinutesToTimeStringFormat(((Long)object), ":"));
				continue;
			}
			a.add(object);
		}
		GlobalUtils.displayVectorMember(a);
	}
	
	public static List<Object> getOneSiteTotalBasedOnSiteIdCustomerId(String siteId, String customerId, String fromReadingDate, String toReadingDate){
		List<Object> tranList = new ArrayList<Object>();
		List<Object> oneSiteTotal = SiteMetaInfo.getOneSiteTotalBasedOnSiteIdCustomerId(siteId, customerId, fromReadingDate, toReadingDate);
		//display(oneSiteTotal);
		Vector<Object> v = new Vector<Object>();
		
		v.add(NumberUtility.formatNumber(((Long)oneSiteTotal.get(6))));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)oneSiteTotal.get(8), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)oneSiteTotal.get(12), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)oneSiteTotal.get(19), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)oneSiteTotal.get(20), ":"));
		long gridFault = (Long)oneSiteTotal.get(22) + (Long)oneSiteTotal.get(24); 
		v.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
		long gridShutdown = (Long)oneSiteTotal.get(23) + (Long)oneSiteTotal.get(25);
		v.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
		long faultHours = 	(Long)oneSiteTotal.get(12) + (Long)oneSiteTotal.get(19) + (Long)oneSiteTotal.get(20) + 
							(Long)oneSiteTotal.get(21) + (Long)oneSiteTotal.get(22) + (Long)oneSiteTotal.get(23) + 
							(Long)oneSiteTotal.get(24) + (Long)oneSiteTotal.get(25) + (Long)oneSiteTotal.get(26);
		v.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));
		v.add(oneSiteTotal.get(13));
		v.add(oneSiteTotal.get(15));
		v.add(oneSiteTotal.get(16));
		v.add(oneSiteTotal.get(14));
		tranList.add(v);
		return tranList;
	}
	
	public static List<Object> getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerId(String siteId, String customerId, String fromReading, String toReading){
		List<Object> tranList = new ArrayList<Object>();
		List<ArrayList<Object>> oneSiteWECWiseTotal = SiteMetaInfo.getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerId(siteId, customerId, DateUtility.convertDateFormats(fromReading, "dd/MM/yyyy", "dd-MMM-yy"), DateUtility.convertDateFormats(toReading, "dd/MM/yyyy", "dd-MMM-yy"));
		//GlobalUtils.displayVectorMember(oneSiteWECWiseTotal);
		for (ArrayList<Object> wecTotal : oneSiteWECWiseTotal) {
			Vector<Object> v = new Vector<Object>();
			v.add(wecTotal.get(1));
			v.add(NumberUtility.formatNumber(((Long)wecTotal.get(6))));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(12), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(19), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(20), ":"));
			long gridFault = (Long)wecTotal.get(22) + (Long)wecTotal.get(24); 
			v.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
			long gridShutdown = (Long)wecTotal.get(23) + (Long)wecTotal.get(25);
			v.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
			long faultHours = 	(Long)wecTotal.get(12) + (Long)wecTotal.get(19) + (Long)wecTotal.get(20) + 
								(Long)wecTotal.get(21) + (Long)wecTotal.get(22) + (Long)wecTotal.get(23) + 
								(Long)wecTotal.get(24) + (Long)wecTotal.get(25) + (Long)wecTotal.get(26);
			v.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));
			v.add(wecTotal.get(13));
			v.add(wecTotal.get(15));
			v.add(wecTotal.get(16));
			v.add(wecTotal.get(14));
			tranList.add(v);
		}
		//GlobalUtils.displayVectorMember(tranList);
		return tranList;
	}
	
	public static List<Object> getOneWECInfoForOneDayCumOneWECTotalForOneMonthButLessThanCurrentReadingDate(String ebId, String requestDate){
		List<Object> tranList = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> wecInfo = EBMetaInfo.getOneEBWECWiseInfoForOneDay(ebId, AdminDao.convertDateFormat(requestDate, "dd/MM/yyyy", "dd-MMM-yy"));
		Vector<Object> v = new Vector<Object>();
		for (ArrayList<Object> wecOneDayInfo : wecInfo) {
			v.add(wecOneDayInfo.get(1));
			v.add(wecOneDayInfo.get(3));
			v.add(NumberUtility.formatNumber(((Long)wecOneDayInfo.get(6))));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecOneDayInfo.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecOneDayInfo.get(12), ":"));
			v.add(wecOneDayInfo.get(13));
			v.add(wecOneDayInfo.get(14));
			v.add(wecOneDayInfo.get(15));
			v.add(wecOneDayInfo.get(0));
			v.add(((String)wecOneDayInfo.get(10)).equals("")?"NIL" : wecOneDayInfo.get(10));
			v.add(wecOneDayInfo.get(18));
			List<Object> wecMonthTotal = WECMetaInfo.getoneWECTotalForOneMonthButLessThanCurrentReadingDate((String)wecOneDayInfo.get(0), AdminDao.convertDateFormat(requestDate, "dd/MM/yyyy", "dd-MMM-yy"), DateUtility.gettingMonthNumberFromStringDate(requestDate, "dd/MM/yyyy"), DateUtility.gettingYearFromStringDate(requestDate, "dd/MM/yyyy"));
			v.add(wecMonthTotal.get(6));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecMonthTotal.get(8), ":"));
			v.add(wecOneDayInfo.get(17));
			v.add(wecOneDayInfo.get(16));
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
	}
	
	public static List<Object> getOneEBTotalForOneDayCumOneEBTotalForOneMonthButLessThanCurrentReadingDate(String ebId, String requestDate){
		List<Object> tranList = new ArrayList<Object>();
		List<Object> oneEBTotalForOneDay = EBMetaInfo.getOneEBTotalForOneDay(ebId, AdminDao.convertDateFormat(requestDate, "dd/MM/yyyy", "dd-MMM-yy"));
		Vector<Object> v = new Vector<Object>();
		v.add(oneEBTotalForOneDay.get(27));
		v.add(NumberUtility.formatNumber(((Long)oneEBTotalForOneDay.get(6))));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)oneEBTotalForOneDay.get(8), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)oneEBTotalForOneDay.get(12), ":"));
		v.add(oneEBTotalForOneDay.get(13));
		v.add(oneEBTotalForOneDay.get(14));
		v.add(oneEBTotalForOneDay.get(15));
		List<Object> oneEBTotalForOneMonthButLessThanCurrentReadingDate = EBMetaInfo.getOneEBTotalForOneMonthButLessThanCurrentReadingDate(ebId, AdminDao.convertDateFormat(requestDate, "dd/MM/yyyy", "dd-MMM-yy"), DateUtility.gettingMonthNumberFromStringDate(requestDate, "dd/MM/yyyy"), DateUtility.gettingYearFromStringDate(requestDate, "dd/MM/yyyy"));
		v.add(oneEBTotalForOneMonthButLessThanCurrentReadingDate.get(6));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)oneEBTotalForOneMonthButLessThanCurrentReadingDate.get(8), ":"));
		v.add(oneEBTotalForOneDay.get(17));
		v.add(oneEBTotalForOneDay.get(16));
		tranList.add(v);
		return tranList;
	}
	
	
	
	
	
	public static List<Object> getOneEBWECWiseTotalForOneFiscalYear(String ebId, int fiscalYear){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> oneEBWECWiseTotalForOneFiscalYear = EBMetaInfo.getOneEBWECWiseTotalForOneFiscalYear(ebId, fiscalYear);
		for (ArrayList<Object> wecTotalForOneFiscalYear : oneEBWECWiseTotalForOneFiscalYear) {
			
			v.add(wecTotalForOneFiscalYear.get(1));
			v.add(wecTotalForOneFiscalYear.get(3));
			v.add(NumberUtility.formatNumber(((Long)wecTotalForOneFiscalYear.get(6))));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotalForOneFiscalYear.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotalForOneFiscalYear.get(12), ":"));
			v.add(wecTotalForOneFiscalYear.get(13));
			v.add(wecTotalForOneFiscalYear.get(14));
			v.add(wecTotalForOneFiscalYear.get(15));
			v.add(wecTotalForOneFiscalYear.get(0));
			v.add("NIL");
			v.add("0");
			v.add(wecTotalForOneFiscalYear.get(17));
			v.add(wecTotalForOneFiscalYear.get(16));
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
	}

	public static List<Object> getOneEBWECWiseInfoForOneMonth(String ebId, int month, int year){
		List<Object> tranList = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> wecInfo = EBMetaInfo.getOneEBWECWiseInfoForOneMonthQuery(ebId, month, year);
		
		for (ArrayList<Object> wecIn : wecInfo) {
			Vector<Object> v = new Vector<Object>();
			v.add(wecIn.get(1));
			v.add(wecIn.get(3));
			v.add(NumberUtility.formatNumber(((Long)wecIn.get(6))));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecIn.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecIn.get(12), ":"));
			v.add(wecIn.get(13));
			v.add(wecIn.get(14));
			v.add(wecIn.get(15));
			v.add(wecIn.get(0));
			v.add(DateUtility.sqlDateToStringDate((java.sql.Date)wecIn.get(5), "dd-MM-yyyy"));
			v.add(wecIn.get(18));
			v.add(((String)wecIn.get(10)).equals("") ? "NIL" : wecIn.get(10));
			v.add(wecIn.get(17));
			v.add(wecIn.get(16));
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
	}

	public static List<Object> getOneEBTotalForOneFiscalYear(String ebId, int fiscalYear){
		List<Object> tranList = new ArrayList<Object>();
		List<Object> oneEBTotalForOneFiscalYear = EBMetaInfo.getOneEBTotalForOneFiscalYear(ebId, fiscalYear);
		Vector<Object> v = new Vector<Object>();
		v.add(oneEBTotalForOneFiscalYear.get(27));
		v.add(NumberUtility.formatNumber(((Long)oneEBTotalForOneFiscalYear.get(6))));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)oneEBTotalForOneFiscalYear.get(8), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)oneEBTotalForOneFiscalYear.get(12), ":"));
		v.add(oneEBTotalForOneFiscalYear.get(13));
		v.add(oneEBTotalForOneFiscalYear.get(14));
		v.add(oneEBTotalForOneFiscalYear.get(15));
		v.add(oneEBTotalForOneFiscalYear.get(17));
		v.add(oneEBTotalForOneFiscalYear.get(16));
		v.add(oneEBTotalForOneFiscalYear.get(18));
		tranList.add(v);
		
		return tranList;
	}

	public static List<Object> getOneEBWECWiseTotalForOneMonth(String ebid,int month,int year){
		List<Object> tranList = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> ebInfo = EBMetaInfo.getOneEBWECWiseTotalForOneMonth(ebid, month, year);
	
		for (ArrayList<Object> WECInfo : ebInfo) {
			Vector<Object> v = new Vector<Object>();
			v.add(WECInfo.get(1));
			v.add(WECInfo.get(3));
			v.add(NumberUtility.formatNumber(((Long)WECInfo.get(6))));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)WECInfo.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)WECInfo.get(12), ":"));
			v.add(WECInfo.get(13));
			v.add(WECInfo.get(14));
			v.add(WECInfo.get(15));
			v.add(WECInfo.get(0));
			v.add("NIL");
			v.add(WECInfo.get(18));
			v.add(WECInfo.get(17));
			v.add(WECInfo.get(16));
			tranList.add(v);
	
		}
	
		return tranList;
	}

	public static List<Object> getOneEBTotalForOneMonth(String ebId, int month , int year){
		List<Object> tranList = new ArrayList<Object>();
		List<Object> ebTotalInfo = EBMetaInfo.getOneEBTotalForOneMonth(ebId, month, year);
		Vector<Object> v = new Vector<Object>();
		v.add(ebTotalInfo.get(27));
		v.add(NumberUtility.formatNumber(((Long)ebTotalInfo.get(6))));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)ebTotalInfo.get(8), ":"));
		v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)ebTotalInfo.get(12), ":"));
		v.add(ebTotalInfo.get(13));
		v.add(ebTotalInfo.get(14));
		v.add(ebTotalInfo.get(15));
		v.add(ebTotalInfo.get(17));
		v.add(ebTotalInfo.get(16));
		v.add(ebTotalInfo.get(18));
		
		tranList.add(v);
		
		return tranList;
	}

	public static List<Object> getSiteWiseTotalForOneDayBasedOnStateIdCustomerId(String customerId, String stateId,String requestdate){
		List<Object> tranList = new ArrayList<Object>();
		
		try{
			
			Vector<Object> v = new Vector<Object>();
			ArrayList<ArrayList<Object>> siteWiseInfo = new ArrayList<ArrayList<Object>>();
			siteWiseInfo = SiteMetaInfo.getSiteWiseTotalForOneDayBasedOnStateIdCustomerId(customerId, stateId, AdminDao.convertDateFormat(requestdate, "dd/MM/yyyy", "dd-MMM-yy"));
			//GlobalUtils.displayVectorMember(siteWiseInfo);
			for (ArrayList<Object> siteInfo : siteWiseInfo) {
				//boolean isWECCountZero = (((Integer)siteInfo.get(27)) == 0);
				if(siteInfo.size() == 5){
					v.add(siteInfo.get(3));
				}
				else{
					v.add(siteInfo.get(30));
					v.add(NumberUtility.formatNumber((Long)siteInfo.get(6)));
					v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteInfo.get(8), ":"));
					v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteInfo.get(12), ":"));
					v.add(siteInfo.get(13));
					v.add(siteInfo.get(14));
					v.add(siteInfo.get(15));
					v.add(siteInfo.get(31));
					v.add(siteInfo.get(17));
					v.add(siteInfo.get(16));
					v.add(siteInfo.get(18));
				}
				tranList.add(v);
				v = new Vector<Object>();
			}
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return tranList;
	}
	
	
	
	public static List<Object> getSiteTotalForOneMonthBasedOnStateIdCustomerId(String customerId, String stateId, int month, int year){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> siteWiseInfo = new ArrayList<ArrayList<Object>>();
		siteWiseInfo = SiteMetaInfo.getSiteWiseTotalForOneMonthBasedOnStateIdCustomerId(customerId, stateId, month, year);
		for (ArrayList<Object> siteInfo : siteWiseInfo) {
			if(siteInfo.size() == 5){
				v.add(siteInfo.get(3));
			}
			else{
				v.add(siteInfo.get(31));
				v.add(NumberUtility.formatNumber(((Long)siteInfo.get(6))));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteInfo.get(8), ":"));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)siteInfo.get(12), ":"));
				v.add(siteInfo.get(13));
				v.add(siteInfo.get(14));
				v.add(siteInfo.get(15));
				v.add(siteInfo.get(32));
				v.add(siteInfo.get(17));
				v.add(siteInfo.get(16));
				v.add(siteInfo.get(18));
			}
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
		
	}
	
	public static List<Object> getSiteWiseTotalForOneMonthBasedOnStateIdCustomerIdMeta(String customerId, String stateId, int month, int year){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> siteWiseInfo = new ArrayList<ArrayList<Object>>();
		siteWiseInfo = SiteMetaInfo.getSiteWiseTotalForOneMonthBasedOnStateIdCustomerIdMeta(customerId, stateId, month, year);
		for (ArrayList<Object> stateWECTotal : siteWiseInfo) {
			for(int i = 0; i < stateWECTotal.size(); i++){
				v.add(stateWECTotal.get(i));
			}
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
	}

	public static List<Object> getSiteWiseTotalForOneFiscalYearBasedOnCustomerIdStateId(String customerId, String stateId, int fiscalYear){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> stateWiseEbTotal = SiteMetaInfo.getSiteWiseTotalForOneFiscalYearBasedOnCustomerIdStateId(customerId, stateId, fiscalYear);
		for (ArrayList<Object> stateEBTotal : stateWiseEbTotal) {
			
			v.add(stateEBTotal.get(30));
			v.add(NumberUtility.formatNumber(((Long)stateEBTotal.get(6))));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)stateEBTotal.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)stateEBTotal.get(12), ":"));
			v.add(stateEBTotal.get(13));
			v.add(stateEBTotal.get(14));
			v.add(stateEBTotal.get(15));
			v.add(stateEBTotal.get(31));
			v.add(stateEBTotal.get(17));
			v.add(stateEBTotal.get(16));
			v.add(stateEBTotal.get(18));
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
	}

	
	public static List<Object> getStateWiseTotalForOneDayBasedOnCustomerId(String customerId, String requestDate){
		List<Object> tranList = new ArrayList<Object>();
		/*Connection conn = null;*/
		try{
		
			Vector<Object> v = new Vector<Object>();
			/*conn = new JDBCUtils().getConnection();
			StateMetaInfo.setConnection(conn);*/
			ArrayList<ArrayList<Object>> stateWiseEbTotal = StateMetaInfo.getStateWiseTotalForOneDayBasedOnCustomerId(customerId, AdminDao.convertDateFormat(requestDate, "dd/MM/yy", "dd-MMM-yy"));
			
			//GlobalUtils.displayVectorMember(stateWiseEbTotal);
			for (ArrayList<Object> stateEBTotal : stateWiseEbTotal) {
				if(stateEBTotal.size() == 4){
					v.add(stateEBTotal.get(2));
				}
				else{
					v.add(stateEBTotal.get(29));
					v.add(NumberUtility.formatNumber((Long)stateEBTotal.get(6)));
					v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)stateEBTotal.get(8), ":"));
					v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)stateEBTotal.get(12), ":"));
					v.add(stateEBTotal.get(13));
					v.add(stateEBTotal.get(14));
					v.add(stateEBTotal.get(15));
					v.add(stateEBTotal.get(30));
					v.add(stateEBTotal.get(17));
					v.add(stateEBTotal.get(16));
					v.add(stateEBTotal.get(18));
				}
				tranList.add(v);
				v = new Vector<Object>();
			}
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			
		}
		
		return tranList;
	}
	
	public static List<Object> getOneEbWECDayWiseInfoForOneDayMeta(String ebId, String readingDate){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> oneEbWECDayWiseInfo = EBMetaInfo.getOneEbWECDayWiseInfoForOneDayMeta(ebId, DateUtility.convertDateFormats(readingDate, "dd/MM/yyyy", "dd-MMM-yyyy"));
		for (ArrayList<Object> oneWECInfo : oneEbWECDayWiseInfo) {
			String wecId = (String) oneWECInfo.get(0);
			//System.out.println("WEC Id:" + wecId);
			for(int i = 0; i < oneWECInfo.size(); i++){
				v.add(oneWECInfo.get(i));
			}
			tranList.add(v);
			v = new Vector<Object>();
		}
		GlobalUtils.displayVectorMember(tranList);
		return tranList;
	}
	
	public static List<Object> getStateWiseTotalForOneMonthBasedOnCustomerId(String customerId, int month, int year){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> stateWiseEbTotal = StateMetaInfo.getStateWiseTotalForOneMonthBasedOnCustomerId(customerId, month, year);
		GlobalUtils.displayVectorMember(stateWiseEbTotal);
		for (ArrayList<Object> stateEBTotal : stateWiseEbTotal) {
			if(stateEBTotal.size() == 4){
				v.add(stateEBTotal.get(2));
			}
			else{
				v.add(stateEBTotal.get(30));
				v.add(NumberUtility.formatNumber(((Long)stateEBTotal.get(6))));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)stateEBTotal.get(8), ":"));
				v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)stateEBTotal.get(12), ":"));
				v.add(stateEBTotal.get(13));
				v.add(stateEBTotal.get(14));
				v.add(stateEBTotal.get(15));
				v.add(stateEBTotal.get(31));
				v.add(stateEBTotal.get(17));
				v.add(stateEBTotal.get(16));
				v.add(stateEBTotal.get(18));
			}
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
	}
	
	public static List<Object> getStateWiseTotalForOneMonthBasedOnCustomerIdMeta(String customerId, int month, int year){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> stateWiseWECTotal = StateMetaInfo.getStateWiseTotalForOneMonthBasedOnCustomerIdMeta(customerId, month, year);
		GlobalUtils.displayVectorMember(stateWiseWECTotal);
		for (ArrayList<Object> stateWECTotal : stateWiseWECTotal) {
			for(int i = 0; i < stateWECTotal.size(); i++){
				v.add(stateWECTotal.get(i));
			}
			tranList.add(v);
			v = new Vector<Object>();
		}
		
		return tranList;
	}

	public static List<Object> getStateWiseTotalForOneFiscalYearBasedOnCustomerId(String customerId, int fiscalYear){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		ArrayList<ArrayList<Object>> stateWiseEbTotal = StateMetaInfo.getStateWiseTotalForOneFiscalYearBasedOnCustomerId(customerId, fiscalYear);
		
		for (ArrayList<Object> stateEBTotal : stateWiseEbTotal) {
			
			v.add(stateEBTotal.get(29));
			v.add(NumberUtility.formatNumber(((Long)stateEBTotal.get(6))));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)stateEBTotal.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)stateEBTotal.get(12), ":"));
			v.add(stateEBTotal.get(13));
			v.add(stateEBTotal.get(14));
			v.add(stateEBTotal.get(15));
			v.add(stateEBTotal.get(30));
			v.add(stateEBTotal.get(17));
			v.add(stateEBTotal.get(16));
			v.add(stateEBTotal.get(18));
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;
	}
	
	/**
	 * 27 : WEC Count
	 * @param customerId
	 * @param readingDate
	 * @return
	 */
	public static List<Object> getOverallGenerationTotalForOneDayBasedOnCustomerId(String customerId, String requestDate){
		List<Object> tranList = new ArrayList<Object>();
		Vector<Object> v = new Vector<Object>();
		List<Object> overAllTotalInfo = new ArrayList<Object>();
		
		//overAllTotalInfo = CustomerMetaInfo.getOverallTotalBaseOnCustomerIdForOneDay(customerId, AdminDao.convertDateFormat(requestDate, "dd/MM/yyyy", "dd-MMM-yy"));
		overAllTotalInfo = CustomerMetaInfo.getOverallGenerationTotalForOneDayBasedOnCustomerId(customerId, AdminDao.convertDateFormat(requestDate, "dd/MM/yyyy", "dd-MMM-yy"));
		
			v.add("Overall");
			v.add(NumberUtility.formatNumber((Long)overAllTotalInfo.get(6)));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)overAllTotalInfo.get(8), ":"));
			v.add(TimeUtility.convertMinutesToTimeStringFormat((Long)overAllTotalInfo.get(12), ":"));
			v.add(overAllTotalInfo.get(13));
			v.add(overAllTotalInfo.get(14));
			v.add(overAllTotalInfo.get(15));
			v.add(overAllTotalInfo.get(17));
			v.add(overAllTotalInfo.get(16));
			tranList.add(v);
		return tranList;
	}
	
	

	

	
	
	
	
	
	
	
	
	
	
	

	public static List<String> getStateNameStateIdBasedOnWECId(String wecId) {
		List<String> s = StateMetaInfo.getStateNameStateIdBasedOnWECId(wecId);
		return null;
	}

	
	
	
	
	
	public static List<Object> getOneWECTotalForBetweenDaysBasedOnWECIdMeta(String wecId, String fromDate, String toDate){
		//List<Object> wecTotal = WECMetaInfo.getOneWECTotalForBetweenDays(wecId, fromDate, toDate);
		return WECMetaInfo.getOneWECTotalForBetweenDaysMeta(wecId, fromDate, toDate);
		/*List<Object> tranList = new ArrayList<Object>();
		if(wecTotal.size() == 2){
			tranList.add((String)wecTotal.get(0));
			tranList.add((String)wecTotal.get(1));
			return wecTotal;
		}
		tranList.add((String)wecTotal.get(0));
		tranList.add((String)wecTotal.get(1));
		tranList.add((String)wecTotal.get(2));
		tranList.add((String)wecTotal.get(3));
		tranList.add((Integer)wecTotal.get(4));
		tranList.add((String)wecTotal.get(5));
		tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(6))));
		tranList.add(NumberUtility.formatNumber(((Long)wecTotal.get(7))));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(8), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(9), ":"));
		tranList.add((String)wecTotal.get(10));
		tranList.add((java.sql.Date)wecTotal.get(11));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(12), ":"));
		tranList.add(wecTotal.get(13));
		tranList.add(wecTotal.get(14));
		tranList.add(wecTotal.get(15));
		tranList.add(wecTotal.get(16));
		tranList.add(wecTotal.get(17));
		tranList.add(wecTotal.get(18));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(19), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(20), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(21), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(22), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(23), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(24), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(25), ":"));
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat((Long)wecTotal.get(26), ":"));
		long gridFault = (Long)wecTotal.get(22) + (Long)wecTotal.get(24); 
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridFault, ":"));
		long gridShutdown = (Long)wecTotal.get(23) + (Long)wecTotal.get(25);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(gridShutdown, ":"));
		long faultHours = 	(Long)wecTotal.get(12) + (Long)wecTotal.get(19) + (Long)wecTotal.get(20) + 
							(Long)wecTotal.get(21) + (Long)wecTotal.get(22) + (Long)wecTotal.get(23) + 
							(Long)wecTotal.get(24) + (Long)wecTotal.get(25) + (Long)wecTotal.get(26);
		tranList.add(TimeUtility.convertMinutesToTimeStringFormat(faultHours, ":"));
		
		return tranList;*/
	}
	
	
}
