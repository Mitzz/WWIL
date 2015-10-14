package com.enercon.model.summaryreport;

public enum Month {
	
	JAN(1), FEB(2), MAR(3), APR(4), MAY(5), JUN(6), JUL(7), AUG(8), SEP(9), OCT(10), NOV(11), DEC(12);
	
	private int value;
	
	Month(int value){
		this.value = value;
	}
	
	public Integer getValue(){
		return this.value;
	}

	public static Month valueOf(Integer month) {
		switch (month) {
			case 1 :return Month.JAN;
			case 2 :return Month.FEB;
			case 3 :return Month.MAR;
			case 4 :return Month.APR;
			case 5 :return Month.MAY;
			case 6 :return Month.JUN;
			case 7 :return Month.JUL;
			case 8 :return Month.AUG;
			case 9 :return Month.SEP;
			case 10:return Month.OCT;
			case 11:return Month.NOV;
			case 12:return Month.DEC;
			default: throw new IllegalArgumentException(month.toString());
		}
	}
	
	public static int fiscalValue(Month month) {
		switch (month) {
			case JAN : return 10 ;
			case FEB : return 11 ;
			case MAR : return 12 ;
			case APR : return 1 ;
			case MAY : return 2 ;
			case JUN : return 3 ;
			case JUL : return 4 ;
			case AUG : return 5 ;
			case SEP : return 6 ;
			case OCT : return 7 ;
			case NOV : return 8 ;
			case DEC : return 9 ;
			default: throw new IllegalArgumentException(month.toString());
		}
	}
	
	public static String getShortHandMonthName(Month month) {
		switch (month.getValue()) {
			case 1 :return "JAN";
			case 2 :return "FEB";
			case 3 :return "MAR";
			case 4 :return "APR";
			case 5 :return "MAY";
			case 6 :return "JUN";
			case 7 :return "JUL";
			case 8 :return "AUG";
			case 9 :return "SEP";
			case 10:return "OCT";
			case 11:return "NOV";
			case 12:return "DEC";
			default: throw new IllegalArgumentException(month.toString());
		}
	}
	
	public static Month[] fiscalYearValues(int lo, int hi){
		int s = (lo + 9) % 12 == 0 ? 12 : (lo + 9) % 12;
		int e = (hi + 9) % 12 == 0 ? 12 : (hi + 9) % 12;
		
		
		if(s > e) throw new IllegalArgumentException("Lo Greater Than Hi");
		Month[] months = new Month[e - s + 1];
		
		
		int index = 0;
		for(int i = s; i <= e; i++){
			months[index++] = valueOf(lo % 12 == 0 ? 12 : lo % 12);
			lo++;
		}
		return months;
	}
	
	public static Month[] values(int lo, int hi){
		int s = lo;
		int e = hi;
		
		if(s > e) throw new IllegalArgumentException("Month.values(int, int) : Month[] - Lo Greater Than Hi");
		if(lo <= 0 || lo >= 13) throw new IllegalArgumentException("Month.values(int, int) : Month[] - lo must be in Range 1 - 12");
		if(hi <= 0 || hi >= 13) throw new IllegalArgumentException("Month.values(int, int) : Month[] - hi must be in Range 1 - 12");
		
		Month[] months = new Month[e - s + 1];
		
		int index = 0;
		for(int i = s; i <= e; i++){
			months[index++] = valueOf(i);
		}
		return months;
	}
	
	public static void main(String[] args) {
//		System.out.println(Month.fiscalValue(Month.MAR));
		for(Month m : Month.fiscalYearValues(4, 3)){
			System.out.println(m);
		}
//		
//		for(Month m : Month.values(1, 12)){
//			System.out.println(m);
//		}
	}

}
