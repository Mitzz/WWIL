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
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

%>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
  
<%

String custid=request.getParameter("custid");
String rdate=request.getParameter("rd");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");
System.out.print(custid+","+rdate+","+stateid+","+siteid);
String ebid="";
String wecid="";
String state="";

String cname="";
String type="Y";
String wectype="";
String wecname="";


SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);
int month=ffd.getMonth()+1;


//int month=ffd.getMonth()+1;


String year=rdate.substring(6);
//String syear="";




int cyear = 2000+ffd.getYear()-100;
int nyear =cyear;
//System.out.println("Month: " + month);
//System.out.println("Year: " + cyear);
 	 			     	     if(month>=4)
 	 			     	     	{
 	 			     			   nyear=cyear+1;
 	 			     		    }
 	 			     	    else
	 			    	        {   nyear=cyear;
	 			    	            cyear=cyear-1;
	 			    	        }
 	 			     		String pdate=Integer.toString(cyear);
 	 			     		String ndate=Integer.toString(nyear);;
//String syear="";


String monthname="";
if(month==4)
{
	   monthname="APRIL";
	}
	if(month==3)
	{	
	monthname="MARCH";
	}
	if(month==1)
	{	
	monthname="JANUARY";
	}
	if(month==2)
	{
	monthname="FEBRUARY";
	}

	if(month==5)
	{
		monthname="MAY";
	}

	if(month==6)
	{
	monthname="JUNE";
	}

	if(month==7)
	{
	monthname="JULY";
	}

	if(month==8)
	{
		monthname="AUGUST";
	}

	if(month==9)
	{
		monthname="SEPTEMBER";
	}

	if(month==10)
	{
		monthname="OCTOBER";
	}

	if(month==11)
	{	
		monthname="NOVEMBER";
	}

	if(month==12)
	{	
		monthname="DECEMBER";
	}
String cls="TableRow1";

%>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  
   
  
   <TR>
    
    <TD class=SectionTitle colspan="3" noWrap><input  type="button" value="Excel" onClick=location.href="ExportAllAdmin.jsp?custid=<%=custid%>&rd=<%=rdate%>&siteid=<%=siteid%>&stateid=<%=stateid%>">  </TD>
    </TR>
    
  <TR>
    
    <TD class=SectionTitle colspan="3" noWrap>Yearly Generation Report  </TD>
    </TR></TABLE>
  
<P><BR>
<%

List tranList = new ArrayList();

CustomerUtil secutils = new CustomerUtil();
tranList = (List)secutils.getEBHeading(custid,rdate,stateid,siteid); 

String Remarks="NIL";
for (int i=0; i <tranList.size(); i++)
{
	Vector v = new Vector();
	v = (Vector)tranList.get(i);
    ebid=(String)v.get(5);
    
    cname=(String)v.get(0).toString().replaceAll("&","and");
    state=(String)v.get(3)+"-"+(String)v.get(4);

  %>
  <%  
        
          List tranListData = new ArrayList();
          tranListData = (List)secutils.getWECData(ebid,rdate,type); 
         
      cls="TableRow1";
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0)
      { 
    	    for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				wecname = (String)vdata.get(0);
				wecid=(String)vdata.get(8);
				wectype=(String)vdata.get(1);
		
      
      
      %>
 <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  
<tr class=TableTitle>
<td vAlign = middle class=TableCell align = center width = 100% colspan="6"><%=cname%></td>
		            
		            </tr>
		            <tr class=TableSummaryRow>
		          <td vAlign = middle class=TableCell width = 100% align = center colspan="6"></td>
		           </tr>
		           
		       
		             
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>

		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell1 align = left width = 20%>Location:</td>
		           <td vAlign = middle  align = left width = 38% colspan=2><%=state%> </td>
		           
		            <td vAlign = middle  class=TableCell1 align = left width = 18%>Year: </td>
		           <td vAlign = middle align = left width = 24% colspan=2><%=pdate%>-<%=ndate%></td>
		           
		             <tr class=TableSummaryRow>
		           <td vAlign = middle  align = center width = 100% colspan="6"></td>
		           </tr>
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell1 align = left width = 20%>Capacity:</td>
		           <td vAlign = middle  align = left width = 38% colspan=2><%=wectype%> </td>
		           
		            <td vAlign = middle  class=TableCell1 align = left width = 18%>WEC: </td>
		           <td vAlign = middle  align = left width = 24% colspan=2><%=wecname%></td>
		           
		             <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>
		          
		          

</TBODY></TABLE>

<P></P>
<SPAN class=TableTitle>WEC Data </SPAN><BR>
<TABLE height=6 cellSpacing=0 cellPadding=0 border=0>
  <TBODY>
  <TR>
    <TD></TD></TR></TBODY></TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#555555 border=0>
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
        <TBODY>
        
          
       <%  
       
       
       List wecListData = new ArrayList();
        wecListData = (List)secutils.getWECMonthwiseAdmin(ebid,wecid,rdate,type);   
         
      cls="TableRow1";
      //System.out.println("tranListData"+wecListData.size());
      int  wec_size=wecListData.size();
      if(wec_size>0){ %>
    	<TR class=TableTitleRow>
    	   <TD class=TableCell width="100">WEC</TD>
           <TD class=TableCell width="100">Month</TD>
           
          <TD class=TableCell width="100">Generation</TD> 
          <TD class=TableCell width="50">Operating Hours </TD>
          <TD class=TableCell width="50">Lull Hours </TD>
          <TD class=TableCell width="50">Machine Fault</TD>
          <TD class=TableCell width="50">Machine S/D</TD>
          <TD class=TableCell width="50">Internal Fault </TD>
          <TD class=TableCell width="150">Internal S/D</TD>
         <TD class=TableCell width="50">External Fault </TD>
          <TD class=TableCell width="50">External S/D</TD>
           <TD class=TableCell width="50">Total HRS.</TD>
          <TD class=TableCell width="50">Capacity Factor(%) </TD>
          
          <TD class=TableCell width="50">Machine Availability(%)</TD>
          <TD class=TableCell width="50">Grid Int Availability(%) </TD>
          <TD class=TableCell width="50">Grid  Ext Availability(%) </TD>
          <TD class=TableCell width="50">Winffarm Availability(%) </TD>
          <TD class=TableCell width="50">WEC_SPSD </TD>
          <TD class=TableCell width="50">EB_SPSD  </TD>
        </TR>  
    	   
    	  
   <%   for (int k=0; k <wecListData.size(); k++)
			{   
				Vector wecdata = new Vector();
				wecdata = (Vector)wecListData.get(k);
				String name = (String)wecdata.get(0);
				//String gen =String.parse(vdata.get(1));
              //String gen ="23.00";
				//String gen2 =gen.toString();

				//String ghrs = (String)vdata.get(2);
				//String lhrs = (String)vdata.get(3);
				//String mavdataail = (String)vdata.get(4);
				//String gavdataail = (String)vdata.get(5);
				//String cfactor = (String)vdata.get(6);
				//String stateid = (String)vdata.get(7);
				int rem=1;
				rem=k%2;
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
        
            		%>
        
        <TR class=<%=cls%>>
            <TD class=TableCell><%=wecname%></TD>
          <TD class=TableCell><%=wecdata.get(0)%></TD>
          <TD class=TableCell><%=wecdata.get(1)%></TD>
          <TD class=TableCell><%=wecdata.get(2)%></TD>
          <TD class=TableCell><%=wecdata.get(3)%></TD>
          
          <TD class=TableCell><%=wecdata.get(7)%></TD>
          <TD class=TableCell><%=wecdata.get(8)%></TD>
          <TD class=TableCell><%=wecdata.get(9)%></TD>
          <TD class=TableCell><%=wecdata.get(10)%></TD>
          <TD class=TableCell><%=wecdata.get(11)%></TD>
          <TD class=TableCell><%=wecdata.get(12)%></TD>
          <TD class=TableCell><%=wecdata.get(13)%></TD>
          
          <TD class=TableCell><%=wecdata.get(6)%></TD>
          <TD class=TableCell><%=wecdata.get(4)%></TD>
         
          
          <TD class=TableCell><%=wecdata.get(14)%></TD>
           <TD class=TableCell><%=wecdata.get(5)%></TD>
          <TD class=TableCell><%=wecdata.get(15)%></TD>
           <TD class=TableCell><%=wecdata.get(16)%></TD>
            <TD class=TableCell><%=wecdata.get(17)%></TD>
          </TR>
      
        
        
        <% }%> 
        
      




        <% 	
        wecListData.clear();
        wecListData = (List)secutils.getWECDetailTotalAdmin(ebid,wecid,rdate,"Y");   
        for (int l=0; l <wecListData.size(); l++)
		{
			Vector wecdata = new Vector();
			wecdata = (Vector)wecListData.get(l);
			//String name = (String)vdata.get(0);
			%> 
				<TR class=TableSummaryRow>
			
	        <TD class=TableCell colspan="2">Total:</TD>
	        
          <TD class=TableCell><%=wecdata.get(0)%></TD>
            <TD class=TableCell><%=wecdata.get(1)%></TD>
          <TD class=TableCell><%=wecdata.get(2)%></TD>
          <TD class=TableCell><%=wecdata.get(3)%></TD>
          <TD class=TableCell><%=wecdata.get(4)%></TD>
          <TD class=TableCell><%=wecdata.get(5)%></TD>
          <TD class=TableCell><%=wecdata.get(6)%></TD>
          <TD class=TableCell><%=wecdata.get(7)%></TD>
          <TD class=TableCell><%=wecdata.get(8)%></TD>
          <TD class=TableCell><%=wecdata.get(12)%></TD>
          <TD class=TableCell><%=wecdata.get(11)%></TD>
          <TD class=TableCell><%=wecdata.get(9)%></TD>
           <TD class=TableCell><%=wecdata.get(13)%></TD>
          <TD class=TableCell><%=wecdata.get(10)%></TD>
          <TD class=TableCell><%=wecdata.get(14)%></TD>
           <TD class=TableCell><%=wecdata.get(15)%></TD>
            <TD class=TableCell><%=wecdata.get(16)%></TD>
          <TR>
	        
	        <% }}
      wecListData.clear();
	        %> 
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR>
  
    	<% }} tranListData.clear();}%> 
      
</CENTER></BODY></HTML>