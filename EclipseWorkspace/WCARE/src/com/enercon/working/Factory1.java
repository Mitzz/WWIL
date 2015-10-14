package com.enercon.working;

public class Factory1 {

	public static AbstractCommand createCommandForClient1(){
		Macro m1 = new Macro();
		m1.addCommand(new ConcreteCommand1());
		m1.addCommand(new ConcreteCommand3());
		
		Macro m2 = new Macro();
		m1.addCommand(new ConcreteCommand5());
		m1.addCommand(new ConcreteCommand6());
		
		Macro m = new Macro();
		m.addCommand(m1);
		m.addCommand(m2);
		
		return m;
	}
	
	public static AbstractCommand createCommandForClient2(){
		Macro m1 = new Macro();
		m1.addCommand(new ConcreteCommand2());
		m1.addCommand(new ConcreteCommand4());
		
		Macro m2 = new Macro();
		m1.addCommand(new ConcreteCommand7());
		m1.addCommand(new ConcreteCommand8());
		
		Macro m = new Macro();
		m.addCommand(m1);
		m.addCommand(m2);
		
		return m;
	}
}
