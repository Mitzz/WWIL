package com.enercon.customer.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.customer.dao.CustomerDao;
import com.enercon.customer.dao.CustomerDaoNew;
import com.enercon.global.utility.StopWatch;

public class CustomerUtil {
    /**
     * For Logging Purpose.
     */
    private static Logger logger = Logger.getLogger(CustomerUtil.class);

    public CustomerUtil() {
    }

   
   public List getOverallTotal(String custid,String rdate,String RType) throws Exception {
   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
        try {

           CustomerDao custDAO = new CustomerDao();
          	List transList = custDAO.getOverallTotal(custid,rdate,RType);
            return transList;
        	} catch (Exception exp) {
            logger.error("Error  ");
            throw exp;
        }
    }
   public List getMessage(String custid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMessage(custid); 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   public List getMessageByLogid(String custid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMessageByLogid(custid); 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   public List getState() throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getState(); 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   public List getState(String wecid,String ebid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getState(wecid,ebid); 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   public List getSWecType(String wectype) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECType(wectype); 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }  
   public List getOverallTotalAdmin(String custid,String rdate,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getOverallTotalAdmin(custid,rdate,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   public List getCustomerDetail(String loginid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getCustomerDetail(loginid); 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   public List getLoginHistoryDetail() throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getLoginHistoryDetail(); 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   public List getStateTotal(String custid,String rdate,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getStateTotal(custid,rdate,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   public List getSubstationTotal(String custid,String rdate,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSubstationTotal(custid,rdate,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
    
   
   public List getStateTotalByLogid(String custid,String rdate,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getStateTotalByLogid(custid,rdate,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   
   /*public List getERDADetail(String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getERDADetail(rdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }*/
    
   
   public List getEBDataDetail(String fdate,String tdate,String site,String state,String custid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBDataDetail(fdate,tdate,site,state,custid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   public List getBillDataDetail(String fdate,String tdate,String site,String state,String custid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getBillDataDetail(fdate,tdate,site,state,custid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   public List getMailData(String custid,String rdate,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMailData(custid,rdate,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   public List getMailDataCustomer(String custid,String rdate,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMailDataCustomer(custid,rdate,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   public List getMailDataCLP(String custid,String rdate,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMailDataCLP(custid,rdate,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   
   public List getStateTotalAdmin(String custid,String rdate,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getStateTotalAdmin(custid,rdate,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
   public List getStateTotalByID(String custid,String rdate,String stateid,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getStateTotalByID(custid,rdate,stateid,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getSiteTotal(String custid,String rdate,String stateid,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSiteTotal(custid,rdate,stateid,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getSiteTotalAdmin(String custid,String rdate,String stateid,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSiteTotalAdmin(custid,rdate,stateid,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getEBHeading(String custid,String rdate,String stateid,String siteid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBHeading(custid,rdate,stateid,siteid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getWECCurtailmentList(String custid,String fromDate,String toDate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECCurtailmentList(custid,fromDate,toDate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getNearByWECCurtailmentList(String wecid,String fromDate,String toDate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getNearByWECCurtailmentList(wecid,fromDate,toDate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getEBHeadingSite(String custid,String rdate,String stateid,String siteid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBHeadingSite(custid,rdate,stateid,siteid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getEBHeadingState(String custid,String rdate,String stateid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBHeadingState(custid,rdate,stateid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getEBHeadingTotal(String custid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBHeadingTotal(custid,rdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }


public List getBillingDetail(String custid,String month,String year,String site,String state)throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getBillingDetail(custid, month, year,site,state);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getEBHeadingSiteWise(String stateid,String siteid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBHeadingSiteWise(stateid,siteid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getMPRHeading(String stateid,String WEC) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMPRHeading(stateid,WEC);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getSubMPRHeading(String stateid,String WEC) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSubMPRHeading(stateid,WEC);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getCustMPRHeading(String cid,String stateid,String site) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getCustMPRHeading(cid,stateid,site);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getMPRHeading1(String wecid,String ebid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMPRHeading1(wecid,ebid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getFindWECByEB(String ebid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getFindWECByEB(ebid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getSiteMPRHeading(String stateid,String WEC,String siteid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSiteMPRHeading(stateid,WEC,siteid); 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getState(String custid,String rdate,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getState(custid,rdate,RType);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getSite(String custid,String rdate,String stateid,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
    try {

       CustomerDao custDAO = new CustomerDao();
      	List transList = custDAO.getSite(custid,rdate,stateid,RType);
        return transList;
    	} catch (Exception exp) {
        logger.error("Error  ");
        throw exp;
    }
}

public List getWEC(String custid,String rdate,String siteid,String RType) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
 try {

    CustomerDao custDAO = new CustomerDao();
   	List transList = custDAO.getWEC(custid,rdate,siteid,RType);
     return transList;
 	} catch (Exception exp) {
     logger.error("Error  ");
     throw exp;
 }
}
public List getWECDataCum(String ebid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
try {

 CustomerDao custDAO = new CustomerDao();
	List transList = custDAO.getWECDataCum(ebid,rdate);
  return transList;
	} catch (Exception exp) {
  logger.error("Error  ");
  throw exp;
}
}

public List getEBWiseTotalCum(String ebid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
try {

CustomerDao custDAO = new CustomerDao();
	List transList = custDAO.getEBWiseTotalCum(ebid,rdate);
return transList;
	} catch (Exception exp) {
logger.error("Error  ");
throw exp;
}
}

public List getWECData(String ebid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECData(ebid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getWECDataNew(String ebid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECDataNew(ebid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getWECDataUpload(String ebid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECDataUpload(ebid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getWECDataAdmin(String ebid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECDataAdmin(ebid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getFeedbackReport() throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getFeedbackReport();
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getWECDataCompare(String wecid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECDataCompare(wecid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getCompareData(String ebid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getCompareData(ebid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }



public List getCompareWECData(String ebid,String wecid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getCompareWECData(ebid,wecid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    } 

public List getCustomerDetail(String custid,String stateid,String siteid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getCustomerDetail(custid,stateid,siteid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    } 


public List getWECDateWise(String ebid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECDateWise(ebid,rdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getWECDetail(String ebid,String wecid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECDetail(ebid,wecid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getWECDetailAdmin(String ebid,String wecid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECDetailAdmin(ebid,wecid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getStateWiseDash(String stateid, String fdate, String tdate)  throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getStateWiseDash(stateid,fdate,tdate) ; 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getStateWiseDashAvg(String stateid, String fdate, String tdate)  throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getStateWiseDashAvg(stateid,fdate,tdate) ; 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }


public List getStateWiseAverage(String fdate, String tdate)  throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getStateWiseAverage(fdate,tdate) ; 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getSiteWiseAverage(String id,String fdate, String tdate)  throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSiteWiseAverage(id,fdate,tdate) ; 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getSiteWiseAverageGeneration(String id,String fdate, String tdate)  throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSiteWiseAverageGeneration(id,fdate,tdate) ; 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getWECTypeWiseAverageGeneration(String id,String fdate, String tdate)  throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECTypeWiseAverageGeneration(id,fdate,tdate) ; 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getWECTypeWiseAverageGenerationAvg(String id,String fdate, String tdate)  throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECTypeWiseAverageGenerationAvg(id,fdate,tdate) ; 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getSiteWiseAverageGenerationAvg(String id,String fdate, String tdate)  throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSiteWiseAverageGenerationAvg(id,fdate,tdate) ; 
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getMPRDetailAdmin(String siteid,String customerid,String fdate,String tdate,String wectype) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMPRDetailAdmin(siteid,customerid,fdate,tdate,wectype);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getSubMPRDetailAdmin(String siteid,String customerid,String fdate,String tdate,String wectype) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSubMPRDetailAdmin(siteid,customerid,fdate,tdate,wectype);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getCpmDetailAdmin(String siteid,String customerid,String fdate,String wectype,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	            CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getCpmDetailAdmin(siteid,customerid,fdate,wectype,type);  
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getQueryDetail(String userid,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getQueryDetail(userid,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getMPRSerachByWec(String siteid,String customerid,String fdate,String tdate,String wectype,String wecid,String ebid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMPRSerachByWec(siteid,customerid,fdate,tdate,wectype,wecid,ebid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getMPRSerachByWecTotal(String siteid,String customerid,String fdate,String tdate,String wectype,String wecid,String ebid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMPRSerachByWecTotal(siteid,customerid,fdate,tdate,wectype,wecid,ebid);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getFilterDetail(String stateid,String siteid,String fdate,String tdate,String wectype,String fobject,String ftype,String firstparam,String secparam,String ebobject) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getFilterDetail(stateid,siteid,fdate,tdate,wectype,fobject,ftype,firstparam,secparam,ebobject);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }


public List getWecSelectedListDetail(String weclist,String fdate,String tdate,String ptype) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWecSelectedListDetail(weclist,fdate,tdate,ptype);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getEBSelectDetail(String weclist,String fdate,String tdate,String ptype) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBSelectDetail(weclist,fdate,tdate,ptype);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getEBSelectedListDetail(String weclist,String fdate,String tdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBSelectedListDetail(weclist,fdate,tdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getCustFilterDetail(String fdate,String tdate,String fobject,String ftype,String firstparam,String secparam,String userid,String ebobject) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getCustFilterDetail(fdate,tdate,fobject,ftype,firstparam,secparam,userid,ebobject);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getMPRDetailAdminTotal(String siteid,String customerid,String fdate,String tdate,String wectype) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getMPRDetailAdminTotal(siteid,customerid,fdate,tdate,wectype);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getCMPDetailAdminTotal(String siteid,String customerid,String fdate,String wectype,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getCMPDetailAdminTotal(siteid,customerid,fdate,wectype,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getWECMonthwise(String ebid,String wecid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECMonthwise(ebid,wecid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getWECSitewise(String siteid,String stateid,String custid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECSitewise(siteid,stateid,custid,rdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getWECStatewise(String stateid,String custid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECStatewise(stateid,custid,rdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getWECMonthwiseAdmin(String ebid,String wecid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECMonthwiseAdmin(ebid,wecid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getWECDetailTotal(String ebid,String wecid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECDetailTotal(ebid,wecid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getWECDetailTotalAdmin(String ebid,String wecid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getWECDetailTotalAdmin(ebid,wecid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getEBData(String ebid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBData(ebid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    } 
public List getEBDataCum(String ebid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBDataCum(ebid,rdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    } 
public List getEBWiseTotal(String ebid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBWiseTotal(ebid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getSiteWiseTotal(String siteid,String custid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getSiteWiseTotal(siteid,custid,rdate,type); 
	            return transList; 
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getStateWiseTotal(String stateid,String custid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getStateWiseTotal(stateid,custid,rdate,type); 
	            return transList; 
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getUploadStatus(String userid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
//	           StopWatch watch = new StopWatch();
	          	List transList = custDAO.getUploadStatus(userid,rdate);
//	          	System.out.println(watch.getElapsedTimeInSeconds());
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getStateSiteUploadStatus(String userid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getStateSiteUploadStatus(userid,rdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getEmployeeEmailList() throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEmployeeEmailList();
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getEBWiseTotalAdmin(String ebid,String rdate,String type) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getEBWiseTotalAdmin(ebid,rdate,type);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getCustList() throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getCustList();
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public String getCustemail(String userid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	           String email = custDAO.getCustemail(userid);
	            return email;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public String getEBRemarks(String ebid,String readdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	           String email = custDAO.getEBRemarks(ebid,readdate);
	            return email;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getCustList(String Userid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	           
	           List transList = custDAO.getCustList(Userid);
	            return transList;
	            
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }

public List getSiteList(String Userid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	           
	           List transList = custDAO.getSiteList(Userid);
	            return transList;
	            
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getStateList(String Userid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	           
	           List transList = custDAO.getStateList(Userid);
	            return transList;
	            
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }


public List getWecTypeList(String Userid) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	           
	           List transList = custDAO.getWecTypeList(Userid);
	            return transList;
	            
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }



public List getIPAddress(String Userid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	           
	           List transList = custDAO.getIPAddress(Userid,rdate);
	            return transList;
	            
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getERDADetail1(String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          	List transList = custDAO.getERDADetail(rdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getERDAWECWiseDetail(String custid, String stateid,String rdate) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDao custDAO = new CustomerDao();
	          
	          	List transList = custDAO.getERDAWECWiseDetail(custid,stateid,rdate);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
public List getInitialYearGen(String custid,String fdate, String tdate, String type,String rtype) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
	        try {

	           CustomerDaoNew custDAO = new CustomerDaoNew();
	          	List transList = custDAO.getInitialYearGen(custid,fdate,tdate,type,rtype);
	            return transList;
	        	} catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
	public List getInitialYearDailyGen(String custid,String fdate, String tdate, String type,String rtype) throws Exception {
	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
		try {
			CustomerDaoNew custDAO = new CustomerDaoNew();
	        List transList = custDAO.getInitialYearDailyGen(custid,fdate,tdate,type,rtype);
	        return transList;
	        } catch (Exception exp) {
	            logger.error("Error  ");
	            throw exp;
	        }
	    }
	public int getTotalUnpublishedData(String ardate){
		int ttlUnpublishedData = 0;
		try {
			CustomerDaoNew custDAO = new CustomerDaoNew();
		 	ttlUnpublishedData = custDAO.getTotalUnpublishedData(ardate);
		 
		}catch(Exception e){
			e.printStackTrace();
		}
		return ttlUnpublishedData;
	}
	
	public List getMissingScadaDataReport1(String stateName, String areaName, String siteName, String reportDate) throws Exception {	   
	    try {
	        CustomerDao custDAO = new CustomerDao();
	      	List transList = custDAO.getMissingScadaDataReport1(stateName,areaName,siteName,reportDate);
	        return transList;
	    	} catch (Exception exp) {
	        logger.error("Error  ");
	        throw exp;
	    }
	}
	
	public List getMissingScadaDataReport(String stateName, String areaName, String siteName, String reportDate) throws Exception {	   
	    try {
	        CustomerDao custDAO = new CustomerDao();
	      	List transList = custDAO.getMissingScadaDataReport(stateName,areaName,siteName,reportDate);
	        return transList;
	    	} catch (Exception exp) {
	        logger.error("Error  ");
	        throw exp;
	    }
	}
	public StringBuffer getWECByEBId(String ebId,String rdate) throws Exception{
		try {
	    	
			CustomerDao custDAO = new CustomerDao();
	      	List transList = custDAO.getWECByEbId(ebId,rdate);
	      	StringBuffer htmlFormat = new StringBuffer();
	      	int ii = 0;
	      	for (Object object : transList) {
	      		
	      		if(ii % 2 == 0){
	      			htmlFormat.append("\t\t\t\t<TR class=TableRow1>\n");
	      		}
	      		else{
	      			htmlFormat.append("\t\t\t\t<TR class=TableRow2>\n");
	      		}
	      		ii++;
				htmlFormat.append("\t\t\t\t\t<td class=TableCell1 width='14.28%'>" + object +"</td>\n");
				htmlFormat.append("\t\t\t\t\t<td class=TableCell width='14.28%'>" + "-" +"</td>\n");
				htmlFormat.append("\t\t\t\t\t<td class=TableCell width='14.28%'>" + "-" +"</td>\n");
				htmlFormat.append("\t\t\t\t\t<td class=TableCell width='14.28%'>" + "-" +"</td>\n");
				htmlFormat.append("\t\t\t\t\t<td class=TableCell width='14.28%'>" + "-" +"</td>\n");
				htmlFormat.append("\t\t\t\t\t<td class=TableCell width='14.28%'>" + "-" +"</td>\n");
				htmlFormat.append("\t\t\t\t\t<td class=TableCell width='14.28%'>" + "-" +"</td>\n");
				htmlFormat.append("\t\t\t\t</TR>\n");
			}
	      		
	      	return htmlFormat;
	    	} catch (Exception exp) {
	        logger.error("Error");
	        throw exp;
	    }
	}
}

