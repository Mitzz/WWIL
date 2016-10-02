package com.enercon.service.parameter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.enercon.dao.parameter.EbParameterDataDao;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.parameter.eb.DayEbParameterVoEvaluator;
import com.enercon.model.parameter.eb.EbParameterData;
import com.enercon.model.parameter.eb.IEbParameterVo;
import com.enercon.model.table.EbReadingVo;
import com.enercon.model.utility.EbDate;

public class EbParameterEvaluator {
	
	private static Logger logger = Logger.getLogger(EbParameterEvaluator.class);
	
	private EbParameterEvaluator(){}
	
	private static class SingletonHelper{
		public final static EbParameterEvaluator INSTANCE = new EbParameterEvaluator();
	}
	
	public static EbParameterEvaluator getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public IEbParameterVo getTotal(EbParameterData ebParameterData) throws SQLException, ParseException {
		IEbParameterVo p = new DayEbParameterVoEvaluator(ebParameterData).evaluate();
		return p;
	}
	
	public static void main(String[] args) {
		/*EbMasterService ebMasterService = EbMasterService.getInstance();
		String ebId = "1000001218";
		IEbMasterVo eb = ebMasterService.get(ebId);
		
		List<IEbMasterVo> ebs = Arrays.asList(eb, ebMasterService.get("1000001312"));
		Set<EbParameter> parameters = new HashSet<EbParameter>(Arrays.asList(EbParameter.KWHEXPORT, EbParameter.KWHIMPORT));
		String date = "09-FEB-2016";
		
		try {
			IEbParameterVo ebParameterVo = EbParameterEvaluator.getInstance().getByEbsDate(ebs, date, parameters, false);
			logger.debug(ebParameterVo);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}*/ 
	}

	public Map<IEbMasterVo, IEbParameterVo> getEbWise(EbParameterData ebParameterdata) throws SQLException {
		return EbParameterDataDao.getInstance().getEbWise(ebParameterdata);
	}

	public Map<EbDate, IEbParameterVo> getEBDateWise(EbParameterData ebParameterdata) throws SQLException {
		return EbParameterDataDao.getInstance().getEbDateWise(ebParameterdata);
	}
	
	public boolean delete(EbReadingVo vo) throws SQLException, CloneNotSupportedException {
		return EbParameterDataDao.getInstance().delete(vo);
	}
}
