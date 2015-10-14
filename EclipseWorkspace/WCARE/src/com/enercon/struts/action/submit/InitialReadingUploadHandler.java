package com.enercon.struts.action.submit;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.ExcelReader;
import com.enercon.global.utils.JDBCUtils;
			 
public class InitialReadingUploadHandler extends Action{
	private final String UPLOAD_DIRECTORY = "C:/uploads";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	       
        //process only if its multipart content
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
               
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        String name = new File(item.getName()).getName();
                        System.out.println("Name of File:" + name);
                        item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                    }
                }
            
               //File uploaded successfully
               request.setAttribute("message", "File Uploaded Successfully");
               
            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed due to " + ex);
            }        
          
        }else{
            request.setAttribute("message",
                                 "Sorry this Servlet only handles file upload request");
        }
     
        ArrayList<ArrayList<String>> excelValues = new ExcelReader().method1();
        insertIntoWECReading(excelValues);
        //request.getRequestDispatcher("/Developer/result/result.jsp").forward(request, response);
        return mapping.findForward("success");
    }

	private void insertIntoWECReading(ArrayList<ArrayList<String>> excelValues) {
		int recordNo = 0;
		int cellNo = 0;
		
		String dateValue = "";
		String wecId = "";
		String readType = "";
		String remark = "";
		String cumulativeGeneration = "";
		String cumulativeOperatingHours = "";
		String actualGeneration = "";
		String actualOperatingHours = "";
		
		for (ArrayList<String> eachRecord : excelValues) {
			recordNo++;
			if(recordNo == 1){
				continue;
			}
			for (String cellValue : eachRecord) {
				cellNo++;
				switch (cellNo) {
				case 1:
						dateValue = DateUtility.convertDateFormats(cellValue.trim(), "dd.MM.yyyy", "dd-MMM-yyyy");
						//System.out.println("Date Value:" + dateValue);
					break;
				case 2 :
						wecId = getWECIdFromDescription(cellValue.trim());
						//System.out.println("WEC ID:" + wecId);
						System.out.println(wecId);
					break;
				case 3:
						readType = cellValue.trim();
						//System.out.println("Read Type:" + readType);
					break;
				case 4 :
						remark = cellValue.trim();
						//System.out.println("Remark:" + remark);
					break;
				case 5:
						cumulativeGeneration = cellValue.trim();
						//System.out.println("Cumulative Generation:" + cumulativeGeneration);
					break;
				case 6 :
						cumulativeOperatingHours = cellValue.trim();
						//System.out.println("Cumulative Operating Hours:" + cumulativeOperatingHours);
					break;
				case 7:
						actualGeneration = cellValue.trim();
						//System.out.println("Actual Generation:" + actualGeneration);
					break;
				case 8 :
						actualOperatingHours = cellValue.trim();
						//System.out.println("Actual Operating Hours:" + actualOperatingHours);
					break;	
				default:
					
				}
			}
			insertIntoWECReadingWithoutRemark(dateValue, wecId, readType, remark, cumulativeGeneration, cumulativeOperatingHours, actualGeneration, actualOperatingHours);
//			insertIntoWECReading(dateValue, wecId, readType, remark, cumulativeGeneration, cumulativeOperatingHours, actualGeneration, actualOperatingHours);
			cellNo = 0;
		}
	}

	private void insertIntoWECReading(String dateValue, String wecId,
			String readType, String remark, String cumulativeGeneration,
			String cumulativeOperatingHours, String actualGeneration,
			String actualOperatingHours) {

		String customerID = getCustomerIDFromWECId(wecId);
		String ebId = getEbIdFromWECId(wecId);
		
		//System.out.println("Customer Id:" + customerID);
		//System.out.println("EB Id:" + ebId);
		
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		//String customerID = null;
		try{
			conn = conmanager.getConnection();
			sqlQuery = 	"Merge Into Tbl_Wec_Reading T " + 
						"        Using (Select ? As D_Reading_Date,'0808000022' As S_Mp_Id, ? As S_Wec_Id From Dual) S " + 
						"        On (T.S_Wec_Id = S.S_Wec_Id And T.D_Reading_Date = S.D_Reading_Date And T.S_Mp_Id = S.S_Mp_Id and T.S_CREATED_BY = 'SYSTEM') " + 
						"        When Matched Then  " + 
						"          Update Set N_Value = ?, S_Last_Modified_By = 'SYS_Update' , D_Last_Modified_Date = To_Date(Sysdate),  " + 
						"                    N_Actual_Value = ? " + 
						"        When Not Matched Then " + 
						"        Insert(S_Reading_Id, D_Reading_Date, S_Mp_Id, S_Wec_Id, S_Eb_Id, S_Customer_Id, N_Value,  " + 
						"              S_Created_By, D_Created_Date, S_Last_Modified_By, D_Last_Modified_Date, N_Actual_Value,  " + 
						"              S_Reading_Type, N_Publish, S_Remarks, N_Publish_Fact)  " + 
						"        Values (Scadadw.Wec_Scada_Reading_Id.Nextval,?, '0808000022', ?, ?, ?, ?, " + 
						"                'SYSTEM',To_Date(Sysdate),'SYS_Insert',To_Date(Sysdate),?, " + 
						"                ?,'1',?,0) " ; 
  

			prepStmt = conn.prepareStatement(sqlQuery);
			
			//Source Data
			prepStmt.setObject(1, dateValue);
			prepStmt.setObject(2, wecId);
			
			//Update Data
			prepStmt.setObject(3, cumulativeGeneration);
			prepStmt.setObject(4, actualGeneration);
			
			//Insert Data
			prepStmt.setObject(5, dateValue);
			prepStmt.setObject(6, wecId);
			prepStmt.setObject(7, ebId);
			prepStmt.setObject(8, customerID);
			prepStmt.setObject(9, cumulativeGeneration);
			prepStmt.setObject(10, actualGeneration);
			prepStmt.setObject(11, readType);
			prepStmt.setObject(12, (remark == null  || remark.equalsIgnoreCase("NIL")) ? "" : remark);
			
			prepStmt.executeUpdate();
			
			prepStmt.close();
			sqlQuery = 	"Merge Into Tbl_Wec_Reading T " + 
						"        Using (Select ? As D_Reading_Date,'0808000023' As S_Mp_Id, ? As S_Wec_Id From Dual) S " + 
						"        On (T.S_Wec_Id = S.S_Wec_Id And T.D_Reading_Date = S.D_Reading_Date And T.S_Mp_Id = S.S_Mp_Id and T.S_CREATED_BY = 'SYSTEM') " + 
						"        When Matched Then  " + 
						"          Update Set N_Value = ?, S_Last_Modified_By = 'SYS_Update' , D_Last_Modified_Date = To_Date(Sysdate),  " + 
						"                    N_Actual_Value = ? " + 
						"        When Not Matched Then " + 
						"        Insert(S_Reading_Id, D_Reading_Date, S_Mp_Id, S_Wec_Id, S_Eb_Id, S_Customer_Id, N_Value,  " + 
						"              S_Created_By, D_Created_Date, S_Last_Modified_By, D_Last_Modified_Date, N_Actual_Value,  " + 
						"              S_Reading_Type, N_Publish, N_Publish_Fact)  " + 
						"        Values (Scadadw.Wec_Scada_Reading_Id.Nextval,?, '0808000023', ?, ?, ?, ?, " + 
						"                'SYSTEM',To_Date(Sysdate),'SYS_Insert',To_Date(Sysdate),?, " + 
						"                ?,'1',0) " ; 


		prepStmt = conn.prepareStatement(sqlQuery);
		
		//Source Data
		prepStmt.setObject(1, dateValue);
		prepStmt.setObject(2, wecId);
		
		//Update Data
		prepStmt.setObject(3, cumulativeOperatingHours);
		prepStmt.setObject(4, actualOperatingHours);
		
		//Insert Data
		prepStmt.setObject(5, dateValue);
		prepStmt.setObject(6, wecId);
		prepStmt.setObject(7, ebId);
		prepStmt.setObject(8, customerID);
		prepStmt.setObject(9, cumulativeOperatingHours);
		prepStmt.setObject(10, actualOperatingHours);
		prepStmt.setObject(11, readType);
		//prepStmt.setObject(12, remark == "NIL" ? null : remark);
		
		prepStmt.executeUpdate();
		prepStmt.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		//return null;
		
	}
	
	private void insertIntoWECReadingWithoutRemark(String dateValue, String wecId,
			String readType, String remark, String cumulativeGeneration,
			String cumulativeOperatingHours, String actualGeneration,
			String actualOperatingHours) {

		String customerID = getCustomerIDFromWECId(wecId);
		String ebId = getEbIdFromWECId(wecId);
		
		//System.out.println("Customer Id:" + customerID);
		//System.out.println("EB Id:" + ebId);
		
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		//String customerID = null;
		try{
			conn = conmanager.getConnection();
			sqlQuery = 	"Merge Into Tbl_Wec_Reading T " + 
						"        Using (Select ? As D_Reading_Date,'0808000022' As S_Mp_Id, ? As S_Wec_Id From Dual) S " + 
						"        On (T.S_Wec_Id = S.S_Wec_Id And T.D_Reading_Date = S.D_Reading_Date And T.S_Mp_Id = S.S_Mp_Id)" + 
						"        When Matched Then  " + 
						"          Update Set N_Value = ?, S_Last_Modified_By = 'SYS_Update' , D_Last_Modified_Date = To_Date(Sysdate),  " + 
						"                    N_Actual_Value = ? " + 
						"        When Not Matched Then " + 
						"        Insert(S_Reading_Id, D_Reading_Date, S_Mp_Id, S_Wec_Id, S_Eb_Id, S_Customer_Id, N_Value,  " + 
						"              S_Created_By, D_Created_Date, S_Last_Modified_By, D_Last_Modified_Date, N_Actual_Value,  " + 
						"              S_Reading_Type, N_Publish, S_Remarks, N_Publish_Fact)  " + 
						"        Values (Scadadw.Wec_Scada_Reading_Id.Nextval,?, '0808000022', ?, ?, ?, ?, " + 
						"                'SYSTEM',To_Date(Sysdate),'SYS_Insert',To_Date(Sysdate),?, " + 
						"                ?,'1',?,0) " ; 
  

			prepStmt = conn.prepareStatement(sqlQuery);
			
			//Source Data
			prepStmt.setObject(1, dateValue);
			prepStmt.setObject(2, wecId);
			
			//Update Data
			prepStmt.setObject(3, cumulativeGeneration);
			prepStmt.setObject(4, actualGeneration);
			
			//Insert Data
			prepStmt.setObject(5, dateValue);
			prepStmt.setObject(6, wecId);
			prepStmt.setObject(7, ebId);
			prepStmt.setObject(8, customerID);
			prepStmt.setObject(9, cumulativeGeneration);
			prepStmt.setObject(10, actualGeneration);
			prepStmt.setObject(11, readType);
			prepStmt.setObject(12, (remark == null  || remark.equalsIgnoreCase("NIL")) ? "" : remark);
			
			prepStmt.executeUpdate();
			
			prepStmt.close();
			sqlQuery = 	"Merge Into Tbl_Wec_Reading T " + 
						"        Using (Select ? As D_Reading_Date,'0808000023' As S_Mp_Id, ? As S_Wec_Id From Dual) S " + 
						"        On (T.S_Wec_Id = S.S_Wec_Id And T.D_Reading_Date = S.D_Reading_Date And T.S_Mp_Id = S.S_Mp_Id) " + 
						"        When Matched Then  " + 
						"          Update Set N_Value = ?, S_Last_Modified_By = 'SYS_Update' , D_Last_Modified_Date = To_Date(Sysdate),  " + 
						"                    N_Actual_Value = ? " + 
						"        When Not Matched Then " + 
						"        Insert(S_Reading_Id, D_Reading_Date, S_Mp_Id, S_Wec_Id, S_Eb_Id, S_Customer_Id, N_Value,  " + 
						"              S_Created_By, D_Created_Date, S_Last_Modified_By, D_Last_Modified_Date, N_Actual_Value,  " + 
						"              S_Reading_Type, N_Publish, N_Publish_Fact)  " + 
						"        Values (Scadadw.Wec_Scada_Reading_Id.Nextval,?, '0808000023', ?, ?, ?, ?, " + 
						"                'SYSTEM',To_Date(Sysdate),'SYS_Insert',To_Date(Sysdate),?, " + 
						"                ?,'1',0) " ; 


		prepStmt = conn.prepareStatement(sqlQuery);
		
		//Source Data
		prepStmt.setObject(1, dateValue);
		prepStmt.setObject(2, wecId);
		
		//Update Data
		prepStmt.setObject(3, cumulativeOperatingHours);
		prepStmt.setObject(4, actualOperatingHours);
		
		//Insert Data
		prepStmt.setObject(5, dateValue);
		prepStmt.setObject(6, wecId);
		prepStmt.setObject(7, ebId);
		prepStmt.setObject(8, customerID);
		prepStmt.setObject(9, cumulativeOperatingHours);
		prepStmt.setObject(10, actualOperatingHours);
		prepStmt.setObject(11, readType);
		//prepStmt.setObject(12, remark == "NIL" ? null : remark);
		
		prepStmt.executeUpdate();
		prepStmt.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		//return null;
		
	}

	public String getEbIdFromWECId(String wecId) {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String ebId = null;
		try{
			conn = conmanager.getConnection();
			sqlQuery = 	"Select S_EB_Id " + 
						"From Customer_Meta_Data " + 
						"where S_wec_id = ? ";  

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ebId = rs.getString("S_EB_Id");
			}
			return ebId;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	public String getCustomerIDFromWECId(String wecId) {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String customerId = null;
		try{
			conn = conmanager.getConnection();
			sqlQuery = 	"Select S_Customer_Id " + 
						"From Customer_Meta_Data " + 
						"where S_wec_id = ? " ; 

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				customerId = rs.getString("S_Customer_Id");
			}
			return customerId;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	public String getWECIdFromDescription(String wecDescription) {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String wecID = null;
		try{
			conn = conmanager.getConnection();
			sqlQuery = "Select S_WEC_ID From Tbl_Wec_Master Where S_Wecshort_Descr in (?)";
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecDescription);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecID = rs.getString("S_WEC_ID");
			}
			return wecID;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
}

