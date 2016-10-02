package com.enercon.model.graph.listener;

import com.enercon.model.graph.event.WecMasterEvent;

public interface WecMasterListener {

	void handler(WecMasterEvent event);
	
}
