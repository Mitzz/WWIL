<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@page contentType="application/vnd.ms-excel"%><%@page pageEncoding="UTF-8"%><%
request.setCharacterEncoding("utf-8");
String file = request.getParameter("File"); 
if(file==null || file.equals("")) file="ExportData.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@page import="com.enercon.security.utils.SecurityUtils"%>


<HTML><HEAD>

<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>



<% 
response.setHeader("Pragma","no-cache");
//response.getOutputStream().flush();
//response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

%>

</HEAD>
<BODY>

<%

//System.out.println("LoginType"+session.getAttribute("LoginType").toString());


String ardate = "";
//System.out.println("Previous date: " + rdate);
//System.out.println("Currnent date: " + ardate);
//System.out.println("Next date: " + nextDate);

//System.out.println(request.getParameter("Datetxt"));
if(request.getParameter("Datetxt")!=null)
{
	ardate=request.getParameter("Datetxt");
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



   <TABLE  width="100%" border="1">
          <TBODY>
        
       <%  
       
       //String custid=request.getParameter("id");
     //String rdate=request.getParameter("rd");
     String sateid = "";
     
      tranList.clear();
     // tranList = (List)secutils.getERDADetail(custid,sateid,ardate); 
      tranList = (List)secutils.getERDADetail1(ardate);
     
     // msgListsize = tranList.size();
      %>
     <TR >
       
      <TD  colspan="6" align="center" ><font size="5">Vaayu Windfarm Power Generation Report</font>	</TD>
	
     
       
    </TR>
     
      
    
      <TR >
      <TD  colspan="4" ><font size="2"></font></TD>
     
       <TD  COLSPAN="2">Cumulative </TD>
      
    </TR>
      <TR >
      <TD  colspan="3" >&nbsp;</TD>
       <TD  >Daily</TD>
       <TD  >Month (July)</TD>
       <TD  >FY (2011-12)</TD>
    </TR>
    	  
    	 
          <TR >
            <TD  width="2%">Sr.</TD>
           
            <TD  width="15%">Project</TD>
            
           
            <TD  width="9%">Project Size(MW) </TD>
            <TD  width="10%">Generation (KWH)</TD>
           
             <TD  width="10%">Generation (KWH)</TD>
             <TD  width="10%">Generation (KWH)</TD>
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
        
        <TR >
          <TD ><%=i+1%></TD>
          
          <TD >
         
          <%=v.get(1)%>
         
          
          
          </TD>
        
          <TD ><%=v.get(3)%></TD>
          <TD ><%=aval%></TD>
          <TD ><%=cval%></TD>
          <TD ><%=yval%></TD>
         </TR>
        
        <% }}%> 
        
        
        <%if(tranList.size()>1){ %>
        <TR >
          <TD  colspan="2"><b>Total</b></TD>
          <TD ><%=formatter.format(tcap)%></TD>
          <TD ><%=tgen%></TD>
          <TD ><%=tcumgen%></TD>
          <TD ><%=tcumygen%></TD>
         </TR>
        <%}%> 
        <%tranList.clear();%>
        <%}%> 
        </TBODY></TABLE>
        
     
</CENTER>



</BODY>


</HTML>

