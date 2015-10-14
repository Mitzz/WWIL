package com.enercon.working;

public class Client2 {

	public static void doWork() {
		
		
		ServiceProvider.flexiService(Factory1.createCommandForClient2());
	}

}
