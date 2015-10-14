<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<%@page import="com.enercon.model.ireda.IredaProject"%>
<%@page import="com.enercon.global.utility.DateUtility"%>
<%@page import="com.enercon.model.WECData"%>
<%@page import="com.enercon.dao.WECParameterVoDao"%>
<%@page import="com.enercon.admin.dao.WcareDao"%>
<%@page import="com.enercon.reports.dao.ReportDao"%>
<%@page import="com.enercon.connection.WcareConnectorManager"%>
<%@page import="com.enercon.global.utility.FilePathUtility"%>
<%@page import="com.enercon.reports.pojo.GenerationReport"%>
<%@page import="com.enercon.global.utils.CallJobToPushScadaData"%>
<%@page import="com.enercon.admin.machineGridAutomation.MachineGridAvailabilityRetriever"%>
<%@page import="com.enercon.global.utility.TimeUtility"%>
<%@page import="com.enercon.global.utility.NumberUtility"%>
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%@page import="com.enercon.global.utils.CallSchedulerForMissingScadaData"%>
<%@page import="com.enercon.global.utils.CallSchedulerForSendingMail"%>
<%@page import="com.enercon.global.utility.JDBCUtilsTest"%>
<%@page import="com.enercon.global.utils.CallScheduler"%>
<%@page import="com.enercon.admin.dao.AdminDao"%>
<%@page import="com.enercon.global.utility.DatabaseUtility"%>
<%@page import="com.enercon.admin.metainfo.SiteMetaInfo"%>
<%@page import="com.enercon.admin.metainfo.StateMetaInfo"%>
<%@page import="com.enercon.admin.metainfo.EBMetaInfo"%>
<%@page import="com.enercon.admin.metainfo.WECMetaInfo"%>
<%@page import="com.enercon.admin.metainfo.CustomerMetaInfo"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%@page import="com.enercon.customer.dao.CustomerDao"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>

<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
        <%
        
        if (session.getAttribute("loginID") == null) {
		    response.sendRedirect(request.getContextPath());
		}
        %>
		<%
         	/* IredaProject project = new IredaProject("1919");
                         System.out.println(project);
                 		try {
                 	project.populateStateWiseTotalDataForOneDay("10-JAN-2015");
                 	project.populateGrandTotalForOneDay("10-JAN-2015");
                 		} catch (SQLException e) {
                 	e.printStackTrace();
                 		} */
		%>
        <%
        //new ReportDao().test();
        %>
        
        <%
		/* Set<String> wecIds = new HashSet<String>();
    	
    	wecIds.add("111100004012324");
    	wecIds.add("1109000007");
    	wecIds.add("1108000047");
    	wecIds.add("11080000482342");
    	wecIds.add("1109000008");
    	wecIds.add("1108000033");
    	wecIds.add("1108000034");
    	wecIds.add("1112000031");
    	wecIds.add("1112000032");
    	wecIds.add("1108000035");
    	wecIds.add("1108000036");
    	wecIds.add("1108000025");
    	wecIds.add("1108000026");
    	wecIds.add("1108000027");
    	wecIds.add("1108000028");
    	wecIds.add("1109000009");
    	wecIds.add("1109000024");
    	wecIds.add("1112000033");
    	wecIds.add("1108000040");
    	wecIds.add("1111000038");
    	wecIds.add("1111000039");
    	wecIds.add("1109000010");
    	wecIds.add("1108000041");
    	wecIds.add("1108000042");
    	wecIds.add("1109000011");
    	
    	System.out.println(new WECDao().getManyWECsOneDateDateWiseTotal(wecIds, "21-JUL-2014")); */
        %>
        
        <%
		
        //System.out.println( new CustomerDao().getAllScadaSrNoUploaded(DateUtility.stringDateFormatToSQLDate("21-JUN-2014", "dd-MMM-yyyy")));
        //System.out.println( new CustomerDao().getScadaConnectedStatewiseUploadedCount(DateUtility.stringDateFormatToSQLDate("23-JUL-2014", "dd-MMM-yyyy")));
        
        %>
        <%
        /* String wecId = "1110000131";
        String readingDate = "21-APR-2014";
        String fromDate = "01-APR-2014";
        String toDate = readingDate;
        		
		Set<String> wecIds = new HashSet<String>();
                
        wecIds.add("1110000131");
        wecIds.add("1110000129");
        
        WECDao wecDao = new WECDao();
        try{
	       
        	System.out.println(wecDao.getOneWECOneDayInfoOrTotal(wecId, readingDate));
	       	System.out.println(wecDao.getOneWECManyDatesWECWiseTotal(wecId, fromDate, toDate));
	       	System.out.println(wecDao.getOneWECManyDatesDateWiseTotal(wecId, fromDate, toDate));
	       	System.out.println(wecDao.getManyWECsOneDateWECWiseTotal(wecIds, readingDate));
	       	System.out.println(wecDao.getManyWECsOneDateDateWiseTotal(wecIds, readingDate));
	       	System.out.println(wecDao.getmanyWECsManyDatesDateWiseTotal(wecIds, fromDate, toDate));
	       	
        }
        catch(Exception e){
        	e.printStackTrace();
        } */
                
                //GlobalUtils.displayVectorMember(WECMetaInfo.getManyWECsWECWiseTotalForBetweenDaysMeta(wecIds, "01-APR-2014", "17-APR-2014"));
        %>
        <%
        
        
        String customerFullName = "VISH WIND INFRASTRUCTURE LLP";
        String customerShortName = "Vish Wind LLP";
        Set<String> customerIds = new HashSet<String>();
        
        customerIds.add("1009000010");
		/* customerIds.add("1009000004");
		customerIds.add("1107000004");
		customerIds.add("1107000002");
		customerIds.add("1107000003");
		customerIds.add("1205000001");
		customerIds.add("1204000008");
		customerIds.add("1204000009");
		customerIds.add("1207000002");
		customerIds.add("1304000001");
		customerIds.add("1304000029"); */
		
		//new CallSchedulerForSendingMail().sendMail(Arrays.asList(new GenerationReport(customerFullName, customerShortName , customerIds)));
		
			/*-----------------------------------------------*/
		/* try{
			
		}
		catch(Exception e){
			e.printStackTrace();
		} */
			/*-----------------------------------------------*/
			
			/*-----------------------------------------------*/
			
			/*-----------------------------------------------*/
			
		
		
		//System.out.println("-------" + FilePathUtility.getInstance().getPath("spring\\bean-config.xml"));
		/* WcareConnectorManager.getInstance(); */
        
        /*-----------------Start of Leftout Data*/
        //new CallJobToPushScadaData().executeTest();
        /*-----------------End of Leftout Data*/
        
        /*-----------------Start of Missing Scada Data*/
        /* CallSchedulerForMissingScadaData c = new CallSchedulerForMissingScadaData();
        c.callScheduleForMissingScadaData("28/11/2013"); */
        /*-----------------End of Missing Scada Data*/
       	
        /*-------------------Start of Utility Files*/
        
        String query = "";
        String fileName = "";
        String absolutePath = "";
        
        
        //DatabaseUtility.getUserViewSourceCodeInFile();
        
         //DatabaseUtility.storingAllUserFunctionSourceInFile();
        //DatabaseUtility.getTableDataWhoseRecordCountIsInGivenRange(25000);
        
        /* query = "Select S_Under_1, S_Under_2, S_Under_3,S_Transaction_Name, S_Url From Tbl_Transaction order by N_Seq_No";
        fileName = "TransactionURLMapping";
        absolutePath = "D:\\Mithul\\Google Drive\\WWIL\\WCARE\\Utility Files\\" + fileName + ".html";
        DatabaseUtility.getSQLQueryResultInHTMLFile(query, absolutePath);
        
        query = "Select wecmaster.S_wecshort_descr,wecmaster.S_wec_id,wecmaster.S_Technical_No,plantmaster.S_Serial_No,plantmaster.s_wec_name,plantmaster.s_location_no,plantmaster.s_plant_no From Tbl_Wec_Master Wecmaster,Scadadw.Tbl_Plant_Master Plantmaster  Where Wecmaster.S_Wecshort_Descr = Plantmaster.S_Wec_Name order by plantmaster.s_location_no,plantmaster.s_plant_no";
        fileName = "ECARE_SCADA_WECMapping";
        absolutePath = "D:\\Mithul\\Google Drive\\WWIL\\WCARE\\Utility Files\\" + fileName + ".html";
        DatabaseUtility.getSQLQueryResultInHTMLFile(query, absolutePath); 
        
        query = "Select * From Customer_Meta_Data";
        fileName = "Customer_Meta_Data";
        absolutePath = "D:\\Mithul\\Google Drive\\WWIL\\WCARE\\Utility Files\\" + fileName + ".html";
        DatabaseUtility.getSQLQueryResultInHTMLFile(query, absolutePath); */
        
        /*-------------------End of Utility Files*/

        
        /* DatabaseUtility.que.delete(0, DatabaseUtility.que.length());
        CallScheduler c = new CallScheduler();
        c.callSchedule("Testing", "03/08/2013");
        System.out.println(DatabaseUtility.que); */
        /* String query = "Select * From Customer_Meta_Data";
        DatabaseUtility.getSQLQueryResultInHTMLFile(query, "Customer_Meta_Data"); */
        
        //CustomerUtility.getStateWiseTotalForOneMonthBasedOnCustomerIdMeta("1009000010", 9, 2013);
        
        //WECMetaInfo.getOneWECInfoForOneDayMeta("1110000044", "14-JUN-2013");
        
        //WECMetaInfo.getOneWECTotalForOneMonthMeta("1110000044", 12, 2010);
        
        //WECMetaInfo.getOneWECTotalForOneYearMeta("1110000044", 2014);
        
        //WECMetaInfo.getOneWECTotalForBetweenDaysMeta("1110000044", "01-JAN-2013", "14-JUN-2013");
        
        //WECMetaInfo.getOneWECInfoDayWiseForOneMonthMeta("1110000044", 7, 2013);
        
        //WECMetaInfo.getOneWECInfoDayWiseForOneYearMeta("1110000044", 2013);
        
        /* ArrayList<String> wecIds = new ArrayList<String>();
        
        wecIds.add("1110000044");
        wecIds.add("1110000059");
        wecIds.add("1110000060"); */
        
        //WECMetaInfo.getManyWECTotalForOneDayMeta(wecIds, "15-JUN-2013");
        
        //WECMetaInfo.getManyWECTotalForOneMonthMeta(wecIds, 7, 2013);
        
        //WECMetaInfo.getManyWECTotalForOneYearMeta(wecIds, 2014);
        
        //WECMetaInfo.getManyWECTotalForBetweenDaysMeta(wecIds, "01-jun-2013", "21-jan-2013");
        
        /* for (ArrayList<Object> s : StateMetaInfo.getStateWiseTotalForOneMonthBasedOnCustomerIdMeta("1009000010", 5, 2013)){
        	CustomerUtility.display(s);
        } */
        
        /* for (ArrayList<Object> s : SiteMetaInfo.getSiteWiseTotalForOneMonthBasedOnStateIdCustomerIdMeta("1009000010", "1000000080", 9, 2013)){
    		CustomerUtility.display(s);
    	} */
    	
    	/* List<Object> d = new ArrayList<Object>();
    	d = CustomerUtility.getStateWiseTotalForOneMonthBasedOnCustomerIdMeta("1000000736", 2, 2013);
    	
    	for(Object v : d){
    		Vector<Object> vector = new Vector<Object>();
    		vector = (Vector<Object>)v;
    		if(vector.size() > 5){
    			String ss = NumberUtility.formatNumber((Long)vector.get(6));
    			String s = TimeUtility.convertMinutesToTimeStringFormat(((Long)vector.get(8)), ":")  ;
    			System.out.println("Generation:" + ss + ",Operating Hrs:" + s);
    		}
    		//GlobalUtils.displayVectorMember(vector);
    	} */
    	
    	/* ArrayList<String> wecIds = new ArrayList<String>();
    	
    	wecIds.add("1111000040");
    	wecIds.add("1109000007");
    	wecIds.add("1108000047");
    	wecIds.add("1108000048");
    	wecIds.add("1109000008");
    	wecIds.add("1108000033");
    	wecIds.add("1108000034");
    	wecIds.add("1112000031");
    	wecIds.add("1112000032");
    	wecIds.add("1108000035");
    	wecIds.add("1108000036");
    	wecIds.add("1108000025");
    	wecIds.add("1108000026");
    	wecIds.add("1108000027");
    	wecIds.add("1108000028");
    	wecIds.add("1109000009");
    	wecIds.add("1109000024");
    	wecIds.add("1112000033");
    	wecIds.add("1108000040");
    	wecIds.add("1111000038");
    	wecIds.add("1111000039");
    	wecIds.add("1109000010");
    	wecIds.add("1108000041");
    	wecIds.add("1108000042");
    	wecIds.add("1109000011");
    	
    	GlobalUtils.displayVectorMember(EBMetaInfo.getOneEbWECDayWiseInfoForOneDayMeta("1108000006", "21-AUG-2013")); */
        
    	
    	//MachineGridAvailabilityRetriever.getMGA();
        	String rn = "";
        	String de = "";
        	String ri = "";
        	DynaBean dynabean = (DynaBean) session.getAttribute("dynabean");
        	if (dynabean != null) {
        	    rn = dynabean.getProperty("MsgHeadtxt").toString();
        	    de = dynabean.getProperty("MsgDescriptiontxt").toString();
        	    ri = dynabean.getProperty("MsgIdtxt").toString();
        	    session.removeAttribute("dynabean");
        	}
        %>
        <%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	//String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",ex);
		%>
        <script type="text/javascript">
            function checkWholeForm() {

                var why = "";
                why += checkUsername(document.forms[0].MsgHeadtxt);
                why += checkUsername(document.forms[0].MsgDescriptiontxt);

                why += checkUsername(document.forms[0].MsgSigntxt);

                if (why != "") {
                    alert(why);
                    return false;
                }

                return true;

            }
            function checkDropdown(choice) {
                var error = "";
                if (choice == 0) {
                    error = "You didn't choose an option from the drop-down list.\n";
                }
                return error;
            }
            function checkUsername(strng) {
                var SDate = strng.value;

                var error = "";
                if (SDate == '') {
                    error = "All Field Are Mandatory.\n";
                }
                return error;
            }

        </script>
    </head>
    <body>
        <%

        %>
        <div align="center">
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
                <tr width="100%">
                    <td width="100%" align="center">
                        <form action="StdMail.jsp" name="StdMail" method="post" >
                            <table align="center" border="0" cellpadding="0" cellspacing="0" width="662">
                                <tbody>
                                    <tr>
                                        <td class="newhead1"></td>
                                        <th width="352" class="headtext">An Email To Service Support</th>
                                        <td width="65"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
                                        <td width="173" class="newhead3">&nbsp;</td>
                                        <td width="36" class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
                                    </tr>
                                    <tr>
                                        <td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
                                        <td colspan="3">
                                            <img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                <tbody>
                                                    <tr>
                                                        <td bgcolor="#dbeaf5">
                                                            <table border="0" cellpadding="2" cellspacing="1" width="100%">
                                                                <tbody>						
                                                                    <tr class="bgcolor"> 
                                                                        <td id="t_street_address" width="234">&nbsp;Query &nbsp;Subject:</td>
                                                                        <td class="bgcolor" width="345">
                                                                            <input type="text" id="MsgHeadtxt" name="MsgHeadtxt" size="55" value="<%=rn%>" class="BoxBorder" maxlength="55" />
                                                                        </td>
                                                                    </tr>
                                                                    <tr class="bgcolor"> 
                                                                        <td id="t_street_address">&nbsp;Query Description:</td>
                                                                        <td class="bgcolor">
                                                                            <input type="text" id="MsgDescriptiontxt" name="MsgDescriptiontxt" class="BoxBorder" size="55" value="<%=de%>" />
                                                                        </td>
                                                                    </tr>
                                                                    <tr class="bgcolor"> 
                                                                        <td id="t_street_address">&nbsp;Sender Signature(Please Specify your Name,Designation and Contact No.):</td>
                                                                        <td class="bgcolor"><input type="text" id="MsgSigntxt" name="MsgSigntxt" class="BoxBorder" size="55" value="<%=de%>" /></td>
                                                                    </tr>

                                                                    <tr class="bgcolor"> 
                                                                        <td colspan="2">
                                                                            <html:errors /><B><font color="red">
                                                                                    <%String str = (String) request.getParameter("msg");%>
                                                                                    <%if (str != null) {%>
                                                                                    <%=str%>
                                                                                    <%}%>
                                                                                </font></B>
                                                                        </td>
                                                                    </tr>	
                                                                </tbody>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                        </td>
                                        <td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
                                    </tr>
                                    <tr>
                                        <td width="36">
                                            <img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10">
                                        </td>
                                        <td colspan="4" align="right" bgcolor="#006633">
                                            <table border="0" cellpadding="0" cellspacing="0">
                                                <tbody>
                                                    <tr>
                                                        <td class="btn" width="100">
                                                            <input type="hidden" id="MsgIdtxt" name="MsgIdtxt" value="<%=ri%>" />
                                                            <input type="hidden" name="Admin_Input_Type" value="StdMail"  />	
                                                            <input name="Submit" type="submit" class="btnform" value="Sent" onClick="return checkWholeForm()"/>
                                                        </td>
                                                        <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
                                                        <td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
                                                        <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
                                                    </tr>
                                                </tbody>
											</table>
                                        </td>
                                    </tr>
                                </tbody>
                           	</table>
                        </form>	
                    </td>		
                </tr>
                <tr>
                    <td align="center">
                        <table border="1" cellpadding="0" cellspacing="0" width="800">
                        	<tbody>
                                <% 	
	CustomerUtil custutils = new CustomerUtil();
    List tranListData = new ArrayList();
    tranListData = (List) custutils.getQueryDetail(session.getAttribute("loginID").toString(), "C");
                                %>
                                <tr align='center' height='20'>
                                	<th class='detailsheading' width='30'>S.N.</th>
                                	<th class='detailsheading' width='100'>Subject</th>
                                	<th class='detailsheading' width='100'>Description</th>
                                	<th class='detailsheading' width='100'>Sender signature</th>
                                	<th class='detailsheading' width='50'>Status</th>
                                	<th class='detailsheading' width='240'>Solution</th>
								</tr>
                                <%   
	for (int k = 0; k < tranListData.size(); k++) {
       Vector wecdata = new Vector();
       wecdata = (Vector) tranListData.get(k);
                                %>	
                                <tr align='center' height='20' class='detailsbody1'>
                                	<td align='left'><%=k + 1%></td>
                                	<td align='left'><%=wecdata.get(1)%></td>
                                	<td align='left'><%=wecdata.get(2)%></td>
                                	<td align='left'><%=wecdata.get(3)%></td>
                                	<td align='left'><%=wecdata.get(5)%></td>
                                	<td align='left'><%=wecdata.get(4)%></td>
								</tr>
                                <% 
	}
                                %>
							</tbody>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>