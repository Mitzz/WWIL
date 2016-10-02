package com.enercon.scada;

import java.sql.SQLException;

import com.enercon.service.ScadaService;

public class WindSpeedUpload {
	private String date;
	
	public void uploadWindspeed() throws SQLException{
		
		ScadaService service = new ScadaService();
		service.mergeWindSpeed(date);
//		service.mergeWindSpeedDate(date);
           
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
