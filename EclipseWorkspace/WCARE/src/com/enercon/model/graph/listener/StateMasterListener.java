package com.enercon.model.graph.listener;

import com.enercon.model.graph.event.StateMasterEvent;

public interface StateMasterListener {

	void handler(StateMasterEvent event);
}
