package com.enercon.model.report;


public abstract class ReportAbstract implements Report{
	
	protected long generation = 0  ;
	protected long operatingHour =  0 ;
	protected long lullHours =  0 ;
	protected double mavial =  0.0 ;
	protected double gavial =  0.0 ;
	protected double capacityFactor =  0.0 ;
	protected double giavail =  0.0 ;
	protected double miavail =  0.0 ;
	protected int trialRun =  0 ;
	protected long machineFault = 0  ;
	protected long machineShutdown =  0 ;
	protected long loadRestriction =  0 ;
	protected long internalFault = 0  ;
	protected long internalShutdown =  0 ;
	protected long externalGridFault = 0  ;
	protected long externalGridShutdown =  0 ;
	protected long wecSpecialShutdown = 0 ;
	
	public ReportAbstract() {}
	
	public ReportAbstract(long generation, long operatingHour,
			long lullHours,
			double mavial, double gavial, double capacityFactor,
			double giavail, double miavail, int trialRun, long machineFault,
			long machineShutdown, long loadRestriction, long internalFault,
			long internalShutdown, long externalGridFault,
			long externalGridShutdown, long wecSpecialShutdown) {
		super();
		this.generation = generation;
		this.operatingHour = operatingHour;
		this.lullHours = lullHours;
		this.mavial = mavial;
		this.gavial = gavial;
		this.capacityFactor = capacityFactor;
		this.giavail = giavail;
		this.miavail = miavail;
		this.trialRun = trialRun;
		this.machineFault = machineFault;
		this.machineShutdown = machineShutdown;
		this.loadRestriction = loadRestriction;
		this.internalFault = internalFault;
		this.internalShutdown = internalShutdown;
		this.externalGridFault = externalGridFault;
		this.externalGridShutdown = externalGridShutdown;
		this.wecSpecialShutdown = wecSpecialShutdown;
	}

	public long getGeneration() {
		return generation;
	}

	public void setGeneration(long generation) {
		this.generation = generation;
	}


	public long getOperatingHour() {
		return operatingHour;
	}

	public void setOperatingHour(long operatingHour) {
		this.operatingHour = operatingHour;
	}

	public long getLullHours() {
		return lullHours;
	}

	public void setLullHours(long lullHours) {
		this.lullHours = lullHours;
	}

	public double getMavial() {
		return mavial;
	}

	public void setMavial(double mavial) {
		this.mavial = mavial;
	}

	public double getGavial() {
		return gavial;
	}

	public void setGavial(double gavial) {
		this.gavial = gavial;
	}

	public double getCapacityFactor() {
		return capacityFactor;
	}

	public void setCapacityFactor(double capacityFactor) {
		this.capacityFactor = capacityFactor;
	}

	public double getGiavail() {
		return giavail;
	}

	public void setGiavail(double giavail) {
		this.giavail = giavail;
	}

	public double getMiavail() {
		return miavail;
	}

	public void setMiavail(double miavail) {
		this.miavail = miavail;
	}

	public int getTrialRun() {
		return trialRun;
	}

	public void setTrialRun(int trialRun) {
		this.trialRun = trialRun;
	}

	public long getMachineFault() {
		return machineFault;
	}

	public void setMachineFault(long machineFault) {
		this.machineFault = machineFault;
	}

	public long getMachineShutdown() {
		return machineShutdown;
	}

	public void setMachineShutdown(long machineShutdown) {
		this.machineShutdown = machineShutdown;
	}

	public long getLoadRestriction() {
		return loadRestriction;
	}

	public void setLoadRestriction(long loadRestriction) {
		this.loadRestriction = loadRestriction;
	}

	public long getInternalFault() {
		return internalFault;
	}

	public void setInternalFault(long internalFault) {
		this.internalFault = internalFault;
	}

	public long getInternalShutdown() {
		return internalShutdown;
	}

	public void setInternalShutdown(long internalShutdown) {
		this.internalShutdown = internalShutdown;
	}

	public long getExternalGridFault() {
		return externalGridFault;
	}

	public void setExternalGridFault(long externalGridFault) {
		this.externalGridFault = externalGridFault;
	}

	public long getExternalGridShutdown() {
		return externalGridShutdown;
	}

	public void setExternalGridShutdown(long externalGridShutdown) {
		this.externalGridShutdown = externalGridShutdown;
	}

	public long getWecSpecialShutdown() {
		return wecSpecialShutdown;
	}

	public void setWecSpecialShutdown(long wecSpecialShutdown) {
		this.wecSpecialShutdown = wecSpecialShutdown;
	}

}