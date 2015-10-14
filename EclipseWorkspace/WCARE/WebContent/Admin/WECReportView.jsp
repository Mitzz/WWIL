<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="java.util.*" %>

<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<META http-equiv=Content-Type content="text/html; charset=windows-1252">

<style type="text/css" media="print">
.printbutton {
  visibility: hidden;
  display: none;
}
</style>

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/site_jui.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/demo_table_jui.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/jquery-ui-1.7.2.custom.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/Report.css" type="text/css">
<SCRIPT src="<%=request.getContextPath()%>/resources/js/complete.min.js" type=text/javascript></SCRIPT>

<SCRIPT src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js" type=text/javascript></SCRIPT>

<SCRIPT type=text/javascript>
			function fnFeaturesInit ()
			{
				/* Not particularly modular this - but does nicely :-) */
				$('ul.limit_length>li').each( function(i) {
					if ( i > 10 ) {
						this.style.display = 'none';
					}
				} );				
				$('ul.limit_length').append( '<li class="css_link">Show more<\/li>' );
				$('ul.limit_length li.css_link').click( function () {
					$('ul.limit_length li').each( function(i) {
						if ( i > 5 ) {
							this.style.display = 'list-item';
						}
					} );
					$('ul.limit_length li.css_link').css( 'display', 'none' );
				} );
			}			
			$(document).ready( function() {
				fnFeaturesInit();
				$('#example').dataTable( {
					"bJQueryUI": true,
					"sPaginationType": "full_numbers"
				} );				
				SyntaxHighlighter.config.clipboardSwf = 'media/javascript/syntax/clipboard.swf';
				SyntaxHighlighter.all();
			} );
</SCRIPT>

<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
%>


</HEAD>
<BODY text=#000000 bottomMargin=0 leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
<%

String state=request.getParameter("state");
String area=request.getParameter("area");
String substation=request.getParameter("substation");
String feeder=request.getParameter("feeder");
String site=request.getParameter("site");
String cust=request.getParameter("cust");
String eb=request.getParameter("eb");
String wec=request.getParameter("wec");
String wectype=request.getParameter("wectype");
String comm=request.getParameter("comm");
String comm1=request.getParameter("comm1");
String wecStatus=request.getParameter("WECStatustxt");

AdminUtil adminutil = new AdminUtil();
String cls="TableRow1";

%> 
 <table width="100%" border="0">    
 	<tr><td class="SectionTitle1" colspan="14">&nbsp;</td></tr> 
 	<tr><td class="SectionTitle1" colspan="14" align="center">WEC Master Data Report</td></tr>
    <tr><td class="SectionTitle1" colspan="14" align="left">
	      <input  type="button" value="Excel" onClick=location.href='ExportWECReportView.jsp?state=<%=state%>&area=<%=area%>&substation=<%=substation%>&feeder=<%=feeder%>&site=<%=site%>&cust=<%=cust%>&eb=<%=eb%>&wec=<%=wec%>&wectype=<%=wectype%>&comm=<%=comm%>&comm1=<%=comm1%>&wecStatus=<%=wecStatus%>'>
	      <input type="button" value="Print" onClick="window.print()" class="printbutton"></td>	    
	</tr> 
 </table>
 <table   class=display id=example style="WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
<%if((wecStatus=="3")||(wecStatus.equals("3"))) {%>
        <thead >
       	 <tr class=SectionTitle>
    	  <th class=TableCell1 width="5.28%">Sr. No</th>
    	  <th class=TableCell1 width="10%">STATE</th>
    	  <th class=TableCell1 width="10%">AREA</th>
    	  <th class=TableCell1 width="10%">SUBSTATION</th>
    	  <th class=TableCell1 width="10%">FEEDER</th>
    	  <th class=TableCell1 width="10%">SITE</th>
          <th class=TableCell1 width="15%">WEC</th>
          <th class=TableCell1 width="15%">EB</th>
          <th class=TableCell1 width="20%">CUSTOMER </th>
          <th class=TableCell1 width="20%">CUSTOMER CODE</th>
          <th class=TableCell width="10%">FOUND. LOC.</th>
          <th class=TableCell width="10%">COMM. DATE</th>
          <th class=TableCell width="10%">STATUS</th>
          <th class=TableCell width="10%">WEC TYPE</th>          
          <th class=TableCell width="10%">CAPACITY</th>
          <th class=TableCell width="10%">TRANSFER DATE</th>          
          <th class=TableCell width="10%">WEC NAME</th>
        </tr>  
     </thead> 
<% } else { %>
		<thead >
       	 <tr class=SectionTitle>
    	  <th class=TableCell1 width="5.28%">Sr. No</th>
    	  <th class=TableCell1 width="10%">STATE</th>
    	  <th class=TableCell1 width="10%">AREA</th>
    	  <th class=TableCell1 width="10%">SUBSTATION</th>
    	  <th class=TableCell1 width="10%">FEEDER</th>
    	  <th class=TableCell1 width="10%">SITE</th>
          <th class=TableCell1 width="15%">WEC</th>
          <th class=TableCell1 width="15%">EB</th>
          <th class=TableCell1 width="20%">CUSTOMER </th>
          <th class=TableCell1 width="20%">CUSTOMER CODE</th>
          <th class=TableCell width="10%">FOUND. LOC.</th>
          <th class=TableCell width="10%">COMM. DATE</th>
          <th class=TableCell width="10%">STATUS</th>
          <th class=TableCell width="10%">WEC TYPE</th>          
          <th class=TableCell width="10%">CAPACITY</th>          
        </tr>  
     </thead> 
<%} %>
     <tbody class="scrollContent"> 
       <%  
       List tranListData = new ArrayList(); 
       tranListData = (List)adminutil.getWECMasterData(state,area,substation,feeder,site,cust,eb,wec,wectype,comm,userid,comm1,wecStatus);   
	   //cls="";
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>    	
     
   <%  
	   for (int j=0; j <tranListData.size(); j++)
		{   
			Vector vdata = new Vector();
			vdata = (Vector)tranListData.get(j);
			int rem=1;
			rem=j%2;
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
      		%>
      		<%if((wecStatus=="3")||(wecStatus.equals("3"))) {%>
      		
		        <TR class=<%=cls%>>
		           	<TD class=TableCell1><%=j+1%></TD>
		          	<TD class=TableCell1><%=vdata.get(0)%></TD>
		          	<TD class=TableCell1><%=vdata.get(13)%></TD>
		          	<TD class=TableCell1><%=vdata.get(14)%></TD>
		          	<TD class=TableCell1><%=vdata.get(15)%></TD>
		          	<TD class=TableCell1><%=vdata.get(1)%></TD>
		          	<TD class=TableCell1><%=vdata.get(2)%></TD>
		          	<TD class=TableCell1><%=vdata.get(3)%></TD>
		     	  	<TD class=TableCell1><%=vdata.get(4)%></TD>
		     	  	<TD class=TableCell1><%=vdata.get(10)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(5)%></TD> 
		     	  	<TD class=TableCell><%=vdata.get(6)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(7)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(8)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(9)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(11)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(12)%></TD>
	          	</TR>
	          <% } else { %>
	          	<TR class=<%=cls%>>
		           	<TD class=TableCell1><%=j+1%></TD>
		          	<TD class=TableCell1><%=vdata.get(0)%></TD>
		          	<TD class=TableCell1><%=vdata.get(11)%></TD>
		          	<TD class=TableCell1><%=vdata.get(12)%></TD>
		          	<TD class=TableCell1><%=vdata.get(13)%></TD>
		          	<TD class=TableCell1><%=vdata.get(1)%></TD>
		          	<TD class=TableCell1><%=vdata.get(2)%></TD>
		          	<TD class=TableCell1><%=vdata.get(3)%></TD>
		     	  	<TD class=TableCell1><%=vdata.get(4)%></TD>
		     	  	<TD class=TableCell1><%=vdata.get(10)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(5)%></TD> 
		     	  	<TD class=TableCell><%=vdata.get(6)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(7)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(8)%></TD>
		     	  	<TD class=TableCell><%=vdata.get(9)%></TD>
	          	</TR>
	          <%} %>
        
        <% }}
      else
      {%>
    	  <script type="text/javascript">
	    	  alert("Such Type Of Record Not Found Found In Database.");
	    	  self.close(); 
    	  </script>
      
     <% }tranListData.clear();%> 
        </tbody>
        </table>

      <script>
        stripedTable();
      </script>
</CENTER></BODY></HTML>