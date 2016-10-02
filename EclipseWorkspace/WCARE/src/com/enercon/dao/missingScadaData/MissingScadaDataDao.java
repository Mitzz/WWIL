package com.enercon.dao.missingScadaData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.dao.master.WecMasterDao;
import com.enercon.global.utility.DateUtility;
import com.enercon.model.graph.IPlantMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.missingScadaData.MissingScadaDataReason;
import com.enercon.model.missingScadaData.MissingScadaDataVo;

public class MissingScadaDataDao implements WcareConnector{
	private final static Logger logger = Logger.getLogger(MissingScadaDataDao.class);
	private final static Long WSD_ROW_CUT_OFF = 200L;
	private final static Long FAULT_HOUR = 4 * 60L;
	
	private MissingScadaDataDao(){}
	
	private static class SingletonHelper{
		public final static MissingScadaDataDao INSTANCE = new MissingScadaDataDao();
	}
	
	public static MissingScadaDataDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<MissingScadaDataVo> getMissingScadaDataVos(String date) throws SQLException{
		logger.debug("Start");
		List<MissingScadaDataVo> missingScadaDataVos = new ArrayList<MissingScadaDataVo>();
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String wecId = null;
		int missingDaysCount = 0;
		MissingScadaDataVo missingScadaDataVo = null;
		IWecMasterVo wec = null;
		logger.debug("Start");
		WecMasterDao wecDao = WecMasterDao.getInstance();
		logger.debug("Start");
		try {
			conn = wcareConnector.getConnectionFromPool();

			//Operating Hour = 0 and Fault Parameter < 4 hours
			query = "Select wecmaster.S_weC_id, MISSING_SCADA_DATA.GET_MISSING_DAYS(wecmaster.S_wec_id, summary.D_reading_date) as Missing_Days_Count " + 
					"from tbl_reading_summary summary, tbl_wec_master wecmaster " + 
					"where summary.S_wec_id = wecmaster.S_wec_id " + 
					"and summary.D_reading_date = ? " + 
					"and wecmaster.S_scada_flag = '1' " +
					"and wecmaster.S_status = 1 " +
					"and summary.n_ophr = 0 " + 
					"and summary.N_mf + summary.n_ms + summary.n_eb_load + summary.n_gif + summary.n_gis + summary.N_gef + summary.n_ges + summary.N_fm + summary.n_omnp < ? "; 
			logger.debug("Start");
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, date);
			prepStmt.setLong(2, FAULT_HOUR);
			
//			logger.debug("End");
			
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				wecId = rs.getString("S_WEC_ID");
				missingDaysCount = rs.getInt("Missing_days_count");
				wec = wecDao.get(wecId);
				
				missingScadaDataVo = new MissingScadaDataVo();
				missingScadaDataVo.setDate(date);
				missingScadaDataVo.setNoOfDaysMissing(missingDaysCount);
				missingScadaDataVo.setMasterVo(wec);
				
				missingScadaDataVos.add(missingScadaDataVo);
			}
			
			//Check data Availability
			query = 
					"Select S_wec_id, MISSING_SCADA_DATA.GET_MISSING_DAYS(wecmaster.S_wec_id, ? ) as Missing_Days_Count " + 
					"from tbl_wec_master wecmaster " + 
					"where S_scada_flag = 1 " + 
					"and S_status = 1 " + 
					"and S_wec_id not in (Select S_wec_id from tbl_reading_summary summary where D_Reading_date = ? ) " ; 
 
			logger.debug("Start");
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, date);
			prepStmt.setString(2, date);
			
			rs = prepStmt.executeQuery();
			
			logger.debug("End");
			
			while (rs.next()) {
				
				wecId = rs.getString("S_WEC_ID");
				missingDaysCount = rs.getInt("Missing_days_count");
				wec = wecDao.get(wecId);
				
				missingScadaDataVo = new MissingScadaDataVo();
				missingScadaDataVo.setDate(date);
				missingScadaDataVo.setNoOfDaysMissing(missingDaysCount);
				missingScadaDataVo.setMasterVo(wec);
				
				missingScadaDataVos.add(missingScadaDataVo);
			}
			
			logger.warn("Missing Scada Data Size: " + missingScadaDataVos.size());
			return missingScadaDataVos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}

	}
	
	private boolean isMappingProper(IWecMasterVo wec) throws SQLException {
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String wecId = wec.getId();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = 
					"select count(1) as RowCount " + 
					"from  scadadw.tbl_plant_master plant, " + 
					"      tbl_wec_master wec " + 
					"where plant.s_serial_no = wec.s_technical_no " + 
					"and wec.S_wec_id = ? " ; 

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, wecId);
			
			rs = prepStmt.executeQuery();
			if(rs.next()) {
				return (rs.getInt("RowCount") == 1);  				
			}
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return false;
	}
	
	private boolean isDataAvailable(IPlantMasterVo plant, String date) throws SQLException{
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String locationNO = plant.getLocationNo();
		String plantNo = plant.getPlantNo();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select count(1) as Count " +
					"from scadadw.TBL_GENERATION_MIN_10 " +
					"where D_DATE= ? " +
					"and S_LOCATION_NO= ? " +
					"and S_PLANT_NO= ? ";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, date);
			prepStmt.setString(2, locationNO);
			prepStmt.setString(3, plantNo);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
			
				if(rs.getInt("Count") == 0){
					return false;
				}else{
					return true;
				}						
			}
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return false;
	}
	
	private boolean isDataZero(IPlantMasterVo plant, String date) throws SQLException{
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String locationNO = plant.getLocationNo();
		String plantNo = plant.getPlantNo();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select N_CUMULATIVE_WORKH,N_CUMULATIVE_KWH " +
					"from scadadw.TBL_GENERATION_MIN_10 " +
					"where D_DATE= ? " +
					"and S_LOCATION_NO= ? " +
					"and S_PLANT_NO= ? ";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, date);
			prepStmt.setString(2, locationNO);
			prepStmt.setString(3, plantNo);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				//if( ! /*(rs.getInt("N_CUMULATIVE_WORKH") == 0) &&*/ (rs.getInt("N_CUMULATIVE_KWH") == 0)  ) {
				if( (rs.getInt("N_CUMULATIVE_KWH") == 0) ) {
					return true;
				}else{
					return false;
				}
			}
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return false;
	}
	
	private boolean isDataConstant(IPlantMasterVo plant, String date) throws SQLException{
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String locationNO = plant.getLocationNo();
		String plantNo = plant.getPlantNo();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select count(distinct N_CUMULATIVE_KWH) as Count " +
					"from scadadw.TBL_GENERATION_MIN_10 " +
					"where D_DATE= ? " +
					"and S_LOCATION_NO= ? " +
					"and S_PLANT_NO= ?" +
					"and N_CUMULATIVE_KWH <> 0 ";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, date);
			prepStmt.setString(2, locationNO);
			prepStmt.setString(3, plantNo);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {			
	
				if(rs.getInt("Count") == 1){
					return true;
				}else{
					return false;
				}
				
			}			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return false;
	}
	
	private boolean isDataJump(IPlantMasterVo plant, String date) throws SQLException{
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String locationNO = plant.getLocationNo();
		String plantNo = plant.getPlantNo();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "select a.N_HOUR,a.N_MINUTE,a.N_SECOND, kwh1,kwh2, kwh2-kwh1 as Diff, W2 - W1 as OpHr_Diff " + 
					"  FROM ( " + 
					"   " + 
					"      SELECT N_HOUR,N_MINUTE,N_SECOND,N_CUMULATIVE_KWH  as KWH1, n_cumulative_workh as W1,row_number () over ( order by N_hour, N_minute, N_second) as rn " + 
					"        FROM scadadw.TBL_GENERATION_MIN_10  " + 
					"        where D_DATE= ?  " + 
					"        and S_LOCATION_NO= ?  " + 
					"        and S_PLANT_NO= ?  " +
					"and N_CUMULATIVE_KWH <> 0 " +
					"         " + 
					"         " + 
					"       ) a " + 
					"       ,( " + 
					"        SELECT N_HOUR,N_MINUTE,N_SECOND,N_CUMULATIVE_KWH as KWH2, n_cumulative_workh as W2, row_number () over ( order by N_hour, N_minute, N_second) as rn " + 
					"        FROM scadadw.TBL_GENERATION_MIN_10  " + 
					"        where D_DATE= ?  " + 
					"        and S_LOCATION_NO= ?  " + 
					"        and S_PLANT_NO= ?  " +
					"and N_CUMULATIVE_KWH <> 0 " +
					"         " + 
					"       ) b " + 
					"where a.rn + 1 = b.rn " + 
					"order by a.n_hour, a.n_minute, a.n_second " ; 
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, date);
			prepStmt.setString(2, locationNO);
			prepStmt.setString(3, plantNo);
			prepStmt.setString(4, date);
			prepStmt.setString(5, locationNO);
			prepStmt.setString(6, plantNo);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {			
			
				if((rs.getInt("Diff") > WSD_ROW_CUT_OFF || rs.getInt("Diff") < 0) || (rs.getInt("OpHr_Diff") > 1 || rs.getInt("OpHr_Diff") < 0)){
					return true;
				}
				
			}			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return false;
	}
	
	private boolean isOperatingHourGreaterThanGeneration(IPlantMasterVo plant, String date) throws SQLException{
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String locationNO = plant.getLocationNo();
		String plantNo = plant.getPlantNo();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select count(1) as count " + 
					"from scadadw.tbl_generation_min_10 " + 
					"where d_date = ? " + 
					"and S_location_no = ? " + 
					"and S_plant_no = ? " + 
					"and n_cumulative_workh > n_cumulative_kwh " ; 
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, date);
			prepStmt.setString(2, locationNO);
			prepStmt.setString(3, plantNo);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {			
			
				if(rs.getInt("Count") > 0){
					return true;
				}
				
			}			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return false;
	}
	
	public static void main(String[] args) {
		
	}
	
	public void reason(MissingScadaDataVo vo) throws SQLException{
		IPlantMasterVo plant = vo.getWec().getPlant();
		String date = vo.getDate();
		
		if(!isMappingProper(vo.getWec()))
			vo.setReason(MissingScadaDataReason.MAPPING_NOT_PROPER);
		else if(!isDataAvailable(plant, date)) 		
			vo.setReason(MissingScadaDataReason.DATA_NOT_AVAILABLE); 
		else if(isDataZero(plant, date)) 		
			vo.setReason(MissingScadaDataReason.DATA_ZERO);
		else if(isDataConstant(plant, date)) 	
			vo.setReason(MissingScadaDataReason.DATA_CONSTANT);
		else if(isDataJump(plant, date)) 		
			vo.setReason(MissingScadaDataReason.DATA_JUMP);
		else if(isOperatingHourGreaterThanGeneration(plant, date)) 
			vo.setReason(MissingScadaDataReason.OPERATING_HR_GREATER_GENERATION);
		else if(isDataJumpPreviously(plant, date))
			vo.setReason(MissingScadaDataReason.DATA_JUMP_PREVIOUSLY);
		else
			vo.setReason(MissingScadaDataReason.UNKNOWN);
	}

	private boolean isDataJumpPreviously(IPlantMasterVo plant, String date) throws SQLException {
		return isDataJump(plant, DateUtility.getPreviousDateFromGivenDateInString(date));
	}
	
}
