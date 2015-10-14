package com.enercon.struts.pojo;

public class ScadaDataJump {

	private boolean isDataCorrupt = false;
	private String readingDate;
	private String locationNo;
	private String plantNo;
	private String wecName;
	private String customerName;
	private String stateName;
	private long currentMaxGeneration;
	private long currentMaxOperatingHour;
	private long previousMaxGeneration;
	private long previousMaxOperatingHour;
	private int machineCapacity;
	private long generationDifference ;
	private long operatingHrDifference;

	@Override
	public String toString() {
		return "ScadaDataJump [ readingDate=" + readingDate + ", wecName=" + wecName + ", locationNo=" + locationNo
				+ ", plantNo=" + plantNo
				+ ", customerName=" + customerName + ", stateName=" + stateName
				+ ", currentMaxGeneration=" + currentMaxGeneration
				+ ", currentMaxOperatingHour=" + currentMaxOperatingHour
				+ ", previousMaxGeneration=" + previousMaxGeneration
				+ ", previousMaxOperatingHour=" + previousMaxOperatingHour
				+ ", machineCapacity=" + machineCapacity
				+ ", generationDifference=" + (currentMaxGeneration - previousMaxGeneration)
				+ ", operatingHrDifference=" +(currentMaxOperatingHour - previousMaxOperatingHour)+ "]";
	}

	public ScadaDataJump() {
	}
	
	public ScadaDataJump(String readingDate,
			String locationNo, String plantNo, String wecName,
			long currentMaxGeneration, long currentMaxOperatingHour,
			long previousMaxGeneration, long previousMaxOperatingHour,
			int machineCapacity) {
		
		super();
		this.readingDate = readingDate;
		this.locationNo = locationNo;
		this.plantNo = plantNo;
		this.wecName = wecName;
		this.currentMaxGeneration = currentMaxGeneration;
		this.currentMaxOperatingHour = currentMaxOperatingHour;
		this.previousMaxGeneration = previousMaxGeneration;
		this.previousMaxOperatingHour = previousMaxOperatingHour;
		this.machineCapacity = machineCapacity;
		
		calculateDifferenceInGenOper();
//		validateData();
	}

	public void validateData() {
		long generationDifference = currentMaxGeneration - previousMaxGeneration;
		long operatingHrDifference = currentMaxOperatingHour - previousMaxOperatingHour;
		
		if(currentMaxGeneration == 0 || previousMaxGeneration == 0){
			setDataCorrupt(false);
			return;
		}
		
		if((generationDifference > (24 * machineCapacity)) || (operatingHrDifference > 27) || (operatingHrDifference < 0) || (generationDifference < 0)){
			setDataCorrupt(true);
		}
		
	}
	
	public void calculateDifferenceInGenOper(){
		this.generationDifference = this.currentMaxGeneration - this.previousMaxGeneration;
		this.operatingHrDifference = this.currentMaxOperatingHour - this.previousMaxOperatingHour;
	}
	
	public String getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(String readingDate) {
		this.readingDate = readingDate;
	}

	public String getLocationNo() {
		return locationNo;
	}

	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	public String getPlantNo() {
		return plantNo;
	}

	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}

	public String getWecName() {
		return wecName;
	}

	public void setWecName(String wecName) {
		this.wecName = wecName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public long getCurrentMaxGeneration() {
		return currentMaxGeneration;
	}

	public void setCurrentMaxGeneration(long currentMaxGeneration) {
		this.currentMaxGeneration = currentMaxGeneration;
	}

	public long getCurrentMaxOperatingHour() {
		return currentMaxOperatingHour;
	}

	public void setCurrentMaxOperatingHour(long currentMaxOperatingHour) {
		this.currentMaxOperatingHour = currentMaxOperatingHour;
	}

	public long getPreviousMaxGeneration() {
		return previousMaxGeneration;
	}

	public void setPreviousMaxGeneration(long previousMaxGeneration) {
		this.previousMaxGeneration = previousMaxGeneration;
	}

	public long getPreviousMaxOperatingHour() {
		return previousMaxOperatingHour;
	}

	public void setPreviousMaxOperatingHour(long previousMaxOperatingHour) {
		this.previousMaxOperatingHour = previousMaxOperatingHour;
	}

	public int getMachineCapacity() {
		return machineCapacity;
	}

	public void setMachineCapacity(int machineCapacity) {
		this.machineCapacity = machineCapacity;
	}

	public boolean isDataCorrupt() {
		return isDataCorrupt;
	}

	private void setDataCorrupt(boolean isDataCorrupt) {
		this.isDataCorrupt = isDataCorrupt;
	}

	public long getGenerationDifference() {
		return generationDifference;
	}

	public void setGenerationDifference(long generationDifference) {
		this.generationDifference = generationDifference;
	}

	public long getOperatingHrDifference() {
		return operatingHrDifference;
	}

	public void setOperatingHrDifference(long operatingHrDifference) {
		this.operatingHrDifference = operatingHrDifference;
	}
}


/*package com.enercon.struts.pojo;

import java.sql.Date;

public class ScadaDataJump {

	private boolean isDataCorrupt = false;
	private java.sql.Date readingDate;
	private String locationNo;
	private String plantNo;
	private String wecName;
	private long currentMaxGeneration;
	private long currentMaxOperatingHour;
	private long previousMaxGeneration;
	private long previousMaxOperatingHour;
	private int machineCapacity;

	@Override
	public String toString() {
		return "ScadaDataJump [isDataCorrupt=" + isDataCorrupt
				+ ", readingDate=" + readingDate + ", locationNo=" + locationNo
				+ ", plantNo=" + plantNo + ", wecName=" + wecName
				+ ", currentMaxGeneration=" + currentMaxGeneration
				+ ", currentMaxOperatingHour=" + currentMaxOperatingHour
				+ ", previousMaxGeneration=" + previousMaxGeneration
				+ ", previousMaxOperatingHour=" + previousMaxOperatingHour
				+ ", generationDifference=" + (currentMaxGeneration - previousMaxGeneration)
				+ ", operatingHourDifference=" + (currentMaxOperatingHour - previousMaxOperatingHour)
				+ ", machineCapacity=" + machineCapacity + "]";
	}

	public ScadaDataJump() {
	}

	public ScadaDataJump(Date readingDate,
			String locationNo, String plantNo, String wecName,
			long currentMaxGeneration, long currentMaxOperatingHour,
			long previousMaxGeneration, long previousMaxOperatingHour,
			
			int machineCapacity) {
		super();
		this.readingDate = readingDate;
		this.locationNo = locationNo;
		this.plantNo = plantNo;
		this.wecName = wecName;
		this.currentMaxGeneration = currentMaxGeneration;
		this.currentMaxOperatingHour = currentMaxOperatingHour;
		this.previousMaxGeneration = previousMaxGeneration;
		this.previousMaxOperatingHour = previousMaxOperatingHour;
		this.machineCapacity = machineCapacity;
//		validateData();
	}

	public void validateData() {
		long generationDifference = currentMaxGeneration - previousMaxGeneration;
		long operatingHrDifference = currentMaxOperatingHour - previousMaxOperatingHour;
		if((generationDifference > (24 * machineCapacity)) || (operatingHrDifference > 27) || (operatingHrDifference <= 0) || (generationDifference <= 0)){
			setDataCorrupt(true);
		}
	}

	public java.sql.Date getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(java.sql.Date readingDate) {
		this.readingDate = readingDate;
	}

	public String getLocationNo() {
		return locationNo;
	}

	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	public String getPlantNo() {
		return plantNo;
	}

	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}

	public String getWecName() {
		return wecName;
	}

	public void setWecName(String wecName) {
		this.wecName = wecName;
	}

	public long getCurrentMaxGeneration() {
		return currentMaxGeneration;
	}

	public void setCurrentMaxGeneration(long currentMaxGeneration) {
		this.currentMaxGeneration = currentMaxGeneration;
	}

	public long getCurrentMaxOperatingHour() {
		return currentMaxOperatingHour;
	}

	public void setCurrentMaxOperatingHour(long currentMaxOperatingHour) {
		this.currentMaxOperatingHour = currentMaxOperatingHour;
	}

	public long getPreviousMaxGeneration() {
		return previousMaxGeneration;
	}

	public void setPreviousMaxGeneration(long previousMaxGeneration) {
		this.previousMaxGeneration = previousMaxGeneration;
	}

	public long getPreviousMaxOperatingHour() {
		return previousMaxOperatingHour;
	}

	public void setPreviousMaxOperatingHour(long previousMaxOperatingHour) {
		this.previousMaxOperatingHour = previousMaxOperatingHour;
	}

	public int getMachineCapacity() {
		return machineCapacity;
	}

	public void setMachineCapacity(int machineCapacity) {
		this.machineCapacity = machineCapacity;
	}

	public boolean isDataCorrupt() {
		return isDataCorrupt;
	}

	private void setDataCorrupt(boolean isDataCorrupt) {
		this.isDataCorrupt = isDataCorrupt;
	}
}
*/