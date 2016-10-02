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

import com.enercon.model.graph.Graph;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.StateMasterVo;
import com.enercon.model.graph.event.StateMasterEvent;
import com.enercon.model.graph.listener.StateMasterListener;
import com.enercon.spring.context.ApplicationContextProvider;
import com.enercon.spring.service.master.StateMasterService;

@Component
@Scope(value="singleton")
public class StateMasterDao {
	
	private static Logger logger = Logger.getLogger(StateMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	private List<StateMasterListener> listeners =  new ArrayList<StateMasterListener>();

	private StateMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	public static StateMasterDao getInstance(){
		ApplicationContextProvider p = ApplicationContextProvider.getInstance();
		ApplicationContext applicationContext = p.getApplicationContext();
		return applicationContext.getBean("stateMasterDao", StateMasterDao.class);
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
	
	public List<IStateMasterVo> getAll() throws SQLException{
		List<IStateMasterVo> states = new ArrayList<IStateMasterVo>(Graph.getInstance().getStatesM().values());
		return states;
	}
	
	
	public boolean create(StateMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_STATE_MASTER(S_STATE_ID, S_STATE_NAME, S_CREATED_BY, S_LAST_MODIFIED_BY, S_SAP_STATE_CODE) values " +
																	"(:id, :name, :createdBy, :modifiedBy, :sapCode)", params);
		if(rowInserted == 1){
			StateMasterEvent event = new StateMasterEvent();
			event.setCreate(true);
			event.setState(vo);
			fireStateMasterEvent(event);
		}
		return (rowInserted == 1);
	}

	public boolean updateForMaster(IStateMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_STATE_NAME");
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("S_SAP_STATE_CODE");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(IStateMasterVo state, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(state);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_STATE_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_STATE_NAME")){
				query.append(column + " = :name, ");
			} else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			} else if(column.equals("S_SAP_STATE_CODE")){
				query.append(column + " = :sapCode, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");
		query.append("WHERE S_STATE_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		if(rowUpdate == 1) {
			StateMasterEvent event = new StateMasterEvent();
			event.setState(state);
			event.setUpdate(true);
			event.setColumns(columns);
			fireStateMasterEvent(event);
		}
		return (rowUpdate == 1);
	}
	
	public void addStateMasterListener(StateMasterListener listener){
		listeners.add(listener);
		
	}
	
	public void fireStateMasterEvent(StateMasterEvent event){
		for(StateMasterListener listener: listeners){
			listener.handler(event);
		}
	}
	
	public boolean exist(StateMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_STATE_MASTER WHERE S_STATE_NAME = :name", params);
		return count == 1;
	}
}
