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



<html>
    <head>
     
        <script src="<%=request.getContextPath()%>/resources/Grid/GridE.js"> </script>
        <script type="text/javascript">  
  

<!-- Begin
function popUp(custid,type,rdate) {
location.href="OverAllCustomerReport.jsp?id="+custid+"&type="+type+"&rd="+rdate;
//location.href="FilterReportExcel.jsp?stateid="+state+"&wectype="+wec+"&fdate="+fdate+"&tdate="+tdate+"&siteid="+siteid+"&fobjectdesc="+fobjectdesc+"&ftypedesc="+ftypedesc+"&firstparam="+firstparam+"&secondparam="+secondparam+"&ebobject="+ebobject;
	
//day = new Date();
//id = day.getTime();
//eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,men ubar=0,resizable=0,width=400,height=10,left = 262,top = 234');");
}

</script>
 <LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
 
 
    </head>
   
    <body style="overflow: hidden">
    
   
    <form>
    <input type="hidden" name="custid" value="<%=request.getParameter("id") %>" />
    <input type="hidden" name="custid" value="<%=request.getParameter("rd") %>" />
   <% 
   
   
     String custid=request.getParameter("id");
     String rdate=request.getParameter("rd");
     String type=request.getParameter("type");
     String atype=request.getParameter("type");
     String adate="01/04/3009";
     SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
     // System.out.println(adate);
		java.util.Date ffd = format.parse(rdate);
		java.util.Date afd = format.parse(adate);
		long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
     if(type.equals("DM"))
     {
    	 type="M";
     }
     if(type.equals("DG"))
     {
    	 type="D";
     }
     if(type.equals("YR"))
     {
    	 type="Y";
     }
     //System.out.println(rdate);
     //System.out.println(type);
     
     String strXML = "<div id='menu' align='left'><table cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=0><tr class=TableTitleRow><td class=TableCell11>State wise Power Generation Summary</td></tr></table></div>"; 
   		if(diff<0)
   			{
	   			strXML +="<Table  cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=1><tr class=TableTitleRow><td class=class=TableCell1>Location</td><td class=TableCell11>Generation(KWH)</td><td class=TableCell11>Operating Hours(hrs)</td><td class=TableCell11>Lull HRS</td><td class=TableCell11>Capacity Factor(%)</td><td class=TableCell11>Machine Avail(%)</td><td class=TableCell11>Grid Avail(%)</td></tr>";
	   
			} else
			{ 
				strXML +="<Table  cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=1><tr class=TableTitleRow><td class=class=TableCell1>Location</td><td class=TableCell11>Generation(KWH)</td><td class=TableCell11>Operating Hours(hrs)</td><td class=TableCell11>Lull HRS</td><td class=TableCell11>Capacity Factor(%)</td><td class=TableCell11>Machine Avail(%)</td><td class=TableCell11>Grid Avail(%) Internal</td><td class=TableCell11>Grid Avail(%) External</td></tr>";
		   
			} 
   
     List tranList = new ArrayList();
     List sitetranList = new ArrayList();
     CustomerUtil secutils = new CustomerUtil();
     // String st="_blank";
     String cls = "TableRow1";
     int showgrid=0;
        tranList = (List)secutils.getStateTotal(custid,rdate,type);
        for (int i=0; i <tranList.size(); i++)
			{   showgrid++;
 				Vector v = new Vector();
 				v = (Vector)tranList.get(i);
 				String name = (String)v.get(0);
				String stateid = (String)v.get(7);
				//strXML +="<I Location='"+name+"' Generation='"+v.get(1)+"' HRS='"+v.get(2)+"' LullHrs='"+v.get(3)+"' MAvail='"+v.get(4)+"' GAvail='"+v.get(5)+"' CFactor='"+v.get(6)+"'>";
				 sitetranList.clear(); 
				sitetranList = (List)secutils.getSiteTotal(custid,rdate,stateid,type);
		        for (int j=0; j <sitetranList.size(); j++)
					{
		        	Vector v1 = new Vector();
	 				v1 = (Vector)sitetranList.get(j);
	 				String sname = (String)v1.get(0);
	 				int rem = 1;
					rem = j % 2;

					if (rem == 0)
						cls = "TableRow2";
					else
						cls = "TableRow1";
					
					String siteid = (String)v1.get(7);
					if(atype.equals("DM"))
					{
						if(diff<0)
			   			{
						strXML +="<tr class="+cls+" align=left><td class=TableCell1><a href=DayWiseMonthly.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate+" target='_blank'>"+sname+"</td><td align=center>"+v1.get(1)+"</td><td align=center>"+v1.get(2)+"</td>";
						if(v1.get(10).toString().equals("0"))
							{
								strXML +="<TD align=center>"+v1.get(3)+"</td><td align=center>"+v1.get(6)+"</td><TD align=center>"+v1.get(4)+"</td><td align=center>"+v1.get(5)+"</td></tr>";
			   				}
						else
							{
							strXML +="<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";
					   		
							}
						
			   			}
						else
						{
							strXML +="<tr class="+cls+" align=left><td class=TableCell1><a href=DayWiseMonthly.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate+" target='_blank'>"+sname+"</td><td align=center>"+v1.get(1)+"</td><td align=center>"+v1.get(2)+"</td>";
				   			
							if(v1.get(10).toString().equals("0"))
							{
								strXML +="<TD align=center>"+v1.get(3)+"</td><td align=center>"+v1.get(6)+"</td><td align=center>"+v1.get(8)+"</td><td align=center>"+v1.get(9)+"</td><td align=center>"+v1.get(5)+"</td></tr>";
			   				}
						else
							{
							strXML +="<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";
					   		
							}
						}
					}
					else if(atype.equals("DG"))
					{
						if(diff<0)
			   			{
						strXML +="<tr class="+cls+" align=left><td class=TableCell1><a href=NewDGRReport.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate+" target='_blank'>"+sname+"</td><td align=center>"+v1.get(1)+"</td><td align=center>"+v1.get(2)+"</td>";
							if(v1.get(10).toString().equals("0"))
							{
								strXML +="<TD align=center>"+v1.get(3)+"</td><td align=center>"+v1.get(6)+"</td><TD align=center>"+v1.get(4)+"</td><td align=center>"+v1.get(5)+"</td></tr>";
		   					}
						else
							{
								strXML +="<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";
				   		
							}
			   			
			   			
			   			}
						else
							{
								strXML +="<tr class="+cls+" align=left><td><a href=NewDGRReport.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate+" target='_blank'>"+sname+"</td><td align=center>"+v1.get(1)+"</td><td align=center>"+v1.get(2)+"</td>";
					   		
								if(v1.get(10).toString().equals("0"))
								{
								strXML +="<TD align=center>"+v1.get(3)+"</td><td align=center>"+v1.get(6)+"</td><td align=center>"+v1.get(8)+"</td><td align=center>"+v1.get(9)+"</td><td align=center>"+v1.get(5)+"</td></tr>";
		   						}
							else
								{
									strXML +="<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";
				   		
								}
						}
					}
					else
					{
						if(diff<0)
			   			{
							if(atype.equals("YR"))
							{
								strXML +="<tr class="+cls+" align=left><td class=TableCell1><a href=SiteCumulativeReport.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+atype+"&rd="+rdate+" target='_blank'>"+sname+"</td><td align=center>"+v1.get(1)+"</td><td align=center>"+v1.get(2)+"</td>";
									
							}
							else
							{
								strXML +="<tr class="+cls+" align=left><td class=TableCell1><a href=CustomerReport.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate+"  target='_blank'>"+sname+"</td><td align=center>"+v1.get(1)+"</td><td align=center>"+v1.get(2)+"</td>";
							}
							if(v1.get(10).toString().equals("0"))
							{
								strXML +="<TD align=center>"+v1.get(3)+"</td><td align=center>"+v1.get(6)+"</td><TD align=center>"+v1.get(4)+"</td><td align=center>"+v1.get(5)+"</td></tr>";
	   						}
						else
							{
								strXML +="<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";
			   			
							}
			   			
			   			}
						else
						{
							if(atype.equals("YR"))
							{
								strXML +="<tr class="+cls+" align=left><td class=TableCell1><a href=SiteCumulativeReport.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+atype+"&rd="+rdate+" target='_blank'>"+sname+"</td><td align=center>"+v1.get(1)+"</td><td align=center>"+v1.get(2)+"</td>";
									
							}
							else
							{
								strXML +="<tr class="+cls+" align=left><td class=TableCell1><a   href=CustomerReport.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate+" target='_blank'>"+sname+"</td><td align=center>"+v1.get(1)+"</td><td align=center>"+v1.get(2)+"</td>";
							}
								if(v1.get(10).toString().equals("0"))
								{
								strXML +="<TD align=center>"+v1.get(3)+"</td><td align=center>"+v1.get(6)+"</td><td align=center>"+v1.get(8)+"</td><td align=center>"+v1.get(9)+"</td><td align=center>"+v1.get(5)+"</td></tr>";
		   						}
							else
								{
									strXML +="<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";
				   		
								}
					
						}
					   	
					}
				}
		        if(diff<0)
	   			{
		        strXML +="<tr class=TableSummaryRow>";
		        	if(atype.equals("YR"))
					{
		        		strXML +="<td class=TableCell1><b><a href=StateCumulativeReport.jsp?stateid="+stateid+"&id="+custid+"&type="+atype+"&rd="+rdate+" target='_blank'>"+name+"</b></td>";	
					}
		        	else
		        	{
		        		strXML +="<td class=TableCell1><b>"+name+"</b></td>";
		        	}
		       			 strXML +="<td align=center><b>"+v.get(1)+"</b></td><td align=center><b>"+v.get(2)+"</b></td>";
				        if(v.get(10).toString().equals("0"))
						{
						strXML +="<td align=center><b>"+v.get(3)+"</b></td><td align=center><b>"+v.get(6)+"</b></td><td align=center><b>"+v.get(4)+"</b></td><td align=center><b>"+v.get(5)+"</b></td></tr>";
							}
					  else
						{
							strXML +="<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";
		   		
						}
	   			
	   			}
		        else
		        {
		        strXML +="<tr class=TableSummaryRow>";
		        if(atype.equals("YR"))
				{
	        		strXML +="<td class=TableCell1><b><a href=StateCumulativeReport.jsp?stateid="+stateid+"&id="+custid+"&type="+atype+"&rd="+rdate+">"+name+"</b></td>";	
				}
	        	else
	        	{
	        		strXML +="<td class=TableCell1><b>"+name+"</b></td>";
	        	}
		        
		        
		        
		        strXML +="<td align=center><b>"+v.get(1)+"</b></td><td  align=center><b>"+v.get(2)+"</b></td>";
			   	
				        if(v.get(10).toString().equals("0"))
						{
						strXML +="<td  align=center><b>"+v.get(3)+"</b></td><td  align=center><b>"+v.get(6)+"</b></td><td align=center><b>"+v.get(8)+"</b></td><td align=center><b>"+v.get(9)+"</b></td><td align=center><b>"+v.get(5)+"</b></td></tr>";
							}
					  else
						{
							strXML +="<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";
		   		
						}
		        }
		        sitetranList.clear();
		       
			}
          tranList.clear();
          // System.out.println(showgrid);
          if(showgrid>1)
          {
          tranList = (List)secutils.getOverallTotal(custid,rdate,type);
          
          for (int i=0; i <tranList.size(); i++)
     		{
       		Vector v = new Vector();
       		v = (Vector)tranList.get(i);
       		String name = (String)v.get(0);
       		
       		showgrid=1;
       		 if(atype.equals("D"))
		 {
            if(custid.equals("1000000762")){
            	 if(diff<0)
 	   			{
            		 strXML +="<tr class=TableSummaryRow align=center><td class=TableCell111 align=center><a href=javascript:onClick=popUp('"+custid+"','"+type+"','"+rdate+"')>"+name+"</a></td><td class=TableCell111 align=center>"+v.get(1)+"</td><td class=TableCell111  align=center>"+v.get(2)+"</td><td class=TableCell111  align=center>"+v.get(3)+"</td><td class=TableCell111 align=center>"+v.get(6)+"</td><td class=TableCell111 align=center>"+v.get(4)+"</td><td class=TableCell111 align=center>"+v.get(5)+"</td></tr>";
 	   			}
            	 else
            	 {
            		 strXML +="<tr class=TableSummaryRow align=center><td class=TableCell111 align=center><a href=javascript:onClick=popUp('"+custid+"','"+type+"','"+rdate+"')>"+name+"</a></td><td class=TableCell111 align=center>"+v.get(1)+"</td><td class=TableCell111  align=center>"+v.get(2)+"</td><td class=TableCell111  align=center>"+v.get(3)+"</td><td class=TableCell111 align=center>"+v.get(6)+"</td><td class=TableCell111 align=center>"+v.get(7)+"</td><td class=TableCell111 align=center>"+v.get(8)+"</td><td class=TableCell111 align=center>"+v.get(5)+"</td></tr>";
          	   		 
            	 }
            
            }
             else
             {
            	 if(diff<0)
  	   			{ 
                   strXML +="<tr class=TableSummaryRow align=center><td class=TableCell11>"+name+"</td><td class=TableCell111 align=center>"+v.get(1)+"</td><td class=TableCell111  align=center>"+v.get(2)+"</td><td class=TableCell111  align=center>"+v.get(3)+"</td><td class=TableCell111 align=center>"+v.get(6)+"</td><td class=TableCell111 align=center>"+v.get(4)+"</td><td class=TableCell111 align=center>"+v.get(5)+"</td></tr>";
  	   			}
            	 else
            	 {
            		 strXML +="<tr class=TableSummaryRow align=center><td class=TableCell11>"+name+"</td><td class=TableCell111 align=center>"+v.get(1)+"</td><td class=TableCell111  align=center>"+v.get(2)+"</td><td class=TableCell111  align=center>"+v.get(3)+"</td><td class=TableCell111 align=center>"+v.get(6)+"</td><td class=TableCell111>"+v.get(7)+"</td><td class=TableCell111>"+v.get(8)+"</td><td class=TableCell111 align=center>"+v.get(5)+"</td></tr>";
       	   				 
            	 }
             }
		 }
     		}
          }
            tranList.clear();
         strXML +="</table>";
	    
   
   %> 
   
   
    </form>
     <script type="text/javascript">
     
    parent.window.document.getElementById("progressbar").style.display ="block";
	parent.window.document.getElementById("progressbar").style.display ="none";
	
	</script>
   <% if(showgrid>0){ %> 
    
        <div style="width:100%;height:100%;" > 
 		     <%=strXML%>
        </div>
     <% } else{%> 
     <script type="text/javascript">
          alert("Sorry!Data For Selected Date or Month Or Year Not Available.");
         
      </script>
      <jsp:include page="Blank.jsp"/>
 
     <% }%>
     
                 
    </body>
</html>

