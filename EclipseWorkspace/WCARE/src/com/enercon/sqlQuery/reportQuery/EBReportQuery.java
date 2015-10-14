package com.enercon.sqlQuery.reportQuery;

public interface EBReportQuery {
	public final static String oneEBWECWiseInfoForOneDayQuery = 
			"Select * From Meta_Data "
			+ "Where S_EB_Id = ? " 
			+ "and D_Reading_Date = ? " 
			+ "order by S_WECSHORT_DESCR";
	/*
Select * From Meta_Data
Where S_EB_Id = ''
and D_Reading_Date = ''
order by S_WECSHORT_DESCR;

	 */
	
	public final static String oneEBWECWiseInfoForOneMonthQuery = 
				"Select * From Meta_Data  " +
				"Where S_Eb_Id = ?  " +
				"And extract(month from D_READING_DATE) = ?  " +
				"And extract(year from D_READING_DATE) = ?  " +
				"order by D_Reading_Date, S_Wecshort_Descr " ;
	/*
Select * From Meta_Data
Where S_Eb_Id = ''
And extract(month from D_READING_DATE) = ''
And extract(year from D_READING_DATE) = ''
order by D_Reading_Date, S_Wecshort_Descr  ;

	 */
	
	public final static String oneEBWECWiseInfoForBetweenDaysQuery = 
				"Select * From Meta_Data  " +
				"Where S_Eb_Id = ?  " +
				"And D_Reading_Date Between ? And ?  " +
				"order by D_Reading_Date, S_Wecshort_Descr " ;
	/*
Select * From Meta_Data
Where S_Eb_Id = ''
And D_Reading_Date Between '' And ''
order by D_Reading_Date, S_Wecshort_Descr  ;

	 */
	
	public final static String oneEBTotalForOneDayQuery = 
				"Select count(S_wec_ID) As Total_WEC,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
				"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, " +
				"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
				"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
				"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " +
				"From Meta_Data " + 
				"Where S_Eb_Id = ? " + 
				"And D_Reading_Date = ? " + 
				"order by S_WECSHORT_DESCR ";
	/*
Select count(S_wec_ID) As Total_WEC,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,
sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN,
sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd,
sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT,
sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST
From Meta_Data
Where S_Eb_Id = ''
And D_Reading_Date = ''
order by S_WECSHORT_DESCR ;

	 */
	
	public final static String oneEBTotalForOneMonthQuery =
		"Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
		"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, " +
		"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
		"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
		"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(TRIALRUN) AS TRIALRUN " +
		"From Meta_Data " +
		"Where S_Eb_Id = ? " +
		"And Extract (Month From D_Reading_Date) = ? " +
		"and Extract (Year From D_Reading_Date) = ? " ;
	/*
Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,
sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor,
sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd,
sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT,
sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(TRIALRUN) AS TRIALRUN
From Meta_Data
Where S_Eb_Id = ''
And Extract (Month From D_Reading_Date) = ''
and Extract (Year From D_Reading_Date) = ''  ;

	 */
	
	public final static String oneEBTotalForOneMonthButLessThanCurrentReadingDateQuery =
			"Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
			"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, " +
			"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
			"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
			"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(TRIALRUN) AS TRIALRUN " +
			"From Meta_Data " +
			"Where S_Eb_Id = ? " +
			"And Extract (Month From D_Reading_Date) = ? " +
			"and Extract (Year From D_Reading_Date) = ? " +
			"and D_READING_DATE <= ? " ;
	/**
Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,
sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor,
sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd,
sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT,
sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(TRIALRUN) AS TRIALRUN
From Meta_Data
Where S_Eb_Id = ''
And Extract (Month From D_Reading_Date) = ''
and Extract (Year From D_Reading_Date) = ''
and D_READING_DATE <= ''  ;

	 */
	
	public final static String oneEBTotalForOneFiscalYearQuery =
			"Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
			"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, " +
			"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
			"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
			"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(TRIALRUN) AS TRIALRUN " +
			"From Meta_Data " +
			"Where S_Eb_Id = ? " +
			"And ((Extract(Year From D_Reading_Date) = ? " +
			"And Extract(Month From D_Reading_Date) In (1,2,3)) " +
			"Or(Extract(Year From D_Reading_Date) = ? " +
			"And Extract(Month From D_Reading_Date) In (4,5,6,7,8,9,10,11,12))) " +
			"order by D_reading_date " ;
	/*
Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,
sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor,
sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd,
sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT,
sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(TRIALRUN) AS TRIALRUN
From Meta_Data
Where S_Eb_Id = ''
And ((Extract(Year From D_Reading_Date) = ''
And Extract(Month From D_Reading_Date) In (1,2,3))
Or(Extract(Year From D_Reading_Date) = ''
And Extract(Month From D_Reading_Date) In (4,5,6,7,8,9,10,11,12)))
order by D_reading_date  ;

	 */
	
	public final static String oneEBTotalForBetweenDaysQuery = 
			"Select count(S_wec_ID) As Total_WEC,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
			"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, " +
			"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
			"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
			"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " +
			"From Meta_Data " + 
			"Where S_Eb_Id = ? " + 
			"And D_Reading_Date between ? and ? " + 
			"order by S_WECSHORT_DESCR ";
	/*
Select count(S_wec_ID) As Total_WEC,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,
sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor,
sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd,
sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT,
sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST
From Meta_Data
Where S_Eb_Id = ''
And D_Reading_Date between '' and ''
order by S_WECSHORT_DESCR ;

	 */
}
