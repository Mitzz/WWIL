package com.enercon.reports.pojo;

public class WECReadingTrackerDataStorage {
	private String readingDate;
	private String wecName;
	private String mpDescription;
	private String activityType;
	private String activityTime;
	private String oldValue;
	private String newValue;
	private String modifiedBy;
	
	public WECReadingTrackerDataStorage() {}
	
	public WECReadingTrackerDataStorage(String readingDate, String wecName,
			String mpDescription, String activityType, String activityTime,
			String oldValue, String newValue, String modifiedBy) {
		this.readingDate = readingDate;
		this.wecName = wecName;
		this.mpDescription = mpDescription;
		this.activityType = activityType;
		this.activityTime = activityTime;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.modifiedBy = modifiedBy;
	}

	public String getReadingDate() {
		return readingDate;
	}
	public void setReadingDate(String readingDate) {
		this.readingDate = readingDate;
	}
	public String getWecName() {
		return wecName;
	}
	public void setWecName(String wecName) {
		this.wecName = wecName;
	}
	public String getMpDescription() {
		return mpDescription;
	}
	public void setMpDescription(String mpDescription) {
		this.mpDescription = mpDescription;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	

}
