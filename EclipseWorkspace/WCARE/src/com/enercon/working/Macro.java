package com.enercon.working;

import java.util.ArrayList;
import java.util.List;

public class Macro implements AbstractCommand{

	private List<AbstractCommand> l = new ArrayList<AbstractCommand>();
	
	public void execute() {
		for (AbstractCommand c : l) {
			c.execute();
		}
	}
	
	public void addCommand(AbstractCommand c){
		l.add(c);
	}

}
