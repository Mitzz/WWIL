package com.enercon.model.utility;

import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.model.graph.IEbMasterVo;
import com.enercon.service.master.EbMasterService;

public class EbDetails {
	
	private static Logger logger = Logger.getLogger(EbDetails.class);

	//Input
	private IEbMasterVo eb;
	
	//Output
	private List<EbWecTypeCount> wecTypeCounts;
	private double capacity;
	
	//Local
	private EbMasterService ebService = EbMasterService.getInstance();
	
	public EbDetails(IEbMasterVo eb){
		this.eb = eb;
		wecTypeCounts = ebService.getActiveWecTypeCount(this.eb);
		
		for(EbWecTypeCount wecTypeCount: wecTypeCounts){
			this.capacity += wecTypeCount.getCapacity();
		}
	}
	
	public String getWecCountType(){
		StringBuilder builder = new StringBuilder();
		
		for(EbWecTypeCount wecTypeCount: wecTypeCounts){
			builder.append(wecTypeCount.getWecType() + "X" + wecTypeCount.getWecCount() + ",");
		}
		
		return builder.toString().substring(0, builder.toString().length()-1);
	}
	
	public double getCapacity(){
		return capacity;
	}
	
	public static void main(String[] args) {
//		String ebId = "1000001451";
//		String ebId = "1000001410";
		String ebId = "1000001040";
		
		IEbMasterVo eb = EbMasterService.getInstance().get(ebId);
		
		EbDetails machineType = new EbDetails(eb);
		
		logger.debug(machineType.getWecCountType() + " " + machineType.getCapacity()/1000 + "MW");
	}

	@Override
	public String toString() {
		return "EbDetails [wecTypeCounts=" + wecTypeCounts + ", capacity=" + capacity + "]";
	}
	
	
}
