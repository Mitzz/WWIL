package com.enercon.model.report;

import com.enercon.model.summaryreport.Parameter;

public interface ParameterVo {

	long generation();
	void generation(long value);
	
	long operatingHour();
	void operatingHour(long value);
	
	long fm();
	void fm(long value);
	
	double ma();
	void ma(double ma);
	
	double ga();
	void ga(double value);
	
	double ws();
	void ws(double value);
	
	Object getValue(Parameter parameter);
}
