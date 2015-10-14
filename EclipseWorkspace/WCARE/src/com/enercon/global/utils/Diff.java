package com.enercon.global.utils;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.security.*;

public class Diff
{
    public static int printDiff(String sdate1, String sdate2, String fmt, TimeZone tz)
    {
        SimpleDateFormat df = new SimpleDateFormat(fmt);

        Date date1  = null;
        Date date2  = null;
        
        try 
        {
            date1 = df.parse(sdate1); 
            date2 = df.parse(sdate2); 
        }
        catch (ParseException pe)
        {
            pe.printStackTrace();
        }

        Calendar cal1 = null; 
        Calendar cal2 = null;
        
        if (tz == null)
        {
          cal1=Calendar.getInstance(); 
          cal2=Calendar.getInstance(); 
        }
        else
        {
          cal1=Calendar.getInstance(tz); 
          cal2=Calendar.getInstance(tz); 
        }
          
        
        // different date might have different offset
        cal1.setTime(date1);          
        long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
        
        cal2.setTime(date2);
        long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
        
        // Use integer calculation, truncate the decimals
        int hr1   = (int)(ldate1/3600000); //60*60*1000
        int hr2   = (int)(ldate2/3600000);

        int days1 = hr1/24;
        int days2 = hr2/24;

        
        int dateDiff  = days2 - days1;
        int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK))<0 ? 1 : 0;
        int weekDiff  = dateDiff/7 + weekOffset; 
        int yearDiff  = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR); 
        int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);

        
        return dateDiff;
    }
    
    public boolean isNumber(String s)
    {
      String validChars = "0123456789.";
      boolean isNumber = true;
   
      for (int i = 0; i < s.length() && isNumber; i++) 
      { 
        char c = s.charAt(i); 
        if (validChars.indexOf(c) == -1) 
        {
          isNumber = false;
        }
        else
        {
          isNumber = true;
        }
      }
      return isNumber;
    }
    public String encrpt(String passwd){
    	String newpwd="";
    	try {
            MessageDigest sha = MessageDigest.getInstance("MD5");
            byte[] tmp = passwd.getBytes();
            sha.update(tmp);
            newpwd = new String(sha.digest());
            
         }
         catch (java.security.NoSuchAlgorithmException e) {
          //  System.out.println("Rats, MD5 doesn't exist");
            System.out.println(e.toString());
         }
    	return newpwd;
    }
    public String encrpt2(String passwd){
    	String newpwd="";
    	try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            String clearPassword = passwd;
            md.update(clearPassword.getBytes());
            byte[] digestedPassword = md.digest();
            newpwd = digestedPassword.toString();
          }
          catch (java.security.NoSuchAlgorithmException e) {
           //  System.out.println("Rats, MD5 doesn't exist");
             System.out.println(e.toString());
             return null;
          }
    	return newpwd;
    }
    
    public static float Round(float Rval, int Rpl) {
  	  float p = (float)Math.pow(10,Rpl);
  	  Rval = Rval * p;
  	  float tmp = Math.round(Rval);
  	  return tmp/p;
  	    }

}