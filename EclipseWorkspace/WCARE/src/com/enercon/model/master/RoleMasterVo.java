package com.enercon.model.master;

public class RoleMasterVo extends MasterVo{

	private String id;
	private String name;
	private String description;
	
	public RoleMasterVo(String id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	@Override
//	public String toString() {
//		return "RoleMasterVo [id=" + id + ", name=" + name + ", description="
//				+ description + ", createdBy=" + createdBy + ", createdAt="
//				+ createdAt + ", modifiedBy=" + modifiedBy + ", modifiedAt="
//				+ modifiedAt + "]";
//	}
	

	
}
