package com.enercon.model.summaryreport;

import java.util.Set;

public enum Parameter {

	GENERATION("Generation", "N_GEN", "SUM", "GEN"), 
	OPERATING_HOUR("OperatingHour", "N_OPHR", "SUM", "OPHR"), 
	MA("MachineAvailability", "N_MA", "AVG", "MA"),
	GA("GridAvailability", "N_GA", "AVG", "GA"),
	FM("SpecialShutdown", "N_FM", "SUM", "FM"),
	WS("Windspeed", "N_WS", "AVG", "WS"),
	
	TOTAL_GENERATION("TotalGeneration", "N_GEN_TOTAL", "SUM", "GEN_TOTAL"), 
	TOTAL_OPERATING_HOUR("TotalOperatingHour", "N_OPHR_TOTAL", "SUM", "OPHR_TOTAL"), 
	GEA("GridExternalAvailability","N_GEA","AVG","GEA"),
	SA("StimulationAvailability","N_SA","AVG","SA"),
	RA("ResourceAvailability","N_RA","AVG","RA"),
	CF("CapacityFactor","N_CF","AVG","CF"),
	GIA("GridInternalAvailability","N_GIA","AVG","GIA"),
	MIA("MachineInternalAvailability","N_MIA","AVG","MIA"),
	
	MF("MachineFault","N_MF","SUM","MF"),
	MS("MachineShutdown","N_MS","SUM","MS"),
	GIF("GridInternalFault","N_GIF","SUM","GIF"),
	GIS("GridInternalShutdown","N_GIS","SUM","GIS"),
	GEF("GridExternalFault","N_GEF","SUM","GEF"),
	GES("GridExternalShutdown","N_GES","SUM","GES"),
	E1F("GridVCB_TO_WWILSSFault","N_E1F","SUM","E1F"),
	E1S("GridVCB_TO_WWILSSShutdown","NE1S","SUM","E1S"),
	E2F("GridWWILSSFault","N_E2F","SUM","E2F"),
	E2S("GridWWILSSShutdown","N_E2S","SUM","E2S"),
	E3F("GridWWILSS_ONWARDSFault","N_E3F","SUM","E3F"),
	E3S("GridWWILSS_ONWARDSShutdown","N_E3S","SUM","E3S"),
	EB_LOAD("EBLOADRST","N_EB_LOAD","SUM","EB_LOAD"),
	LULL_HOUR("LullHour","N_LULL_HOUR","SUM","LULL_HOUR"),
	OMNP("CustomerScopeShutdown","N_OMNP","SUM","OMNP"),
	GTC("GridTripCount","N_GTC","SUM","GTC"),
	
	REVENUE("Revenue", "(N_gen - (N_gen * 3 /100)) * wec_master_utility.cost(S_wec_id)", "SUM", "Revenue"),
	LR_LOSS("LRLoss", "param_calculator.get_lr_loss(S_wec_id, D_reading_date)", "SUM", "LRLoss"),
	FM_LOSS("FMLoss", "param_calculator.get_fm_loss(S_wec_id, D_reading_date)", "SUM", "FMLoss"),
	REMARK("Remark", "S_REMARK", "MAX", "Remark");
	
	private String paramterName;
	private String columnName;
	private String operationType;
	private String parameterCode;
	
	private Parameter(String parameterName, String columnName, String operationType, String parameterCode) {
		this.paramterName = parameterName;
		this.columnName = columnName;
		this.operationType = operationType;
		this.parameterCode = parameterCode;
	}

	public String getColumnName(){
		return this.columnName;
	}

	public String getOperationType(){
		return this.operationType;
	}

	public String getParamterName() {
		return paramterName;
	}
	
	public String getParameterCode(){
		return parameterCode;
	}
	
	public static String getQuery(Set<Parameter> parameters){
		StringBuffer buffer = new StringBuffer();
		for (Parameter parameter : parameters) {
			switch (parameter) {
			case GENERATION:
			case OPERATING_HOUR:
			case FM:
			case TOTAL_GENERATION:
			case TOTAL_OPERATING_HOUR:
			case MF:
			case MS:
			case GIF:
			case GIS:
			case GEF:
			case GES:
			case E1F:
			case E1S:
			case E2F:
			case E2S:
			case E3F:
			case E3S:
			case EB_LOAD:
			case LULL_HOUR:
			case OMNP:
			case GTC:
			case REMARK:
				buffer.append(parameter.getOperationType() + "(" + parameter.getColumnName() + ") as " + parameter.getParamterName() + ", ");
				break;
			
			case MA:
			case GA:
			case WS:  
			case GEA: 
			case SA: 
			case RA: 
			case CF: 
			case GIA:
			case MIA:
				buffer.append("round(" + parameter.getOperationType() + "(" + parameter.getColumnName() + "), 2) as " + parameter.getParamterName() + ", "); break;
			case REVENUE:
			case LR_LOSS:
			case FM_LOSS:
				buffer.append("round(" + parameter.getOperationType() + "(" + parameter.getColumnName() + "), 0) as " + parameter.getParamterName() + ", "); break;
			default:				throw new IllegalArgumentException("Parameter is not defined");
			}
		}
		
		return new String(buffer.deleteCharAt(buffer.length() - 2));
	}
}
