package com.enercon.model.parameter.wec;

import org.apache.log4j.Logger;

import com.enercon.global.utility.NumberUtility;
import com.enercon.global.utility.TimeUtility;

public class WecParameterVo implements IWecParameterVo {
	private final static Logger logger = Logger.getLogger(WecParameterVo.class);
	private final static int ROUND_OFF = 2;
	
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
	private long averageGeneration;
	private long averageOperatingHour;
	
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
	private int publish;

	private double averageGenerationPerWec;
	private double averageOperatingHourPerWec;
	
	public long generation() {
		return generation;
	}
	
	public long getGeneration() {
		return generation();
	}

	public void generation(long value) {
		generation = value;
	}

	public long operatingHour() {
		return operatingHour;
	}
	
	public long getOperatingHour() {
		return operatingHour();
	}

	public void operatingHour(long value) {
		operatingHour = value;	
	}

	public long mf() {
		return mf;
	}
	
	public long getMf() {
		return mf();
	}

	public void mf(long value) {
		mf = value;
	}

	public long ms() {
		return ms;
	}
	
	public long getMs() {
		return ms();
	}

	public void ms(long value) {
		this.ms = value;
	}

	public long gif() {
		return gif;
	}
	
	public long getGif() {
		return gif();
	}

	public void gif(long value) {
		gif = value;
	}

	public long gis() {
		return gis;
	}
	
	public long getGis() {
		return gis();
	}

	public void gis(long value) {
		gis = value;
	}

	public long gef() {
		return gef;
	}
	
	public long getGef() {
		return gef();
	}

	public void gef(long value) {
		gef = value;
	}

	public long ges() {
		return ges;
	}
	
	public long getGes() {
		return ges();
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
	
	public long getEbLoad() {
		return ebLoad;
	}

	public long lullHour() {
		return lullHour;
	}
	
	public long getLullHour() {
		return lullHour();
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
	
	public double getCf() {
		return cf();
	}

	public void cf(double value) {
		cf = value;
	}

	public double gia() {
		return gia;
	}
	
	public double getGia() {
		return gia();
	}

	public void gia(double value) {
		gia = value;
	}

	public double mia() {
		return mia;
	}
	
	public double getMia() {
		return mia();
	}

	public void mia(double value) {
		mia = value;
	}

	public double ma() {
		return ma;
	}
	
	public double getMa() {
		return ma();
	}

	public void ma(double value) {
		ma = value;
	}

	public double ga() {
		return ga;
	}
	
	public double getGa() {
		return ga();
	}

	public void ga(double value) {
		ga = value;
	}

	@Override
	public String toString() {
		return (this == null) ? "WecParameterVo is null" : "WecParameterVo [generation=" + generation + ", MA=" + ma + "]";
	}
	
	

	public long fm() {
		return fm;
	}
	
//	@Override
//	public String toString() {
//		return (this == null) ? "WecParameterVo is null" : "WecParameterVo [generation=" + generation + ", lrLoss=" + lrLoss + ", fmLoss=" + fmLoss + "]";
//	}

	public long getFm() {
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
			case AVG_GENERATION: return averageGeneration();
			case AVG_OPERATING_HOUR: return getHourFormat(averageOperatingHour());
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
			case PUBLISH: return publish();
			
			case AVG_GENERATION_PER_WEC: return new Double(averageGenerationPerWec()).longValue();
			case AVG_OPERATING_HOUR_PER_WEC: return getHourFormat(new Double(averageOperatingHourPerWec()).longValue()).longValue();
			
			default: {
				logger.error("Wec Parameter '" + parameter.getParamterName() + "' not defined");
				throw new IllegalArgumentException("Wec Parameter not defined");
			}
		}
	}

	public void value(Parameter parameter, Object value) {
		switch (parameter) {
			case GENERATION: generation(getLong(value));break;
			case OPERATING_HOUR: operatingHour(getLong(value));break;
			case AVG_GENERATION:  averageGeneration(getLong(value));break;
			case AVG_OPERATING_HOUR: averageOperatingHour(getLong(value));break;
			case MA: ma(getDouble(value));break;
			case GA: ga(getDouble(value));break;
			case WS: ws(getDouble(value));break;
			case FM: fm(getLong(value));break;
			case LULL_HOUR: lullHour(getLong(value));break;
			case TOTAL_GENERATION: totalGeneration(getLong(value));break;
			case TOTAL_OPERATING_HOUR: totalOperatingHour(getLong(value));break;
			case MF: mf(getLong(value));break;
			case MS: ms(getLong(value));break;
			case GIF: gif(getLong(value));break;
			case GIS: gis(getLong(value));break;
			case GEF: gef(getLong(value));break;
			case GES: ges(getLong(value));break;
			case E1F: e1f(getLong(value));break;
			case E1S: e1s(getLong(value));break;
			case E2F: e2f(getLong(value));break;
			case E2S: e2s(getLong(value));break;
			case E3F: e3f(getLong(value));break;
			case E3S: e3s(getLong(value));break;
			case EB_LOAD: ebLoad(getLong(value)); break;
			case OMNP: omnp(getLong(value));break;
			case GTC:  gtc(getLong(value));break;
			case REVENUE:  revenue(getLong(value));break;
			case LR_LOSS:  lrLoss(getLong(value));break;
			case FM_LOSS:  fmLoss(getLong(value));break;
			
			case GEA:  gea(getDouble(value));break;
			case SA:  sa(getDouble(value));break;
			case RA:  ra(getDouble(value));break;
			case CF:  cf(getDouble(value));break;
			case GIA: gia(getDouble(value));break;
			case MIA: mia(getDouble(value));break;
			
			case REMARK:remark(value == null ? null : value.toString());break;
			case PUBLISH: publish(getLong(value).intValue());break;
			case AVG_GENERATION_PER_WEC:averageGenerationPerWec(NumberUtility.round(getDouble(value) / size, 1));break;
			case AVG_OPERATING_HOUR_PER_WEC:averageOperatingHourPerWec(NumberUtility.round(getDouble(value) / size, 1));break;
			
			default: {
				logger.error("Wec Parameter '" + parameter.getParamterName() + "' not defined");
				throw new IllegalArgumentException("Wec Parameter not defined");
			}
			
		}
	}
	
	private double getDouble(Object value){
		return NumberUtility.getDouble(value);
	}
	
	private Long getLong(Object value){
		return NumberUtility.getLong(value);
	}

	public IWecParameterVo add(IWecParameterVo that) {
		if(that == null) throw new NullPointerException("Adding a WecParameterVo whose value is null");
		if(that.size() == 0) throw new IllegalArgumentException("Adding(that) a WecParameterVo whose size is 0");
		generation(generation + that.generation());
		operatingHour(operatingHour + that.operatingHour());
		fm(fm + that.fm());
		ma(NumberUtility.round(((ma * size) + (that.ma() * that.size()))/(size + that.size()), ROUND_OFF));
		ga(NumberUtility.round(((ga * size) + (that.ga() * that.size()))/(size + that.size()), ROUND_OFF));
		cf(NumberUtility.round(((cf * size) + (that.cf() * that.size()))/(size + that.size()), ROUND_OFF));
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
		ebLoad(ebLoad + that.ebLoad());
		lrLoss(lrLoss + that.lrLoss());
		fmLoss(fmLoss + that.fmLoss());
		
		gea(((gea * size) + (that.gea() * that.size()))/(size + that.size()));
		sa(((sa * size) + (that.sa() * that.size()))/(size + that.size()));
		ra(((ra * size) + (that.ra() * that.size()))/(size + that.size()));
		
		gia(((gia * size) + (that.gia() * that.size()))/(size + that.size()));
		mia(((mia * size) + (that.mia() * that.size()))/(size + that.size()));
		remark(remark + "::" + that.remark());
		publish(Math.min(publish, that.publish()));
		
		//Important
		size(size + that.size());
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
	
	public String getRemark() {
		return remark();
	}

	public void remark(String value) {
		this.remark = value;
	}

	public int size() {
		return size;
	}
	
	public int getSize() {
		return size();
	}

	public void size(int size) {
		this.size = size;
	}

	public long averageGeneration() {
		return averageGeneration;
	}

	public void averageGeneration(long value) {
		averageGeneration = value;
	}

	public long averageOperatingHour() {
		return averageOperatingHour;
	}

	public void averageOperatingHour(long value) {
		averageOperatingHour = value;
	}

	public double averageGenerationPerWec() {
		return averageGenerationPerWec;
	}

	public void averageGenerationPerWec(double value) {
		averageGenerationPerWec = value;
	}

	public double averageOperatingHourPerWec() {
		return averageOperatingHourPerWec;
	}

	public void averageOperatingHourPerWec(double value) {
		averageOperatingHourPerWec = value;
	}

	public int publish() {
		return publish;
	}

	public void publish(int value) {
		publish = value;
	}

	public boolean isLullHourDash() {
		long faultHour = omnp + e1f + e2f + e3f + e1s + e2s + e3s + fm + ebLoad + gis + gif + ms + mf;
		if(operatingHour == 0 && faultHour < 4 * 60) return true;
		return false;
	}

	public boolean isPublish() {
		return publish == 1;
	}
	
}
