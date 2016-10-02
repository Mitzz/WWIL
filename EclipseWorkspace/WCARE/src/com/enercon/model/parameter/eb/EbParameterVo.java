package com.enercon.model.parameter.eb;

import org.apache.log4j.Logger;

import com.enercon.global.utility.NumberUtility;

public class EbParameterVo implements IEbParameterVo{
	private static Logger logger = Logger.getLogger(EbParameterVo.class);
	
	private int size;
	private double kwhExport;
	private double kwhImport;
	private String remark;
	
	public void value(EbParameter parameter, Object value) {
		switch (parameter) {
			case KWHEXPORT: kwhExport(getDouble(value)); break;
			case KWHIMPORT: kwhImport(getDouble(value)); break;
			case REMARK: remark(value == null ? null : value.toString()); break;
			default: 
				logger.error("EB Parameter '" + parameter.getDescription() + "' not defined");
				throw new IllegalArgumentException("EB Parameter not defined");
		
		}
	}

	public Object value(EbParameter parameter) {
		switch (parameter) {
			case KWHEXPORT: return kwhExport();
			case KWHIMPORT: return kwhImport();	
			case REMARK: return remark();
			default: 
				logger.error("EB Parameter '" + parameter.getDescription() + "' not defined");
				throw new IllegalArgumentException("EB Parameter not defined");
		}
	}

	public IEbParameterVo add(IEbParameterVo that) {
		if(that == null) throw new NullPointerException("Adding a EbParameterVo whose value is 'null'");
		kwhExport(kwhExport + that.kwhExport());
		kwhImport(kwhExport + that.kwhImport());

		//Important
		size(size + that.size());
		return this;
	}

	public int size() {
		return size;
	}

	public void size(int size) {
		this.size = size;
	}

	public void kwhExport(double value) {
		this.kwhExport = value;
	}

	public double kwhExport() {
		return kwhExport;
	}
	
	public double getKwhExport() {
		return kwhExport();
	}
	
	public double exportImportDiff() {
		return kwhExport() - kwhImport();
	}
	
	public double getExportImportDiff() {
		return kwhExport() - kwhImport();
	}

	public void kwhImport(double value) {
		kwhImport = value;
	}

	public double kwhImport() {
		return kwhImport;
	}
	
	public double getKwhImport() {
		return kwhImport();
	}
	
	private double getDouble(Object value){
		return NumberUtility.getDouble(value);
	}
	
	

	/*@Override
	public String toString() {
		return "EbParameterVo [size=" + size + ", kwhExport=" + kwhExport + ", kwhImport=" + kwhImport + ", exportImportDiff=" + exportImportDiff() + "]";
	}*/
	
	

	public void remark(String value) {
		remark = value; 
	}

	@Override
	public String toString() {
		return "EbParameterVo [remark=" + remark + "]";
	}

	public String remark() {
		return remark;
	}

	public String getRemark() {
		return remark();
	}
	
}
