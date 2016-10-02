package com.enercon.struts.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.enercon.admin.dao.WcareDao;
import com.enercon.global.utility.ReadWriteExcelFile;
import com.enercon.struts.exception.ExcelException;
import com.enercon.struts.exception.GridBifurcationException;
import com.enercon.struts.form.InitialReadingUploadForm;

public class InitialReadingUploadAction extends Action{
	private final static Logger logger = Logger.getLogger(InitialReadingUploadAction.class);
	public InitialReadingUploadAction() {
		
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession(true);
		String loginId = session.getAttribute("loginID").toString() + "_IR";
		ActionMessages messages = new ActionMessages();
		InitialReadingUploadForm initialReadingUpload = null;
		WcareDao dao = new WcareDao();
		
		try{
			initialReadingUpload = (InitialReadingUploadForm) form;
			
			FormFile excelFile = initialReadingUpload.getExcelFile();
			File uploadedExcelFile = uploadToServer(excelFile);
			ArrayList<ArrayList<String>> excelData = readExcelData(uploadedExcelFile, initialReadingUpload.getExcelSheetName());
			
			dao.validateDataForInitialReading(excelData);
			dao.insertIntoWECReading(excelData, loginId);

			messages.add("successfulUploaded", new ActionMessage("message.common.file.upload"));
			saveMessages(request, messages); // storing messages as request attributes
			
		}
		catch(OfficeXmlFileException e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			messages.add("dataNotUploaded", new ActionMessage("message.err.gridbifurcation.date.notuploaded"));
			saveMessages(request, messages); // storing messages as request attributes
		}
		catch(FileNotFoundException e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			messages.add("dataNotUploaded", new ActionMessage("message.err.gridbifurcation.date.notuploaded"));
			saveMessages(request, messages); // storing messages as request attributes
		}
		catch(IOException e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			messages.add("dataNotUploaded", new ActionMessage("message.err.gridbifurcation.date.notuploaded"));
			saveMessages(request, messages); // storing messages as request attributes
		}
		catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			messages.add("fileDateProblem", new ActionMessage("message.err.gridbifurcation.date.properformat"));
			saveMessages(request, messages); // storing messages as request attributes
		}
		catch (GridBifurcationException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			messages.add("fileTimeProblem", new ActionMessage(e.getMessage(), e.getRecordNo()));
			saveMessages(request, messages); // storing messages as request attributes
		}
		catch (ExcelException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			messages.add("excelSheetNotPresent", new ActionMessage(e.getMsg()));
			saveMessages(request, messages); // storing messages as request attributes
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			messages.add("dataNotUploaded", new ActionMessage("message.err.gridbifurcation.date.notuploaded"));
			saveMessages(request, messages); // storing messages as request attributes
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			messages.add("dataNotUploaded", new ActionMessage("message.err.gridbifurcation.date.notuploaded"));
			saveMessages(request, messages); // storing messages as request attributes
		}
		
		return mapping.findForward("success");
	}
	
//	private void checkDataAlreadyPresent(ArrayList<ArrayList<String>> excelData) throws GridBifurcationException, SQLException{ 
//		int recordNo = 0;
//		String wecId = null;
//		for (ArrayList<String> excelRecord : excelData) {
//			recordNo++;
//			if(recordNo == 1){
//				continue;
//			}
//			
//			wecId = getWECIdFromDescription(excelRecord.get(1).trim());
//			if(checkAlreadyUploadedData(wecId, DateUtility.convertDateFormats(excelRecord.get(0), "dd.MM.yyyy", "dd-MMM-yyyy")) > 0){
//				throw new GridBifurcationException("message.err.gridbifurcation.data.present", recordNo);
//			}
//		}
//	}

//	private int checkAlreadyUploadedData(String wecId, String date) throws SQLException {
//
//		int recordCount = -1;
//
//		String sqlQuery = "";
//		PreparedStatement prepStmt = null;
//		ResultSet rs = null;
//		
//		try{
//			sqlQuery = 
//					"Select count(1) " + 
//					"from tbl_wec_reading " + 
//					"where D_reading_date = ? " + 
//					"and S_wec_id = ? " + 
//					"and S_created_by not in ('System') " ; 
//			prepStmt = conn.prepareStatement(sqlQuery);
//			prepStmt.setObject(1, date);
//			prepStmt.setObject(2, wecId);
//			rs = prepStmt.executeQuery();
//			if(rs.next()) {
//				recordCount = rs.getInt(1);
//			}
//			return recordCount;
//		}
//		finally{
//			try{
//				if(prepStmt != null){
//					prepStmt.close();
//				}
//				if(rs != null){
//					rs.close();
//				}
//			}
//			catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//	
//	}


	private File uploadToServer(FormFile file)
			throws FileNotFoundException, IOException {
		String filePath = 
				getServlet().getServletContext().getRealPath("/") +"upload";

		//create the upload folder if not exists
		File folder = new File(filePath);
		if(!folder.exists()){
			folder.mkdir();
		}

		String fileName = file.getFileName();

		File newFile = new File(filePath, fileName);	    
		if(!("").equals(fileName)){  

			FileOutputStream fos = new FileOutputStream(newFile);
			fos.write(file.getFileData());
			fos.flush();
			fos.close();
		}
		return newFile;
	}

	private ArrayList<ArrayList<String>> readExcelData(File uploadedExcelFile, String sheetName) throws IOException, ExcelException {
		if(uploadedExcelFile.getName().endsWith(".xls")){
			return ReadWriteExcelFile.readXLSFile(uploadedExcelFile.getAbsolutePath(), sheetName);
		}
		else if(uploadedExcelFile.getName().endsWith(".xlsx")){
			return ReadWriteExcelFile.readXLSXFile(uploadedExcelFile.getAbsolutePath(), sheetName);
		}
		else{
			return null;
		}

	}


}
