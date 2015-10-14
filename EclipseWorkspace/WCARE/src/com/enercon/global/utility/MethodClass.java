package com.enercon.global.utility;

public class MethodClass {
	private static int inoutCount = 0;
	
	private void increment(){
		inoutCount++;
	}
	
	private void dcrement(){
		inoutCount--;
	}
	
	public void inClassMethod(){
		System.out.println();
		increment();
		System.out.println("Inside:" + inoutCount);
		displayMethodClassName();
	}
	
	public void outClass(){
		
		displayMethodClassName();
		System.out.println("Outside:" + inoutCount);
		dcrement();
		System.out.println();
		
	}
	
	public static void displayMethodClassName(){
		System.out.println("Class:" + Thread.currentThread().getStackTrace()[2].getClassName());
		System.out.println("Method:" + Thread.currentThread().getStackTrace()[2].getMethodName());
	}
	
	public static String getClassName(){
		return Thread.currentThread().getStackTrace()[2].getClassName();
	}
	
	public static String getMethodName(){
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
}

