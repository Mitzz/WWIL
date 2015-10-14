<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.global.utility.MethodClass"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%!
boolean debug = true;

%>
<%@page contentType="application/vnd.ms-excel"%><%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File"); 
	if(file==null || file.equals("")) file="WecTypeExcel.xls";
	response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");
%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>

<%@ page import="java.util.*"%>


<HTML>
<HEAD>
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>

<TITLE>DGR DashBoard Report</TITLE>

<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	String Customeridtxt = "";
%>
</HEAD>
<BODY>
<CENTER>
<%
	String wectype = request.getParameter("wectype");
	String fdate = request.getParameter("fdate");
	String tdate = request.getParameter("tdate");
	
	if(debug){
		MethodClass.displayMethodClassName();
		System.out.println("wectype:" + wectype);
		System.out.println("fdate:" + fdate);
		System.out.println("tdate:" + tdate);
	}
	String[]  wec=wectype.split("/");
	List statetranList = new ArrayList();
	
	CustomerUtil secutils = new CustomerUtil();
	statetranList = (List)secutils.getState();  
	String stateid ,sname;
	for (int z=0; z <statetranList.size(); z++)
	{   
		Vector vstate = new Vector();
		vstate = (Vector)statetranList.get(z);
		stateid = vstate.get(0).toString();
		sname = vstate.get(1).toString();
		//System.out.println(stateid);
		//System.out.println(sname);
%>


	          

<%
List tranList = new ArrayList();
List sitetranList = new ArrayList();

tranList = (List)secutils.getMPRHeading(stateid,wec[0]);  
int  wecsize=tranList.size();
if(wecsize>0){%>
<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1" >
	<TBODY>
		<TR >
		              <td vAlign = "middle" align = "center" colspan="3"><b>State:<%=sname%></b></td>
                      
                      <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="4"><b><%=wec[0]%> Monthly Performance Report</b></td>
		               <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="2"><b>Date:<%=fdate%>-<%=tdate%> </b></td>
		               <%if(!wectype.equals("ALL")){%>
		              <td vAlign = "middle"  align = "center" colspan="3"><b><%=wec[0]%>,<%=wec[1]%> KWh</b></td>
		              <%}%>
		            </tr>
		           
		            
		            <TR >
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
		              <td vAlign = "middle" align = "center" colspan="2"><b> Grid</b></td>
		              <td vAlign = "middle" align = "center" rowspan="2"><b> Total</b></td>
		            </tr>
		            <tr>
		            <td vAlign = "middle" align = "center"><b>Fault</b></td>
		              <td vAlign = "middle" align = "center"><b>Shutdown</b></td>
		             <td vAlign = "middle" align = "center"><b>Fault</b></td>
		              <td vAlign = "middle" align = "center"><b>Shutdown</b></td>
		            </tr>
<%for (int j=0; j <tranList.size(); j++){ 

                 Vector vdata = new Vector();
				 vdata = (Vector)tranList.get(j);
				 String cnt = vdata.get(5).toString();
				 String cid = vdata.get(4).toString();
				 String stid = vdata.get(3).toString();
				 //System.out.println("cnt"+cnt);
				 //System.out.println("cid"+cid);
				 //System.out.println("stid"+stid);
               if(cnt.equals("0")){}else{ %>
               
                  <TR bgcolor="#CCCCCC">                  	  
		              <td vAlign = "middle" align = "center" colspan="3"><b>Site: <%=vdata.get(1)%></b></td>
                      <td vAlign ="middle"  align = "center" colspan="7"><b><%=vdata.get(0)%></b></td>
                      <td vAlign = "middle" align = "center" colspan="2"><b>Capacity : <%=vdata.get(6)%></b></td>
                      
		          </TR>
		       
		        <%   int tra=0; 
		        sitetranList = (List)secutils.getMPRDetailAdmin(stid,cid,fdate,tdate,wectype); 
		          //System.out.println("sitetranList.size()"+sitetranList.size());
		             if(sitetranList.size()==0)
		             { %>
		             <tr ><td align="center" colspan="12">No Data</td></tr>
		            	 
		            <% }%>
		               <%   for (int i=0; i <sitetranList.size(); i++)
		                  {
		                	  
		       
		                	  Vector vwec = new Vector();
		     				   vwec = (Vector)sitetranList.get(i); %>
		             
		             <TR >
		             
		              <td vAlign = "middle" align = "center" ><%=vwec.get(0)%></td>
                      <td vAlign ="middle"  align = "center" ><%=vwec.get(1)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(2)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(3)%></td>
                      <td vAlign ="middle"  align = "center" ><%=vwec.get(4)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(5)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(6)%></td>
                      <td vAlign ="middle"  align = "center" ><%=vwec.get(7)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(8)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(9)%></td>
                      <td vAlign ="middle"  align = "center" ><%=vwec.get(10)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(12)%></td>
                      
		             </TR>
		             <%
		             
		             tra=tra+1;}sitetranList.clear(); %>
		        
		            <%  
		            if(tra>1){
		            sitetranList = (List)secutils.getMPRDetailAdminTotal(stid,cid,fdate,tdate,wectype); 
		          //System.out.println("sitetranList.size()"+sitetranList.size());
		                  for (int i=0; i <sitetranList.size(); i++)
		                  {
		                	  
		       
		                	  Vector vwec = new Vector();
		     				   vwec = (Vector)sitetranList.get(i); %>
		             
		             <TR >
		              <td vAlign = "middle" align = "center" ><b>Total</b></td>
		              <td vAlign ="middle"  align = "center" ><b><%=vwec.get(0)%></b></td>
                      <td vAlign ="middle"  align = "center" ><b><%=vwec.get(1)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(2)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(3)%></b></td>
                      <td vAlign ="middle"  align = "center" ><b><%=vwec.get(4)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(5)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(6)%></b></td>
                      <td vAlign ="middle"  align = "center" ><b><%=vwec.get(7)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(8)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(9)%></b></td>
                      <td vAlign ="middle"  align = "center" ><b><%=vwec.get(11)%></b></td>
                     
                      
		             </TR>
		             <%}sitetranList.clear();} %>
		       
		       
		       
		       
		       
		       
                

         <%} }} %>
		
		
		
				
</TBODY>
</TABLE>
  <%}statetranList.clear(); %>
</CENTER>
</BODY>
</HTML>