package com.enercon.struts.action;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.ajax.ajaxVo.SelectVo;
import com.enercon.struts.dao.SelectVoDao;
public class HistorycalDataReportRequestProcessor extends Action{
	
	List<SelectVo> customerSelectVo=null;
	List<SelectVo> stateSelectVo=null;
	List<SelectVo> wecTypeSelectVo=null;
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		ExecutorService executorService=Executors.newFixedThreadPool(3);


	executorService.submit(new Runnable()
		{
			public void run(){
				wecTypeSelectVo = SelectVoDao.getSelectVo("WECTypeMaster");
			}
		});




		executorService.submit(new Runnable()
		{
			public void run(){
				customerSelectVo = SelectVoDao.getSelectVo("CustomerMaster");
			}
		});
		executorService.submit(new Runnable()
		{
			public void run(){
				stateSelectVo = SelectVoDao.getSelectVo("StateMaster");
			}
		});
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.DAYS);

		request.setAttribute("CustomerMasterSelectVo", customerSelectVo);
		request.setAttribute("WECTypeMasterSelectVo", wecTypeSelectVo);
		request.setAttribute("StateMasterSelectVo", stateSelectVo);
		
		//System.out.println(wecTypeSelectVo);
		return mapping.findForward("success");
	}

}
