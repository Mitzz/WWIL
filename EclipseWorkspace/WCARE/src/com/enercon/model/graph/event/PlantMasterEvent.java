package com.enercon.model.graph.event;

import com.enercon.model.graph.IPlantMasterVo;

public class PlantMasterEvent extends CUDEvent{

	private IPlantMasterVo plant;

	public IPlantMasterVo getPlant() {
		return plant;
	}

	public void setPlant(IPlantMasterVo plant) {
		this.plant = plant;
	}
	
	
}
