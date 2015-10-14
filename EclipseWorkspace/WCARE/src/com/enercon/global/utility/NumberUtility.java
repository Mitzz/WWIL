package com.enercon.global.utility;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtility {

	private static NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getLastDigit(2004, 1));
	}
	
	public static String formatNumber(long num){
		return numberFormat.format(num);
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
	    
	    return bd.doubleValue();
	}
	
	public static String getLastDigit(int a, int n){
		StringBuffer buffer = new StringBuffer(String.valueOf(a));
		
		return new String(buffer.substring(buffer.length() - n, buffer.length()));
	}
}
