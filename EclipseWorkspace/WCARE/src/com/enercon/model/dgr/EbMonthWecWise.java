package com.enercon.model.dgr;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.enercon.model.comparator.EbMasterVoComparator;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.utility.EbDetails;
import com.enercon.service.WecMasterService;

public class EbMonthWecWise {

	private IEbMasterVo eb;
	private ICustomerMasterVo customer;
	private EbDetails details;
	private List<EbMonthView> view;
	
	private Set<IWecMasterVo> wecs;
	
	public final static Comparator<EbMonthWecWise> BY_EB_NAME = new Comparator<EbMonthWecWise>(){

		public int compare(EbMonthWecWise o1, EbMonthWecWise o2) {
			return EbMasterVoComparator.sortByEb(o1.getEb(), o2.getEb());
		}
		
	};

	public EbMonthWecWise() {
	}
	
	public EbMonthWecWise populateWecs(){
		wecs = new HashSet<IWecMasterVo>(WecMasterService.getInstance().getDisplayActive(eb, customer));
		return this;
	}

	public IEbMasterVo getEb() {
		return eb;
	}

	public void setEb(IEbMasterVo eb) {
		this.eb = eb;
	}

	public EbDetails getDetails() {
		return details;
	}

	public void setDetails(EbDetails details) {
		this.details = details;
	}

	public List<EbMonthView> getView() {
		return view;
	}
	
	public String getTypeCount(){
		return details.getWecCountType();
	}
	
	public double getCapacity(){
		return details.getCapacity();
	}

	public void setView(List<EbMonthView> view) {
		this.view = view;
	}

	public ICustomerMasterVo getCustomer() {
		return customer;
	}

	public void setCustomer(ICustomerMasterVo customer) {
		this.customer = customer;
	}

	public Set<IWecMasterVo> getWecs() {
		return wecs;
	}

	public void setWecs(Set<IWecMasterVo> wecs) {
		this.wecs = wecs;
	}

	public EbMonthWecWise populateEbDetails(){
		details = new EbDetails(eb);
		return this;
	}
	
	public EbMonthWecWise sortView(){
		Collections.sort(view, EbMonthView.BY_DATE);
		return this;
	}
}
