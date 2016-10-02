package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.enercon.dao.DaoUtility;
import com.enercon.model.graph.Graph;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.graph.SiteMasterVo;
import com.enercon.model.graph.event.SiteMasterEvent;
import com.enercon.model.graph.listener.SiteMasterListener;
import com.enercon.model.master.FederMasterVo;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;
import com.enercon.service.master.FederMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.sun.org.apache.bcel.internal.generic.LUSHR;

public class SiteMasterDao {
	
	private static Logger logger = Logger.getLogger(SiteMasterDao.class);
	private List<SiteMasterListener> listeners = new ArrayList<SiteMasterListener>();
//	private Graph G = Graph.getInstance();

	private SiteMasterDao(){}
	
	private static class SingletonHelper{
		public final static SiteMasterDao INSTANCE = new SiteMasterDao();
	}
	
	public static SiteMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<ISiteMasterVo> getAll(ICustomerMasterVo customer, IStateMasterVo state){
		
		List<ISiteMasterVo> sitesByState = getAll(state);
		List<ISiteMasterVo> sitesByCustomer = getAll(customer);
		List<ISiteMasterVo> sites = new ArrayList<ISiteMasterVo>();
		for(ISiteMasterVo site : sitesByCustomer){
			if(sitesByState.contains(site)){
				sites.add(site);
			}
		}
		
		return sites;
	}

	private List<ISiteMasterVo> getAll(ICustomerMasterVo customer) {
		return customer.getSites();
	}

	private List<ISiteMasterVo> getAll(IStateMasterVo state) {
		return state.getSites();
	}
	
	public List<ISiteMasterVo> getActive(ICustomerMasterVo customer) {
		List<ISiteMasterVo> allSites = getAll(customer);
		
		Set<ISiteMasterVo> activeSitesS = new HashSet<ISiteMasterVo>();
		for(ISiteMasterVo site: allSites){
			for(IWecMasterVo wec: site.getWecs()){
				if(wec.getStatus().equals("1") && wec.getCustomer().equals(customer)){
					activeSitesS.add(site);
				}
			}
		}
		List<ISiteMasterVo> activeSites = new ArrayList<ISiteMasterVo>(activeSitesS);
		return activeSites;
	}
	
	public List<ISiteMasterVo> getActive(IStateMasterVo state) {
		List<ISiteMasterVo> allSites = getAll(state);
		
		Set<ISiteMasterVo> activeSitesS = new HashSet<ISiteMasterVo>();
		for(ISiteMasterVo site: allSites){
			for(IWecMasterVo wec: site.getWecs()){
				if(wec.getStatus().equals("1") && wec.getState().equals(state)){
					activeSitesS.add(site);
				}
			}
		}
		
		List<ISiteMasterVo> activeSites = new ArrayList<ISiteMasterVo>(activeSitesS);
		return activeSites;
	}
	
	public static void main(String[] args) {
		List<FederMasterVo> feders = null;
		try {
			feders = FederMasterService.getInstance().getAll();
			List<ISiteMasterVo> sites = SiteMasterService.getInstance().populate(feders);
			/*List<IAreaMasterVo> areas = AreaMasterService.getInstance().populate(sites);*/
			/*List<IStateMasterVo> states = StateMasterService.getInstance().populate(areas);*/
			List<IStateMasterVo> states = StateMasterService.getInstance().populate(sites);
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT); 
			SimpleBeanPropertyFilter federFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id");
			SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "feders");
			SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "sites");
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "areas");
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
			propertyFilter.addFilter("federMasterVo", federFilter);
			propertyFilter.addFilter("siteMasterVo", siteFilter);
			propertyFilter.addFilter("areaMasterVo", areaFilter);
			propertyFilter.addFilter("stateMasterVo", stateFilter);
			
		    FilterProvider statesFilter = propertyFilter;
		    
			// Converting List of Java Object to List of Javascript Object
			String stateJson = objectMapper.writer(statesFilter).writeValueAsString(states);
			
			
			logger.debug(stateJson);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (JsonProcessingException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		
		
	}

	public List<ISiteMasterVo> getActive(ICustomerMasterVo customer, IStateMasterVo state) {
		List<ISiteMasterVo> activeSitesByState = getActive(state);
		List<ISiteMasterVo> activeSitesByCustomer = getActive(customer);
		List<ISiteMasterVo> activeSitesByStateCustomer = new ArrayList<ISiteMasterVo>();
		for(ISiteMasterVo siteByState: activeSitesByState){
			if(activeSitesByCustomer.contains(siteByState)){
				activeSitesByStateCustomer.add(siteByState);
			}
		}
//		logger.debug("Active Sites By State and Customer Size: " + activeSitesByStateCustomer.size());
		return activeSitesByStateCustomer;
	}

	public ISiteMasterVo get(String id) {
		return Graph.getInstance().getSitesM().get(id);
	}

	public List<ISiteMasterVo> getAll() {
		return new ArrayList<ISiteMasterVo>(Graph.getInstance().getSitesM().values());
	}
	
	public boolean create(SiteMasterVo vo1) throws SQLException, CloneNotSupportedException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		SiteMasterVo vo = (SiteMasterVo) vo1.clone();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into TBL_SITE_MASTER(S_SITE_ID, S_SITE_NAME, S_STATE_ID, S_CREATED_BY, D_CREATED_DATE ," +
												  " S_LAST_MODIFIED_BY ,  S_SITE_CODE, S_SITE_INCHARGE, S_SITE_ADDRESS, S_AREA_ID ) " +											 										 
			   						       "values( ?, ?, ?, ?, localtimestamp," +
					   						     "  ?,  ?, ?, ?, ? ) ";				   						   				   						    
					   						     
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getName());			
			prepStmt.setObject(3, vo.getStateId());
			prepStmt.setObject(4, vo.getCreatedBy());
			
			prepStmt.setObject(5, vo.getModifiedBy());			
			prepStmt.setObject(6, vo.getCode());			
			prepStmt.setObject(7, vo.getIncharge());
			prepStmt.setObject(8, vo.getAddress());
			prepStmt.setObject(9, vo.getAreaId());
			
			int rowInserted = prepStmt.executeUpdate();
			
			if(rowInserted == 1){
				Graph.getInstance().siteCreated(vo);
			}
			
            return (rowInserted == 1);
			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean update(SiteMasterVo vo1) throws SQLException, CloneNotSupportedException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		SiteMasterVo vo = (SiteMasterVo) vo1.clone();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " UPDATE TBL_SITE_MASTER " +
						" SET  S_SITE_NAME = ?,  S_LAST_MODIFIED_BY = ?, " +						
						" S_SITE_CODE = ?, S_SITE_INCHARGE = ?, S_SITE_ADDRESS = ?  " +						
						" WHERE S_SITE_ID = ?  " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);
			
			prepStmt.setObject(1, vo.getName());
			prepStmt.setObject(2, vo.getModifiedBy());
			
			prepStmt.setObject(3, vo.getCode());
			prepStmt.setObject(4, vo.getIncharge());
			prepStmt.setObject(5, vo.getAddress());
			
			prepStmt.setObject(6, vo.getId());
			
				
			int rowUpdated = prepStmt.executeUpdate();
			if(rowUpdated == 1){
				Graph.getInstance().siteUpdated(vo);
			}
			return(rowUpdated == 1);
			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public List<ISiteMasterVo> getAll(String userId) throws SQLException {
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List<ISiteMasterVo> sites = new ArrayList<ISiteMasterVo>();
		List<String> siteIds = new ArrayList<String>();		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "SELECT S_SITE_ID " + 
					"FROM TBL_STATE_SITE_RIGHTS " + 
					"where S_USER_ID = ?  " + 
					"group by S_site_id  " ; 

			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setObject(1, userId);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				String siteId = rs.getString("S_SITE_ID");
				siteIds.add(siteId);
				
			}
			sites = get(siteIds);
			
			
			return sites;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	private List<ISiteMasterVo> get(List<String> siteIds) {
		Set<ISiteMasterVo> sitesS = new HashSet<ISiteMasterVo>();
		for(String siteId: siteIds){
			sitesS.add(get(siteId));
		}
		
		return new ArrayList<ISiteMasterVo>(sitesS);
	}

	public List<ISiteMasterVo> populate(List<FederMasterVo> feders) {
		List<ISiteMasterVo> sites = getAll();
		
		Map<String, ISiteMasterVo> sitesM = Maps.uniqueIndex(
				sites, new Function<ISiteMasterVo, String>() {
					
					public String apply(ISiteMasterVo site) {
						site.getFeders().clear();
						return site.getId();
					}
					
				});
		
		for(FederMasterVo feder: feders){
			String siteId = feder.getSiteId();
			
			if(siteId != null && sitesM.containsKey(siteId)){
				feder.setSite(sitesM.get(siteId));
			}
				
		}
		
		return sites;
	}
	
	public List<SiteMasterVo> getRemarks() throws SQLException {
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List<SiteMasterVo> vos = new ArrayList<SiteMasterVo>();
		SiteMasterVo vo = null;	
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "SELECT * FROM TBL_SITE_MASTER WHERE S_SITE_REMARKS IS NOT NULL " ; 

			
			prepStmt = conn.prepareStatement(query);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				vo = new SiteMasterVo();
				
				vo.setId(rs.getString("S_SITE_ID"));
				vo.setName(rs.getString("S_SITE_NAME"));
				vo.setRemark(rs.getString("S_SITE_REMARKS"));
				
				vos.add(vo);
			}
			return vos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean checkRemark(SiteMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " SELECT count(*) as COUNT FROM TBL_SITE_MASTER" +
						" WHERE S_SITE_ID = ? AND D_LAST_MODIFIED_DATE = TO_DATE(SYSDATE) AND S_SITE_REMARKS = ? " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);	
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getRemark());

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
		
	public boolean updateRemark(SiteMasterVo vo) throws SQLException{
		
		SortedMap<String, Object> m = new TreeMap<String, Object>();
		
		//vo.setModifiedBy(vo.getModifiedBy());
		m.put("S_LAST_MODIFIED_BY", vo.getModifiedBy());
		m.put("S_SITE_REMARKS", vo.getRemark());
		
		return partialUpdate(vo,m);	
	}
	
	private boolean partialUpdate(SiteMasterVo vo , Map<String, Object> m) throws SQLException{
		Connection conn = null;
		StringBuilder query = new StringBuilder();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int paramterCount = m.size();
		int index = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			query.append("UPDATE TBL_SITE_MASTER SET ");
			
			for(String column: m.keySet()){
				query.append(column + " = ?, ");
			}
			//Removing Last Comma
			query = new StringBuilder(query).deleteCharAt(query.length() - 2);
			query.append("WHERE S_SITE_ID = ? ");
			logger.debug("S_SITE_ID :: "+vo.getId());
			prepStmt = conn.prepareStatement(new String(query));
			logger.debug("query :: "+query);
			for(String column: m.keySet()){
				index++;
				prepStmt.setObject(index, m.get(column));
			}
			prepStmt.setObject(paramterCount + 1, vo.getId());
			
			int rowUpdate = prepStmt.executeUpdate();
			return (rowUpdate == 1);
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}
	
	public void addSiteMasterListener(SiteMasterListener listener){
		listeners.add(listener);
	}
	
	public void fireSiteMasterEvent(SiteMasterEvent event){
		for(SiteMasterListener l: listeners)
			l.handler(event);
	}
}
