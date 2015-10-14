<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>

<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Calendar"%>

<HTML>
<HEAD>
<%  
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>
 
<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">

<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	//String userid = session.getAttribute("loginID").toString();
	//String Customeridtxt = "";
%>
<script type="text/javascript">
function myFunction(stateid,siteid,custid,type,rdate)
{
alert(stateid);

var url="pdffile.jsp"+"?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate;
alert(siteid);
var name="PDFEXport"
window.open(url,name,'height=600,width=800,top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}
</script>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0"
	marginwidth="0">
<CENTER>
<%
	String custid = request.getParameter("id");
	String month = request.getParameter("month");
	String year = request.getParameter("year");
	String site = request.getParameter("site");
	String state = request.getParameter("state");
	String cname = request.getParameter("cname").replace("SPACE"," ");
	
	String acname = cname.replace(" ","SPACE");
	
%>

<%
String rdate="20"+"/"+month+"/"+year;
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24*20;
java.util.Date date= dateFormat.parse(rdate);



String prevdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
String crdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
//String ardate = dateFormat.format(date.getTime());
String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);

String pmonth=prevdate.substring(3,5);

String pyear=prevdate.substring(6,10);

String nmonth=nextDate.substring(3,5);

String nyear=nextDate.substring(6,10);
int nmn=Integer.parseInt(nmonth);
int nyr=Integer.parseInt(nyear);
Calendar now = Calendar.getInstance();    
int cmn=now.get(Calendar.MONTH)+1;
int cyear= now.get(Calendar.YEAR);
int s=1;
if (nyr == cyear && nmn > cmn)
{
	 s=0;
}

%>

<TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="100%" border=0>
	<TBODY>
		<tr>
			<TD align="left" colspan="2"></TD>
		     <td align="right" colspan="4"> Export To:
				<input  align="right" type="button" value="Excel" onClick=location.href="ExportBillingReport.jsp?id=<%=custid%>&month=<%=month%>&year=<%=year%>&site=<%=site%>&state=<%=state%>&cname=<%=acname%>">		
				<input align="right" type="button" value="Print" onClick="window.print()" class="printbutton">
		  	</td>
		</tr>
		<TR>
			<td colspan="6"></td>
		</tr>
	</TBODY>
</TABLE>

<TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="100%" border=0>

	<tr>
		<td colspan="2" align="left"><input type="button" style="color:white;font=bold;background-color:green" value="Previous"  onClick=location.href="NewBillingReport.jsp?id=<%=custid%>&month=<%=pmonth%>&year=<%=pyear%>&site=<%=site%>&state=<%=state%>&cname=<%=acname%>"></TD>
		<td colspan="4" align="center"></td>
		<td colspan="2"  align="right">
		<% if(s==1){  %>
		<input type="button" style="color:white;font=bold;background-color:green" value="Next" onClick=location.href="NewBillingReport.jsp?id=<%=custid%>&month=<%=nmonth%>&year=<%=nyear%>&site=<%=site%>&state=<%=state%>&cname=<%=acname%>">
		<%} %>
		</TD>
   </TR>
		
		

	
</TABLE>

<%
	List tranList = new ArrayList();
	DecimalFormat df = new DecimalFormat("0.##");
	CustomerUtil custutils = new CustomerUtil();
	tranList = (List) custutils.getBillingDetail(custid, month, year,site,state);
	String cls = "TableRow1";
	String ebid = "";
	
	String loc = "";
	String Remarks = "";
	String RemarksWEC = "";
	int j=0;
	int g=1;
	if(g==0)
	{%>
	
	                 
		<TABLE> 
		<TBODY>	
		<TR>
		<TD>
		<TABLE>
		<TBODY>
	<% }
	
	double t1=0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0,t9=0;
	int twec=0,k=0;
	for (int i = 0; i < tranList.size(); i++) 
	{   j=1;
	    k=k+1;
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		
		String sloc=v.get(0).toString();
		
 
 


if(!loc.equals(sloc))
		{
	     
	     if(i !=0){ 
			   if(k>1) {%>
		
		   <TR  class=TableTitleRow>
						<TD class=TableCell colspan="3">Total</TD>
						
						<TD class=TableCell ><%=twec%></TD>
						
						<TD class=TableCell><%=df.format(t1)%></TD>
						<TD class=TableCell><%=df.format(t2)%></TD>
						<TD class=TableCell><%=df.format(t3)%></TD>
						<TD class=TableCell><%=df.format(t4)%></TD>
						<TD class=TableCell><%=df.format(t5)%></TD>
						<TD class=TableCell><%=df.format(t6)%></TD>
						<TD class=TableCell><%=df.format(t7)%></TD>
						<TD class=TableCell><%=df.format(t8)%></TD>
						<TD class=TableCell><%=df.format(t9)%></TD>
					
					</TR>
					<%}} %>
		  </TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
		
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<%if(i==0){ %>
		<tr class=TableTitleRow>
			<td vAlign=middle width=100% class="TableCell" align=center colspan="4"><%=cname%> : SITE - <%=v.get(0)%></td>
		</tr>
		<tr class=TableTitleRow>
			<td vAlign=middle width=100% class="TableCell" align=center colspan="4">BILLING REPORT FOR THE MONTH :<%=v.get(2)%></td>
		</tr>
		<%}else{ %>
		<tr class=TableTitleRow>
			<td vAlign=middle  align=center class="TableCell" width=100% colspan="4">&nbsp;</td>
		</tr>
		<tr class=TableTitleRow>
			<td vAlign=middle width=100% class="TableCell" align=center colspan="4"><u>SITE- <%=v.get(0)%></u></td>
		</tr>
		<%} %>
		<tr class=TableTitleRow>
			<td vAlign=middle  align=center class="TableCell" width=100% colspan="4">&nbsp;</td>
		</tr>

		

	</TBODY>
</TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#555555 border=0>
	<TBODY>
		<TR>
			<TD>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<TBODY>
                	<TR class=TableTitleRow>
					    <TD class=TableCell width="2.28%">Sr no.</TD>
						<TD class=TableCell width="9.00%">Meter Point</TD>
						
						<TD class=TableCell width="11.00%">Billing Period</TD>
						<TD class=TableCell width="5.28%">WEC Connected</TD>
						<TD class=TableCell width="7.28%">WEC Generation</TD>
						
						<TD class=TableCell width="7.28%">KWH Export</TD>
						<TD class=TableCell width="7.28%">KWH Import </TD>
						<TD class=TableCell width="5.28%">Rkvah Export Lag </TD>
					    <TD class=TableCell width="5.28%">Rkvah Export Lead</TD>
						<TD class=TableCell width="5.28%">Rkvah Import Lag</TD>
						<TD class=TableCell width="5.28%">Rkvah Import Lead</TD>
						<TD class=TableCell width="5.28%">KVAH Export</TD>
						<TD class=TableCell width="5.28%">KVAH Import</TD>
					</TR>
<%  
loc=sloc;
t1=0;t2=0;t3=0;t4=0;t6=0;t7=0;t8=0;t9=0;
twec=0;k=0;
} %>



					<%
						
					 		
							int rem = 1;
							rem = i % 2;

							if (rem == 0)
								cls = "TableRow2";
							else
								cls = "TableRow1";
					%>
					<TR class=<%=cls%>>
						<TD class=TableCell><%=i+1%></TD>
						<TD class=TableCell><%=v.get(1)%></TD>
						
						<% if(!v.get(4).toString().equals("")){%>
						<TD class=TableCell><%=v.get(4)%> To <%=v.get(3)%></TD>
						<%}else{ %>
						<TD class=TableCell>First Reading:<%=v.get(3)%></TD>
						
						<%}%>
						<TD class=TableCell><%=v.get(14)%></TD>
						<TD class=TableCell><%=v.get(13)%></TD>
						<TD class=TableCell><%=v.get(5)%></TD>
						<TD class=TableCell><%=v.get(6)%></TD>
						<TD class=TableCell><%=v.get(7)%></TD>
						<TD class=TableCell><%=v.get(8)%></TD>
						<TD class=TableCell><%=v.get(9)%></TD>
						<TD class=TableCell><%=v.get(10)%></TD>
						<TD class=TableCell><%=v.get(11)%></TD>
						<TD class=TableCell><%=v.get(12)%></TD>
					
					</TR>
				






<%
						twec=twec+Integer.parseInt(v.get(14).toString());
						if(!v.get(13).equals("-"))
						  t1=t1+Double.parseDouble(v.get(13).toString());
						t2=t2+Double.parseDouble(v.get(5).toString());
						t3=t3+Double.parseDouble(v.get(6).toString());
						t4=t4+Double.parseDouble(v.get(7).toString());
						t5=t5+Double.parseDouble(v.get(8).toString());
						t6=t6+Double.parseDouble(v.get(9).toString());
						t7=t7+Double.parseDouble(v.get(10).toString());
						t8=t8+Double.parseDouble(v.get(11).toString());
						t9=t9+Double.parseDouble(v.get(12).toString());
						
	}	tranList.clear();
	
	
   if(k>1) {%>

   <TR  class=TableTitleRow>
	<TD class=TableCell colspan="3">Total</TD>
	
	<TD class=TableCell ><%=twec%></TD>
	
	<TD class=TableCell><%=df.format(t1)%></TD>
	<TD class=TableCell><%=df.format(t2)%></TD>
	<TD class=TableCell><%=df.format(t3)%></TD>
	<TD class=TableCell><%=df.format(t4)%></TD>
	<TD class=TableCell><%=df.format(t5)%></TD>
	<TD class=TableCell><%=df.format(t6)%></TD>
	<TD class=TableCell><%=df.format(t7)%></TD>
	<TD class=TableCell><%=df.format(t8)%></TD>
	<TD class=TableCell><%=df.format(t9)%></TD>

</TR>
<%} %>

  
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>

<%


if(j==0){ %>
   <font size="3">Sorry!Selected period records are not available in the system! </font>
    

<%} %>
</CENTER>
</BODY>
</HTML>
