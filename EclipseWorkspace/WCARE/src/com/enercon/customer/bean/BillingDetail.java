package com.enercon.customer.bean;

import java.util.*;

public class BillingDetail {

	
	String siteName;
	String ebName;
	float wecgen;
	float kwhExp;
	float kwhImp;
	float rkvhExpLead;
	float rkvhExpLag;
	float rkvhImpLead;
	float rkvhImpLag;
	float kvhExp;
	float kvhImp;
	String month;
	Date fromDate;
	Date toDate;
	public String getEbName() {
		return ebName;
	}
	public void setEbName(String ebName) {
		this.ebName = ebName;
	}
	
	public float getKvhExp() {
		return kvhExp;
	}
	public void setKvhExp(float kvhExp) {
		this.kvhExp = kvhExp;
	}
	public float getKvhImp() {
		return kvhImp;
	}
	public void setKvhImp(float kvhImp) {
		this.kvhImp = kvhImp;
	}
	public float getKwhExp() {
		return kwhExp;
	}
	public void setKwhExp(float kwhExp) {
		this.kwhExp = kwhExp;
	}
	public float getKwhImp() {
		return kwhImp;
	}
	public void setKwhImp(float kwhImp) {
		this.kwhImp = kwhImp;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public float getRkvhExpLag() {
		return rkvhExpLag;
	}
	public void setRkvhExpLag(float rkvhExpLag) {
		this.rkvhExpLag = rkvhExpLag;
	}
	public float getRkvhExpLead() {
		return rkvhExpLead;
	}
	public void setRkvhExpLead(float rkvhExpLead) {
		this.rkvhExpLead = rkvhExpLead;
	}
	public float getRkvhImpLag() {
		return rkvhImpLag;
	}
	public void setRkvhImpLag(float rkvhImpLag) {
		this.rkvhImpLag = rkvhImpLag;
	}
	public float getRkvhImpLead() {
		return rkvhImpLead;
	}
	public void setRkvhImpLead(float rkvhImpLead) {
		this.rkvhImpLead = rkvhImpLead;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public float getWecgen() {
		return wecgen;
	}
	public void setWecgen(float wecgen) {
		this.wecgen = wecgen;
	}
	
	public final Date getFromDate() {
		return fromDate;
	}

	public final void setFromDate(java.util.Date fromDate) {
		this.fromDate = fromDate;
	}
	public final Date getToDate() {
		return toDate;
	}

	public final void setToDate(java.util.Date toDate) {
		this.toDate = toDate;
	}
	
}
