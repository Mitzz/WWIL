package com.enercon.model.parameter.eb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

public enum EbParameter {
	
//	Description, OperationType, Mp_Id, ColumnName, Alias
	KWHEXPORT("KWHEXPORT", "SUM", "0808000010", "N_ACTUAL_VALUE", "KWHEXPORT"),
	KWHIMPORT("KWHIMPORT", "SUM", "0808000011", "N_ACTUAL_VALUE", "KWHIMPORT"),
	EXP_IMP_DIFF("EXP_IMP_DIFF", null, null, null, "DIF"),
	REMARK("REMARK", "MAX", null, "ER.S_REMARKS", "Remark");
	private static Logger logger = Logger.getLogger(EbParameter.class);
	
	private String description;
	private String operationType;
	private String mpId;
	private String columnName;
	private String alias;
	
	private EbParameter(String description) {
		this.description = description;
	}
	
	private EbParameter(String description, String operationType, String mpId, String columnName, String alias) {
		this(description);
		this.operationType = operationType;
		this.mpId = mpId;
		this.columnName = columnName;
		this.alias = alias;
	}
	
	public static String getQuery(Collection<EbParameter> ebParameters){
		StringBuilder builder = new StringBuilder();
		
		for(EbParameter parameter: ebParameters){
			switch (parameter) {
			case KWHEXPORT:
			case KWHIMPORT:
				builder.append(getSQL(parameter));
				break;
			case REMARK: builder.append(String.format("%s(%s) as %s, ", parameter.getOperationType(), parameter.getColumnName(), parameter.getAlias()));break;
			default: 
				logger.error("EB Parameter '" + parameter.getDescription() + "' not defined");
				throw new IllegalArgumentException("EB Parameter not defined");
			}
		}
		
		return new String(builder.deleteCharAt(builder.length() - 2));
		
	}
	
	public static void main(String[] args) {
		
		List<EbParameter> ebParameters = new ArrayList<EbParameter>();
		ebParameters.add(EbParameter.KWHEXPORT);
		ebParameters.add(EbParameter.KWHIMPORT);
		
		String query = getQuery(ebParameters);
		
		logger.debug("Generated Query: " + query);
		
	}
	
	private static String getSQL(EbParameter ebParameter){
		return String.format("%s( DECODE( er.S_MP_ID, '%s', er.%s, 0 ) ) %s, ", ebParameter.getOperationType(), ebParameter.getMpId(), ebParameter.getColumnName(), ebParameter.getAlias());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getMpId() {
		return mpId;
	}

	public void setMpId(String mpId) {
		this.mpId = mpId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
	
}
