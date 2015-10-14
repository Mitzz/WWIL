package com.enercon.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enercon.dao.master.WecMasterDao;
import com.enercon.model.master.WecMasterVo;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.model.summaryreport.Parameter;
import com.enercon.model.summaryreport.ParameterEvaluator;

public class DataVoService {

	public List<DataVo> dailyMALessThan95(Set<String> wecIds, String date, Set<Parameter> parameters) throws SQLException, ParseException {
		List<DataVo> dataVos = new LinkedList<DataVo>();
		Map<String, IWecParameterVo> wecWiseWecParameterVo = new ParameterEvaluator().getWecWiseWecParameterVo(date, wecIds, parameters);
		Map<String, WecMasterVo> wecMasterVos = new WecMasterDao().getWecMasterVo(wecIds);
		
		for(String wecId: wecWiseWecParameterVo.keySet()){
			IWecParameterVo wecParameterVo = wecWiseWecParameterVo.get(wecId);
			
			if(wecParameterVo.ma() < 95){
				DataVo dataVo = new DataVo();
				
				dataVo.setWecParameterVo(wecParameterVo);
				dataVo.setWecMasterVo(wecMasterVos.get(wecId));
				
				dataVos.add(dataVo);
			}
		}
		return dataVos;
	}
	
	public List<DataVo> periodMALessThan98(Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters) throws SQLException, ParseException {
		List<DataVo> dataVos = new LinkedList<DataVo>();
		Map<String, IWecParameterVo> wecWiseWecParameterVo = new ParameterEvaluator().getWecWiseWecParameterVo(fromDate, toDate, wecIds, parameters);
		Map<String, WecMasterVo> wecMasterVos = new WecMasterDao().getWecMasterVo(wecIds);
		
		for(String wecId: wecWiseWecParameterVo.keySet()){
			IWecParameterVo wecParameterVo = wecWiseWecParameterVo.get(wecId);
			
			if(wecParameterVo.ma() < 98){
				DataVo dataVo = new DataVo();
				
				dataVo.setWecParameterVo(wecParameterVo);
				dataVo.setWecMasterVo(wecMasterVos.get(wecId));
				
				dataVos.add(dataVo);
			}
			
		}
		
		return dataVos;
	}
}
