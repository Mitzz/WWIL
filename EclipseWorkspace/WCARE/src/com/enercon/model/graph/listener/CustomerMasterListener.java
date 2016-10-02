package com.enercon.model.graph.listener;

import com.enercon.model.graph.event.CustomerMasterEvent;

public interface CustomerMasterListener {

	void handler(CustomerMasterEvent event);
	
}
