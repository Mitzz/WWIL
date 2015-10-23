package com.enercon.model.master;

public class StandardMessageMasterVo extends MasterVo{
	
	private String id;
	private String messageHead;
	private String messageDescription;
	
	private StandardMessageMasterVo (StandardMessageMasterVoBuilder builder){
		
		this.id = builder.id;
		this.messageHead = builder.messageHead;
		this.messageDescription = builder.messageDescription;
		this.createdBy = builder.createdBy;
		this.createdAt = builder.createdAt;
		this.modifiedBy = builder.modifiedBy;
		this.modifiedAt = builder.modifiedAt;
	}
	
	
	public StandardMessageMasterVo() {
	}


	@Override
	public String toString() {
		return "StandardMessageMasterVo [id=" + id + ", messageHead="
				+ messageHead + ", messageDescription=" + messageDescription
				+ "]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessageHead() {
		return messageHead;
	}
	public void setMessageHead(String messageHead) {
		this.messageHead = messageHead;
	}
	public String getMessageDescription() {
		return messageDescription;
	}
	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}
	
	public static class StandardMessageMasterVoBuilder extends MasterVo{
		
		private String id;
		private String messageHead;
		private String messageDescription;
		
		public StandardMessageMasterVoBuilder id(String id) {
			this.id = id;
			return this;
		}
		
		public StandardMessageMasterVoBuilder messageHead(String messageHead) {
			this.messageHead = messageHead;
			return this;
		}
		
		public StandardMessageMasterVoBuilder messageDescription(String messageDescription) {
			this.messageDescription = messageDescription;
			return this;
		}
		

		public StandardMessageMasterVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public StandardMessageMasterVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public StandardMessageMasterVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public StandardMessageMasterVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}

		public StandardMessageMasterVo build() {
			StandardMessageMasterVo vo = new StandardMessageMasterVo(this);

			return vo;
		}
	 
	}

}
