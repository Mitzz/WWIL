package com.enercon.global.utility;

import org.apache.log4j.Logger;

public class TimeUtility {
	 private final static Logger logger = Logger.getLogger(TimeUtility.class);
	public static void main(String[] args) {
		System.out.println(convertTimeStringToMinutes("4", "."));
	}

	/**
	 * 
	 * @param totalMinutes
	 * @param delimiter
	 * @return
	 */
	public static String convertMinutesToTimeStringFormat(int totalMinutes,String delimiter){
		int [] dd = convertMinutesToHHMMArray(totalMinutes);
		return convertHHMMArrayToTimeString(dd, delimiter);
	}
	
	public static String convertMinutesToTimeStringFormat(long totalMinutes,String delimiter){
		long [] dd = convertMinutesToHHMMArray(totalMinutes);
		return convertHHMMArrayToTimeString(dd, delimiter);
	}
	
	/*TIme in HH:MM or HH.MM format converted to totalMinutes*/
	public static int convertTimeStringToMinutes(String str,String delimiter){
		int totalTimeInMinutes = 0;
		String[] timeStringSplitted = new String[2];
		int timeStringMinuteValue = 0;
		int timeStringHourValue = 0;
		try{
			if (str.contains(delimiter)) {
				if(delimiter.equalsIgnoreCase(".")){
					timeStringSplitted = str.split("\\.");
				}
				else{
					timeStringSplitted = str.split(delimiter);
				}
				if(timeStringSplitted[0].length() == 0){
					//System.out.println("Live Server Error Handling Block");
					timeStringHourValue = 0;
				}
				else{
					timeStringHourValue   = Integer.parseInt(timeStringSplitted[0]);
				}
				
				if(timeStringSplitted.length == 2){
					if(timeStringSplitted[1].length() == 2){
						timeStringMinuteValue = Integer.parseInt(timeStringSplitted[1]);
					}
					else if(timeStringSplitted[1].length() == 1){
						timeStringMinuteValue = Integer.parseInt(timeStringSplitted[1]) * 10;
					}
					else{
						timeStringMinuteValue = 0;
					}
				}
				else{
					timeStringMinuteValue = 0;
				}
				totalTimeInMinutes = timeStringMinuteValue + (timeStringHourValue * 60);
			} else {
				totalTimeInMinutes = Integer.parseInt(str) * 60;
			}
		}catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return totalTimeInMinutes;
	}
	
	/*TIme in HH:MM or HH.MM format converted to totalMinutes*/
	public static long convertTimeStringToMinutesLong(String str,String delimiter){
		long totalTimeInMinutes = 0;
		String[] timeStringSplitted = new String[2];
		long timeStringMinuteValue = 0;
		long timeStringHourValue = 0;
		try{
			if (str.contains(delimiter)) {
				if(delimiter.equalsIgnoreCase(".")){
					timeStringSplitted = str.split("\\.");
				}
				else{
					timeStringSplitted = str.split(delimiter);
				}
				if(timeStringSplitted[0].length() == 0){
					//System.out.println("Live Server Error Handling Block");
					timeStringHourValue = 0;
				}
				else{
					timeStringHourValue   = Long.parseLong(timeStringSplitted[0]);
				}
				
				if(timeStringSplitted.length == 2){
					if(timeStringSplitted[1].length() == 2){
						timeStringMinuteValue = Long.parseLong(timeStringSplitted[1]);
					}
					else if(timeStringSplitted[1].length() == 1){
						timeStringMinuteValue = Long.parseLong(timeStringSplitted[1]) * 10;
					}
					else{
						timeStringMinuteValue = 0;
					}
				}
				else{
					timeStringMinuteValue = 0;
				}
				totalTimeInMinutes = timeStringMinuteValue + (timeStringHourValue * 60);
			} else {
				totalTimeInMinutes = Long.parseLong(str) * 60;
			}
		}catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return totalTimeInMinutes;
	}
	
	/**
	 *  1st Element has Hour Value
	 *  0th Element has Minute Value
	 */
	public static String convertHHMMArrayToTimeString(int[] s, String delimiter){
		StringBuffer minuteHour = new StringBuffer();
		if(s[1] <= 9){
			minuteHour.append("0" + s[1] + delimiter);
		}
		else{
			minuteHour.append(s[1] + "" + delimiter);
		}
		
		if(s[0] <= 9 ){
			minuteHour.append("0" + s[0]);
		}
		else{
			minuteHour.append(s[0]);
		}
		//System.out.println(hourMinute);
		
		return new String(minuteHour);
	}
	
	/**
	 *  1st Element has Hour Value
	 *  0th Element has Minute Value
	 */
	public static String convertHHMMArrayToTimeString(long[] s, String delimiter){
		StringBuffer minuteHour = new StringBuffer();
		if(s[1] <= 9){
			minuteHour.append("0" + s[1] + delimiter);
			//minuteHour.append("0" + NumberUtility.formatNumber(s[1]) + delimiter);
		}
		else{
			minuteHour.append(s[1] + "" + delimiter);
			//minuteHour.append(NumberUtility.formatNumber(s[1]) + "" + delimiter);
		}
		
		if(s[0] <= 9 ){
			minuteHour.append("0" + s[0]);
		}
		else{
			minuteHour.append(s[0]);
		}
		//System.out.println(hourMinute);
		
		return new String(minuteHour);
	}
	
	public static int[] convertTimeStringToHHMMArray(String timeString, String delimiter){
		int totalMinutes = convertTimeStringToMinutes(timeString, delimiter);
		return convertMinutesToHHMMArray(totalMinutes);
	}
	
	public static long[] convertTimeStringToHHMMArrayLong(String timeString, String delimiter){
		long totalMinutes = convertTimeStringToMinutesLong(timeString, delimiter);
		return convertMinutesToHHMMArray(totalMinutes);
	}
	
	public static int[] convertMinutesToHHMMArray(int minutes) {
		return new int[]{minutes % 60,minutes / 60};
	}
	
	public static long[] convertMinutesToHHMMArray(long minutes) {
		return new long[]{minutes % 60,minutes / 60};
	}
	
	public static int convertHHMMArrayToMinutes(int[] HHMMArray){
		return (HHMMArray[0] + HHMMArray[1] * 60);
	}
	
	public static long convertHHMMArrayToMinutes(long[] HHMMArray){
		return (HHMMArray[0] + HHMMArray[1] * 60);
	}
	
	/**
	 * Returns an Integer Array with time Difference in terms of hour,minute and second 
	 * Valid only for difference in time for the same day  
	 * @param cTime:Oth element has second,1st is minute and 2nd is hour value 
	 * @param nTime:Oth element has second,1st is minute and 2nd is hour value
	 * @return Time Difference in terms of hour,minute and second
	 */
	public static int[] getTimeDifferenceInSeconds(int[] cTime, int[] nTime){
		int cSec = cTime[0];
		int cMin = cTime[1];
		int cHour = cTime[2];
		int nSec = nTime[0];
		int nMin = nTime[1];
		int nHour = nTime[2];
		
		int[] dTime = new int[3];
		
		double timeDifferenceInSecond = ((nHour - cHour)*60*60 + (nMin - cMin) * 60 + (nSec - cSec));
	
		int q = (int) (timeDifferenceInSecond / 60);
		int r = (int) (timeDifferenceInSecond % 60);
		dTime[0] = r;
		if(q < 60){
			dTime[1] = q;
			dTime[2] = 0;
			return dTime;
		}
		
		r = q % 60;
		q = q / 60;
		dTime[1] = r;
		dTime[2] = q;
	
		return dTime;
	}
}
