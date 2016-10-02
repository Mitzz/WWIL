package com.enercon.spring.dao.master;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.graph.event.WecMasterEvent;
import com.enercon.model.graph.listener.WecMasterListener;
import com.enercon.spring.context.ApplicationContextProvider;

@Component
@Scope(value="singleton")
public class WecMasterDao {
	
	private static Logger logger = Logger.getLogger(WecMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	private List<WecMasterListener> listeners =  new ArrayList<WecMasterListener>();

	private WecMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	public static WecMasterDao getInstance(){
		ApplicationContextProvider p = ApplicationContextProvider.getInstance();
		ApplicationContext applicationContext = p.getApplicationContext();
		return applicationContext.getBean("wecMasterDao", WecMasterDao.class);
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public boolean create(IWecMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_WEC_TYPE( S_WEC_ID, S_WECSHORT_DESCR, S_CUSTOMER_ID, S_EB_ID, S_FOUND_LOC, " +
															   " S_WEC_TYPE, N_MULTI_FACTOR, N_WEC_CAPACITY,  S_CREATED_BY, S_LAST_MODIFIED_BY, " +
															   " D_COMMISION_DATE, S_STATUS, N_GEN_COMM, N_MAC_AVA, N_EXT_AVA, " +
															   " N_INT_AVA, D_START_DATE, D_END_DATE, S_FORMULA_NO, S_SHOW, " +
															   " N_COST_PER_UNIT, S_TECHNICAL_NO, S_GUARANTEE_TYPE, S_CUSTOMER_TYPE, S_SCADA_FLAG, " +
															   " S_FEEDER_ID, N_PES_SCADA_STATUS ) " +
													   "values ( :id, :name, :customerId, :ebId, :foundationNo, " +
															   " :type, :multiFactor, :capacity, :createdBy, :modifiedBy, " +
															   " :commissionDate, :status, :genComm, :machineAvailability, :extGridAvailability, " +
															   " :intGridAvailability, :startDate, :endDate,:formula, :show, " +
															   " :costPerUnit, :technicalNo, :guaranteeType, :customerType, :scadaStatus, " +
															   " :feederId, :scadaStatus)", params);
		if(rowInserted == 1){
			WecMasterEvent event = new WecMasterEvent();
			event.setCreate(true);
			event.setWec(vo);
			fireWecMasterEvent(event);
		}
		return (rowInserted == 1);
	}

	public boolean updateForMaster(IWecMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		
		columns.add("S_WECSHORT_DESCR");
		columns.add("S_CUSTOMER_ID");
		columns.add("S_EB_ID");
		columns.add("S_FOUND_LOC");
		
		columns.add("S_WEC_TYPE");
		columns.add("N_MULTI_FACTOR");
		columns.add("N_WEC_CAPACITY");
		columns.add("S_CREATED_BY");
		
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("D_COMMISION_DATE");
		columns.add("S_STATUS");
		columns.add("N_GEN_COMM"); 
		
		columns.add("N_MAC_AVA");
		columns.add("N_EXT_AVA");
		columns.add("N_INT_AVA");
		columns.add("D_START_DATE");
		
		columns.add("D_END_DATE");
		columns.add("S_FORMULA_NO");
		columns.add("S_SHOW");
		columns.add("N_COST_PER_UNIT");
		
		columns.add("S_TECHNICAL_NO"); 
		columns.add("S_GUARANTEE_TYPE"); 
		columns.add("S_CUSTOMER_TYPE"); 
		columns.add("S_SCADA_FLAG");
		
		columns.add("S_FEEDER_ID"); 
		columns.add("N_PES_SCADA_STATUS"); 
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(IWecMasterVo vo, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_WEC_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_WECSHORT_DESCR")){
				query.append(column + " = :name, ");
			} else if(column.equals("S_CUSTOMER_ID")){
				query.append(column + " = :customerId, ");
			} else if(column.equals("S_EB_ID")){
				query.append(column + " = :ebId, ");
			} else if(column.equals("S_FOUND_LOC")){
				query.append(column + " = :foundationNo, ");
			}else if(column.equals("S_WEC_TYPE")){
				query.append(column + " = :type, ");
			}else if(column.equals("N_MULTI_FACTOR")){
				query.append(column + " = :multiFactor, ");
			}else if(column.equals("N_WEC_CAPACITY")){
				query.append(column + " = :capacity, ");
			}else if(column.equals("S_CREATED_BY")){
				query.append(column + " = :createdBy, ");
			}else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			}else if(column.equals("D_COMMISION_DATE")){
				query.append(column + " = :commissionDate, ");
			}else if(column.equals("S_STATUS")){
				query.append(column + " = :status, ");
			}else if(column.equals("N_GEN_COMM")){
				query.append(column + " = :genComm, ");
			}else if(column.equals("N_MAC_AVA")){
				query.append(column + " = :machineAvailability, ");
			}else if(column.equals("N_EXT_AVA")){
				query.append(column + " = :extGridAvailability, ");
			}else if(column.equals("N_INT_AVA")){
				query.append(column + " = :intGridAvailability, ");
			}else if(column.equals("D_START_DATE")){
				query.append(column + " = :startDate, ");
			}else if(column.equals("D_END_DATE")){
				query.append(column + " = :endDate, ");
			}else if(column.equals("S_FORMULA_NO")){
				query.append(column + " = :formula, ");
			}else if(column.equals("S_SHOW")){
				query.append(column + " = :show, ");
			}else if(column.equals("N_COST_PER_UNIT")){
				query.append(column + " = :costPerUnit, ");
			}else if(column.equals("S_TECHNICAL_NO")){
				query.append(column + " = :technicalNo, ");
			}else if(column.equals("S_GUARANTEE_TYPE")){
				query.append(column + " = :guaranteeType, ");
			}else if(column.equals("S_CUSTOMER_TYPE")){
				query.append(column + " = :customerType, ");
			}else if(column.equals("S_SCADA_FLAG")){
				query.append(column + " = :scadaStatus, ");
			}else if(column.equals("S_FEEDER_ID")){
				query.append(column + " = :feederId, ");
			}else if(column.equals("N_PES_SCADA_STATUS")){
				query.append(column + " = :scadaStatus, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");
		query.append("WHERE S_WEC_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		if(rowUpdate == 1) {
			WecMasterEvent event = new WecMasterEvent();
			event.setWec(vo);
			event.setUpdate(true);
			event.setColumns(columns);
			fireWecMasterEvent(event);
		}
		return (rowUpdate == 1);
	}
	
	public boolean exist(IWecMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_WEC_MASTER WHERE S_WECSHORT_DESCR = :name", params);
		return count == 1;
	}

	public void addWecMasterListener(WecMasterListener listener){
		listeners.add(listener);
	}
	
	public void fireWecMasterEvent(WecMasterEvent event){
		for(WecMasterListener listener: listeners){
			listener.handler(event);
		}
	}
}
