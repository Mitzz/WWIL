package com.enercon.global.utility;

public class StringUtility {

	public static String replaceAt(String str, int index, char replace){     
	    if(str==null){
	        return str;
	    }else if(index<0 || index>=str.length()){
	        return str;
	    }
	    char[] chars = str.toCharArray();
	    chars[index] = replace;
	    return String.valueOf(chars);       
	}
	
	public static String includeAt(String str, int index, String replace){
		return str.substring(0, index) + replace + str.substring(index + 1);
	}
	
	public static int ordinalIndexOf(String str, char c, int n) {
	    int pos = str.indexOf(c, 0);
	    while (n-- > 0 && pos != -1)
	        pos = str.indexOf(c, pos+1);
	    return pos;
	}
	
	public static int countCharacter(String s, CharSequence c){
		return s.length() - s.replace(c, "").length();
	}
}
