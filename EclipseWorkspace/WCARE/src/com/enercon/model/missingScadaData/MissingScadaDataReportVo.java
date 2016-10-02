package com.enercon.model.missingScadaData;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.enercon.dao.master.StateMasterDao;
import com.enercon.dao.missingScadaData.MissingScadaDataDao;
import com.enercon.model.comparator.MissingScadaDataVoComparators;
import com.enercon.model.comparator.StateMasterVoComparator;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;

public class MissingScadaDataReportVo {

	private String date;
	private List<MissingScadaDataVo> missingScadaDataVos;
	private Map<IStateMasterVo, Integer> stateWiseMissingWecsCount = new TreeMap<IStateMasterVo, Integer>(StateMasterVoComparator.BY_NAME);
	private final static Logger logger = Logger.getLogger(MissingScadaDataReportVo.class);
	
	public MissingScadaDataReportVo(String date) {
		this.date = date;
	}

	public MissingScadaDataReportVo populateData() throws SQLException{
		logger.debug("kjdfs");
		MissingScadaDataDao dao = MissingScadaDataDao.getInstance();
		logger.debug("kjdfs");
		missingScadaDataVos = dao.getMissingScadaDataVos(date);

		logger.debug("kjdfs");
		Collections.sort(missingScadaDataVos, MissingScadaDataVoComparators.SALPW);
		
		for(MissingScadaDataVo missingScadaVo : missingScadaDataVos){
			dao.reason(missingScadaVo);
			logger.warn(missingScadaVo);
		}
		
//		logger.debug("kjdfs");
		logger.warn("Total Wecs Data not available due to Scada for " + date + ": " + missingScadaDataVos.size());
		
		//State-wise Count
		for (MissingScadaDataVo missingScadaVo : missingScadaDataVos) {
			IWecMasterVo wec = missingScadaVo.getWec();
			IStateMasterVo stateMasterVo = wec.getState();
			stateWiseMissingWecsCount.put(stateMasterVo, stateWiseMissingWecsCount.containsKey(stateMasterVo) ? stateWiseMissingWecsCount.get(stateMasterVo) + 1 : 1);
		}
		
//		logger.debug("kjdfs");
		
		List<IStateMasterVo> states = StateMasterDao.getInstance().getAll();
		
		//State with no Missing SCADA Data
		for (IStateMasterVo state : states) {
			if(!stateWiseMissingWecsCount.containsKey(state)){
				stateWiseMissingWecsCount.put(state, 0);
			}
		}
//		logger.debug("kjdfs");
		
		return this;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<MissingScadaDataVo> getMissingScadaDataVos() {
		return missingScadaDataVos;
	}

	public void setMissingScadaDataVos(List<MissingScadaDataVo> missingScadaDataVos) {
		this.missingScadaDataVos = missingScadaDataVos;
	}

	public Map<IStateMasterVo, Integer> getStateWise() {
		return stateWiseMissingWecsCount;
	}

	public void setStateWise(Map<IStateMasterVo, Integer> stateWise) {
		this.stateWiseMissingWecsCount = stateWise;
	}
}