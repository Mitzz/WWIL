package com.enercon.sqlQuery.reportQuery;

public interface WECReportQuery {
	public final static String oneWECInfoForOneDayQuery = 
			"Select * From Meta_Data "
			+ "Where S_Wec_Id = ? " + "and D_Reading_Date = ? ";
	/*
Select * From Meta_Data
Where S_Wec_Id = ''   and D_Reading_Date = '' ;
	 */

	public final static String oneWECInfoForOneMonthQuery = 
			  "Select * From Meta_Data "
			+ "Where S_Wec_Id = ? "
			+ "And Extract(Year From D_Reading_Date) = ? "
			+ "and extract(month from D_reading_date) = ? "
			+ "order by D_READING_DATE ";
	/*
Select * From Meta_Data
Where S_Wec_Id = ''
And Extract(Year From D_Reading_Date) = ''
and extract(month from D_reading_date) = ''
order by D_READING_DATE ;
	 */

	public final static String oneWECInfoForBetweenDaysQuery = 
			"Select * From Meta_Data "
			+ "Where S_Wec_Id = ? "
			+ "and D_Reading_Date between ? and ? "
			+ "order by D_READING_DATE";
	/*
Select * From Meta_Data
Where S_Wec_Id = ''
and D_Reading_Date between '' and ''
order by D_READING_DATE;

	 */

	public final static String oneWECTotalForOneMonthQuery = 
			"Select Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, "
			+ "Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date, "
			+ "Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs, "
			+ "max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS, "
			+ "sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, "
			+ "sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, "
			+ "sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, "
			+ "sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST "
			+ "From Meta_Data "
			+ "Where S_Wec_Id = ? "
			+ "And Extract(Year From D_Reading_Date) = ? "
			+ "and extract(month from D_reading_date) = ? "
			+ "order by S_WECSHORT_DESCR ";
	/*
Select Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype,
Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date,
Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs,
max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,
sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN,
sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd,
sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT,
sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST
From Meta_Data
Where S_Wec_Id = ''
And Extract(Year From D_Reading_Date) = ''
and extract(month from D_reading_date) = ''
order by S_WECSHORT_DESCR ;

	 */

	public final static String oneWECTotalForOneMonthButLessThanCurrentReadingDateQuery = 
			"Select Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, "
			+ "Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date, "
			+ "Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs, "
			+ "max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS, "
			+ "sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, "
			+ "sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, "
			+ "sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, "
			+ "sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST "
			+ "From Meta_Data "
			+ "Where S_Wec_Id = ? "
			+ "And Extract(Year From D_Reading_Date) = ? "
			+ "and extract(month from D_reading_date) = ? "
			+ "and D_reading_date <= ? " + "order by S_WECSHORT_DESCR ";
	/*
Select Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype,
Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date,
Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs,
max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,
sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN,
sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd,
sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT,
sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST
From Meta_Data
Where S_Wec_Id = ''
And Extract(Year From D_Reading_Date) = ''
and extract(month from D_reading_date) = ''
and D_reading_date <= ''   order by S_WECSHORT_DESCR ;

	 */

	public final static String oneWECTotalForOneFiscalYearQuery = 
			"Select Max( S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, "
			+ "Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date, "
			+ "Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs, "
			+ "max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS, "
			+ "sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, "
			+ "sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, "
			+ "sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, "
			+ "sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST "
			+ "From Meta_Data "
			+ "Where S_Wec_Id = ? "
			+ "And ((Extract(Year From D_Reading_Date) = ? "
			+ "And Extract(Month From D_Reading_Date) In (1,2,3)) "
			+ "Or(Extract(Year From D_Reading_Date) = ? "
			+ "And Extract(Month From D_Reading_Date) In (4,5,6,7,8,9,10,11,12))) "
			+ "order by D_reading_date ";
	/*
Select Max( S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype,
Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date,
Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs,
max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,
sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN,
sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd,
sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT,
sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST
From Meta_Data
Where S_Wec_Id = ''
And ((Extract(Year From D_Reading_Date) = ''
And Extract(Month From D_Reading_Date) In (1,2,3))
Or(Extract(Year From D_Reading_Date) = ''
And Extract(Month From D_Reading_Date) In (4,5,6,7,8,9,10,11,12)))
order by D_reading_date ;

	 */

	public final static String oneWECTotalForBetweenDaysQuery = 
			"Select Max( S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, "
			+ "Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date, "
			+ "Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs, "
			+ "max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS, "
			+ "sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, "
			+ "sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, "
			+ "sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, "
			+ "sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(CUSTOMER_SCOPE) as CUSTOMERSCOPE  "
			+ "From Meta_Data "
			+ "Where S_Wec_Id = ? "
			+ "And D_Reading_Date between ? and ? ";
	
	public final static String oneWECTotalForBetweenDaysMPRQuery = 
			"Select Max( S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, "
			+ "Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date, "
			+ "Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs, "
			+ "max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS, "
			+ "sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, "
			+ "sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, "
			+ "sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, "
			+ "sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(CUSTOMER_SCOPE) as CUSTOMERSCOPE "
			+ "From Meta_Data_MPR "
			+ "Where S_Wec_Id = ? "
			+ "And D_Reading_Date between ? and ? ";
	/*
Select Max( S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype,
Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date,
Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs,
max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,
sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN,
sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd,
sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT,
sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST
From Meta_Data
Where S_Wec_Id = ''
And D_Reading_Date between '' and '' ;

	 */

}
