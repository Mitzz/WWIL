package com.enercon.model.report;

import com.enercon.global.utility.TimeUtility;
import com.enercon.model.summaryreport.Parameter;

public class WecParameterVo implements IWecParameterVo {
	private int size;
	
	private long generation;
	private long operatingHour;
	private long fm;
	private long totalGeneration;
	private long totalOperatingHour;
	private long mf;
	private long ms;
	private long gif;
	private long gis;
	private long gef;
	private long ges;
	private long e1f;
	private long e1s;
	private long e2f;
	private long e2s;
	private long e3f;
	private long e3s;
	private long ebLoad;
	private long lullHour;
	private long omnp;
	private long gtc;
	private long revenue;
	private long lrLoss;
	private long fmLoss;
	
	private double ma;
	private double ga;
	private double ws;
	private double gea;
	private double sa;
	private double ra;
	private double cf;
	private double gia;
	private double mia;
	
	private String remark;
	
	public long generation() {
		return generation;
	}

	public void generation(long value) {
		generation = value;
	}

	public long operatingHour() {
		return operatingHour;
	}

	public void operatingHour(long value) {
		operatingHour = value;	
	}

	public long mf() {
		return mf;
	}

	public void mf(long value) {
		mf = value;
	}

	public long ms() {
		return ms;
	}

	public void ms(long value) {
		this.ms = value;
	}

	public long gif() {
		return gif;
	}

	public void gif(long value) {
		gif = value;
	}

	public long gis() {
		return gis;
	}

	public void gis(long value) {
		gis = value;
	}

	public long gef() {
		return gef;
	}

	public void gef(long value) {
		gef = value;
	}

	public long ges() {
		return ges;
	}

	public void ges(long value) {
		ges = value;
	}

	public long e1f() {
		return e1f;
	}

	public void e1f(long value) {
		e1f = value;
	}

	public long e1s() {
		return e1s;
	}

	public void e1s(long value) {
		e1s = value;
	}

	public long e2f() {
		return e2f;
	}

	public void e2f(long value) {
		e2f = value;
	}

	public long e2s() {
		return e2s;
	}

	public void e2s(long value) {
		e2s = value;
	}

	public long e3f() {
		return e3f;
	}

	public void e3f(long value) {
		e3f = value;
	}

	public long e3s() {
		return e3s;
	}

	public void e3s(long value) {
		e3s = value;
	}

	public long ebLoad() {
		return ebLoad;
	}

	public long lullHour() {
		return lullHour;
	}

	public void lullHour(long value) {
		lullHour = value;
	}

	public long omnp() {
		return omnp;
	}

	public void omnp(long value) {
		this.omnp = value;
	}

	public long gtc() {
		return gtc;
	}

	public void gtc(long value) {
		gtc = value;
	}

	public double gea() {
		return gea;
	}

	public void gea(double value) {
		gea = value;
	}

	public double sa() {
		return sa;
	}

	public void sa(double value) {
		sa = value;
	}

	public double ra() {
		return ra;
	}

	public void ra(double value) {
		ra = value;
	}

	public double cf() {
		return cf;
	}

	public void cf(double value) {
		cf = value;
	}

	public double gia() {
		return gia;
	}

	public void gia(double value) {
		gia = value;
	}

	public double mia() {
		return mia;
	}

	public void mia(double value) {
		mia = value;
	}

	public double ma() {
		return ma;
	}

	public void ma(double value) {
		ma = value;
	}

	public double ga() {
		return ga;
	}

	public void ga(double value) {
		ga = value;
	}

	@Override
	public String toString() {
		return "WecParameterVo [generation=" + generation + ", operatingHour="
				+ operatingHour + ", revenue=" + revenue + ", lrLoss=" + lrLoss
				+ ", fmLoss=" + fmLoss + ", ma=" + ma + ", ga=" + ga + ", cf="
				+ cf + ", remark=" + remark + "]";
	}

	public long fm() {
		return fm;
	}

	public void fm(long value) {
		fm = value;
	}

	public double ws() {
		return ws;
	}

	public void ws(double value) {
		ws = value;
	}
	
	private static Double getHourFormat(long value){
		return Double.parseDouble(TimeUtility.convertMinutesToTimeStringFormat(value, "."));
	}

	public Object value(Parameter parameter) {

		switch (parameter) {
			case GENERATION: return generation();
			case OPERATING_HOUR: return getHourFormat(operatingHour());
			case TOTAL_GENERATION:return totalGeneration();
			case TOTAL_OPERATING_HOUR:return totalOperatingHour()/60;
			case FM:return getHourFormat(fm());
			case MF:return mf();
			case MS:return ms();
			case GIF:return gif();
			case GIS:return gis();
			case GEF:return gef();
			case GES:return ges();
			case E1F:return e1f();
			case E1S:return e1s();
			case E2F:return e2f();
			case E2S:return e2s();
			case E3F:return e3f();
			case E3S:return e3s();
			case EB_LOAD: return ebLoad();
			case LULL_HOUR: return lullHour()/60;
			case OMNP:return omnp();
			case GTC: return gtc();
			
			case MA: return ma();
			case GA: return ga();
			case WS: return ws();
			case GEA: return gea();
			case SA: return sa();
			case RA: return ra();
			case CF: return cf();
			case GIA:return gia();
			case MIA:return mia();
			
			default: throw new IllegalArgumentException("Parameter not defined");
		}
	}

	public void value(Parameter parameter, Object value) {
		switch (parameter) {
			case GENERATION: generation(((Number)value).longValue());break;
			case OPERATING_HOUR: operatingHour(((Number)value).longValue());break;
			case MA: ma(((Number)value).doubleValue());break;
			case GA: ga(((Number)value).doubleValue());break;
			case WS: ws(((Number)value).doubleValue());break;
			case FM: fm(((Number)value).longValue());break;
			case LULL_HOUR: lullHour(((Number)value).longValue());break;
			case TOTAL_GENERATION: totalGeneration(((Number)value).longValue());break;
			case TOTAL_OPERATING_HOUR: totalOperatingHour(((Number)value).longValue());break;
			case MF: mf(((Number)value).longValue());break;
			case MS: ms(((Number)value).longValue());break;
			case GIF: gif(((Number)value).longValue());break;
			case GIS: gis(((Number)value).longValue());break;
			case GEF: gef(((Number)value).longValue());break;
			case GES: ges(((Number)value).longValue());break;
			case E1F: e1f(((Number)value).longValue());break;
			case E1S: e1s(((Number)value).longValue());break;
			case E2F: e2f(((Number)value).longValue());break;
			case E2S: e2s(((Number)value).longValue());break;
			case E3F: e3f(((Number)value).longValue());break;
			case E3S: e3s(((Number)value).longValue());break;
			case OMNP: omnp(((Number)value).longValue());break;
			case GTC:  gtc(((Number)value).longValue());break;
			case REVENUE:  revenue(((Number)value).longValue());break;
			case LR_LOSS:  lrLoss(((Number)value).longValue());break;
			case FM_LOSS:  fmLoss(((Number)value).longValue());break;
			
			case GEA:  gea(((Number)value).doubleValue());break;
			case SA:  sa(((Number)value).doubleValue());break;
			case RA:  ra(((Number)value).doubleValue());break;
			case CF:  cf(((Number)value).doubleValue());break;
			case GIA: gia(((Number)value).doubleValue());break;
			case MIA: mia(((Number)value).doubleValue());break;
			
			case REMARK: remark(value.toString());break;
			
			default: throw new IllegalArgumentException("Parameter not defined");
			
		}
	}

	public IWecParameterVo add(IWecParameterVo that) {
		generation(generation + that.generation());
		operatingHour(operatingHour + that.operatingHour());
		fm(fm + that.fm());
		ma(((ma * size) + (that.ma() * that.size()))/(size + that.size()));
		ga(((ga * size) + (that.ga() * that.size()))/(size + that.size()));
		ws(ws + that.ws());
		lullHour(lullHour + that.lullHour());
		totalGeneration(totalGeneration + that.totalGeneration());
		totalOperatingHour(totalOperatingHour + that.totalOperatingHour());
		mf(mf + that.mf());
		ms(ms + that.ms());
		gif(gif + that.gif());
		gis(gis + that.gis());
		gef(gef + that.gef());
		ges(ges + that.ges());
		e1f(e1f + that.e1f());
		e1s(e1s + that.e1s());
		e2f(e2f + that.e2f());
		e2s(e2s + that.e2s());
		e3f(e3f + that.e3f());
		e3s(e3s + that.e3s());
		omnp(omnp + that.omnp());
		gtc(gtc + that.gtc());
		revenue(revenue + that.revenue());
		lrLoss(lrLoss + that.lrLoss());
		fmLoss(fmLoss + that.fmLoss());
		
		gea(((ma * size) + (that.ma() * that.size()))/(size + that.size()));
		sa(((ma * size) + (that.ma() * that.size()))/(size + that.size()));
		ra(((ma * size) + (that.ma() * that.size()))/(size + that.size()));
		cf(((ma * size) + (that.ma() * that.size()))/(size + that.size()));
		gia(((ma * size) + (that.ma() * that.size()))/(size + that.size()));
		mia(((ma * size) + (that.ma() * that.size()))/(size + that.size()));
		return this;
	}

	public long totalGeneration() {
		return totalGeneration;
	}

	public void totalGeneration(long value) {
		totalGeneration = value;
	}

	public long totalOperatingHour() {
		return totalOperatingHour;
	}

	public void totalOperatingHour(long value) {
		totalOperatingHour = value;
	}

	public void ebLoad(long value) {
		ebLoad = value;
	}

	public long revenue() {
		return revenue;
	}

	public void revenue(long value) {
		this.revenue = value;
	}

	public long lrLoss() {
		return lrLoss;
	}

	public void lrLoss(long value) {
		lrLoss = value;
	}

	public long fmLoss() {
		return fmLoss;
	}

	public void fmLoss(long value) {
		fmLoss = value;
	}

	public String remark() {
		return remark;
	}

	public void remark(String value) {
		this.remark = value;
	}

	public int size() {
		return size;
	}

	public void size(int size) {
		this.size = size;
	}
	
}
