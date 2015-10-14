package com.enercon.dao.missingScadaData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.master.WecMasterDao;
import com.enercon.global.utility.MethodClass;
import com.enercon.model.master.WecMasterVo;
import com.enercon.model.missingScadaData.MissingScadaDataVo;

public class MissingScadaDataDao implements WcareConnector{

	public List<MissingScadaDataVo> getMissingScadaDataVoForReport(String date) throws SQLException{
		
		List<MissingScadaDataVo> missingScadaDataVos = new ArrayList<MissingScadaDataVo>();
		List<WecMasterVo> wecMasterVos = new ArrayList<WecMasterVo>();
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String wecId = null;
		int missingDaysCount = 0;
		MissingScadaDataVo missingScadaDataVo = null;
		WecMasterVo wecMasterVo = null;
		
		try {
			conn = wcareConnector.getConnectionFromPool();

			query = "Select wecmaster.S_weC_id, MISSING_SCADA_DATA.GET_MISSING_DAYS(wecmaster.S_wec_id, summary.D_reading_date) as Missing_Days_Count " + 
					"from tbl_reading_summary summary, tbl_wec_master wecmaster " + 
					"where summary.S_wec_id = wecmaster.S_wec_id " + 
					"and summary.D_reading_date = ? " + 
					"and wecmaster.S_scada_flag = '1' " +
					"and wecmaster.S_status = 1 " +
					"and summary.n_ophr = 0 " + 
					"and summary.N_mf + summary.n_ms + summary.n_eb_load + summary.n_gif + summary.n_gis + summary.N_gef + summary.n_ges + summary.N_fm + summary.n_omnp < ((4 * 60)) "; 

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, date);
			
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				wecId = rs.getString("S_WEC_ID");
				missingDaysCount = rs.getInt("Missing_days_count");
				
				wecMasterVo = new WecMasterVo(wecId);
				wecMasterVos.add(wecMasterVo);
				
				missingScadaDataVo = new MissingScadaDataVo();
				missingScadaDataVo.setDate(date);
				missingScadaDataVo.setNoOfDaysMissing(missingDaysCount);
				missingScadaDataVo.setMasterVo(wecMasterVo);
				
				missingScadaDataVos.add(missingScadaDataVo);
			}
			
			query = 
					"Select S_wec_id, MISSING_SCADA_DATA.GET_MISSING_DAYS(wecmaster.S_wec_id, ? ) as Missing_Days_Count " + 
					"from tbl_wec_master wecmaster " + 
					"where S_scada_flag = 1 " + 
					"and S_status = 1 " + 
					"and S_wec_id not in (Select S_wec_id from tbl_reading_summary summary where D_Reading_date = ? ) " ; 
 
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, date);
			prepStmt.setString(2, date);
			
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				wecId = rs.getString("S_WEC_ID");
				missingDaysCount = rs.getInt("Missing_days_count");
				
				wecMasterVo = new WecMasterVo(wecId);
				wecMasterVos.add(wecMasterVo);
				
				missingScadaDataVo = new MissingScadaDataVo();
				missingScadaDataVo.setDate(date);
				missingScadaDataVo.setNoOfDaysMissing(missingDaysCount);
				missingScadaDataVo.setMasterVo(wecMasterVo);
				
				missingScadaDataVos.add(missingScadaDataVo);
			}
			
			new WecMasterDao().populateGraph(wecMasterVos);
			
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return missingScadaDataVos;
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}

	}
	
	public static void main(String[] args) {
		try {
			new MissingScadaDataDao().getMissingScadaDataVoForReport("06-OCT-2015");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
