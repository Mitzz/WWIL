package com.enercon.global.utility.master;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.model.graph.IEbMasterVo;

public class EbMasterUtility {
	
	private static Logger logger = Logger.getLogger(EbMasterUtility.class);

	public static Set<String> getEbIds(Collection<IEbMasterVo> ebs) {
		Set<String> ebIds = new HashSet<String>();
		String ebId = null;
		for(IEbMasterVo eb: ebs){
			ebId = eb.getId();
			if(ebId == null){
				logger.error("Eb id is null");
				throw new NullPointerException("Eb id cannot be null");
			}
			ebIds.add(ebId);
		}
		return ebIds;
	}
}
