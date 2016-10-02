package com.enercon.model.master;

import org.apache.log4j.Logger;


public class TransactionMasterVo {
	
	private static Logger logger = Logger.getLogger(TransactionMasterVo.class);

	private String id;
	private String name;
	private String description;
	private String url;
	private String under1;
	private String under2;
	private String under3;
	private int sequenceNo;
	private String display;
	private String under1ImageNormal;
	private String under1ImageMouse;
	private String under1ImageExpand;
	private String under2ImageNormal;
	private String under2ImageMouse;
	private String under2ImageExpand;
	private String under3ImageNormal;
	private String under3ImageMouse;
	private String under3ImageExpand;
	private String linkNormal;
	private String linkMouse;
	private String linkExpand;

	public TransactionMasterVo(TransactionMasterVoBuilder builder) {
		super();
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.url = builder.url;
		this.under1 = builder.under1;
		this.under2 = builder.under2;
		this.under3 = builder.under3;
		this.sequenceNo = builder.sequenceNo;
		this.display = builder.display;
		this.under1ImageNormal = builder.under1ImageNormal;
		this.under1ImageMouse = builder.under1ImageMouse;
		this.under1ImageExpand = builder.under1ImageExpand;
		this.under2ImageNormal = builder.under2ImageNormal;
		this.under2ImageMouse = builder.under2ImageMouse;
		this.under2ImageExpand = builder.under2ImageExpand;
		this.under3ImageNormal = builder.under3ImageNormal;
		this.under3ImageMouse = builder.under3ImageMouse;
		this.under3ImageExpand = builder.under3ImageExpand;
		this.linkNormal = builder.linkNormal;
		this.linkMouse = builder.linkMouse;
		this.linkExpand = builder.linkExpand;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUnder1() {
		return under1;
	}

	public void setUnder1(String under1) {
		this.under1 = under1;
	}

	public String getUnder2() {
		return under2;
	}

	public void setUnder2(String under2) {
		this.under2 = under2;
	}

	public String getUnder3() {
		return under3;
	}

	public void setUnder3(String under3) {
		this.under3 = under3;
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getUnder1ImageNormal() {
		return under1ImageNormal;
	}

	public void setUnder1ImageNormal(String under1ImageNormal) {
		this.under1ImageNormal = under1ImageNormal;
	}

	public String getUnder1ImageMouse() {
		return under1ImageMouse;
	}

	public void setUnder1ImageMouse(String under1ImageMouse) {
		this.under1ImageMouse = under1ImageMouse;
	}

	public String getUnder1ImageExpand() {
		return under1ImageExpand;
	}

	public void setUnder1ImageExpand(String under1ImageExpand) {
		this.under1ImageExpand = under1ImageExpand;
	}

	public String getUnder2ImageNormal() {
		return under2ImageNormal;
	}

	public void setUnder2ImageNormal(String under2ImageNormal) {
		this.under2ImageNormal = under2ImageNormal;
	}

	public String getUnder2ImageMouse() {
		return under2ImageMouse;
	}

	public void setUnder2ImageMouse(String under2ImageMouse) {
		this.under2ImageMouse = under2ImageMouse;
	}

	public String getUnder2ImageExpand() {
		return under2ImageExpand;
	}

	public void setUnder2ImageExpand(String under2ImageExpand) {
		this.under2ImageExpand = under2ImageExpand;
	}

	public String getUnder3ImageNormal() {
		return under3ImageNormal;
	}

	public void setUnder3ImageNormal(String under3ImageNormal) {
		this.under3ImageNormal = under3ImageNormal;
	}

	public String getUnder3ImageMouse() {
		return under3ImageMouse;
	}

	public void setUnder3ImageMouse(String under3ImageMouse) {
		this.under3ImageMouse = under3ImageMouse;
	}

	public String getUnder3ImageExpand() {
		return under3ImageExpand;
	}

	public void setUnder3ImageExpand(String under3ImageExpand) {
		this.under3ImageExpand = under3ImageExpand;
	}

	public String getLinkNormal() {
		return linkNormal;
	}

	public void setLinkNormal(String linkNormal) {
		this.linkNormal = linkNormal;
	}

	public String getLinkMouse() {
		return linkMouse;
	}

	public void setLinkMouse(String linkMouse) {
		this.linkMouse = linkMouse;
	}

	public String getLinkExpand() {
		return linkExpand;
	}

	public void setLinkExpand(String linkExpand) {
		this.linkExpand = linkExpand;
	}

	public static class TransactionMasterVoBuilder {
		private String id;
		private String name;
		private String description;
		private String url;
		private String under1;
		private String under2;
		private String under3;
		private int sequenceNo;
		private String display;
		private String under1ImageNormal;
		private String under1ImageMouse;
		private String under1ImageExpand;
		private String under2ImageNormal;
		private String under2ImageMouse;
		private String under2ImageExpand;
		private String under3ImageNormal;
		private String under3ImageMouse;
		private String under3ImageExpand;
		private String linkNormal;
		private String linkMouse;
		private String linkExpand;

		public TransactionMasterVoBuilder id(String id) {
			this.id = id;
			return this;
		}

		public TransactionMasterVoBuilder name(String name) {
			this.name = name;
			return this;
		}

		public TransactionMasterVoBuilder description(String description) {
			this.description = description;
			return this;
		}

		public TransactionMasterVoBuilder url(String url) {
			this.url = url;
			return this;
		}

		public TransactionMasterVoBuilder under1(String under1) {
			this.under1 = under1;
			return this;
		}

		public TransactionMasterVoBuilder under2(String under2) {
			this.under2 = under2;
			return this;
		}

		public TransactionMasterVoBuilder under3(String under3) {
			this.under3 = under3;
			return this;
		}

		public TransactionMasterVoBuilder sequenceNo(int sequenceNo) {
			this.sequenceNo = sequenceNo;
			return this;
		}

		public TransactionMasterVoBuilder display(String display) {
			this.display = display;
			return this;
		}

		public TransactionMasterVoBuilder under1ImageNormal(String under1ImageNormal) {
			this.under1ImageNormal = under1ImageNormal;
			return this;
		}

		public TransactionMasterVoBuilder under1ImageMouse(String under1ImageMouse) {
			this.under1ImageMouse = under1ImageMouse;
			return this;
		}

		public TransactionMasterVoBuilder under1ImageExpand(String under1ImageExpand) {
			this.under1ImageExpand = under1ImageExpand;
			return this;
		}

		public TransactionMasterVoBuilder under2ImageNormal(String under2ImageNormal) {
			this.under2ImageNormal = under2ImageNormal;
			return this;
		}

		public TransactionMasterVoBuilder under2ImageMouse(String under2ImageMouse) {
			this.under2ImageMouse = under2ImageMouse;
			return this;
		}

		public TransactionMasterVoBuilder under2ImageExpand(String under2ImageExpand) {
			this.under2ImageExpand = under2ImageExpand;
			return this;
		}

		public TransactionMasterVoBuilder under3ImageNormal(String under3ImageNormal) {
			this.under3ImageNormal = under3ImageNormal;
			return this;
		}

		public TransactionMasterVoBuilder under3ImageMouse(String under3ImageMouse) {
			this.under3ImageMouse = under3ImageMouse;
			return this;
		}

		public TransactionMasterVoBuilder under3ImageExpand(String under3ImageExpand) {
			this.under3ImageExpand = under3ImageExpand;
			return this;
		}

		public TransactionMasterVoBuilder linkNormal(String linkNormal) {
			this.linkNormal = linkNormal;
			return this;
		}

		public TransactionMasterVoBuilder linkMouse(String linkMouse) {
			this.linkMouse = linkMouse;
			return this;
		}

		public TransactionMasterVoBuilder linkExpand(String linkExpand) {
			this.linkExpand = linkExpand;
			return this;
		}

		public TransactionMasterVo build() {
			TransactionMasterVo vo = new TransactionMasterVo(this);

			return vo;
		}

	}
	
	public String get(int no){
		
		switch(no){
			case 0 : return getId();
			case 1 : return getName();
			case 2 : return getUrl();
			case 3 : return getUnder1();
			case 4 : return getUnder2();
			case 5 : return getUnder3();
			case 6 : return getDescription();
			case 7 : return getDisplay();
			case 8 : return getUnder1ImageNormal();
			case 9 : return getUnder1ImageMouse();
			case 10 : return getUnder1ImageExpand();
			case 11 : return getUnder2ImageNormal();
			case 12 : return getUnder2ImageMouse();
			case 13: return getUnder2ImageExpand();
			case 14: return getUnder3ImageNormal();
			case 15: return getUnder3ImageMouse();
			case 16: return getUnder3ImageExpand();
			case 17: return getLinkNormal();
			case 18: return getLinkMouse();
			case 19: return getLinkExpand();
			default: logger.error(no + " Not Defined");
		}
		return null;
	}

	@Override
	public String toString() {
		return "TransactionMasterVo [id=" + id + ", name=" + name + ", description=" + description + ", url=" + url + ", under1=" + under1 + ", under2=" + under2 + ", under3=" + under3 + ", sequenceNo=" + sequenceNo + ", display=" + display + "]";
	}

}
