package com.enercon.model.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.service.WecMasterService;

public class WecDate {

	private IWecMasterVo wec;
	private String date;

	public WecDate(IWecMasterVo wec, String date) {
		this.wec = wec;
		this.date = date;
	}

	public IWecMasterVo getWec() {
		return wec;
	}

	public void setWec(IWecMasterVo wec) {
		this.wec = wec;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public static List<WecDate> get(Collection<IWecMasterVo> wecs, String fromDate, String toDateMutated) {
		List<WecDate> wecDates = new ArrayList<WecDate>();
		for(; !fromDate.equalsIgnoreCase(toDateMutated); toDateMutated = DateUtility.getPreviousDateFromGivenDateInString(toDateMutated)){
			for(IWecMasterVo wec: wecs){
				wecDates.add(new WecDate(wec, toDateMutated));
			}
		}
		for(IWecMasterVo wec: wecs){
			wecDates.add(new WecDate(wec, fromDate));
		}
		return wecDates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((wec == null) ? 0 : wec.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WecDate other = (WecDate) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (wec == null) {
			if (other.wec != null)
				return false;
		} else if (!wec.equals(other.wec))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WecDate [wec=" + wec + ", date=" + date + "]";
	}
	
	public static void main(String[] args) {
		WecMasterService wecService = WecMasterService.getInstance();
		
//		Case 1
		String id1 = "1110000129";
		String id2 = "1110000129";
		
		IWecMasterVo wec1 = wecService.get(id1);
		IWecMasterVo wec2 = wecService.get(id2);
		
		String date1 = "29-OCT-2016";
		String date2 = "29-OCT-2016";
		
		
//		Case 2
		id1 = "1110000129";
		id2 = "1110000130";
		
		wec1 = wecService.get(id1);
		wec2 = wecService.get(id2);
		
		date1 = "29-OCT-2016";
		date2 = "29-OCT-2016";

//		Case 3
		id1 = "1110000129";
		id2 = "1110000129";
		
		wec1 = wecService.get(id1);
		wec2 = wecService.get(id2);
		
		date1 = "29-OCT-2016";
		date2 = "29-OCT-2015";
		
//		Case 4
		id1 = "1110000129";
		id2 = "1110000130";
		
		wec1 = wecService.get(id1);
		wec2 = wecService.get(id2);
		
		date1 = "29-OCT-2016";
		date2 = "29-OCT-2015";
		
		WecDate wecDate1 = new WecDate(wec1, date1);
		WecDate wecDate2 = new WecDate(wec2, date2);
		
		System.out.println(wecDate1.equals(wecDate2));
	}

}
