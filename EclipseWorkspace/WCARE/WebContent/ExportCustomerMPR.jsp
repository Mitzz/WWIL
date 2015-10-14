<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@page import="com.enercon.global.utility.TimeUtility"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>

<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%! boolean debug = false;%>
<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String file = request.getParameter("File"); 
if(file==null || file.equals("")){ 
	file="CustomerMPRExcel.xls";
}
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");
%>



<html>
	<HEAD>
	<%
		if (session.getAttribute("loginID") == null) {
			response.sendRedirect(request.getContextPath());
		}
	%>
	
	<title>DGR DashBoard Report</TITLE>
	<%
		response.setHeader("Pragma", "no-cache");
		response.getOutputStream().flush();
		response.getOutputStream().close();
	//	String userid = session.getAttribute("loginID").toString();
	//	String Customeridtxt = "";
		int totalLackOfWind = 0;
	%>
	</HEAD>
	<BODY>
		<CENTER>
		<%

	String cid = request.getParameter("id");
	String stateid = request.getParameter("state");
	String wectype = request.getParameter("wectype");
	String fdate = request.getParameter("fd");
	String tdate = request.getParameter("td");
	String site = request.getParameter("site");
	String sname = request.getParameter("sname");
	sname=sname.replaceFirst(",","");
	
	if(debug){
		System.out.println("cid" + cid);
		System.out.println("stateid" + stateid);
		System.out.println("fdate" + fdate);
		System.out.println("tdate" + tdate);
		System.out.println("site" + site);
		System.out.println("sname" + sname);
	}
	
//	List wectypeList = new ArrayList();
	CustomerUtil custUtil = new CustomerUtil();
		
		%>

			<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1" >
				<TBODY>
		        	<TR >
						<td vAlign = "middle" align = "center"><b>State</b></td>
                      	<td vAlign = "middle"  align = "center" colspan="3"><b><%=sname%></b></td>
						<td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="5"><b> Monthly Performance Report</b></td>
						<td vAlign = "middle" align = "center" colspan="3"><b>Date:<%=fdate%>-<%=tdate%> </b></td>		              	            
		            </tr>
		            <tr>
		              	<td vAlign = "middle" align = "center" ROWSPAN="3"><b>Wec No.</b></td>
                      	<td vAlign = "middle" align = "center" colspan="2"><b>Generation</b></td>
                      	<td vAlign = "middle" align = "center" ROWSPAN="3"><b>Lack Of Wind</b></td>
		              	<td vAlign = "middle" align = "center" colspan="5"><b>Down Time</b></td>
		              	<td vAlign = "middle" align = "center" ROWSPAN="3"><b>Machine Availabilty(%)</b></td>
		              	<td vAlign = "middle" align = "center" ROWSPAN="3"><b>Capacity Factor(%)</b></td>
		              	<td vAlign = "middle" align = "center" ROWSPAN="3"><b>Grid Availabilty(%)</b></td>		             
		            </tr>
		            <tr>
		              	<td vAlign = "middle" align = "center" ROWSPAN="2"><b>KWh</b></td>
		              	<td vAlign = "middle" align = "center" ROWSPAN="2"><b>Hrs</b></td>
		              	<td vAlign = "middle" align = "center" colspan="2"><b>Machine</b></td>
		              	<td vAlign = "middle" align = "center" colspan="2"><b>Grid</b></td>
		              	<td vAlign = "middle" align = "center" rowspan="2"><b>Total</b></td>
		            </tr>
		            <tr>
		              	<td vAlign = "middle" align = "center"><b>Fault</b></td>
		             	<td vAlign = "middle" align = "center"><b>Shutdown</b></td>
		              	<td vAlign = "middle" align = "center"><b>Fault</b></td>
		              	<td vAlign = "middle" align = "center"><b>Shutdown</b></td>
		            </tr>
					<%

List tranList_1 = new ArrayList();
List sitetranList_3 = new ArrayList();
List sitetranList_5 = new ArrayList();
/* cid'0905000002'
stateid'1000000038'
fdate08/05/2013
tdate18/06/2013
site'1108000001'
snameMAHARASHTRA */
tranList_1 = (List)custUtil.getCustMPRHeading(cid,stateid,site);   // 1
if(debug){
	System.out.println("---------------1--------------");
	GlobalUtils.displayVectorMember(tranList_1);
}

for (int j=0; j <tranList_1.size(); j++){ 
	Vector vector_12 = new Vector();
	vector_12 = (Vector)tranList_1.get(j);
	if(debug){
		System.out.println("-------1--------2--------------");
		GlobalUtils.displayVectorMember(vector_12);
	}
	
	String cnt = vector_12.get(1).toString();
	// String cid = vector_12.get(4).toString();
	String stid = vector_12.get(4).toString();
	//System.out.println("cnt"+cnt);
	//System.out.println("cid"+cid);
	//System.out.println("stid"+stid);
    if(cnt.equals("0")){
 	   
    }
	else{ 
					%>
					<TR bgcolor="#CCCCCC">
						<td vAlign = "middle" align = "center" colspan="3"><b>Site: <%=vector_12.get(1)%>,<%=vector_12.get(6)%></b></td>
						<td vAlign = "middle"  align = "center" colspan="7"><b><%=vector_12.get(0)%></b></td>
						<td vAlign = "middle" align = "center" colspan="2"><b>Total WEC : <%=vector_12.get(2)%></b></td>
		          	</TR>
		        	<% 
		int tra=0; 
		sitetranList_3 = (List)custUtil.getMPRDetailAdmin(stid,vector_12.get(5).toString(),fdate,tdate,"ALL"); // 2
		
		if(debug){
			System.out.println("---------------3--------------");
			GlobalUtils.displayVectorMember(sitetranList_3);
			System.out.println("sitetranList_3.size()"+sitetranList_3.size());
		}
		
		if(sitetranList_3.size()==0){ 
					%>	
					<tr>
			        	<td align="center" colspan="12">No Data</td>
					</tr>
					<% 
		}
					%>
					<%
		for (int i = 0; i < sitetranList_3.size(); i++){
			Vector vector_3_4 = new Vector();
			vector_3_4 = (Vector)sitetranList_3.get(i);
			if(debug){
				System.out.println("-------3--------4--------------");
				GlobalUtils.displayVectorMember(vector_3_4);
			}
					%>
					<tr>
						<td vAlign = "middle" align = "center" ><%=vector_3_4.get(0)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(1)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(2)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(3).toString()%></td>
				        	        <%
			//System.out.println("---------------ExportCoPage:" + (String)vector_3_4.get(3));
			//System.out.println("---------------ExportCoPageEc:" + GlobalUtils.getMinutes((String)vector_3_4.get(3)));	        
			//totalLackOfWind += GlobalUtils.getMinutes((String)vector_3_4.get(3));
			totalLackOfWind += TimeUtility.convertTimeStringToMinutes((String)vector_3_4.get(3), ":");
				        %>
				        <td valign = "middle" align = "center" ><%=vector_3_4.get(4)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(5)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(6)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(7)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(8)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(9)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(10)%></td>
				        <td vAlign = "middle" align = "center" ><%=vector_3_4.get(12)%></td>
					</tr>
					<%
			             
			tra=tra+1;
		}
		sitetranList_3.clear(); 
					%>
			        <%  
		if(tra>1){
			sitetranList_5 = (List)custUtil.getMPRDetailAdminTotal(stid,vector_12.get(5).toString(),fdate,tdate,"ALL"); // 3
			if(debug){
				System.out.println("sitetranList_5.size():"+sitetranList_5.size());
				System.out.println("---------------5--------------");
				GlobalUtils.displayVectorMember(sitetranList_5);
			}
			
			for (int i=0; i <sitetranList_5.size(); i++){
				Vector vector_5_6 = new Vector();
			    vector_5_6 = (Vector)sitetranList_5.get(i);
			    if(debug){
			    	System.out.println("-------5--------6--------------");
				    GlobalUtils.displayVectorMember(vector_5_6);
			    }
			    
			    int[] hourMinute = GlobalUtils.minuteToHour(totalLackOfWind);
			    String  kls = "";
			    if(hourMinute[1] < 10){
			    	kls = hourMinute[0] + ":0" + hourMinute[1];
			    }
			    else{
			    	kls = hourMinute[0] + ":" + hourMinute[1];
			    }
			    	%>
					<TR >
			        	<td vAlign = "middle" align = "center" ><b>Total</b></td>
			        	<td vAlign ="middle"  align = "center" ><b><%=vector_5_6.get(0)%></b></td>
	                    <td vAlign ="middle"  align = "center" ><b><%=vector_5_6.get(1)%></b></td>
	                    <td vAlign = "middle" align = "center" ><b><%=kls%></b></td>
	                    <td vAlign = "middle" align = "center" ><b><%=vector_5_6.get(3)%></b></td>
	                    <td vAlign ="middle"  align = "center" ><b><%=vector_5_6.get(4)%></b></td>
	                    <td vAlign = "middle" align = "center" ><b><%=vector_5_6.get(5)%></b></td>
	                    <td vAlign = "middle" align = "center" ><b><%=vector_5_6.get(6)%></b></td>
	                    <td vAlign ="middle"  align = "center" ><b><%=vector_5_6.get(7)%></b></td>
	                    <td vAlign = "middle" align = "center" ><b><%=vector_5_6.get(8)%></b></td>
	                    <td vAlign = "middle" align = "center" ><b><%=vector_5_6.get(9)%></b></td>
	                    <td vAlign ="middle"  align = "center" ><b><%=vector_5_6.get(11)%></b></td>
					</TR>
			        <%
			}
			sitetranList_5.clear();
		} 
						        
	}
	totalLackOfWind = 0;
} 
 					%>
				</tbody>
			</table>
		</CENTER>
	</BODY>
</HTML>