package com.enercon.struts.requestProcessor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.SiteMasterVo;
import com.enercon.service.SiteMasterService;

public class ManageSiteRemarksRP extends Action{
	
	private static Logger logger = Logger.getLogger(ManageSiteRemarksRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		
		List<ISiteMasterVo> sites = SiteMasterService.getInstance().getAll();
		
		List<SiteMasterVo> sitesRemarks = SiteMasterService.getInstance().getRemarks(); 
		
		request.setAttribute("sites", sites);
		request.setAttribute("sitesRemarks", sitesRemarks);
		return mapping.findForward("success");
	}

	
	/*public static void main(String args[]){
		List<ISiteMasterVo> sites = SiteMasterService.getInstance().getAll();
		for(ISiteMasterVo site : sites){
			logger.debug(site.getId()+" :: "+site.getName());
		}
	}*/
	
}

