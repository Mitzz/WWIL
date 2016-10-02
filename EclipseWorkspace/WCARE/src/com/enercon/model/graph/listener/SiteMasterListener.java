package com.enercon.model.graph.listener;

import com.enercon.model.graph.event.SiteMasterEvent;

public interface SiteMasterListener {
	void handler(SiteMasterEvent event);
}
