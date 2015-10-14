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
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@page import="com.enercon.security.utils.SecurityUtils;"%>

<HTML><HEAD>

<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<TITLE>DGR</TITLE>

<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script> 

<% 
response.setHeader("Pragma","no-cache");
//response.getOutputStream().flush();
//response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

%>
 
<script type="text/javascript">

function gotoSave()
{ 
         var rd=document.forms[0].Datetxt.value;
        
     	var pdate=rd;
     	
     	var cdate = document.forms[0].todaydate.value;;    
     	
	 	var todate=new Date(cdate.substr(6,4),cdate.substr(3,2) -1,cdate.substr(0,2)); //yyyy-mm-dd
   	 	var fromdate = new Date(pdate.substr(6,4),pdate.substr(3,2) -1,pdate.substr(0,2));
	 	var one_day=1000*60*60*24;	
    	 var days = (Math.ceil((todate.getTime()-fromdate.getTime())/one_day) );	
      
     	if(days<0)
  	 		{
	  	 		alert("Please check the date of your request.");
	  	 		return false;
  	 		}else
  	 		{
  	 		return true;
  	 		}
  }


</script>
</HEAD>
<BODY  bgcolor="#FFFFFF">
<FORM   bgcolor="#FFFFFF">

 
<%
String cls = "TableRow1";
//System.out.println("LoginType"+session.getAttribute("LoginType").toString());

int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
Date date = new Date();

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/yyyy");

String rdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
String ardate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
String nextDate = dateFormat1.format(date.getTime() + MILLIS_IN_DAY);

String month = "";
String year = "";
int intYearH = 2012;
int intYearL = 2011;

if(request.getParameter("Datetxt")!=null)
{
	ardate=request.getParameter("Datetxt");
	  
	Date  date1 = (Date)dateFormat.parse(ardate);
	nextDate = dateFormat1.format(date1.getTime());
	
	month = ardate.substring(3,5);
	year = ardate.substring(6);

	int intMonth = Integer.parseInt(month);

	intYearH = Integer.parseInt(year);

	if(intMonth<=3){
		intYearL = intYearH-1;
		intYearH = intYearH;    
	}else{
		intYearL = intYearH;
		intYearH = intYearH+1;	
	}
	
}
%>
 
<%
if (session.getAttribute("LoginType") == null)
{
      response.sendRedirect(request.getContextPath());
}


else if(session.getAttribute("LoginType").equals("C"))
{
   //System.out.println("LoginType"+session.getAttribute("LoginType").toString());


   %>
<CENTER>
 
	
  

 <%  
     //SecurityUtils secUtil = new SecurityUtils();
 	 //List tranList1 = new ArrayList();
 	 //String logid=session.getAttribute("loginID").toString();
 	
 	 //tranList1 = (List)session.getAttribute("custtypee");
 	
 	 //if(tranList1.size()==0)
 	 //{
 		//	 tranList1=secUtil.getcustomerdetails(logid);
 	 //}
     //String custid=request.getParameter("id");
     //String rdate=request.getParameter("rd");
   	 //String custid="";
     
     List tranList = new ArrayList();
     //List sitetranList = new ArrayList();
     //List msgList = new ArrayList();
     CustomerUtil secutils = new CustomerUtil();
    
   	//  tranList = (List)secutils.getCustomerDetail(logid);     
    // tranList = (List)secutils.getStateTotal(custid,rdate,"D"); 
  %>



<TABLE cellSpacing=0 cellPadding=0 width="90%"  border=0>
  <TBODY>
  
     <TR class=TableTitleRow>
      <TD class=TableCell colspan="4" >Select Date:<input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" value=<%=ardate%>  onfocus="dc.focus()" />
			<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.forms[0].Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
		<input type="submit" value="Generate" onSubmit=location.href="ERDACustomerBoard.jsp?Datetxt=<%=ardate%>">  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Export To:<input align="right" type="button" value="Excel" onClick=location.href="ExcelERDABoard.jsp?Datetxt=<%=ardate%>">	
	  </TD>
    </TR>
    
  <TR>
 
    <TD>
    
    <TABLE cellSpacing=1 cellPadding=2 width="100%" border="1">
          <TBODY>
        
       <%  
       
       //String custid=request.getParameter("id");
       //String rdate=request.getParameter("rd");
       //String sateid = "";
     
      tranList.clear();
     // tranList = (List)secutils.getERDADetail(custid,sateid,ardate); 
      tranList = (List)secutils.getERDADetail1(ardate);
     
     // msgListsize = tranList.size();
      %>
     <TR class=TableTitleRow>
       
      <TD class=TableCell colspan="6" ><font size="5">Vaayu Windfarm Power Generation Report</font>	</TD>
	
     
       
    </TR>
     
      
    
      <TR class=TableTitleRow>
      <TD class=TableCell colspan="4" ><font size="2">&nbsp;</font></TD>
     
       <TD class=TableCell COLSPAN="2">Cumulative </TD>
      
    </TR>
      <TR class=TableTitleRow>
      <TD class=TableCell colspan="3" >&nbsp;</TD>
       <TD class=TableCell >Daily</TD>
       <TD class=TableCell >Month (<%=nextDate.substring(3,6)%>)</TD>
       <TD class=TableCell >FY (<%=intYearL%>-<%=intYearH%>)</TD>
    </TR>
    	  
    	 
          <TR class=TableTitleRow>
            <TD class=TableCell1 width="2%">Sr.</TD>
           
            <TD class=TableCell1 width="15%">Project</TD>
            
           
            <TD class=TableCell width="9%">Project Size(MW) </TD>
            <TD class=TableCell width="10%">Generation (KWH)</TD>
           
             <TD class=TableCell width="10%">Generation (KWH)</TD>
             <TD class=TableCell width="10%">Generation (KWH)</TD>
          </TR>
   <%  	 int tcumgen=0;
         int tcumygen=0;
         int tgen=0;
         double tcap=0;
         String pname="";
         String name="";
         String psname="";
         String sname="";
         DecimalFormat formatter = new DecimalFormat("#########0.00");
         
         for (int i=0; i <tranList.size(); i++)
			{
        	 
        	 %>
        	 
        	 <script type="text/javascript">
             
        	     parent.window.document.getElementById("progressbar").style.display ="block";
				 parent.window.document.getElementById("progressbar").style.display ="none";
        		
        		</script>
        		<% 
				Vector v = new Vector();
				v = (Vector)tranList.get(i);
				pname=name;
				name = (String)v.get(0);
				
				psname=sname;
				sname = (String)v.get(1);
			
				//String gen =String.parse(v.get(1));
                //String gen ="23.00";
				//String gen2 =gen.toString();

				//String ghrs = (String)v.get(2);
				//String lhrs = (String)v.get(3);
				//String mavail = (String)v.get(4);
				//String gavail = (String)v.get(5);
				//String cfactor = (String)v.get(6);
				//String stateid = (String)v.get(7);
				int rem=1;
				rem=i%2;
				
				if(!v.get(3).toString().equals("0"))
	            {
	            	
	            	tcap=tcap+Double.parseDouble(v.get(3).toString());
	            }
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
            String aval="Yet To Publish";
            if(!v.get(4).toString().equals("0"))
            {
            	aval=v.get(4).toString();
            	tgen=tgen+Integer.parseInt(aval);
            }
            	
            String cval="Yet To Publish";
            if(!v.get(5).toString().equals("0"))
            {
            	cval=v.get(5).toString();
            	tcumgen=tcumgen+Integer.parseInt(cval);
            }
            String yval="Yet To Publish";
            if(!v.get(6).toString().equals("0"))
            {
            	yval=v.get(6).toString();
            	tcumygen=tcumygen+Integer.parseInt(yval);
            }	
        
            if(!v.get(3).toString().equals("0")&& !v.get(4).toString().equals("0"))
            {
            		%>
        
        <TR class=<%=cls%>>
          <TD class=TableCell1><%=i+1%></TD>
          
          <TD class=TableCell1>
         
          <a href="ERDAStateWise.jsp?cid=<%=v.get(7).toString()%>&sid=<%=v.get(2).toString()%>&rdate=<%=ardate%>&stname=<%=v.get(1).toString()%>&stcap=<%=v.get(3).toString()%>"   target="_blank"><%=v.get(1)%></a>
         
          
          
          </TD>
        
          <TD class=TableCell><%=v.get(3)%></TD>
          <TD class=TableCell><%=aval%></TD>
          <TD class=TableCell><%=cval%></TD>
          <TD class=TableCell><%=yval%></TD>
         </TR>
        
        <% }}%> 
        
        
        <%if(tranList.size()>1){ %>
        <TR class=<%=cls%>>
          <TD class=TableCell colspan="2"><b>Total</b></TD>
          <TD class=TableCell><%=formatter.format(tcap)%></TD>
          <TD class=TableCell><%=tgen%></TD>
          <TD class=TableCell><%=tcumgen%></TD>
          <TD class=TableCell><%=tcumygen%></TD>
         </TR>
        <%}%> 
        <%tranList.clear();%>
        <%}%> 
        </TBODY></TABLE>
        </TD></TR>
        <tr><td align="center">
        
         <input type="hidden" name="todaydate" id="todaydate" value=<%=rdate%>>
         </TD></TR> </TBODY></TABLE>
     
</CENTER>

</FORM>

</BODY>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

</HTML>

