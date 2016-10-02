package com.enercon.dao.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DateUtility;

import com.enercon.model.graph.AreaMasterVo;
import com.enercon.model.graph.Graph;
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.graph.event.AreaMasterEvent;
import com.enercon.model.graph.listener.AreaMasterListener;
import com.enercon.model.master.StateMasterVo;

public class AreaMasterDao implements WcareConnector{
	
	private final static Logger logger = Logger.getLogger(AreaMasterDao.class);
//	private final Graph G = Graph.getInstance();
	private List<AreaMasterListener> listeners = new ArrayList<AreaMasterListener>();
	
	private static class SingletonHelper{
		public final static AreaMasterDao INSTANCE = new AreaMasterDao();
	}
	
	public static AreaMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	private AreaMasterDao(){	
	}
	
	public boolean create(AreaMasterVo vo1) throws SQLException, CloneNotSupportedException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		AreaMasterVo vo = (AreaMasterVo) vo1.clone();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into TBL_AREA_MASTER(S_AREA_ID, S_AREA_NAME, S_STATE_ID, S_CREATED_BY, " +
					   								"S_LAST_MODIFIED_BY, S_AREA_CODE, S_AREA_INCHARGE_ID) " +
					   						"values(?, ?, ?, ?, " +
					   								"?, ?, ?) ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getName());
			prepStmt.setObject(3, vo.getState().getId());
			prepStmt.setObject(4, vo.getCreatedBy());
			
			prepStmt.setObject(5, vo.getModifiedBy());
			prepStmt.setObject(6, vo.getCode());
			prepStmt.setObject(7, vo.getInCharge());
		
			int rowInserted = prepStmt.executeUpdate();
			if(rowInserted == 1)
				Graph.getInstance().areaCreated(vo);
			
            return (rowInserted == 1);
			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public boolean update(AreaMasterVo vo1) throws SQLException, CloneNotSupportedException {
	
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		AreaMasterVo vo = (AreaMasterVo) vo1.clone();		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  
					"UPDATE TBL_AREA_MASTER " +
					"SET S_AREA_NAME = ?, S_STATE_ID = ?, S_LAST_MODIFIED_By = ?, S_AREA_CODE = ?, " +
					"S_AREA_INCHARGE_ID = ?, D_LAST_MODIFIED_DATE = ?  " +
			        "WHERE S_AREA_ID = ? ";
			
			prepStmt = conn.prepareStatement(sqlQuery);			
			prepStmt.setObject(1, vo.getName());
			prepStmt.setObject(2, vo.getState().getId());
			prepStmt.setObject(3, vo.getModifiedBy());
			prepStmt.setObject(4, vo.getCode());
			prepStmt.setObject(5, vo.getInCharge());
			prepStmt.setObject(6, DateUtility.getTodaysDateInGivenFormat("dd-MMM-yy"));
			prepStmt.setObject(7, vo.getId());
			
			int rowUpdated = prepStmt.executeUpdate();
			if(rowUpdated == 1)
				Graph.getInstance().areaUpdate(vo);
			return(rowUpdated == 1);
			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public List<IAreaMasterVo> getAll() {
		List<IAreaMasterVo> areas = new ArrayList<IAreaMasterVo>();
		Map<String, IAreaMasterVo> areasM = Graph.getInstance().getAreasM();
		for(String id: areasM.keySet()){
			areas.add(areasM.get(id));
		}
		return areas;
	}

	public IAreaMasterVo get(String id) {
		return Graph.getInstance().getAreasM().get(id);
	}
	
	public void addAreaMasterListener(AreaMasterListener l){
		listeners.add(l);
	}
	
	public void fireAreaMasterEvent(AreaMasterEvent event){
		for(AreaMasterListener l: listeners)
			l.handler(event);
	}
}
