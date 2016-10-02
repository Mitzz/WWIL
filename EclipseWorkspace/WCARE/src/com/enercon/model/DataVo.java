package com.enercon.model;

import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;

public class DataVo {

	private IWecParameterVo wecParameterVo;
	private IWecMasterVo wec;
	

	public String getAreaName() {
		return wec.getArea().getName();
	}

	public String getWecName() {
		return wec.getName();
	}

	public IWecParameterVo getWecParameterVo() {
		return wecParameterVo;
	}

	public void setWecParameterVo(IWecParameterVo wecParameterVo) {
		this.wecParameterVo = wecParameterVo;
	}

	public String getStateName() {
		return wec.getState().getName();
	}

	public String getSiteName() {
		return wec.getSite().getName();
	}



	public IWecMasterVo getWec() {
		return wec;
	}

	public void setWec(IWecMasterVo wecMasterVo) {
		this.wec = wecMasterVo;
	}
	
	
}
