package com.enercon.struts.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.dgr.MPR;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;
import com.enercon.service.WecMasterService;
import com.enercon.service.master.EbMasterService;
import com.enercon.service.master.WecTypeMasterService;

public class MprAction extends Action{
	private static Logger logger = Logger.getLogger(MprAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//<td align="center" colspan="2"> <input  align="right" type="button" value="Export To Excel" onClick=location.href="Change/EbIdMPRChange.jsp?wectype=<%=wec%>&fdate=<%=fdate%>&tdate=<%=tdate%>&ebid=<%=ebid%>&wecid=<%=wecid%>"></td>
		String fromDate = request.getParameter("fdate");
		String toDate = request.getParameter("tdate");
		String wecType = request.getParameter("wectype");
		String stateId = request.getParameter("stateid");
		String siteId = request.getParameter("siteid");
		String ebId = request.getParameter("ebid");
		String wecId = request.getParameter("wecid");
		
		List<MPR> mprs = new ArrayList<MPR>();
		
		logger.debug(String.format("WecType: %s, State: %s, Site: %s, Eb: %s, Wec: %s", wecType, stateId, siteId, ebId, wecId));
		
		IWecMasterVo wec = (wecId == null) ? null : WecMasterService.getInstance().get(wecId);
		IEbMasterVo eb = (ebId == null) ? null : EbMasterService.getInstance().get(ebId);
		ISiteMasterVo site = (siteId == null) ? null : SiteMasterService.getInstance().get(siteId);
		IStateMasterVo state = (stateId == null) ? null : StateMasterService.getInstance().get(stateId);

		String fromDateOracleFormat = DateUtility.convertDateFormats(fromDate, "dd/MM/yyyy", "dd-MMM-yyyy");
		String toDateOracleFormat = DateUtility.convertDateFormats(toDate, "dd/MM/yyyy", "dd-MMM-yyyy");
		
		if("ALL".equals(wecType) && siteId != null){
			List<WecTypeMasterVo> wecTypes = WecTypeMasterService.getInstance().get(site);
			for(WecTypeMasterVo type: wecTypes){
				mprs.add(new MPR(fromDateOracleFormat, toDateOracleFormat, type, state, site, null, null));
			}
		} else {
			WecTypeMasterVo wecTypeMaster = wecType == null ? null : WecTypeMasterService.getInstance().get(wecType.split("/")[0]);
			MPR mpr = new MPR(fromDateOracleFormat, toDateOracleFormat, wecTypeMaster, state, site, eb, wec);
			mprs.add(mpr);
		}
		
		request.setAttribute("mprs", mprs);
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);
		
		return mapping.findForward("success");
	}
}
