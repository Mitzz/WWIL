package com.enercon.global.utility;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.enercon.dao.master.WecMasterDao;
import com.enercon.model.graph.IWecMasterVo;

public class WecTransferRemark extends SimpleTagSupport{
	private IWecMasterVo wec;
	
	public void doTag() throws JspException, IOException {
		getJspContext().getOut().write(getRemark());
	}

	private String getRemark() {
		
		String remark = "Unknown";
		String workingStatus = null;
		workingStatus = wec.getStatus();
		if(workingStatus.equals(WecMasterDao.WorkingStatus.ACTIVE.getCode())){
			remark = "Data Not Available" ;
		}else if(workingStatus.equals(WecMasterDao.WorkingStatus.TRANSFER.getCode())) {
			remark = "Machine Transferred: "  + wec.getRemark();
		}
		return remark;
	}
    
	public IWecMasterVo getWec() {
		return wec;
	}

	public void setWec(IWecMasterVo wec) {
		this.wec = wec;
	}
	
}
