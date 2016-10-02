package com.enercon.model.graph.listener;

import com.enercon.model.graph.event.EbMasterEvent;

public interface EbMasterListener {

	void handler(EbMasterEvent event);
}
