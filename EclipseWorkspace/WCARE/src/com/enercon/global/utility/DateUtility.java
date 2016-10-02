package com.enercon.global.utility;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.YearMonth;

import com.enercon.Time24HoursValidator;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Year;

public class DateUtility {
	private final static Logger logger = Logger.getLogger(DateUtility.class);
	
	private static final String oracleDateFormat = "dd-MMM-yyyy";
	
	public static void main(String[] args) throws Exception {
		
		String fromDate = "29-APR-2015";
		String toDate = "08-MAR-2016";
		
		DateTime from = getJoda(fromDate);
		logger.debug(from.getMonthOfYear());
		logger.debug(from.getYear());
		logger.debug(from.getDayOfMonth() - from.dayOfMonth().withMaximumValue().getDayOfMonth());
		logger.debug(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy H:m a"));
//		int daysDifferenceInclusive = DateUtility.getDaysDifferenceInclusive(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy"), toDate);
//				
//		logger.debug(daysDifferenceInclusive);
		
//		int fromDateMonth = Integer.parseInt(DateUtility.convertDateFormats(fromDate, "dd-MMM-yyyy", "MM"));
//		int fromDateYear = Integer.parseInt(DateUtility.convertDateFormats(fromDate, "dd-MMM-yyyy", "yyyy"));
//		
//		int toDateMonth = Integer.parseInt(DateUtility.convertDateFormats(toDate, "dd-MMM-yyyy", "MM"));
//		int toDateYear = Integer.parseInt(DateUtility.convertDateFormats(toDate, "dd-MMM-yyyy", "yyyy"));
//		Set<YearMonth> wecDatesNotAvailable = DateUtility.getYearMonth(fromDate, toDate);
//		
//		for(YearMonth yearMonth: wecDatesNotAvailable){
//			logger.debug(String.format("%s", yearMonth));
//			int year = yearMonth.getYear();
//			int month = yearMonth.getMonthOfYear();
//			int parseInt = -1;
//			if(year == fromDateYear && month == fromDateMonth){
//				parseInt = getDaysDifferenceInclusive(fromDate, DateUtility.getLastDateOfMonth(fromDate, "dd-MMM-yyyy"));
//			} else if (year == toDateYear && month == toDateMonth){
//				parseInt = getDaysDifferenceInclusive(DateUtility.getFirstDateOfMonth(toDate, "dd-MMM-yyyy"), toDate);
//			} else {
//				parseInt = Integer.parseInt(DateUtility.convertDateFormats(DateUtility.getLastDateOfTheMonthBasedOnMonthYear(yearMonth.getMonthOfYear(), yearMonth.getYear(), "dd-MMM-yyyy"), "dd-MMM-yyyy", "dd"));
//			}
//			
//			logger.debug("No of Days: " + parseInt);
//		}
		
		
//		logger.debug(DateUtility.convertDateFormats(DateUtility.getFirstDateOfMonth(fromDate), "dd-MMM-yyyy", "dd/MM/yyyy"));
//
//		logger.debug(getDateRange(fromDate, toDate));
//		String previousFrom = DateUtility.getBackwardDateInStringWRTDays(fromDate, "dd-MMM-yyyy", -1);
//		logger.debug(previousFrom);
		
//		Set<org.joda.time.YearMonth> yearMonth = new HashSet<org.joda.time.YearMonth>();
//
//		org.joda.time.YearMonth obj1 = new org.joda.time.YearMonth(2016, 2);
//		org.joda.time.YearMonth obj2 = new org.joda.time.YearMonth(1, 12);
//		org.joda.time.YearMonth obj3 = new org.joda.time.YearMonth(2016, 2);
//		logger.debug(obj1.monthOfYear().getAsText());
//		logger.debug(obj1.monthOfYear().getAsShortText());
//		logger.debug(obj1);
//		yearMonth.add(obj1);
//		yearMonth.add(obj2);
//		yearMonth.add(obj3);
//		
//		logger.debug(yearMonth.size());
		
		
//		for(; !fromDate.equalsIgnoreCase(toDate); toDate = getPreviousDateFromGivenDateInString(toDate)){
//			System.out.println(fromDate + ":" + toDate);
//		}
		
		
		
//		System.out.println(getPeriodFiscalYearWise(fromDate, toDate, oracleDateFormat, getFYDiff(fromDate, toDate)));
		
//		fromDate = "22/01/2016";
		
//		System.out.println(DateUtility.compareGivenDateWithTodayInTermsOfDays(fromDate));
//		System.out.println(getBackwardDateInStringWRTDays("22/01/2016", "dd/MM/yyyy", 1));
		
//		System.out.println(getFirstDateOfMonth(Month.FEB, Year.Y2014));
//		System.out.println(getLastDateOfMonth(Month.FEB, Year.Y2014));
		;
//		System.out.println(getMonthDifferenceBetweenTwoDates("01-DEC-2014", "01-DEC-2015"));
//		System.out.println(getJoda("03-DEC-2014").compareTo(getJoda("02-DEC-2014")));
//		final Map<Year, ArrayList<Month>> period = getPeriod("31-DEC-2014", "01-APR-2015");
//		for (final Year fy : period.keySet()) {
////			ArrayList<Month> months = ;
//			System.out.println(fy);
////			System.out.println();
//			for (final Month month : period.get(fy)) {
//				System.out.println(fy + ":" + month.getValue());
//				
//			}
//		}
		
//		System.out.println(getPeriod("01-APR-2014", "01-APR-2015"));
		
		/*System.out.println(compareDate("01-JUN-2014", "dd-MMM-yyyy"));
		System.out.println(compareDate("01-JAN-2006", "dd-MMM-yyyy"));
		System.out.println(compareDate("31-DEC-2004", "dd-MMM-yyyy"));*/
		
		/*try {
			System.out.println(getPreviousYearDateInString("25-JUL-2014", "dd-MMM-yyyy"));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		/*
		try {
			String fromDate = "31/03/2013";
			String toDate = "21/04/2014";
			String previousDayDateInSQLStringFormat = "25-APR-2014";
			String dateFormat = "dd/MM/yyyy";
			String firstDateOfTheMonth = DateUtility.getBackwardDateInStringWRTDays(previousDayDateInSQLStringFormat, "dd-MMM-yyyy", -(DateUtility.getCurrentDateDay(previousDayDateInSQLStringFormat, "dd-MMM-yyyy")) + 1);
			System.out.println("first" + firstDateOfTheMonth);
			System.out.println(getMonthFirstDateByOffsetFromGivenDate(toDate, dateFormat, -1));
			System.out.println(getMonthFirstDateByOffsetFromGivenDate(toDate, dateFormat, 0));
			System.out.println(getMonthFirstDateByOffsetFromGivenDate(toDate, dateFormat, 1));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	*/
		/*try{
			String month = "1";
			String year = "2014";
			
			System.out.println(getFirstDateOfTheMonthBasedOnMonthYear(month, year, "dd-MMM-yyyy"));
			System.out.println(getLastDateOfTheMonthBasedOnMonthYear(month, year, "dd-MMM-yyyy"));
			
			
			System.out.println("Current Date : " + getTodaysDateInGivenFormat("yyyy"));
			System.out.println("Current Date : " + getTodaysDateInGivenFormat("MM"));
			System.out.println("Current Date : " + getTodaysDateInGivenFormat("dd"));
			
			System.out.println(getBackwardDateInStringWRTDays(getTodaysDateInGivenFormat("dd-MMM-yyyy"), "dd-MMM-yyyy", 1));
			System.out.println(getBackwardDateInStringWRTDays(getTodaysDateInGivenFormat("dd-MMM-yyyy"), "dd-MMM-yyyy", -4));
			
		}
		catch(Exception e){
			e.printStackTrace();
		}*/
		
//		try{
//			String month = "1";
//			String year = "2014";
//			
//			/*System.out.println(getFirstDateOfTheMonthBasedOnMonthYear(month, year, "dd-MMM-yyyy"));
//			System.out.println(getLastDateOfTheMonthBasedOnMonthYear(month, year, "dd-MMM-yyyy"));*/
//			
//			
//			System.out.println("Current Date : " + getTodaysDateInGivenFormat("yyyy"));
//			System.out.println("Current Date : " + getTodaysDateInGivenFormat("MM"));
//			System.out.println("Current Date : " + getTodaysDateInGivenFormat("dd"));
//			
//			System.out.println(getBackwardDateInStringWRTDays(getTodaysDateInGivenFormat("dd-MMM-yyyy"), "dd-MMM-yyyy", 1));
//			System.out.println(getBackwardDateInStringWRTDays(getTodaysDateInGivenFormat("dd-MMM-yyyy"), "dd-MMM-yyyy", -4));
//			
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
	}

	public static List<String> getDateRange(String fromDate, String toDate) {
		List<String> dates = new ArrayList<String>();
		
		while(!fromDate.equalsIgnoreCase(toDate)){
			dates.add(fromDate.toUpperCase());
			fromDate = getOffsetDateInStringWRTDays(fromDate, "dd-MMM-yyyy", 1);
		}
		dates.add(toDate.toUpperCase());
		return dates;
		
	}

	public static String getDateRange(Integer startOffset,Integer endOffset) throws Exception {
		String format = "[yyyy,MM,dd]";
		String today = getTodaysDateInGivenFormat(format);
		return "[" + getOffsetDateInStringWRTDays(today,format, startOffset) + "," + getOffsetDateInStringWRTDays(today,format, endOffset) + "]";
	}
	
	public static int getDaysDifferenceInclusive(String fromDate, String toDate) throws ParseException{
		Days days = Days.daysBetween(getJoda(fromDate, oracleDateFormat), getJoda(toDate, oracleDateFormat));
		return days.getDays() + 1;
	}
	
	public static String getOffsetDateInStringWRTDays(String dateString,
			String format, int day) {
		String result = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);

			java.util.Date myDate = dateFormat.parse(dateString);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate);
			calendar.add(Calendar.DAY_OF_YEAR, day);

			java.util.Date previousDate = calendar.getTime();
			result = dateFormat.format(previousDate);

			return result;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return result;
	}
	
	public static String getFirstDateOfTheMonthBasedOnMonthYear(String monthInString, String yearInString, String format) throws ParseException{
		int month = Integer.parseInt(monthInString,10);
		int year = Integer.parseInt(yearInString);
		String midDate = "01-" + month + "-" + year;
		return getFirstDateOfMonth(convertDateFormats(midDate, "dd-MM-yyyy", format) , format);
	}
	
	public static String getLastDateOfTheMonthBasedOnMonthYear(String monthInString, String yearInString, String format) throws ParseException{
		int month = Integer.parseInt(monthInString,10);
		int year = Integer.parseInt(yearInString);
		String midDate = "01-" + month + "-" + year;
		return getLastDateOfMonth(convertDateFormats(midDate, "dd-MM-yyyy", format) , format);
	}
	
	public static String getFirstDateOfTheMonthBasedOnMonthYear(int month, int year, String format) throws ParseException{
		String midDate = "01-" + month + "-" + year;
		return getFirstDateOfMonth(convertDateFormats(midDate, "dd-MM-yyyy", format) , format);
	}
	
	public static String getLastDateOfTheMonthBasedOnMonthYear(int month, int year, String format) throws ParseException{
		String midDate = "01-" + month + "-" + year;
		return getLastDateOfMonth(convertDateFormats(midDate, "dd-MM-yyyy", format) , format);
	}
	
	public static Map<String, String> getPeriodFiscalYearWise(String fromDate, String toDate) throws ParseException{
		return getPeriodFiscalYearWise(fromDate, toDate, oracleDateFormat, getFYDiff(fromDate, toDate));
	}
	
	public static Map<String, String> getPeriodFiscalYearWise(String fromDate, String toDate, String dateFormat, int difference) throws ParseException{
		if(difference < 0){
			throw new IllegalArgumentException("Date range not proper");
		}
//		System.out.println("Difference : " + difference);
		LinkedHashMap<String, String> periods = new LinkedHashMap<String, String>();
		if(difference == 0){
			periods.put(fromDate, toDate);
		}
		else if (difference == 1){
			periods.put(fromDate, getLastDateOfFiscalYear(getFiscalYear(fromDate, dateFormat), dateFormat));
			periods.put(getFirstDateOfFiscalYear(getFiscalYear(toDate, dateFormat), dateFormat), toDate);
		}
		else{
			periods.put(fromDate, getLastDateOfFiscalYear(getFiscalYear(fromDate, dateFormat), dateFormat));
			for (int i = getFiscalYear(fromDate, dateFormat) + 1; i < getFiscalYear(toDate, dateFormat); i++) {
				periods.put(getFirstDateOfFiscalYear(i, dateFormat), getLastDateOfFiscalYear(i, dateFormat));
			} 

			periods.put(getFirstDateOfFiscalYear(getFiscalYear(toDate, dateFormat), dateFormat), toDate);
		}
		//System.out.println(periods);
		return periods;
	}
	
	public static Map<String, String> getPeriodYearWise(String fromDate, String toDate, String dateFormat) throws ParseException{
		return getPeriodYearWise(fromDate, toDate, dateFormat, DateUtility.getYearDifference(fromDate, toDate));
	}
	
	public static Map<String, String> getYearWisePeriod(String fromDate, String toDate) throws ParseException{
		return getPeriodYearWise(fromDate, toDate, oracleDateFormat, DateUtility.getYearDifference(fromDate, toDate));
	}
	
	public static Map<String, String> getPeriodYearWise(String fromDate, String toDate, String dateFormat, int difference) throws ParseException{
		if(difference < 0){
			return null;
		}
//		System.out.println("Difference : " + difference);
		LinkedHashMap<String, String> periods = new LinkedHashMap<String, String>();
		if(difference == 0){
			periods.put(fromDate, toDate);
		}
		else if (difference == 1){
			periods.put(fromDate, getLastDateOfYear(getYear(fromDate, dateFormat), dateFormat));
			periods.put(getFirstDateOfYear(getYear(toDate, dateFormat), dateFormat), toDate);
		}
		else{
			periods.put(fromDate, getLastDateOfYear(getYear(fromDate, dateFormat), dateFormat));
			for (int i = getYear(fromDate, dateFormat) + 1; i < getYear(toDate, dateFormat); i++) {
				periods.put(getFirstDateOfYear(i, dateFormat), getLastDateOfYear(i, dateFormat));
			} 

			periods.put(getFirstDateOfYear(getYear(toDate, dateFormat), dateFormat), toDate);
		}
		//System.out.println(periods);
		return periods;
	}

	public static int getFiscalYear(String dateInString, String dateFormat) throws ParseException {

		DateTime jodaDate = getJoda(dateInString, dateFormat);
		int year = jodaDate.getYear();
		int month = jodaDate.getMonthOfYear();
		
		//System.out.println("Month:" + month);
		//System.out.println("Year:" + year);
		if(month >= 4){
			return year;
		}
		else{
			return (year - 1);
		}
	}
	
	public static int getFiscalYear(String dateInString) throws ParseException {
		return getFiscalYear(dateInString, oracleDateFormat);
		
	}
	
	public static int getYear(String dateInString, String dateFormat) throws ParseException {

		DateTime jodaDate = getJoda(dateInString, dateFormat);
		int year = jodaDate.getYear();
//		int month = jodaDate.getMonthOfYear();
		
		//System.out.println("Month:" + month);
		//System.out.println("Year:" + year);
		return year;
		
	}
	
	public static int getYear(String dateInString) throws ParseException {
		return getYear(dateInString, oracleDateFormat);
	}

	public static java.sql.Date getTodaysDateInSQL(){
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}

	public static int getYesterdayDateDay(){
		int day = Integer.parseInt(getYesterdayDateInGivenStringFormat("dd"));
		////System.out.println("Day:" + day);
		//return Integer.parseInt(yesterdayDateInyyyyMMddFormat.substring(6, yesterdayDateInyyyyMMddFormat.length()));
		return day;
	}

	public static int getCurrentDateDay(String date, String format){
		int day = Integer.parseInt(convertDateFormats(date, format, "dd"));

		////System.out.println("Day:" + day);
		//return Integer.parseInt(yesterdayDateInyyyyMMddFormat.substring(6, yesterdayDateInyyyyMMddFormat.length()));
		return day;
	}

	public static String getTodaysDateInGivenFormat(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

	/**
	 * @param format
	 * @return Date In Given String Format
	 */
	public static String getYesterdayDateInGivenStringFormat(String format) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			return dateFormat.format(cal.getTime());
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return null;
	}

	public static java.sql.Date getYesterdayDateInSQL(){
		//DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

		//java.sql.Date TodayDate = new java.sql.Date();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);// 1 is used for tomorrow date, and -1 is used for yesterday date.

		java.util.Date yesDate = (java.util.Date) calendar.getTime();
		return new java.sql.Date(yesDate.getTime());

		//int DifferenceDate = (int) ((TodayDate.getTime() - yesDate.getTime()) / (1000 * 60 * 60 * 24));

		//System.out.println("Difference is : " + DifferenceDate);
	}

	/**
	 * @param givenDate
	 *            Date With a Given Date Format
	 * @param format
	 * @return Previous Date In Given String Format
	 */
	public static String getPreviousDateFromGivenDateInString(String givenDate,
			String format) {
		String result = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			java.util.Date myDate = dateFormat.parse(givenDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			java.util.Date previousDate = calendar.getTime();
			result = dateFormat.format(previousDate);
			return result;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return result;

	}
	
	public static String getPreviousDateFromGivenDateInString(String givenDate) {
		return getPreviousDateFromGivenDateInString(givenDate, oracleDateFormat);

	}

	public static String getBackwardDateInStringWRTDays(String dateString,
			String format, int day) {
		String result = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);

			java.util.Date myDate = dateFormat.parse(dateString);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate);
			calendar.add(Calendar.DAY_OF_YEAR, day);

			java.util.Date previousDate = calendar.getTime();
			result = dateFormat.format(previousDate);

			return result;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return result;
	}

	public static String getBackwardDateInStringWRTMonths(String dateString,
			String format, int months) {
		// Create a date formatter using your format string
		String result = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);

			// Parse the given date string into a Date object.
			// Note: This can throw a ParseException.
			java.util.Date myDate = dateFormat.parse(dateString);

			// Use the Calendar class to subtract one day
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate);
			calendar.add(Calendar.MONTH, months);

			// Use the date formatter to produce a formatted date string
			java.util.Date previousDate = calendar.getTime();
			result = dateFormat.format(previousDate);

			return result;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return result;
	}
	
	public static String getBackwardDateInStringWRTMonths(String dateString, int months) {
		return getBackwardDateInStringWRTMonths(dateString, oracleDateFormat, months);
	}

	public static String convertDateFormats(String date, String oldFormat,
			String newFormat) {

		String strDate = date;

		try {
			SimpleDateFormat sdfSource = new SimpleDateFormat(oldFormat);

			java.util.Date utilDate = sdfSource.parse(strDate);

			// create SimpleDateFormat object with desired date format
			SimpleDateFormat sdfDestination = new SimpleDateFormat(newFormat);

			// parse the date into another format
			strDate = sdfDestination.format(utilDate);

			return strDate;

		} catch (ParseException pe) {
			logger.error("\nClass: " + pe.getClass() + "\nMessage: " + pe.getMessage() + "\n", pe);
		}
		return null;
	}

	public static java.util.Date stringDateFormatToUtilDate(
			String dateInStringFormat, String format) {
		String testDate = "";
		DateFormat formatter = new SimpleDateFormat(format);
		java.util.Date date = null;
		try {
			testDate = dateInStringFormat;
			formatter = new SimpleDateFormat(format);
			date = formatter.parse(testDate);
			return date;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return null;
	}

	public static java.sql.Date stringDateFormatToSQLDate(
			String dateInStringFormat, String format) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(format);
		java.util.Date javaDate = null;
		java.sql.Date sqlDate = null;
		try {
			javaDate = sdf1.parse(dateInStringFormat);
			sqlDate = new java.sql.Date(javaDate.getTime());
			return sqlDate;
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return sqlDate;
	}

	public static String utilDateInStringFormat(java.util.Date utilDate,
			String format) {
		DateFormat formatter = null;
		String reportDate = "";
		try {
			formatter = new SimpleDateFormat(format);
			reportDate = formatter.format(utilDate);
			return reportDate;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return null;
	}

	public static java.sql.Date utilDateToSQLDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}

	public static String sqlDateToStringDate(java.sql.Date sqlDate,
			String format) {
		DateFormat formatter = null;
		String reportDate = "";
		try {

			formatter = new SimpleDateFormat(format);
			reportDate = formatter.format(sqlDate);
			return reportDate;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return null;
	}

	public static java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) {
		return new java.util.Date(sqlDate.getTime());
	}

	public static String getMonthForInt(int monthNumber) {
		int actualMonth = monthNumber - 1; 
		String month = "wrong";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (actualMonth >= 0 && actualMonth <= 11) {
			month = months[actualMonth];
		}
		return month;
	}

	public static int gettingMonthNumberFromStringDate(String dateString, String format){
		java.util.Date date;
		Calendar cal = Calendar.getInstance();
		try {
			date = new SimpleDateFormat(format, Locale.ENGLISH).parse(dateString);
			cal.setTime(date);
			return cal.get(Calendar.MONTH) + 1;
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return cal.get(Calendar.MONTH);
	}
	
	public static int gettingMonthNumberFromStringDate(String dateString){
		return gettingMonthNumberFromStringDate(dateString, oracleDateFormat);
	}

	public static String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}

	public static String gettingMonthNameFromStringDate(String dateString, String format){
		java.util.Date date;
		Calendar cal = Calendar.getInstance();
		try {
			date = new SimpleDateFormat(format, Locale.ENGLISH).parse(dateString);
			cal.setTime(date);
			return getMonth(cal.get(Calendar.MONTH) + 1);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return getMonth(cal.get(Calendar.MONTH) + 1);
	}

	public static int gettingYearFromStringDate(String dateString, String format){
		java.util.Date date;
		Calendar cal = Calendar.getInstance();
		try {
			date = new SimpleDateFormat(format, Locale.ENGLISH).parse(dateString);
			cal.setTime(date);
			//System.out.println("-----MOnth:" + cal.get(Calendar.YEAR));
			return cal.get(Calendar.YEAR);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return cal.get(Calendar.YEAR);
	}
	
	public static int gettingYearFromStringDate(String dateString){
		
		return gettingYearFromStringDate(dateString, oracleDateFormat);
	}

	public static void dateValidatorIndd_MM_yyyy(String dateString) throws ParseException{
		if (dateString == null || !dateString.matches("\\d{2}.\\d{2}.\\d{4}")){
			throw new ParseException("Date Not In Required Format", 2);
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		dateFormat.setLenient(false);
		dateFormat.parse(dateString);
	}
	
	public static String getPreviousYearDateInString(String dateString, String format) 
	         throws ParseException {
	     // Create a date formatter using your format string
	     DateFormat dateFormat = new SimpleDateFormat(format);

	     // Parse the given date string into a Date object.
	     // Note: This can throw a ParseException.
	     Date myDate = dateFormat.parse(dateString);

	     // Use the Calendar class to subtract one year
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTime(myDate);
	     calendar.add(Calendar.YEAR, -1);
	     
	     // Use the date formatter to produce a formatted date string
	     Date previousDate = calendar.getTime();
	     String result = dateFormat.format(previousDate);

	     return result;
	}

	public static int compareGivenDateWithTodayInTermsOfDays(String date, String dateFormat){
		DateTime today = new DateTime(new Date());
		DateTime givenDate =  new DateTime(stringDateFormatToUtilDate(date, dateFormat));
		return Days.daysBetween(today, givenDate).getDays();
	}
	
	public static int compareGivenDateWithTodayInTermsOfDays(String date){
		return compareGivenDateWithTodayInTermsOfDays(date, oracleDateFormat);
	}
	
	public static String getLastDateOfYear(int year, String format) throws ParseException{
		return getJoda("31-DEC-" + year, oracleDateFormat).toString(format);
	}
	
	public static String getFirstDateOfYear(int year, String format) throws ParseException{
		return getJoda("01-JAN-" + year, oracleDateFormat).toString(format);
	}
	
	public static String getFirstDateOfNextYear(String date) throws ParseException{
		
		return getJoda("01-JAN-" + getYear(date, oracleDateFormat) + 1, oracleDateFormat).toString(oracleDateFormat);
	}
	
	public static String getLastDateOfFiscalYear(int fiscalYear, String format) throws ParseException{
		return getJoda("31-MAR-" + (fiscalYear + 1), oracleDateFormat).toString(format);
	}
	
	public static String getLastDateOfFiscalYear(int fiscalYear) throws ParseException{
		return getJoda("31-MAR-" + (fiscalYear + 1), oracleDateFormat).toString(oracleDateFormat);
	}
	
	public static String getFirstDateOfFiscalYear(int fiscalYear, String format) throws ParseException{
		return getJoda("01-APR-" + fiscalYear, oracleDateFormat).toString(format);
	}
	
	public static String getFirstDateOfFiscalYear(int fiscalYear) throws ParseException{
		return getJoda("01-APR-" + fiscalYear, oracleDateFormat).toString(oracleDateFormat);
	}
	
	public static Map<String, String> getPeriodMonthWise(String fromDate, String toDate, String dateFormat, int difference) throws ParseException{
		if(difference < 0){
			return null;
		}
		Map<String, String> periods = new LinkedHashMap<String, String>();
		if(difference == 0){
			periods.put(fromDate, toDate);
		}
		else if (difference == 1){
			periods.put(fromDate, getMonthLastDateByOffsetFromGivenDate(fromDate, dateFormat, 0));
			periods.put(getMonthFirstDateByOffsetFromGivenDate(toDate, dateFormat, 0), toDate);
		}
		else{
			periods.put(fromDate, getMonthLastDateByOffsetFromGivenDate(fromDate, dateFormat, 0));
			for (int i = 1; i < difference; i++) {
				periods.put(getMonthFirstDateByOffsetFromGivenDate(fromDate, dateFormat, i), getMonthLastDateByOffsetFromGivenDate(fromDate, dateFormat, i));
			} 

			periods.put(getMonthFirstDateByOffsetFromGivenDate(toDate, dateFormat, 0), toDate);
		}
		//System.out.println(periods);
		return periods;
	}
	
	
	public static boolean compareDate(String date, String format){
		java.util.Date myDate = stringDateFormatToUtilDate(date, format);
		java.util.Date today = stringDateFormatToUtilDate("01-JAN-2006", oracleDateFormat);

//		System.out.println(myDate.toString());
		System.out.println(today.toString());
		if(today.compareTo(myDate)<0){
		    System.out.println("Post");
		    return true;
		}
		else if(today.compareTo(myDate)>0){
			System.out.println("Pre");
			return true;
		}
		else{
			System.out.println("Equal");
			return true;
		}
	}	

	public static String getMonthFirstDateByOffsetFromGivenDate(String date, String dateFormat, int incrementedMonth) throws ParseException{
		return getJoda(date,dateFormat).plusMonths(incrementedMonth).dayOfMonth().withMinimumValue().toString(dateFormat);
	}
	
	public static String getMonthFirstDateByOffsetFromGivenDate(String date, int incrementedMonth) throws ParseException{
		return getMonthFirstDateByOffsetFromGivenDate(date, oracleDateFormat, incrementedMonth);
	}

	public static String getMonthLastDateByOffsetFromGivenDate(String date, String dateFormat, int incrementedMonth) throws ParseException{
		return getJoda(date,dateFormat).plusMonths(incrementedMonth).dayOfMonth().withMaximumValue().toString(dateFormat);
	}
	
	public static String getMonthLastDateByOffsetFromGivenDate(String date, int incrementedMonth) throws ParseException{
		return getMonthLastDateByOffsetFromGivenDate(date, oracleDateFormat, incrementedMonth);
	}

	public static String getLastDateOfMonth(String date, String format) throws ParseException{
		return getJoda(date,format).dayOfMonth().withMaximumValue().toString(format);
	}
	
	public static String getLastDateOfMonth(String date) throws ParseException{
		return getLastDateOfMonth(date, oracleDateFormat);
	}

	public static String getFromDate(String fromDate) throws ParseException{
		if(getFirstDateOfMonth(fromDate, oracleDateFormat).equalsIgnoreCase(fromDate)){
			return fromDate;
		} else {
			return getFirstDateOfMonth(DateUtility.getBackwardDateInStringWRTMonths(fromDate, oracleDateFormat, 1), oracleDateFormat);
		}
	}
	
	public static String getToDate(String toDate) throws ParseException{
		if(getLastDateOfMonth(toDate, oracleDateFormat).equalsIgnoreCase(toDate)){
			return toDate;
		} else {
			return getLastDateOfMonth(DateUtility.getBackwardDateInStringWRTMonths(toDate, oracleDateFormat, -1), oracleDateFormat);
		}
		
	}
	
	public static String getFirstDateOfMonth(String date, String format) throws ParseException{
		return getJoda(date,format).dayOfMonth().withMinimumValue().toString(format);
	}
	
	public static String getFirstDateOfMonth(String date) throws ParseException{
		return getFirstDateOfMonth(date, oracleDateFormat);
	}
	
	public static DateTime getJoda(String date) throws ParseException{
		return getJoda(date, oracleDateFormat);
	}

	public static DateTime getJoda(String date, String format) throws ParseException{

		return new DateTime(new SimpleDateFormat(format).parse(date));
	}

	public static int getMonthDifferenceBetweenTwoDates(String fromDate, String toDate, String dateFormat) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);

		DateTime fromDateTime = new DateTime(format.parse(fromDate));
		DateTime toDateTime = new DateTime(format.parse(toDate));

		int fromDateMonth = fromDateTime.getMonthOfYear();
		int fromDateYear = fromDateTime.getYear();

		int toDateMonth = toDateTime.getMonthOfYear();
		int toDateYear = toDateTime.getYear();

		int diff = (((toDateYear - fromDateYear) * 12) + (toDateMonth - fromDateMonth));

		return diff;
	}
	
	public static int getMonthDifferenceBetweenTwoDates(String fromDate, String toDate) throws ParseException{
		String dateFormat = oracleDateFormat;
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);

		DateTime fromDateTime = new DateTime(format.parse(fromDate));
		DateTime toDateTime = new DateTime(format.parse(toDate));

		int fromDateMonth = fromDateTime.getMonthOfYear();
		int fromDateYear = fromDateTime.getYear();

		int toDateMonth = toDateTime.getMonthOfYear();
		int toDateYear = toDateTime.getYear();

		int diff = (((toDateYear - fromDateYear) * 12) + (toDateMonth - fromDateMonth));

		return diff;
	}
	
	public static Map<Year, ArrayList<Month>> getPeriod(String fromDate, String toDate) throws ParseException {
		Map<Year, ArrayList<Month>> r = new TreeMap<Year, ArrayList<Month>>();
		ArrayList<Month> mmm = new ArrayList<Month>();
		Map<String, String> mm =  getPeriodYearWise(fromDate, toDate, "dd-MMM-yyyy", getYearDifference(fromDate, toDate));
		
		System.out.println(mm);
		
		for(String key : mm.keySet()){
//			System.out.println(FiscalYear.valueOf(getFiscalYear(key, oracleDateFormat)));
			
			for(Month m : Month.values(gettingMonthNumberFromStringDate(key, oracleDateFormat), gettingMonthNumberFromStringDate(mm.get(key), oracleDateFormat))){
				
				mmm.add(m);
			}
			
			r.put(Year.valueOf(getYear(key, oracleDateFormat)), mmm);
			mmm = new ArrayList<Month>();
			
		}
//		System.out.println(r);
		return r;
		
	}

	
	
	private static int getFYDiff(String fromDate, String toDate) throws ParseException {
		return getFiscalYear(toDate, "dd-MMM-yyyy") - getFiscalYear(fromDate, "dd-MMM-yyyy");
	}
	
	public static int getYearDifference(String fromDate, String toDate) throws ParseException {
		
		return getYear(toDate, oracleDateFormat) - getYear(fromDate, oracleDateFormat);
	}

	private static Map<FiscalYear, ArrayList<Month>> fiscalYear(String date) throws ParseException {
		
//		System.out.println("Fiscal Year and Month of date :: " + getFiscalYear(date, "dd-MMM-yyyy") + "-" + gettingMonthNumberFromStringDate(date, "dd-MMM-yyyy"));
//		System.out.println(FiscalYear.valueOf(getFiscalYear(date, "dd-MMM-yyyy")) + "-" + Month.valueOf(gettingMonthNumberFromStringDate(date, "dd-MMM-yyyy")));
		
//		Month m = Month.valueOf(gettingMonthNumberFromStringDate(date, "dd-MMM-yyyy"));
//		FiscalYear fy = FiscalYear.valueOf(getFiscalYear(date, "dd-MMM-yyyy"));
		
//		System.out.println(fy + "::" + fy.getValue());
//		System.out.println(m + "::"  +  m.getValue());
		
//		for (Month month : Month.values()) {
//			System.out.println(month);
//		}
//		for(FiscalYear fy : FiscalYear.values(2012, 2014)){
//			System.out.println(fy);
//		}
		
		for(Month m : Month.fiscalYearValues(4, 1)){
			System.out.println(m);
		}
		
		return null;
	}

	public static String getFirstDateOfMonth(Month month, Year year) throws ParseException {
		return getFirstDateOfTheMonthBasedOnMonthYear(month.getValue().toString(), year.getValue().toString(), oracleDateFormat);
	}
	
	public static String getLastDateOfMonth(Month month, Year year) throws ParseException {
		return getLastDateOfTheMonthBasedOnMonthYear(month.getValue().toString(), year.getValue().toString(), oracleDateFormat);
	}

	public static boolean isFirstDateOfTheMonth(String fromDate) throws ParseException {
		return getFirstDateOfMonth(fromDate).equalsIgnoreCase(fromDate); 
	}

	public static boolean isLastDateOfTheMonth(String toDate) throws ParseException {
		return getLastDateOfMonth(toDate).equalsIgnoreCase(toDate);
	}
	
	public static boolean isFirstDateOfTheFiscalYear(String fromDate) throws ParseException {
		return getFirstDateOfFiscalYear(getFiscalYear(fromDate, oracleDateFormat)).equalsIgnoreCase(fromDate); 
	}

	public static boolean isLastDateOfTheFiscalYear(String toDate) throws ParseException {
		return getLastDateOfFiscalYear(getFiscalYear(toDate, oracleDateFormat)).equalsIgnoreCase(toDate);
	}

	public static Set<YearMonth> getYearMonth(String fromDate, String toDate) {
		Set<YearMonth> yearMonths = new TreeSet<YearMonth>(); 
		
		int fromMonth = Integer.parseInt(DateUtility.convertDateFormats(fromDate, oracleDateFormat, "MM"));
		int fromYear = Integer.parseInt(DateUtility.convertDateFormats(fromDate, oracleDateFormat, "yyyy"));
		
		int toMonth = Integer.parseInt(DateUtility.convertDateFormats(toDate, oracleDateFormat, "MM"));
		int toYear = Integer.parseInt(DateUtility.convertDateFormats(toDate, oracleDateFormat, "yyyy"));
		
		while(!(fromMonth == toMonth && fromYear == toYear)){
			yearMonths.add(new YearMonth(fromYear, fromMonth));
			
			fromDate = getOffsetDateInStringWRTDays(fromDate, oracleDateFormat, 29);
			fromMonth = Integer.parseInt(DateUtility.convertDateFormats(fromDate, oracleDateFormat, "MM"));
			fromYear = Integer.parseInt(DateUtility.convertDateFormats(fromDate, oracleDateFormat, "yyyy"));
		}
		yearMonths.add(new YearMonth(toYear, toMonth));
		return yearMonths;
	}
	
	public static java.sql.Timestamp getCurrentTimestampInSQL(){
		Calendar c = Calendar.getInstance();
		java.sql.Timestamp currentTime = new Timestamp(c.getTimeInMillis());
		return currentTime;
	}
}

class JodaDateDifferentExample {
	private final static Logger logger = Logger.getLogger(JodaDateDifferentExample.class);   
	public static void main(String[] args) {

		//demo();

		try {
			String fromDate = "14/02/2014";
			String toDate = "01/03/2014";

			String dateFormat = "dd/MM/yyyy";
			getPeriodMonthWise(fromDate, toDate, dateFormat, getMonthDifferenceBetweenTwoDates(fromDate, toDate, dateFormat));
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}

	}

	private static Map<String, String> getPeriodMonthWise(String fromDate, String toDate, String dateFormat, int difference) throws ParseException{
		if(difference < 0){
			return null;
		}
		LinkedHashMap<String, String> periods = new LinkedHashMap<String, String>();
		if(difference == 0){
			periods.put(fromDate, toDate);
		}
		else if (difference == 1){
			periods.put(fromDate, getLastDateOfMonth(fromDate, dateFormat));
			periods.put(getFirstDateOfMonth(toDate, dateFormat), toDate);
		}
		else{
			periods.put(fromDate, getLastDateOfMonth(fromDate, dateFormat));
			for (int i = 1; i < difference; i++) {
				periods.put(getNextMonthFirstDate(fromDate, dateFormat, i), getNextMonthLastDate(fromDate, dateFormat, i));
			} 

			periods.put(getFirstDateOfMonth(toDate, dateFormat), toDate);
		}
		System.out.println(periods);
		return periods;
	}

	private static String getNextMonthFirstDate(String date, String dateFormat, int incrementedMonth) throws ParseException{
		return getJoda(date,dateFormat).plusMonths(incrementedMonth).dayOfMonth().withMinimumValue().toString(dateFormat);
	}

	private static String getNextMonthLastDate(String date, String dateFormat, int incrementedMonth) throws ParseException{
		return getJoda(date,dateFormat).plusMonths(incrementedMonth).dayOfMonth().withMaximumValue().toString(dateFormat);
	}

	private static String getLastDateOfMonth(String date, String format) throws ParseException{
		return getJoda(date,format).dayOfMonth().withMaximumValue().toString(format);
	}

	private static String getFirstDateOfMonth(String date, String format) throws ParseException{
		
		return getJoda(date,format).dayOfMonth().withMinimumValue().toString(format);
	}

	private static DateTime getJoda(String date, String format) throws ParseException{

		return new DateTime(new SimpleDateFormat(format).parse(date));
	}

	private static int getMonthDifferenceBetweenTwoDates(String fromDate, String toDate, String dateFormat) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);

		DateTime fromDateTime = new DateTime(format.parse(fromDate));
		DateTime toDateTime = new DateTime(format.parse(toDate));

		int fromDateMonth = fromDateTime.getMonthOfYear();
		int fromDateYear = fromDateTime.getYear();

		int toDateMonth = toDateTime.getMonthOfYear();
		int toDateYear = toDateTime.getYear();

		int diff = (((toDateYear - fromDateYear) * 12) + (toDateMonth - fromDateMonth));
		/*System.out.println("FD Month :" + fromDateMonth);
		System.out.println("FD Year :" + fromDateYear);
		System.out.println("TD Month :" + toDateMonth);
		System.out.println("TD Year :" + toDateYear);*/
		/*System.out.println("Difference:" + diff);
		System.out.println("Last Date of FD:" + Fd.dayOfMonth().withMaximumValue().toString("dd-MMM-yyyy"));
		System.out.println("First Date of TD:" + Td.dayOfMonth().withMinimumValue().toString("dd-MMM-yyyy"));*/


		return diff;
	}

	private static void demo() {
		String dateStart = "01/14/2012 09:29:58";
		String dateStop = "01/15/2012 10:31:48";

		String hr = Time24HoursValidator.getIn24HrFormat("23.59", ".");

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("HH.mm");

		Date d1 = null;
		Date d2 = null;
		Date d3 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
			d2 = format2.parse(hr);

			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);

			DateTime dt3 = new DateTime(d3);

			System.out.println(dt3.getMinuteOfDay());
			System.out.println(dt3.getMinuteOfDay());

			System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
			System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
			System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
			System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}// TODO Auto-generated method stub

	}
	
	

}