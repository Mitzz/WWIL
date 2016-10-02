package com.enercon.model.graph;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.model.master.MasterVo;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;
@JsonFilter("wecMasterVo")
public class WecMasterVo extends CommonFormVo implements IWecMasterVo, Serializable, Comparable<WecMasterVo>{
	
	private static final long serialVersionUID = 8905058216029508620L;
	private String id;
	private String name;
	private String scadaStatus;
	private String status;
	private Double capacity;
	private String technicalNo;
	private String commissionDate;
	private String type;
	private String foundationNo;
	private String show;
	private String remark;
	private String transferDate;
	private String transferWecId;
	private String startDate;
	private String endDate;
	private String multiFactor;
	private String genComm;
	private Double costPerUnit;
	private String machineAvailability;
	private String extGridAvailability;
	private String intGridAvailability;
	private String formula;
	private String customerId;
	private String ebId;
	private String feederId;
	private String guaranteeType;
	private String customerType;
	
	private IPlantMasterVo plant;
	private ICustomerMasterVo customer;
	private IEbMasterVo eb;

	private ISiteMasterVo site;
	private IAreaMasterVo area;
	private IStateMasterVo state;
	
	public WecMasterVo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public WecMasterVo() {
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
	
	public IPlantMasterVo getPlant() {
		return plant;
	}
	
	public IWecMasterVo setPlant(IPlantMasterVo plant) {
		if(plant != null){
			this.plant = plant;
			plant.setWec(this);
		}
		return this;
	}
	
	public IEbMasterVo getEb() {
		return eb;
	}
	
	public IWecMasterVo setEb(IEbMasterVo eb) {
		eb.addWec(this);
		this.eb = eb;
		return this;
	}
	
	public ISiteMasterVo getSite() {
		if(site == null)
			site = MasterUtility.getSiteByEb(getEb());
		return site;
	}
	
	public IStateMasterVo getState() {
		if(state == null)
			state = MasterUtility.getStateByArea(getArea());
		return state;
	}

	public ICustomerMasterVo getCustomer() {
		return customer;
	}
	
	public IWecMasterVo setCustomer(ICustomerMasterVo customer) {
		this.customer = customer;
		customer.addWec(this);
		return this;
	}

	public String getScadaStatus() {
		return scadaStatus;
	}

	public void setScadaStatus(String scadaStatus) {
		this.scadaStatus = scadaStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	
	public IAreaMasterVo getArea() {
		if(area == null)
			area = MasterUtility.getAreaBySite(getSite());
		return area;
	}

	public String getTechnicalNo() {
		return technicalNo;
	}

	public void setTechnicalNo(String technicalNo) {
		this.technicalNo = technicalNo;
	}

	@Override
	public String toString() {
		return "WecMasterVo [id=" + id + ", name=" + name + "]";
	}

	public String getCommissionDate() {
		return commissionDate;
	}

	public void setCommissionDate(String commissionDate) {
		this.commissionDate = commissionDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFoundationNo() {
		return foundationNo;
	}

	public void setFoundationNo(String foundationNo) {
		this.foundationNo = foundationNo;
	}
	
	public void setShow(String show){
		this.show = show;
	}

	public String getShow() {
		return show;
	}

	public int compareTo(WecMasterVo that) {
		if(this.id.compareTo(that.getId()) < 0) return -1;
		if(this.id.compareTo(that.getId()) > 0) return +1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WecMasterVo other = (WecMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public String getTransferWecId() {
		return transferWecId;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public void setTransferWecId(String transferWecId) {
		this.transferWecId = transferWecId;
	}


public WecMasterVo(WecMasterBuilder builder){
		
		this.id = builder.id;
		this.name = builder.name;
		this.scadaStatus = builder.scadaStatus;
		this.status = builder.status;
		
		this.capacity = builder.capacity;
		this.technicalNo = builder.technicalNo;
		this.commissionDate = builder.commissionDate;
		this.type = builder.type;
		
		this.foundationNo = builder.foundationNo;
		this.show = builder.show;
		this.remark = builder.remark;
		this.transferDate = builder.transferDate;
		
		this.transferWecId = builder.transferWecId;
		this.setMultiFactor(builder.multiFactor);
		this.setGenComm(builder.genComm);
		this.setCostPerUnit(builder.costPerUnit);
		
		this.setMachineAvailability(builder.machineAvailability);
		this.setExtGridAvailability(builder.extGridAvailability);
		this.setIntGridAvailability(builder.intGridAvailability);
		this.setCustomerId(builder.customerId);
		
		this.setEbId(builder.ebId);
		this.setFeederId(builder.feederId);
		this.setGuaranteeType(builder.guaranteeType);
		this.setCustomerType(builder.customerType);
		
		this.setStartDate(builder.startDate);
		this.setEndDate(builder.endDate);
		this.setFormula(builder.formula);
		this.createdBy = builder.getCreatedBy();
		this.createdAt = builder.getCreatedAt();
		this.modifiedBy = builder.getModifiedBy();
		this.modifiedAt = builder.getModifiedAt();
	}
	 public String getStartDate() {
	return startDate;
}

public void setStartDate(String startDate) {
	this.startDate = startDate;
}
	public String getEndDate() {
	return endDate;
}

public void setEndDate(String endDate) {
	this.endDate = endDate;
}
	public String getMultiFactor() {
	return multiFactor;
}

public void setMultiFactor(String multiFactor) {
	this.multiFactor = multiFactor;
}
	public String getGenComm() {
	return genComm;
}

public void setGenComm(String genComm) {
	this.genComm = genComm;
}
	public Double getCostPerUnit() {
	return costPerUnit;
}

public void setCostPerUnit(Double costPerUnit) {
	this.costPerUnit = costPerUnit;
}
	public String getMachineAvailability() {
	return machineAvailability;
}

public void setMachineAvailability(String machineAvailability) {
	this.machineAvailability = machineAvailability;
}
	public String getExtGridAvailability() {
	return extGridAvailability;
}

public void setExtGridAvailability(String extGridAvailability) {
	this.extGridAvailability = extGridAvailability;
}
	public String getIntGridAvailability() {
	return intGridAvailability;
}

public void setIntGridAvailability(String intGridAvailability) {
	this.intGridAvailability = intGridAvailability;
}
	public String getFormula() {
	return formula;
}

public void setFormula(String formula) {
	this.formula = formula;
}
	public String getCustomerId() {
	return customerId;
}

public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
	public String getEbId() {
	return ebId;
}

public void setEbId(String ebId) {
	this.ebId = ebId;
}
	public String getFeederId() {
	return feederId;
}

public void setFeederId(String feederId) {
	this.feederId = feederId;
}
	public String getGuaranteeType() {
	return guaranteeType;
}

public void setGuaranteeType(String guaranteeType) {
	this.guaranteeType = guaranteeType;
}
	public String getCustomerType() {
	return customerType;
}

public void setCustomerType(String customerType) {
	this.customerType = customerType;
}
	public static class WecMasterBuilder extends MasterVo{
		 
		    private String id;
		 	private String name;
			private String scadaStatus;
			private String status;
			
			private Double capacity;
			private String technicalNo;
			private String commissionDate;
			private String type;
			
			private String foundationNo;
			private String show;
			private String remark;
			private String transferDate;
			
			private String transferWecId;			
			private String multiFactor;
			private String genComm;
			private Double costPerUnit;
			
			private String machineAvailability;
			private String extGridAvailability;
			private String intGridAvailability;		
			private String customerId;
			
			private String ebId;
			private String feederId;
		 
			private String startDate;
			private String endDate;
			private String formula;
			private String guaranteeType;
			private String customerType;
			
			public WecMasterBuilder id(String id) {
				this.id = id;
				return this;
			}
			public WecMasterBuilder name(String name) {
				this.name = name;
				return this;
			}
			public WecMasterBuilder scadaStatus(String scadaStatus) {
				this.scadaStatus = scadaStatus;
				return this;
			}
			public WecMasterBuilder status(String status) {
				this.status = status;
				return this;
			}
			public WecMasterBuilder capacity(Double capacity) {
				this.capacity = capacity;
				return this;
			}
			public WecMasterBuilder technicalNo(String technicalNo) {
				this.technicalNo = technicalNo;
				return this;
			}
			public WecMasterBuilder commissionDate(String commissionDate) {
				this.commissionDate = commissionDate;
				return this;
			}
			public WecMasterBuilder type(String type) {
				this.type = type;
				return this;
			}
			public WecMasterBuilder foundationNo(String foundationNo) {
				this.foundationNo = foundationNo;
				return this;
			}
			public WecMasterBuilder show(String show) {
				this.show = show;
				return this;
			}
			public WecMasterBuilder remark(String remark) {
				this.remark = remark;
				return this;
			}
			public WecMasterBuilder transferDate(String transferDate) {
				this.transferDate = transferDate;
				return this;
			}
			public WecMasterBuilder transferWecId(String transferWecId) {
				this.transferWecId = transferWecId;
				return this;
			}
			public WecMasterBuilder multiFactor(String multiFactor) {
				this.multiFactor = multiFactor;
				return this;
			}
			public WecMasterBuilder genComm(String genComm) {
				this.genComm = genComm;
				return this;
			}
			public WecMasterBuilder costPerUnit(Double costPerUnit) {
				this.costPerUnit = costPerUnit;
				return this;
			}
			public WecMasterBuilder machineAvailability(String machineAvailability) {
				this.machineAvailability = machineAvailability;
				return this;
			}
			public WecMasterBuilder extGridAvailability(String extGridAvailability) {
				this.extGridAvailability = extGridAvailability;
				return this;
			}
			public WecMasterBuilder intGridAvailability(String intGridAvailability) {
				this.intGridAvailability = intGridAvailability;
				return this;
			}
			public WecMasterBuilder customerId(String customerId) {
				this.customerId = customerId;
				return this;
			}
			public WecMasterBuilder ebId(String ebId) {
				this.ebId = ebId;
				return this;
			}
			public WecMasterBuilder feederId(String feederId) {
				this.feederId = feederId;
				return this;
			}
	
			public WecMasterBuilder startDate(String startDate) {
				this.startDate = startDate;
				return this;
			}
			public WecMasterBuilder endDate(String endDate) {
				this.endDate = endDate;
				return this;
			}
			public WecMasterBuilder formula(String formula) {
				this.formula = formula;
				return this;
			}
			public WecMasterBuilder guaranteeType(String guaranteeType) {
				this.guaranteeType = guaranteeType;
				return this;
			}
			public WecMasterBuilder customerType(String customerType) {
				this.customerType = customerType;
				return this;
			}
			public WecMasterBuilder createdBy(String createdBy) {
				setCreatedBy(createdBy);
				return this;
			}

			public WecMasterBuilder createdAt(String createdAt) {
				setCreatedAt(createdAt);
				return this;
			}

			public WecMasterBuilder modifiedBy(String modifiedBy) {
				setModifiedBy(modifiedBy);
				return this;
			}

			public WecMasterBuilder modifiedAt(String modifiedAt) {
				setModifiedAt(modifiedAt);
				return this;
			}
			
			public WecMasterVo build() {
				WecMasterVo vo = new WecMasterVo(this);

				return vo;
			} 
	 }

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		setId(null);
		 setName(null);
		 setScadaStatus(null);
		 setStatus(null);
		 setCapacity(null);
		 setTechnicalNo(null);
		 setCommissionDate(null);
		 setType(null);
		 setFoundationNo(null);
		 setShow(null);
		 setRemark(null);
		 setTransferDate(null);
		 setTransferWecId(null);
		 setStartDate(null);
		 setEndDate(null);
		 setMultiFactor(null);
		 setGenComm(null);
		 setCostPerUnit(null);
		 setMachineAvailability(null);
		 setExtGridAvailability(null);
		 setIntGridAvailability(null);
		 setFormula(null);
		 setCustomerId(null);
		 setEbId(null);
		 setFeederId(null);
		 setGuaranteeType(null);
		 setCustomerType(null);	
		
		 setCreatedBy(null);
		 setModifiedBy(null);
		 setCreatedAt(null);
		 setModifiedAt(null);
		 
		
	}
	 
}
