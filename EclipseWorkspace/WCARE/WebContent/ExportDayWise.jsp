<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "Export.xls";
	response.addHeader("Content-Disposition", "attachment; filename=\""+ file + "\"");
%>


<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.enercon.admin.dao.*"%>


<HTML>
<HEAD>
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>

<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	// String userid = session.getAttribute("loginID").toString();
	// String Customeridtxt = "";
%>
</HEAD>
<BODY>

<%
	String custid = request.getParameter("id");
	String stateid = request.getParameter("stateid");
	String siteid = request.getParameter("siteid");
	String rdate = request.getParameter("rd");
	// String type = request.getParameter("type");
%> <%
 	List tranList = new ArrayList();
 	// List sitetranList = new ArrayList();
 	CustomerUtil secutils = new CustomerUtil();
 	tranList = (List) secutils.getEBHeading(custid, rdate, stateid,
 			siteid);
 	String cls = "TableRow1";
 	String ebid = "";
 	String cname = "";
 	String state = "";
 	String Remarks = "NA";
 	String nedate = "";
 	String pdate1 = "";
 	List wecListData = new ArrayList();
 	List ebListData = new ArrayList();
 	for (int i = 0; i < tranList.size(); i++) {
 		String RemarksWEC = "";
 		Vector v = new Vector();
 		v = (Vector) tranList.get(i);
 		ebid = (String) v.get(5);

 		cname = (String) v.get(0).toString().replaceAll("&", "and");
 		state = (String) v.get(3) + "-" + (String) v.get(4);
 %> <%
 		String adate="01/04/3009";
 		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
 		java.util.Date ffd = format.parse(rdate);
 		java.util.Date afd = format.parse(adate);
 		long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
 		int month = ffd.getMonth() + 1;
 		int day = ffd.getDay();

 		String year = rdate.substring(6);
 		//String syear="";

 		int cyear = ffd.getYear() - 100;
 		int nyear = cyear;

 		String monthname = "";
 		if (month == 4) {
 			monthname = "APRIL";
 		}
 		if (month == 3) {
 			monthname = "MARCH";
 		}
 		if (month == 1) {
 			monthname = "JANUARY";
 		}
 		if (month == 2) {
 			monthname = "FEBRUARY";
 		}

 		if (month == 5) {
 			monthname = "MAY";
 		}

 		if (month == 6) {
 			monthname = "JUNE";
 		}

 		if (month == 7) {
 			monthname = "JULY";
 		}

 		if (month == 8) {
 			monthname = "AUGUST";
 		}

 		if (month == 9) {
 			monthname = "SEPTEMBER";
 		}

 		if (month == 10) {
 			monthname = "OCTOBER";
 		}

 		if (month == 11) {
 			monthname = "NOVEMBER";
 		}

 		if (month == 12) {
 			monthname = "DECEMBER";
 		}
 %> <BR>
<TABLE cellSpacing="1" cellPadding="1" width="90%" border="1">
	<TBODY>

		<tr>
			<td vAlign="middle" align="center" width="100%" colspan="8"><b><%=v.get(0)%></b></td>
		</tr>
		<tr>
			<td vAlign="middle" width="100%" align="center" colspan="8"></td>
		</tr>

		<tr>
			<td vAlign="middle" align="center" width="100%" colspan="8"></td>
		</tr>

		<tr>
			<td vAlign="middle" align="left" width="20%">Location:</td>
			<td vAlign="middle" align="left" width="38%" colspan="3"><%=v.get(3)%> - <%=v.get(4)%></td>

			<td vAlign="middle" align="left" width="18%" colspan="2">Machines:</td>
			<td vAlign="middle" align="left" width="24%" colspan="2"><%=v.get(8)%></td>
		</tr>
		
		<tr>
			<td vAlign="middle" width="100%" align="center" colspan="8"></td>
		</tr>
		<tr>
			<td vAlign="middle" align="left" width="20%">Location Capacity:</td>
			<td vAlign="middle" align="left" width="38%" colspan="3"><%=v.get(7)%> MW</td>

			<td vAlign="middle" align="left" width="18%" colspan="2">Month:</td>
			<td vAlign="middle" align="left" width="24%" colspan="2"><%=monthname%>-<%=year%></td>
		</tr>

	</TBODY>
</TABLE>

<%
		List tranListData = new ArrayList();
		tranListData = (List) secutils.getWECDateWise(ebid, rdate);
		int tsize = 0;
		cls = "TableRow1";
		//System.out.println("tranListData" + rdate);
		//System.out.println("tranListData" + tranListData.size());
		int wecsize = tranListData.size();
		if (wecsize > 0) {
 			RemarksWEC = "";
 			String pdate = "";
 			String ndate = "";
            String nousevar="0";
 			AdminDao ad = new AdminDao();

 			for (int j = 0; j < tranListData.size(); j++) {

	 		int rem = 1;
	 		rem = j % 2;
	
	 		if (rem == 0)
	 			cls = "TableRow2";
	 		else
	 			cls = "TableRow1";
	
	 		Vector vdata = new Vector();
	 		vdata = (Vector) tranListData.get(j);
	 		ndate = (String) vdata.get(9);
	 		pdate1 = ad.convertDateFormat(ndate, "dd-MM-yyyy","dd/MM/yyyy");
	
	 		//System.out.println("pdate"+pdate);
	 		//System.out.println("ndate"+ndate);
	
	 		if (ndate.equals(pdate))
	
	 		{
	 %> <%
	 		tsize = tsize + 1;
	 		} else {

 			if (j != 0) {
	 				nedate = ad.convertDateFormat(pdate,
	 				"dd-MM-yyyy", "dd/MM/yyyy");
	 				wecListData.clear();
	 				if (tsize > 0) {
			 			wecListData = (List) secutils.getEBWiseTotal(ebid, nedate, "D");
			 			for (int k = 0; k < wecListData.size(); k++) {
			 				Vector vdt = new Vector();
			 				vdt = (Vector) wecListData.get(k);
			 				//String name = (String)vdata.get(0);
							 %>
							<TR>
								<TD>Total:<%=vdt.get(0)%></TD>
								<TD><%=vdt.get(1)%></TD>
								<TD><%=vdt.get(2)%></TD>
								<TD><%=vdt.get(3)%></TD>
								<TD><%=vdt.get(6)%></TD>
								
								<TD><%=vdt.get(4)%></TD>
						        <TD><%=vdt.get(5)%></TD>								
							</TR>
							
							<%
						}
					}
					tsize = 0;
					
					if(nousevar.equals("Test")){
							%>
							<TABLE>
							<TBODY>
								<TR>
									<TD>
										<TABLE>
										<TBODY>
							<%} %>
					                    </TBODY>
					                    </TABLE>
					                </TD>
					            </TR>
					        </TBODY>
					       </TABLE>
					
					<%
									ebListData.clear();
									ebListData = (List) secutils.getEBData(ebid,
									nedate, "D");
									cls = "TableRow1";
									//System.out.println(ebListData.size());
									if (ebListData.size() > 0) {
					%>
									<SPAN><center><B>EB Data</B></center></SPAN><BR>
										<TABLE height="6"  border="1">
											<TBODY>
												<TR>
													<TD></TD>
												</TR>
											</TBODY>
										</TABLE>
										<TABLE  width="90%" >
											<TBODY>
												<TR>
													<TD>
													<TABLE  width="100%" border="1">
														<TBODY>
															<TR>
																<TD width="16%" colspan="2">Description</TD>
																<TD width="27%" colspan="2">KWH Export</TD>
																<TD width="27%" colspan="2">KWH Import</TD>
																<TD width="27%" colspan="2">Net KWH</TD>									
															</TR>
													<%
									}
									%>
									<%
									for (int c = 0; c < ebListData.size(); c++) {
										Vector vedata = new Vector();
										vedata = (Vector) ebListData.get(c);
										String name = (String) vedata.get(0);
		
										int rem1 = 1;
										rem1 = c % 2;
										//Remarks = (String) vedata.get(4);
										if (!vedata.get(4).toString().equals("NIL"))
											if (!Remarks.equals(".")) {
												{
											Remarks = (String) vedata
													.get(4);
		
												}
											}
										if (rem1 == 0)
											cls = "TableRow2";
										else
											cls = "TableRow1";
										%>
					
										<TR >
											<TD colspan="2"><%=vedata.get(0)%></TD>
											<TD colspan="2"><%=vedata.get(1)%></TD>
											<TD colspan="2"><%=vedata.get(2)%></TD>
											<TD colspan="2"><%=vedata.get(3)%></TD>
										</TR>
					
										<%
									}
									%>
				
								</TBODY>
							</TABLE>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
		
					<SPAN ><center>Remarks </center></SPAN><BR>
					<TABLE width="90%"  border="1">
					<TBODY>
			
					<%
								if (Remarks.equals("")) {
								Remarks = "";
								}
							if (Remarks.equals(".")) {
						Remarks = "";
							}
			%>
			<%
							if (RemarksWEC.equals("")) {
							RemarksWEC = "";
								}
					if (RemarksWEC.equals(".")) {
							 RemarksWEC = "";
					}
				%>
				<tr>
					<td vAlign="middle" class="TableCell11" align="center" width="100%" colspan="8"><%=Remarks%></td>
				</tr>
				<tr>
					<td vAlign="middle" class="TableCell11" align="center" width="100%" colspan="8"><%=RemarksWEC%></td>
				</tr>
				<%
				Remarks = "";
				RemarksWEC = "";
				%>
			
			</TBODY>
		</TABLE>
		<%
		}
	%>
	
<SPAN ><B>Generation Date: <%=ndate%> </B></SPAN><BR>

<TABLE width="90%"  border="1">
	<TBODY>
		<TR>
			<TD>
				<TABLE  width=""100%"" border="1">
					<TBODY>

						<TR>
							<TD width="14.28%">WEC Name</TD>
	
							<TD width="14.28%">Generation</TD>
							<TD width="14.28%">Operating Hours</TD>
							<TD width="14.28%">Lull Hours</TD>
							<TD width="14.28%">Capacity Factor(%)</TD>
							<TD width="14.28%">Machine Availability(%)</TD>
							<% if(diff<0){ %>
							<TD width="14.28%" colspan="2">Grid Availability(%)</TD>
							<%} else{ %>
							<TD width="14.28%">Grid Internal Availability(%)</TD>
							<TD width="14.28%">Grid External Availability(%)</TD>
							<%} %>
	
						</TR>
						<%
								}
								
								String name = (String) vdata.get(0);
								//String gen =String.parse(vdata.get(1));
								//String gen ="23.00";
								//String gen2 =gen.toString();
	
								//String ghrs = (String)vdata.get(2);
								//String lhrs = (String)vdata.get(3);
								//String mavdataail = (String)vdata.get(4);
								//String gavdataail = (String)vdata.get(5);
								//String cfactor = (String)vdata.get(6);
								//String stateid = (String)vdata.get(7);
						%>

						<TR >
	
							<TD><%=vdata.get(0)%></TD>
	
	
							<TD><%=vdata.get(2)%></TD>
							<TD><%=vdata.get(3)%></TD>
	
	
							<%
							
							// System.out.println("vdata.get(10).toString()"+vdata.get(10).toString());
										if (!vdata.get(11).toString().equals("NIL")) {
										if (!RemarksWEC.equals(".")) {
											RemarksWEC = RemarksWEC+ (String) vdata.get(11);
	
										}
									}
	
									if (!vdata.get(10).toString().equals("1")) {
							%>
	
	
	
									<TD><%=vdata.get(4)%></TD>
									<TD><%=vdata.get(7)%></TD>
						
									<TD><%=vdata.get(5)%></TD>
									<TD colspan="2"><%=vdata.get(6)%></TD>
							<%
							} else {
							%>
	
									
									<TD colspan="4">WEC Is In Stabilization Phase</TD>
									
	
							<%
							}
							%>
	
						</TR>
					<%
							pdate = ndate;
							nedate = ad.convertDateFormat(pdate, "dd-MM-yyyy","dd/MM/yyyy");
								}
					%>
					<%
					tranListData.clear();
					%>
					<%
								wecListData.clear();

								if (tsize > 0) {
							wecListData = (List) secutils.getEBWiseTotal(ebid,
									pdate1, "D");
							for (int k = 0; k < wecListData.size(); k++) {
								Vector vdt = new Vector();
								vdt = (Vector) wecListData.get(k);
								//String name = (String)vdata.get(0);
					%>
					<TR >
						<TD>Total:<%=vdt.get(0)%></TD>
						
						<TD><%=vdt.get(1)%></TD>
						<TD><%=vdt.get(2)%></TD>
						<TD><%=vdt.get(3)%></TD>
						<TD><%=vdt.get(6)%></TD>
						
						<TD><%=vdt.get(4)%></TD>
						<TD><%=vdt.get(5)%></TD>
						
						
					</TR>

						<%
									}
									}
						%>
					
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>

<%
			ebListData.clear();
			ebListData = (List) secutils.getEBData(ebid, nedate, "D");
			cls = "TableRow1";
			//System.out.println(ebListData.size());
			if (ebListData.size() > 0) {
%>


<SPAN><center><B>EB Data</B></center></SPAN><BR>

<TABLE  width="90%"  border="1">
	<TBODY>
		<TR>
			<TD>
			<TABLE  width="100%" border="1">
				<TBODY>
					<TR>
						<TD width="16%" colspan="2">Description</TD>
						<TD width="27%" colspan="2">KWH Export</TD>
						<TD width="27%" colspan="2">KWH Import</TD>
						<TD width="27%" colspan="2">Net KWH</TD>

					</TR>
					<%
					}
					%>
					<%
							for (int c = 0; c < ebListData.size(); c++) {
							Vector vedata = new Vector();
							vedata = (Vector) ebListData.get(c);
							String name = (String) vedata.get(0);

							int rem1 = 1;
							rem1 = c % 2;

							if (!vedata.get(4).toString().equals("NIL"))
								if (!Remarks.equals(".")) {
									{
								Remarks = (String) vedata.get(4);

									}
								}
							if (rem1 == 0)
								cls = "TableRow2";
							else
								cls = "TableRow1";
					%>

					<TR >
						<TD colspan="2"><%=vedata.get(0)%></TD>
						<TD colspan="2"><%=vedata.get(1)%></TD>
						<TD colspan="2"><%=vedata.get(2)%></TD>
						<TD colspan="2"><%=vedata.get(3)%></TD>
					</TR>

					<%
					}
					%>

				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>

<SPAN><center>Remarks </center></SPAN><BR>
<TABLE  width="90%"  border="1">
	<TBODY>
		<%
				if (Remarks.equals("")) {
				Remarks = "";
					}
					if (Remarks.equals(".")) {
				Remarks = "";
					}
		%>
		<%
				if (RemarksWEC.equals("")) {
				RemarksWEC = "";
					}
		if (RemarksWEC.equals(".")) {
			RemarksWEC = "";
				}
		%>
		<tr>
			<td vAlign="middle" class='TableCell11' align="center" width="100%" colspan="8"><%=Remarks%></td>

		</tr>
		<tr>

			<td vAlign="middle" class='TableCell11' align="center" width="100%" colspan="8"><%=RemarksWEC%></td>
		</tr>
		<%
		Remarks = "";
		RemarksWEC = "";
		%>
	
	</TBODY>
</TABLE>


<%
	}
	}
	tranList.clear();
%>



</BODY>
</HTML>

<%-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "Export.xls";
	response.addHeader("Content-Disposition", "attachment; filename=\""+ file + "\"");
%>


<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.enercon.admin.dao.*"%>


<HTML>
<HEAD>
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>

<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	// String userid = session.getAttribute("loginID").toString();
	// String Customeridtxt = "";
%>
</HEAD>
<BODY>

<%
	String custid = request.getParameter("id");
	String stateid = request.getParameter("stateid");
	String siteid = request.getParameter("siteid");
	String rdate = request.getParameter("rd");
	int monthR = Integer.parseInt(request.getParameter("month"));
	int yearR = Integer.parseInt(request.getParameter("year"));
	// String type = request.getParameter("type");
%> <%
 	List tranList = new ArrayList();
 	// List sitetranList = new ArrayList();
 	CustomerUtil secutils = new CustomerUtil();
 	tranList = (List) secutils.getEBHeading(custid, rdate, stateid, siteid);
 	String cls = "TableRow1";
 	String ebid = "";
 	String cname = "";
 	String state = "";
 	String Remarks = "NA";
 	String nedate = "";
 	String pdate1 = "";
 	List wecListData = new ArrayList();
 	List ebListData = new ArrayList();
 	for (int i = 0; i < tranList.size(); i++) {
 		String RemarksWEC = "";
 		Vector v = new Vector();
 		v = (Vector) tranList.get(i);
 		ebid = (String) v.get(5);

 		cname = (String) v.get(0).toString().replaceAll("&", "and");
 		state = (String) v.get(3) + "-" + (String) v.get(4);
 %> <%
 		String adate="01/04/3009";
 		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
 		java.util.Date ffd = format.parse(rdate);
 		java.util.Date afd = format.parse(adate);
 		long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
 		int month = ffd.getMonth() + 1;
 		int day = ffd.getDay();

 		String year = rdate.substring(6);
 		//String syear="";

 		int cyear = ffd.getYear() - 100;
 		int nyear = cyear;

 		String monthname = "";
 		if (month == 4) {
 			monthname = "APRIL";
 		}
 		if (month == 3) {
 			monthname = "MARCH";
 		}
 		if (month == 1) {
 			monthname = "JANUARY";
 		}
 		if (month == 2) {
 			monthname = "FEBRUARY";
 		}

 		if (month == 5) {
 			monthname = "MAY";
 		}

 		if (month == 6) {
 			monthname = "JUNE";
 		}

 		if (month == 7) {
 			monthname = "JULY";
 		}

 		if (month == 8) {
 			monthname = "AUGUST";
 		}

 		if (month == 9) {
 			monthname = "SEPTEMBER";
 		}

 		if (month == 10) {
 			monthname = "OCTOBER";
 		}

 		if (month == 11) {
 			monthname = "NOVEMBER";
 		}

 		if (month == 12) {
 			monthname = "DECEMBER";
 		}
 %> <BR>
<TABLE cellSpacing="1" cellPadding="1" width="90%" border="1">
	<TBODY>

		<tr>
			<td vAlign="middle" align="center" width="100%" colspan="8"><b><%=v.get(0)%></b></td>
		</tr>
		<tr>
			<td vAlign="middle" width="100%" align="center" colspan="8"></td>
		</tr>

		<tr>
			<td vAlign="middle" align="center" width="100%" colspan="8"></td>
		</tr>

		<tr>
			<td vAlign="middle" align="left" width="20%">Location:</td>
			<td vAlign="middle" align="left" width="38%" colspan="3"><%=v.get(3)%> - <%=v.get(4)%></td>

			<td vAlign="middle" align="left" width="18%" colspan="2">Machines:</td>
			<td vAlign="middle" align="left" width="24%" colspan="2"><%=v.get(8)%></td>
		</tr>
		
		<tr>
			<td vAlign="middle" width="100%" align="center" colspan="8"></td>
		</tr>
		<tr>
			<td vAlign="middle" align="left" width="20%">Location Capacity:</td>
			<td vAlign="middle" align="left" width="38%" colspan="3"><%=v.get(7)%> MW</td>

			<td vAlign="middle" align="left" width="18%" colspan="2">Month:</td>
			<td vAlign="middle" align="left" width="24%" colspan="2"><%=monthname%>-<%=year%></td>
		</tr>

	</TBODY>
</TABLE>

<%
		List tranListData = new ArrayList();
		tranListData = CustomerUtility.getOneEBWECWiseInfoForOneMonth(ebid, monthR, yearR);
		int tsize = 0;
		cls = "TableRow1";
		//System.out.println("tranListData" + rdate);
		//System.out.println("tranListData" + tranListData.size());
		int wecsize = tranListData.size();
		if (wecsize > 0) {
 			RemarksWEC = "";
 			String pdate = "";
 			String ndate = "";
            String nousevar="0";
 			AdminDao ad = new AdminDao();

 			for (int j = 0; j < tranListData.size(); j++) {

	 		int rem = 1;
	 		rem = j % 2;
	
	 		if (rem == 0)
	 			cls = "TableRow2";
	 		else
	 			cls = "TableRow1";
	
	 		Vector vdata = new Vector();
	 		vdata = (Vector) tranListData.get(j);
	 		ndate = (String) vdata.get(9);
	 		pdate1 = ad.convertDateFormat(ndate, "dd-MM-yyyy","dd/MM/yyyy");
	
	 		//System.out.println("pdate"+pdate);
	 		//System.out.println("ndate"+ndate);
	
	 		if (ndate.equals(pdate))
	
	 		{
	 %> <%
	 		tsize = tsize + 1;
	 		} else {

 			if (j != 0) {
	 				nedate = ad.convertDateFormat(pdate,
	 				"dd-MM-yyyy", "dd/MM/yyyy");
	 				wecListData.clear();
	 				if (tsize > 0) {
			 			wecListData = (List) secutils.getEBWiseTotal(ebid, nedate, "D");
			 			for (int k = 0; k < wecListData.size(); k++) {
			 				Vector vdt = new Vector();
			 				vdt = (Vector) wecListData.get(k);
			 				//String name = (String)vdata.get(0);
							 %>
							<TR>
								<TD>Total:<%=vdt.get(0)%></TD>
								<TD><%=vdt.get(1)%></TD>
								<TD><%=vdt.get(2)%></TD>
								<TD><%=vdt.get(3)%></TD>
								<TD><%=vdt.get(6)%></TD>
								
								<TD><%=vdt.get(4)%></TD>
						        <TD><%=vdt.get(5)%></TD>								
							</TR>
							
							<%
						}
					}
					tsize = 0;
					
					if(nousevar.equals("Test")){
							%>
							<TABLE>
							<TBODY>
								<TR>
									<TD>
										<TABLE>
										<TBODY>
							<%} %>
					                    </TBODY>
					                    </TABLE>
					                </TD>
					            </TR>
					        </TBODY>
					       </TABLE>
					
					<%
									ebListData.clear();
									ebListData = (List) secutils.getEBData(ebid,
									nedate, "D");
									cls = "TableRow1";
									//System.out.println(ebListData.size());
									if (ebListData.size() > 0) {
					%>
									<SPAN><center><B>EB Data</B></center></SPAN><BR>
										<TABLE height="6"  border="1">
											<TBODY>
												<TR>
													<TD></TD>
												</TR>
											</TBODY>
										</TABLE>
										<TABLE  width="90%" >
											<TBODY>
												<TR>
													<TD>
													<TABLE  width="100%" border="1">
														<TBODY>
															<TR>
																<TD width="16%" colspan="2">Description</TD>
																<TD width="27%" colspan="2">KWH Export</TD>
																<TD width="27%" colspan="2">KWH Import</TD>
																<TD width="27%" colspan="2">Net KWH</TD>									
															</TR>
													<%
									}
									%>
									<%
									for (int c = 0; c < ebListData.size(); c++) {
										Vector vedata = new Vector();
										vedata = (Vector) ebListData.get(c);
										String name = (String) vedata.get(0);
		
										int rem1 = 1;
										rem1 = c % 2;
										//Remarks = (String) vedata.get(4);
										if (!vedata.get(4).toString().equals("NIL"))
											if (!Remarks.equals(".")) {
												{
											Remarks = (String) vedata
													.get(4);
		
												}
											}
										if (rem1 == 0)
											cls = "TableRow2";
										else
											cls = "TableRow1";
										%>
					
										<TR >
											<TD colspan="2"><%=vedata.get(0)%></TD>
											<TD colspan="2"><%=vedata.get(1)%></TD>
											<TD colspan="2"><%=vedata.get(2)%></TD>
											<TD colspan="2"><%=vedata.get(3)%></TD>
										</TR>
					
										<%
									}
									%>
				
								</TBODY>
							</TABLE>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
		
					<SPAN ><center>Remarks </center></SPAN><BR>
					<TABLE width="90%"  border="1">
					<TBODY>
			
					<%
								if (Remarks.equals("")) {
								Remarks = "";
								}
							if (Remarks.equals(".")) {
						Remarks = "";
							}
			%>
			<%
							if (RemarksWEC.equals("")) {
							RemarksWEC = "";
								}
					if (RemarksWEC.equals(".")) {
							 RemarksWEC = "";
					}
				%>
				<tr>
					<td vAlign="middle" class="TableCell11" align="center" width="100%" colspan="8"><%=Remarks%></td>
				</tr>
				<tr>
					<td vAlign="middle" class="TableCell11" align="center" width="100%" colspan="8"><%=RemarksWEC%></td>
				</tr>
				<%
				Remarks = "";
				RemarksWEC = "";
				%>
			
			</TBODY>
		</TABLE>
		<%
		}
	%>
	
<SPAN ><B>Generation Date: <%=ndate%> </B></SPAN><BR>

<TABLE width="90%"  border="1">
	<TBODY>
		<TR>
			<TD>
				<TABLE  width=""100%"" border="1">
					<TBODY>

						<TR>
							<TD width="14.28%">WEC Name</TD>
	
							<TD width="14.28%">Generation</TD>
							<TD width="14.28%">Operating Hours</TD>
							<TD width="14.28%">Lull Hours</TD>
							<TD width="14.28%">Capacity Factor(%)</TD>
							<TD width="14.28%">Machine Availability(%)</TD>
							<% if(diff<0){ %>
							<TD width="14.28%" colspan="2">Grid Availability(%)</TD>
							<%} else{ %>
							<TD width="14.28%">Grid Internal Availability(%)</TD>
							<TD width="14.28%">Grid External Availability(%)</TD>
							<%} %>
	
						</TR>
						<%
								}
								
								String name = (String) vdata.get(0);
								//String gen =String.parse(vdata.get(1));
								//String gen ="23.00";
								//String gen2 =gen.toString();
	
								//String ghrs = (String)vdata.get(2);
								//String lhrs = (String)vdata.get(3);
								//String mavdataail = (String)vdata.get(4);
								//String gavdataail = (String)vdata.get(5);
								//String cfactor = (String)vdata.get(6);
								//String stateid = (String)vdata.get(7);
						%>

						<TR >
	
							<TD><%=vdata.get(0)%></TD>
	
	
							<TD><%=vdata.get(2)%></TD>
							<TD><%=vdata.get(3)%></TD>
	
	
							<%
							
							// System.out.println("vdata.get(10).toString()"+vdata.get(10).toString());
										if (!vdata.get(11).toString().equals("NIL")) {
										if (!RemarksWEC.equals(".")) {
											RemarksWEC = RemarksWEC+ (String) vdata.get(11);
	
										}
									}
	
									if (!vdata.get(10).toString().equals("1")) {
							%>
	
	
	
									<TD><%=vdata.get(4)%></TD>
									<TD><%=vdata.get(7)%></TD>
						
									<TD><%=vdata.get(5)%></TD>
									<TD colspan="2"><%=vdata.get(6)%></TD>
							<%
							} else {
							%>
	
									
									<TD colspan="4">WEC Is In Stabilization Phase</TD>
									
	
							<%
							}
							%>
	
						</TR>
					<%
							pdate = ndate;
							nedate = ad.convertDateFormat(pdate, "dd-MM-yyyy","dd/MM/yyyy");
								}
					%>
					<%
					tranListData.clear();
					%>
					<%
								wecListData.clear();

								if (tsize > 0) {
							wecListData = (List) secutils.getEBWiseTotal(ebid,
									pdate1, "D");
							for (int k = 0; k < wecListData.size(); k++) {
								Vector vdt = new Vector();
								vdt = (Vector) wecListData.get(k);
								//String name = (String)vdata.get(0);
					%>
					<TR >
						<TD>Total:<%=vdt.get(0)%></TD>
						
						<TD><%=vdt.get(1)%></TD>
						<TD><%=vdt.get(2)%></TD>
						<TD><%=vdt.get(3)%></TD>
						<TD><%=vdt.get(6)%></TD>
						
						<TD><%=vdt.get(4)%></TD>
						<TD><%=vdt.get(5)%></TD>
						
						
					</TR>

						<%
									}
									}
						%>
					
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>

<%
			ebListData.clear();
			ebListData = (List) secutils.getEBData(ebid, nedate, "D");
			cls = "TableRow1";
			//System.out.println(ebListData.size());
			if (ebListData.size() > 0) {
%>


<SPAN><center><B>EB Data</B></center></SPAN><BR>

<TABLE  width="90%"  border="1">
	<TBODY>
		<TR>
			<TD>
			<TABLE  width="100%" border="1">
				<TBODY>
					<TR>
						<TD width="16%" colspan="2">Description</TD>
						<TD width="27%" colspan="2">KWH Export</TD>
						<TD width="27%" colspan="2">KWH Import</TD>
						<TD width="27%" colspan="2">Net KWH</TD>

					</TR>
					<%
					}
					%>
					<%
							for (int c = 0; c < ebListData.size(); c++) {
							Vector vedata = new Vector();
							vedata = (Vector) ebListData.get(c);
							String name = (String) vedata.get(0);

							int rem1 = 1;
							rem1 = c % 2;

							if (!vedata.get(4).toString().equals("NIL"))
								if (!Remarks.equals(".")) {
									{
								Remarks = (String) vedata.get(4);

									}
								}
							if (rem1 == 0)
								cls = "TableRow2";
							else
								cls = "TableRow1";
					%>

					<TR >
						<TD colspan="2"><%=vedata.get(0)%></TD>
						<TD colspan="2"><%=vedata.get(1)%></TD>
						<TD colspan="2"><%=vedata.get(2)%></TD>
						<TD colspan="2"><%=vedata.get(3)%></TD>
					</TR>

					<%
					}
					%>

				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>

<SPAN><center>Remarks </center></SPAN><BR>
<TABLE  width="90%"  border="1">
	<TBODY>
		<%
				if (Remarks.equals("")) {
				Remarks = "";
					}
					if (Remarks.equals(".")) {
				Remarks = "";
					}
		%>
		<%
				if (RemarksWEC.equals("")) {
				RemarksWEC = "";
					}
		if (RemarksWEC.equals(".")) {
			RemarksWEC = "";
				}
		%>
		<tr>
			<td vAlign="middle" class='TableCell11' align="center" width="100%" colspan="8"><%=Remarks%></td>

		</tr>
		<tr>

			<td vAlign="middle" class='TableCell11' align="center" width="100%" colspan="8"><%=RemarksWEC%></td>
		</tr>
		<%
		Remarks = "";
		RemarksWEC = "";
		%>
	
	</TBODY>
</TABLE>


<%
	}
	}
	tranList.clear();
%>



</BODY>
</HTML>
 --%>