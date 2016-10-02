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

import com.enercon.model.graph.EbMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.event.EbMasterEvent;
import com.enercon.model.graph.listener.EbMasterListener;
import com.enercon.spring.context.ApplicationContextProvider;

@Component
@Scope(value="singleton")
public class EbMasterDao {

	private static Logger logger = Logger.getLogger(EbMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	private List<EbMasterListener> listeners =  new ArrayList<EbMasterListener>();

	public EbMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	public static EbMasterDao getInstance(){
		ApplicationContextProvider p = ApplicationContextProvider.getInstance();
		ApplicationContext applicationContext = p.getApplicationContext();
		return applicationContext.getBean("ebMasterDao", EbMasterDao.class);
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
	
	public boolean create(EbMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_EB_MASTER( S_EB_ID, S_EBSHORT_DESCR, S_EB_DESCRIPTION, S_STATUS," +
																	  " S_SITE_ID, S_CREATED_BY, S_LAST_MODIFIED_BY, S_FEDER_ID, S_CUSTOMER_ID ) values " +
																	" (:id, :name, :description, :workingStatus," +
																	" :siteId, :createdBy, :modifiedBy, :federId, :customerId)", params);
		if(rowInserted == 1){
			EbMasterEvent event = new EbMasterEvent();
			event.setCreate(true);
			event.setEb(vo);
			fireEbMasterEvent(event);
		}
		return (rowInserted == 1);
	}

	public boolean updateForMaster(IEbMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_EBSHORT_DESCR");
		columns.add("S_EB_DESCRIPTION");
		columns.add("S_STATUS");
		columns.add("S_SITE_ID");
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("S_FEDER_ID");
		columns.add("S_CUSTOMER_ID");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(IEbMasterVo vo, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_EB_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_EBSHORT_DESCR")){
				query.append(column + " = :name, ");
			} else if(column.equals("S_EB_DESCRIPTION")){
				query.append(column + " = :description, ");
			} else if(column.equals("S_STATUS")){
				query.append(column + " = :workingStatus, ");
			} else if(column.equals("S_SITE_ID")){
				query.append(column + " = :siteId, ");
			} else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			} else if(column.equals("S_FEDER_ID")){
				query.append(column + " = :federId, ");
			} else if(column.equals("S_CUSTOMER_ID")){
				query.append(column + " = :customerId, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");
		query.append("WHERE S_EB_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		if(rowUpdate == 1) {
			EbMasterEvent event = new EbMasterEvent();
			event.setEb(vo);
			event.setUpdate(true);
			event.setColumns(columns);
			fireEbMasterEvent(event);
		}
		return (rowUpdate == 1);
	}
	
	public boolean exist(EbMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_EB_MASTER WHERE s_ebshort_descr = :name", params);
		return count == 1;
	}

	public void addEbMasterListener(EbMasterListener listener){
		listeners.add(listener);
	}
	
	public void fireEbMasterEvent(EbMasterEvent event){
		for(EbMasterListener listener: listeners){
			listener.handler(event);
		}
	}
	
	
}
