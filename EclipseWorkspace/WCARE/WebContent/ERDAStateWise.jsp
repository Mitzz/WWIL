<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@page import="com.enercon.security.utils.SecurityUtils"%>

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
<BODY>
<FORM  >

 
<%
String cls = "TableRow1";
//System.out.println("LoginType"+session.getAttribute("LoginType").toString());



String cid = request.getParameter("cid").toString();
String sid = request.getParameter("sid").toString();
String stname = request.getParameter("stname").toString();
String stcap = request.getParameter("stcap").toString();
String ardate = request.getParameter("rdate").toString();

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/yyyy");

Date  date1 = (Date)dateFormat.parse(ardate);
String nextDate = dateFormat1.format(date1.getTime());

String month = ardate.substring(3,5);
String year = ardate.substring(6);

int intMonth = Integer.parseInt(month);

int intYearH = Integer.parseInt(year);
int intYearL = 0;
if(intMonth<=3){
	intYearL = intYearH-1;
	intYearH = intYearH;    
}else{
	intYearL = intYearH;
	intYearH = intYearH+1;	
}

String astname=stname.replace(" ",",");


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
     SecurityUtils secUtil = new SecurityUtils();
 	 List tranList1 = new ArrayList();
 	 String logid=session.getAttribute("loginID").toString();
 	
 	//tranList1 = (List)session.getAttribute("custtypee");
 	//System.out.println("tranList1"+tranList1);
 	 //if(tranList1.size()==0)
 	 //{
 	//	 tranList1=secUtil.getcustomerdetails(logid);
 	 //}
       //String custid=request.getParameter("id");
     //String rdate=request.getParameter("rd");
   String custid="";
     
     List tranList = new ArrayList();
     List sitetranList = new ArrayList();
     List msgList = new ArrayList();
     CustomerUtil secutils = new CustomerUtil();
    
   //  tranList = (List)secutils.getCustomerDetail(logid); 
    // //System.out.println("customer:"+tranList.size());
         // tranList = (List)secutils.getStateTotal(custid,rdate,"D"); 
  %>



<TABLE cellSpacing=0 cellPadding=0 width="90%"  border=0>
  <TBODY>
  
   
    
  <TR>
 
    <TD>
    
    <TABLE cellSpacing=1 cellPadding=2 width="100%" border="1">
          <TBODY>
        
       <%  
       
       //String custid=request.getParameter("id");
     //String rdate=request.getParameter("rd");
     
      tranList.clear();
      //tranList = (List)secutils.getERDADetail1(ardate);
      tranList = (List)secutils.getERDAWECWiseDetail(cid,sid,ardate);
     
     // msgListsize = tranList.size();
      %>
     <TR class=TableTitleRow>
       
      <TD class=TableCell colspan="5" ><font size="5">Vaayu Windfarm Power Generation Report</font>	</TD>
		<TD class=TableCell  ><font size="2">Export To :
     <input align="right" type="button" value="Excel" onClick=location.href="ExcelERDAStateWise.jsp?cid=<%=cid%>&sid=<%=sid%>&rdate=<%=ardate%>&stname=<%=astname%>&stcap=<%=stcap%>">	
      
	 </TD>
       
    </TR>
     
      
    
      <TR class=TableTitleRow>
      <!-- <TD class=TableCell colspan="4" ><font size="2">Power Generation Date:<%=ardate %></font></TD> -->
      <TD class=TableCell colspan="3" ><font size="2">State:&nbsp;<%=stname%></font></TD>
      <TD class=TableCell ><font size="2">Project Capacity:&nbsp;<%=stcap%></font></TD>
     
      <TD class=TableCell COLSPAN="2">Cumulative </TD>
      
    </TR>
      <TR class=TableTitleRow>
      <TD class=TableCell colspan="3" >Generation as on :&nbsp;<%=ardate%></TD>
       <TD class=TableCell >Daily</TD>
       <TD class=TableCell >Month (<%=nextDate.substring(3,6)%>)</TD>
       <TD class=TableCell >FY (<%=intYearL%>-<%=intYearH%>)</TD>
    </TR>
    	  
    	 
          <TR class=TableTitleRow>
          
             <TD class=TableCell1 width="2%">Sr.</TD>           
             <TD class=TableCell width="15%" colspan="2" align="center">WEC Name</TD>            
           
             <!-- <TD class=TableCell width="9%">Project Size(MW) </TD> -->
             <TD class=TableCell width="10%">Generation (KWH)</TD>
           
             <TD class=TableCell width="10%">Generation (KWH)</TD>
             <TD class=TableCell width="10%">Generation (KWH)</TD>
             
          </TR>
   <%  	 int tcumgen=0;
         int tcumygen=0;
         int tgen=0;
         double tcap=0;
         double tcap1=0;
         String pname="";
         String name="";
         String psname="";
         String sname="";
         DecimalFormat formatter = new DecimalFormat("#########0.00");
         
         for (int i=0; i <tranList.size(); i++)
			{
        	 
        	 
        		
        		//System.out.println("tranList.size()="+tranList.size());
				Vector v = new Vector();
				v = (Vector)tranList.get(i);
				pname=name;
				name = (String)v.get(0);
				
				psname=sname;
				tcap1 = Double.parseDouble(v.get(1).toString());
			
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
				
				if(!v.get(1).toString().equals("0"))
	            {
	            	
	            	tcap=tcap+Double.parseDouble(v.get(1).toString());
	            }
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
            String aval="Yet To Publish";
            if(!v.get(1).toString().equals("0"))
            {
            	aval=v.get(1).toString();
            	tgen=tgen+Integer.parseInt(aval);
            }
            	
            String cval="Yet To Publish";
            if(!v.get(2).toString().equals("0"))
            {
            	cval=v.get(2).toString();
            	tcumgen=tcumgen+Integer.parseInt(cval);
            }
            String yval="Yet To Publish";
            if(!v.get(3).toString().equals("0"))
            {
            	yval=v.get(3).toString();
            	tcumygen=tcumygen+Integer.parseInt(yval);
            }	
        
         	%>
  
        <TR class=<%=cls%>>
          <TD class=TableCell1><%=i+1%></TD>
          <TD class=TableCell colspan="2"><%=v.get(0)%></TD>
          <!-- <TD class=TableCell1><%=v.get(1)%></TD> -->
        
          
          <TD class=TableCell><%=aval%></TD>
          <TD class=TableCell><%=cval%></TD>
           <TD class=TableCell><%=yval%></TD>
         </TR>
        
        <% }%> 
        <%tranList.clear();%>
        
        
        <TR class=<%=cls%>>
          <TD class=TableCell colspan="3"><b>Total</b></TD>
          <!-- <TD class=TableCell><%=formatter.format(tcap)%></TD> -->
          <TD class=TableCell><b><%=tgen%></b></TD>
          <TD class=TableCell><b><%=tcumgen%></b></TD>
          <TD class=TableCell><b><%=tcumygen%></b></TD>
         </TR>
        <%} %> 
        </TBODY></TABLE>
        </TD></TR>
        <tr><td align="center">
        
         <input type="hidden" name="todaydate" id="todaydate" value=<%=ardate%>>
         </TD></TR> </TBODY></TABLE>
     
</CENTER>

</FORM>

</BODY>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

</HTML>

