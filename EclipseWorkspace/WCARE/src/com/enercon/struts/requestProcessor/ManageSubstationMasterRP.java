package com.enercon.struts.requestProcessor;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.comparator.AreaMasterVoComparator;
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.master.AreaMasterService;
import com.enercon.service.master.FeederMasterService;
import com.enercon.service.master.SubstationMasterService;

public class ManageSubstationMasterRP extends Action {

	private static Logger logger = Logger.getLogger(ManageSubstationMasterRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		
		List<IAreaMasterVo> areas = AreaMasterService.getInstance().getAll();
		List<SubstationMasterVo> substations = SubstationMasterService.getInstance().getAll();
		FeederMasterService.getInstance().associateSubstations(substations);
		Collections.sort(areas, AreaMasterVoComparator.BY_NAME);
		request.setAttribute("areas", areas);
		request.setAttribute("substations", substations);
		return mapping.findForward("success");
	}
}
