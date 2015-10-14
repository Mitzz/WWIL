package com.enercon.model;

import java.util.Comparator;

import com.enercon.model.master.WecMasterVo;
import com.enercon.model.report.IWecParameterVo;

public class DataVo {

	private IWecParameterVo wecParameterVo;
	private WecMasterVo wecMasterVo;
	public final static Comparator<DataVo> AREA_WEC = new SomeClass();
	public final static Comparator<DataVo> STATE_SITE_WEC = new SomeClass1();
	public final static Comparator<DataVo> MA = new SomeClass2();

	public String getAreaName() {
		return wecMasterVo.getAreaName();
	}

	public String getWecName() {
		return wecMasterVo.getName();
	}

	public IWecParameterVo getWecParameterVo() {
		return wecParameterVo;
	}

	public void setWecParameterVo(IWecParameterVo wecParameterVo) {
		this.wecParameterVo = wecParameterVo;
	}

	public String getStateName() {
		return wecMasterVo.getStateName();
	}

	public String getSiteName() {
		return wecMasterVo.getSiteName();
	}

	private static class SomeClass implements Comparator<DataVo>{

		public int compare(DataVo v, DataVo w) {
			if(v.getAreaName().compareTo(w.getAreaName()) < 0) return -1;
			if(v.getAreaName().compareTo(w.getAreaName()) > 0) return +1;
			if(v.getWecName().compareTo(w.getWecName()) < 0) return -1;
			if(v.getWecName().compareTo(w.getWecName()) > 0) return +1;
			return 0;
		}
		
	}
	
	private static class SomeClass1 implements Comparator<DataVo>{

		public int compare(DataVo v, DataVo w) {
			if(v.getStateName().compareTo(w.getStateName()) < 0) return -1;
			if(v.getStateName().compareTo(w.getStateName()) > 0) return +1;
			if(v.getSiteName().compareTo(w.getSiteName()) < 0) return -1;
			if(v.getSiteName().compareTo(w.getSiteName()) > 0) return +1;
			if(v.getWecName().compareTo(w.getWecName()) < 0) return -1;
			if(v.getWecName().compareTo(w.getWecName()) > 0) return +1;
			return 0;
		}
		
	}
	
	private static class SomeClass2 implements Comparator<DataVo>{

		public int compare(DataVo v, DataVo w) {
			if(v.getWecParameterVo().ma() < w.getWecParameterVo().ma()) return -1;
			if(v.getWecParameterVo().ma() > w.getWecParameterVo().ma()) return +1;
			return 0;
		}
		
	}

	public WecMasterVo getWecMasterVo() {
		return wecMasterVo;
	}

	public void setWecMasterVo(WecMasterVo wecMasterVo) {
		this.wecMasterVo = wecMasterVo;
	}
	
	
}
