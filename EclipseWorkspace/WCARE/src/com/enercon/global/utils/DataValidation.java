 package com.enercon.global.utils;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import com.enercon.projects.dao.ProjectDao;
import com.enercon.siteuser.dao.SiteUserDao;

import com.enercon.scada.dao.ScadaDao;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.customer.dao.CustomerDao;
import com.enercon.admin.dao.*;
 
public final class DataValidation extends Action
{
//	private static final long serialVersionUID = 1L;
//	public void service(HttpServletRequest req,HttpServletResponse res)
//	throws IOException, ServletException
//	{
	public ActionForward execute(ActionMapping mapping,	ActionForm form,HttpServletRequest request,	HttpServletResponse response) 
	throws IOException, ServletException
	{
		DynaBean dynaBean = GlobalUtils.getDynaBean(request);
		if (dynaBean == null) {
			 dynaBean = GlobalUtils.getAttDynaBean(request);
		}
		//System.out.println("Data Validation:" + dynaBean);
		String msg="";
		String NUM="";
		List translist=new ArrayList();
		
		HttpSession session = request.getSession(true);
		try
		{	
			String uploadtype = dynaBean.getProperty("Admin_Input_Type").toString();
			AdminDao ad = new AdminDao();
			ProjectDao pr = new ProjectDao();
				
			CustomerDao ct = new CustomerDao();
			SiteUserDao su = new SiteUserDao();
		//AssignLogin	
			ScadaDao sd = new ScadaDao();
			
			if(uploadtype.equals("RoleMaster")){					
				msg = ad.addNewRoleIn(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext")){
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("DGRViaEmail"))
			{					
				msg = ad.UpdateLoginDetail(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("RoleTranMaster"))
			{					
				msg = ad.addNewRoleTran(session.getAttribute("loginID").toString(),dynaBean);
				session.setAttribute("SubmitMessage","Success");
			}
			else if(uploadtype.equals("RoleTranMasterNew"))
			{					
				msg = ad.addNewRoleTranNew(session.getAttribute("loginID").toString(),dynaBean);
				session.setAttribute("SubmitMessage","Success");
			}
			else if(uploadtype.equals("UserRoleMaster"))
			{					
				msg = ad.UserRoleTran(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}		
			else if(uploadtype.equals("LocationRight"))
			{					
				msg = ad.AddLocationRight(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.setAttribute("SubmitMessage","Success");
				}else
				{
					session.setAttribute("SubmitMessage","");
				}				
			}				
			else if(uploadtype.equals("ManageProfile"))
			{					
				msg = ct.addManageProfile(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{session.setAttribute("SubmitMessage","Success");
					session.removeAttribute("dynabean");
				}			
			}
			
			else if(uploadtype.equals("CustomerFeedBack"))
			{					
				msg = ct.addCustomerFeedBack(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{session.setAttribute("SubmitMessage","Success");
					session.removeAttribute("dynabean");
				}			
			}
			else if(uploadtype.equals("ManageProfile1"))
			{					
				msg = ct.addManageProfile(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{session.setAttribute("SubmitMessage","Success");
					session.removeAttribute("dynabean");
				}			
			}
			else if(uploadtype.equals("EBEntry"))
			{
				msg = su.NewEBDataEntry(session.getAttribute("loginID").toString(),dynaBean);
			}
			else if(uploadtype.equals("WECEntry"))
			{
				msg = su.NewWECDataEntry(session.getAttribute("loginID").toString(),dynaBean);
			}
			else if(uploadtype.equals("WECEntry_user"))
			{
				msg = su.NewWECDataEntry_user(session.getAttribute("loginID").toString(),dynaBean);
			}
			else if(uploadtype.equals("FDEntry"))
			{
				msg = su.NewFDDataEntry(session.getAttribute("loginID").toString(),dynaBean);
			}
			else if(uploadtype.equals("ChangePassword"))
			{					
				msg = ad.ChangePassword(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("StateMaster"))
			{					
				msg = ad.addNewStateIn(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("AreaMaster"))
			{					
				msg = ad.addNewArea(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.setAttribute("SubmitMessage","Success");
				}else
				{
					session.setAttribute("SubmitMessage","");
				}
			}
			else if(uploadtype.equals("SubStationMaster"))
			{					
				msg = ad.addNewSubstation(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("FeederSiteMaster"))
			{					
				msg = ad.addNewFeeder(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.setAttribute("SubmitMessage","Success");
				}else
				{
					session.setAttribute("SubmitMessage","");
				}
			}		
			else if(uploadtype.equals("SiteMaster"))
			{					
				msg = ad.addNewSite(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.setAttribute("SubmitMessage","Success");
				}else
				{
					session.setAttribute("SubmitMessage","");
				}
			}
			else if(uploadtype.equals("TypeMaster"))
			{					
				msg = ad.addTypeMaster(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}							
			else if(uploadtype.equals("MPMaster"))
			{					
				msg = ad.addMeterPiont(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("RemarksMaster"))
			{					
			//	msg = ad.addRemarks(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("RemarksSiteMaster"))
			{					
				msg = ad.addSiteRemarks(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("EBMaster"))
			{					
				msg = ad.addEBMaster(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.setAttribute("SubmitMessage","Success");
				}else
				{
					session.setAttribute("SubmitMessage","");
				}
				//if (msg.toString().contains("sucessmsgtext"))
				//{
				//	session.removeAttribute("dynabean");
				//}
			}
			else if(uploadtype.equals("EBMFactor"))
			{					
				msg = ad.addEBMFactor(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.setAttribute("SubmitMessage","Success");
				}else
				{
					session.setAttribute("SubmitMessage","");
				}
				//if (msg.toString().contains("sucessmsgtext"))
				//{
				//	session.removeAttribute("dynabean");
				//}
			}
			else if(uploadtype.equals("FDMFactor"))
			{					
				msg = ad.addFDMFactor(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.setAttribute("SubmitMessage","Success");
				}else
				{
					session.setAttribute("SubmitMessage","");
				}
			}
			else if(uploadtype.equals("FDMaster"))
			{					
				msg = ad.addFDMaster(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.setAttribute("SubmitMessage","Success");
				}else
				{
					session.setAttribute("SubmitMessage","");
				}
			}
			
			else if(uploadtype.equals("CustomerMaster"))
			{					
				msg = ad.addCustomer(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					//session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("LoginMaster"))
			{					
				msg = ad.addLoginMaster(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("AssignLogin"))
			{					
				msg = ad.UpdateLoginMaster(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("WecMaster"))
			{					
				msg = ad.AddWecMaster(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.setAttribute("SubmitMessage","Success");
				}else
				{
					session.setAttribute("SubmitMessage","");
				}
			}
			else if(uploadtype.equals("MaterialMaster"))
			{					
				msg = pr.addNewMaterialIn(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}			
			else if(uploadtype.equals("PublishData"))
			{					
				msg = su.PublishData(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					//session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("PublishScadaData"))
			{					
				msg = su.PublishScadaData(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					//session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("TransferEB"))
			{					
				msg = su.TransferEB(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					//session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("TransferWEC"))
			{					
				msg = su.TransferWEC(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					//session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("StdMessage"))
			{					
				msg = ct.addStdMessage(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("PostMessage"))
			{					
				msg = ct.addPostMessage(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("ManageNews"))
			{					
				msg = ad.addNews(session.getAttribute("loginID").toString(),dynaBean);
				if (msg.toString().contains("sucessmsgtext"))
				{
					session.removeAttribute("dynabean");
				}
			}
			else if(uploadtype.equals("DeleteEBData"))
			{					
				msg = su.DeleteEBData(session.getAttribute("loginID").toString(),dynaBean);
				//session.removeAttribute("dynabean");
			}
			else if(uploadtype.equals("DeleteWECData"))
			{					
				msg = su.DeleteWECData(session.getAttribute("loginID").toString(),dynaBean);
				//session.removeAttribute("dynabean");
			}
			else if(uploadtype.equals("DeleteFederData"))
			{					
				msg = su.DeleteFederData(session.getAttribute("loginID").toString(),dynaBean);
				//session.removeAttribute("dynabean");
			}
			else if(uploadtype.equals("UploadScadaData"))
			{					
				msg = sd.uploadScadaData(session.getAttribute("loginID").toString(),dynaBean);
			}
			else if(uploadtype.equals("WECCurtailment")){				
				if(session.getAttribute("loginID") != null){
					msg = ad.wecCurtailment((String)session.getAttribute("loginID"),dynaBean);
				}
			}
			else if(uploadtype.equals("Activate")){
				System.out.println("Activate action");
				if(session.getAttribute("loginID") != null){
					msg = ad.activateWECForPESData((String)session.getAttribute("loginID"),dynaBean);
				}
			}
			else if(uploadtype.equals("Deactivate")){
				System.out.println("DeActivate action");
				if(session.getAttribute("loginID") != null){
					msg = ad.deactivateWECForPESData((String)session.getAttribute("loginID"),dynaBean);
				}
			}
			else if(uploadtype.equals("Report")){		
				System.out.println("Report action");
				/*if(session.getAttribute("loginID") != null){
					msg = ad.wecCurtailment((String)session.getAttribute("loginID"),dynaBean);
				}*/
			}
			else
			{
				msg = "<font class='errormsgtext'>No Linkage of Struts Found</font>";
			}			
			session.setAttribute("translist",translist);
            session.setAttribute("msg",msg);
			return mapping.findForward("success");
		}
		catch(Exception e){
			System.out.println("error:"+e);
			msg = "<font class='errormsgtext'>"+e.toString()+"</font>";
			session.setAttribute("msg",msg);
			return mapping.findForward("failure");
		}
		//finally{
			//HttpSession session = req.getSession(false);
            //session.setAttribute("msg", msg);            
			//req.getRequestDispatcher("/Admin/MonthSalaryUpload.jsp").include(req,res);
		//}
	}
}