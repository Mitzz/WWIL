package com.enercon.model.thread.map;

import java.util.Set;

import com.enercon.dao.WECParameterVoDao;
import com.enercon.model.report.ManyWECsManyDatesTotal;

public class ManyWECsManyDatesTotalWorker<Key, Value> implements MapValueEvaluatorWorker<Key, ManyWECsManyDatesTotal>{
	private Key key;
	private Set<String> wecIds;
	private String fromDate;
	private String toDate;
	
	public ManyWECsManyDatesTotalWorker(Key key, Set<String> wecIds, String fromDate, String toDate){
		this.key = key;
		this.wecIds = wecIds;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
	public Key getKey(){
		return key;
	}

	public ManyWECsManyDatesTotal call() throws Exception {
		WECParameterVoDao wecDao = new WECParameterVoDao();
		return wecDao.getManyWECsManyDatesTotal(wecIds, fromDate, toDate);
	}
}
