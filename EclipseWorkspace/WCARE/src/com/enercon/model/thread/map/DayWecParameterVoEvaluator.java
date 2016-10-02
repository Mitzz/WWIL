package com.enercon.model.thread.map;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.dao.WecParameterDataDao;
import com.enercon.global.utility.master.WecMasterUtility;
import com.enercon.model.master.WecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;

public class DayWecParameterVoEvaluator<Key, Value extends IWecParameterVo> implements MapValueEvaluatorWorker<Key, Value>{
	private final static Logger logger = Logger.getLogger(DayWecParameterVoEvaluator.class);
	private Key key;
	private Set<String> wecIds;
	private String date;
	private Set<Parameter> parameters;
	private boolean dataCheck;
	
	private WecParameterData parameterData;
	
	public DayWecParameterVoEvaluator(Set<String> wecIds, String date, Set<Parameter> parameters) {
		this.wecIds = wecIds;
		this.date = date;
		this.parameters = parameters;
	}
	
	public DayWecParameterVoEvaluator(Set<String> wecIds, String date, Set<Parameter> parameters, boolean dataCheck) {
		this.wecIds = wecIds;
		this.date = date;
		this.parameters = parameters;
		this.dataCheck = dataCheck;
	}
	
	public DayWecParameterVoEvaluator(Key key, Set<String> wecIds, String date, Set<Parameter> parameters) {
		this.key = key;
		this.wecIds = wecIds;
		this.date = date;
		this.parameters = parameters;
	}
	
	public DayWecParameterVoEvaluator(Key key, List<WecMasterVo> wecs, String date, List<Parameter> parameters) {
		this.key = key;
		this.wecIds =  new LinkedHashSet<String>(WecMasterUtility.getWecIds(wecs));
		this.date = date;
		this.parameters = new HashSet<Parameter>(parameters);
	}

	public DayWecParameterVoEvaluator(WecParameterData parameterData) {
		this.parameterData = parameterData;
		
	}

	public <T extends IWecParameterVo> T evaluateByWecParameterData() throws SQLException, ParseException {
		T parameter = null;
		parameter = WecParameterDataDao.getInstance().getTotal(parameterData);
		return parameter;
	}

	public static void main(String[] args) {
		try {
			IWecParameterVo parameterVo = new DayWecParameterVoEvaluator<String, IWecParameterVo>(null, null, null).evaluate();
			
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
	
	public <T extends IWecParameterVo> T evaluate() throws SQLException, ParseException{
		T parameter = null;
		parameter = WecParameterDataDao.getInstance().getTotal(wecIds, date, parameters, dataCheck);
		return parameter;
	}

	public Value call() throws SQLException, ParseException{
		return evaluate();
	}
	
	public Key getKey() {
		return key;
	}
	
	public Set<String> getWecIds() {
		return wecIds;
	}

	public String getDate() {
		return date;
	}
	
}
