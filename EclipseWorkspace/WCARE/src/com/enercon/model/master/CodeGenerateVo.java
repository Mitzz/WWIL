package com.enercon.model.master;

import org.apache.log4j.Logger;

public class CodeGenerateVo {
	
	private final static Logger logger = Logger.getLogger(CodeGenerateVo.class);

	private String tableName;
	private String sequenceCode;
	private int sequenceNo;
	
	private CodeGenerateVo(CodeGenerateVoBuilder builder) {
		
		this.tableName = builder.tableName;
		this.sequenceCode = builder.sequenceCode;
		this.sequenceNo = builder.sequenceNo;
	}
	
	public String id(){
		String sequence = Integer.toString(sequenceNo);
		sequence = padLeft(sequence, 6, '0');
	    return sequenceCode + sequence;
	}
	
	public static String padLeft(String s, int n, char c) {
	    return String.format("%1$" + n + "s", s).replace(' ', c);  
	}

	@Override
	public String toString() {
		return "CodeGenerateVo [tableName=" + tableName + ", sequenceCode="
				+ sequenceCode + ", sequenceNo=" + sequenceNo + "]";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSequenceCode() {
		return sequenceCode;
	}

	public void setSequenceCode(String sequenceCode) {
		this.sequenceCode = sequenceCode;
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public static class CodeGenerateVoBuilder {
		private String tableName;
		private String sequenceCode;
		private int sequenceNo;

		public CodeGenerateVoBuilder(String tableName) {
			this.tableName = tableName;
		}
		
		public CodeGenerateVoBuilder sequenceCode(String sequenceCode) {
			this.sequenceCode = sequenceCode;
			return this;
		}
		
		public CodeGenerateVoBuilder sequenceNo(int sequenceNo) {
			this.sequenceNo = sequenceNo;
			return this;
		}

		public CodeGenerateVo build() {
			CodeGenerateVo vo = new CodeGenerateVo(this);
			return vo;
		}

	}

}
