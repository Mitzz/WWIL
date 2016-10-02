<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.enercon.global.utility.DateUtility"%>
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="com.enercon.admin.dao.AdminDao"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<HTML>
    <HEAD>    
<%!
	java.util.Date nextdate = null;
	public boolean showMasterData = false;
	boolean debug = false;
	String wecDescription = new String();
	String oneWecRemark = new String();	
%>
<%
	if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>
		<TITLE>DGR DashBoard Report</TITLE>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">
        <style type="text/css" media="print">
            .printbutton {
                visibility: hidden;
                display: none;
            }
        </style>

        <LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
        <%
   	response.setHeader("Pragma", "no-cache");
   	response.getOutputStream().flush();
   	response.getOutputStream().close();
   	//	String userid = session.getAttribute("loginID").toString();
   	//	String Customeridtxt = "";
        %>
        <script type="text/javascript">
        	//May be not called 
            function myFunction(ur, wty, wna, eb, wid, rd, state, cname, type) {
                var url = ur + "?wectype=" + wty + "&wecname=" + wna + "&ebid=" + eb + "&wecid=" + wid + "&rd=" + rd + "&state=" + state + "&cname=" + cname + "&type=" + type;
                window.open(url, 'name', 'height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');
            }
        </script>
    </HEAD>
    <BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0" marginwidth="0">
        <CENTER>
            <%
   	String custid = request.getParameter("id");
   	String stateid = request.getParameter("stateid");
   	String siteid = request.getParameter("siteid");
   	String rdate = request.getParameter("rd");
   	//String type = request.getParameter("type");
   	if (debug) {
   		System.out.println("custid:" + custid);
   		System.out.println("stateid:" + stateid);
   		System.out.println("siteid:" + siteid);
   		System.out.println("rdate:" + rdate);
   		//System.out.println("type:" + type);
   	}
   	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
   	int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
   	java.util.Date date = dateFormat.parse(rdate);
   	/* String prevdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
   	 String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY); */

   	String prevdate = "";
   	String nextDate = "";

   	//Used for previous button
   	prevdate = DateUtility.getBackwardDateInStringWRTDays(rdate,"dd/MM/yyyy", -1);
   			
   	//Used for next button
   	nextDate = DateUtility.getBackwardDateInStringWRTDays(rdate,"dd/MM/yyyy", 1);
   	nextdate = DateUtility.stringDateFormatToUtilDate(nextDate,"dd/MM/yyyy");
            %>
            <TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <tr>
                        <TD align="right" colspan="6">
                            <input type="button" value="Print" onClick="window.print()" class="printbutton">
                            <input align="right" type="button" class="printbutton" value="Excel" onClick=location.href="../Excel/GenerationReportByDay.jsp?stateid=<%=stateid%>&siteid=<%=siteid %>&id=<%=custid%>&type=<%="D"%>&rd=<%=rdate%>">
							<input align="right" type="button" class="printbutton" value="PDF" onClick=location.href="../PDF/GenerationReportByDayPDF.jsp?stateid=<%=stateid%>&siteid=<%=siteid %>&id=<%=custid%>&type=<%="D"%>&rd=<%=rdate%>"></td>
						</td>
                    </TR>
                    <TR>
                        <td colspan="6"></td>
                    </tr>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="90%" border=0>
                <tr>
                    <td colspan="6" height="5"></td>
                </tr>
                <tr>
                    <td colspan="2" align="left">
                    	<input type="button" class="printbutton"  style="color:white;font=bold;background-color:green" value="Previous"  onClick=location.href="CustomerReportChangeByDay.jsp?id=<%=custid%>&rd=<%=prevdate%>&siteid=<%=siteid%>&stateid=<%=stateid%>&type=<%="D"%>">
					</TD>
                    <td colspan="4" align="center"></td>
                    <td colspan="2"  align="right">
                    	<input type="button"  class="printbutton"  style="color:white;font=bold;background-color:green" value="Next" onClick=location.href="CustomerReportChangeByDay.jsp?id=<%=custid%>&rd=<%=nextDate%>&siteid=<%=siteid%>&stateid=<%=stateid%>&type=<%="D"%>">
					</TD>
                </TR>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <TR>
                        <TD class=SectionTitle colspan="6" align="center" noWrap>Power Generation Report</TD>
                    </TR>	
                </TBODY>
            </TABLE>
            <%
    List getEBHeadingList_1 = new ArrayList();
    //	List sitetranList = new ArrayList();
	CustomerUtil secutils = new CustomerUtil();
	//System.out.println("CustomerReportByChange....");
       	getEBHeadingList_1 = (List) secutils.getEBHeading(custid, rdate, stateid, siteid);
       	if (debug) {
       		System.out.println("---------------------1---------------------");
       		GlobalUtils.displayVectorMember(getEBHeadingList_1);
       	}
       	String cls = "TableRow1";
       	String ebid = "";
       	String cname = "";
       	String state = "";
       	String Remarks = "";
       	String RemarksWEC = "";
       	boolean ebScadaConnectivity = false;
       	for (int i = 0; i < getEBHeadingList_1.size(); i++) {//forloop1start
       		Vector getEBHeadingListEach_2 = new Vector();
       		getEBHeadingListEach_2 = (Vector) getEBHeadingList_1.get(i);
       		if (debug) {
       			System.out.println("---------------------2---------------------");
       			GlobalUtils.displayVectorMember(getEBHeadingListEach_2);
       		}
       		
       		Remarks = "";
       		RemarksWEC = "";
       		
       		//S_eb_id
       		ebid = (String) getEBHeadingListEach_2.get(5);
       		//S_customer_name
       		cname = (String) getEBHeadingListEach_2.get(0).toString().replaceAll("&", "and");
       		//S_State_name
       		state = (String) getEBHeadingListEach_2.get(3) + "-" + (String) getEBHeadingListEach_2.get(4);
       		
       		String requestDateInStringFormat = DateUtility.convertDateFormats(rdate, "dd/MM/yyyy", "dd-MMM-yyyy");
       		
       		//S_WEC_TYPE i.e. no active wec connected to eb
       		if (getEBHeadingListEach_2.get(8) != "0") {
%>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <tr class=TableTitle>
                    	<%-- Customer Name --%>
                        <td valign=middle class=TableCell align=center width=90% colspan="6"><%=getEBHeadingListEach_2.get(0)%></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell width=90% align=center colspan="6"></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell align=center width=90% colspan="6"></td>
                    </tr>

                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell1 align=left width=20%>Location:</td>
                        <%-- Site Name - State Name --%>
                        <td valign=middle align=left width=38% colspan=2><%=getEBHeadingListEach_2.get(3)%> - <%=getEBHeadingListEach_2.get(4)%></td>

                        <td valign=middle class=TableCell1 align=left width=18%>Machines:</td>
                        <%-- WecType --%>
                        <td valign=middle align=left width=24% colspan=2><%=getEBHeadingListEach_2.get(8)%> </td>
                        
                    <tr class=TableSummaryRow>
                        <td valign=middle width=90% align=left colspan="6"></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell1 align=left width=20%>Location Capacity:</td>
                        <%-- lcapacity --%>
                        <td valign=middle align=left width=38% colspan=2><%=getEBHeadingListEach_2.get(7)%> MW</td>
                        <td valign=middle class=TableCell1 align=left width=18%>Date:</td>
                        <%-- Report Date --%>
                        <td valign=middle align=left width=24% colspan=2><%=requestDateInStringFormat%></td>
                    </tr>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <TR>
                        <TD class=SectionTitle colspan="6" align="center" noWrap>WEC Generation</TD>
                    </TR>
                </TBODY>
            </TABLE>
<%
            }
%>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
                <TBODY>
                    <TR>
                        <TD>
                            <TABLE cellSpacing=1 cellPADding=2 width="100%" border=0>
                                <TBODY>
<%
       		/*Lull Hour Individual*/
       		List getWECDataList_3 = new ArrayList();

       		/* getWECDataList_3 = (List) secutils.getWECData(ebid, rdate, type); */
       		getWECDataList_3 = (List) CustomerUtility.getOneEBWECWiseInfoForOneDay(ebid, rdate);
       		//CustomerUtility.getOneEbWECDayWiseInfoForOneDayMeta(ebid, rdate);
       		if (debug) {
       			System.out.println("---------------------3---------------------");
       			GlobalUtils.displayVectorMember(getWECDataList_3);
       		}
       		cls = "TableRow1";
       		int totalLullHour = 0;
       		int totalLullMinute = 0;
       		int wecsize = getWECDataList_3.size();
       		System.out.println("getWECDataList_3.size()" + getWECDataList_3.size());
       		if (wecsize > 0) {//condition-1
%>
                                    <TR class=TableTitleRow>
                                        <TD class=TableCell1 width="14.28%">WEC Name</TD>
                                        <TD class=TableCell width="14.28%">Generation(KWH)</TD>
                                        <TD class=TableCell width="14.28%">Operating Hours</TD>
                                        <TD class=TableCell width="14.28%">Lull Hours</TD>
                                        <TD class=TableCell width="14.28%">Capacity Factor(%)</TD>
                                        <TD class=TableCell width="14.28%">Machine Availability(%)</TD>
                                        <TD class=TableCell width="14.28%">Grid Availability(%)</TD>
                                            
                                    </TR>
<%
				double totalCapacityFactor = 0;
				int noCapacityFactorCount = 0;
                for (int j = 0; j < getWECDataList_3.size(); j++) {
                	cls = ((j % 2) == 0) ? "TableRow2" : "TableRow1";
                      				
                    Vector getWECDataListEach_3_4 = new Vector();
                    getWECDataListEach_3_4 = (Vector) getWECDataList_3.get(j);
                      				
                    /*WEC Description*/
                    wecDescription = getWECDataListEach_3_4.get(0).toString();
                      				
                    int namelen = wecDescription.length();
                    oneWecRemark = getWECDataListEach_3_4.get(9).toString();
                      				
                    if (debug/*  || true */) {
                    	System.out.println("---------------------4---------------------");
                      	GlobalUtils.displayVectorMember(getWECDataListEach_3_4);
                    }

                    if (!getWECDataListEach_3_4.get(7).equals("-")) {
                    	totalCapacityFactor += Double.parseDouble((String) getWECDataListEach_3_4.get(7));
                    	noCapacityFactorCount++;
                    }
                      				
                    if ((!oneWecRemark.equals("NIL"))&& (oneWecRemark.length() >= namelen)) {
						RemarksWEC += "<b>" + oneWecRemark.substring(0, namelen - 1) + "</b>" + oneWecRemark.substring(namelen - 1) + "<br>";

                    }
%>
                                    <TR class=<%=cls%>>
                                        <TD class=TableCell1 align="left"><%=wecDescription%></TD>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(2)%></TD>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(3)%></TD>
<%
                	//Checking trial run
                	if (!getWECDataListEach_3_4.get(10).toString().equals("1")) {
       					String lullHourInString = (String) getWECDataListEach_3_4.get(4);
       					
       					
       					if (!lullHourInString.equals("00:00") && !lullHourInString.equals("-")) {

       						/*For each and every machine
       						 Storing lull hour and minute individually.*/
       						String[] splitHourMinute = lullHourInString.split(":");
       						totalLullHour = totalLullHour + Integer.parseInt(splitHourMinute[0]);
       						totalLullMinute = totalLullMinute + Integer.parseInt(splitHourMinute[1]);
       					}
                                   					
%>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(4)%></TD>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(7)%></TD>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(5)%></TD>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(6)%></TD>
<%
					} else {
%>
										<TD class=TableCell colspan="4">WEC Is In Stabilization Phase</TD>
<%	
					}
%>
                                    </TR>
<%
				}
       			double avgCapacityFactor = totalCapacityFactor/ noCapacityFactorCount;
       			int[] hourMinute = GlobalUtils.minuteToHour(totalLullMinute);
       			totalLullMinute = hourMinute[1];
       			totalLullHour = totalLullHour + hourMinute[0];
       			int totalLullMinuteLength = Integer.toString(totalLullMinute).length();
       			int totalLullHourLength = Integer.toString(totalLullHour).length();
       			String totalLullHourMinute = "";
       			if (totalLullMinuteLength == 1) {
       				totalLullHourMinute = totalLullHour + ":0" + totalLullMinute;
       			} else {
       				totalLullHourMinute = totalLullHour + ":" + totalLullMinute;
       			}
       			if (totalLullHourLength == 1) {
       				totalLullHourMinute = "0" + totalLullHourMinute;
       			}
       			getWECDataList_3.clear();
       			if (wecsize > 1 && noCapacityFactorCount != 0) {
       				/* List getEBWiseTotalList_5 = (List) secutils.getEBWiseTotal(ebid, rdate, type); */
       				List getEBWiseTotalList_5 = (List) CustomerUtility.getOneEBTotalForOneday(ebid, rdate);
       				if (debug) {
       					System.out.println("---------------------5---------------------");
       					GlobalUtils.displayVectorMember(getEBWiseTotalList_5);
       				}
       				for (int j = 0; j < getEBWiseTotalList_5.size(); j++) {
       					Vector getEBWiseTotalListEach_5_6 = new Vector();
       					getEBWiseTotalListEach_5_6 = (Vector) getEBWiseTotalList_5.get(j);
       					if (debug) {
       						System.out.println("---------------------6---------------------");
       						GlobalUtils.displayVectorMember(getEBWiseTotalListEach_5_6);
       					}
%>
                                    <TR class=TableSummaryRow>
                                    	<%-- Wec Total Size --%>
                                        <TD class=TableCell>Total:<%=getEBWiseTotalListEach_5_6.get(0)%></TD>
                                        
                                        <%-- Generation --%>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(1)%></TD>
                                        
                                        <%-- Operating Hour --%>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(2)%></TD>
<%
               			/*Trial Run Check.*/
               			if (getEBWiseTotalListEach_5_6.get(9).toString().equals("0")) {
%>
										<TD class=TableCell><%=totalLullHourMinute%></TD>
                                        <TD class=TableCell><%=new DecimalFormat("##.##").format(avgCapacityFactor)%></TD>
                                        
                                        <%-- Mavail --%>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(4)%></TD>
                                            		
                                        <%-- Gavail --%>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(5)%></TD>
<%  
						} else { 
%>
                                        <TD class=TableCell colspan=4>WEC Is In Stabilization Phase</TD>
<%	
						} 
%>
                                    </TR>
<%
					}
					getEBWiseTotalList_5.clear();
				}
%>
                                </TBODY>
                            </TABLE>
                        </TD>
                    </TR>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <TR>
                        <TD class=SectionTitle colspan="6" align="center" noWrap>EB Generation</TD>
                    </TR>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
                <TBODY>
                    <TR>
                        <TD>
                            <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
                                <TBODY>
                                    <TR class=TableTitleRow>
                                        <TD class=TableCell width="16%">Description</TD>
                                        <TD class=TableCell width="10%">KWH Export</TD>
                                        <TD class=TableCell width="14%">KWH Import</TD>
                                        <TD class=TableCell width="11%">Net KWH</TD>
                                    </TR>
<%
				List getEBDataList_7 = (List) secutils.getEBData(ebid, rdate, "D");
       			if (debug) {
       				System.out.println("---------------------7---------------------");
       				GlobalUtils.displayVectorMember(getEBDataList_7);
       			}
       			cls = "TableRow1";
       			//Eb Data
       			if (getEBDataList_7.size() > 0) {
       				for (int j = 0; j < getEBDataList_7.size(); j++) {
       					Vector getEBDataListEach_7_8 = new Vector();
       					getEBDataListEach_7_8 = (Vector) getEBDataList_7.get(j);
       					if (debug) {
       						System.out.println("---------------------8---------------------");
       						GlobalUtils.displayVectorMember(getEBDataListEach_7_8);
       					}

       					/* System.out.println("getEBDataListEach_7_8");
       					 GlobalUtils.displayVectorMember(getEBDataListEach_7_8); */

       					int rem = 1;
       					rem = j % 2;
       					
       					//Remarks
       					if (!getEBDataListEach_7_8.get(4).toString().equals("NIL") && !Remarks.equals(".")) {
   							if (Remarks.length() > 15) {
   								Remarks = "<b>" + getEBDataListEach_7_8.get(4).toString().substring(0, 15) + "</b>" + getEBDataListEach_7_8.get(4).toString().substring(15);
   							} else {
   								Remarks = "<b>" + getEBDataListEach_7_8.get(4).toString();
   							}
       					}
%>
                                    <TR class=<%=rem == 0 ? "TableRow2" : "TableRow1"%>>
                                    	<%--  --%>
                                        <TD class=TableCell><%=getEBDataListEach_7_8.get(0) %> </TD>
                                        
                                        <%-- KWHEXPORT --%>
                                        <TD class=TableCell><%=getEBDataListEach_7_8.get(1) %> </TD>
                                        <%-- KWHIMPORT --%>
                                        <TD class=TableCell><%=getEBDataListEach_7_8.get(2) %> </TD>
                                        <%-- DIF --%>
                                        <TD class=TableCell><%=getEBDataListEach_7_8.get(3) %></TD>
                                    </TR>
<%
					}
					getEBDataList_7.clear();
				} //Eb Data Present 
				else {
%>
                                    <TR class=<%=cls%>>
                                        <TD class=TableCell>0</TD>
                                        <TD class=TableCell>0</TD>
                                        <TD class=TableCell>0</TD>
                                        <TD class=TableCell>0</TD>
                                    </TR>
<%
				} //Eb Data Not Present
			}//condition-1 
			else {//else65start
       			//No active WEC
       			if (getEBHeadingListEach_2.get(8) != "0") {
       				//System.out.println("0000000000000000000000000000000ebid:" + ebid);
       				ebScadaConnectivity = true;
       				java.util.Date todaysDate = new Date();
       				System.out.println("dkjsdhk: " + nextdate.after(todaysDate));
       				if (nextdate.after(todaysDate)) {
%>
                                    <tr>
                                        <td>
                                            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                                                <TBODY>
                                                    <TR>
                                                        <TD class=SectionTitle colspan="6" align="center" noWrap>Data is not available.</TD>
                                                    </TR>
                                                </TBODY>
                                            </TABLE>
                                        </td>
                                    </tr>
<%
					} else {
%>

                                    <!-- ------------------------------------------------------------------------------- -->
                                    <tr>
                                        <td>
                                            <TABLE  cellSpacing=0 cellPadding=0 width="100%" border=1 bgColor=#555555>
                                                <TBODY>
                                                    <TR class=TableTitleRow>
                                                        <TD class=TableCell1 width="14.28%">WEC Name</TD>
                                                        <TD class=TableCell width="14.28%">Generation(KWH)</TD>
                                                        <TD class=TableCell width="14.28%">Operating Hours</TD>
                                                        <TD class=TableCell width="14.28%">Lull Hours</TD>
                                                        <TD class=TableCell width="14.28%">Capacity Factor(%)</TD>
                                                        <TD class=TableCell width="14.28%">Machine Availability(%)</TD>
                                                        <TD class=TableCell width="14.28%">Grid Availability(%)</TD>
                                                            
                                                    </TR>
                                                </TBODY>
<%
						if (debug) {
                        	System.out.println(secutils.getWECByEBId(ebid,rdate));
						}
%>
						<%=secutils.getWECByEBId(ebid, rdate)%>
                                            </TABLE>
                                        </td>
                                    </tr>
                                    <!-- ------------------------------------------------------------------------------- -->
<%
					}
				} 
			}//else65end
%> 
                				</TBODY>
            				</TABLE>
        				</TD>
    				</TR>
				</TBODY>
			</TABLE>
<%
			if (getEBHeadingListEach_2.get(8) != "0") {
%>
			<TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
			    <TBODY>
			        <TR>
			            <TD class=SectionTitle colspan="6" align="center" noWrap>Remarks</TD>
			        </TR>
			    </TBODY>
			</TABLE>
			<TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
			    <TBODY>
			        <tr class=TableRow1>
			            <td valign=middle  align=center width=90% colspan="6"><font size="2"><%=Remarks%></font></td>
			        </tr>
			        <tr class=TableRow2>
			            <td valign=middle  align=center width=90% colspan="6"><font size="2"><%=RemarksWEC%></font></td>
			        </tr>
			    </TBODY>
			</TABLE>
<%
			}
			if (Remarks.equals("") || Remarks.equals(".")) {
				Remarks = "";
			}
%>
			
<%
			if (ebScadaConnectivity) {
				java.util.Date todaysDate = new Date();
				if (nextdate.after(todaysDate)) {
				} else {
%>		
			<TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
			    <TBODY>
			        <tr class=TableRow1>
			            <td valign=middle  align=center width=90% colspan="6"><font style='color:red;' size="2">Connectivity failure during data transfer</font></td>
			        </tr>
			    </TBODY>
			</TABLE>
<%
				}
			}
			ebScadaConnectivity = false;
		
%>
			<P></P>
			<P></P>
<%
		}//forloop1end
    	getEBHeadingList_1.clear();
%>
			<P></P>
		</CENTER>
	</BODY>
</HTML>
