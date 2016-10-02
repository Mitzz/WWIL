package com.enercon.working;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.struts.dao.SelectVoDao;

public class Rporting implements WcareConnector{
	 private final static Logger logger = Logger.getLogger(Rporting.class);
	public static void main(String[] args) {
		System.out.println("Successful");
		try {
			new Rporting().getWecId("07-JAN-2015");
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
	
	private void getWecId(String date) throws SQLException{
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String query = "";
		Set<String> result = new LinkedHashSet<String>();
		Set<String> finalResult = new LinkedHashSet<String>();
		int count = 0;
		try {
			
			
			conn = wcareConnector.getConnectionFromPool();
			for(int i = 0; i < 7; i++){
				query = "Select Gen.D_Date, " + 
						"      Plant.S_Location_No                                                                                                                                               As \"Loc_No\", " + 
						"      Plant.S_Plant_No                                                                                                                                                  As \"plant\", " + 
						"      Plant.S_Wec_Name                                                                                                                                                  As \"WecName\", " + 
						"      wecmaster.S_Wec_Id                                                                                                                                                  As \"WecId\", " +
						"      Wectype.N_Wec_Capacity                                                                                                                                            As \"Cap\", " + 
						"      Scadadw.Get_Max_Generation(Gen.D_Date, Plant.S_Location_No, Plant.S_Plant_No)                                                                                     As \"Cur_Gen\", " + 
						"      Scadadw.Get_Max_Generation(Gen.D_Date                                         - 1, Plant.S_Location_No, Plant.S_Plant_No)                                         As \"Prev_Gen\", " + 
						"      Scadadw.Get_Max_Generation(Gen.D_Date, Plant.S_Location_No, Plant.S_Plant_No) - Scadadw.Get_Max_Generation(Gen.D_Date - 1, Plant.S_Location_No, Plant.S_Plant_No) As \"Gen_Diff\", " + 
						"      Scadadw.Get_Max_Operating_Hr(Gen.D_Date, Plant.S_Location_No, Plant.S_Plant_No)                                                                                   As \"Cur_Op\", " + 
						"      Scadadw.Get_Max_Operating_Hr(Gen.D_Date                                         - 1, Plant.S_Location_No, Plant.S_Plant_No)                                                                               As \"Prev_Op\", " + 
						"      Scadadw.Get_Max_Operating_Hr(Gen.D_Date, Plant.S_Location_No, Plant.S_Plant_No) - Scadadw.Get_Max_Operating_Hr(Gen.D_Date - 1, Plant.S_Location_No, Plant.S_Plant_No)                                     As \"Op_Hr_Diff\" " + 
						"From  Scadadw.Tbl_Plant_Master Plant, " + 
						"      Scadadw.Tbl_Generation_Min_10 Gen, " + 
						"      Ecare.Tbl_Wec_Master Wecmaster, " + 
						"      Ecare.Tbl_Wec_Type Wectype " + 
						"Where Gen.S_Location_No        = Plant.S_Location_No " + 
						"  And Gen.S_Plant_No           = Plant.S_Plant_No " + 
						"  And Wecmaster.S_Technical_No = Plant.S_Serial_No " + 
						"  And Wecmaster.S_Wec_Id  In (Select S_Wec_Id " + 
						"                                                            From  Ecare.Tbl_Wec_Reading " + 
						/*1*/"                                                            Where D_Reading_Date = ? " + 
						"                                                            And S_Mp_Id Not   In ('1408000002','0808000022','0808000023','1000000001','0808000029') " + //--Removing Generation, Operating Hour,Grid Shutdown External,Grid Fault External and Grid Trip Count 
						"                                                            And S_Wec_Id      In (Select  Wec.S_Wec_Id " + 
						"                                                                                                From  Scadadw.Tbl_Plant_Master Plant, Ecare.Tbl_Wec_Master Wec " + 
						"                                                                                                Where Plant.S_Serial_No = Wec.S_Technical_No " + 
						"                                                                                                And Wec.S_Wec_Id Not In " + 
						"                                                                                                                                                (Select S_Wec_Id " + 
						"                                                                                                                                                From  Ecare.Tbl_Wec_Reading " + 
						/*2*/"                                                                                                                                                Where D_Reading_Date = ? " + 
						"                                                                                                                                                And S_Created_By   = 'SYSTEM' " + 
						"                                                                                                                                                Group By S_Wec_Id) " + 
						"                                                                                                And (Plant.S_Serial_No In  " + 
						"                                                                                                                                          (SELECT S_serial_no " + 
						"                                                                                                                                          FROM (SELECT rownum rnum,S_serial_no " + 
						"                                                                                                                                                      FROM (Select Plantmas.S_Serial_No " + 
						"                                                                                                                                                                  From  Scadadw.Tbl_Generation_Min_10 Gen, Scadadw.Tbl_Plant_Master Plantmas " + 
						"                                                                                                                                                                  Where Gen.S_Location_No = Plantmas.S_Location_No " + 
						"                                                                                                                                                                  And Gen.S_Plant_No    = Plantmas.S_Plant_No " + 
						/*3*/"                                                                                                                                                                  And D_Date = ? " + 
						"                                                                                                                                                                  Group By Plantmas.S_Serial_No " + 
						"                                                                                                                                                                  ) " + 
						"                                                                                                                                                      WHERE rownum <= " + ((i+1) * 1000) + ") " + 
						"                                                                                                                                          WHERE rnum >= " + ((i * 1000) + 1) + ")) " + 
						"  And Wec.S_Scada_Flag = '1' " + 
						"  And Wec.S_Status    In ('1')) " + 
						"Group By S_Wec_Id " + 
						"Having Sum(Ecare.Get_Fraction(N_Actual_Value)) < (60 * 60 * 60 )) " + 
						"  And Wecmaster.S_Wec_Type = Wectype.S_Wec_Type " + 
						/*4 and 5*/"  And Gen.D_Date Between ? And ? " + 
						"Group By Gen.D_Date, " + 
						"      Plant.S_Location_No, " + 
						"      Plant.S_Plant_No, " + 
						"      Plant.S_Wec_Name, " +
						"      wecmaster.S_Wec_Id, " +
						"      Wectype.N_Wec_Capacity " + 
						"Order By Plant.S_Location_No, " + 
						"      Plant.S_Plant_No , " + 
						"      Gen.D_Date  " ;
				
				prepStmt = conn.prepareStatement(query);

				prepStmt.setString(1, date);
				prepStmt.setString(2, date);
				prepStmt.setString(3, date);
				prepStmt.setString(4, date);
				prepStmt.setString(5, date);
				
				
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					result.add(rs.getString("WecId"));
				}
				
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				
				
			}
			
			System.out.println("Size : " + result.size());
			System.out.println(result);
			
			query = "Select S_wec_id,Sum(Ecare.Get_Fraction(N_Actual_Value)) " + 
					"From  Tbl_Wec_Reading " + 
					"Where D_Reading_Date Between ? And ? " + 
					"And S_Mp_Id Not In ('1408000002','0808000022','0808000023','1000000001','0808000029') " +//Removing GTC, Gen, OpHr, GES and GEF 
					"  And S_Wec_Id  In " + GlobalUtils.getStringFromArrayForQuery(result) + 
					"group by S_wec_id " + 
					"Having Sum(Ecare.Get_Fraction(N_Actual_Value)) < (4 * 60 * 60 )  " + 
					"Order By S_Wec_Id ";
			prepStmt = conn.prepareStatement(query);

			prepStmt.setString(1, date);
			prepStmt.setString(2, date);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				finalResult.add(rs.getString("S_wec_id"));
			}
			
			System.out.println("Size : " + finalResult.size());
			System.out.println(finalResult);
		}
		finally {
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
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	}
}
