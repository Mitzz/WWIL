package com.enercon.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

public class InitialReadingUploadForm extends ActionForm{

	private FormFile excelFile;
	private String excelSheetName;
	
	public InitialReadingUploadForm() {
		super();
		//MethodClass.displayMethodClassName();
	}
	public FormFile getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(FormFile excelFile) {
		//MethodClass.displayMethodClassName();
		this.excelFile = excelFile;
		
	}
	
	
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		//MethodClass.displayMethodClassName();
		//System.out.println("File content type:" + excelFile.getContentType());
		//System.out.println("File Name:" + excelFile.getFileName());
		ActionErrors errors = new ActionErrors();

		if( getExcelFile().getFileSize()== 0){
			errors.add("common.file.err", new ActionMessage("error.common.file.required"));
			return errors;
		}

		if(!("application/vnd.ms-excel".equals(getExcelFile().getContentType()) || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(getExcelFile().getContentType())|| ("application/x-download").equals(getExcelFile().getContentType())) ){
			errors.add("common.file.err.ext", new ActionMessage("error.common.file.textfile.only"));
			return errors;
		}

		//System.out.println(getExcelFile().getFileSize());
		if(getExcelFile().getFileSize() > 1024000){ //100kb
			errors.add("common.file.err.size", new ActionMessage("error.common.file.size.limit", 100));
			return errors;
		}
		
		if(getExcelSheetName() != null && getExcelSheetName().trim().equals("")){ //100kb
			errors.add("error.file.excel.sheetname", new ActionMessage("message.err.gridbifurcation.excel.sheetname"));
			return errors;
		}
		if(getExcelSheetName() == null){
			errors.add("error.file.excel.sheetname", new ActionMessage("message.err.gridbifurcation.excel.sheetname"));
			return errors;
		}
		

		return errors;
	}
	public String getExcelSheetName() {
		return excelSheetName;
	}
	public void setExcelSheetName(String excelSheetName) {
		this.excelSheetName = excelSheetName;
	}

}
