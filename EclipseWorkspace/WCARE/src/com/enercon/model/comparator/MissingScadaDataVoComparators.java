package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.missingScadaData.MissingScadaDataVo;

public class MissingScadaDataVoComparators {

	public final static Comparator<MissingScadaDataVo> SALPW = salpw(); 

	private static Comparator<MissingScadaDataVo> salpw(){
		return new Comparator<MissingScadaDataVo>(){
			public int compare(MissingScadaDataVo o1, MissingScadaDataVo o2) {
				return WecMasterVoComparator.sortSALPW(o1.getMasterVo(), o2.getMasterVo());
			}
		};
	}
}
