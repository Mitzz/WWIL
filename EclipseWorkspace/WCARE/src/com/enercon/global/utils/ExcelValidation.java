package com.enercon.global.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.GlobalUtils;
//import java.util.*;
public class ExcelValidation extends ActionForm {
	private static final long serialVersionUID = 1L;	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest req){
		 HttpSession session = req.getSession(false);
		 ActionErrors errors=new ActionErrors();
		 DynaBean dynaBean = GlobalUtils.getDynaBean(req);
		 if (dynaBean == null) {
			 dynaBean = GlobalUtils.getAttDynaBean(req);
		 }
		 String fd = "";
		 java.util.Date fromdate = null;
		 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		 if(dynaBean.getProperty("MsgHeadtxt") != null){
			 if(dynaBean.getProperty("MsgHeadtxt").toString().length() <= 0){
				 ActionError err=new ActionError("msg.head.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 if(dynaBean.getProperty("MsgDescriptiontxt") != null){
			 if(dynaBean.getProperty("MsgDescriptiontxt").toString().length() <= 0){
				 ActionError err=new ActionError("msg.desc.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 
		 ////////////Project Module /////////////////
		 if(dynaBean.getProperty("MNametxt") != null){
			 if(dynaBean.getProperty("MNametxt").toString().length() <= 0){
				 ActionError err=new ActionError("material.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 if(dynaBean.getProperty("MCodetxt") != null){
			 if(dynaBean.getProperty("MCodetxt").toString().length() <= 0){
				 ActionError err=new ActionError("material.code.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }			 
		 if(dynaBean.getProperty("CustNametxt") != null){
			 if(dynaBean.getProperty("CustNametxt").toString().length() <= 0){
				 ActionError err=new ActionError("cus.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 if(dynaBean.getProperty("CustCodetxt") != null){
			 if(dynaBean.getProperty("CustCodetxt").toString().length() <= 0){
				 ActionError err=new ActionError("cus.code.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }			 
		 if(dynaBean.getProperty("CustContacttxt") != null){
			 if(dynaBean.getProperty("CustContacttxt").toString().length() <= 0){
				 ActionError err=new ActionError("cus.cont.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 if(dynaBean.getProperty("CustPhonetxt") != null){
			 if(dynaBean.getProperty("CustPhonetxt").toString().length() <= 0){
				 ActionError err=new ActionError("cus.phone.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 if(dynaBean.getProperty("CustCelltxt") != null){
			 if(dynaBean.getProperty("CustCelltxt").toString().length() <= 0){
				 ActionError err=new ActionError("cus.cell.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 if(dynaBean.getProperty("CustEmailtxt") != null){
			 if(dynaBean.getProperty("CustEmailtxt").toString().length() <= 0){
				 ActionError err=new ActionError("cus.email.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		 if(dynaBean.getProperty("CustFaxtxt") != null){
			 if(dynaBean.getProperty("CustFaxtxt").toString().length() <= 0){
				 ActionError err=new ActionError("cus.fax.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		
				 if(dynaBean.getProperty("roletxt") != null){
					 if(dynaBean.getProperty("roletxt").toString().length() <= 0){
						 ActionError err=new ActionError("mlogin.role.required");
						 errors.add(ActionErrors.GLOBAL_ERROR,err);
					 }
				 }
				 if(dynaBean.getProperty("logintypetxt") != null){
					 if(dynaBean.getProperty("logintypetxt").toString().length() <= 0){
						 ActionError err=new ActionError("mlogin.type.required");
						 errors.add(ActionErrors.GLOBAL_ERROR,err);
					 }
				 }
				 if(dynaBean.getProperty("Passwordtxt") != null){
					 if(dynaBean.getProperty("Passwordtxt").toString().length() <= 0){
						 ActionError err=new ActionError("mlogin.pass.required");
						 errors.add(ActionErrors.GLOBAL_ERROR,err);
					 }
				 }
				 if(dynaBean.getProperty("desctxt") != null){
					 if(dynaBean.getProperty("desctxt").toString().length() <= 0){
						 ActionError err=new ActionError("desc.required");
						 errors.add(ActionErrors.GLOBAL_ERROR,err);
					 }
				 }
				 if(dynaBean.getProperty("loginidtxt") != null){
					 if(dynaBean.getProperty("loginidtxt").toString().length() <= 0){
						 ActionError err=new ActionError("mlogin.uid.required");
						 errors.add(ActionErrors.GLOBAL_ERROR,err);
					 }
				 }
			 
			 
			 
			 if(dynaBean.getProperty("Desctxt") != null){
				 if(dynaBean.getProperty("Desctxt").toString().length() <= 0){
					 ActionError err=new ActionError("desc.required");
					 errors.add(ActionErrors.GLOBAL_ERROR,err);
				 }
			 }		 
			 if(dynaBean.getProperty("RoleNametxt") != null){
				 if(dynaBean.getProperty("RoleNametxt").toString().length() <= 0){
					 ActionError err=new ActionError("role.name.required");
					 errors.add(ActionErrors.GLOBAL_ERROR,err);
				 }
			 }		 
		 
		 
		 
		 if(dynaBean.getProperty("StateNametxt") != null){
			 if(dynaBean.getProperty("StateNametxt").toString().length() <= 0){
				 ActionError err=new ActionError("state.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 if(dynaBean.getProperty("StateCodetxt") != null){
			 if(dynaBean.getProperty("StateCodetxt").toString().length() <= 0){
				 ActionError err=new ActionError("state.code.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		
		 if(dynaBean.getProperty("Statetxt") != null){
			 if(dynaBean.getProperty("Statetxt").toString().length() <= 0){
				 ActionError err=new ActionError("state.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 if(dynaBean.getProperty("SiteNametxt") != null){
			 if(dynaBean.getProperty("SiteNametxt").toString().length() <= 0){
				 ActionError err=new ActionError("site.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 if(dynaBean.getProperty("SiteCodetxt") != null){
			 if(dynaBean.getProperty("SiteCodetxt").toString().length() <= 0){
				 ActionError err=new ActionError("site.code.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 
		 if(dynaBean.getProperty("MPShowtxt") != null){
			 if(dynaBean.getProperty("MPShowtxt").toString().length() <= 0){
				 ActionError err=new ActionError("mp.show.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
			 
		 if(dynaBean.getProperty("MPDesctxt") != null){
			 if(dynaBean.getProperty("MPDesctxt").toString().length() <= 0){
				 ActionError err=new ActionError("mp.desc.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 if(dynaBean.getProperty("MPTypetxt") != null){
			 if(dynaBean.getProperty("MPTypetxt").toString().length() <= 0){
				 ActionError err=new ActionError("mp.type.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 if(dynaBean.getProperty("MPUnittxt") != null){
			 if(dynaBean.getProperty("MPUnittxt").toString().length() <= 0){
				 ActionError err=new ActionError("mp.unit.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 if(dynaBean.getProperty("MpIdtxt") != null){
			 if(dynaBean.getProperty("MpIdtxt").toString().length() <= 0){
				 ActionError err=new ActionError("mp.id.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
	 
		 if(dynaBean.getProperty("EBShorttxt") != null){
			 if(dynaBean.getProperty("EBShorttxt").toString().length() <= 0){
				 ActionError err=new ActionError("ebs.desc.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 
		 if(dynaBean.getProperty("EBDesctxt") != null){
			 if(dynaBean.getProperty("EBDesctxt").toString().length() <= 0){
				 ActionError err=new ActionError("ebs.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 
		 if(dynaBean.getProperty("EBMFactortxt") != null){
			 if(dynaBean.getProperty("EBMFactortxt").toString().length() <= 0){
				 ActionError err=new ActionError("ebs.multi.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 
		 //if(dynaBean.getProperty("EBCapacitytxt") != null){
		//	 if(dynaBean.getProperty("EBCapacitytxt").toString().length() <= 0){
		//		 ActionError err=new ActionError("ebs.cap.required");
		//		 errors.add(ActionErrors.GLOBAL_ERROR,err);
		//	 }
		 //}		 
		 if(dynaBean.getProperty("EBSitetxt") != null){
			 if(dynaBean.getProperty("EBSitetxt").toString().length() <= 0){
				 ActionError err=new ActionError("site.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 	 
		 if(dynaBean.getProperty("EBTypetxt") != null){
			 if(dynaBean.getProperty("EBTypetxt").toString().length() <= 0){
				 ActionError err=new ActionError("ebs.type.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 if(dynaBean.getProperty("EBSubTypetxt") != null){
			 if(dynaBean.getProperty("EBSubTypetxt").toString().length() <= 0){
				 ActionError err=new ActionError("ebs.subtype.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
			 		 
		if(dynaBean.getProperty("EBIdtxt") != null){
			 if(dynaBean.getProperty("EBIdtxt").toString().length() <= 0){
				 ActionError err=new ActionError("wec.ebname.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		if(dynaBean.getProperty("WECIdtxt") != null){
			 if(dynaBean.getProperty("WECIdtxt").toString().length() <= 0){
				 ActionError err=new ActionError("wec.wname.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		if(dynaBean.getProperty("FederIdtxt") != null){
			 if(dynaBean.getProperty("FederIdtxt").toString().length() <= 0){
				 ActionError err=new ActionError("fds.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		
		if(dynaBean.getProperty("FDDesctxt") != null){
			 if(dynaBean.getProperty("FDDesctxt").toString().length() <= 0){
				 ActionError err=new ActionError("fds.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 
		 if(dynaBean.getProperty("FDShorttxt") != null){
			 if(dynaBean.getProperty("FDShorttxt").toString().length() <= 0){
				 ActionError err=new ActionError("fds.desc.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }		 
		 if(dynaBean.getProperty("FDTypetxt") != null){
			 if(dynaBean.getProperty("FDTypetxt").toString().length() <= 0){
				 ActionError err=new ActionError("fds.type.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 if(dynaBean.getProperty("FDSubTypetxt") != null){
			 if(dynaBean.getProperty("FDSubTypetxt").toString().length() <= 0){
				 ActionError err=new ActionError("fds.subtype.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 
		 //if(dynaBean.getProperty("FDCapacitytxt") != null){
		//	 if(dynaBean.getProperty("FDCapacitytxt").toString().length() <= 0){
		//		 ActionError err=new ActionError("fds.cap.required");
		//		 errors.add(ActionErrors.GLOBAL_ERROR,err);
		//	 }
		 //}		 
		 if(dynaBean.getProperty("FDSitetxt") != null){
			 if(dynaBean.getProperty("FDSitetxt").toString().length() <= 0){
				 ActionError err=new ActionError("site.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }

		if(dynaBean.getProperty("Customeridtxt") != null){
			 if(dynaBean.getProperty("Customeridtxt").toString().length() <= 0){
				 ActionError err=new ActionError("wec.cname.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	

		if(dynaBean.getProperty("WECTypetxt") != null){
			 if(dynaBean.getProperty("WECTypetxt").toString().length() <= 0){
				 ActionError err=new ActionError("wec.wtype.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		

		if(dynaBean.getProperty("WECShorttxt") != null){
			 if(dynaBean.getProperty("WECShorttxt").toString().length() <= 0){
				 ActionError err=new ActionError("wec.wname.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		

		if(dynaBean.getProperty("locationtxt") != null){
			 if(dynaBean.getProperty("locationtxt").toString().length() <= 0){
				 ActionError err=new ActionError("wec.funame.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 
			

		if(dynaBean.getProperty("Weccapacitytxt") != null){
			 if(dynaBean.getProperty("Weccapacitytxt").toString().length() <= 0){
				 ActionError err=new ActionError("wec.cap.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		
		if(dynaBean.getProperty("WECMFactortxt") != null){
			 if(dynaBean.getProperty("WECMFactortxt").toString().length() <= 0){
				 ActionError err=new ActionError("wec.multi.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 
		 if(dynaBean.getProperty("Transactiontxt") != null){
			 if(dynaBean.getProperty("Transactiontxt").toString().length() <= 0){
				 ActionError err=new ActionError("tran.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		 if(dynaBean.getProperty("Exceltxt") != null){
			 if(dynaBean.getProperty("Exceltxt").toString().length() <= 0){
				 ActionError err=new ActionError("excel.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		 if(dynaBean.getProperty("Sheettxt") != null){
			 if(dynaBean.getProperty("Sheettxt").toString().length() <=0 ){
				 ActionError err=new ActionError("sheet.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		 if(dynaBean.getProperty("Descriptiontxt") != null){
			 if(dynaBean.getProperty("Descriptiontxt").toString().length() <=0 ){
				 ActionError err=new ActionError("description.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		 if(dynaBean.getProperty("FromDatetxt") != null){
			 fd = dynaBean.getProperty("FromDatetxt").toString();
			 try {
				 fromdate = format.parse(fd);
			 } catch (ParseException e) {
				 //e.printStackTrace();
			 }
			 if(dynaBean.getProperty("FromDatetxt").toString().length() <= 0){
				 ActionError err=new ActionError("fromdate.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("ToDatetxt") != null){
			 fd = dynaBean.getProperty("ToDatetxt").toString();
			 try {
				 fromdate = format.parse(fd);
			 } catch (ParseException e) {
				 //e.printStackTrace();
			 }
			 if(dynaBean.getProperty("ToDatetxt").toString().length() <= 0){
				 ActionError err=new ActionError("todate.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("Monthtxt") != null){
			 if(dynaBean.getProperty("Monthtxt").toString().length() <= 0){
				 ActionError err=new ActionError("month.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("Yeartxt") != null){
			 if(dynaBean.getProperty("Yeartxt").toString().length() <= 0){
				 ActionError err=new ActionError("year.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("Reasontxt") != null){
			 if(dynaBean.getProperty("Reasontxt").toString().length() <= 0){
				 ActionError err=new ActionError("reason.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("Datetxt") != null){
			 if(dynaBean.getProperty("Datetxt").toString().length() <= 0){
				 ActionError err=new ActionError("date.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("Nametxt") != null){
			 if(dynaBean.getProperty("Nametxt").toString().length() <= 0){
				 ActionError err=new ActionError("name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("Righttxt") != null){
			 if(dynaBean.getProperty("Righttxt").toString().length() <= 0){
				 ActionError err=new ActionError("right.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("OPasswordtxt") != null){
			 if(dynaBean.getProperty("OPasswordtxt").toString().length() <= 0){
				 ActionError err=new ActionError("old.password.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("NPasswordtxt") != null){
			 if(dynaBean.getProperty("NPasswordtxt").toString().length() <= 0){
				 ActionError err=new ActionError("new.password.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("VPasswordtxt") != null){
			 if(dynaBean.getProperty("VPasswordtxt").toString().length() <= 0){
				 ActionError err=new ActionError("verify.password.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }	
		 }
		 if(dynaBean.getProperty("VPasswordtxt") != null && dynaBean.getProperty("NPasswordtxt") != null){
			 if(dynaBean.getProperty("VPasswordtxt").toString().length() > 0 && dynaBean.getProperty("NPasswordtxt").toString().length() > 0){
				 if (dynaBean.getProperty("VPasswordtxt").toString().equals(dynaBean.getProperty("NPasswordtxt").toString())){
					 
				 }else{
					 ActionError err=new ActionError("new.verify.password.not.equal");
					 errors.add(ActionErrors.GLOBAL_ERROR,err);
				 }
			 }	
		 }
		 if(dynaBean.getProperty("Address") != null){
			 if(dynaBean.getProperty("Address").toString().length() <= 0){
				 ActionError err=new ActionError("Address.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		 if(dynaBean.getProperty("reject")!=null ){
			 if(dynaBean.getProperty("remarks") != null){
				 if(dynaBean.getProperty("remarks").toString().length() <= 0){
					 ActionError err=new ActionError("remarks.required");
					 errors.add(ActionErrors.GLOBAL_ERROR,err);
				 }
			 }
		 }
		 if(dynaBean.getProperty("Remarkstxt") != null){
			 if(dynaBean.getProperty("Remarkstxt").toString().length() <= 0){
				 ActionError err=new ActionError("remarks.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		 if(dynaBean.getProperty("RoleNametxtt") != null){
			 if(dynaBean.getProperty("RoleNametxtt").toString().length() <= 0){
				 ActionError err=new ActionError("role.name.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 if(dynaBean.getProperty("RoleDescriptiontxt") != null){
			 if(dynaBean.getProperty("RoleDescriptiontxt").toString().length() <= 0){
				 ActionError err=new ActionError("role.desc.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }	
		 
		 if(dynaBean.getProperty("UserId") != null){
			 if(dynaBean.getProperty("UserId").toString().length() <= 0){
				 ActionError err=new ActionError("user.required");
				 errors.add(ActionErrors.GLOBAL_ERROR,err);
			 }
		 }
		 
		 session.setAttribute("dynabean", dynaBean);
		 return errors;		
	}
	
}

