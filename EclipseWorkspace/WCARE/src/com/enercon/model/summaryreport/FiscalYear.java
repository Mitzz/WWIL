package com.enercon.model.summaryreport;

import java.text.ParseException;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.NumberUtility;

public enum FiscalYear{
	
	FY200809(2008), FY200708(2007), FY200607(2006),	FY200506(2005), FY200405(2004), FY200910(2009),FY201011(2010), FY201112(2011), FY201213(2012),	FY201314(2013), FY201415(2014), FY201516(2015);
	
	int value;

	private FiscalYear(int value) {
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
	public static FiscalYear valueOf(int fiscalYear) {

		switch (fiscalYear) {
			case 2004 : return FiscalYear.FY200405;
			case 2005 : return FiscalYear.FY200506;
			case 2006 : return FiscalYear.FY200607;
			case 2007 : return FiscalYear.FY200708;
			case 2008 : return FiscalYear.FY200809;
			case 2009 : return FiscalYear.FY200910;
			case 2010 : return FiscalYear.FY201011;
			case 2011 : return FiscalYear.FY201112;
			case 2012 : return FiscalYear.FY201213;
			case 2013 : return FiscalYear.FY201314;
			case 2014 : return FiscalYear.FY201415;	
			case 2015 : return FiscalYear.FY201516;
			default   : throw new IllegalArgumentException("Fiscal Year '" + fiscalYear + "-" + (fiscalYear + 1) + "' is not defined");
		}
	}
	
	public static FiscalYear fiscalYear(Year year, Month month){
		return FiscalYear.valueOf(year.getValue() - ((int)Month.fiscalValue(month))/10);
	}
	
	public static void main(String[] args) {
		System.out.println(FY200506.getReportValue());
		
	}
	
	public static FiscalYear[] values(int lo, int hi) {
		FiscalYear[] fys = new FiscalYear[hi - lo + 1];
		int index = 0; 
		for(FiscalYear fy : FiscalYear.values()){
			if(lo <= fy.getValue() && hi >= fy.getValue()){
				fys[index++] = valueOf(fy.getValue());
			}
		}
		return fys;
	}

	public String getFirstDate() throws ParseException {
		return DateUtility.getFirstDateOfFiscalYear(this.getValue());
	}

	public String getLastDate() throws ParseException {
		return DateUtility.getLastDateOfFiscalYear(this.getValue());
	}
	
	public String getReportValue(){
		return "FY " + NumberUtility.getLastDigit(getValue(), 2) + "-" + NumberUtility.getLastDigit(getValue() + 1, 2) + "";
	}
	
	
}
