package com.enercon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.xml.internal.ws.message.stream.PayloadStreamReaderMessage;

public class Time24HoursValidator{

	private static Pattern pattern;
	private static Matcher matcher;
	private static Pattern patternForWholeNumber;

	private static final String TIME24HOURS_PATTERN = 
			"([01]?[0-9]|2[0-4]).[0-5][0-9]";
	
	private static final String WHOLE_NUMBER_PATTERN = 
			"^([0-9]{1,2}){1}(\\.[0]{1,2})?$";

	static{
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
		patternForWholeNumber = Pattern.compile(WHOLE_NUMBER_PATTERN);
	}
	
	public static boolean validateWholeNumber(final String number){
		try{
			Double.parseDouble(number);
		}
		catch(Exception e){
			return false;
		}
		matcher = patternForWholeNumber.matcher(number);
		return matcher.matches();
	}

	/**
	 * Validate time in 24 hours format with regular expression
	 * @param time time address for validation
	 * @return true valid time format, false invalid time format
	 */
	public static boolean validate(final String time){
		
		if(Double.parseDouble(time) > 24){
			return false;
		}
		else{
			matcher = pattern.matcher(time);
			return matcher.matches();
		}
	}
	
	public static String getIn24HrFormat(String timeString, String splitSpecifier){
//		System.out.println("Time String : " + timeString);
		String timeIn24HrFormat = "";
		String[] time = null;
		if(splitSpecifier.equals(".")){
			time = timeString.split("\\.");
		}
		else{
			time = timeString.split(splitSpecifier);
		}
		if(time[0].length() == 1){
			time[0] = "0" + time[0];
		}
		
		if(time.length > 1){
			if(time[1].length() == 1){
				time[1] = time[1] + "0";
			}
			timeIn24HrFormat = time[0] + splitSpecifier + time[1];
		}
		else{
			timeIn24HrFormat = time[0] + splitSpecifier + "00"; 
		}
		//System.out.println(timeIn24HrFormat);
//		System.out.println("timeIn24HrFormat : " + timeIn24HrFormat);
		return timeIn24HrFormat;
	}
	
	public static int getMinutesFrom24HrFormat(String hourFormat){
		return Integer.parseInt(hourFormat.split("\\.")[0]) * 60 + Integer.parseInt(hourFormat.split("\\.")[1]);  
	}

	public static void main(String[] args) {
		String timeString = "1";
		System.out.println(validateWholeNumber(timeString));
		//System.out.println(Time24HoursValidator.validate(getIn24HrFormat(timeString, ".")));
	}
}
