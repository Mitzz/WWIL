package com.enercon.global.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.WecParameterVo;

public class WecParameterUtility {

	public static IWecParameterVo total(Collection<IWecParameterVo> parameterVos){
		if(parameterVos.size() <= 1) return null;
		IWecParameterVo total = null;
		for(IWecParameterVo parameterVo: parameterVos){
			if(parameterVo != null){
				if(total == null) total = new WecParameterVo();
				total.add(parameterVo);
			}
		}
		return total;
	}
	
	public static List<IWecMasterVo> connectivity(Map<IWecMasterVo, IWecParameterVo> wecsData){
		List<IWecMasterVo> noConnectivityWecs = new ArrayList<IWecMasterVo>();
		WecDgrShow dgr = new WecDgrShow();
		for(IWecMasterVo wec: wecsData.keySet()){
			if(!dgr.setWecParameterVo(wecsData.get(wec)).isShow()){
				noConnectivityWecs.add(wec);
			}
		}
		return noConnectivityWecs;
	}
}
