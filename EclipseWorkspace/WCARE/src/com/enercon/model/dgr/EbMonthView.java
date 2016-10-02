package com.enercon.model.dgr;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import weblogic.utils.collections.TreeMap;

import com.enercon.global.utility.WecParameterUtility;
import com.enercon.model.comparator.WecMasterVoComparator;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.eb.IEbParameterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;

public class EbMonthView {

	private String date;
	private IEbParameterVo ebVo;
	private Map<IWecMasterVo, IWecParameterVo> wecsData = new TreeMap<IWecMasterVo, IWecParameterVo>(WecMasterVoComparator.BY_NAME);
	private IWecParameterVo total;
	private List<IWecMasterVo> noConnectivityWecs;
	
	public final static Comparator<EbMonthView> BY_DATE = new Comparator<EbMonthView>(){

		public int compare(EbMonthView o1, EbMonthView o2) {
			if(o1.getDate().compareTo(o2.getDate()) < 0) return -1;
			if(o1.getDate().compareTo(o2.getDate()) > 0) return +1;
			return 0;
		}
		
	};

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public IEbParameterVo getEbData() {
		return ebVo;
	}

	public void setEbVo(IEbParameterVo ebVo) {
		this.ebVo = ebVo;
	}

	public Map<IWecMasterVo, IWecParameterVo> getWecsData() {
		return wecsData;
	}

	public void setWecsData(Map<IWecMasterVo, IWecParameterVo> wecsData) {
		this.wecsData.putAll(wecsData);
		checkWecsConnectivity();
		doTotal();
	}

	private void doTotal() {
		total = WecParameterUtility.total(wecsData.values());
	}

	public IWecParameterVo getTotal() {
		return total;
	}

	private void checkWecsConnectivity() {
		noConnectivityWecs = WecParameterUtility.connectivity(wecsData);
		
	}

	public List<IWecMasterVo> getNoConnectivityWecs() {
		return noConnectivityWecs;
	}
	
}
