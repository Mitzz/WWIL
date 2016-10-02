package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.DataVo;

public class DataVoComparator {

	public final static Comparator<DataVo> BY_AREA_WEC = new Comparator<DataVo>() {
		public int compare(DataVo v, DataVo w) {
			return byAreaWec(v, w);
		}
	};
	
	public final static Comparator<DataVo> BY_STATE_SITE_WEC = new Comparator<DataVo>(){
		public int compare(DataVo v, DataVo w) {
			return byStateSiteWec(v, w);
		}
	};
	
	public final static Comparator<DataVo> BY_MA = new Comparator<DataVo>(){
		public int compare(DataVo v, DataVo w) {
			return byMA(v, w);
		}
	};
	
	public static int byAreaWec(DataVo v, DataVo w) {
		return WecMasterVoComparator.sortByAreaWec(v.getWec(), w.getWec());
	}
	
	public static int byStateSiteWec(DataVo v, DataVo w) {
		return WecMasterVoComparator.sortByStateSiteWec(v.getWec(), w.getWec());
	}
	
	public static int byMA(DataVo v, DataVo w) {
		if(v.getWecParameterVo().ma() < w.getWecParameterVo().ma()) return -1;
		if(v.getWecParameterVo().ma() > w.getWecParameterVo().ma()) return +1;
		return 0;
	}
	
}
