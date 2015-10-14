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
import com.enercon.model.master.StateMasterVo;
import com.enercon.model.master.WecMasterVo;

public class MissingScadaDataReportVo {

	private String date;
	private List<MissingScadaDataVo> missingScadaDataVos;
	private Map<StateMasterVo, Integer> stateWise = new TreeMap<StateMasterVo, Integer>(StateMasterVoComparator.NAME);
	private final static Logger logger = Logger.getLogger(MissingScadaDataVo.class);
	
	public MissingScadaDataReportVo(String date) {
		this.date = date;
	}

	public MissingScadaDataReportVo populateData() throws SQLException{
		missingScadaDataVos = new MissingScadaDataDao().getMissingScadaDataVoForReport(date);
		Collections.sort(missingScadaDataVos, MissingScadaDataVoComparators.SALPW);
		logger.info("Total Wecs Data not available due to Scada for " + date + ": " + missingScadaDataVos.size());
		for (MissingScadaDataVo missingScadaVo : missingScadaDataVos) {
			WecMasterVo vo = missingScadaVo.getMasterVo();
			StateMasterVo stateMasterVo = vo.getEb().getSite().getArea().getState();
			stateWise.put(stateMasterVo, stateWise.containsKey(stateMasterVo) ? stateWise.get(stateMasterVo) + 1 : 1);
		}
		
		List<StateMasterVo> stateMasterVos = new StateMasterDao().getStateMasterVos();
		Collections.sort(stateMasterVos, StateMasterVoComparator.NAME);
		
		for (StateMasterVo vo : stateMasterVos) {
			stateWise.put(vo, stateWise.containsKey(vo) ? stateWise.get(vo) : 0);
		}
		
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

	public Map<StateMasterVo, Integer> getStateWise() {
		return stateWise;
	}

	public void setStateWise(Map<StateMasterVo, Integer> stateWise) {
		this.stateWise = stateWise;
	}
	
	
}

