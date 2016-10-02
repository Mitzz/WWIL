package com.enercon.model.parameter.eb;

import java.sql.SQLException;
import java.text.ParseException;

import com.enercon.dao.parameter.EbParameterDataDao;

public class DayEbParameterVoEvaluator {

	private EbParameterData ebParameterData;
	
	public DayEbParameterVoEvaluator() {}

	public DayEbParameterVoEvaluator(EbParameterData ebParameterData) {
		super();
		this.ebParameterData = ebParameterData;
	}

	public IEbParameterVo evaluate() throws SQLException, ParseException{
		EbParameterDataDao dao = EbParameterDataDao.getInstance();
		return dao.getTotal(ebParameterData);
	}
}
