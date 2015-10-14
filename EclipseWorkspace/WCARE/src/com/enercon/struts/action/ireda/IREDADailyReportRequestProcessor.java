package com.enercon.struts.action.ireda;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.dao.IredaDAO;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.MethodClass;
import com.enercon.model.ireda.IredaProject;

public class IREDADailyReportRequestProcessor extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		/*ObjectMapper mapper = new ObjectMapper();
		File file = new File("C:\\Users\\91014863\\Desktop\\New Text Document.txt");*/
		String date = request.getParameter("date");
		//System.out.println("Date : " + date);
		if (null == date) {
			date = DateUtility.getYesterdayDateInGivenStringFormat("dd-MMM-yyyy");
		}

		Set<String> iredaProjectNoList = null;
		Set<IredaProject> iredaProjectList = new LinkedHashSet<IredaProject>();
		IredaDAO iredaDAO = new IredaDAO();
		try {
			iredaProjectNoList = iredaDAO.getAllIredaProjectNo();
			//System.out.println("Project No : " + iredaProjectNoList);
			
			for (String projectNo : iredaProjectNoList) {
				iredaProjectList.add(new IredaProject(projectNo).populateStateWiseTotalDataForOneDayWithThreading(date).populateGrandTotalForOneDay(date));
			}
		} catch (SQLException e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		
		request.setAttribute("iredaDetails", iredaProjectList);
		/*try {
			mapper.writeValue(file, iredaProjectList);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		request.setAttribute("date", date);

		return mapping.findForward("success");
	}
}
