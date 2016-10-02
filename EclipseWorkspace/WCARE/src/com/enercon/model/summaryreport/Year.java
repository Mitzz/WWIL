package com.enercon.model.summaryreport;

import org.apache.log4j.Logger;

public enum Year {
	
	Y2005(2005),Y2006(2006), Y2007(2007), Y2008(2008), Y2009(2009), Y2010(2010), Y2011(2011), Y2012(2012), Y2013(2013), Y2014(2014), Y2015(2015), Y2016(2016);
	private final static Logger logger = Logger.getLogger(Year.class);
	
	private int value;
	
	Year(int value){
		this.value = value;
	}
	
	public Integer getValue(){
		return this.value;
	}

	public static Year valueOf(int year) {

		switch (year) {
			case 2005 : return Year.Y2005;
			case 2006 : return Year.Y2006;
			case 2007 : return Year.Y2007;
			case 2008 : return Year.Y2008;
			case 2009 : return Year.Y2009;
			case 2010 : return Year.Y2010;	
			case 2011 : return Year.Y2011;
			case 2012 : return Year.Y2012;
			case 2013 : return Year.Y2013;
			case 2014 : return Year.Y2014;	
			case 2015 : return Year.Y2015;
			case 2016 : return Year.Y2016;
			default   : logger.error("Year '" + year + "' is not defined.");throw new IllegalArgumentException("Year '" + year + "' is not defined.");
		}
		
	}
	
	
}
