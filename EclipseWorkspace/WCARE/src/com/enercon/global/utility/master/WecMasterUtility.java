package com.enercon.global.utility.master;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.model.master.WecMasterVo;

public class WecMasterUtility {
	private final static Logger logger = Logger.getLogger(WecMasterUtility.class);

	public static List<String> getWecIds(List<WecMasterVo> wecs){
		List<String> wecIds = new ArrayList<String>();
		String wecId = null;
		for(WecMasterVo wec: wecs){
			wecId = wec.getId();
			if(wecId == null){
				logger.error("Wec id is null");
				throw new NullPointerException("Wec id cannot be null");
			}
			wecIds.add(wec.getId());
		}
		return wecIds;
	}
}
