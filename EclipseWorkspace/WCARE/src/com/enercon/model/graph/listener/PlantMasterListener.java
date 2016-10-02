package com.enercon.model.graph.listener;

import com.enercon.model.graph.event.PlantMasterEvent;

public interface PlantMasterListener {

	void handler(PlantMasterEvent event);
}
