<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK 
href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();

%>
<script type="text/javascript">
function myFunction(ur,wty,wna,eb,wid,rd,state,cname,type)
{

var url=ur+"?wectype="+wty+ "&wecname="+wna+ "&ebid="+eb+ "&wecid="+wid+ "&rd="+rd+ "&state="+state + "&cname="+cname +"&type="+type;

window.open(url,'name','height=600,width=1000, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}

function myAllFunction(ur,custid,rdate,siteid,stateid)
{

var url=ur+"?custid="+custid+ "&rd="+rdate+ "&stateid="+stateid+ "&siteid="+siteid;

window.open(url,'name','height=600,width=1000, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}

</script>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
  


<%
	CustomerUtil secutils = new CustomerUtil();
%>

<% 
SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

//System.out.println("rdate1: " + rdate);

%>






<SPAN class=TableTitle>Feedback Report</SPAN><BR>

<TABLE cellSpacing=0 cellPadding=0 width="100%"   border="1">
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing=1 cellPadding=1 width="100%" border="1">
        <TBODY>
        
        
       <%  
       
       
       List tranListData = new ArrayList();
          tranListData = (List)secutils.getFeedbackReport(); 
         
      //cls="TableRow1";
      //////System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>
    	  
    	<TR class=TableTitleRow>
           <TD class=TableCell width="10">Sr. No.</TD>
          <TD class=TableCell width="50">Customer</TD>
           
          <TD class=TableCell width="50">State</TD>
          <TD class=TableCell width="50">Site</TD>
          <TD class=TableCell width="50">WEC Type </TD>
          <TD class=TableCell width="30">Criteria</TD>
          
          <TD class=TableCell width="10">PM S</TD>
          <TD class=TableCell width="10">PM E</TD>
          <TD class=TableCell width="10">PM Q </TD>
          <TD class=TableCell width="10">BM E</TD>
          <TD class=TableCell width="10">BM S </TD>
          <TD class=TableCell width="10">BM E</TD>
          <TD class=TableCell width="10">MP C</TD>
          <TD class=TableCell width="10">MP S </TD>
          <TD class=TableCell width="10">MP R</TD>
          <TD class=TableCell width="10">MA </TD>
          <TD class=TableCell width="10">CF</TD>
          <TD class=TableCell width="10">Lias.T.</TD>
          <TD class=TableCell width="10">Lias. N. </TD>
          <TD class=TableCell width="10">GA </TD>
          <TD class=TableCell width="10">Reporting R </TD>
          <TD class=TableCell width="10">Reporting C </TD>
          <TD class=TableCell width="10">Reporting A </TD>
          <TD class=TableCell width="10">Introduction FC </TD>
          <TD class=TableCell width="10">Introduction FR </TD>
          <TD class=TableCell width="10">Introduction FTS </TD>
          <TD class=TableCell width="10">Introduction CC </TD>
          <TD class=TableCell width="10">Introduction CR </TD>
          <TD class=TableCell width="10">Introduction CTS </TD>
          <TD class=TableCell width="10">General MH </TD>
          <TD class=TableCell width="10">General W  </TD>
          <TD class=TableCell width="10">General Q.C. </TD>
          <TD class=TableCell width="50">Remarks</TD>
          <TD class=TableCell width="50">Date</TD>
          <TD class=TableCell width="50">IP Address</TD>
        </TR>  
    	  
   <%   for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				String name = (String)vdata.get(1);
			
			//	int rem=1;
			//	rem=j%2;
				
           // if(rem==0)
            //	cls="TableRow2";
            //else
            //	cls="TableRow1";
        
            		%>
        
        <TR class=TableRow1>
       
         <TD class=TableCell rowspan="2">
         		 <%=j+1%>
         </TD>
            <TD class=TableCell rowspan="2">
            <%
           List tList = (List) secutils.getCustList(name);
	
	
			String cname1 = "";
	
			for (int i1 = 0; i1 < tList.size(); i1++) {
				Vector v1 = new Vector();
				v1	 = (Vector) tList.get(i1);
		
				cname1 = (String) v1.get(0);%>
			<%=cname1%>	<br>
  			<% }
			tList.clear();
  			%>
            
            
            </TD>
            
            
            <TD class=TableCell  rowspan="2">
            
            <% 
            tList = (List) secutils.getStateList(name);
	
	
			cname1 = "";
	
			for (int i1 = 0; i1 < tList.size(); i1++) {
				Vector v1 = new Vector();
				v1	 = (Vector) tList.get(i1);
		
		
		
				cname1 = (String) v1.get(0);%>
			<%=cname1	 %>	<br>
  			<% }
			tList.clear();
  			%>
            
            </TD>
            
            
            <TD class=TableCell  rowspan="2">
            
            <% 
            tList = (List) secutils.getSiteList(name);
	
	
			cname1 = "";
	
			for (int i1 = 0; i1 < tList.size(); i1++) {
				Vector v1 = new Vector();
				v1	 = (Vector) tList.get(i1);
		
		
		
				cname1 = (String) v1.get(0);%>
			<%=cname1	 %>	<br>
  			<% }
			tList.clear();
  			%>
            
            </TD>
            
              <TD class=TableCell  rowspan="2">
            
            <%
            tList = (List) secutils.getWecTypeList(name);
	
	
			 cname1 = "";
	
			for (int i1 = 0; i1 < tList.size(); i1++) {
				Vector v1 = new Vector();
				v1	 = (Vector) tList.get(i1);
				cname1 = (String) v1.get(0);%>
			<%=cname1	 %>	<br>
  			<% }
			tList.clear();
  			%>
            
            </TD>
            <TD class=TableCell>Concern</TD> 
            <TD class=TableCell><%=vdata.get(2)%></TD> 
            <TD class=TableCell><%=vdata.get(3)%></TD>
            <TD class=TableCell><%=vdata.get(4)%></TD>
            <TD class=TableCell><%=vdata.get(5)%></TD>
            <TD class=TableCell><%=vdata.get(6)%></TD>
            <TD class=TableCell><%=vdata.get(7)%></TD>
            <TD class=TableCell><%=vdata.get(8)%></TD>
            <TD class=TableCell><%=vdata.get(9)%></TD>
            <TD class=TableCell><%=vdata.get(10)%></TD>
            <TD class=TableCell><%=vdata.get(11)%></TD>
            <TD class=TableCell><%=vdata.get(12)%></TD>
            <TD class=TableCell><%=vdata.get(13)%></TD>
            <TD class=TableCell><%=vdata.get(14)%></TD>
            <TD class=TableCell><%=vdata.get(15)%></TD>
            <TD class=TableCell><%=vdata.get(16)%></TD>
            
            <TD class=TableCell><%=vdata.get(17)%></TD> 
            <TD class=TableCell><%=vdata.get(18)%></TD>
            <TD class=TableCell><%=vdata.get(19)%></TD>
            <TD class=TableCell><%=vdata.get(20)%></TD>
            <TD class=TableCell><%=vdata.get(21)%></TD>
            <TD class=TableCell><%=vdata.get(22)%></TD>
            <TD class=TableCell><%=vdata.get(23)%></TD>
            <TD class=TableCell><%=vdata.get(24)%></TD>
            <TD class=TableCell><%=vdata.get(25)%></TD>
            <TD class=TableCell><%=vdata.get(26)%></TD>
            <TD class=TableCell><%=vdata.get(27)%></TD>
            <TD class=TableCell rowspan="2"><%=vdata.get(28)%></TD>
            <TD class=TableCell rowspan="2"><%=vdata.get(29)%></TD>
             <TD class=TableCell rowspan="2">
           <%
            tList = (List) secutils.getIPAddress(name,vdata.get(29).toString());
	
	
			 cname1 = "";
	
			for (int i1 = 0; i1 < tList.size(); i1++) {
				Vector v1 = new Vector();
				v1	 = (Vector) tList.get(i1);
				cname1 = (String) v1.get(0);%>
			<%=cname1	 %>	<br>
  			<% }
			tList.clear();
  			%>
  			</TD>
          </TR>
          <TR class=TableRow2>
          <TD class=TableCell>Rating</TD> 
            
            <TD class=TableCell><%=vdata.get(30)%></TD>
            <TD class=TableCell><%=vdata.get(31)%></TD>
            <TD class=TableCell><%=vdata.get(32)%></TD>
            <TD class=TableCell><%=vdata.get(33)%></TD>
            <TD class=TableCell><%=vdata.get(34)%></TD>
            <TD class=TableCell><%=vdata.get(35)%></TD>
            <TD class=TableCell><%=vdata.get(36)%></TD>
            <TD class=TableCell><%=vdata.get(37)%></TD>
            <TD class=TableCell><%=vdata.get(38)%></TD>
            <TD class=TableCell><%=vdata.get(39)%></TD>
            <TD class=TableCell><%=vdata.get(40)%></TD>
            <TD class=TableCell><%=vdata.get(41)%></TD>
            <TD class=TableCell><%=vdata.get(42)%></TD>
            <TD class=TableCell><%=vdata.get(43)%></TD>
            <TD class=TableCell><%=vdata.get(44)%></TD> 
            <TD class=TableCell><%=vdata.get(45)%></TD>
            <TD class=TableCell><%=vdata.get(46)%></TD>
            <TD class=TableCell><%=vdata.get(47)%></TD>
            <TD class=TableCell><%=vdata.get(48)%></TD>
            <TD class=TableCell><%=vdata.get(49)%></TD>
            <TD class=TableCell><%=vdata.get(50)%></TD>
            <TD class=TableCell><%=vdata.get(51)%></TD>
            <TD class=TableCell><%=vdata.get(52)%></TD>
            <TD class=TableCell><%=vdata.get(53)%></TD>
             <TD class=TableCell><%=vdata.get(54)%></TD>
            <TD class=TableCell><%=vdata.get(55)%></TD>
           
          
          </TR>
        
       <%} } tranListData.clear();%>
      
</TBODY></TABLE></TD></TR></TBODY></TABLE>	

</CENTER></BODY></HTML>