package com.enercon.model.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.graph.IEbMasterVo;

public class EbDate {

	private String date;
	private IEbMasterVo eb;

	public EbDate() {
		super();
	}
	
	public EbDate(IEbMasterVo eb, String date) {
		super();
		this.date = date;
		this.eb = eb;
	}



	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public IEbMasterVo getEb() {
		return eb;
	}

	public void setEb(IEbMasterVo eb) {
		this.eb = eb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((eb == null) ? 0 : eb.hashCode());
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
		EbDate other = (EbDate) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (eb == null) {
			if (other.eb != null)
				return false;
		} else if (!eb.equals(other.eb))
			return false;
		return true;
	}

	public static List<EbDate> get(Collection<IEbMasterVo> wecs, String fromDate, String toDateMutated) {
		List<EbDate> wecDates = new ArrayList<EbDate>();
		for(; !fromDate.equalsIgnoreCase(toDateMutated); toDateMutated = DateUtility.getPreviousDateFromGivenDateInString(toDateMutated)){
			for(IEbMasterVo wec: wecs){
				wecDates.add(new EbDate(wec, toDateMutated));
			}
		}
		for(IEbMasterVo wec: wecs){
			wecDates.add(new EbDate(wec, fromDate));
		}
		return wecDates;
	}
}
