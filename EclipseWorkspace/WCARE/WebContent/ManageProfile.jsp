<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="com.enercon.global.utils.DynaBean"%>

<HTML>
<head>
<TITLE>Manage Profile</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>

<script type="text/javascript">
function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=SEC_LOGINDETAIL&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{  
	var cart = dataXml.getElementsByTagName("logindetail")[0];
	var items = cart.getElementsByTagName("lcode");	
		//var divdetails = document.getElementById("logindetail");
		//divdetails.innerHTML = "";
		for (var I = 0 ; I < items.length ; I++)
	   	{   	 
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("slogid")[0].firstChild;
	     	if (nname != null )
	     	{   
	     	    
	     		var scative=item.getElementsByTagName("sactive")[0].firstChild.nodeValue;
	     		if(scative=="1"){
	     		    document.forms[0].Admin_Input_Type.value="ManageProfile1";
	     			document.getElementById('ManageProfile').action ="<%=request.getContextPath()%>/ManageProfile1.do";
	     			}else{
	     			 document.forms[0].Admin_Input_Type.value="ManageProfile";
	     			document.getElementById('ManageProfile').action ="<%=request.getContextPath()%>/ManageProfile.do";
	     			}
	     	    
	     		document.forms[0].logIdtxt.value =item.getElementsByTagName("slogid")[0].firstChild.nodeValue;
	     		document.forms[0].CustOwneretxt.value =item.getElementsByTagName("soname")[0].firstChild.nodeValue;
	     		document.forms[0].CustOEmailtxt.value =item.getElementsByTagName("soemail")[0].firstChild.nodeValue;
	     		document.forms[0].CustOPhonetxt.value =item.getElementsByTagName("sophone")[0].firstChild.nodeValue;
	     		if(item.getElementsByTagName("sofax")[0].firstChild.nodeValue=="0")
	     		  document.forms[0].CustOFaxtxt.value ="";
	     		else
	     		 document.forms[0].CustOFaxtxt.value =item.getElementsByTagName("sofax")[0].firstChild.nodeValue;
	     		
	     		if(item.getElementsByTagName("socell")[0].firstChild.nodeValue=="0")
	     		  document.forms[0].CustOMobiletxt.value ="";
	     		else
	     		 document.forms[0].CustOMobiletxt.value =item.getElementsByTagName("socell")[0].firstChild.nodeValue;
	     		
	     		
	     		if(item.getElementsByTagName("odoab")[0].firstChild.nodeValue=="0")
	     		  document.forms[0].CustODOBtxt.value ="";
	     		else
	     		document.forms[0].CustODOBtxt.value =item.getElementsByTagName("odoab")[0].firstChild.nodeValue;
	     		
	     		if(item.getElementsByTagName("odoad")[0].firstChild.nodeValue=="0")
	     		  document.forms[0].CustODOAtxt.value ="";
	     		else
	     		 document.forms[0].CustODOAtxt.value =item.getElementsByTagName("odoad")[0].firstChild.nodeValue;
	     		document.forms[0].CustCNametxt.value =item.getElementsByTagName("sperson")[0].firstChild.nodeValue;
	     		document.forms[0].CustAddresstxt.value =item.getElementsByTagName("saddr")[0].firstChild.nodeValue;
	     		document.forms[0].CustCitytxt.value =item.getElementsByTagName("scity")[0].firstChild.nodeValue;
	     		document.forms[0].CustZiptxt.value =item.getElementsByTagName("szip")[0].firstChild.nodeValue;
	     		document.forms[0].CustEmailtxt.value =item.getElementsByTagName("semail")[0].firstChild.nodeValue; 
	     		document.forms[0].CustPhonetxt.value =item.getElementsByTagName("sphone")[0].firstChild.nodeValue;
	     		document.forms[0].CustMobiletxt.value =item.getElementsByTagName("scell")[0].firstChild.nodeValue;
	     		if(item.getElementsByTagName("sfax")[0].firstChild.nodeValue=="0")
	     		  document.forms[0].CustFaxtxt.value ="";
	     		else
	     		document.forms[0].CustFaxtxt.value =item.getElementsByTagName("sfax")[0].firstChild.nodeValue;
	     		if(item.getElementsByTagName("dobd")[0].firstChild.nodeValue=="0")
	     		  document.forms[0].CustDOBtxt.value ="";
	     		else
	     		document.forms[0].CustDOBtxt.value =item.getElementsByTagName("dobd")[0].firstChild.nodeValue;
	     		if(item.getElementsByTagName("doad")[0].firstChild.nodeValue=="0")
	     		  document.forms[0].CustDOAtxt.value ="";
	     		else
	     		document.forms[0].CustDOAtxt.value =item.getElementsByTagName("doad")[0].firstChild.nodeValue;
	     		
	     		document.forms[0].btnSubmit.value ="Update";
	     		
	   	 	}else{
	   	 		document.forms[0].Admin_Input_Type.value="ManageProfile";
	   	 		document.getElementById('ManageProfile').action ="<%=request.getContextPath()%>/ManageProfile.do";
	   	 	}   	 	
		} 
		
}
function checkWholeForm() {

    var why = "";
     	why += checkUsername(document.forms[0].CustOwneretxt.value,'Owner Name');
     	
     	why += checkUsername(document.forms[0].CustOEmailtxt.value,'Owner Email Address');
     	
     	why += checkUsername(document.forms[0].CustOPhonetxt.value,'Owner Phone Number');
     	
     	why += checkUsername(document.forms[0].CustCNametxt.value,'Contact Person Name');
     	
     	why += checkUsername(document.forms[0].CustAddresstxt.value,'Correpondence Address');
     	
     	why += checkUsername(document.forms[0].CustCitytxt.value,'City Name');
     	
     	why += checkUsername(document.forms[0].CustZiptxt.value,'ZIP code');
     	
     	why += checkUsername(document.forms[0].CustOEmailtxt.value,'Email Address Of Contact Person');
     	
     	why += checkUsername(document.forms[0].CustPhonetxt.value,'Phone Number Of Contact Person');
     	
     	why += checkUsername(document.forms[0].CustMobiletxt.value,'Cell Number Of Contact Person');
     	
     	why +=checkEmail(document.forms[0].CustOEmailtxt.value);
     	why +=checkEmail(document.forms[0].CustEmailtxt.value);
     	why +=checkPhone(document.forms[0].CustMobiletxt.value);
     	
     	
     	
    if (why != "") {
       alert(why);
       return false;
    }
   
}
function checkDropdown(choice) {
    var error = "";
    if (choice == 0) {
       error = "You didn't choose an option from the drop-down list.\n";
    }    
return error;
}  
function checkEmail (strng) {
var error="";
if (strng == "") {
   error = "Incorrect Email ID.\n";
}

    var emailFilter=/^.+@.+\..{2,3}$/;
    if (!(emailFilter.test(strng))) { 
       error = "Incorrect Email ID.\n";
    }
    else {
//test email for illegal characters
       var illegalChars= /[\(\)\<\>\,\;\:\\\"\[\]]/
         if (strng.match(illegalChars)) {
          error = "Incorrect Email ID.\n";
       }
    }
return error;    
}

function checkUsername (strng,str) {
	//var SDate = strng.value; 
	       
 	var error = "";
 	if (strng=='') {
   	 error = "You didn't enter "+str+" .\n";
 		}
 		return error;
 }   
 
 function checkPhone (strng) {
var error = "";


var stripped = strng.replace(/[\(\)\.\-\ ]/g, ''); //strip out acceptable non-numeric characters
    if (isNaN(parseInt(stripped))) {
       error = "Incorrect  Cell number.";
  
    }
    if (!(stripped.length == 11)) {
	error = "Incorrect  Cell number.\n";
    } 
return error;
}
function hide(c)
	{	var d=c
		alert(d);
		
		if(d=="b"){
			alert(d);
 				alert(document.getElementById("b").style.display="block");
 				document.getElementById("b").style.display="block";
				document.getElementById("b").style.visibility="visible";
				document.getElementById("a").style.display="none";
				document.getElementById("a").style.visibility="hidden";
				
		}else{
				document.getElementById("b").style.display="none";
				document.getElementById("b").style.visibility="hidden";
				document.getElementById("a").style.display="block";
				document.getElementById("a").style.visibility="visible";
		}
	
	}  	
</script>
<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	String Customeridtxt = "",lid="",cname="";
	%>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</HEAD>

<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
		else
		{
			lid =session.getAttribute("loginID").toString();
			cname=session.getAttribute("Name").toString();
		}



String ldid = "",ccode = "",oname="",ocell="",ophone="",oemail="",ofax="",odob="",odoa="",cphone="",ccellno="",ccontact="",cemail="",ccity="",czip="",cfax="",cdob="",cdoa="";

DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	ldid=dynabean.getProperty("logIdtxt").toString();
	oname = dynabean.getProperty("CustOwneretxt").toString();
	oemail = dynabean.getProperty("CustOEmailtxt").toString();
	ophone = dynabean.getProperty("CustOPhonetxt").toString();
	ocell = dynabean.getProperty("CustOMobiletxt").toString();
	ofax = dynabean.getProperty("CustOFaxtxt").toString();
	
	odob = dynabean.getProperty("CustODOBtxt").toString();
	odoa = dynabean.getProperty("CustODOAtxt").toString();
	
	ccode=dynabean.getProperty("CustCNametxt").toString();
	ccontact = dynabean.getProperty("CustAddresstxt").toString();
	 ccity= dynabean.getProperty("CustCitytxt").toString();
	czip= dynabean.getProperty("CustZiptxt").toString();
	cemail = dynabean.getProperty("CustEmailtxt").toString();
	cphone = dynabean.getProperty("CustPhonetxt").toString();
	ccellno = dynabean.getProperty("CustMobiletxt").toString();
	cfax = dynabean.getProperty("CustFaxtxt").toString();
	cdob = dynabean.getProperty("CustDOBtxt").toString();
	cdoa = dynabean.getProperty("CustDOAtxt").toString();
	session.removeAttribute("dynabean");
	
	
}

%>
<BODY onLoad="findApplication()">
<form   id="ManageProfile" action="<%=request.getContextPath()%>/ManageProfile.do" method="post" name="ManageProfile" onSubmit="return checkWholeForm()">

<table align="center" border="0" cellpadding="0" cellspacing="0" width="799">  <tbody>
    <tr>
      <td class="newhead1" width="10"></td>
      <th class="headtext" width="283"><div align="center">Login Details </div></th>
      <td width="301"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
      <td class="newhead3" width="13">&nbsp;</td>
      <td class="newhead4" width="10"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
    </tr>
    <tr>
    <td background="<%=request.getContextPath()%>/resources/images/line_l.gif" width="10"><img src="<%=request.getContextPath()%>resources/images/pixel.gif"				 						border="0" width="1" height="1"></td>
    <td colspan=3 width=597>
    
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tbody>
        <tr>
        
        <td bgcolor=#dbeaf5><table border="0" cellpadding="2" cellspacing="1" width="797">
          <tbody>
            <tr bgcolor=#ffffff>
              <td id="t_company" width="172">&nbsp;</td>
              <td colspan="3" valign="top"><div align="center">Customer Name:</div></td>
              <td id="t_company" width="198"><input name="CustNametxt2" id="CustNametxt2" value="<%=cname%>" size="32" class="ctrl" type="text"></td>
              <td colspan="3" valign="top">&nbsp;</td>
            </tr>
			 <tr bgcolor=#ffffff>
              <td id="t_company" width="172">Owner/DirectorName:</td>
              <td colspan="7" valign="top"><input name="CustOwneretxt" id="CustOwneretxt" value="<%=oname%>" size="62" class="ctrl" type="text"><span class="style1">*</span></td>
           </tr>
            <tr bgcolor=#ffffff>
              <td id="t_country" >Email </td>
              <td colspan="3"><input name="CustOEmailtxt"  id="CustOEmailtxt" value="<%=oemail%>" class="ctrl" size="32" type="text"><span class="style1">*</span></td>
              <td id="t_country" >Office Phone Number:<span class="style1"><br>(eg.022-023232,343434)</span> </td>
              <td width=220 colspan="3"><input name="CustOPhonetxt"  id="CustOPhonetxt" value="<%=cphone%>" class="ctrl" size="32" type="text"> <span class="style1">*</span>             </td>
            </tr>
            <tr bgcolor=#ffffff>
              <td id=t_telephone_number>Mobile No: </td>
              <td colspan="3"><input name="CustOMobiletxt" id="CustOMobiletxt" value="<%=ophone%>" size="32" class="ctrl" type="text">              </td>
              <td id=t_telephone_number>Fax &nbsp;Number: </td>
              <td width=220 colspan="3"><input name="CustOFaxtxt" id="CustOFaxtxt" value="<%=ofax%>" size="32" class="ctrl" type="text">              </td>
            </tr>
			
            <tr bgcolor=#ffffff>
              <td id=t_country >Date Of Birth</td>
              <td colspan="3"><input name="CustODOBtxt" id="CustODOBtxt" value="<%=odob%>" size="25" class="ctrl" type="text" onFocus="dc3.focus()" >            
              
                 <a href="javascript:void(0)" id="dc3" onClick="if(self.gfPop)gfPop.fPopCalendar(document.ManageProfile.CustODOBtxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
            </td>
              
              <td id=t_country >Date Of Anniversary</td>
              <td width=220 colspan="3"><input name="CustODOAtxt" id="CustODOAtxt" value="<%=odob%>" size="25" class="ctrl" type="text" onFocus="dc2.focus()" >            
              
                <a href="javascript:void(0)" id="dc2" onClick="if(self.gfPop)gfPop.fPopCalendar(document.ManageProfile.CustODOAtxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
            </td>
            </tr>
			 <tr bgcolor=#ffffff>
              <td id=t_country colspan="8" align="center"> <strong>Correspondence  Details </strong></td>
            </tr>
            <tr bgcolor="#ffffff">
              <td id=t_country width="172" colspan="3" align="right">  Contact Person:    </td> 
              
             <td id=t_country width="198" colspan="5"><input name="CustCNametxt" id="CustCNametxt" value="<%=ccode%>" size="52" class="ctrl" type="text"><span class="style1">*</span></td>
              </tr>
              
			<tr bgcolor="#ffffff">
              <td id="t_country" width="172">Correspondence Address: </td>
              <td colspan="7">
			  <textarea cols="60" name="CustAddresstxt" id="CustAddresstxt" value="<%=ccontact%>" class="ctrl">
				</textarea>
			  <span class="style1">*</span>
			        </td>
              </tr>
              
               <tr bgcolor=#ffffff>
              <td id=t_country >City:</td>
              <td colspan="3"><input name="CustCitytxt" id="CustCitytxt" value="<%=ccity%>" size="25" class="ctrl" type="text">     <span class="style1">*</span>         </td>
              <td id=t_country >Postal Code:</td>
              <td width=220 colspan="3"><input name="CustZiptxt" id="CustZiptxt" value="<%=czip%>" size="25" class="ctrl" type="text">        <span class="style1">*</span>      </td>
            </tr>
			<tr bgcolor="#ffffff">
              <td id="t_country" width="172">Email: </td>
              <td colspan="3"><input name="CustEmailtxt" id="CustEmailtxt" value="<%=cemail%>" size="25" class="ctrl" type="text">   <span class="style1">*</span>           </td>
              <td id="t_country" width="198">Office Phone Number:<span class="style1"><br>(eg.022-023232,343434)</span></td>
              <td colspan="3"><input name="CustPhonetxt" id="CustPhonetxt" value="<%=cphone%>" size="25" class="ctrl" type="text">         <span class="style1">*</span>     </td>
            </tr>
            <tr bgcolor="#ffffff">
              <td id="t_email" width="172">Mobile No:<span class="style1">(eg. 09812345678)</span> </td>
              <td colspan="3"><input name="CustMobiletxt"  id="CustMobiletxt" value="<%=ccellno%>" size="25" class="ctrl" type="text">       <span class="style1">*</span>       </td>
              <td id="t_email" width="198">Fax &nbsp;Number: </td>
              <td colspan="3"><input name="CustFaxtxt"  id="CustFaxtxt" value="<%=cfax%>" size="25" class="ctrl" type="text">              </td>
            </tr>
            <tr bgcolor="#ffffff">
              <td id="t_general_information" width="172">Date Of Birth</td>
              <td colspan="3" bgcolor="#ffffff"><input name="CustDOBtxt"  id="CustDOBtxt" value="<%=cdob%>" size="25" class="ctrl" type="text" onFocus="dc1.focus()">
         <a href="javascript:void(0)" id="dc1" onClick="if(self.gfPop)gfPop.fPopCalendar(document.ManageProfile.CustDOBtxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
            </td>
              <td id="t_general_information" width="198">Date Of Anniversary</td>
              <td colspan="3" bgcolor="#ffffff"><input name="CustDOAtxt"  id="CustDOAtxt" value="<%=cdoa%>" size="25" class="ctrl" type="text" onFocus="dc.focus()" >
            <a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.ManageProfile.CustDOAtxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
            </td>
            </tr>
            <tr class="bgcolor"> 
			<td colspan="8" class="bgcolor">
				<span class="style1"> Note: All * Specified Fields are Mandatory.</span> </td>
		</tr>	
            <tr class="bgcolor"> 
			<td colspan="4">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>			</td>
		</tr>	
          </tbody>
        </table></td>
        </tr>
      </tbody>
    </table>
  <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
  </td>
  
  <td background="<%=request.getContextPath()%>/resources/images/line_r.gif" width="10"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
  </tr>
  <tr>
    <td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
    <td colspan="4" align="right" bgcolor="#006633"><table border="0" cellpadding="0" cellspacing="0">
        <tbody>
          <tr>
            <td class="btn" width="100"><input name="btnSubmit" id="btnSubmit" value="Submit" class="btnform" type="Submit"  ></td>
            <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
            <td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="ManageProfile" />
              <input type="hidden" name="logIdtxt" id="logIdtxt"  value="<%=ldid%>"/>
              <input type="hidden" name="logIdmastertxt" id="logIdmastertxt" value="<%=lid%>" />
              <input name="Reset" value="Cancel" class="btnform" type="reset"></td>
            <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
          </tr>
        </tbody>
      </table></td>
  </tr>
  </tbody>
</table>
</form>
</BODY>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</HTML>
