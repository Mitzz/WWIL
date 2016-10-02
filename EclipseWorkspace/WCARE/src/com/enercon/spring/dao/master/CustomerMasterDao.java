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

import com.enercon.model.graph.CustomerMasterVo;
import com.enercon.model.graph.Graph;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.event.CustomerMasterEvent;
import com.enercon.model.graph.listener.CustomerMasterListener;
import com.enercon.spring.context.ApplicationContextProvider;

@Component
@Scope(value="singleton")
public class CustomerMasterDao {

	private static Logger logger = Logger.getLogger(CustomerMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	private List<CustomerMasterListener> listeners =  new ArrayList<CustomerMasterListener>();

	private CustomerMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	public static CustomerMasterDao getInstance(){
		ApplicationContextProvider p = ApplicationContextProvider.getInstance();
		ApplicationContext applicationContext = p.getApplicationContext();
		return applicationContext.getBean("customerMasterDao", CustomerMasterDao.class);
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public List<ICustomerMasterVo> getAll(){
	
		List<ICustomerMasterVo> customers = new ArrayList<ICustomerMasterVo>();
		for(String custId: Graph.getInstance().getCustomersM().keySet()){
			customers.add(Graph.getInstance().getCustomersM().get(custId));
			//logger.debug("customers :: " + customers);
		}	
		return customers;
	}
	
	public boolean create(CustomerMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_CUSTOMER_MASTER( S_CUSTOMER_ID, S_SAP_CUSTOMER_CODE, S_ACTIVE, S_CUSTOMER_NAME," +
																	  " S_CUSTOMER_CONTACT, S_PHONE_NUMBER, S_CELL_NUMBER, S_FAX_NUMBER, " +
																	  " S_EMAIL, S_CREATED_BY, S_LAST_MODIFIED_BY, S_MARKETING_PERSON ) values " +
																	" (:id, :sapCode, :active, :name," +
																	" :contactPerson, :telephoneNo, :cellNo, :faxNo," +
																	" :email, :createdBy, :modifiedBy, :marketingPerson)", params);
		if(rowInserted == 1){
			CustomerMasterEvent event = new CustomerMasterEvent();
			event.setCreate(true);
			event.setCustomer(vo);
			fireCustomerMasterEvent(event);
		}
		return (rowInserted == 1);
	}

	public boolean updateForMaster(ICustomerMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_SAP_CUSTOMER_CODE");
		columns.add("S_ACTIVE");
		columns.add("S_CUSTOMER_NAME");
		columns.add("S_CUSTOMER_CONTACT");
		columns.add("S_PHONE_NUMBER");
		columns.add("S_CELL_NUMBER");
		columns.add("S_FAX_NUMBER");
		columns.add("S_EMAIL");
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("S_MARKETING_PERSON");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(ICustomerMasterVo vo, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_CUSTOMER_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_SAP_CUSTOMER_CODE")){
				query.append(column + " = :sapCode, ");
			} else if(column.equals("S_ACTIVE")){
				query.append(column + " = :active, ");
			} else if(column.equals("S_CUSTOMER_NAME")){
				query.append(column + " = :name, ");
			} else if(column.equals("S_CUSTOMER_CONTACT")){
				query.append(column + " = :contactPerson, ");
			} else if(column.equals("S_PHONE_NUMBER")){
				query.append(column + " = :telephoneNo, ");
			} else if(column.equals("S_CELL_NUMBER")){
				query.append(column + " = :cellNo, ");
			} else if(column.equals("S_FAX_NUMBER")){
				query.append(column + " = :faxNo, ");
			}else if(column.equals("S_EMAIL")){
				query.append(column + " = :email, ");
			}else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			}else if(column.equals("S_MARKETING_PERSON")){
				query.append(column + " = :marketingPerson, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");		
		query.append("WHERE S_CUSTOMER_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		if(rowUpdate == 1) {
			CustomerMasterEvent event = new CustomerMasterEvent();
			event.setCustomer(vo);
			event.setUpdate(true);
			event.setColumns(columns);
			fireCustomerMasterEvent(event);
		}
		return (rowUpdate == 1);
	}
	
	public boolean exist(CustomerMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_Customer_MASTER WHERE S_CUSTOMER_NAME = :name", params);
		return count == 1;
	}

	public void addCustomerMasterListener(CustomerMasterListener listener){
		listeners.add(listener);
	}
	
	public void fireCustomerMasterEvent(CustomerMasterEvent event){
		for(CustomerMasterListener listener: listeners){
			listener.handler(event);
		}
	}
	
}
