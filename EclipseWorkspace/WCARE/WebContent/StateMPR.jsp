<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.global.utility.MethodClass"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
    String file = request.getParameter("File");
    if (file == null || file.equals("")) {
        file = "AnalysisReport_MPR";
    }
    response.addHeader("Content-Disposition", "attachment; filename=\"" + file + "\"");%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>

<%@ page import="java.util.*"%>


<HTML>
    <HEAD>
        <%
response.setHeader("Pragma", "no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
// String userid = session.getAttribute("loginID").toString();
// String Customeridtxt = "";
        %>
    </HEAD>
    <BODY>
        <CENTER>
            <TABLE cellSpacing="1" cellPadding="1" width="100%" border="1">
                <TBODY>
					<%
					//MethodClass.displayMethodClassName();
String roleid = session.getAttribute("RoleID").toString();

String stateid = request.getParameter("stateid");
String wectype = request.getParameter("wectype");
String fdate = request.getParameter("fdate");
String tdate = request.getParameter("tdate");
String sname = request.getParameter("sname");
sname = sname.replace(",", " ");
String[] wec = wectype.split("/");;
List wectypeList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
wectypeList = (List) secutils.getSWecType(wec[0]);
//GlobalUtils.displayVectorMember(wectypeList);
if (roleid.equals("0000000001") && session.getAttribute("loginID").toString().equals("MANOJ")) {
    for (int h = 0; h < wectypeList.size(); h++) {
        Vector wecty = new Vector();
        wecty = (Vector) wectypeList.get(h);
        wectype = wecty.get(0).toString();
        wec = wectype.split("/");
                    %>

                    <%
		List tranList = new ArrayList();
		List sitetranList = new ArrayList();


		tranList = (List) secutils.getSubMPRHeading(stateid, wec[0]);
        // int  wecsize=tranList.size();
        for (int j = 0; j < tranList.size(); j++) {

	        Vector vdata = new Vector();
	        vdata = (Vector) tranList.get(j);
	        String cnt = vdata.get(5).toString();
	        String cid = vdata.get(4).toString();
	        String stid = vdata.get(3).toString();
	        String arname = vdata.get(7).toString();
	        String subname = vdata.get(8).toString();


			int tra = 0;
            sitetranList = (List) secutils.getSubMPRDetailAdmin(stid, cid, fdate, tdate, wectype);

            if (cnt.equals("0")) {
        	} 
            else {
            	if (sitetranList.size() > 0) {
            %>
                    <tr>
                        <td vAlign = "top" align = "center" bgcolor="#CCCCCE" rowspan="4">Substation</td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCE" colspan="2"><b>State:<%=sname%></b></td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCE" colspan="2"><b>Area:<%=arname%></b></td>                       
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="5"><b><%=wec[0]%> Monthly Performance Report</b></td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCE" colspan="1"><b>Date:<%=fdate%>-<%=tdate%> </b></td>
                        <%
					if (!wectype.equals("ALL")) {
                        %>
                        <td vAlign = "middle"  align = "center" bgcolor="#CCCCCE" colspan="2"><b><%=wec[0]%>,<%=wec[1]%> KWh</b></td>
                        <%
					}
                        %>
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
				}
                    %>
                    <tr bgcolor="#CCCCCC">
                        <td vAlign = "top" align = "center"><b><%=subname%></b></td>
                        <td vAlign = "middle" align = "center" colspan="2"><b>Site: <%=vdata.get(1)%></b></td>
                        <td vAlign = "middle" align = "center" colspan="8"><b><%=vdata.get(0)%></b></td>
                        <td vAlign = "middle" align = "center" colspan="2"><b>Capacity : <%=vdata.get(6)%></b></td>                      
                    </tr>

                    <%
                        //System.out.println("sitetranList.size()"+sitetranList.size());
				if (sitetranList.size() == 0) {
					%>
                    <tr >
                    	<td align="center" colspan="12">No Data</td></tr>

                    <% 
				}
                    %>
                    <%   
				for (int i = 0; i < sitetranList.size(); i++) {
					Vector vwec = new Vector();
                    vwec = (Vector) sitetranList.get(i);
                                       %>

                    <tr>
                        <td vAlign = "middle" align = "center" ></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(0)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(1)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(2)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(3)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(4)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(5)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(6)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(7)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(8)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(9)%></td>
                        <td vAlign = "middle" align = "center" ><%=vwec.get(10)%></td>

                        <td vAlign = "middle" align = "center" ><%=vwec.get(12)%></td>
                    </tr>
                    <%

					tra = tra + 1;
				}
                sitetranList.clear();
                %>
                    <%
				if (tra > 1) {
                	sitetranList = (List) secutils.getMPRDetailAdminTotal(stid, cid, fdate, tdate, wectype);
                    //System.out.println("sitetranList.size()"+sitetranList.size());
                    for (int i = 0; i < sitetranList.size(); i++) {
						Vector vwec = new Vector();
                        vwec = (Vector) sitetranList.get(i);
					%>

                    <tr>		             
                        <td vAlign = "middle" align = "center" colspan="2"><b>Total</b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(0)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(1)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(2)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(3)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(4)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(5)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(6)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(7)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(8)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(9)%></b></td>
                        <td vAlign = "middle" align = "center" ><b><%=vwec.get(11)%></b></td>                      
                    </tr>
                    <%
					}
                    sitetranList.clear();
				}
			}
		}
	}
} else {
	for (int h = 0; h < wectypeList.size(); h++) {
    	Vector wecty = new Vector();
        wecty = (Vector) wectypeList.get(h);
        wectype = wecty.get(0).toString();
        wec = wectype.split("/");
                    %>

                    <%
        List tranList = new ArrayList();
        List sitetranList = new ArrayList();

        tranList = (List) secutils.getMPRHeading(stateid, wec[0]);
        // int  wecsize=tranList.size();
        for (int j = 0; j < tranList.size(); j++) {
			Vector vdata = new Vector();
            vdata = (Vector) tranList.get(j);
            String cnt = vdata.get(5).toString();
            String cid = vdata.get(4).toString();
            String stid = vdata.get(3).toString();
            //System.out.println("cnt"+cnt);
            //System.out.println("cid"+cid);
            //System.out.println("stid"+stid);

            int tra = 0;
            	sitetranList = (List) secutils.getMPRDetailAdmin(stid, cid, fdate, tdate, wectype);

			if (cnt.equals("0")) {
        	} else {
            	if (sitetranList.size() > 0) {
            %>
                    <tr>
                        <td vAlign = "middle" align = "center" colspan="3"><b>State:<%=sname%></b></td>

                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="4"><b><%=wec[0]%> Monthly Performance Report</b></td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="2"><b>Date:<%=fdate%>-<%=tdate%> </b></td>
                        <%
					if (!wectype.equals("ALL")) {
                        %>

                        <td vAlign = "middle"  align = "center" colspan="3"><b><%=wec[0]%>,<%=wec[1]%> KWh</b></td>
                        <%
					}
                        %>
                    </tr>
                    <tr>
                        <td vAlign = "middle" align = "center" ROWSPAN="3"><b>Wec No.</b></td>
                        <td vAlign ="middle"  align = "center" colspan="2"><b>Generation</b></td>
                        <td vAlign = "middle" align = "center" ROWSPAN="3"><b>Lack Of Wind</b></td>
                        <td vAlign = "middle"  align = "center"colspan="5"><b>Down Time</b></td>
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
				}
                    %>
                    <tr bgcolor="#CCCCCC">
                        <td vAlign = "middle" align = "center" colspan="2"><b>Area: <%=vdata.get(7)%></b></td>
                        <td vAlign = "middle" align = "center" colspan="3"><b>Site: <%=vdata.get(1)%></b></td>
                        <td vAlign ="middle"  align = "center" colspan="5"><b><%=vdata.get(0)%></b></td>
                        <td vAlign = "middle" align = "center" colspan="2"><b>Capacity : <%=vdata.get(6)%></b></td>                      
                    </tr>

                    <%
                        //System.out.println("sitetranList.size()"+sitetranList.size());
				if (sitetranList.size() == 0) {
					%>
                    <tr >
                    	<td align="center" colspan="12">No Data</td>
					</tr>

                    <% 
				}
				%>
                    <%   
				for (int i = 0; i < sitetranList.size(); i++) {
					Vector vwec = new Vector();
                	vwec = (Vector) sitetranList.get(i);
				%>
                    <tr>		             
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
                    </tr>
                    <%

					tra = tra + 1;
              	}
                sitetranList.clear();
                %>

                    <%
				if (tra > 1) {
                	sitetranList = (List) secutils.getMPRDetailAdminTotal(stid, cid, fdate, tdate, wectype);
                    //System.out.println("sitetranList.size()"+sitetranList.size());
                    for (int i = 0; i < sitetranList.size(); i++) {
						Vector vwec = new Vector();
						vwec = (Vector) sitetranList.get(i);
						%>

                    <tr>
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
                    </tr>
                    <%
					}
                    sitetranList.clear();
				}
			}
		}
	}
}
                    %>
                </TBODY>
            </TABLE>
        </CENTER>
    </BODY>
</HTML>
<%-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="application/vnd.ms-excel"%><%@page pageEncoding="UTF-8"%><%
request.setCharacterEncoding("utf-8");
String file = request.getParameter("File"); 
if(file==null || file.equals("")) file="MPRExcel.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>

<%@ page import="java.util.*"%>


<HTML>
<HEAD>


<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	// String userid = session.getAttribute("loginID").toString();
	// String Customeridtxt = "";
%>
</HEAD>
<BODY>
<CENTER>
<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1">
<TBODY>
<%	
	String roleid = session.getAttribute("RoleID").toString();	
	
	String stateid = request.getParameter("stateid");
	String wectype = request.getParameter("wectype");
	String fdate = request.getParameter("fdate");
	String tdate = request.getParameter("tdate");
	String sname = request.getParameter("sname");
	sname=sname.replace(","," ");
	String[]  wec=wectype.split("/");;
	List wectypeList = new ArrayList();
	CustomerUtil secutils = new CustomerUtil();
	wectypeList = (List)secutils.getSWecType(wec[0]);  
	GlobalUtils.displayVectorMember(wectypeList);
if(roleid.equals("0000000001") && session.getAttribute("loginID").toString().equals("MANOJ")){
	for (int h=0; h <wectypeList.size(); h++){ 
		Vector wecty = new Vector();
		wecty = (Vector)wectypeList.get(h);		
		 wectype = wecty.get(0).toString();
		 wec=wectype.split("/");
%>

<%
List tranList = new ArrayList();
List sitetranList = new ArrayList();


tranList = (List)secutils.getSubMPRHeading(stateid,wec[0]);  
// int  wecsize=tranList.size();
for (int j=0; j <tranList.size(); j++){ 

                 Vector vdata = new Vector();
				 vdata = (Vector)tranList.get(j);
				 String cnt = vdata.get(5).toString();
				 String cid = vdata.get(4).toString();
				 String stid = vdata.get(3).toString();
				 String arname = vdata.get(7).toString();
				 String subname = vdata.get(8).toString();
				 
				 
				 int tra=0; 
		         sitetranList = (List)secutils.getSubMPRDetailAdmin(stid,cid,fdate,tdate,wectype); 
		        
               if(cnt.equals("0")){}else{ 
                if(sitetranList.size()>0){%>
				 <tr>
				 	   <td vAlign = "top" align = "center" bgcolor="#CCCCCE" rowspan="4">Substation</td>
		               <td vAlign = "middle" align = "center" bgcolor="#CCCCCE" colspan="2"><b>State:<%=sname%></b></td>
                       <td vAlign = "middle" align = "center" bgcolor="#CCCCCE" colspan="2"><b>Area:<%=arname%></b></td>                       
                       <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="5"><b><%=wec[0]%> Monthly Performance Report</b></td>
		               <td vAlign = "middle" align = "center" bgcolor="#CCCCCE" colspan="1"><b>Date:<%=fdate%>-<%=tdate%> </b></td>
		              <%if(!wectype.equals("ALL")){%>
	
		              <td vAlign = "middle"  align = "center" bgcolor="#CCCCCE" colspan="2"><b><%=wec[0]%>,<%=wec[1]%> KWh</b></td>
		              <%}%>
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
				 <%}%>
                  <tr bgcolor="#CCCCCC">
                  	  <td vAlign = "top" align = "center"><b><%=subname%></b></td>
		              <td vAlign = "middle" align = "center" colspan="2"><b>Site: <%=vdata.get(1)%></b></td>
                      <td vAlign = "middle" align = "center" colspan="8"><b><%=vdata.get(0)%></b></td>
                      <td vAlign = "middle" align = "center" colspan="2"><b>Capacity : <%=vdata.get(6)%></b></td>                      
		          </tr>
		       
		        <%  
		          //System.out.println("sitetranList.size()"+sitetranList.size());
		             if(sitetranList.size()==0)
		             { %>
		             <tr ><td align="center" colspan="12">No Data</td></tr>
		            	 
		            <% }%>
		               <%   for (int i=0; i <sitetranList.size(); i++)
		                  {
		                	  
		       
		                	  Vector vwec = new Vector();
		     				   vwec = (Vector)sitetranList.get(i); %>
		             
		             <tr>
		              <td vAlign = "middle" align = "center" ></td>
		              <td vAlign = "middle" align = "center" ><%=vwec.get(0)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(1)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(2)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(3)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(4)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(5)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(6)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(7)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(8)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(9)%></td>
                      <td vAlign = "middle" align = "center" ><%=vwec.get(10)%></td>
                     
                      <td vAlign = "middle" align = "center" ><%=vwec.get(12)%></td>
		             </tr>
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
		             
		             <tr>		             
		              <td vAlign = "middle" align = "center" colspan="2"><b>Total</b></td>
		              <td vAlign = "middle" align = "center" ><b><%=vwec.get(0)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(1)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(2)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(3)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(4)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(5)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(6)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(7)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(8)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(9)%></b></td>
                      <td vAlign = "middle" align = "center" ><b><%=vwec.get(11)%></b></td>                      
		             </tr>
		             <%
		             }sitetranList.clear();
		       	} 		       
          	} 
     	}
 	} 
} else {
	for (int h=0; h <wectypeList.size(); h++){ 
		 Vector wecty = new Vector();
		 wecty = (Vector)wectypeList.get(h);		
		 wectype = wecty.get(0).toString();
		 wec=wectype.split("/");
%>

<%
List tranList = new ArrayList();
List sitetranList = new ArrayList();

tranList = (List)secutils.getMPRHeading(stateid,wec[0]);  
// int  wecsize=tranList.size();
for (int j=0; j <tranList.size(); j++){ 

                 Vector vdata = new Vector();
				 vdata = (Vector)tranList.get(j);
				 String cnt = vdata.get(5).toString();
				 String cid = vdata.get(4).toString();
				 String stid = vdata.get(3).toString();
				 //System.out.println("cnt"+cnt);
				 //System.out.println("cid"+cid);
				 //System.out.println("stid"+stid);
				 
				  int tra=0; 
		         sitetranList = (List)secutils.getMPRDetailAdmin(stid,cid,fdate,tdate,wectype); 
		        
               if(cnt.equals("0")){}else{ 
                if(sitetranList.size()>0){%>
				 <tr>
		               <td vAlign = "middle" align = "center" colspan="3"><b>State:<%=sname%></b></td>
                      
                       <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="4"><b><%=wec[0]%> Monthly Performance Report</b></td>
		               <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="2"><b>Date:<%=fdate%>-<%=tdate%> </b></td>
		              <%if(!wectype.equals("ALL")){%>
	
		              <td vAlign = "middle"  align = "center" colspan="3"><b><%=wec[0]%>,<%=wec[1]%> KWh</b></td>
		              <%}%>
		            </tr>
					  <tr>
		              <td vAlign = "middle" align = "center" ROWSPAN="3"><b>Wec No.</b></td>
                      <td vAlign ="middle"  align = "center" colspan="2"><b>Generation</b></td>
                      <td vAlign = "middle" align = "center" ROWSPAN="3"><b>Lack Of Wind</b></td>
		              <td vAlign = "middle"  align = "center"colspan="5"><b>Down Time</b></td>
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
				 <%}%>
                  <tr bgcolor="#CCCCCC">
                  	  <td vAlign = "middle" align = "center" colspan="2"><b>Area: <%=vdata.get(7)%></b></td>
		              <td vAlign = "middle" align = "center" colspan="3"><b>Site: <%=vdata.get(1)%></b></td>
                      <td vAlign ="middle"  align = "center" colspan="5"><b><%=vdata.get(0)%></b></td>
                      <td vAlign = "middle" align = "center" colspan="2"><b>Capacity : <%=vdata.get(6)%></b></td>                      
		          </tr>
		       
		        <%  
		          //System.out.println("sitetranList.size()"+sitetranList.size());
		             if(sitetranList.size()==0)
		             { %>
		             <tr ><td align="center" colspan="12">No Data</td></tr>
		            	 
		            <% }%>
		               <%   for (int i=0; i <sitetranList.size(); i++)
		                  {  
		       
		                	  Vector vwec = new Vector();
		     				  vwec = (Vector)sitetranList.get(i); %>
		             
		             <tr>		             
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
		             </tr>
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
		             
		             <tr>
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
		             </tr>
		             <%
		             }sitetranList.clear();
		       	} 		       
          	} 
     	}
 	}
}
 %>
 </TBODY>
</TABLE>
</CENTER>
</BODY>
</HTML> --%>