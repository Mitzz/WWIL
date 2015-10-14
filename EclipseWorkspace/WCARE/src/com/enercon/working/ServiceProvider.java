package com.enercon.working;

public class ServiceProvider {

	public static void flexiService(AbstractCommand command){
		System.out.println("In Invariant 1");
		command.execute();
		System.out.println("In Invariant 2");
	}
}
