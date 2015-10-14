package com.enercon.struts.action.summaryreport;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.ajax.ajaxVo.SelectVo;
import com.enercon.struts.model.SelectVoCallableTask;

public class SummaryReportRequestProcessor extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)

	throws Exception {
		List<SelectVo> customerSelectVo = null;
		
		List<SelectVo> wecTypeSelectVo = null;
		List<SelectVo> stateSelectVo = null;

		ExecutorService server = Executors.newFixedThreadPool(3);

		Future<List<SelectVo>> future1 = server.submit(new SelectVoCallableTask("CustomerMasterSelectVo"));
		Future<List<SelectVo>> future2 = server.submit(new SelectVoCallableTask("WECTypeMasterSelectVo"));
		Future<List<SelectVo>> future3 = server.submit(new SelectVoCallableTask("StateMasterSelectVo"));
		
		server.shutdown();
		server.awaitTermination(1, TimeUnit.DAYS);
		customerSelectVo = future1.get();
		wecTypeSelectVo = future2.get();
		stateSelectVo = future3.get();

		request.setAttribute("CustomerMasterSelectVo", customerSelectVo);
		request.setAttribute("WECTypeMasterSelectVo", wecTypeSelectVo);
		request.setAttribute("StateMasterSelectVo", stateSelectVo);
		
		return mapping.findForward("success");

	}
}