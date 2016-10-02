package com.enercon.model.parameter.eb;


public interface IEbParameterVo {

	int size();
	void size(int size);
	
	void kwhExport(double value);
	double kwhExport();
	
	void kwhImport(double value);
	double kwhImport();
	
	double exportImportDiff();
	
	void remark(String value);
	String remark();
	
	Object value(EbParameter parameter);
	void value(EbParameter parameter, Object value);
	
	IEbParameterVo add(IEbParameterVo that);
}
