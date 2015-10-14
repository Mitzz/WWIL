package com.enercon.model.thread.map;

import java.util.Set;

import com.enercon.dao.WECParameterVoDao;
import com.enercon.model.report.ManyWECsOneDateTotal;

public class ManyWECsOneDateTotalWorker<Key, Value> implements MapValueEvaluatorWorker<Key, ManyWECsOneDateTotal>{
	private Key stateId;
	private Set<String> wecIds;
	private String date;
	
	public ManyWECsOneDateTotalWorker(Key stateId, Set<String> wecIds, String date){
		this.stateId = stateId;
		this.wecIds = wecIds;
		this.date = date;
	}
	
	public Key getKey(){
		return stateId;
	}

	public ManyWECsOneDateTotal call() throws Exception {
		WECParameterVoDao wecDao = new WECParameterVoDao();
		return wecDao.getManyWECsOneDateTotal(wecIds, date);
	}
}
