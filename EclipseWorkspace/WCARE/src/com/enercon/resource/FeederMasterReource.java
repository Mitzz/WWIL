package com.enercon.resource;

import org.apache.log4j.Logger;

import com.enercon.service.master.FeederMasterService;

public class FeederMasterReource {

	private static Logger logger = Logger.getLogger(FeederMasterReource.class);
	private static FeederMasterService service = FeederMasterService.getInstance();

	/*public List<FeederMasterVo> getAll() {
		List<FeederMasterVo> feeders = null;
		try {
			feeders = service.getAll();
			SubstationMasterService.getInstance().populate(feeders);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return feeders;
	}*/
}
