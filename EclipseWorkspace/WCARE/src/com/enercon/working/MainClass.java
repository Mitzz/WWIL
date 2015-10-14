package com.enercon.working;

import java.util.HashSet;

public class MainClass {
	
	public static void main(String[] args) {
		
//		testingLengthOfWWCCHOTO();

		int a=500, b = 500;
		Integer x=500, y= 500;
		Integer m=100,n=100;

		System.out.println(a==b);
		System.out.println(x==y);
		System.out.println(m==n); 
//		testingHHMMFormat();
	
		/*System.out.println(new HashSet<String>().size());
//		Client1.doWork();
//		Client2.doWork();
		String desc="LAL,9999,324,wewe,dssdf,1,23-48,1,91014869";
		String[] appid = desc.split(",");
		
		String loc=appid[0].trim();
		String reason=appid[1].trim();
//		String desc=appid[2].trim();
		desc = "";
		for(int i = 2; i < (appid.length - 4); i++){
			if(i == 2){
				desc += appid[i];
			}
			else{
				desc+= "," + appid[i];
			}
			 
		}
		String cnf=appid[appid.length - 4].trim();
		String targetdt=appid[appid.length - 3].trim();
		String crt=appid[appid.length - 2].trim();
		String empid=appid[appid.length - 1].trim();
		
		System.out.println(desc);
		System.out.println("EmpId : " + empid);*/
	}

	private static void testingHHMMFormat() {
		String finalAnswer = "";
		String hhmm = "49:01";
		
		Integer hh = Integer.parseInt(hhmm.split(":")[0]);
		Integer mm = Integer.parseInt(hhmm.split(":")[1]);
		
		Integer totalMinutes = hh * 60 + mm;
		
		Integer days = hh/24;
		Integer remainingHour = hh % 24;
		
		System.out.println("Total Minutes : " + totalMinutes);
		System.out.println("Days : " + days);
		System.out.println("Hours : " + remainingHour);
		System.out.println("Minutes : " + mm);
		
		String day = "day";
		String hour = "hr";
		String minute = "min";
				
		if(totalMinutes >= 1440){
			
			if(days > 0){
				finalAnswer = days + day + " ";
			}
			
			finalAnswer +=  remainingHour + hour + " ";
			finalAnswer += mm + minute + " ";
		}
		else{
			finalAnswer += hh + hour + " ";
			finalAnswer += mm + minute + " "; 
		}
		
		System.out.println(finalAnswer);
	}

	private static void testingLengthOfWWCCHOTO() {
		int reasonLength = 75;
		int i = "Location No:".length() + 5 + "Total WECs:".length() + 4 + "Reason:".length() + reasonLength + "Handover Date and Time:".length() + 20 + "Ticket No:".length() + "H".length() + 
							4 + "**System Generated SMS**".length();
		
		System.out.println(i);//225
		//1 HO MESSAGE -> 6 * 11
		//1 TO MESSAGE -> 150 * 3
	}
	
}
