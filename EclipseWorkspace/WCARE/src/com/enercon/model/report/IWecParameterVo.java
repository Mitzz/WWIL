package com.enercon.model.report;

import com.enercon.model.summaryreport.Parameter;

public interface IWecParameterVo {
	
	int size();
	void size(int size);

	long generation();
	void generation(long value);
	
	long operatingHour();
	void operatingHour(long value);
	
	long fm();
	void fm(long value);
	
	long totalGeneration();
	void totalGeneration(long value);
	
	long totalOperatingHour();
	void totalOperatingHour(long value);
	
	long mf();
	void mf(long value);
	
	long ms();
	void ms(long value);
	
	long gif();
	void gif(long value);
	
	long gis();
	void gis(long value);
	
	long gef();
	void gef(long value);
	
	long ges();
	void ges(long value);
	
	long e1f();
	void e1f(long value);
	
	long e1s();
	void e1s(long value);
	
	long e2f();
	void e2f(long value);
	
	long e2s();
	void e2s(long value);
	
	long e3f();
	void e3f(long value);
	
	long e3s();
	void e3s(long value);
	
	long ebLoad();
	void ebLoad(long value);
	
	long lullHour();
	void lullHour(long value);
	
	long omnp();
	void omnp(long value);
	
	long gtc();
	void gtc(long value);
	
	long revenue();
	void revenue(long value);
	
	long lrLoss();
	void lrLoss(long value);
	
	long fmLoss();
	void fmLoss(long value);
	
	double ma();
	void ma(double value);
	
	double ga();
	void ga(double value);
	
	double ws();
	void ws(double value);
	
	double gea();
	void gea(double value);
	
	double sa();
	void sa(double value);
	
	double ra();
	void ra(double value);
	
	double cf();
	void cf(double value);
	
	double gia();
	void gia(double value);
	
	String remark();
	void remark(String value);
	
	Object value(Parameter parameter);
	void value(Parameter parameter, Object value);
	
	IWecParameterVo add(IWecParameterVo that);
}
