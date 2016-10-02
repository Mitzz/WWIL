package com.enercon.model.report;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.WecParameterVo;

public class ReportContainer {

	private Map<IWecMasterVo, IWecParameterVo> wecsData;
	private Map<IEbMasterVo , IWecParameterVo> ebsData;
	private Map<ISiteMasterVo, IWecParameterVo> sitesData;
	private Map<IStateMasterVo, IWecParameterVo> statesData;
	
	private IWecParameterVo total;
	private List<IWecMasterVo> wecs;
	
	public ReportContainer wecsTotal(){
		if(wecsData.size() <= 1) return this;
		this.total = total(statesData.values());
		return this;
	}
	
	public ReportContainer ebsTotal(){
		if(ebsData.size() <= 1) return this;
		this.total = total(statesData.values());
		return this;
	}
	
	public ReportContainer sitesTotal(){
		if(sitesData.size() <= 1) return this;
		this.total = total(statesData.values());
		return this;
	}
	
	public ReportContainer statesTotal(){
		if(statesData.size() <= 1) return this;
		this.total = total(statesData.values());
		return this;
	}
	
	private IWecParameterVo total(Collection<IWecParameterVo> parameterVos){
		for(IWecParameterVo parameterVo: parameterVos){
			if(parameterVo != null){
				if(total == null) total = new WecParameterVo();
				total.add(parameterVo);
			}
		}
		return total;
	}
	
	public double getCapacity(){
		double capacity = 0.0;
		for(IWecMasterVo wec: wecs){
			capacity += wec.getCapacity();
		}
		return capacity;
	}
}
