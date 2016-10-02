package com.enercon.struts.form;

import java.util.Arrays;

import org.apache.struts.action.ActionForm;

import com.enercon.global.utility.DateUtility;

public class GridBifurcationReportForm extends ActionForm {
	
	private String[] wecSelected;
	private String toDate;
	private String fromDate;
	private String reportTypeSelection;
	private boolean windspeed;
	private String structureType;
	private String[] parameterSelected;
		
	@Override
	public String toString() {
		return "GridBifurcationReportForm [wecSelected="
				+ Arrays.toString(wecSelected) + ", toDate=" + toDate
				+ ", fromDate=" + fromDate + ", reportTypeSelection="
				+ reportTypeSelection + ", windspeed=" + windspeed
				+ ", parameterSelected=" + Arrays.toString(parameterSelected)
				+ ", structureType=" + structureType + "]";
	}

	public String[] getWecSelected() {
		return wecSelected;
	}

	public void setWecSelected(String[] wecSelected) {
		this.wecSelected = wecSelected;
	}

	public String getToDate() {
		return toDate;
	}
	
	public String getToDateInOracleFormat() {
		return DateUtility.convertDateFormats(toDate, "dd/MM/yyyy", "dd-MMM-yyyy");
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public String getFromDateInOracleFormat() {
		return DateUtility.convertDateFormats(fromDate, "dd/MM/yyyy", "dd-MMM-yyyy");
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getReportTypeSelection() {
		return reportTypeSelection;
	}

	public void setReportTypeSelection(String reportTypeSelection) {
		this.reportTypeSelection = reportTypeSelection;
	}

	public String getStructureType() {
		return structureType;
	}

	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}

	public boolean isWindspeed() {
		return windspeed;
	}

	public void setWindspeed(boolean windspeed) {
		this.windspeed = windspeed;
	}

	public String[] getParameterSelected() {
		return parameterSelected;
	}

	public void setParameterSelected(String[] parameterSelected) {
		this.parameterSelected = parameterSelected;
	}

}

/*package com.enercon.struts.form;

import java.util.Arrays;

import org.apache.struts.action.ActionForm;

import com.enercon.global.utility.DateUtility;

public class GridBifurcationReportForm extends ActionForm {

	private String[] wecSelected;
	private String toDate;
	private String fromDate;
	private String reportTypeSelection;
	private boolean windspeed;

	@Override
	public String toString() {
		return "GridBifurcationReportForm [wecSelected="
				+ Arrays.toString(wecSelected) + ", toDate=" + toDate
				+ ", fromDate=" + fromDate + ", reportTypeSelection="
				+ reportTypeSelection + ", windspeed=" + windspeed + "]";
	}

	public String[] getWecSelected() {
		return wecSelected;
	}

	public void setWecSelected(String[] wecSelected) {
		this.wecSelected = wecSelected;
	}

	public String getToDate() {
		return toDate;
	}
	
	public String getToDateInOracleFormat() {
		return DateUtility.convertDateFormats(toDate, "dd/MM/yyyy", "dd-MMM-yyyy");
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public String getFromDateInOracleFormat() {
		return DateUtility.convertDateFormats(fromDate, "dd/MM/yyyy", "dd-MMM-yyyy");
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getReportTypeSelection() {
		return reportTypeSelection;
	}

	public void setReportTypeSelection(String reportTypeSelection) {
		this.reportTypeSelection = reportTypeSelection;
	}

	public boolean isWindspeed() {
		return windspeed;
	}

	public void setWindspeed(boolean windspeed) {
		this.windspeed = windspeed;
	}

}
*/