package com.enercon.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enercon.dao.master.WecMasterDao;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.WecParameterEvaluator;

public class DataVoService {

	public List<DataVo> dailyMALessThan95(Collection<IWecMasterVo> wecs, String date, Set<Parameter> parameters) throws SQLException, ParseException {
//		List<DataVo> dataVos = new LinkedList<DataVo>();
//		Set<String> wecIds = WecMasterUtility.getWecIds(wecs);
//		Map<String, IWecParameterVo> wecWiseWecParameterVo = WecParameterEvaluator.getInstance().getWecWiseWecParameterVo(date, wecIds, parameters);
//		WecMasterDao wecDao = WecMasterDao.getInstance();
//		for(String wecId: wecWiseWecParameterVo.keySet()){
//			IWecParameterVo wecParameterVo = wecWiseWecParameterVo.get(wecId);
//			
//			if(wecParameterVo.ma() < 95){
//				DataVo dataVo = new DataVo();
//				
//				dataVo.setWecParameterVo(wecParameterVo);
//				dataVo.setWec(wecDao.get(wecId));
//				dataVos.add(dataVo);
//				
//			}
//		}
//		return dataVos;
		
		WecParameterData wecParameterData = new WecParameterData();
		wecParameterData.setFromDate(date);
		wecParameterData.setToDate(date);
		wecParameterData.setParameters(parameters);
		wecParameterData.setWecs(new HashSet<IWecMasterVo>(wecs));
		
		List<DataVo> dataVos = new LinkedList<DataVo>();
		Map<IWecMasterVo, IWecParameterVo> wecWiseWecParameterVo = WecParameterEvaluator.getInstance().getWecWise(wecParameterData);
		for(IWecMasterVo wec: wecWiseWecParameterVo.keySet()){
			IWecParameterVo wecParameterVo = wecWiseWecParameterVo.get(wec);
			
			if(wecParameterVo.ma() < 95){
				DataVo dataVo = new DataVo();
				
				dataVo.setWecParameterVo(wecParameterVo);
				dataVo.setWec(wec);
				dataVos.add(dataVo);
				
			}
		}
		return dataVos;
	}
	
	public List<DataVo> periodMALessThan98(Collection<IWecMasterVo> wecs, String fromDate, String toDate, Set<Parameter> parameters) throws SQLException, ParseException {
		/*List<DataVo> dataVos = new LinkedList<DataVo>();
		Set<String> wecIds = WecMasterUtility.getWecIds(wecs);
		Map<String, IWecParameterVo> wecWiseWecParameterVo = WecParameterEvaluator.getInstance().getWecWiseWecParameterVo(fromDate, toDate, wecIds, parameters);
		WecMasterDao wecDao = WecMasterDao.getInstance();
		for(String wecId: wecWiseWecParameterVo.keySet()){
			IWecParameterVo wecParameterVo = wecWiseWecParameterVo.get(wecId);
			
			if(wecParameterVo.ma() < 98){
				DataVo dataVo = new DataVo();
				
				dataVo.setWecParameterVo(wecParameterVo);
				dataVo.setWec(wecDao.get(wecId));
				dataVos.add(dataVo);
			}
			
		}
		
		return dataVos;*/
		List<DataVo> dataVos = new LinkedList<DataVo>();
		
		WecParameterData wecParameterData = new WecParameterData();
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setParameters(parameters);
		wecParameterData.setWecs(new HashSet<IWecMasterVo>(wecs));
		
		Map<IWecMasterVo, IWecParameterVo> wecWiseWecParameterVo = WecParameterEvaluator.getInstance().getWecWise(wecParameterData);
		
		for(IWecMasterVo wec: wecWiseWecParameterVo.keySet()){
			IWecParameterVo wecParameterVo = wecWiseWecParameterVo.get(wec);
			
			if(wecParameterVo.ma() < 98){
				DataVo dataVo = new DataVo();
				
				dataVo.setWecParameterVo(wecParameterVo);
				dataVo.setWec(wec);
				dataVos.add(dataVo);
			}
			
		}
		
		return dataVos;
	}
}
