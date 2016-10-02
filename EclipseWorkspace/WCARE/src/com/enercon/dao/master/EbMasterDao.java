package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.enercon.dao.DaoUtility;
import com.enercon.model.graph.EbMasterVo;
import com.enercon.model.graph.Graph;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.graph.event.EbMasterEvent;
import com.enercon.model.graph.listener.EbMasterListener;
import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.WecMasterVo;
import com.enercon.model.transfer.TransferEbVo;
import com.enercon.model.utility.EbWecTypeCount;
import com.enercon.service.WecMasterService;

public class EbMasterDao {
	private final static Logger logger = Logger.getLogger(EbMasterDao.class);

	private List<EbMasterListener> listeners = new ArrayList<EbMasterListener>();
//	private Graph G = Graph.getInstance();
	private EbMasterDao(){}
	
	private static class SingletonHelper{
		public final static EbMasterDao INSTANCE = new EbMasterDao();
	}
	
	public static EbMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}

	public CustomerMasterVo get(CustomerMasterVo customer) throws SQLException{

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		List<WecMasterVo> wecs = customer.getWecs();
		String wecId = null;
		String wecName = null;
		
		try {
			conn = wcareConnector.getConnectionFromPool();

			query = 
					"Select S_wec_id, s_wecshort_descr " + 
					"from tbl_wec_master " + 
					"where S_customer_id = ? " + 
					"and s_status in ('1', '9')  " ; 

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, customer.getId());
			
			rs = prepStmt.executeQuery();
			logger.debug("Done");
			
			while (rs.next()) {
				
				wecId = rs.getString("S_WEC_ID");
				wecName = rs.getString("S_Wecshort_descr");

				WecMasterVo wecMasterVo = new WecMasterVo(wecId);
				wecMasterVo.setName(wecName);
				wecMasterVo.setCustomer(customer);
			}
			
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return customer;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}

	public IEbMasterVo get(String id) {
		return Graph.getInstance().getEbsM().get(id);
	}

	public List<IEbMasterVo> getWecActiveEbs(ICustomerMasterVo customer, ISiteMasterVo site) {
		List<IEbMasterVo> ebs = new ArrayList<IEbMasterVo>();
		
		List<IEbMasterVo> activeEbsByCustomer = getWecActiveEbs(customer);
		List<IEbMasterVo> activeEbsBySite = getActiveEbs(site);
		
		for(IEbMasterVo eb: activeEbsByCustomer)
			if(activeEbsBySite.contains(eb)) ebs.add(eb);
		
		return ebs;
	}
	
	public List<IEbMasterVo> getWecActiveEbs(ICustomerMasterVo customer) {
		Set<IEbMasterVo> ebsS = new HashSet<IEbMasterVo>();
		List<IWecMasterVo> activeWecsByCustomer = WecMasterService.getInstance().getActive(customer);
		for(IWecMasterVo wec: activeWecsByCustomer){
			ebsS.add(wec.getEb());
		}
		return new ArrayList<IEbMasterVo>(ebsS);
	}
	
	public List<IEbMasterVo> getActiveEbs(ISiteMasterVo site) {
		Set<IEbMasterVo> ebsS = new HashSet<IEbMasterVo>();
		List<IWecMasterVo> activeWecsByCustomer = WecMasterService.getInstance().getActive(site);
		for(IWecMasterVo wec: activeWecsByCustomer){
			ebsS.add(wec.getEb());
		}
		return new ArrayList<IEbMasterVo>(ebsS);
	}

	public List<EbWecTypeCount> getActiveWecTypeCount(IEbMasterVo eb) {
		WecMasterService wecService = WecMasterService.getInstance();
		List<EbWecTypeCount> wecTypeCount = new ArrayList<EbWecTypeCount>();
		Map<String, Integer> wecTypeCountM = new HashMap<String, Integer>();
		Map<String, Double> typeCapacity = new HashMap<String, Double>();
		 
		List<IWecMasterVo> wecs = wecService.getActive(eb);
		String wecType = null;
		
		for(IWecMasterVo wec: wecs){
			wecType = wec.getType();
			wecTypeCountM.put(wecType, wecTypeCountM.containsKey(wecType) ? wecTypeCountM.get(wecType) + 1 : 1);
			typeCapacity.put(wecType, wec.getCapacity());
		}
		
		for(String type : wecTypeCountM.keySet()){
			int typeCount = wecTypeCountM.get(type);
			wecTypeCount.add(new EbWecTypeCount(typeCount, type, typeCapacity.get(type) * typeCount));
		}
		
		return wecTypeCount;
	}

	public List<IEbMasterVo> getWecDisplayActiveEbs(ICustomerMasterVo customer, ISiteMasterVo site) {
		List<IEbMasterVo> ebs = new ArrayList<IEbMasterVo>();
		
		List<IEbMasterVo> wecDisplayActiveEbsByCustomer = getWecDisplayActiveEbs(customer);
		List<IEbMasterVo> activeEbsBySite = getWecDisplayActiveEbs(site);
		
		for(IEbMasterVo eb: wecDisplayActiveEbsByCustomer)
			if(activeEbsBySite.contains(eb)) ebs.add(eb);
		
		return ebs;
	}

	public List<IEbMasterVo> getWecDisplayActiveEbs(ISiteMasterVo site) {
		Set<IEbMasterVo> ebsS = new HashSet<IEbMasterVo>();
		List<IWecMasterVo> displayActiveWecsByCustomer = WecMasterService.getInstance().getDisplayActive(site);
		for(IWecMasterVo wec: displayActiveWecsByCustomer){
			ebsS.add(wec.getEb());
		}
		return new ArrayList<IEbMasterVo>(ebsS);
	}

	public List<IEbMasterVo> getWecDisplayActiveEbs(ICustomerMasterVo customer) {
		Set<IEbMasterVo> ebsS = new HashSet<IEbMasterVo>();
		List<IWecMasterVo> displayActiveWecsByCustomer = WecMasterService.getInstance().getDisplayActive(customer);
		for(IWecMasterVo wec: displayActiveWecsByCustomer){
			ebsS.add(wec.getEb());
		}
		return new ArrayList<IEbMasterVo>(ebsS);
	}

	public boolean transfer(TransferEbVo vo) throws SQLException {
		IEbMasterVo toEb = get(vo.getToEbId());
		List<ICustomerMasterVo> toCustomers = toEb.getCustomers();
		IEbMasterVo fromEb = get(vo.getFromEbId());
		List<ICustomerMasterVo> fromCustomers = fromEb.getCustomers();
		
		logger.debug(String.format("ToCustSize: %d, FromCustSize: %d", toCustomers.size(), fromCustomers.size()));
		if(toCustomers.size() == 1 && fromCustomers.size() == 1){
			SortedMap<String, Object> data = new TreeMap<String, Object>();
			
			data.put("S_REMARKS", vo.getRemark());
			data.put("S_EB_ID_TO", vo.getToEbId());
			data.put("D_TRANSFER_DATE", vo.getDateInOracleFormat());
			data.put("S_STATUS", "9");
			
			return partialUpdate(fromEb, data);
		} else {
			return false;
		}
		
	}

	private boolean partialUpdate(IEbMasterVo eb, Map<String, Object> data) throws SQLException {
		logger.debug(data);
		Connection conn = null;
		StringBuilder query = new StringBuilder();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int paramterCount = data.size();
		int index = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			query.append("UPDATE TBL_EB_MASTER SET ");
			
			for(String column: data.keySet()){
				query.append(column + " = ?, ");
			}
			
			query = new StringBuilder(query).deleteCharAt(query.length() - 2);
			query.append("WHERE S_EB_ID = ? ");
			logger.debug(eb.getId());
			prepStmt = conn.prepareStatement(new String(query));
			logger.debug(query);
			for(String column: data.keySet()){
				index++;
				prepStmt.setObject(index, data.get(column));
			}
			prepStmt.setObject(paramterCount + 1, eb.getId());
			
			int rowUpdate = prepStmt.executeUpdate();
			if(rowUpdate == 1){
				Graph.getInstance().ebUpdatePartial(eb, data);
			}
			return (rowUpdate == 1);
//			return true;
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}
	
	public boolean create(IEbMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = " Insert into TBL_EB_MASTER( S_EB_ID, S_EBSHORT_DESCR, S_EB_DESCRIPTION, "+
												  " S_STATUS, S_SITE_ID, S_CREATED_BY, " +
												  " S_LAST_MODIFIED_BY, S_FEDER_ID, S_CUSTOMER_ID ) " +
										  "values ( ? , ? , ? , " +
										          " ? , ? , ? , " +
										          " ? , ? , ? ) ";
					   						         
			
			prepStmt = conn.prepareStatement(sqlQuery);
			
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getName());
			prepStmt.setObject(3, vo.getDescription());
			
			prepStmt.setObject(4, vo.getWorkingStatus());			
			prepStmt.setObject(5, vo.getSiteId());
			prepStmt.setObject(6, vo.getCreatedBy());
			
			prepStmt.setObject(7, vo.getModifiedBy());
			prepStmt.setObject(8, vo.getFederId());
			prepStmt.setObject(9, vo.getCustomerId());
			
			int rowInserted = prepStmt.executeUpdate();
			
            return (rowInserted == 1);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean partialUpdate(IEbMasterVo vo) throws SQLException{
		
		SortedMap<String, Object> m = new TreeMap<String, Object>();
					
		m.put("S_EBSHORT_DESCR", vo.getName());
		m.put("S_EB_DESCRIPTION", vo.getDescription());
		m.put("S_STATUS", vo.getWorkingStatus());
		m.put("S_SITE_ID", vo.getSiteId());
		m.put("s_Last_Modified_By", vo.getModifiedBy());
		m.put("d_Last_Modified_Date", new java.sql.Timestamp(new Date().getTime()));
		m.put("S_FEDER_ID", vo.getWorkingStatus());
		m.put("S_CUSTOMER_ID", vo.getSiteId());
		return partialUpdate(vo, m);	
	}
	
	public boolean check(IEbMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " SELECT count(*) as COUNT " +
					    " FROM TBL_EB_MASTER " +
					    " WHERE s_ebshort_descr = ? and S_EB_DESCRIPTION = ? ";
	
			prepStmt = conn.prepareStatement(sqlQuery);	
			
			prepStmt.setObject(1, vo.getName());
			prepStmt.setObject(2, vo.getDescription());

			rs = prepStmt.executeQuery();
			while (rs.next()) {
			 if( rs.getInt("COUNT") == 1)
				 return true;
			}
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return false;
	}
	
	public void addEbMasterListener(EbMasterListener l){
		listeners.add(l);
	}
	
	public void fireEbMasterEvent(EbMasterEvent event){
		for(EbMasterListener l: listeners)
			l.handler(event);
	}
}
