package com.enercon.working;

public class Client1{
	public static void doWork(){
		ServiceProvider.flexiService(Factory1.createCommandForClient1());
	}
}
