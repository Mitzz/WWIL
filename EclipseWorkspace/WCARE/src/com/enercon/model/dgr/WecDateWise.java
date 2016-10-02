package com.enercon.model.dgr;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.enercon.global.utility.WecParameterUtility;
import com.enercon.model.comparator.WecMasterVoComparator;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.WecParameterEvaluator;

public class WecDateWise {
	
	public final static Comparator<WecDateWise> BY_WEC_NAME = new Comparator<WecDateWise>() {

		public int compare(WecDateWise o1, WecDateWise o2) {
			return WecMasterVoComparator.sortByWec(o1.getWec(), o2.getWec());
		}
	};

	//Input
	private String fromDate;
	private String toDate;
	private IWecMasterVo wec;
	private Set<Parameter> wecParameters;

	// Output
	private Map<String, IWecParameterVo> datesData = new TreeMap<String, IWecParameterVo>();
	private IWecParameterVo total;

	// Local
	private WecParameterData wecParameterData;

	public WecDateWise(String fromDate, String toDate, IWecMasterVo wec, Set<Parameter> wecParameters) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.wec = wec;
		this.wecParameters = wecParameters;

		setUp();
	}

	private void setUp() {
		setWecParameterData();
	}

	private void setWecParameterData() {
		wecParameterData = new WecParameterData();
		wecParameterData.setDataCheck(true);
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setParameters(wecParameters);

		Set<IWecMasterVo> wecs = new HashSet<IWecMasterVo>();
		wecs.add(wec);
		wecParameterData.setWecs(wecs);
		wecParameterData.setPublish(1);

	}

	public WecDateWise populateData() throws SQLException {
		WecParameterEvaluator evaluator = WecParameterEvaluator.getInstance();
		datesData.putAll(evaluator.getDateWise(wecParameterData));
		return this;
	}
	
	public WecDateWise populateTotal(){
		total = WecParameterUtility.total(datesData.values());
		return this;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public IWecMasterVo getWec() {
		return wec;
	}

	public void setWec(IWecMasterVo wec) {
		this.wec = wec;
	}

	public Map<String, IWecParameterVo> getDatesData() {
		return datesData;
	}

	public void setDatesData(Map<String, IWecParameterVo> datesData) {
		this.datesData = datesData;
	}
	
	public IWecParameterVo getTotal() {
		return total;
	}

	public void setTotal(IWecParameterVo datesDataTotal) {
		this.total = datesDataTotal;
	}
	
	
}
