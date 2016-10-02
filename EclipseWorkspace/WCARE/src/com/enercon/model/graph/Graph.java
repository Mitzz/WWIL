package com.enercon.model.graph;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DateUtility;
import com.enercon.model.graph.event.AreaMasterEvent;
import com.enercon.model.graph.event.CustomerMasterEvent;
import com.enercon.model.graph.event.EbMasterEvent;
import com.enercon.model.graph.event.SiteMasterEvent;
import com.enercon.model.graph.event.StateMasterEvent;
import com.enercon.model.graph.event.WecMasterEvent;
import com.enercon.model.graph.listener.AreaMasterListener;
import com.enercon.model.graph.listener.CustomerMasterListener;
import com.enercon.model.graph.listener.EbMasterListener;
import com.enercon.model.graph.listener.SiteMasterListener;
import com.enercon.model.graph.listener.StateMasterListener;
import com.enercon.model.graph.listener.WecMasterListener;
import com.enercon.spring.dao.master.AreaMasterDao;
import com.enercon.spring.dao.master.CustomerMasterDao;
import com.enercon.spring.dao.master.EbMasterDao;
import com.enercon.spring.dao.master.SiteMasterDao;
import com.enercon.spring.dao.master.StateMasterDao;
import com.enercon.spring.dao.master.WecMasterDao;

public class Graph implements Serializable, StateMasterListener, AreaMasterListener, SiteMasterListener, CustomerMasterListener, EbMasterListener, WecMasterListener{
	
	private static final long serialVersionUID = -9097892330823072097L;
	private List<IPlantMasterVo> plants = new ArrayList<IPlantMasterVo>();
	private List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
	private List<ICustomerMasterVo> customers = new ArrayList<ICustomerMasterVo>();
	private List<IEbMasterVo> ebs = new ArrayList<IEbMasterVo>();
	private List<ISiteMasterVo> sites = new ArrayList<ISiteMasterVo>();
	private List<IAreaMasterVo> areas = new ArrayList<IAreaMasterVo>();
	private List<IStateMasterVo> states = new ArrayList<IStateMasterVo>();
	
	private Map<String, IPlantMasterVo> plantsM = new HashMap<String, IPlantMasterVo>();
	private Map<String, IWecMasterVo> wecsM = new HashMap<String, IWecMasterVo>();
	private Map<String, ICustomerMasterVo> customersM = new HashMap<String, ICustomerMasterVo>();
	private Map<String, IEbMasterVo> ebsM = new HashMap<String, IEbMasterVo>();
	private Map<String, ISiteMasterVo> sitesM = new HashMap<String, ISiteMasterVo>();
	private Map<String, IAreaMasterVo> areasM = new HashMap<String, IAreaMasterVo>();
	private Map<String, IStateMasterVo> statesM = new HashMap<String, IStateMasterVo>();
	
	private static Logger logger = Logger.getLogger(Graph.class);
	
	private static class SingletonHelper{
        private static final Graph INSTANCE = new Graph();
    }
     
    public static Graph getInstance(){
        return SingletonHelper.INSTANCE;
    }
	
	private Graph(){
		logger.debug("Graph Constructor");
		try {
			populateGraph();
			populateGraphAsListener();
			populateStateMasterAsListener(null);
			populateAreaMasterAsListener(null);
			populateSiteMasterAsListener(null);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
	}
	
	private void populateStateMasterAsListener(IStateMasterVo state){
		SiteMasterDao siteD = SiteMasterDao.getInstance();
		EbMasterDao ebD =  EbMasterDao.getInstance();
		WecMasterDao wecD =  WecMasterDao.getInstance();
		
		if(state != null){
			siteD.addSiteMasterListener(state);
			ebD.addEbMasterListener(state);
			wecD.addWecMasterListener(state);
		} else {
			for(IStateMasterVo existingState: statesM.values()){
				siteD.addSiteMasterListener(existingState);
				ebD.addEbMasterListener(existingState);
				wecD.addWecMasterListener(existingState);
			}
		}
	}
	
	private void populateAreaMasterAsListener(IAreaMasterVo area){
		EbMasterDao ebD =  EbMasterDao.getInstance();
		WecMasterDao wecD =  WecMasterDao.getInstance();
		
		if(area != null){
			ebD.addEbMasterListener(area);
			wecD.addWecMasterListener(area);
		} else {
			for(IAreaMasterVo existingArea: areasM.values()){
				ebD.addEbMasterListener(existingArea);
				wecD.addWecMasterListener(existingArea);
			}
		}
	}
	
	private void populateSiteMasterAsListener(ISiteMasterVo site){
		WecMasterDao wecD =  WecMasterDao.getInstance();
		
		if(site != null){
			wecD.addWecMasterListener(site);
		} else {
			for(ISiteMasterVo existingSite: sitesM.values()){
				wecD.addWecMasterListener(existingSite);
			}
		}
	}
	
    private void populateGraphAsListener() {
    	StateMasterDao stateD = StateMasterDao.getInstance();
		AreaMasterDao areaD = AreaMasterDao.getInstance();
		SiteMasterDao siteD = SiteMasterDao.getInstance();
		CustomerMasterDao customerD =  CustomerMasterDao.getInstance();
		EbMasterDao ebD =  EbMasterDao.getInstance();
		WecMasterDao wecD =  WecMasterDao.getInstance();
		
		stateD.addStateMasterListener(this);
		areaD.addAreaMasterListener(this);
		siteD.addSiteMasterListener(this);
		ebD.addEbMasterListener(this);
		customerD.addCustomerMasterListener(this);
		wecD.addWecMasterListener(this);
		
	}

	public Graph initialize() throws SQLException{
    	logger.warn("Re-initialize");
    	flush().populateGraph();
    	logger.warn("Re-initialize Done");
    	return this;
    }
    
    public Graph flush(){
    	plantsM = new HashMap<String, IPlantMasterVo>();
    	wecsM = new HashMap<String, IWecMasterVo>();
    	customersM = new HashMap<String, ICustomerMasterVo>();
    	ebsM = new HashMap<String, IEbMasterVo>();
    	sitesM = new HashMap<String, ISiteMasterVo>();
    	areasM = new HashMap<String, IAreaMasterVo>();
    	statesM = new HashMap<String, IStateMasterVo>();
    	return this;
    }


    public Graph populateGraph() throws SQLException {
		
		long start = System.currentTimeMillis();
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		WecMasterVo wec;
		PlantMasterVo plant;
		CustomerMasterVo customer;
		EbMasterVo eb;
		SiteMasterVo site;
		AreaMasterVo area;
		StateMasterVo state;
		
		/*WecMaster*/
		String wecId = null;
		String wecName = null;
		String wecScadaStatus = null;
		String wecStatus = null; 
		Double wecCapacity = null;
		String technicalNo = null;
		String commissionDate = null;
		String wecType = null;
		String wecFoundationNo = null;
		String wecShow = null;
		String wecRemark = null;
		String wecMultiFactor = null; 
		String wecGenComm = null;
		Double wecCostPerUnit = null;
		String wecMachineAvailability= null;
		String wecExtGridAvailability= null;
		String wecIntGridAvailability= null;
		
		/*PlantMaster*/
		String plantNo = null;
		String locationNo = null;
		
		/*EbMaster*/
		String ebId = null;
		String ebName = null;
		String ebWorkingStatus = null;
		String ebDescription = null;
		
		/*SiteMaster*/
		String siteId = null;
		String siteName = null;
		String siteCode = null;
		String siteIncharge = null;
		String siteAddress = null;
		String siteRemark = null;
		
		/*AreaMaster*/
		String areaId = null;
		String areaName = null;
		String areaCode = null;
		String areaIncharge = null;
		
		/*StateMaster*/
		String stateId = null;
		String stateName = null;
		String stateSapCode = null;
		
		/*CustomerMaster*/
		String customerId = null;
		String customerName = null;
		String customerSapCode = null;
		String customerPhoneNo = null;
		String customerCellNo = null;
		String customerFaxNo = null;
		String customerContactPerson = null; 
		String customerMarketingPerson = null;
		String email = null;
		String customerActive = null;
		String loginMasterId = null;
		
		try {
			conn = WcareConnector.wcareConnector.getConnectionFromPool();
			query = 
					"Select wec.S_wec_id, wec.s_wecshort_descr, wec.s_scada_flag as WecScadaStatus, wec.s_status as WecStatus, wec.N_wec_capacity as WecCapacity, wec.S_TECHNICAL_NO as technicalNo, wec.D_COMMISION_DATE as CommissionDate, wec.S_WEC_TYPE as wecType,wec.S_FOUND_LOC as foundationNo, wec.S_show as WecShow, wec.S_remarks as WecRemark,wec.N_MULTI_FACTOR as WecMultiFactor ,wec.N_GEN_COMM as WecGenComm, wec.N_COST_PER_UNIT as WecCostPerUnit,wec.N_MAC_AVA as WecMachineAvailability, wec.N_EXT_AVA as WecExtGridAvailability, wec.N_INT_AVA as WecIntGridAvailability," +  
					"plant.s_location_no, plant.s_plant_no, " + 
					"customer.S_customer_id, customer.S_customer_name, customer.S_SAP_CUSTOMER_CODE, customer.S_PHONE_NUMBER, customer.S_CELL_NUMBER, customer.S_FAX_NUMBER, " +
					"customer.S_CUSTOMER_CONTACT, customer.S_MARKETING_PERSON, customer.S_EMAIL, customer.S_ACTIVE, customer.S_LOGIN_MASTER_ID, eb.S_eb_id, eb.s_ebshort_descr, eb.S_Status as EbWorkingStatus, eb.S_eb_description as ebDescription, " + 
					"site.S_site_id, site.s_site_name,site.S_site_code as siteCode, site.S_SITE_INCHARGE as siteIncharge, site.S_SITE_ADDRESS as siteAddress, site.S_site_remarks as siteRemark,  " + 
					"area.S_area_id, area.S_area_name, area.S_area_code as areaCode, area.S_area_incharge_id as areaInCharge, " + 
					"state.s_state_id, state.s_state_name, state.s_sap_state_code as stateSapCode " + 
					"from scadadw.tbl_plant_master plant right outer join tbl_wec_master wec on wec.S_technical_no = plant.s_serial_no  " + 
					"      full outer join tbl_eb_master eb on eb.S_eb_id = wec.S_eb_id " + 
					"      full outer join tbl_site_master site on eb.S_site_id = site.S_site_id " + 
					"      full outer join tbl_area_master area on site.S_area_id = area.S_area_id " + 
					"full outer join tbl_state_master state on area.S_state_id = state.S_state_id " +
					"full outer join tbl_customer_master customer on wec.S_customer_id = customer.S_customer_id " ;
					/*"and customer.S_Customer_id in (Select S_customer_id " +
													"from tbl_customer_master " +
													"where (upper(S_customer_name) like '%MADRAS%' " +
													"or  upper(S_customer_name) like '%DRAVID%' " +
													"or  upper(S_customer_name) like '%CLP%')) " ;*/
			
			prepStmt = conn.prepareStatement(query);

			rs = prepStmt.executeQuery();
			logger.debug("Result Set Done");
			while (rs.next()) {
				/*Initialize*/
				plant = null;
				wec = new WecMasterVo();
				customer = new CustomerMasterVo();
				eb = new EbMasterVo();
				site = new SiteMasterVo();
				area = new AreaMasterVo();
				state = new StateMasterVo();
				
				/*State Master*/
				stateId = rs.getString("S_state_ID");
				if(stateId != null){
					stateName = rs.getString("S_state_name");
					stateSapCode = rs.getString("stateSapCode");
					
					state.setId(stateId);
					state.setName(stateName);
					state.setSapCode(stateSapCode);
					
					if(!statesM.containsKey(stateId)) {
						statesM.put(stateId, state);
//						siteDao.addSiteMasterListener(state);
//						ebDao.addEbMasterListener(state);
//						wecDao.addWecMasterListener(state);
//						customerDao.addCustomerListener(state);
					}
				
				}
				
				/*Area Master*/
				areaId = rs.getString("S_area_ID");
				if(areaId != null){
					areaName = rs.getString("S_area_name");
					areaCode = rs.getString("areacode");
					areaIncharge = rs.getString("areaincharge");
					
					area.setId(areaId);
					area.setName(areaName);
					area.setCode(areaCode);
					area.setInCharge(areaIncharge);
					
					if(!areasM.containsKey(areaId)) {
						if(stateId != null)
							area.setState(statesM.get(stateId));
						areasM.put(areaId, area);
//						ebDao.addEbMasterListener(area);
//						wecDao.addWecMasterListener(area);
//						customerDao.addCustomerListener(area);
					}
				
				}
				
				/*Site Master*/
				siteId = rs.getString("S_site_ID");
				if(siteId != null){
					siteName = rs.getString("S_site_name");
					siteCode = rs.getString("sitecode");
					siteIncharge = rs.getString("siteIncharge");
					siteAddress = rs.getString("siteaddress");
					siteRemark = rs.getString("siteRemark");
					
					site.setId(siteId);
					site.setName(siteName);
					site.setCode(siteCode);
					site.setIncharge(siteIncharge);
					site.setAddress(siteAddress);
					site.setRemark(siteRemark);
					
					if(!sitesM.containsKey(siteId)) {
						if(areaId != null)
							site.setArea(areasM.get(areaId));
						sitesM.put(siteId, site);
						
//						wecDao.addWecMasterListener(site);
//						customerDao.addCustomerListener(site);
					}
				}
				
				/*Eb Master*/
				ebId = rs.getString("S_EB_ID");
				if(ebId != null){
					ebName = rs.getString("S_ebshort_descr");
					ebWorkingStatus = rs.getString("EbWorkingStatus");
					ebDescription = rs.getString("ebDescription");
					eb.setId(ebId);
					eb.setName(ebName);
					eb.setWorkingStatus(ebWorkingStatus);
					eb.setDescription(ebDescription);
					
					if(!ebsM.containsKey(ebId)){
						if(siteId != null)
							eb.setSite(sitesM.get(siteId));
						ebsM.put(ebId, eb);
						
//						customerDao.addCustomerListener(eb);
					}
				}
				
				/*Customer Master*/
				customerId = rs.getString("S_customer_id");
				if(customerId != null){
					customerName = rs.getString("S_Customer_Name");
					customerSapCode = rs.getString("S_SAP_CUSTOMER_CODE");
					customerPhoneNo = rs.getString("S_PHONE_NUMBER");
					customerCellNo = rs.getString("S_CELL_NUMBER");
					customerFaxNo = rs.getString("S_FAX_NUMBER");
					customerContactPerson = rs.getString("S_CUSTOMER_CONTACT");
					customerMarketingPerson = rs.getString("S_MARKETING_PERSON");
					email = rs.getString("S_EMAIL");
					customerActive = rs.getString("S_ACTIVE");
					loginMasterId = rs.getString("S_LOGIN_MASTER_ID");
					
					customer.setId(customerId);
					customer.setName(customerName);
					customer.setSapCode(customerSapCode);
					customer.setTelephoneNo(customerPhoneNo);
					customer.setCellNo(customerCellNo);
					customer.setFaxNo(customerFaxNo);
					customer.setContactPerson(customerContactPerson);
					customer.setMarketingPerson(customerMarketingPerson);
					customer.setEmail(email);
					customer.setActive(customerActive);
					customer.setLoginId(loginMasterId);
					
					if(!customersM.containsKey(customerId)) {
						customersM.put(customerId, customer);
//						stateDao.addStateMasterListener(customer);
						
//						areaDao.addAreaMasterListener(customer);
//						siteDao.addSiteMasterListener(customer);
//						ebDao.addEbMasterListener(customer);
						
					}
				}
				
				/*Plant Master*/
				plantNo = rs.getString("S_PLANT_NO");
				locationNo = rs.getString("S_LOCATION_NO");
				if(plantNo != null){
					plant = new PlantMasterVo();
					plant.setPlantNo(plantNo);
					plant.setLocationNo(locationNo);
					plants.add(plant);
				}
				
				/*Wec Master*/
				wecId = rs.getString("S_WEC_ID");
				if(wecId != null){
					wecName = rs.getString("S_Wecshort_descr");
					wecScadaStatus = rs.getString("WecScadaStatus");
					wecStatus = rs.getString("WecStatus");
					wecCapacity = rs.getDouble("WecCapacity");
					technicalNo = rs.getString("technicalNo");
					commissionDate = rs.getString("CommissionDate");
					wecType = rs.getString("wecType");
					wecFoundationNo = rs.getString("foundationNo") == null ? "" : rs.getString("foundationNo");
					wecShow = rs.getString("WecShow");
					wecRemark = rs.getString("WecRemark");
					wecMultiFactor = Integer.toString(rs.getInt("WecMultiFactor"));
					wecGenComm = Integer.toString(rs.getInt("WecGenComm"));
					wecCostPerUnit = rs.getDouble("WecCostPerUnit");
					wecMachineAvailability = Integer.toString(rs.getInt("WecMachineAvailability"));
					wecExtGridAvailability = Integer.toString(rs.getInt("WecExtGridAvailability"));
					wecIntGridAvailability = Integer.toString(rs.getInt("WecIntGridAvailability"));
					
					wec.setId(wecId);
					wec.setName(wecName);
					wec.setScadaStatus(wecScadaStatus);
					wec.setStatus(wecStatus);
					wec.setCapacity(wecCapacity);
					wec.setTechnicalNo(technicalNo);
					wec.setCommissionDate(commissionDate);
					wec.setType(wecType);
					wec.setFoundationNo(wecFoundationNo);
					wec.setShow(wecShow);
					wec.setRemark(wecRemark);
					wec.setMultiFactor(wecMultiFactor);
					wec.setGenComm(wecGenComm);
					wec.setCostPerUnit(wecCostPerUnit);
					wec.setMachineAvailability(wecMachineAvailability);
					wec.setExtGridAvailability(wecExtGridAvailability);
					wec.setIntGridAvailability(wecIntGridAvailability);
					
					/*Adding Dependency*/
					if(plantNo != null) wec.setPlant(plant);
					if(customerId != null) wec.setCustomer(customersM.get(customerId));
					if(ebId != null) wec.setEb(ebsM.get(ebId));
					
					if(!wecsM.containsKey(wecId)) wecsM.put(wecId, wec);
				}
			}
			
//			populatedRemainingState();
			
			long end = System.currentTimeMillis();
			logger.debug("Mapping Done with T:" + ((end - start)/1000));
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return this;
	
	}

	public void log() {
		logger.debug("Customer Size: " + customersM.size());
		logger.debug("Wec Size: " + wecsM.size());
		logger.debug("Eb Size: " + ebsM.size());
		logger.debug("Site Size: " + sitesM.size());
		logger.debug("Area Size: " + areasM.size());
		logger.debug("State Size: " + statesM.size());
	}

	public List<IWecMasterVo> getWecs() {
		return wecs;
	}

	public List<ICustomerMasterVo> getCustomers() {
		return customers;
	}

	public List<IEbMasterVo> getEbs() {
		return ebs;
	}

	public List<ISiteMasterVo> getSites() {
		return sites;
	}

	public List<IStateMasterVo> getStates() {
		return states;
	}

	public List<IPlantMasterVo> getPlants() {
		return plants;
	}

	public Map<String, IPlantMasterVo> getPlantsM() {
		return plantsM;
	}

	public Map<String, IWecMasterVo> getWecsM() {
		return wecsM;
	}

	public Map<String, ICustomerMasterVo> getCustomersM() {
		return customersM;
	}

	public Map<String, IEbMasterVo> getEbsM() {
		return ebsM;
	}

	public Map<String, ISiteMasterVo> getSitesM() {
		return sitesM;
	}

	public Map<String, IStateMasterVo> getStatesM() {
		return statesM;
	}

	public List<IAreaMasterVo> getAreas() {
		return areas;
	}

	public Map<String, IAreaMasterVo> getAreasM() {
		return areasM;
	}
	
	public static void test(){
		
		logger.debug("Validating Graph");
		Graph G = Graph.getInstance();
		
		PlantMasterVo plant;
		ICustomerMasterVo customer;
//		IWecMasterVo wec;
//		IEbMasterVo eb;
//		ISiteMasterVo site;
//		IAreaMasterVo area;
		IStateMasterVo state;
		
		/*for(String stateId: G.getStatesM().keySet()){
			state = G.getStatesM().get(stateId);
			logger.debug(String.format("State: %s(%s)", state.getName(),state.getId()));
			for(String areaId: G.getAreasM().keySet()){
				area = G.getAreasM().get(areaId);
				if(area.getState().getId().equals(state.getId())){
					logger.debug(String.format("\tArea: %s(%s)", area.getName(),area.getId()));
				} else {
					continue;
				}
				for(String siteId: G.getSitesM().keySet()){
					site = G.getSitesM().get(siteId);
					if(site.getArea().getId().equals(area.getId())){
						logger.debug(String.format("\t\tSite: %s(%s)", site.getName(),site.getId()));
					} else {
						continue;
					}
					for(String ebId: G.getEbsM().keySet()){
						eb = G.getEbsM().get(ebId);
						if(eb.getSite().getId().equals(site.getId())){
							logger.debug(String.format("\t\t\tEb: %s(%s)", eb.getName(),eb.getId()));
						} else {
							continue;
						}
						for(String wecId: G.getWecsM().keySet()){
							wec = G.getWecsM().get(wecId);
							if(wec.getEb().getId().equals(eb.getId())){
								logger.debug(String.format("\t\t\t\tWec3214 : %s(%s) -> %s -> %s -> %s -> %s", wec.getName(),wec.getId(), eb.getName(), site.getName(), area.getName(), state.getName()));
							} else {
								continue;
							}
						}
					}
				}
			}
		}*/
		
		for(String stateId: G.getStatesM().keySet()){
			state = G.getStatesM().get(stateId);
			logger.debug(String.format("State: %s(%s)", state.getName(), state.getId()));
			for(IAreaMasterVo area: state.getAreas()){
				logger.debug(String.format("\tArea: %s(%s)", area.getName(),area.getId()));
				for(ISiteMasterVo site: area.getSites()){
					logger.debug(String.format("\t\tSite: %s(%s)", site.getName(),site.getId()));
					for(IEbMasterVo eb: site.getEbs()){
						logger.debug(String.format("\t\t\tEb: %s(%s)", eb.getName(),eb.getId()));
						for(IWecMasterVo wec: eb.getWecs()){
							logger.debug(String.format("\t\t\t\tW123 : %s(%s) -> %s -> %s -> %s -> %s", wec.getName(),wec.getId(), eb.getName(), site.getName(), area.getName(), state.getName()));
						}
					}
				}
			}
		}
		
		for(String customerId: G.getCustomersM().keySet()){
			customer = G.getCustomersM().get(customerId);
			for(IWecMasterVo wec: customer.getWecs()){
				logger.debug(String.format("Customer: %s, Wec : %s(%s)", customer.getName(), wec.getName(),wec.getId()));
			}
		}
		
	}
	
	public Graph stateCreated(IStateMasterVo state){
		statesM.put(state.getId(), state);
		return this;
	}
	
	public Graph stateUpdated(IStateMasterVo updatedState){
		StateMasterVo oldState = (StateMasterVo) statesM.get(updatedState.getId());
		oldState.setName(updatedState.getName());
		oldState.setSapCode(updatedState.getSapCode());
		oldState.setModifiedBy(updatedState.getModifiedBy());
		oldState.setModifiedAt(updatedState.getModifiedAt());
		
		return this;
	}
	
	public Graph areaCreated(IAreaMasterVo area){
		String stateId = area.getStateId();
		if(stateId != null)
			area.setState(statesM.get(stateId));
		areasM.put(area.getId(), area);
		
		return this;
	}
	
	public Graph areaUpdate(IAreaMasterVo updatedArea) {
		AreaMasterVo oldArea = (AreaMasterVo) areasM.get(updatedArea.getId());
		oldArea.setName(updatedArea.getName());
		oldArea.setCode(updatedArea.getCode());
		oldArea.setInCharge(updatedArea.getInCharge());
		oldArea.setModifiedBy(updatedArea.getModifiedBy());
		oldArea.setModifiedAt(updatedArea.getModifiedAt());
		return this;
	}
	
	public static void main(String[] args) throws Exception{
		Graph.getInstance();
	}

	private static Graph deserialize() {
		Graph G;
		G = null;
		try {
			FileInputStream fileIn = new FileInputStream("E:\\Git\\WWIL\\EclipseWorkspace\\WCARE\\graph.txt");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			G = (Graph) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			return null;
		} catch (ClassNotFoundException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			return null;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			return null;
		}
		G.log();
		logger.debug("Deserializing Employee...");
		return G;
	}

	private static void serialize() {
		Graph G = null;
		 
		try {
			G = Graph.getInstance();
			FileOutputStream fileOut = new FileOutputStream("./graph.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(G);
			out.close();
			fileOut.close();
			logger.debug("Serialized data is saved in ./graph.txt file");
		} catch (IOException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	private static void comissionDateTest() throws ParseException {
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		DateFormat outputFormat2 = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = null;
		String commissionDate = null;
		
		Graph G = Graph.getInstance();
		Map<String, IWecMasterVo> wecs = G.getWecsM();
		
		for(String wecId: G.getWecsM().keySet()){
			IWecMasterVo wec = wecs.get(wecId);
			commissionDate = wec.getCommissionDate();
			String outputString = null;
			if(commissionDate != null){
				date = inputFormat.parse(wec.getCommissionDate());
				outputString = outputFormat2.format(date);
				logger.debug(String.format("Wec: %s, TodaysDate - CommissionDate: %s", wec, DateUtility.compareGivenDateWithTodayInTermsOfDays(outputString)));
			}
		}
	}

	public Graph siteCreated(ISiteMasterVo site) {
		sitesM.put(site.getId(), site);
		return this;
	}

	public Graph siteUpdated(SiteMasterVo updatedSite) {
		SiteMasterVo oldSite = (SiteMasterVo) sitesM.get(updatedSite.getId());
		oldSite.setName(updatedSite.getName());
		oldSite.setModifiedBy(updatedSite.getModifiedBy());
		oldSite.setModifiedAt(updatedSite.getModifiedAt());
		
		oldSite.setId(updatedSite.getId());
		oldSite.setCode(updatedSite.getCode());
		oldSite.setIncharge(updatedSite.getIncharge());
		oldSite.setAddress(updatedSite.getAddress());
		
		return this;
	}

	public Graph customerCreated(CustomerMasterVo vo) {
		customersM.put(vo.getId(), vo);
		return this;
	}

	public Graph customerUpdated(com.enercon.model.graph.CustomerMasterVo updatedVo) {
		CustomerMasterVo oldVo = (CustomerMasterVo) customersM.get(updatedVo.getId());
		
		oldVo.setId(updatedVo.getId());
		oldVo.setName(updatedVo.getName());
		oldVo.setSapCode(updatedVo.getSapCode());
		oldVo.setTelephoneNo(updatedVo.getTelephoneNo());
		oldVo.setCellNo(updatedVo.getCellNo());
		oldVo.setFaxNo(updatedVo.getFaxNo());
		oldVo.setContactPerson(updatedVo.getContactPerson());
		oldVo.setMarketingPerson(updatedVo.getMarketingPerson());
		oldVo.setEmail(updatedVo.getEmail());
		oldVo.setActive(updatedVo.getActive());
		
		return this;
	}

	public Graph ebUpdatePartial(IEbMasterVo updatedEb, Map<String, Object> m) {
		EbMasterVo oldEb = (EbMasterVo) ebsM.get(updatedEb.getId());
		for(String property: m.keySet()){
			if(property.equals("S_REMARKS")){
				oldEb.setRemark((String) m.get(property));
			}
			else if(property.equals("S_EB_ID_TO")){
				oldEb.setTransferToEbId((String) m.get(property));
			}
			else if(property.equals("D_TRANSFER_DATE")){
				oldEb.setTransferDate((String) m.get(property));
			}
			else if(property.equals("S_STATUS")){
				oldEb.setWorkingStatus((String) m.get(property));
			}
		}
		return this;
	}
	
	private void logEvent(String master, String type){
		logger.debug(String.format("Event Occurred at %s. Master: %s. Type: %s", DateUtility.getCurrentTimestampInSQL(), master, type));
	}

	public void handler(StateMasterEvent event) {
		String type = "";
		IStateMasterVo newState = event.getState();
		if(event.isCreate()){
			type = "Create";
			statesM.put(newState.getId(), newState);
			populateStateMasterAsListener(newState);
		} else if(event.isUpdate()){
			StateMasterVo oldState = (StateMasterVo) statesM.get(newState.getId());
			List<String> columns = event.getColumns();
			for(String property : columns){
				if(property.equals("S_STATE_NAME")){
					oldState.setName(newState.getName());
				} else if (property.equals("S_LAST_MODIFIED_BY")){
					oldState.setModifiedBy(newState.getModifiedBy());
				} else if (property.equals("S_SAP_STATE_CODE")){
					oldState.setSapCode(newState.getSapCode());
				}
			}
			oldState.setModifiedAt(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy H:m a"));
			type = "Update";
		}
		
		logEvent("State", type);
	}
	
	public void handler(AreaMasterEvent event) {
		String type = "";
		IAreaMasterVo newArea = event.getArea();
		if(event.isCreate()){
			type = "Create";
			newArea.setState(statesM.get(newArea.getStateId()));
			areasM.put(newArea.getId(), newArea);
		}else if(event.isUpdate()){
			type = "Update";
			AreaMasterVo oldArea = (AreaMasterVo) areasM.get(newArea.getId());
			List<String> columns = event.getColumns();
			for(String property : columns){
				if(property.equals("S_AREA_NAME")){
					oldArea.setName(newArea.getName());
				}else if(property.equals("S_STATE_ID")){
					oldArea.setStateId(newArea.getStateId());
				}else if(property.equals("S_LAST_MODIFIED_BY")){
					oldArea.setModifiedBy(newArea.getModifiedBy());
				}else if(property.equals("S_AREA_CODE")){
					oldArea.setCode(newArea.getCode());
				}else if(property.equals("S_AREA_INCHARGE_ID")){
					oldArea.setInCharge(newArea.getInCharge());
				} 
			}
			oldArea.setModifiedAt(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy H:m a"));
		}
		logEvent("Area", type);
	}

	public void handler(SiteMasterEvent event) {
		String type = "";
		ISiteMasterVo newSite = event.getSite();
		if(event.isCreate()){
			type = "Create";
			newSite.setArea(areasM.get(newSite.getAreaId()));
			sitesM.put(newSite.getId(), newSite);
		}else if(event.isUpdate()){
			type = "Update";
			SiteMasterVo oldSite = (SiteMasterVo) sitesM.get(newSite.getId());
			List<String> columns = event.getColumns();
			for(String property : columns){
				if(property.equals("S_SITE_NAME")){
					oldSite.setName(newSite.getName());
				}else if(property.equals("S_STATE_ID")){
					oldSite.setStateId(newSite.getStateId());
				}else if(property.equals("S_LAST_MODIFIED_BY")){
					oldSite.setModifiedBy(newSite.getModifiedBy());
				}else if(property.equals("S_SITE_CODE")){
					oldSite.setCode(newSite.getCode());
				}else if(property.equals("S_SITE_INCHARGE")){
					oldSite.setIncharge(newSite.getIncharge());
				} else if(property.equals("S_SITE_ADDRESS")){
					oldSite.setAddress(newSite.getAddress());
				} else if(property.equals("S_AREA_ID")){
					oldSite.setAreaId(newSite.getAreaId());
				} else if(property.equals("S_SITE_REMARKS")){
					oldSite.setRemark(newSite.getRemark());
				} 
			}
			oldSite.setModifiedAt(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy H:m a"));
		}
		logEvent("Site", type);
	}

	public void handler(CustomerMasterEvent event) {
		
		ICustomerMasterVo newCustomer = event.getCustomer();
		String type = "";
		if(event.isCreate()){
			type = "Create";
			customersM.put(newCustomer.getId(), newCustomer);
		}else if(event.isUpdate()){
			type = "Update";
			CustomerMasterVo oldCustomer = (CustomerMasterVo) customersM.get(newCustomer.getId());
			List<String> columns = event.getColumns();
			for(String property : columns){
				if(property.equals("S_SAP_CUSTOMER_CODE")){
					oldCustomer.setSapCode(newCustomer.getSapCode());
				}else if(property.equals("S_ACTIVE")){
					oldCustomer.setActive(newCustomer.getActive());
				}else if(property.equals("S_CUSTOMER_NAME")){
					oldCustomer.setName(newCustomer.getName());
				}else if(property.equals("S_CUSTOMER_CONTACT")){
					oldCustomer.setContactPerson(newCustomer.getContactPerson());
				}else if(property.equals("S_PHONE_NUMBER")){
					oldCustomer.setTelephoneNo(newCustomer.getTelephoneNo());
				} else if(property.equals("S_CELL_NUMBER")){
					oldCustomer.setCellNo(newCustomer.getCellNo());
				} else if(property.equals("S_FAX_NUMBER")){
					oldCustomer.setFaxNo(newCustomer.getFaxNo());
				} else if(property.equals("S_EMAIL")){
					oldCustomer.setEmail(newCustomer.getEmail());
				} else if(property.equals("S_LAST_MODIFIED_BY")){
					oldCustomer.setModifiedBy(newCustomer.getModifiedBy());
				} else if(property.equals("S_MARKETING_PERSON")){
					oldCustomer.setMarketingPerson(newCustomer.getMarketingPerson());
				} 
			}
			oldCustomer.setModifiedAt(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy H:m a"));
		}
		logEvent("Customer", type);
		
	}

	public void handler(EbMasterEvent event) {
		
		IEbMasterVo newEb = event.getEb();
		String type = "";
		if(event.isCreate()){
			type = "Create";
			newEb.setSite(sitesM.get(newEb.getSiteId()));
			ebsM.put(newEb.getId(), newEb);
			
		}else if(event.isUpdate()){
			type = "Update";
			EbMasterVo oldEb = (EbMasterVo) ebsM.get(newEb.getId());
			List<String> columns = event.getColumns();
			for(String property : columns){
				if(property.equals("S_EBSHORT_DESCR")){
					oldEb.setName(newEb.getName());
				}else if(property.equals("S_EB_DESCRIPTION")){
					oldEb.setDescription(newEb.getDescription());
				}else if(property.equals("S_STATUS")){
					oldEb.setWorkingStatus(newEb.getWorkingStatus());
				}else if(property.equals("S_SITE_ID")){
					oldEb.setSiteId(newEb.getSiteId());
				}else if(property.equals("S_LAST_MODIFIED_BY")){
					oldEb.setModifiedBy(newEb.getModifiedBy());
				} else if(property.equals("S_FEDER_ID")){
					oldEb.setFederId(newEb.getFederId());
				} else if(property.equals("S_CUSTOMER_ID")){
					oldEb.setCustomerId(newEb.getCustomerId());
				} 
			}
			oldEb.setModifiedAt(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy H:m a"));
		}
		logEvent("Eb", type);
		
	}

	public void handler(WecMasterEvent event) {
		
		IWecMasterVo newWec = event.getWec();
		String type = "";
		if(event.isCreate()){
			type = "Create";
			newWec.setEb(ebsM.get(newWec.getEbId()));
			newWec.setCustomer(customersM.get(newWec.getCustomerId()));
			wecsM.put(newWec.getId(), newWec);
		}else if(event.isUpdate()){
			type = "Update";
			WecMasterVo oldWec = (WecMasterVo) wecsM.get(newWec.getId());
			List<String> columns = event.getColumns();
			for(String property : columns){
				if(property.equals("S_WECSHORT_DESCR")){
					oldWec.setName( newWec.getName());
				}else if(property.equals("S_CUSTOMER_ID")){
					oldWec.setCustomerId( newWec.getCustomerId());
				}else if(property.equals("S_EB_ID")){
					oldWec.setEbId( newWec.getEbId());
				}else if(property.equals("S_FOUND_LOC")){
					oldWec.setFoundationNo( newWec.getFoundationNo());
				}else if(property.equals("S_WEC_TYPE")){
					oldWec.setType( newWec.getType());
				} else if(property.equals("N_MULTI_FACTOR")){
					oldWec.setMultiFactor( newWec.getMultiFactor());
				} else if(property.equals("N_WEC_CAPACITY")){
					oldWec.setCapacity(newWec.getCapacity());
				} else if(property.equals("S_CREATED_BY")){
					oldWec.setCreatedBy( newWec.getCreatedBy());
				} else if(property.equals("S_LAST_MODIFIED_BY")){
					oldWec.setModifiedBy( newWec.getModifiedBy());
				} else if(property.equals("D_COMMISION_DATE")){
					oldWec.setCommissionDate( newWec.getCommissionDate());
				} else if(property.equals("S_STATUS")){
					oldWec.setStatus( newWec.getStatus());
				} else if(property.equals("N_GEN_COMM")){
					oldWec.setGenComm( newWec.getGenComm());
				} else if(property.equals("N_MAC_AVA")){
					oldWec.setMachineAvailability( newWec.getMachineAvailability());
				} else if(property.equals("N_EXT_AVA")){
					oldWec.setExtGridAvailability( newWec.getExtGridAvailability());
				} else if(property.equals("N_INT_AVA")){
					oldWec.setIntGridAvailability( newWec.getIntGridAvailability());
				} else if(property.equals("D_START_DATE")){
					oldWec.setStartDate( newWec.getStartDate());
				} else if(property.equals("D_END_DATE")){
					oldWec.setEndDate( newWec.getEndDate());
				} else if(property.equals("S_FORMULA_NO")){
					oldWec.setFormula( newWec.getFormula());
				} else if(property.equals("S_SHOW")){
					oldWec.setShow( newWec.getShow());
				} else if(property.equals("N_COST_PER_UNIT")){
					oldWec.setCostPerUnit(newWec.getCostPerUnit());
				} else if(property.equals("S_TECHNICAL_NO")){
					oldWec.setTechnicalNo( newWec.getTechnicalNo());
				} else if(property.equals("S_GUARANTEE_TYPE")){
					oldWec.setGuaranteeType( newWec.getGuaranteeType());
				} else if(property.equals("S_CUSTOMER_TYPE")){
					oldWec.setCustomerType( newWec.getCustomerType());
				} else if(property.equals("S_SCADA_FLAG")){
					oldWec.setScadaStatus( newWec.getScadaStatus());
				} else if(property.equals("S_FEEDER_ID")){
					oldWec.setFeederId( newWec.getFeederId());
				} else if(property.equals("N_PES_SCADA_STATUS")){
					oldWec.setScadaStatus( newWec.getScadaStatus());
				} 
			}
			oldWec.setModifiedAt(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy H:m a"));
		}
		logEvent("Wec", type);
		
	}
	
}