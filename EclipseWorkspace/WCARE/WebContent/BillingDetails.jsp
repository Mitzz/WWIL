<%-- <%@ taglib uri="http://displaytag.sf.net" prefix="display"%> --%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %> 
<%@ page import="com.enercon.customer.bean.*" %>  
<%@ page import="com.enercon.customer.dao.*" %>
<%@ page import="com.enercon.customer.util.*" %>
<%@ page import= "java.sql.Connection" %>
<%@ page import ="java.sql.PreparedStatement"%>
<%@ page  import= "java.sql.*" %>
<%@ page import ="java.text.*"%>
<%@ page  import="java.util.*"%>

<%@ page import= "javax.servlet.*"%>
<html>
	<head>
		<title>Billing Details</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytagex.css">
		<script src="<%=request.getContextPath()%>/js/RowHandlers.js" language="javascript" type="text/javascript" /></script>
	</head>
	<body>
<%

String custid = request.getParameter("id");
String month = request.getParameter("month");
String year = request.getParameter("year");
String site = request.getParameter("site");
String state = request.getParameter("state");

BillingDetail billingDetail; 
List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil custutils = new CustomerUtil();
tranList = (List) custutils.getBillingDetail(custid, month, year,site,state);

ArrayList billingDetails = new ArrayList();
int g=0;
for (int i = 0; i < tranList.size(); i++) {
	Vector v = new Vector();
	v = (Vector) tranList.get(i);
	g=g+1;
	
	billingDetail = new BillingDetail();
	//billingDetail.setId("1");
	billingDetail.setSiteName((String)v.get(0));
	billingDetail.setEbName((String)v.get(1));
	billingDetail.setMonth((String)v.get(2));

	
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	java.util.Date ffd = format.parse(v.get(3).toString());
	
	billingDetail.setFromDate(ffd);
	ffd = format.parse(v.get(4).toString());
	billingDetail.setToDate(ffd);
	
	Float f=Float.parseFloat(v.get(5).toString());
	
	//billingDetail.setWecgen(f);
	// f=Float.parseFloat(v.get(5).toString());
	billingDetail.setKwhExp(f);
	f=Float.parseFloat(v.get(6).toString());
	billingDetail.setKwhImp(f);
	f=Float.parseFloat(v.get(8).toString());
	billingDetail.setRkvhExpLead(f);
	f=Float.parseFloat(v.get(7).toString());
	billingDetail.setRkvhExpLag(f);
	f=Float.parseFloat(v.get(9).toString());
	billingDetail.setRkvhImpLead(f);
	f=Float.parseFloat(v.get(10).toString());
	billingDetail.setRkvhImpLag(f);
	f=Float.parseFloat(v.get(11).toString());
	billingDetail.setKvhExp(f);
	f=Float.parseFloat(v.get(12).toString());
	billingDetail.setKvhImp(f);
	f=Float.parseFloat(v.get(13).toString());
	billingDetail.setWecgen(f);
	billingDetails.add(billingDetail);
}

//config.getServletContext().setAttribute("orderDetails",orderDetails);


//System.out.println(config.getServletContext().getAttribute("orderDetails"));
session.setAttribute("orderDetails",billingDetails);
//System.out.println(session.getAttribute("orderDetails"));

if(g>0){
%>


		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="3" class="pageHeader">
					<table width="100%" border=" 0" cellspacing="0" cellpadding="0" style="display:inline">
						<tr>
							<td>
								<div class="pageHeaderText">
									Generation Billing Report
								</div>
							</td>
							<!-- For Internet Explorer, this <td> must have no spaces or line breaks after the <img> -->
							<td class="pageHeader" width="100%" align="right"><img src="<%=request.getContextPath()%>/images/am.GIF" class="logo" /></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="content" valign="top">
					<!--  Note:  the orderDetails ArrayList was created in DataLoader's contextInitialized() method -->
					<display:table name="sessionScope.orderDetails" export="true" id="row" class="dataTable" pagesize="30" cellspacing="0" >
						
						<display:column property="siteName" title="Site Name" sortable="true" group="1" class="siteName" headerClass="siteName" />
						<display:column property="ebName" title="EB Desc" sortable="true" group="1" class="ebName" headerClass="ebName" />
							<display:column property="month" title="Month" sortable="true" group="1" class="month" headerClass="month" />
							<display:column property="toDate" title="Bill From Date" sortable="true" format="{0,date,dd-MM-yyyy}"  />
						     <display:column property="fromDate" title="Bill To Date" sortable="true" format="{0,date,dd-MM-yyyy}"  />
							<display:column property="wecgen" title="WEC Generation" sortable="true" group="1" class="wecgen" headerClass="wecgen" />
						   	<display:column property="kwhExp" title="KWH EXP" sortable="true" group="1" class="kwhExp" headerClass="kwhExp" />
						   <display:column property="kwhImp" title="KWH IMP" sortable="true" group="1" class="kwhImp" headerClass="kwhImp" />
						     <display:column property="rkvhExpLag" title="RKVAH EXP LEAD" sortable="true" group="1" class="rkvhExpLag" headerClass="rkvhExpLag" />
						  <display:column property="rkvhExpLead" title="RKVAH EXP LEAD" sortable="true" group="1" class="rkvhExpLead" headerClass="rkvhExpLead" />
						     <display:column property="rkvhImpLead" title="RKVAH IMP LEAD" sortable="true" group="1" class="rkvhImpLead" headerClass="rkvhImpLead" />
						  <display:column property="rkvhImpLag" title="RKVAH IMP LAG" sortable="true" group="1" class="rkvhImpLag" headerClass="rkvhImpLag" />
						 <display:column property="kvhExp" title="KVAH EMP" sortable="true" group="1" class="kvhExp" headerClass="kvhExp" />
						  <display:column property="rkvhImpLag" title="KVAH IMP" sortable="true" group="1" class="kvhImp" headerClass="kvhImp" />
						   		</display:table>

				<%}else{ %>
				
				<td>No Billing Data</td>
				<%} %>
				<td class="rightColumn" valign="top">
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</html>
