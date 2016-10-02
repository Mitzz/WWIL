package com.enercon.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.event.CustomerMasterEvent;
import com.enercon.model.graph.event.PlantMasterEvent;
import com.enercon.model.graph.event.WecMasterEvent;
import com.enercon.model.master.FederMasterVo;
import com.enercon.model.master.MasterVo;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("siteMasterVo")
public class SiteMasterVo extends CommonFormVo implements ISiteMasterVo, Serializable, Comparable<SiteMasterVo> ,Cloneable{
	
	private static final long serialVersionUID = 3778891231187711816L;

	private static Logger logger = Logger.getLogger(SiteMasterVo.class);
	
	private String id;
	private String name;
	private String code;
	private String incharge;
	private String address;
	private String stateId;
	private String areaId;
	private String remark;
	
	private IStateMasterVo state;
	private IAreaMasterVo area;
	
	private List<IEbMasterVo> ebs = new ArrayList<IEbMasterVo>();
	private List<IWecMasterVo> wecs;
	private List<IPlantMasterVo> plants;
	private List<ICustomerMasterVo> customers;
	private List<FederMasterVo> feders = new ArrayList<FederMasterVo>();

	private boolean wecStale;
	private boolean customerStale;
	private boolean plantStale;
	
	public SiteMasterVo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public SiteMasterVo() {
		super();
	}
	
	
	public ISiteMasterVo setArea(IAreaMasterVo area) {
		this.area = area;
		area.addSite(this);
		return this;
	}

	
	public IAreaMasterVo getArea() {
		return area;
	}
	
	
	public IStateMasterVo getState() {
		//logger.debug(this);
		if(state == null)
			state = MasterUtility.getStateByArea(getArea());
		return state;
	}
	
	
	public ISiteMasterVo addEb(IEbMasterVo eb) {
		ebs.add(eb);
		return this;
	}
	
	
	public List<IEbMasterVo> getEbs() {
		return ebs;
	}

	
	public List<IWecMasterVo> getWecs() {
		if(wecs == null || wecStale){
			wecs = MasterUtility.getWecsByEbs(getEbs());
			wecStale = false;
		}
		return wecs;
	}

	
	public List<IPlantMasterVo> getPlants() {
		if(plants == null || plantStale){
			plants = MasterUtility.getPlantsByWecs(getWecs());
			plantStale = false;
		}
		
		return plants;
	}

	
	public List<ICustomerMasterVo> getCustomers() {
		if(customers == null || customerStale){
			customers = MasterUtility.getCustomersByWecs(getWecs());
			customerStale = false;
		}
		return customers;
	}

	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SiteMasterVo other = (SiteMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIncharge() {
		return incharge;
	}

	public void setIncharge(String incharge) {
		this.incharge = incharge;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Override
	public String toString() {
		return "SiteMasterVo [name=" + name + "]";
	}

	public int compareTo(SiteMasterVo that) {
		if(this.id.compareTo(that.getId()) < 0) return -1;
		if(this.id.compareTo(that.getId()) > 0) return +1;
		return 0;
	}

	public SiteMasterVo(SiteMasterVoBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.code = builder.code;
		this.incharge = builder.incharge;
		this.address = builder.address;
		this.stateId = builder.stateId;
		this.areaId = builder.areaId;		
		this.createdBy = builder.getCreatedBy();
		this.createdAt = builder.getCreatedAt();
		this.modifiedBy = builder.getModifiedBy();
		this.modifiedAt = builder.getModifiedAt();
	}
	
	public static class SiteMasterVoBuilder extends MasterVo {

		private String id;
		private String name;
		private String code;
		private String incharge;
		private String address;
		private String stateId;
		private String areaId;

		public SiteMasterVoBuilder id(String id) {
			this.id = id;
			return this;
		}
		public SiteMasterVoBuilder name(String name) {
			this.name = name;
			return this;
		}
		public SiteMasterVoBuilder code(String code) {
			this.code = code;
			return this;
		}
		public SiteMasterVoBuilder incharge(String incharge) {
			this.incharge = incharge;
			return this;
		}
		public SiteMasterVoBuilder address(String address) {
			this.address = address;
			return this;
		}
		public SiteMasterVoBuilder stateId(String stateId) {
			this.stateId = stateId;
			return this;
		}
		public SiteMasterVoBuilder areaId(String areaId) {
			this.areaId = areaId;
			return this;
		}
		public SiteMasterVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}
		public SiteMasterVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}
		public SiteMasterVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public SiteMasterVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}

		public SiteMasterVo build() {
			SiteMasterVo vo = new SiteMasterVo(this);
			return vo;
		}
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setId(null);
		setName(null);
		setCode(null);
		setIncharge(null);
		setAddress(null);
		setStateId(null);
		setAreaId(null);
	}

	public List<FederMasterVo> getFeders() {
		return feders;
	}

	public void setFeders(List<FederMasterVo> feders) {
		this.feders = feders;
	}
	
	public void addFeder(FederMasterVo feder){
		feders.add(feder);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void handler(WecMasterEvent event) {
		if(event.isCreate()) wecStale = true;
	}

	public void handler(CustomerMasterEvent event) {
		if(event.isCreate()) customerStale = true;
	}

	public void handler(PlantMasterEvent event) {
		if(event.isCreate()) plantStale = true;
	}
}
